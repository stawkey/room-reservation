package io.github.stawkey.roomreservation.mapper;

import io.github.stawkey.roomreservation.dto.ReservationDto;
import io.github.stawkey.roomreservation.entity.Reservation;
import io.github.stawkey.roomreservation.entity.Room;
import io.github.stawkey.roomreservation.repository.RoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ReservationMapper {
    private static final Logger log = LoggerFactory.getLogger(ReservationMapper.class);

    private final RoomRepository roomRepository;

    public ReservationMapper(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
        log.info("ReservationMapper initialized with repository: {}", roomRepository);
    }

    public ReservationDto toDto(Reservation entity) {
        if (entity == null) {
            log.debug("Attempted to map null Reservation entity to DTO");
            return null;
        }

        log.debug("Mapping Reservation entity with ID: {} to DTO", entity.getId());
        ReservationDto dto = new ReservationDto();
        dto.setId(entity.getId());
        dto.setRoomId(entity.getRoom().getId());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setDescription(entity.getDescription());
        dto.setStatus(entity.getStatus());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        log.trace("Completed mapping Reservation with ID: {} to DTO", entity.getId());

        return dto;
    }

    public Reservation fromDto(ReservationDto dto) {
        if (dto == null) {
            log.debug("Attempted to map null ReservationDto to entity");
            return null;
        }

        log.debug("Mapping ReservationDto to entity, DTO roomId: {}", dto.getRoomId());
        Reservation entity = new Reservation();
        if (dto.getId() != null) {
            entity.setId(dto.getId());
            log.trace("Set entity ID to: {}", dto.getId());
        }

        if (dto.getRoomId() != null) {
            Long roomId = dto.getRoomId().longValue();
            log.debug("Looking up Room with ID: {}", roomId);
            Optional<Room> room = roomRepository.findById(roomId);
            if (room.isPresent()) {
                entity.setRoom(room.get());
                log.debug("Set Room with name: {} for Reservation", room.get().getName());
            } else {
                log.warn("Room with ID: {} not found when mapping ReservationDto to entity", roomId);
            }
        }

        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setDescription(dto.getDescription());
        entity.setStatus(dto.getStatus());
        log.trace("Completed mapping ReservationDto to entity");

        return entity;
    }
}
