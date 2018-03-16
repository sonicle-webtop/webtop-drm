/**
 * This class is generated by jOOQ
 */
package com.sonicle.webtop.drm.jooq.tables.pojos;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.5.3"
	},
	comments = "This class is generated by jOOQ"
)
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class LeaveRequests implements java.io.Serializable {

	private static final long serialVersionUID = 814350058;

	private java.lang.Integer       leaveRequestId;
	private java.lang.String        domainId;
	private java.lang.Integer       companyId;
	private java.lang.String        userId;
	private java.lang.String        managerId;
	private java.lang.String        type;
	private org.joda.time.LocalDate fromDate;
	private org.joda.time.LocalDate toDate;
	private java.lang.String        fromHour;
	private java.lang.String        toHour;
	private java.lang.String        status;
	private java.lang.String        notes;
	private org.joda.time.DateTime  employeeReqTimestamp;
	private org.joda.time.DateTime  managerRespTimestamp;
	private java.lang.Boolean       result;
	private java.lang.Boolean       employeeCancReq;
	private org.joda.time.DateTime  employeeCancReqTimestamp;
	private org.joda.time.DateTime  managerCancRespTimetamp;
	private java.lang.String        cancReason;
	private java.lang.Boolean       cancResult;

	public LeaveRequests() {}

	public LeaveRequests(
		java.lang.Integer       leaveRequestId,
		java.lang.String        domainId,
		java.lang.Integer       companyId,
		java.lang.String        userId,
		java.lang.String        managerId,
		java.lang.String        type,
		org.joda.time.LocalDate fromDate,
		org.joda.time.LocalDate toDate,
		java.lang.String        fromHour,
		java.lang.String        toHour,
		java.lang.String        status,
		java.lang.String        notes,
		org.joda.time.DateTime  employeeReqTimestamp,
		org.joda.time.DateTime  managerRespTimestamp,
		java.lang.Boolean       result,
		java.lang.Boolean       employeeCancReq,
		org.joda.time.DateTime  employeeCancReqTimestamp,
		org.joda.time.DateTime  managerCancRespTimetamp,
		java.lang.String        cancReason,
		java.lang.Boolean       cancResult
	) {
		this.leaveRequestId = leaveRequestId;
		this.domainId = domainId;
		this.companyId = companyId;
		this.userId = userId;
		this.managerId = managerId;
		this.type = type;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.fromHour = fromHour;
		this.toHour = toHour;
		this.status = status;
		this.notes = notes;
		this.employeeReqTimestamp = employeeReqTimestamp;
		this.managerRespTimestamp = managerRespTimestamp;
		this.result = result;
		this.employeeCancReq = employeeCancReq;
		this.employeeCancReqTimestamp = employeeCancReqTimestamp;
		this.managerCancRespTimetamp = managerCancRespTimetamp;
		this.cancReason = cancReason;
		this.cancResult = cancResult;
	}

	public java.lang.Integer getLeaveRequestId() {
		return this.leaveRequestId;
	}

	public void setLeaveRequestId(java.lang.Integer leaveRequestId) {
		this.leaveRequestId = leaveRequestId;
	}

	public java.lang.String getDomainId() {
		return this.domainId;
	}

	public void setDomainId(java.lang.String domainId) {
		this.domainId = domainId;
	}

	public java.lang.Integer getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(java.lang.Integer companyId) {
		this.companyId = companyId;
	}

	public java.lang.String getUserId() {
		return this.userId;
	}

	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}

	public java.lang.String getManagerId() {
		return this.managerId;
	}

	public void setManagerId(java.lang.String managerId) {
		this.managerId = managerId;
	}

	public java.lang.String getType() {
		return this.type;
	}

	public void setType(java.lang.String type) {
		this.type = type;
	}

	public org.joda.time.LocalDate getFromDate() {
		return this.fromDate;
	}

	public void setFromDate(org.joda.time.LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	public org.joda.time.LocalDate getToDate() {
		return this.toDate;
	}

	public void setToDate(org.joda.time.LocalDate toDate) {
		this.toDate = toDate;
	}

	public java.lang.String getFromHour() {
		return this.fromHour;
	}

	public void setFromHour(java.lang.String fromHour) {
		this.fromHour = fromHour;
	}

	public java.lang.String getToHour() {
		return this.toHour;
	}

	public void setToHour(java.lang.String toHour) {
		this.toHour = toHour;
	}

	public java.lang.String getStatus() {
		return this.status;
	}

	public void setStatus(java.lang.String status) {
		this.status = status;
	}

	public java.lang.String getNotes() {
		return this.notes;
	}

	public void setNotes(java.lang.String notes) {
		this.notes = notes;
	}

	public org.joda.time.DateTime getEmployeeReqTimestamp() {
		return this.employeeReqTimestamp;
	}

	public void setEmployeeReqTimestamp(org.joda.time.DateTime employeeReqTimestamp) {
		this.employeeReqTimestamp = employeeReqTimestamp;
	}

	public org.joda.time.DateTime getManagerRespTimestamp() {
		return this.managerRespTimestamp;
	}

	public void setManagerRespTimestamp(org.joda.time.DateTime managerRespTimestamp) {
		this.managerRespTimestamp = managerRespTimestamp;
	}

	public java.lang.Boolean getResult() {
		return this.result;
	}

	public void setResult(java.lang.Boolean result) {
		this.result = result;
	}

	public java.lang.Boolean getEmployeeCancReq() {
		return this.employeeCancReq;
	}

	public void setEmployeeCancReq(java.lang.Boolean employeeCancReq) {
		this.employeeCancReq = employeeCancReq;
	}

	public org.joda.time.DateTime getEmployeeCancReqTimestamp() {
		return this.employeeCancReqTimestamp;
	}

	public void setEmployeeCancReqTimestamp(org.joda.time.DateTime employeeCancReqTimestamp) {
		this.employeeCancReqTimestamp = employeeCancReqTimestamp;
	}

	public org.joda.time.DateTime getManagerCancRespTimetamp() {
		return this.managerCancRespTimetamp;
	}

	public void setManagerCancRespTimetamp(org.joda.time.DateTime managerCancRespTimetamp) {
		this.managerCancRespTimetamp = managerCancRespTimetamp;
	}

	public java.lang.String getCancReason() {
		return this.cancReason;
	}

	public void setCancReason(java.lang.String cancReason) {
		this.cancReason = cancReason;
	}

	public java.lang.Boolean getCancResult() {
		return this.cancResult;
	}

	public void setCancResult(java.lang.Boolean cancResult) {
		this.cancResult = cancResult;
	}
}
