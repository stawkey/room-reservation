package io.github.stawkey.roomreservation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

/**
 * CreateTagRequest
 */

@JsonTypeName("createTag_request")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-06-14T13:51:09.501262900+02:00[Europe/Warsaw]", comments = "Generator version: 7.7.0")
public class CreateTagRequest {

  private String name;

  private String description;

  public CreateTagRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CreateTagRequest(String name) {
    this.name = name;
  }

  public CreateTagRequest name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Name of the tag
   * @return name
   */
  @NotNull 
  @Schema(name = "name", example = "whiteboard", description = "Name of the tag", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public CreateTagRequest description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Optional description of what the tag represents
   * @return description
   */
  
  @Schema(name = "description", example = "Room has a whiteboard", description = "Optional description of what the tag represents", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
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
    CreateTagRequest createTagRequest = (CreateTagRequest) o;
    return Objects.equals(this.name, createTagRequest.name) &&
        Objects.equals(this.description, createTagRequest.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, description);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateTagRequest {\n");
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

