package com.sonicle.webtop.drm.swagger.v2.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.sonicle.webtop.drm.swagger.v2.model.ApiManager;
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



@JsonTypeName("ManagersResult")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2026-03-03T15:44:48.527+01:00[Europe/Rome]")
public class ApiManagersResult   {
  private @Valid List<ApiManager> items;

  /**
   **/
  public ApiManagersResult items(List<ApiManager> items) {
    this.items = items;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("items")
  public List<ApiManager> getItems() {
    return items;
  }

  @JsonProperty("items")
  public void setItems(List<ApiManager> items) {
    this.items = items;
  }

  public ApiManagersResult addItemsItem(ApiManager itemsItem) {
    if (this.items == null) {
      this.items = new ArrayList<>();
    }

    this.items.add(itemsItem);
    return this;
  }

  public ApiManagersResult removeItemsItem(ApiManager itemsItem) {
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
    ApiManagersResult managersResult = (ApiManagersResult) o;
    return Objects.equals(this.items, managersResult.items);
  }

  @Override
  public int hashCode() {
    return Objects.hash(items);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApiManagersResult {\n");
    
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

