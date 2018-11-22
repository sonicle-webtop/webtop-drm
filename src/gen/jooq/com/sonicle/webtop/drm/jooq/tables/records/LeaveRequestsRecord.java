/**
 * This class is generated by jOOQ
 */
package com.sonicle.webtop.drm.jooq.tables.records;

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
public class LeaveRequestsRecord extends org.jooq.impl.UpdatableRecordImpl<com.sonicle.webtop.drm.jooq.tables.records.LeaveRequestsRecord> implements org.jooq.Record21<java.lang.Integer, java.lang.String, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, org.joda.time.LocalDate, org.joda.time.LocalDate, java.lang.String, java.lang.String, java.lang.String, java.lang.String, org.joda.time.DateTime, org.joda.time.DateTime, java.lang.Boolean, java.lang.Boolean, org.joda.time.DateTime, org.joda.time.DateTime, java.lang.String, java.lang.Boolean, java.lang.Integer> {

	private static final long serialVersionUID = -1576031070;

	/**
	 * Setter for <code>drm.leave_requests.leave_request_id</code>.
	 */
	public void setLeaveRequestId(java.lang.Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>drm.leave_requests.leave_request_id</code>.
	 */
	public java.lang.Integer getLeaveRequestId() {
		return (java.lang.Integer) getValue(0);
	}

	/**
	 * Setter for <code>drm.leave_requests.domain_id</code>.
	 */
	public void setDomainId(java.lang.String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>drm.leave_requests.domain_id</code>.
	 */
	public java.lang.String getDomainId() {
		return (java.lang.String) getValue(1);
	}

	/**
	 * Setter for <code>drm.leave_requests.company_id</code>.
	 */
	public void setCompanyId(java.lang.Integer value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>drm.leave_requests.company_id</code>.
	 */
	public java.lang.Integer getCompanyId() {
		return (java.lang.Integer) getValue(2);
	}

	/**
	 * Setter for <code>drm.leave_requests.user_id</code>.
	 */
	public void setUserId(java.lang.String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>drm.leave_requests.user_id</code>.
	 */
	public java.lang.String getUserId() {
		return (java.lang.String) getValue(3);
	}

	/**
	 * Setter for <code>drm.leave_requests.manager_id</code>.
	 */
	public void setManagerId(java.lang.String value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>drm.leave_requests.manager_id</code>.
	 */
	public java.lang.String getManagerId() {
		return (java.lang.String) getValue(4);
	}

	/**
	 * Setter for <code>drm.leave_requests.type</code>.
	 */
	public void setType(java.lang.String value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>drm.leave_requests.type</code>.
	 */
	public java.lang.String getType() {
		return (java.lang.String) getValue(5);
	}

	/**
	 * Setter for <code>drm.leave_requests.from_date</code>.
	 */
	public void setFromDate(org.joda.time.LocalDate value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>drm.leave_requests.from_date</code>.
	 */
	public org.joda.time.LocalDate getFromDate() {
		return (org.joda.time.LocalDate) getValue(6);
	}

	/**
	 * Setter for <code>drm.leave_requests.to_date</code>.
	 */
	public void setToDate(org.joda.time.LocalDate value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>drm.leave_requests.to_date</code>.
	 */
	public org.joda.time.LocalDate getToDate() {
		return (org.joda.time.LocalDate) getValue(7);
	}

	/**
	 * Setter for <code>drm.leave_requests.from_hour</code>.
	 */
	public void setFromHour(java.lang.String value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>drm.leave_requests.from_hour</code>.
	 */
	public java.lang.String getFromHour() {
		return (java.lang.String) getValue(8);
	}

	/**
	 * Setter for <code>drm.leave_requests.to_hour</code>.
	 */
	public void setToHour(java.lang.String value) {
		setValue(9, value);
	}

	/**
	 * Getter for <code>drm.leave_requests.to_hour</code>.
	 */
	public java.lang.String getToHour() {
		return (java.lang.String) getValue(9);
	}

	/**
	 * Setter for <code>drm.leave_requests.status</code>.
	 */
	public void setStatus(java.lang.String value) {
		setValue(10, value);
	}

	/**
	 * Getter for <code>drm.leave_requests.status</code>.
	 */
	public java.lang.String getStatus() {
		return (java.lang.String) getValue(10);
	}

	/**
	 * Setter for <code>drm.leave_requests.notes</code>.
	 */
	public void setNotes(java.lang.String value) {
		setValue(11, value);
	}

	/**
	 * Getter for <code>drm.leave_requests.notes</code>.
	 */
	public java.lang.String getNotes() {
		return (java.lang.String) getValue(11);
	}

	/**
	 * Setter for <code>drm.leave_requests.employee_req_timestamp</code>.
	 */
	public void setEmployeeReqTimestamp(org.joda.time.DateTime value) {
		setValue(12, value);
	}

	/**
	 * Getter for <code>drm.leave_requests.employee_req_timestamp</code>.
	 */
	public org.joda.time.DateTime getEmployeeReqTimestamp() {
		return (org.joda.time.DateTime) getValue(12);
	}

	/**
	 * Setter for <code>drm.leave_requests.manager_resp_timestamp</code>.
	 */
	public void setManagerRespTimestamp(org.joda.time.DateTime value) {
		setValue(13, value);
	}

	/**
	 * Getter for <code>drm.leave_requests.manager_resp_timestamp</code>.
	 */
	public org.joda.time.DateTime getManagerRespTimestamp() {
		return (org.joda.time.DateTime) getValue(13);
	}

	/**
	 * Setter for <code>drm.leave_requests.result</code>.
	 */
	public void setResult(java.lang.Boolean value) {
		setValue(14, value);
	}

	/**
	 * Getter for <code>drm.leave_requests.result</code>.
	 */
	public java.lang.Boolean getResult() {
		return (java.lang.Boolean) getValue(14);
	}

	/**
	 * Setter for <code>drm.leave_requests.employee_canc_req</code>.
	 */
	public void setEmployeeCancReq(java.lang.Boolean value) {
		setValue(15, value);
	}

	/**
	 * Getter for <code>drm.leave_requests.employee_canc_req</code>.
	 */
	public java.lang.Boolean getEmployeeCancReq() {
		return (java.lang.Boolean) getValue(15);
	}

	/**
	 * Setter for <code>drm.leave_requests.employee_canc_req_timestamp</code>.
	 */
	public void setEmployeeCancReqTimestamp(org.joda.time.DateTime value) {
		setValue(16, value);
	}

	/**
	 * Getter for <code>drm.leave_requests.employee_canc_req_timestamp</code>.
	 */
	public org.joda.time.DateTime getEmployeeCancReqTimestamp() {
		return (org.joda.time.DateTime) getValue(16);
	}

	/**
	 * Setter for <code>drm.leave_requests.manager_canc_resp_timetamp</code>.
	 */
	public void setManagerCancRespTimetamp(org.joda.time.DateTime value) {
		setValue(17, value);
	}

	/**
	 * Getter for <code>drm.leave_requests.manager_canc_resp_timetamp</code>.
	 */
	public org.joda.time.DateTime getManagerCancRespTimetamp() {
		return (org.joda.time.DateTime) getValue(17);
	}

	/**
	 * Setter for <code>drm.leave_requests.canc_reason</code>.
	 */
	public void setCancReason(java.lang.String value) {
		setValue(18, value);
	}

	/**
	 * Getter for <code>drm.leave_requests.canc_reason</code>.
	 */
	public java.lang.String getCancReason() {
		return (java.lang.String) getValue(18);
	}

	/**
	 * Setter for <code>drm.leave_requests.canc_result</code>.
	 */
	public void setCancResult(java.lang.Boolean value) {
		setValue(19, value);
	}

	/**
	 * Getter for <code>drm.leave_requests.canc_result</code>.
	 */
	public java.lang.Boolean getCancResult() {
		return (java.lang.Boolean) getValue(19);
	}

	/**
	 * Setter for <code>drm.leave_requests.event_id</code>.
	 */
	public void setEventId(java.lang.Integer value) {
		setValue(20, value);
	}

	/**
	 * Getter for <code>drm.leave_requests.event_id</code>.
	 */
	public java.lang.Integer getEventId() {
		return (java.lang.Integer) getValue(20);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Record1<java.lang.Integer> key() {
		return (org.jooq.Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record21 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row21<java.lang.Integer, java.lang.String, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, org.joda.time.LocalDate, org.joda.time.LocalDate, java.lang.String, java.lang.String, java.lang.String, java.lang.String, org.joda.time.DateTime, org.joda.time.DateTime, java.lang.Boolean, java.lang.Boolean, org.joda.time.DateTime, org.joda.time.DateTime, java.lang.String, java.lang.Boolean, java.lang.Integer> fieldsRow() {
		return (org.jooq.Row21) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row21<java.lang.Integer, java.lang.String, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, org.joda.time.LocalDate, org.joda.time.LocalDate, java.lang.String, java.lang.String, java.lang.String, java.lang.String, org.joda.time.DateTime, org.joda.time.DateTime, java.lang.Boolean, java.lang.Boolean, org.joda.time.DateTime, org.joda.time.DateTime, java.lang.String, java.lang.Boolean, java.lang.Integer> valuesRow() {
		return (org.jooq.Row21) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field1() {
		return com.sonicle.webtop.drm.jooq.tables.LeaveRequests.LEAVE_REQUESTS.LEAVE_REQUEST_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field2() {
		return com.sonicle.webtop.drm.jooq.tables.LeaveRequests.LEAVE_REQUESTS.DOMAIN_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field3() {
		return com.sonicle.webtop.drm.jooq.tables.LeaveRequests.LEAVE_REQUESTS.COMPANY_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field4() {
		return com.sonicle.webtop.drm.jooq.tables.LeaveRequests.LEAVE_REQUESTS.USER_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field5() {
		return com.sonicle.webtop.drm.jooq.tables.LeaveRequests.LEAVE_REQUESTS.MANAGER_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field6() {
		return com.sonicle.webtop.drm.jooq.tables.LeaveRequests.LEAVE_REQUESTS.TYPE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<org.joda.time.LocalDate> field7() {
		return com.sonicle.webtop.drm.jooq.tables.LeaveRequests.LEAVE_REQUESTS.FROM_DATE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<org.joda.time.LocalDate> field8() {
		return com.sonicle.webtop.drm.jooq.tables.LeaveRequests.LEAVE_REQUESTS.TO_DATE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field9() {
		return com.sonicle.webtop.drm.jooq.tables.LeaveRequests.LEAVE_REQUESTS.FROM_HOUR;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field10() {
		return com.sonicle.webtop.drm.jooq.tables.LeaveRequests.LEAVE_REQUESTS.TO_HOUR;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field11() {
		return com.sonicle.webtop.drm.jooq.tables.LeaveRequests.LEAVE_REQUESTS.STATUS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field12() {
		return com.sonicle.webtop.drm.jooq.tables.LeaveRequests.LEAVE_REQUESTS.NOTES;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<org.joda.time.DateTime> field13() {
		return com.sonicle.webtop.drm.jooq.tables.LeaveRequests.LEAVE_REQUESTS.EMPLOYEE_REQ_TIMESTAMP;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<org.joda.time.DateTime> field14() {
		return com.sonicle.webtop.drm.jooq.tables.LeaveRequests.LEAVE_REQUESTS.MANAGER_RESP_TIMESTAMP;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Boolean> field15() {
		return com.sonicle.webtop.drm.jooq.tables.LeaveRequests.LEAVE_REQUESTS.RESULT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Boolean> field16() {
		return com.sonicle.webtop.drm.jooq.tables.LeaveRequests.LEAVE_REQUESTS.EMPLOYEE_CANC_REQ;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<org.joda.time.DateTime> field17() {
		return com.sonicle.webtop.drm.jooq.tables.LeaveRequests.LEAVE_REQUESTS.EMPLOYEE_CANC_REQ_TIMESTAMP;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<org.joda.time.DateTime> field18() {
		return com.sonicle.webtop.drm.jooq.tables.LeaveRequests.LEAVE_REQUESTS.MANAGER_CANC_RESP_TIMETAMP;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field19() {
		return com.sonicle.webtop.drm.jooq.tables.LeaveRequests.LEAVE_REQUESTS.CANC_REASON;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Boolean> field20() {
		return com.sonicle.webtop.drm.jooq.tables.LeaveRequests.LEAVE_REQUESTS.CANC_RESULT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field21() {
		return com.sonicle.webtop.drm.jooq.tables.LeaveRequests.LEAVE_REQUESTS.EVENT_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value1() {
		return getLeaveRequestId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value2() {
		return getDomainId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value3() {
		return getCompanyId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value4() {
		return getUserId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value5() {
		return getManagerId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value6() {
		return getType();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.joda.time.LocalDate value7() {
		return getFromDate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.joda.time.LocalDate value8() {
		return getToDate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value9() {
		return getFromHour();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value10() {
		return getToHour();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value11() {
		return getStatus();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value12() {
		return getNotes();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.joda.time.DateTime value13() {
		return getEmployeeReqTimestamp();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.joda.time.DateTime value14() {
		return getManagerRespTimestamp();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Boolean value15() {
		return getResult();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Boolean value16() {
		return getEmployeeCancReq();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.joda.time.DateTime value17() {
		return getEmployeeCancReqTimestamp();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.joda.time.DateTime value18() {
		return getManagerCancRespTimetamp();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value19() {
		return getCancReason();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Boolean value20() {
		return getCancResult();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value21() {
		return getEventId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LeaveRequestsRecord value1(java.lang.Integer value) {
		setLeaveRequestId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LeaveRequestsRecord value2(java.lang.String value) {
		setDomainId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LeaveRequestsRecord value3(java.lang.Integer value) {
		setCompanyId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LeaveRequestsRecord value4(java.lang.String value) {
		setUserId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LeaveRequestsRecord value5(java.lang.String value) {
		setManagerId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LeaveRequestsRecord value6(java.lang.String value) {
		setType(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LeaveRequestsRecord value7(org.joda.time.LocalDate value) {
		setFromDate(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LeaveRequestsRecord value8(org.joda.time.LocalDate value) {
		setToDate(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LeaveRequestsRecord value9(java.lang.String value) {
		setFromHour(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LeaveRequestsRecord value10(java.lang.String value) {
		setToHour(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LeaveRequestsRecord value11(java.lang.String value) {
		setStatus(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LeaveRequestsRecord value12(java.lang.String value) {
		setNotes(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LeaveRequestsRecord value13(org.joda.time.DateTime value) {
		setEmployeeReqTimestamp(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LeaveRequestsRecord value14(org.joda.time.DateTime value) {
		setManagerRespTimestamp(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LeaveRequestsRecord value15(java.lang.Boolean value) {
		setResult(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LeaveRequestsRecord value16(java.lang.Boolean value) {
		setEmployeeCancReq(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LeaveRequestsRecord value17(org.joda.time.DateTime value) {
		setEmployeeCancReqTimestamp(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LeaveRequestsRecord value18(org.joda.time.DateTime value) {
		setManagerCancRespTimetamp(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LeaveRequestsRecord value19(java.lang.String value) {
		setCancReason(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LeaveRequestsRecord value20(java.lang.Boolean value) {
		setCancResult(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LeaveRequestsRecord value21(java.lang.Integer value) {
		setEventId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LeaveRequestsRecord values(java.lang.Integer value1, java.lang.String value2, java.lang.Integer value3, java.lang.String value4, java.lang.String value5, java.lang.String value6, org.joda.time.LocalDate value7, org.joda.time.LocalDate value8, java.lang.String value9, java.lang.String value10, java.lang.String value11, java.lang.String value12, org.joda.time.DateTime value13, org.joda.time.DateTime value14, java.lang.Boolean value15, java.lang.Boolean value16, org.joda.time.DateTime value17, org.joda.time.DateTime value18, java.lang.String value19, java.lang.Boolean value20, java.lang.Integer value21) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached LeaveRequestsRecord
	 */
	public LeaveRequestsRecord() {
		super(com.sonicle.webtop.drm.jooq.tables.LeaveRequests.LEAVE_REQUESTS);
	}

	/**
	 * Create a detached, initialised LeaveRequestsRecord
	 */
	public LeaveRequestsRecord(java.lang.Integer leaveRequestId, java.lang.String domainId, java.lang.Integer companyId, java.lang.String userId, java.lang.String managerId, java.lang.String type, org.joda.time.LocalDate fromDate, org.joda.time.LocalDate toDate, java.lang.String fromHour, java.lang.String toHour, java.lang.String status, java.lang.String notes, org.joda.time.DateTime employeeReqTimestamp, org.joda.time.DateTime managerRespTimestamp, java.lang.Boolean result, java.lang.Boolean employeeCancReq, org.joda.time.DateTime employeeCancReqTimestamp, org.joda.time.DateTime managerCancRespTimetamp, java.lang.String cancReason, java.lang.Boolean cancResult, java.lang.Integer eventId) {
		super(com.sonicle.webtop.drm.jooq.tables.LeaveRequests.LEAVE_REQUESTS);

		setValue(0, leaveRequestId);
		setValue(1, domainId);
		setValue(2, companyId);
		setValue(3, userId);
		setValue(4, managerId);
		setValue(5, type);
		setValue(6, fromDate);
		setValue(7, toDate);
		setValue(8, fromHour);
		setValue(9, toHour);
		setValue(10, status);
		setValue(11, notes);
		setValue(12, employeeReqTimestamp);
		setValue(13, managerRespTimestamp);
		setValue(14, result);
		setValue(15, employeeCancReq);
		setValue(16, employeeCancReqTimestamp);
		setValue(17, managerCancRespTimetamp);
		setValue(18, cancReason);
		setValue(19, cancResult);
		setValue(20, eventId);
	}
}
