package io.github.stawkey.roomreservation.dto;

import com.fasterxml.jackson.annotation.JsonValue;


import jakarta.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Current status of a reservation.
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-05-30T20:04:03.976488063+02:00[Europe/Warsaw]", comments = "Generator version: 7.12.0")
public enum ReservationStatus {
  
  ACTIVE("active"),
  
  CANCELLED("cancelled"),
  
  COMPLETED("completed"),
  
  HIDDEN("hidden");

  private String value;

  ReservationStatus(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static ReservationStatus fromValue(String value) {
    for (ReservationStatus b : ReservationStatus.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}

