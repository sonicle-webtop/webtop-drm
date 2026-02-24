package com.sonicle.webtop.drm.swagger.v2.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;
import javax.validation.Valid;

import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonTypeName;



@JsonTypeName("Company")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2026-02-24T14:49:06.615+01:00[Europe/Rome]")
public class ApiCompany   {
  private @Valid String id;
  private @Valid String displayname;

  /**
   **/
  public ApiCompany id(String id) {
    this.id = id;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("id")
  public String getId() {
    return id;
  }

  @JsonProperty("id")
  public void setId(String id) {
    this.id = id;
  }

  /**
   **/
  public ApiCompany displayname(String displayname) {
    this.displayname = displayname;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("displayname")
  public String getDisplayname() {
    return displayname;
  }

  @JsonProperty("displayname")
  public void setDisplayname(String displayname) {
    this.displayname = displayname;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ApiCompany company = (ApiCompany) o;
    return Objects.equals(this.id, company.id) &&
        Objects.equals(this.displayname, company.displayname);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, displayname);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApiCompany {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    displayname: ").append(toIndentedString(displayname)).append("\n");
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

