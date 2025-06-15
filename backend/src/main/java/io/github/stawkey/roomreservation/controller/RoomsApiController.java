package io.github.stawkey.roomreservation.controller;


import io.github.stawkey.roomreservation.api.RoomsApi;
import io.github.stawkey.roomreservation.dto.*;
import io.github.stawkey.roomreservation.entity.Reservation;
import io.github.stawkey.roomreservation.entity.Room;
import io.github.stawkey.roomreservation.entity.Tag;
import io.github.stawkey.roomreservation.mapper.ReservationMapper;
import io.github.stawkey.roomreservation.mapper.RoomMapper;
import io.github.stawkey.roomreservation.service.ReservationService;
import io.github.stawkey.roomreservation.service.RoomService;
import io.github.stawkey.roomreservation.service.TagService;
import io.github.stawkey.roomreservation.util.Result;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen",
        date = "2025-05-30T20:04:03.976488063+02:00[Europe/Warsaw]", comments = "Generator version: 7.12.0")
@Controller
@RequestMapping("${openapi.roomReservation.base-path:/api}")
public class RoomsApiController implements RoomsApi {
    private static final Logger log = LoggerFactory.getLogger(RoomsApiController.class);

    private final RoomService roomService;
    private final RoomMapper roomMapper;
    private final ReservationService reservationService;
    private final ReservationMapper reservationMapper;
    private final TagService tagService;

    public RoomsApiController(RoomService roomService, RoomMapper roomMapper, ReservationService reservationService,
                              ReservationMapper reservationMapper, TagService tagService) {
        this.roomService = roomService;
        this.roomMapper = roomMapper;
        this.reservationService = reservationService;
        this.reservationMapper = reservationMapper;
        this.tagService = tagService;
        log.info("RoomsApiController initialized");
    }

