package io.github.stawkey.roomreservation.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public class CreateRoomRequest {
  @NotNull
  private String name;

  @NotNull
  private Integer capacity;

  private String description;

  private List<Long> tagIds;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getCapacity() {
    return capacity;
  }

  public void setCapacity(Integer capacity) {
    this.capacity = capacity;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<Long> getTagIds() {
    return tagIds;
  }

  public void setTagIds(List<Long> tagIds) {
    this.tagIds = tagIds;
  }
}