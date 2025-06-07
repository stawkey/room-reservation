package io.github.stawkey.roomreservation.mapper;

import io.github.stawkey.roomreservation.dto.RoomDto;
import io.github.stawkey.roomreservation.entity.Room;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class RoomMapper {

    public RoomDto toDto(Room room) {
        if (room == null) return null;

        RoomDto dto = new RoomDto();
        dto.setId(room.getId());
        dto.setName(room.getName());
        dto.setCapacity(room.getCapacity());
        dto.setDescription(room.getDescription());

        dto.setCreatedAt(OffsetDateTime.now());
        dto.setUpdatedAt(OffsetDateTime.now());

        return dto;
    }

    public Room fromDto(RoomDto dto) {
        if (dto == null) return null;

        Room room = new Room();
        room.setId(dto.getId());
        room.setName(dto.getName());
        room.setCapacity(dto.getCapacity());
        room.setDescription(dto.getDescription());

        return room;
    }
}
