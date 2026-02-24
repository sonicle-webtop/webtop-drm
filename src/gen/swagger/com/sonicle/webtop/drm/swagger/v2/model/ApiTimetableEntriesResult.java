package com.sonicle.webtop.drm.swagger.v2.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.sonicle.webtop.drm.swagger.v2.model.ApiTimetableEntry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;

import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonTypeName;



@JsonTypeName("TimetableEntriesResult")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2026-02-24T14:49:06.615+01:00[Europe/Rome]")
public class ApiTimetableEntriesResult   {
  private @Valid List<ApiTimetableEntry> items;

  /**
   **/
  public ApiTimetableEntriesResult items(List<ApiTimetableEntry> items) {
    this.items = items;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("items")
  public List<ApiTimetableEntry> getItems() {
    return items;
  }

  @JsonProperty("items")
  public void setItems(List<ApiTimetableEntry> items) {
    this.items = items;
  }

  public ApiTimetableEntriesResult addItemsItem(ApiTimetableEntry itemsItem) {
    if (this.items == null) {
      this.items = new ArrayList<>();
    }

    this.items.add(itemsItem);
    return this;
  }

  public ApiTimetableEntriesResult removeItemsItem(ApiTimetableEntry itemsItem) {
    if (itemsItem != null && this.items != null) {
      this.items.remove(itemsItem);
    }

    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ApiTimetableEntriesResult timetableEntriesResult = (ApiTimetableEntriesResult) o;
    return Objects.equals(this.items, timetableEntriesResult.items);
  }

  @Override
  public int hashCode() {
    return Objects.hash(items);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApiTimetableEntriesResult {\n");
    
    sb.append("    items: ").append(toIndentedString(items)).append("\n");
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

