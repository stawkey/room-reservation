package io.github.stawkey.roomreservation.service;

import io.github.stawkey.roomreservation.entity.Tag;
import io.github.stawkey.roomreservation.repository.TagRepository;
import io.github.stawkey.roomreservation.util.Result;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {
    private static final Logger log = LoggerFactory.getLogger(TagService.class);

    private final TagRepository repository;

    public TagService(TagRepository repository) {
        this.repository = repository;
        log.info("TagService initialized with repository: {}", repository);
    }

    public Result<List<Tag>> getAllTags() {
        log.info("Getting all tags");
        try {
            List<Tag> tags = repository.findAll();
            log.info("Retrieved {} tags successfully", tags.size());
            return Result.success(tags);
        } catch (Exception e) {
            log.error("Failed to get all tags", e);
            return Result.failure("Failed to get all tags: " + e.getMessage());
        }
    }

    public List<Tag> findAllById(List<Long> ids) {
        log.debug("Finding tags by ids: {}", ids);
        try {
            List<Tag> tags = repository.findAllById(ids);
            log.debug("Found {} tags out of {} requested ids", tags.size(), ids.size());
            return tags;
        } catch (Exception e) {
            log.error("Error finding tags by ids: {}", ids, e);
            throw e; // Re-throw to allow transaction management to handle it
        }
    }

    public Result<Tag> getTagById(Long id) {
        log.info("Getting tag with ID: {}", id);
        try {
            Optional<Tag> tag = repository.findById(id);
            if (tag.isPresent()) {
                log.info("Tag found with ID: {}", id);
                return Result.success(tag.get());
            } else {
                log.warn("Tag not found with ID: {}", id);
                return Result.failure("Tag not found with ID: " + id);
            }
        } catch (Exception e) {
            log.error("Error getting tag with ID: {}", id, e);
            return Result.failure("Failed to get tag: " + e.getMessage());
        }
    }

    @Transactional
    public Result<Tag> createTag(Tag tag) {
        log.info("Creating new tag: {}", tag.getName());
        try {
            // Perform save operation without transaction annotation
            Tag savedTag = saveTag(tag);
            log.info("Tag created successfully with ID: {}", savedTag.getId());
            return Result.success(savedTag);
        } catch (Exception e) {
            log.error("Failed to create tag: {}", tag.getName(), e);
            return Result.failure("Failed to create tag: " + e.getMessage());
        }
    }

    @Transactional
    protected Tag saveTag(Tag tag) {
        log.debug("Saving tag to database: {}", tag.getName());
        return repository.save(tag);
    }

    @Transactional
    public Result<Tag> updateTag(Long id, Tag updatedTag) {
        log.info("Updating tag with ID: {}", id);
        try {
            Optional<Tag> existingTag = repository.findById(id);

            if (existingTag.isEmpty()) {
                log.warn("Tag not found for update with ID: {}", id);
                return Result.failure("Tag not found with ID: " + id);
            }

            Tag tag = existingTag.get();
            log.debug("Found existing tag: {}", tag.getName());

            if (updatedTag.getName() != null) {
                tag.setName(updatedTag.getName());
            }

            if (updatedTag.getDescription() != null) {
                tag.setDescription(updatedTag.getDescription());
            }

            Tag savedTag = saveTag(tag);
            log.info("Tag updated successfully with ID: {}", savedTag.getId());
            return Result.success(savedTag);
        } catch (Exception e) {
            log.error("Failed to update tag with ID: {}", id, e);
            return Result.failure("Failed to update tag: " + e.getMessage());
        }
    }

    @Transactional
    public Result<Boolean> deleteTag(Long id) {
        log.info("Deleting tag with ID: {}", id);
        try {
            if (!repository.existsById(id)) {
                log.warn("Tag not found for deletion with ID: {}", id);
                return Result.success(false);
            }
            
            deleteTagById(id);
            log.info("Tag successfully deleted with ID: {}", id);
            return Result.success(true);
        } catch (Exception e) {
            log.error("Failed to delete tag with ID: {}", id, e);
            return Result.failure("Failed to delete tag: " + e.getMessage());
        }
    }
    
    @Transactional
    protected void deleteTagById(Long id) {
        log.debug("Executing delete operation for tag ID: {}", id);
        repository.deleteById(id);
    }
}
