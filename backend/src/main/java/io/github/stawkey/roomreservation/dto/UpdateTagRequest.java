package io.github.stawkey.roomreservation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;

import java.util.Objects;

/**
 * UpdateTagRequest
 */

@JsonTypeName("updateTag_request")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-06-14T13:51:09.501262900+02:00[Europe/Warsaw]", comments = "Generator version: 7.7.0")
public class UpdateTagRequest {

  private String name;

  private String description;

  public UpdateTagRequest name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Updated name of the tag
   * @return name
   */
  
  @Schema(name = "name", example = "smart-whiteboard", description = "Updated name of the tag", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public UpdateTagRequest description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Updated description of the tag
   * @return description
   */
  
  @Schema(name = "description", example = "Room has an interactive smart whiteboard", description = "Updated description of the tag", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
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
    UpdateTagRequest updateTagRequest = (UpdateTagRequest) o;
    return Objects.equals(this.name, updateTagRequest.name) &&
        Objects.equals(this.description, updateTagRequest.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, description);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateTagRequest {\n");
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

