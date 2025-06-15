package io.github.stawkey.roomreservation.controller;

import io.github.stawkey.roomreservation.api.ReservationsApi;
import io.github.stawkey.roomreservation.dto.Page;
import io.github.stawkey.roomreservation.dto.ReservationDto;
import io.github.stawkey.roomreservation.entity.Reservation;
import io.github.stawkey.roomreservation.mapper.ReservationMapper;
import io.github.stawkey.roomreservation.service.ReservationService;
import io.github.stawkey.roomreservation.util.Result;
import jakarta.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-05-30T20:04:03.976488063+02:00[Europe/Warsaw]", comments = "Generator version: 7.12.0")
@Controller
@RequestMapping("${openapi.roomReservation.base-path:/api}")
public class ReservationsApiController implements ReservationsApi {
    private static final Logger log = LoggerFactory.getLogger(ReservationsApiController.class);

    private final ReservationService reservationService;
    private final ReservationMapper reservationMapper;

    public ReservationsApiController(ReservationService reservationService, ReservationMapper reservationMapper) {
        this.reservationService = reservationService;
        this.reservationMapper = reservationMapper;
        log.info("ReservationsApiController initialized");
    }

    @Override
    public ResponseEntity<Void> cancelReservation(Long id) {
        log.info("API request: Cancel reservation with ID: {}", id);
        Result<Boolean> result = reservationService.cancelReservation(id);
        
        if (result.isSuccess()) {
            if (result.getValue()) {
                log.info("Reservation with ID: {} successfully canceled", id);
                return ResponseEntity.noContent().build();
            } else {
                log.warn("Reservation with ID: {} not found for cancellation", id);
                return ResponseEntity.notFound().build();
            }
        } else {
            log.error("Failed to cancel reservation with ID: {}: {}", id, result.getError());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<ReservationDto> getReservation(Long id) {
        log.info("API request: Get reservation with ID: {}", id);
        Result<Reservation> result = reservationService.getReservationById(id);
        
        if (result.isSuccess()) {
            log.info("Retrieved reservation with ID: {}", id);
            return ResponseEntity.ok(reservationMapper.toDto(result.getValue()));
        } else {
            log.warn("Reservation with ID: {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Page> listReservation(
            OffsetDateTime startDate, 
            OffsetDateTime endDate, 
            List<Long> roomId,
            String sort, 
            String order, 
            Integer page, 
            Integer pageSize) {
        
        log.info("API request: List reservations with filters - startDate: {}, endDate: {}, roomIds: {}, page: {}, pageSize: {}", 
                startDate, endDate, roomId, page, pageSize);
        log.debug("Sort options - field: {}, order: {}", sort, order);
        
        Result<org.springframework.data.domain.Page<Reservation>> result = reservationService.listReservations(
                startDate, 
                endDate,
                roomId,
                sort,
                order,
                page,
                pageSize
        );
        
        if (result.isFailure()) {
            log.error("Failed to list reservations: {}", result.getError());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        
        org.springframework.data.domain.Page<Reservation> reservationsPage = result.getValue();
        
        List<ReservationDto> dtos = reservationsPage.getContent().stream()
                .map(reservationMapper::toDto)
                .collect(Collectors.toList());
        
        log.info("Retrieved {} reservations from page {} of {}", 
                dtos.size(), page, reservationsPage.getTotalPages());
        
        Page response = new Page();
        response.setItems(dtos);
        response.setPage(page);
        response.setPageSize(pageSize);
        response.setTotal((int) reservationsPage.getTotalElements());
        response.setTotalPages(reservationsPage.getTotalPages());
        
        return ResponseEntity.ok(response);
    }
}
