package io.github.stawkey.roomreservation.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import jakarta.annotation.Generated;

/**
 * Reservation
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-05-30T20:04:03.976488063+02:00[Europe/Warsaw]", comments = "Generator version: 7.12.0")
public class ReservationDto {

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private @Nullable OffsetDateTime createdAt;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private @Nullable OffsetDateTime updatedAt;

  private @Nullable Long id;

  private @Nullable Long roomId;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime startDate;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime endDate;

  private ReservationStatus status;

  private @Nullable String description;

  public ReservationDto() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ReservationDto(Long roomId, OffsetDateTime startDate, OffsetDateTime endDate, ReservationStatus status) {
    this.roomId = roomId;
    this.startDate = startDate;
    this.endDate = endDate;
    this.status = status;
  }

  public ReservationDto createdAt(OffsetDateTime createdAt) {
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

  public ReservationDto updatedAt(OffsetDateTime updatedAt) {
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

  public ReservationDto id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Unique identifier of the reservation.
   * @return id
   */
  
  @Schema(name = "id", example = "1234", description = "Unique identifier of the reservation.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ReservationDto roomId(Long roomId) {
    this.roomId = roomId;
    return this;
  }

  /**
   * ID of the reserved room.
   * @return roomId
   */
  @Schema(name = "roomId", example = "321", description = "ID of the reserved room.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("roomId")
  public Long getRoomId() {
    return roomId;
  }

  public void setRoomId(Long roomId) {
    this.roomId = roomId;
  }

  public ReservationDto start(OffsetDateTime start) {
    this.startDate = start;
    return this;
  }

  /**
   * Reservation start date and time.
   * @return start
   */
  @NotNull @Valid 
  @Schema(name = "start", description = "Reservation start date and time.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("start")
  public OffsetDateTime getStartDate() {
    return startDate;
  }

  public void setStartDate(OffsetDateTime startDate) {
    this.startDate = startDate;
  }

  public ReservationDto end(OffsetDateTime end) {
    this.endDate = end;
    return this;
  }

  /**
   * Reservation end date and time.
   * @return end
   */
  @NotNull @Valid 
  @Schema(name = "end", description = "Reservation end date and time.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("end")
  public OffsetDateTime getEndDate() {
    return endDate;
  }

  public void setEndDate(OffsetDateTime endDate) {
    this.endDate = endDate;
  }

  public ReservationDto status(ReservationStatus status) {
    this.status = status;
    return this;
  }

  /**
   * Current status of the reservation.
   * @return status
   */
  @NotNull @Valid 
  @Schema(name = "status", description = "Current status of the reservation.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("status")
  public ReservationStatus getStatus() {
    return status;
  }

  public void setStatus(ReservationStatus status) {
    this.status = status;
  }

  public ReservationDto description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Optional description or purpose of the reservation.
   * @return description
   */
  
  @Schema(name = "description", description = "Optional description or purpose of the reservation.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
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
    ReservationDto reservationDTO = (ReservationDto) o;
    return Objects.equals(this.createdAt, reservationDTO.createdAt) &&
        Objects.equals(this.updatedAt, reservationDTO.updatedAt) &&
        Objects.equals(this.id, reservationDTO.id) &&
        Objects.equals(this.roomId, reservationDTO.roomId) &&
        Objects.equals(this.startDate, reservationDTO.startDate) &&
        Objects.equals(this.endDate, reservationDTO.endDate) &&
        Objects.equals(this.status, reservationDTO.status) &&
        Objects.equals(this.description, reservationDTO.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(createdAt, updatedAt, id, roomId, startDate, endDate, status, description);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Reservation {\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    roomId: ").append(toIndentedString(roomId)).append("\n");
    sb.append("    start: ").append(toIndentedString(startDate)).append("\n");
    sb.append("    end: ").append(toIndentedString(endDate)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
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

