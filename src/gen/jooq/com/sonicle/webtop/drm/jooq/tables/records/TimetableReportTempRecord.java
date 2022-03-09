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
public class TimetableReportTempRecord extends org.jooq.impl.UpdatableRecordImpl<com.sonicle.webtop.drm.jooq.tables.records.TimetableReportTempRecord> {

	private static final long serialVersionUID = -1318182483;

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

	/**
	 * Setter for <code>drm.timetable_report_temp.work_report_hours</code>.
	 */
	public void setWorkReportHours(java.lang.String value) {
		setValue(17, value);
	}

	/**
	 * Getter for <code>drm.timetable_report_temp.work_report_hours</code>.
	 */
	public java.lang.String getWorkReportHours() {
		return (java.lang.String) getValue(17);
	}

	/**
	 * Setter for <code>drm.timetable_report_temp.job_hours</code>.
	 */
	public void setJobHours(java.lang.String value) {
		setValue(18, value);
	}

	/**
	 * Getter for <code>drm.timetable_report_temp.job_hours</code>.
	 */
	public java.lang.String getJobHours() {
		return (java.lang.String) getValue(18);
	}

	/**
	 * Setter for <code>drm.timetable_report_temp.sickness</code>.
	 */
	public void setSickness(java.lang.String value) {
		setValue(19, value);
	}

	/**
	 * Getter for <code>drm.timetable_report_temp.sickness</code>.
	 */
	public java.lang.String getSickness() {
		return (java.lang.String) getValue(19);
	}

	/**
	 * Setter for <code>drm.timetable_report_temp.other</code>.
	 */
	public void setOther(java.lang.String value) {
		setValue(20, value);
	}

	/**
	 * Getter for <code>drm.timetable_report_temp.other</code>.
	 */
	public java.lang.String getOther() {
		return (java.lang.String) getValue(20);
	}

	/**
	 * Setter for <code>drm.timetable_report_temp.causal_id</code>.
	 */
	public void setCausalId(java.lang.String value) {
		setValue(21, value);
	}

	/**
	 * Getter for <code>drm.timetable_report_temp.causal_id</code>.
	 */
	public java.lang.String getCausalId() {
		return (java.lang.String) getValue(21);
	}

	/**
	 * Setter for <code>drm.timetable_report_temp.total_line_hour</code>.
	 */
	public void setTotalLineHour(java.lang.String value) {
		setValue(22, value);
	}

	/**
	 * Getter for <code>drm.timetable_report_temp.total_line_hour</code>.
	 */
	public java.lang.String getTotalLineHour() {
		return (java.lang.String) getValue(22);
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
	public TimetableReportTempRecord(java.lang.Integer id, java.lang.String domainId, java.lang.String userId, java.lang.Integer companyId, org.joda.time.DateTime date, java.lang.String workingHours, java.lang.String overtime, java.lang.String paidLeave, java.lang.String unpaidLeave, java.lang.String holiday, java.lang.String medicalVisit, java.lang.String contractual, java.lang.String causal, java.lang.String hour, java.lang.String detail, java.lang.String note, java.lang.String targetUserId, java.lang.String workReportHours, java.lang.String jobHours, java.lang.String sickness, java.lang.String other, java.lang.String causalId, java.lang.String totalLineHour) {
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
		setValue(17, workReportHours);
		setValue(18, jobHours);
		setValue(19, sickness);
		setValue(20, other);
		setValue(21, causalId);
		setValue(22, totalLineHour);
	}
}
