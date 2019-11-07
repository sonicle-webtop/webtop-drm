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
public class TimetableReportTempRecord extends org.jooq.impl.UpdatableRecordImpl<com.sonicle.webtop.drm.jooq.tables.records.TimetableReportTempRecord> implements org.jooq.Record17<java.lang.Integer, java.lang.String, java.lang.String, java.lang.Integer, org.joda.time.DateTime, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String> {

	private static final long serialVersionUID = -1147200212;

	/**
	 * Setter for <code>drm.timetable_report_temp.id</code>.
	 */
	public void setId(java.lang.Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>drm.timetable_report_temp.id</code>.
	 */
	public java.lang.Integer getId() {
		return (java.lang.Integer) getValue(0);
	}

	/**
	 * Setter for <code>drm.timetable_report_temp.domain_id</code>.
	 */
	public void setDomainId(java.lang.String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>drm.timetable_report_temp.domain_id</code>.
	 */
	public java.lang.String getDomainId() {
		return (java.lang.String) getValue(1);
	}

	/**
	 * Setter for <code>drm.timetable_report_temp.user_id</code>.
	 */
	public void setUserId(java.lang.String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>drm.timetable_report_temp.user_id</code>.
	 */
	public java.lang.String getUserId() {
		return (java.lang.String) getValue(2);
	}

	/**
	 * Setter for <code>drm.timetable_report_temp.company_id</code>.
	 */
	public void setCompanyId(java.lang.Integer value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>drm.timetable_report_temp.company_id</code>.
	 */
	public java.lang.Integer getCompanyId() {
		return (java.lang.Integer) getValue(3);
	}

	/**
	 * Setter for <code>drm.timetable_report_temp.date</code>.
	 */
	public void setDate(org.joda.time.DateTime value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>drm.timetable_report_temp.date</code>.
	 */
	public org.joda.time.DateTime getDate() {
		return (org.joda.time.DateTime) getValue(4);
	}

	/**
	 * Setter for <code>drm.timetable_report_temp.working_hours</code>.
	 */
	public void setWorkingHours(java.lang.String value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>drm.timetable_report_temp.working_hours</code>.
	 */
	public java.lang.String getWorkingHours() {
		return (java.lang.String) getValue(5);
	}

	/**
	 * Setter for <code>drm.timetable_report_temp.overtime</code>.
	 */
	public void setOvertime(java.lang.String value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>drm.timetable_report_temp.overtime</code>.
	 */
	public java.lang.String getOvertime() {
		return (java.lang.String) getValue(6);
	}

	/**
	 * Setter for <code>drm.timetable_report_temp.paid_leave</code>.
	 */
	public void setPaidLeave(java.lang.String value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>drm.timetable_report_temp.paid_leave</code>.
	 */
	public java.lang.String getPaidLeave() {
		return (java.lang.String) getValue(7);
	}

	/**
	 * Setter for <code>drm.timetable_report_temp.unpaid_leave</code>.
	 */
	public void setUnpaidLeave(java.lang.String value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>drm.timetable_report_temp.unpaid_leave</code>.
	 */
	public java.lang.String getUnpaidLeave() {
		return (java.lang.String) getValue(8);
	}

	/**
	 * Setter for <code>drm.timetable_report_temp.holiday</code>.
	 */
	public void setHoliday(java.lang.String value) {
		setValue(9, value);
	}

	/**
	 * Getter for <code>drm.timetable_report_temp.holiday</code>.
	 */
	public java.lang.String getHoliday() {
		return (java.lang.String) getValue(9);
	}

	/**
	 * Setter for <code>drm.timetable_report_temp.medical_visit</code>.
	 */
	public void setMedicalVisit(java.lang.String value) {
		setValue(10, value);
	}

	/**
	 * Getter for <code>drm.timetable_report_temp.medical_visit</code>.
	 */
	public java.lang.String getMedicalVisit() {
		return (java.lang.String) getValue(10);
	}

	/**
	 * Setter for <code>drm.timetable_report_temp.contractual</code>.
	 */
	public void setContractual(java.lang.String value) {
		setValue(11, value);
	}

	/**
	 * Getter for <code>drm.timetable_report_temp.contractual</code>.
	 */
	public java.lang.String getContractual() {
		return (java.lang.String) getValue(11);
	}

	/**
	 * Setter for <code>drm.timetable_report_temp.causal</code>.
	 */
	public void setCausal(java.lang.String value) {
		setValue(12, value);
	}

	/**
	 * Getter for <code>drm.timetable_report_temp.causal</code>.
	 */
	public java.lang.String getCausal() {
		return (java.lang.String) getValue(12);
	}

	/**
	 * Setter for <code>drm.timetable_report_temp.hour</code>.
	 */
	public void setHour(java.lang.String value) {
		setValue(13, value);
	}

	/**
	 * Getter for <code>drm.timetable_report_temp.hour</code>.
	 */
	public java.lang.String getHour() {
		return (java.lang.String) getValue(13);
	}

	/**
	 * Setter for <code>drm.timetable_report_temp.detail</code>.
	 */
	public void setDetail(java.lang.String value) {
		setValue(14, value);
	}

	/**
	 * Getter for <code>drm.timetable_report_temp.detail</code>.
	 */
	public java.lang.String getDetail() {
		return (java.lang.String) getValue(14);
	}

	/**
	 * Setter for <code>drm.timetable_report_temp.note</code>.
	 */
	public void setNote(java.lang.String value) {
		setValue(15, value);
	}

	/**
	 * Getter for <code>drm.timetable_report_temp.note</code>.
	 */
	public java.lang.String getNote() {
		return (java.lang.String) getValue(15);
	}

	/**
	 * Setter for <code>drm.timetable_report_temp.target_user_id</code>.
	 */
	public void setTargetUserId(java.lang.String value) {
		setValue(16, value);
	}

	/**
	 * Getter for <code>drm.timetable_report_temp.target_user_id</code>.
	 */
	public java.lang.String getTargetUserId() {
		return (java.lang.String) getValue(16);
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
	// Record17 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row17<java.lang.Integer, java.lang.String, java.lang.String, java.lang.Integer, org.joda.time.DateTime, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String> fieldsRow() {
		return (org.jooq.Row17) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row17<java.lang.Integer, java.lang.String, java.lang.String, java.lang.Integer, org.joda.time.DateTime, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String> valuesRow() {
		return (org.jooq.Row17) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field1() {
		return com.sonicle.webtop.drm.jooq.tables.TimetableReportTemp.TIMETABLE_REPORT_TEMP.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field2() {
		return com.sonicle.webtop.drm.jooq.tables.TimetableReportTemp.TIMETABLE_REPORT_TEMP.DOMAIN_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field3() {
		return com.sonicle.webtop.drm.jooq.tables.TimetableReportTemp.TIMETABLE_REPORT_TEMP.USER_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field4() {
		return com.sonicle.webtop.drm.jooq.tables.TimetableReportTemp.TIMETABLE_REPORT_TEMP.COMPANY_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<org.joda.time.DateTime> field5() {
		return com.sonicle.webtop.drm.jooq.tables.TimetableReportTemp.TIMETABLE_REPORT_TEMP.DATE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field6() {
		return com.sonicle.webtop.drm.jooq.tables.TimetableReportTemp.TIMETABLE_REPORT_TEMP.WORKING_HOURS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field7() {
		return com.sonicle.webtop.drm.jooq.tables.TimetableReportTemp.TIMETABLE_REPORT_TEMP.OVERTIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field8() {
		return com.sonicle.webtop.drm.jooq.tables.TimetableReportTemp.TIMETABLE_REPORT_TEMP.PAID_LEAVE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field9() {
		return com.sonicle.webtop.drm.jooq.tables.TimetableReportTemp.TIMETABLE_REPORT_TEMP.UNPAID_LEAVE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field10() {
		return com.sonicle.webtop.drm.jooq.tables.TimetableReportTemp.TIMETABLE_REPORT_TEMP.HOLIDAY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field11() {
		return com.sonicle.webtop.drm.jooq.tables.TimetableReportTemp.TIMETABLE_REPORT_TEMP.MEDICAL_VISIT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field12() {
		return com.sonicle.webtop.drm.jooq.tables.TimetableReportTemp.TIMETABLE_REPORT_TEMP.CONTRACTUAL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field13() {
		return com.sonicle.webtop.drm.jooq.tables.TimetableReportTemp.TIMETABLE_REPORT_TEMP.CAUSAL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field14() {
		return com.sonicle.webtop.drm.jooq.tables.TimetableReportTemp.TIMETABLE_REPORT_TEMP.HOUR;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field15() {
		return com.sonicle.webtop.drm.jooq.tables.TimetableReportTemp.TIMETABLE_REPORT_TEMP.DETAIL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field16() {
		return com.sonicle.webtop.drm.jooq.tables.TimetableReportTemp.TIMETABLE_REPORT_TEMP.NOTE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field17() {
		return com.sonicle.webtop.drm.jooq.tables.TimetableReportTemp.TIMETABLE_REPORT_TEMP.TARGET_USER_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value1() {
		return getId();
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
	public java.lang.String value3() {
		return getUserId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value4() {
		return getCompanyId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.joda.time.DateTime value5() {
		return getDate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value6() {
		return getWorkingHours();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value7() {
		return getOvertime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value8() {
		return getPaidLeave();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value9() {
		return getUnpaidLeave();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value10() {
		return getHoliday();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value11() {
		return getMedicalVisit();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value12() {
		return getContractual();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value13() {
		return getCausal();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value14() {
		return getHour();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value15() {
		return getDetail();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value16() {
		return getNote();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value17() {
		return getTargetUserId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TimetableReportTempRecord value1(java.lang.Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TimetableReportTempRecord value2(java.lang.String value) {
		setDomainId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TimetableReportTempRecord value3(java.lang.String value) {
		setUserId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TimetableReportTempRecord value4(java.lang.Integer value) {
		setCompanyId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TimetableReportTempRecord value5(org.joda.time.DateTime value) {
		setDate(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TimetableReportTempRecord value6(java.lang.String value) {
		setWorkingHours(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TimetableReportTempRecord value7(java.lang.String value) {
		setOvertime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TimetableReportTempRecord value8(java.lang.String value) {
		setPaidLeave(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TimetableReportTempRecord value9(java.lang.String value) {
		setUnpaidLeave(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TimetableReportTempRecord value10(java.lang.String value) {
		setHoliday(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TimetableReportTempRecord value11(java.lang.String value) {
		setMedicalVisit(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TimetableReportTempRecord value12(java.lang.String value) {
		setContractual(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TimetableReportTempRecord value13(java.lang.String value) {
		setCausal(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TimetableReportTempRecord value14(java.lang.String value) {
		setHour(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TimetableReportTempRecord value15(java.lang.String value) {
		setDetail(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TimetableReportTempRecord value16(java.lang.String value) {
		setNote(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TimetableReportTempRecord value17(java.lang.String value) {
		setTargetUserId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TimetableReportTempRecord values(java.lang.Integer value1, java.lang.String value2, java.lang.String value3, java.lang.Integer value4, org.joda.time.DateTime value5, java.lang.String value6, java.lang.String value7, java.lang.String value8, java.lang.String value9, java.lang.String value10, java.lang.String value11, java.lang.String value12, java.lang.String value13, java.lang.String value14, java.lang.String value15, java.lang.String value16, java.lang.String value17) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached TimetableReportTempRecord
	 */
	public TimetableReportTempRecord() {
		super(com.sonicle.webtop.drm.jooq.tables.TimetableReportTemp.TIMETABLE_REPORT_TEMP);
	}

	/**
	 * Create a detached, initialised TimetableReportTempRecord
	 */
	public TimetableReportTempRecord(java.lang.Integer id, java.lang.String domainId, java.lang.String userId, java.lang.Integer companyId, org.joda.time.DateTime date, java.lang.String workingHours, java.lang.String overtime, java.lang.String paidLeave, java.lang.String unpaidLeave, java.lang.String holiday, java.lang.String medicalVisit, java.lang.String contractual, java.lang.String causal, java.lang.String hour, java.lang.String detail, java.lang.String note, java.lang.String targetUserId) {
		super(com.sonicle.webtop.drm.jooq.tables.TimetableReportTemp.TIMETABLE_REPORT_TEMP);

		setValue(0, id);
		setValue(1, domainId);
		setValue(2, userId);
		setValue(3, companyId);
		setValue(4, date);
		setValue(5, workingHours);
		setValue(6, overtime);
		setValue(7, paidLeave);
		setValue(8, unpaidLeave);
		setValue(9, holiday);
		setValue(10, medicalVisit);
		setValue(11, contractual);
		setValue(12, causal);
		setValue(13, hour);
		setValue(14, detail);
		setValue(15, note);
		setValue(16, targetUserId);
	}
}
