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



@JsonTypeName("TimetableEntry")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2026-06-17T16:01:28.552+02:00[Europe/Rome]")
public class ApiTimetableEntry   {
  private @Valid String type;
  private @Valid String date;
  private @Valid String entrance;
  private @Valid String exit;
  private @Valid String location;
  private @Valid Integer minutes;

  /**
   **/
  public ApiTimetableEntry type(String type) {
    this.type = type;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("type")
  public String getType() {
    return type;
  }

  @JsonProperty("type")
  public void setType(String type) {
    this.type = type;
  }

  /**
   **/
  public ApiTimetableEntry date(String date) {
    this.date = date;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("date")
  public String getDate() {
    return date;
  }

  @JsonProperty("date")
  public void setDate(String date) {
    this.date = date;
  }

  /**
   * Entrance date/time (ISO date/time YYYYMMDD&#39;T&#39;HHMMSS&#39;Z&#39;)
   **/
  public ApiTimetableEntry entrance(String entrance) {
    this.entrance = entrance;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Entrance date/time (ISO date/time YYYYMMDD'T'HHMMSS'Z')")
  @JsonProperty("entrance")
  @NotNull
  public String getEntrance() {
    return entrance;
  }

  @JsonProperty("entrance")
  public void setEntrance(String entrance) {
    this.entrance = entrance;
  }

  /**
   * Exit date/time (ISO date/time YYYYMMDD&#39;T&#39;HHMMSS&#39;Z&#39;)
   **/
  public ApiTimetableEntry exit(String exit) {
    this.exit = exit;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Exit date/time (ISO date/time YYYYMMDD'T'HHMMSS'Z')")
  @JsonProperty("exit")
  @NotNull
  public String getExit() {
    return exit;
  }

  @JsonProperty("exit")
  public void setExit(String exit) {
    this.exit = exit;
  }

  /**
   * Exit date/time (ISO date/time YYYYMMDD&#39;T&#39;HHMMSS&#39;Z&#39;)
   **/
  public ApiTimetableEntry location(String location) {
    this.location = location;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Exit date/time (ISO date/time YYYYMMDD'T'HHMMSS'Z')")
  @JsonProperty("location")
  @NotNull
  public String getLocation() {
    return location;
  }

  @JsonProperty("location")
  public void setLocation(String location) {
    this.location = location;
  }

  /**
   **/
  public ApiTimetableEntry minutes(Integer minutes) {
    this.minutes = minutes;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("minutes")
  public Integer getMinutes() {
    return minutes;
  }

  @JsonProperty("minutes")
  public void setMinutes(Integer minutes) {
    this.minutes = minutes;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ApiTimetableEntry timetableEntry = (ApiTimetableEntry) o;
    return Objects.equals(this.type, timetableEntry.type) &&
        Objects.equals(this.date, timetableEntry.date) &&
        Objects.equals(this.entrance, timetableEntry.entrance) &&
        Objects.equals(this.exit, timetableEntry.exit) &&
        Objects.equals(this.location, timetableEntry.location) &&
        Objects.equals(this.minutes, timetableEntry.minutes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, date, entrance, exit, location, minutes);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApiTimetableEntry {\n");
    
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    entrance: ").append(toIndentedString(entrance)).append("\n");
    sb.append("    exit: ").append(toIndentedString(exit)).append("\n");
    sb.append("    location: ").append(toIndentedString(location)).append("\n");
    sb.append("    minutes: ").append(toIndentedString(minutes)).append("\n");
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

