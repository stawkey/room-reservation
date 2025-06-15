package io.github.stawkey.roomreservation.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import org.springframework.lang.Nullable;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.media.Schema;


import jakarta.annotation.Generated;

/**
 * Paginated list wrapper containing reservation items and pagination metadata.
 */

@Schema(name = "Page", description = "Paginated list wrapper containing reservation items and pagination metadata.")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-05-30T20:04:03.976488063+02:00[Europe/Warsaw]", comments = "Generator version: 7.12.0")
public class Page {

  @Valid
  private List<@Valid ReservationDto> items = new ArrayList<>();

  private @Nullable Integer page;

  private @Nullable Integer pageSize;

  private @Nullable Integer total;

  private @Nullable Integer totalPages;

  public Page items(List<@Valid ReservationDto> items) {
    this.items = items;
    return this;
  }

  public Page addItemsItem(ReservationDto itemsItem) {
    if (this.items == null) {
      this.items = new ArrayList<>();
    }
    this.items.add(itemsItem);
    return this;
  }

  /**
   * List of items on the current page.
   * @return items
   */
  @Valid 
  @Schema(name = "items", description = "List of items on the current page.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("items")
  public List<@Valid ReservationDto> getItems() {
    return items;
  }

  public void setItems(List<@Valid ReservationDto> items) {
    this.items = items;
  }

  public Page page(Integer page) {
    this.page = page;
    return this;
  }

  /**
   * Current page number.
   * @return page
   */
  
  @Schema(name = "page", example = "1", description = "Current page number.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("page")
  public Integer getPage() {
    return page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public Page pageSize(Integer pageSize) {
    this.pageSize = pageSize;
    return this;
  }

  /**
   * Number of items per page.
   * @return pageSize
   */
  
  @Schema(name = "pageSize", example = "20", description = "Number of items per page.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("pageSize")
  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public Page total(Integer total) {
    this.total = total;
    return this;
  }

  /**
   * Total number of items available.
   * @return total
   */
  
  @Schema(name = "total", example = "120", description = "Total number of items available.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("total")
  public Integer getTotal() {
    return total;
  }

  public void setTotal(Integer total) {
    this.total = total;
  }

  public Page totalPages(Integer totalPages) {
    this.totalPages = totalPages;
    return this;
  }

  /**
   * Total number of pages.
   * @return totalPages
   */
  
  @Schema(name = "totalPages", example = "6", description = "Total number of pages.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
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
    Page page = (Page) o;
    return Objects.equals(this.items, page.items) &&
        Objects.equals(this.page, page.page) &&
        Objects.equals(this.pageSize, page.pageSize) &&
        Objects.equals(this.total, page.total) &&
        Objects.equals(this.totalPages, page.totalPages);
  }

  @Override
  public int hashCode() {
    return Objects.hash(items, page, pageSize, total, totalPages);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Page {\n");
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

