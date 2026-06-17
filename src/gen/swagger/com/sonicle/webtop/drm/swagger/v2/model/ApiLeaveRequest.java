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



@JsonTypeName("LeaveRequest")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2026-06-17T16:01:28.552+02:00[Europe/Rome]")
public class ApiLeaveRequest   {
  private @Valid Integer leaveRequestId;
  private @Valid String domainId;
  private @Valid Integer companyId;
  private @Valid String userId;
  private @Valid String user;
  private @Valid String managerId;
  private @Valid String manager;
  private @Valid String type;
  private @Valid String fromDate;
  private @Valid String toDate;
  private @Valid String fromHour;
  private @Valid String toHour;
  private @Valid String status;
  private @Valid Boolean result;
  private @Valid Boolean employeeCancReq;
  private @Valid String notes;
  private @Valid String cancReason;
  private @Valid String eventId;

  /**
   **/
  public ApiLeaveRequest leaveRequestId(Integer leaveRequestId) {
    this.leaveRequestId = leaveRequestId;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("leaveRequestId")
  public Integer getLeaveRequestId() {
    return leaveRequestId;
  }

  @JsonProperty("leaveRequestId")
  public void setLeaveRequestId(Integer leaveRequestId) {
    this.leaveRequestId = leaveRequestId;
  }

  /**
   **/
  public ApiLeaveRequest domainId(String domainId) {
    this.domainId = domainId;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("domainId")
  public String getDomainId() {
    return domainId;
  }

  @JsonProperty("domainId")
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }

  /**
   **/
  public ApiLeaveRequest companyId(Integer companyId) {
    this.companyId = companyId;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("companyId")
  public Integer getCompanyId() {
    return companyId;
  }

  @JsonProperty("companyId")
  public void setCompanyId(Integer companyId) {
    this.companyId = companyId;
  }

  /**
   **/
  public ApiLeaveRequest userId(String userId) {
    this.userId = userId;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("userId")
  public String getUserId() {
    return userId;
  }

  @JsonProperty("userId")
  public void setUserId(String userId) {
    this.userId = userId;
  }

  /**
   **/
  public ApiLeaveRequest user(String user) {
    this.user = user;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("user")
  public String getUser() {
    return user;
  }

  @JsonProperty("user")
  public void setUser(String user) {
    this.user = user;
  }

  /**
   **/
  public ApiLeaveRequest managerId(String managerId) {
    this.managerId = managerId;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("managerId")
  public String getManagerId() {
    return managerId;
  }

  @JsonProperty("managerId")
  public void setManagerId(String managerId) {
    this.managerId = managerId;
  }

  /**
   **/
  public ApiLeaveRequest manager(String manager) {
    this.manager = manager;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("manager")
  public String getManager() {
    return manager;
  }

  @JsonProperty("manager")
  public void setManager(String manager) {
    this.manager = manager;
  }

  /**
   **/
  public ApiLeaveRequest type(String type) {
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
  public ApiLeaveRequest fromDate(String fromDate) {
    this.fromDate = fromDate;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("fromDate")
  public String getFromDate() {
    return fromDate;
  }

  @JsonProperty("fromDate")
  public void setFromDate(String fromDate) {
    this.fromDate = fromDate;
  }

  /**
   **/
  public ApiLeaveRequest toDate(String toDate) {
    this.toDate = toDate;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("toDate")
  public String getToDate() {
    return toDate;
  }

  @JsonProperty("toDate")
  public void setToDate(String toDate) {
    this.toDate = toDate;
  }

  /**
   **/
  public ApiLeaveRequest fromHour(String fromHour) {
    this.fromHour = fromHour;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("fromHour")
  public String getFromHour() {
    return fromHour;
  }

  @JsonProperty("fromHour")
  public void setFromHour(String fromHour) {
    this.fromHour = fromHour;
  }

  /**
   **/
  public ApiLeaveRequest toHour(String toHour) {
    this.toHour = toHour;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("toHour")
  public String getToHour() {
    return toHour;
  }

  @JsonProperty("toHour")
  public void setToHour(String toHour) {
    this.toHour = toHour;
  }

  /**
   **/
  public ApiLeaveRequest status(String status) {
    this.status = status;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("status")
  public String getStatus() {
    return status;
  }

  @JsonProperty("status")
  public void setStatus(String status) {
    this.status = status;
  }

  /**
   **/
  public ApiLeaveRequest result(Boolean result) {
    this.result = result;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("result")
  public Boolean getResult() {
    return result;
  }

  @JsonProperty("result")
  public void setResult(Boolean result) {
    this.result = result;
  }

  /**
   **/
  public ApiLeaveRequest employeeCancReq(Boolean employeeCancReq) {
    this.employeeCancReq = employeeCancReq;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("employeeCancReq")
  public Boolean getEmployeeCancReq() {
    return employeeCancReq;
  }

  @JsonProperty("employeeCancReq")
  public void setEmployeeCancReq(Boolean employeeCancReq) {
    this.employeeCancReq = employeeCancReq;
  }

  /**
   **/
  public ApiLeaveRequest notes(String notes) {
    this.notes = notes;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("notes")
  public String getNotes() {
    return notes;
  }

  @JsonProperty("notes")
  public void setNotes(String notes) {
    this.notes = notes;
  }

  /**
   **/
  public ApiLeaveRequest cancReason(String cancReason) {
    this.cancReason = cancReason;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("cancReason")
  public String getCancReason() {
    return cancReason;
  }

  @JsonProperty("cancReason")
  public void setCancReason(String cancReason) {
    this.cancReason = cancReason;
  }

  /**
   **/
  public ApiLeaveRequest eventId(String eventId) {
    this.eventId = eventId;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("eventId")
  public String getEventId() {
    return eventId;
  }

  @JsonProperty("eventId")
  public void setEventId(String eventId) {
    this.eventId = eventId;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ApiLeaveRequest leaveRequest = (ApiLeaveRequest) o;
    return Objects.equals(this.leaveRequestId, leaveRequest.leaveRequestId) &&
        Objects.equals(this.domainId, leaveRequest.domainId) &&
        Objects.equals(this.companyId, leaveRequest.companyId) &&
        Objects.equals(this.userId, leaveRequest.userId) &&
        Objects.equals(this.user, leaveRequest.user) &&
        Objects.equals(this.managerId, leaveRequest.managerId) &&
        Objects.equals(this.manager, leaveRequest.manager) &&
        Objects.equals(this.type, leaveRequest.type) &&
        Objects.equals(this.fromDate, leaveRequest.fromDate) &&
        Objects.equals(this.toDate, leaveRequest.toDate) &&
        Objects.equals(this.fromHour, leaveRequest.fromHour) &&
        Objects.equals(this.toHour, leaveRequest.toHour) &&
        Objects.equals(this.status, leaveRequest.status) &&
        Objects.equals(this.result, leaveRequest.result) &&
        Objects.equals(this.employeeCancReq, leaveRequest.employeeCancReq) &&
        Objects.equals(this.notes, leaveRequest.notes) &&
        Objects.equals(this.cancReason, leaveRequest.cancReason) &&
        Objects.equals(this.eventId, leaveRequest.eventId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(leaveRequestId, domainId, companyId, userId, user, managerId, manager, type, fromDate, toDate, fromHour, toHour, status, result, employeeCancReq, notes, cancReason, eventId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApiLeaveRequest {\n");
    
    sb.append("    leaveRequestId: ").append(toIndentedString(leaveRequestId)).append("\n");
    sb.append("    domainId: ").append(toIndentedString(domainId)).append("\n");
    sb.append("    companyId: ").append(toIndentedString(companyId)).append("\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    user: ").append(toIndentedString(user)).append("\n");
    sb.append("    managerId: ").append(toIndentedString(managerId)).append("\n");
    sb.append("    manager: ").append(toIndentedString(manager)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    fromDate: ").append(toIndentedString(fromDate)).append("\n");
    sb.append("    toDate: ").append(toIndentedString(toDate)).append("\n");
    sb.append("    fromHour: ").append(toIndentedString(fromHour)).append("\n");
    sb.append("    toHour: ").append(toIndentedString(toHour)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    result: ").append(toIndentedString(result)).append("\n");
    sb.append("    employeeCancReq: ").append(toIndentedString(employeeCancReq)).append("\n");
    sb.append("    notes: ").append(toIndentedString(notes)).append("\n");
    sb.append("    cancReason: ").append(toIndentedString(cancReason)).append("\n");
    sb.append("    eventId: ").append(toIndentedString(eventId)).append("\n");
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

