package io.github.stawkey.roomreservation.dto;

import java.util.Objects;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.lang.Nullable;
import io.swagger.v3.oas.annotations.media.Schema;


import jakarta.annotation.Generated;

/**
 * UpdateRoomRequest
 */

@JsonTypeName("updateRoom_request")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-05-30T20:04:03.976488063+02:00[Europe/Warsaw]", comments = "Generator version: 7.12.0")
public class UpdateRoomRequest {

  private @Nullable String name;

  private @Nullable Integer capacity;

  private @Nullable String description;

  private @Nullable List<Long> tagIds;

  public UpdateRoomRequest name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Updated name of the room
   * @return name
   */
  
  @Schema(name = "name", example = "Conference Room B", description = "Updated name of the room", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public UpdateRoomRequest capacity(Integer capacity) {
    this.capacity = capacity;
    return this;
  }

  /**
   * Updated capacity of the room
   * @return capacity
   */
  
  @Schema(name = "capacity", example = "30", description = "Updated capacity of the room", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("capacity")
  public Integer getCapacity() {
    return capacity;
  }

  public void setCapacity(Integer capacity) {
    this.capacity = capacity;
  }

  public UpdateRoomRequest description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Updated descriptive text about the room
   * @return description
   */
  
  @Schema(name = "description", example = "Conference room with a projector.", description = "Updated descriptive text about the room", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public UpdateRoomRequest tagIds(List<Long> tagIds) {
    this.tagIds = tagIds;
    return this;
  }

  /**
   * List of tag IDs to associate with the room
   * @return tagIds
   */
  @Schema(name = "tagIds", description = "List of tag IDs to associate with the room", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("tagIds")
  public List<Long> getTagIds() {
    return tagIds;
  }

  public void setTagIds(List<Long> tagIds) {
    this.tagIds = tagIds;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UpdateRoomRequest updateRoomRequest = (UpdateRoomRequest) o;
    return Objects.equals(this.name, updateRoomRequest.name) &&
        Objects.equals(this.capacity, updateRoomRequest.capacity) &&
        Objects.equals(this.description, updateRoomRequest.description) &&
        Objects.equals(this.tagIds, updateRoomRequest.tagIds);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, capacity, description, tagIds);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateRoomRequest {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    capacity: ").append(toIndentedString(capacity)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    tagIds: ").append(toIndentedString(tagIds)).append("\n");
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

