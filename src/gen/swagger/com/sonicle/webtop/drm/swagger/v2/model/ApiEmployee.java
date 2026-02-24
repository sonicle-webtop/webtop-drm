package com.sonicle.webtop.drm.swagger.v2.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
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



@JsonTypeName("Employee")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2026-02-24T14:49:06.615+01:00[Europe/Rome]")
public class ApiEmployee   {
  private @Valid Integer id;
  private @Valid String domainId;
  private @Valid String userId;
  private @Valid String number;
  private @Valid String tolerance;
  private @Valid Boolean extraordinary;
  private @Valid Boolean onlyPresence;
  private @Valid Integer hourProfileId;
  private @Valid String headquartersCode;
  private @Valid Boolean noStamping;
  private @Valid Integer minimumNumberOfHoursPerTicket;
  private @Valid String stampingMode;
  private @Valid Boolean isManager;
  private @Valid List<String> leaveRequestTypes;

  /**
   **/
  public ApiEmployee id(Integer id) {
    this.id = id;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("id")
  public Integer getId() {
    return id;
  }

  @JsonProperty("id")
  public void setId(Integer id) {
    this.id = id;
  }

  /**
   **/
  public ApiEmployee domainId(String domainId) {
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
  public ApiEmployee userId(String userId) {
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
  public ApiEmployee number(String number) {
    this.number = number;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("number")
  public String getNumber() {
    return number;
  }

  @JsonProperty("number")
  public void setNumber(String number) {
    this.number = number;
  }

  /**
   **/
  public ApiEmployee tolerance(String tolerance) {
    this.tolerance = tolerance;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("tolerance")
  public String getTolerance() {
    return tolerance;
  }

  @JsonProperty("tolerance")
  public void setTolerance(String tolerance) {
    this.tolerance = tolerance;
  }

  /**
   **/
  public ApiEmployee extraordinary(Boolean extraordinary) {
    this.extraordinary = extraordinary;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("extraordinary")
  public Boolean getExtraordinary() {
    return extraordinary;
  }

  @JsonProperty("extraordinary")
  public void setExtraordinary(Boolean extraordinary) {
    this.extraordinary = extraordinary;
  }

  /**
   **/
  public ApiEmployee onlyPresence(Boolean onlyPresence) {
    this.onlyPresence = onlyPresence;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("onlyPresence")
  public Boolean getOnlyPresence() {
    return onlyPresence;
  }

  @JsonProperty("onlyPresence")
  public void setOnlyPresence(Boolean onlyPresence) {
    this.onlyPresence = onlyPresence;
  }

  /**
   **/
  public ApiEmployee hourProfileId(Integer hourProfileId) {
    this.hourProfileId = hourProfileId;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("hourProfileId")
  public Integer getHourProfileId() {
    return hourProfileId;
  }

  @JsonProperty("hourProfileId")
  public void setHourProfileId(Integer hourProfileId) {
    this.hourProfileId = hourProfileId;
  }

  /**
   **/
  public ApiEmployee headquartersCode(String headquartersCode) {
    this.headquartersCode = headquartersCode;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("headquartersCode")
  public String getHeadquartersCode() {
    return headquartersCode;
  }

  @JsonProperty("headquartersCode")
  public void setHeadquartersCode(String headquartersCode) {
    this.headquartersCode = headquartersCode;
  }

  /**
   **/
  public ApiEmployee noStamping(Boolean noStamping) {
    this.noStamping = noStamping;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("noStamping")
  public Boolean getNoStamping() {
    return noStamping;
  }

  @JsonProperty("noStamping")
  public void setNoStamping(Boolean noStamping) {
    this.noStamping = noStamping;
  }

  /**
   **/
  public ApiEmployee minimumNumberOfHoursPerTicket(Integer minimumNumberOfHoursPerTicket) {
    this.minimumNumberOfHoursPerTicket = minimumNumberOfHoursPerTicket;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("minimumNumberOfHoursPerTicket")
  public Integer getMinimumNumberOfHoursPerTicket() {
    return minimumNumberOfHoursPerTicket;
  }

  @JsonProperty("minimumNumberOfHoursPerTicket")
  public void setMinimumNumberOfHoursPerTicket(Integer minimumNumberOfHoursPerTicket) {
    this.minimumNumberOfHoursPerTicket = minimumNumberOfHoursPerTicket;
  }

  /**
   **/
  public ApiEmployee stampingMode(String stampingMode) {
    this.stampingMode = stampingMode;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("stampingMode")
  public String getStampingMode() {
    return stampingMode;
  }

  @JsonProperty("stampingMode")
  public void setStampingMode(String stampingMode) {
    this.stampingMode = stampingMode;
  }

  /**
   **/
  public ApiEmployee isManager(Boolean isManager) {
    this.isManager = isManager;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("isManager")
  public Boolean getIsManager() {
    return isManager;
  }

  @JsonProperty("isManager")
  public void setIsManager(Boolean isManager) {
    this.isManager = isManager;
  }

  /**
   **/
  public ApiEmployee leaveRequestTypes(List<String> leaveRequestTypes) {
    this.leaveRequestTypes = leaveRequestTypes;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("leaveRequestTypes")
  public List<String> getLeaveRequestTypes() {
    return leaveRequestTypes;
  }

  @JsonProperty("leaveRequestTypes")
  public void setLeaveRequestTypes(List<String> leaveRequestTypes) {
    this.leaveRequestTypes = leaveRequestTypes;
  }

  public ApiEmployee addLeaveRequestTypesItem(String leaveRequestTypesItem) {
    if (this.leaveRequestTypes == null) {
      this.leaveRequestTypes = new ArrayList<>();
    }

    this.leaveRequestTypes.add(leaveRequestTypesItem);
    return this;
  }

  public ApiEmployee removeLeaveRequestTypesItem(String leaveRequestTypesItem) {
    if (leaveRequestTypesItem != null && this.leaveRequestTypes != null) {
      this.leaveRequestTypes.remove(leaveRequestTypesItem);
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
    ApiEmployee employee = (ApiEmployee) o;
    return Objects.equals(this.id, employee.id) &&
        Objects.equals(this.domainId, employee.domainId) &&
        Objects.equals(this.userId, employee.userId) &&
        Objects.equals(this.number, employee.number) &&
        Objects.equals(this.tolerance, employee.tolerance) &&
        Objects.equals(this.extraordinary, employee.extraordinary) &&
        Objects.equals(this.onlyPresence, employee.onlyPresence) &&
        Objects.equals(this.hourProfileId, employee.hourProfileId) &&
        Objects.equals(this.headquartersCode, employee.headquartersCode) &&
        Objects.equals(this.noStamping, employee.noStamping) &&
        Objects.equals(this.minimumNumberOfHoursPerTicket, employee.minimumNumberOfHoursPerTicket) &&
        Objects.equals(this.stampingMode, employee.stampingMode) &&
        Objects.equals(this.isManager, employee.isManager) &&
        Objects.equals(this.leaveRequestTypes, employee.leaveRequestTypes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, domainId, userId, number, tolerance, extraordinary, onlyPresence, hourProfileId, headquartersCode, noStamping, minimumNumberOfHoursPerTicket, stampingMode, isManager, leaveRequestTypes);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApiEmployee {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    domainId: ").append(toIndentedString(domainId)).append("\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    number: ").append(toIndentedString(number)).append("\n");
    sb.append("    tolerance: ").append(toIndentedString(tolerance)).append("\n");
    sb.append("    extraordinary: ").append(toIndentedString(extraordinary)).append("\n");
    sb.append("    onlyPresence: ").append(toIndentedString(onlyPresence)).append("\n");
    sb.append("    hourProfileId: ").append(toIndentedString(hourProfileId)).append("\n");
    sb.append("    headquartersCode: ").append(toIndentedString(headquartersCode)).append("\n");
    sb.append("    noStamping: ").append(toIndentedString(noStamping)).append("\n");
    sb.append("    minimumNumberOfHoursPerTicket: ").append(toIndentedString(minimumNumberOfHoursPerTicket)).append("\n");
    sb.append("    stampingMode: ").append(toIndentedString(stampingMode)).append("\n");
    sb.append("    isManager: ").append(toIndentedString(isManager)).append("\n");
    sb.append("    leaveRequestTypes: ").append(toIndentedString(leaveRequestTypes)).append("\n");
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

