package io.github.stawkey.roomreservation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;
import java.util.Objects;

/**
 * Tag
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-06-14T13:51:09.501262900+02:00[Europe/Warsaw]", comments = "Generator version: 7.7.0")
public class TagDto {

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime createdAt;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime updatedAt;

  private Integer id;

  private String name;

  private String description;

  public TagDto() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public TagDto(String name) {
    this.name = name;
  }

  public TagDto createdAt(OffsetDateTime createdAt) {
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

  public TagDto updatedAt(OffsetDateTime updatedAt) {
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

  public TagDto id(Integer id) {
    this.id = id;
    return this;
  }

  /**
   * Unique identifier of the tag.
   * @return id
   */
  
  @Schema(name = "id", example = "1", description = "Unique identifier of the tag.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public TagDto name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Name of the tag.
   * @return name
   */
  @NotNull 
  @Schema(name = "name", example = "projector", description = "Name of the tag.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public TagDto description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Optional description of what the tag represents.
   * @return description
   */
  
  @Schema(name = "description", example = "Room has a projector available", description = "Optional description of what the tag represents.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
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
    TagDto tagDTO = (TagDto) o;
    return Objects.equals(this.createdAt, tagDTO.createdAt) &&
        Objects.equals(this.updatedAt, tagDTO.updatedAt) &&
        Objects.equals(this.id, tagDTO.id) &&
        Objects.equals(this.name, tagDTO.name) &&
        Objects.equals(this.description, tagDTO.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(createdAt, updatedAt, id, name, description);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Tag {\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
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

