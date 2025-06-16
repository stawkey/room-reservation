package io.github.stawkey.roomreservation.service;

import io.github.stawkey.roomreservation.dto.ReservationStatus;
import io.github.stawkey.roomreservation.entity.Reservation;
import io.github.stawkey.roomreservation.entity.Room;
import io.github.stawkey.roomreservation.repository.ReservationRepository;
import io.github.stawkey.roomreservation.repository.RoomRepository;
import io.github.stawkey.roomreservation.util.Result;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    private static final Logger log = LoggerFactory.getLogger(ReservationService.class);

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    
    public ReservationService(ReservationRepository reservationRepository, RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
        log.info("ReservationService initialized with repositories");
    }
    
    public Result<Reservation> getReservationById(Long id) {
        log.info("Getting reservation with ID: {}", id);
        try {
            Optional<Reservation> reservation = reservationRepository.findById(id);
            if (reservation.isPresent()) {
                log.info("Reservation found with ID: {}", id);
                return Result.success(reservation.get());
            } else {
                log.warn("Reservation not found with ID: {}", id);
                return Result.failure("Reservation not found with ID: " + id);
            }
        } catch (Exception e) {
            log.error("Error getting reservation with ID: {}", id, e);
            return Result.failure("Failed to get reservation: " + e.getMessage());
        }
    }
    
    public Result<Page<Reservation>> listReservations(
            OffsetDateTime start,
            OffsetDateTime end,
            List<Long> roomIds,
            String sort,
            String order,
            int page,
            int pageSize
    ) {
        log.info("Listing reservations with filters - page: {}, pageSize: {}", page, pageSize);
        log.debug("Filter parameters - start: {}, end: {}, roomIds: {}, sort: {}, order: {}", 
                  start, end, roomIds, sort, order);
        try {
            Sort.Direction direction = "desc".equalsIgnoreCase(order) ? Sort.Direction.DESC : Sort.Direction.ASC;
            String sortField = getSortField(sort);
            log.debug("Using sort field: {} with direction: {}", sortField, direction);
            
            Sort sorting = Sort.by(direction, sortField);
            Pageable pageable = PageRequest.of(page - 1, pageSize, sorting);
            Specification<Reservation> spec = (root, query, builder) -> null;

            if (start != null) {
                log.debug("Adding start date filter: {}", start);
                spec = addSpecification(spec, (root, query, builder) ->
                    builder.greaterThanOrEqualTo(root.get("startDate"), start));
            }

            if (end != null) {
                log.debug("Adding end date filter: {}", end);
                spec = addSpecification(spec, (root, query, builder) ->
                    builder.lessThanOrEqualTo(root.get("endDate"), end));
            }

            if (roomIds != null && !roomIds.isEmpty()) {
                log.debug("Adding room IDs filter: {}", roomIds);
                spec = addSpecification(spec, (root, query, builder) ->
                    root.get("room").get("id").in(roomIds));
            }

            Page<Reservation> results = reservationRepository.findAll(spec, pageable);
            log.info("Found {} reservations (total: {})", 
                    results.getNumberOfElements(), results.getTotalElements());
            return Result.success(results);
        } catch (Exception e) {
            log.error("Failed to list reservations", e);
            return Result.failure("Failed to list reservations: " + e.getMessage());
        }
    }

    public Result<Page<Reservation>> getReservationsForRoom(
            Long roomId,
            OffsetDateTime start,
            OffsetDateTime end,
            int page,
            int pageSize
    ) {
        log.info("Getting reservations for room ID: {}, page: {}, pageSize: {}", roomId, page, pageSize);
        log.debug("Filter parameters - start: {}, end: {}", start, end);
        try {
            if (!roomRepository.existsById(roomId)) {
                log.warn("Room not found with ID: {} when fetching reservations", roomId);
                return Result.failure("Room not found with ID: " + roomId);
            }
            
            Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("startDate").ascending());
            Specification<Reservation> spec = (root, query, builder) -> builder.equal(root.get("room").get("id"), roomId);

            if (start != null) {
                log.debug("Adding start date filter: {}", start);
                spec = addSpecification(spec, (root, query, builder) ->
                    builder.greaterThanOrEqualTo(root.get("startDate"), start));
            }

            if (end != null) {
                log.debug("Adding end date filter: {}", end);
                spec = addSpecification(spec, (root, query, builder) ->
                    builder.lessThanOrEqualTo(root.get("endDate"), end));
            }

            Page<Reservation> results = reservationRepository.findAll(spec, pageable);
            log.info("Found {} reservations for room ID: {} (total: {})", 
                     results.getNumberOfElements(), roomId, results.getTotalElements());
            return Result.success(results);
        } catch (Exception e) {
            log.error("Failed to get reservations for room ID: {}", roomId, e);
            return Result.failure("Failed to get reservations for room: " + e.getMessage());
        }
    }

    @Transactional
    public Result<Reservation> createReservation(Long roomId, Reservation reservation) {
        log.info("Creating reservation for room ID: {}, start: {}, end: {}", 
                roomId, reservation.getStartDate(), reservation.getEndDate());
        try {
            Optional<Room> roomOpt = roomRepository.findById(roomId);

            if (roomOpt.isEmpty()) {
                log.warn("Room not found with ID: {} when creating reservation", roomId);
                return Result.failure("Room not found with ID: " + roomId);
            }

            log.debug("Checking for conflicting reservations");
            Result<Boolean> conflictResult = hasConflictingReservation(roomId, reservation.getStartDate(),
                    reservation.getEndDate(), null);

            if (conflictResult.isFailure()) {
                log.error("Error checking for conflicts: {}", conflictResult.getError());
                return Result.failure(conflictResult.getError());
            }

            if (conflictResult.getValue()) {
                log.warn("Reservation conflicts with an existing booking for room ID: {}", roomId);
                return Result.failure("Reservation conflicts with an existing booking");
            }

            Room room = roomOpt.get();
            log.debug("Using room: {} (ID: {})", room.getName(), room.getId());
            reservation.setRoom(room);
            Reservation saved = reservationRepository.save(reservation);
            log.info("Reservation created successfully with ID: {}", saved.getId());
            return Result.success(saved);
        } catch (Exception e) {
            log.error("Failed to create reservation for room ID: {}", roomId, e);
            return Result.failure("Failed to create reservation: " + e.getMessage());
        }
    }

    @Transactional
    public Result<Boolean> cancelReservation(Long id) {
        log.info("Canceling reservation with ID: {}", id);
        try {
            Optional<Reservation> reservationOpt = reservationRepository.findById(id);
            if (reservationOpt.isEmpty()) {
                log.warn("Reservation not found with ID: {} for cancellation", id);
                return Result.success(false);
            }
            Reservation reservation = reservationOpt.get();
            reservation.setStatus(ReservationStatus.CANCELLED);
            reservationRepository.save(reservation);
            log.info("Reservation status set to CANCELLED for ID: {}", id);
            return Result.success(true);
        } catch (Exception e) {
            log.error("Failed to cancel reservation with ID: {}", id, e);
            return Result.failure("Failed to cancel reservation: " + e.getMessage());
        }
    }

    private Result<Boolean> hasConflictingReservation(Long roomId, OffsetDateTime start, OffsetDateTime end, Long excludeReservationId) {
        log.debug("Checking for conflicting reservations - roomId: {}, start: {}, end: {}, excludeId: {}", 
                roomId, start, end, excludeReservationId);
        try {
            Specification<Reservation> spec = (root, query, builder) -> {
                var roomPredicate = builder.equal(root.get("room").get("id"), roomId);
                
                var timePredicate = builder.and(
                    builder.lessThan(root.get("startDate"), end),
                    builder.greaterThan(root.get("endDate"), start)
                );
                
                if (excludeReservationId != null) {
                    return builder.and(roomPredicate, timePredicate, builder.notEqual(root.get("id"), excludeReservationId));
                }
                
                return builder.and(roomPredicate, timePredicate);
            };
            
            long conflictCount = reservationRepository.count(spec);
            log.debug("Found {} conflicting reservations", conflictCount);
            return Result.success(conflictCount > 0);
        } catch (Exception e) {
            log.error("Failed to check for conflicting reservations for room ID: {}", roomId, e);
            return Result.failure("Failed to check for conflicting reservations: " + e.getMessage());
        }
    }
    
    private String getSortField(String sort) {
        if (sort == null) {
            return "startDate";
        }
        
        return switch (sort.toLowerCase()) {
            case "date" -> "startDate";
            case "createdat" -> "createdAt";
            case "roomname" -> "room.name";
            default -> "startDate";
        };
    }
    
    private Specification<Reservation> addSpecification(Specification<Reservation> baseSpec, 
                                                      Specification<Reservation> additionalSpec) {
        if (baseSpec == null) {
            return additionalSpec;
        }
        
        return (root, query, builder) -> {
            var basePredicate = baseSpec.toPredicate(root, query, builder);
            var additionalPredicate = additionalSpec.toPredicate(root, query, builder);
            
            if (basePredicate == null) {
                return additionalPredicate;
            }
            
            if (additionalPredicate == null) {
                return basePredicate;
            }
            
            return builder.and(basePredicate, additionalPredicate);
        };
    }

    public Result<Boolean> cancelReservationsForRoom(Long roomId) {
        try {
            List<Reservation> reservations = reservationRepository.findAllByRoomId(roomId);
            for (Reservation reservation : reservations) {
                reservation.setCancelled(true);
            }
            reservationRepository.saveAll(reservations);
            log.info("Canceled {} reservations for room ID: {}", reservations.size(), roomId);
            return Result.success(true);
        } catch (Exception e) {
            log.error("Failed to cancel reservations for room ID: {}", roomId, e);
            return Result.failure("Failed to cancel reservations: " + e.getMessage());
        }
    }
}
