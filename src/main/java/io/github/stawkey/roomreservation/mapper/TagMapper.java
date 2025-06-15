package io.github.stawkey.roomreservation.mapper;

import io.github.stawkey.roomreservation.dto.CreateTagRequest;
import io.github.stawkey.roomreservation.dto.TagDto;
import io.github.stawkey.roomreservation.dto.UpdateTagRequest;
import io.github.stawkey.roomreservation.entity.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {

    public TagDto toDto(Tag entity) {
        if (entity == null) {
            return null;
        }

        TagDto dto = new TagDto();
        dto.setId(entity.getId().intValue());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        return dto;
    }

    public Tag fromDto(TagDto dto) {
        if (dto == null) {
            return null;
        }

        Tag entity = new Tag();
        if (dto.getId() != null) {
            entity.setId(dto.getId().longValue());
        }
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());

        return entity;
    }
    
    public Tag fromCreateRequest(CreateTagRequest request) {
        if (request == null) {
            return null;
        }
        
        Tag entity = new Tag();
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        
        return entity;
    }
    
    public void updateEntityFromRequest(Tag entity, UpdateTagRequest request) {
        if (entity == null || request == null) {
            return;
        }
        
        if (request.getName() != null) {
            entity.setName(request.getName());
        }
        
        if (request.getDescription() != null) {
            entity.setDescription(request.getDescription());
        }
    }
}