    @Override
    public ResponseEntity<RoomDto> createRoom(
            @Parameter(name = "CreateRoomRequest", required = true)
            @Valid
            @RequestBody
            CreateRoomRequest createRoomRequest) {
        log.info("API request: Create room with name: {}", createRoomRequest.getName());
        
        Room room = new Room();
        room.setName(createRoomRequest.getName());
        room.setCapacity(createRoomRequest.getCapacity());
        room.setDescription(createRoomRequest.getDescription());

        Set<Tag> tags = new HashSet<>();
        if (createRoomRequest.getTagIds() != null) {
            log.debug("Fetching tags for room creation: {}", createRoomRequest.getTagIds());
            tags.addAll(tagService.findAllById(createRoomRequest.getTagIds()));
            log.debug("Found {} tags out of {} requested", tags.size(), createRoomRequest.getTagIds().size());
        }
        room.setTags(tags);

        Result<Room> result = roomService.createRoom(room);
        
        if (result.isSuccess()) {
            RoomDto roomDto = roomMapper.toDto(result.getValue());
            log.info("Room created successfully with ID: {}", roomDto.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(roomDto);
        } else {
            log.error("Failed to create room: {}", result.getError());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Override
    public ResponseEntity<RoomDto> getRoom(
            @Parameter(name = "room_id", required = true, in = ParameterIn.PATH)
            @PathVariable("room_id")
            Long roomId) {
        log.info("API request: Get room with ID: {}", roomId);
        
        Result<Room> result = roomService.getRoomById(roomId);
        
        if (result.isSuccess()) {
            RoomDto roomDto = roomMapper.toDto(result.getValue());
            log.info("Room found with ID: {}", roomId);
            return ResponseEntity.ok(roomDto);
        } else {
            log.warn("Room not found with ID: {}", roomId);
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<ListRooms200Response> listRooms(
            @Min(1)
            @Parameter(name = "page", in = ParameterIn.QUERY)
            @Valid
            @RequestParam(value = "page", required = false, defaultValue = "1")
            Integer page,
            
            @Min(1)
            @Max(100)
            @Parameter(name = "page_size", in = ParameterIn.QUERY)
            @Valid
            @RequestParam(value = "page_size", required = false, defaultValue = "20")
            Integer pageSize) {
        
        log.info("API request: List rooms - page: {}, pageSize: {}", page, pageSize);
        
        Result<Integer> countResult = roomService.countRooms();
        if (countResult.isFailure()) {
            log.error("Failed to count rooms: {}", countResult.getError());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        
        int totalRooms = countResult.getValue();
        int totalPages = (int) Math.ceil((double) totalRooms / pageSize);
        log.debug("Total rooms: {}, total pages: {}", totalRooms, totalPages);

        Result<List<Room>> roomsResult = roomService.getPaginatedRooms(page, pageSize);
        if (roomsResult.isFailure()) {
            log.error("Failed to get paginated rooms: {}", roomsResult.getError());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        
        List<RoomDto> dtos = roomsResult.getValue().stream().map(roomMapper::toDto).toList();
        log.info("Retrieved {} rooms for page {}", dtos.size(), page);

        ListRooms200Response response = new ListRooms200Response();
        response.setTotal(totalRooms);
        response.setTotalPages(totalPages);
        response.setPage(page);
        response.setPageSize(pageSize);
        response.setItems(dtos);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> deleteRoom(
            @Parameter(name = "room_id", required = true, in = ParameterIn.PATH)
            @PathVariable("room_id")
            Long roomId) {
        log.info("API request: Delete room with ID: {}", roomId);
        
        Result<Boolean> result = roomService.deleteRoom(roomId);
        
        if (result.isSuccess() && result.getValue()) {
            log.info("Room deleted successfully with ID: {}", roomId);
            return ResponseEntity.noContent().build();
        } else if (result.isSuccess()) {
            log.warn("Room not found for deletion with ID: {}", roomId);
            return ResponseEntity.notFound().build();
        } else {
            log.error("Error deleting room with ID: {}: {}", roomId, result.getError());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<RoomDto> updateRoom(
            @PathVariable("room_id")
            Long roomId,

            @Valid
            @RequestBody
            UpdateRoomRequest updateRoomRequest) {
        log.info("API request: Update room with ID: {}", roomId);
        log.debug("Update data: name={}, capacity={}, tagIds={}", 
                updateRoomRequest.getName(), updateRoomRequest.getCapacity(), updateRoomRequest.getTagIds());
        
        Room updatedRoom = new Room();
        updatedRoom.setName(updateRoomRequest.getName());
        updatedRoom.setCapacity(updateRoomRequest.getCapacity());
        updatedRoom.setDescription(updateRoomRequest.getDescription());

        // Handle tags if provided
        Set<Tag> tags = new HashSet<>();
        if (updateRoomRequest.getTagIds() != null) {
            log.debug("Fetching tags for room update: {}", updateRoomRequest.getTagIds());
            tags.addAll(tagService.findAllById(updateRoomRequest.getTagIds()));
            log.debug("Found {} tags out of {} requested", tags.size(), updateRoomRequest.getTagIds().size());
        }
        updatedRoom.setTags(tags);

        Result<Room> result = roomService.updateRoom(roomId, updatedRoom);

        if (result.isSuccess()) {
            RoomDto responseDto = roomMapper.toDto(result.getValue());
            log.info("Room updated successfully with ID: {}", roomId);
            return ResponseEntity.ok(responseDto);
        } else {
            log.warn("Failed to update room with ID: {}: {}", roomId, result.getError());
            if (result.getError().contains("not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        }
    }

    @Override
    public ResponseEntity<Page> listRoomReservations(
            Long roomId,
            OffsetDateTime startDate,
            OffsetDateTime endDate,
            Integer page,
            Integer pageSize) {
        
        log.info("API request: List reservations for room ID: {}", roomId);
        log.debug("Parameters: startDate={}, endDate={}, page={}, pageSize={}", 
                startDate, endDate, page, pageSize);
        
        Result<org.springframework.data.domain.Page<Reservation>> result =
                reservationService.getReservationsForRoom(roomId, startDate, endDate, page, pageSize);
        
        if (result.isFailure()) {
            log.error("Failed to get reservations for room ID: {}: {}", roomId, result.getError());
            if (result.getError().contains("not found")) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
        
        org.springframework.data.domain.Page<Reservation> reservationsPage = result.getValue();
        log.info("Found {} reservations for room ID: {}", reservationsPage.getTotalElements(), roomId);
        
        List<ReservationDto> dtos =
                reservationsPage.getContent().stream().map(reservationMapper::toDto).collect(Collectors.toList());
        
        Page response = new Page();
        response.setItems(dtos);
        response.setPage(page);
        response.setPageSize(pageSize);
        response.setTotal((int) reservationsPage.getTotalElements());
        response.setTotalPages(reservationsPage.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ReservationDto> createRoomReservation(
            Long roomId,
            
            @Valid
            @RequestBody
            ReservationDto reservationDto) {
        log.info("API request: Create reservation for room ID: {}", roomId);
        log.debug("Reservation details: start={}, end={}, roomId={}",
                reservationDto.getStartDate(), reservationDto.getEndDate(), reservationDto.getRoomId());

        reservationDto.setRoomId(roomId);
        Reservation reservation = reservationMapper.fromDto(reservationDto);
        Result<Reservation> result = reservationService.createReservation(roomId, reservation);

        if (result.isSuccess()) {
            ReservationDto responseDto = reservationMapper.toDto(result.getValue());
            log.info("Reservation created successfully for room ID: {} with reservation ID: {}", 
                    roomId, responseDto.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } else {
            log.error("Failed to create reservation for room ID: {}: {}", roomId, result.getError());
            if (result.getError().contains("not found")) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        }
    }
}
