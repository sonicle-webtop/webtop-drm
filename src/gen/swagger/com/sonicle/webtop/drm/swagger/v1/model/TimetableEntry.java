package com.sonicle.webtop.drm.swagger.v1.model;

import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class TimetableEntry   {
  
  private @Valid String employeeId = null;
  private @Valid String entrance = null;
  private @Valid String exit = null;

  /**
   * Employee id
   **/
  public TimetableEntry employeeId(String employeeId) {
    this.employeeId = employeeId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Employee id")
  @JsonProperty("employeeId")
  @NotNull
  public String getEmployeeId() {
    return employeeId;
  }
  public void setEmployeeId(String employeeId) {
    this.employeeId = employeeId;
  }

  /**
   * Entrance date/time (ISO date/time YYYYMMDD&#39;T&#39;HHMMSS&#39;Z&#39;)
   **/
  public TimetableEntry entrance(String entrance) {
    this.entrance = entrance;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Entrance date/time (ISO date/time YYYYMMDD'T'HHMMSS'Z')")
  @JsonProperty("entrance")
  @NotNull
  public String getEntrance() {
    return entrance;
  }
  public void setEntrance(String entrance) {
    this.entrance = entrance;
  }

  /**
   * Exit date/time (ISO date/time YYYYMMDD&#39;T&#39;HHMMSS&#39;Z&#39;)
   **/
  public TimetableEntry exit(String exit) {
    this.exit = exit;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Exit date/time (ISO date/time YYYYMMDD'T'HHMMSS'Z')")
  @JsonProperty("exit")
  @NotNull
  public String getExit() {
    return exit;
  }
  public void setExit(String exit) {
    this.exit = exit;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TimetableEntry timetableEntry = (TimetableEntry) o;
    return Objects.equals(employeeId, timetableEntry.employeeId) &&
        Objects.equals(entrance, timetableEntry.entrance) &&
        Objects.equals(exit, timetableEntry.exit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(employeeId, entrance, exit);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TimetableEntry {\n");
    
    sb.append("    employeeId: ").append(toIndentedString(employeeId)).append("\n");
    sb.append("    entrance: ").append(toIndentedString(entrance)).append("\n");
    sb.append("    exit: ").append(toIndentedString(exit)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

