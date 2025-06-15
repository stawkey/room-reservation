package io.github.stawkey.roomreservation.mapper;

import io.github.stawkey.roomreservation.dto.RoomDto;
import io.github.stawkey.roomreservation.dto.TagDto;
import io.github.stawkey.roomreservation.entity.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoomMapper {

    private final TagMapper tagMapper;

    @Autowired
    public RoomMapper(TagMapper tagMapper) {
        this.tagMapper = tagMapper;
    }

    public RoomDto toDto(Room room) {
        if (room == null) return null;

        RoomDto dto = new RoomDto();
        dto.setId(room.getId());
        dto.setName(room.getName());
        dto.setCapacity(room.getCapacity());
        dto.setDescription(room.getDescription());
        dto.setCreatedAt(room.getCreatedAt());
        dto.setUpdatedAt(room.getUpdatedAt());
        
        if (room.getTags() != null) {
            List<TagDto> tagDtos = room.getTags().stream()
                .map(tagMapper::toDto)
                .collect(Collectors.toList());
            dto.setTags(tagDtos);
        }

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
