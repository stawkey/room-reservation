package io.github.stawkey.roomreservation.controller;


import io.github.stawkey.roomreservation.api.RoomsApi;
import io.github.stawkey.roomreservation.dto.*;
import io.github.stawkey.roomreservation.entity.Room;
import io.github.stawkey.roomreservation.mapper.RoomMapper;
import io.github.stawkey.roomreservation.service.RoomService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import jakarta.annotation.Generated;
import org.springframework.web.bind.annotation.RequestParam;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-05-30T20:04:03.976488063+02:00[Europe/Warsaw]", comments = "Generator version: 7.12.0")
@Controller
@RequestMapping("${openapi.roomReservation.base-path:/api}")
public class RoomsApiController implements RoomsApi {

    private final RoomService roomService;
    private final RoomMapper roomMapper;


    public RoomsApiController(RoomService roomService, RoomMapper roomMapper) {
        this.roomService = roomService;
        this.roomMapper = roomMapper;
    }

    @Override
    public ResponseEntity<RoomDto> createRoom(
            @Parameter(name = "CreateRoomRequest", description = "", required = true)
            @Valid @RequestBody CreateRoomRequest createRoomRequest
    ) {
        Room room = new Room();
        room.setName(createRoomRequest.getName());
        room.setCapacity(createRoomRequest.getCapacity());
        room.setDescription(createRoomRequest.getDescription());

        Room savedRoom = roomService.createRoom(room);
        RoomDto roomDto = roomMapper.toDto(savedRoom);
        return ResponseEntity.ok(roomDto);
    }

    @Override
    public ResponseEntity<RoomDto> getRoom(
            @Parameter(name = "room_id", description = "ID of the room to retrieve or modify", required = true, in = ParameterIn.PATH)
            @PathVariable("room_id") Integer roomId
    ) {
        return roomService.getRoomById(roomId)
                .map(room -> ResponseEntity.ok(roomMapper.toDto(room)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<ListRooms200Response> listRooms(
            @Min(1) @Parameter(name = "page", description = "Page number for pagination (starting from 1)", in = ParameterIn.QUERY) @Valid @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @Min(1) @Max(100) @Parameter(name = "page_size", description = "Number of rooms per page (max 100)", in = ParameterIn.QUERY) @Valid @RequestParam(value = "page_size", required = false, defaultValue = "20") Integer pageSize
    ) {
        int totalRooms = roomService.countRooms();
        int totalPages = (int) Math.ceil((double) totalRooms / pageSize);

        List<Room> rooms = roomService.getPaginatedRooms(page, pageSize);
        List<RoomDto> dtos = rooms.stream()
                .map(roomMapper::toDto)
                .toList();

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
            @Parameter(name = "room_id", description = "ID of the room to retrieve or modify", required = true, in = ParameterIn.PATH) @PathVariable("room_id") Integer roomId
    ) {

        roomService.deleteRoom(roomId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<RoomDto> updateRoom(
            @Parameter(name = "room_id", description = "ID of the room to retrieve or modify", required = true, in = ParameterIn.PATH) @PathVariable("room_id") Integer roomId,
            @Parameter(name = "UpdateRoomRequest", description = "Room data to update", required = true) @Valid @RequestBody UpdateRoomRequest updateRoomRequest
    ) {
        Room updatedRoom = new Room();
        updatedRoom.setName(updateRoomRequest.getName());
        updatedRoom.setCapacity(updateRoomRequest.getCapacity());
        updatedRoom.setDescription(updateRoomRequest.getDescription());

        Optional<Room> updated = roomService.updateRoom(roomId, updatedRoom);

        if (updated.isPresent()) {
            RoomDto responseDto = roomMapper.toDto(updated.get());
            return ResponseEntity.ok(responseDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    }

