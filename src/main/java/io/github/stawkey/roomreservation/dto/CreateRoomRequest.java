package io.github.stawkey.roomreservation.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.lang.Nullable;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import jakarta.annotation.Generated;

/**
 * CreateRoomRequest
 */

@JsonTypeName("createRoom_request")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-05-30T20:04:03.976488063+02:00[Europe/Warsaw]", comments = "Generator version: 7.12.0")
public class CreateRoomRequest {

  private String name;

  private Integer capacity;

  private @Nullable String description;

  public CreateRoomRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CreateRoomRequest(String name, Integer capacity) {
    this.name = name;
    this.capacity = capacity;
  }

  public CreateRoomRequest name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Name or title identifying the room
   * @return name
   */
  @NotNull 
  @Schema(name = "name", example = "Conference Room B", description = "Name or title identifying the room", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public CreateRoomRequest capacity(Integer capacity) {
    this.capacity = capacity;
    return this;
  }

  /**
   * Maximum number of people the room can accommodate
   * @return capacity
   */
  @NotNull 
  @Schema(name = "capacity", example = "25", description = "Maximum number of people the room can accommodate", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("capacity")
  public Integer getCapacity() {
    return capacity;
  }

  public void setCapacity(Integer capacity) {
    this.capacity = capacity;
  }

  public CreateRoomRequest description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Additional details about the room’s facilities and features
   * @return description
   */
  
  @Schema(name = "description", example = "Room with video conferencing equipment.", description = "Additional details about the room’s facilities and features", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateRoomRequest createRoomRequest = (CreateRoomRequest) o;
    return Objects.equals(this.name, createRoomRequest.name) &&
        Objects.equals(this.capacity, createRoomRequest.capacity) &&
        Objects.equals(this.description, createRoomRequest.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, capacity, description);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateRoomRequest {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    capacity: ").append(toIndentedString(capacity)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
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

