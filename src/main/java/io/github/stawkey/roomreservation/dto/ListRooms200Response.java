package io.github.stawkey.roomreservation.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.ArrayList;
import java.util.List;
import org.springframework.lang.Nullable;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.media.Schema;


import jakarta.annotation.Generated;

/**
 * ListRooms200Response
 */

@JsonTypeName("listRooms_200_response")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-05-30T20:04:03.976488063+02:00[Europe/Warsaw]", comments = "Generator version: 7.12.0")
public class ListRooms200Response {

  @Valid
  private List<@Valid Room> items = new ArrayList<>();

  private @Nullable Integer page;

  private @Nullable Integer pageSize;

  private @Nullable Integer total;

  private @Nullable Integer totalPages;

  public ListRooms200Response items(List<@Valid Room> items) {
    this.items = items;
    return this;
  }

  public ListRooms200Response addItemsItem(Room itemsItem) {
    if (this.items == null) {
      this.items = new ArrayList<>();
    }
    this.items.add(itemsItem);
    return this;
  }

  /**
   * Get items
   * @return items
   */
  @Valid 
  @Schema(name = "items", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("items")
  public List<@Valid Room> getItems() {
    return items;
  }

  public void setItems(List<@Valid Room> items) {
    this.items = items;
  }

  public ListRooms200Response page(Integer page) {
    this.page = page;
    return this;
  }

  /**
   * Get page
   * @return page
   */
  
  @Schema(name = "page", example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("page")
  public Integer getPage() {
    return page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public ListRooms200Response pageSize(Integer pageSize) {
    this.pageSize = pageSize;
    return this;
  }

  /**
   * Get pageSize
   * @return pageSize
   */
  
  @Schema(name = "pageSize", example = "20", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("pageSize")
  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public ListRooms200Response total(Integer total) {
    this.total = total;
    return this;
  }

  /**
   * Get total
   * @return total
   */
  
  @Schema(name = "total", example = "150", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("total")
  public Integer getTotal() {
    return total;
  }

  public void setTotal(Integer total) {
    this.total = total;
  }

  public ListRooms200Response totalPages(Integer totalPages) {
    this.totalPages = totalPages;
    return this;
  }

  /**
   * Get totalPages
   * @return totalPages
   */
  
  @Schema(name = "totalPages", example = "8", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("totalPages")
  public Integer getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(Integer totalPages) {
    this.totalPages = totalPages;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ListRooms200Response listRooms200Response = (ListRooms200Response) o;
    return Objects.equals(this.items, listRooms200Response.items) &&
        Objects.equals(this.page, listRooms200Response.page) &&
        Objects.equals(this.pageSize, listRooms200Response.pageSize) &&
        Objects.equals(this.total, listRooms200Response.total) &&
        Objects.equals(this.totalPages, listRooms200Response.totalPages);
  }

  @Override
  public int hashCode() {
    return Objects.hash(items, page, pageSize, total, totalPages);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ListRooms200Response {\n");
    sb.append("    items: ").append(toIndentedString(items)).append("\n");
    sb.append("    page: ").append(toIndentedString(page)).append("\n");
    sb.append("    pageSize: ").append(toIndentedString(pageSize)).append("\n");
    sb.append("    total: ").append(toIndentedString(total)).append("\n");
    sb.append("    totalPages: ").append(toIndentedString(totalPages)).append("\n");
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

