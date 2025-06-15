package io.github.stawkey.roomreservation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Room
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-06-14T13:51:09.501262900+02:00[Europe/Warsaw]", comments = "Generator version: 7.7.0")
public class RoomDto {

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime createdAt;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime updatedAt;

  private Long id;

  private String name;

  private Integer capacity;

  private String description;

  @Valid
  private List<@Valid TagDto> tagDtos = new ArrayList<>();

  public RoomDto() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public RoomDto(String name, Integer capacity) {
    this.name = name;
    this.capacity = capacity;
  }

  public RoomDto createdAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * Timestamp when the record was created (ISO 8601 format).
   * @return createdAt
   */
  @Valid 
  @Schema(name = "createdAt", example = "2025-05-24T12:34:56Z", description = "Timestamp when the record was created (ISO 8601 format).", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("createdAt")
  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public RoomDto updatedAt(OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  /**
   * Timestamp of the last modification of the record.
   * @return updatedAt
   */
  @Valid 
  @Schema(name = "updatedAt", example = "2025-05-25T08:00Z", description = "Timestamp of the last modification of the record.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("updatedAt")
  public OffsetDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public RoomDto id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Unique identifier of the room.
   * @return id
   */
  
  @Schema(name = "id", example = "321", description = "Unique identifier of the room.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public RoomDto name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Name of the room.
   * @return name
   */
  @NotNull 
  @Schema(name = "name", example = "Conference Room A", description = "Name of the room.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public RoomDto capacity(Integer capacity) {
    this.capacity = capacity;
    return this;
  }

  /**
   * Maximum capacity of the room.
   * @return capacity
   */
  @NotNull 
  @Schema(name = "capacity", example = "20", description = "Maximum capacity of the room.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("capacity")
  public Integer getCapacity() {
    return capacity;
  }

  public void setCapacity(Integer capacity) {
    this.capacity = capacity;
  }

  public RoomDto description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Additional information or features of the room.
   * @return description
   */
  
  @Schema(name = "description", example = "Room equipped with projector and whiteboard.", description = "Additional information or features of the room.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public RoomDto tags(List<@Valid TagDto> tagDtos) {
    this.tagDtos = tagDtos;
    return this;
  }

  public RoomDto addTagsItem(TagDto tagsItem) {
    if (this.tagDtos == null) {
      this.tagDtos = new ArrayList<>();
    }
    this.tagDtos.add(tagsItem);
    return this;
  }

  /**
   * Tags associated with the room.
   * @return tags
   */
  @Valid 
  @Schema(name = "tags", description = "Tags associated with the room.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("tags")
  public List<@Valid TagDto> getTags() {
    return tagDtos;
  }

  public void setTags(List<@Valid TagDto> tagDtos) {
    this.tagDtos = tagDtos;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RoomDto roomDTO = (RoomDto) o;
    return Objects.equals(this.createdAt, roomDTO.createdAt) &&
        Objects.equals(this.updatedAt, roomDTO.updatedAt) &&
        Objects.equals(this.id, roomDTO.id) &&
        Objects.equals(this.name, roomDTO.name) &&
        Objects.equals(this.capacity, roomDTO.capacity) &&
        Objects.equals(this.description, roomDTO.description) &&
        Objects.equals(this.tagDtos, roomDTO.tagDtos);
  }

  @Override
  public int hashCode() {
    return Objects.hash(createdAt, updatedAt, id, name, capacity, description, tagDtos);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Room {\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    capacity: ").append(toIndentedString(capacity)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    tags: ").append(toIndentedString(tagDtos)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

