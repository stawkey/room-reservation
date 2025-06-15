package io.github.stawkey.roomreservation.controller;

import io.github.stawkey.roomreservation.api.TagsApi;
import io.github.stawkey.roomreservation.dto.CreateTagRequest;
import io.github.stawkey.roomreservation.dto.TagDto;
import io.github.stawkey.roomreservation.dto.UpdateTagRequest;
import io.github.stawkey.roomreservation.entity.Tag;
import io.github.stawkey.roomreservation.mapper.TagMapper;
import io.github.stawkey.roomreservation.service.TagService;
import io.github.stawkey.roomreservation.util.Result;
import jakarta.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-06-14T13:43:08.307227200+02:00[Europe/Warsaw]", comments = "Generator version: 7.7.0")
@Controller
@RequestMapping("${openapi.roomReservation.base-path:/api}")
public class TagsApiController implements TagsApi {
    private static final Logger log = LoggerFactory.getLogger(TagsApiController.class);

    private final TagService tagService;
    private final TagMapper tagMapper;

    @Autowired
    public TagsApiController(TagService tagService, TagMapper tagMapper) {
        this.tagService = tagService;
        this.tagMapper = tagMapper;
        log.info("TagsApiController initialized");
    }

    @Override
    public ResponseEntity<TagDto> createTag(CreateTagRequest createTagRequest) {
        log.info("API request: Create tag with name: {}", createTagRequest.getName());
        try {
            Tag tagEntity = tagMapper.fromCreateRequest(createTagRequest);
            log.debug("Mapped tag entity from request: {}", tagEntity.getName());
            
            Result<Tag> result = tagService.createTag(tagEntity);
            
            if (result.isSuccess()) {
                TagDto responseDto = tagMapper.toDto(result.getValue());
                log.info("Tag created successfully with ID: {}", responseDto.getId());
                return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
            } else {
                log.error("Failed to create tag: {}", result.getError());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            log.error("Unexpected error creating tag", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<Void> deleteTag(Integer tagId) {
        log.info("API request: Delete tag with ID: {}", tagId);
        try {
            Result<Boolean> result = tagService.deleteTag(tagId.longValue());
            
            if (result.isSuccess()) {
                if (result.getValue()) {
                    log.info("Tag deleted successfully with ID: {}", tagId);
                    return ResponseEntity.noContent().build();
                } else {
                    log.warn("Tag not found for deletion with ID: {}", tagId);
                    return ResponseEntity.notFound().build();
                }
            } else {
                log.error("Failed to delete tag: {}", result.getError());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } catch (Exception e) {
            log.error("Unexpected error deleting tag with ID: {}", tagId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<TagDto> getTag(Integer tagId) {
        log.info("API request: Get tag with ID: {}", tagId);
        try {
            Result<Tag> result = tagService.getTagById(tagId.longValue());
            
            if (result.isSuccess()) {
                log.info("Tag found with ID: {}", tagId);
                return ResponseEntity.ok(tagMapper.toDto(result.getValue()));
            } else {
                log.warn("Tag not found with ID: {}", tagId);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Unexpected error getting tag with ID: {}", tagId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<List<TagDto>> listTags() {
        log.info("API request: List all tags");
        try {
            Result<List<Tag>> result = tagService.getAllTags();
            
            if (result.isSuccess()) {
                List<TagDto> tags = result.getValue().stream()
                        .map(tagMapper::toDto)
                        .collect(Collectors.toList());
                log.info("Retrieved {} tags successfully", tags.size());
                return ResponseEntity.ok(tags);
            } else {
                log.error("Failed to list tags: {}", result.getError());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } catch (Exception e) {
            log.error("Unexpected error listing tags", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<TagDto> updateTag(Integer tagId, UpdateTagRequest updateTagRequest) {
        log.info("API request: Update tag with ID: {}", tagId);
        log.debug("Update details: name={}, description={}", 
                updateTagRequest.getName(), updateTagRequest.getDescription());
        
        try {
            Result<Tag> getResult = tagService.getTagById(tagId.longValue());
            
            if (getResult.isFailure()) {
                log.warn("Tag not found for update with ID: {}", tagId);
                return ResponseEntity.notFound().build();
            }
            
            Tag tag = getResult.getValue();
            log.debug("Found existing tag: {}", tag.getName());
            tagMapper.updateEntityFromRequest(tag, updateTagRequest);
            
            Result<Tag> updateResult = tagService.updateTag(tagId.longValue(), tag);
            
            if (updateResult.isSuccess()) {
                log.info("Tag updated successfully with ID: {}", tagId);
                return ResponseEntity.ok(tagMapper.toDto(updateResult.getValue()));
            } else {
                log.error("Failed to update tag: {}", updateResult.getError());
                if (updateResult.getError().contains("not found")) {
                    return ResponseEntity.notFound().build();
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                }
            }
        } catch (Exception e) {
            log.error("Unexpected error updating tag with ID: {}", tagId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
