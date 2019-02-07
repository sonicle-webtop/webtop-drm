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
public class WorkReportsRecord extends org.jooq.impl.UpdatableRecordImpl<com.sonicle.webtop.drm.jooq.tables.records.WorkReportsRecord> {

	private static final long serialVersionUID = 547509814;

	/**
	 * Setter for <code>drm.work_reports.work_report_id</code>.
	 */
	public void setWorkReportId(java.lang.String value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>drm.work_reports.work_report_id</code>.
	 */
	public java.lang.String getWorkReportId() {
		return (java.lang.String) getValue(0);
	}

	/**
	 * Setter for <code>drm.work_reports.company_id</code>.
	 */
	public void setCompanyId(java.lang.Integer value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>drm.work_reports.company_id</code>.
	 */
	public java.lang.Integer getCompanyId() {
		return (java.lang.Integer) getValue(1);
	}

	/**
	 * Setter for <code>drm.work_reports.operator_id</code>.
	 */
	public void setOperatorId(java.lang.String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>drm.work_reports.operator_id</code>.
	 */
	public java.lang.String getOperatorId() {
		return (java.lang.String) getValue(2);
	}

	/**
	 * Setter for <code>drm.work_reports.revision_status</code>.
	 */
	public void setRevisionStatus(java.lang.String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>drm.work_reports.revision_status</code>.
	 */
	public java.lang.String getRevisionStatus() {
		return (java.lang.String) getValue(3);
	}

	/**
	 * Setter for <code>drm.work_reports.revision_timestamp</code>.
	 */
	public void setRevisionTimestamp(org.joda.time.DateTime value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>drm.work_reports.revision_timestamp</code>.
	 */
	public org.joda.time.DateTime getRevisionTimestamp() {
		return (org.joda.time.DateTime) getValue(4);
	}

	/**
	 * Setter for <code>drm.work_reports.revision_sequence</code>.
	 */
	public void setRevisionSequence(java.lang.Integer value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>drm.work_reports.revision_sequence</code>.
	 */
	public java.lang.Integer getRevisionSequence() {
		return (java.lang.Integer) getValue(5);
	}

	/**
	 * Setter for <code>drm.work_reports.doc_status_id</code>.
	 */
	public void setDocStatusId(java.lang.Integer value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>drm.work_reports.doc_status_id</code>.
	 */
	public java.lang.Integer getDocStatusId() {
		return (java.lang.Integer) getValue(6);
	}

	/**
	 * Setter for <code>drm.work_reports.contact_id</code>.
	 */
	public void setContactId(java.lang.Integer value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>drm.work_reports.contact_id</code>.
	 */
	public java.lang.Integer getContactId() {
		return (java.lang.Integer) getValue(7);
	}

	/**
	 * Setter for <code>drm.work_reports.customer_id</code>.
	 */
	public void setCustomerId(java.lang.String value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>drm.work_reports.customer_id</code>.
	 */
	public java.lang.String getCustomerId() {
		return (java.lang.String) getValue(8);
	}

	/**
	 * Setter for <code>drm.work_reports.customer_stat_id</code>.
	 */
	public void setCustomerStatId(java.lang.String value) {
		setValue(9, value);
	}

	/**
	 * Getter for <code>drm.work_reports.customer_stat_id</code>.
	 */
	public java.lang.String getCustomerStatId() {
		return (java.lang.String) getValue(9);
	}

	/**
	 * Setter for <code>drm.work_reports.from_date</code>.
	 */
	public void setFromDate(org.joda.time.LocalDate value) {
		setValue(10, value);
	}

	/**
	 * Getter for <code>drm.work_reports.from_date</code>.
	 */
	public org.joda.time.LocalDate getFromDate() {
		return (org.joda.time.LocalDate) getValue(10);
	}

	/**
	 * Setter for <code>drm.work_reports.to_date</code>.
	 */
	public void setToDate(org.joda.time.LocalDate value) {
		setValue(11, value);
	}

	/**
	 * Getter for <code>drm.work_reports.to_date</code>.
	 */
	public org.joda.time.LocalDate getToDate() {
		return (org.joda.time.LocalDate) getValue(11);
	}

	/**
	 * Setter for <code>drm.work_reports.reference_no</code>.
	 */
	public void setReferenceNo(java.lang.String value) {
		setValue(12, value);
	}

	/**
	 * Getter for <code>drm.work_reports.reference_no</code>.
	 */
	public java.lang.String getReferenceNo() {
		return (java.lang.String) getValue(12);
	}

	/**
	 * Setter for <code>drm.work_reports.causal_id</code>.
	 */
	public void setCausalId(java.lang.Integer value) {
		setValue(13, value);
	}

	/**
	 * Getter for <code>drm.work_reports.causal_id</code>.
	 */
	public java.lang.Integer getCausalId() {
		return (java.lang.Integer) getValue(13);
	}

	/**
	 * Setter for <code>drm.work_reports.ddt_no</code>.
	 */
	public void setDdtNo(java.lang.String value) {
		setValue(14, value);
	}

	/**
	 * Getter for <code>drm.work_reports.ddt_no</code>.
	 */
	public java.lang.String getDdtNo() {
		return (java.lang.String) getValue(14);
	}

	/**
	 * Setter for <code>drm.work_reports.ddt_date</code>.
	 */
	public void setDdtDate(org.joda.time.LocalDate value) {
		setValue(15, value);
	}

	/**
	 * Getter for <code>drm.work_reports.ddt_date</code>.
	 */
	public org.joda.time.LocalDate getDdtDate() {
		return (org.joda.time.LocalDate) getValue(15);
	}

	/**
	 * Setter for <code>drm.work_reports.notes</code>.
	 */
	public void setNotes(java.lang.String value) {
		setValue(16, value);
	}

	/**
	 * Getter for <code>drm.work_reports.notes</code>.
	 */
	public java.lang.String getNotes() {
		return (java.lang.String) getValue(16);
	}

	/**
	 * Setter for <code>drm.work_reports.description</code>.
	 */
	public void setDescription(java.lang.String value) {
		setValue(17, value);
	}

	/**
	 * Getter for <code>drm.work_reports.description</code>.
	 */
	public java.lang.String getDescription() {
		return (java.lang.String) getValue(17);
	}

	/**
	 * Setter for <code>drm.work_reports.apply_signature</code>.
	 */
	public void setApplySignature(java.lang.Boolean value) {
		setValue(18, value);
	}

	/**
	 * Getter for <code>drm.work_reports.apply_signature</code>.
	 */
	public java.lang.Boolean getApplySignature() {
		return (java.lang.Boolean) getValue(18);
	}

	/**
	 * Setter for <code>drm.work_reports.charge_to</code>.
	 */
	public void setChargeTo(java.lang.Boolean value) {
		setValue(19, value);
	}

	/**
	 * Getter for <code>drm.work_reports.charge_to</code>.
	 */
	public java.lang.Boolean getChargeTo() {
		return (java.lang.Boolean) getValue(19);
	}

	/**
	 * Setter for <code>drm.work_reports.free_support</code>.
	 */
	public void setFreeSupport(java.lang.Boolean value) {
		setValue(20, value);
	}

	/**
	 * Getter for <code>drm.work_reports.free_support</code>.
	 */
	public java.lang.Boolean getFreeSupport() {
		return (java.lang.Boolean) getValue(20);
	}

	/**
	 * Setter for <code>drm.work_reports.business_trip_id</code>.
	 */
	public void setBusinessTripId(java.lang.Integer value) {
		setValue(21, value);
	}

	/**
	 * Getter for <code>drm.work_reports.business_trip_id</code>.
	 */
	public java.lang.Integer getBusinessTripId() {
		return (java.lang.Integer) getValue(21);
	}

	/**
	 * Setter for <code>drm.work_reports.business_trip_days</code>.
	 */
	public void setBusinessTripDays(java.lang.Short value) {
		setValue(22, value);
	}

	/**
	 * Getter for <code>drm.work_reports.business_trip_days</code>.
	 */
	public java.lang.Short getBusinessTripDays() {
		return (java.lang.Short) getValue(22);
	}

	/**
	 * Setter for <code>drm.work_reports.number</code>.
	 */
	public void setNumber(java.lang.Integer value) {
		setValue(23, value);
	}

	/**
	 * Getter for <code>drm.work_reports.number</code>.
	 */
	public java.lang.Integer getNumber() {
		return (java.lang.Integer) getValue(23);
	}

	/**
	 * Setter for <code>drm.work_reports.year</code>.
	 */
	public void setYear(java.lang.Integer value) {
		setValue(24, value);
	}

	/**
	 * Getter for <code>drm.work_reports.year</code>.
	 */
	public java.lang.Integer getYear() {
		return (java.lang.Integer) getValue(24);
	}

	/**
	 * Setter for <code>drm.work_reports.event_id</code>.
	 */
	public void setEventId(java.lang.Integer value) {
		setValue(25, value);
	}

	/**
	 * Getter for <code>drm.work_reports.event_id</code>.
	 */
	public java.lang.Integer getEventId() {
		return (java.lang.Integer) getValue(25);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Record1<java.lang.String> key() {
		return (org.jooq.Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached WorkReportsRecord
	 */
	public WorkReportsRecord() {
		super(com.sonicle.webtop.drm.jooq.tables.WorkReports.WORK_REPORTS);
	}

	/**
	 * Create a detached, initialised WorkReportsRecord
	 */
	public WorkReportsRecord(java.lang.String workReportId, java.lang.Integer companyId, java.lang.String operatorId, java.lang.String revisionStatus, org.joda.time.DateTime revisionTimestamp, java.lang.Integer revisionSequence, java.lang.Integer docStatusId, java.lang.Integer contactId, java.lang.String customerId, java.lang.String customerStatId, org.joda.time.LocalDate fromDate, org.joda.time.LocalDate toDate, java.lang.String referenceNo, java.lang.Integer causalId, java.lang.String ddtNo, org.joda.time.LocalDate ddtDate, java.lang.String notes, java.lang.String description, java.lang.Boolean applySignature, java.lang.Boolean chargeTo, java.lang.Boolean freeSupport, java.lang.Integer businessTripId, java.lang.Short businessTripDays, java.lang.Integer number, java.lang.Integer year, java.lang.Integer eventId) {
		super(com.sonicle.webtop.drm.jooq.tables.WorkReports.WORK_REPORTS);

		setValue(0, workReportId);
		setValue(1, companyId);
		setValue(2, operatorId);
		setValue(3, revisionStatus);
		setValue(4, revisionTimestamp);
		setValue(5, revisionSequence);
		setValue(6, docStatusId);
		setValue(7, contactId);
		setValue(8, customerId);
		setValue(9, customerStatId);
		setValue(10, fromDate);
		setValue(11, toDate);
		setValue(12, referenceNo);
		setValue(13, causalId);
		setValue(14, ddtNo);
		setValue(15, ddtDate);
		setValue(16, notes);
		setValue(17, description);
		setValue(18, applySignature);
		setValue(19, chargeTo);
		setValue(20, freeSupport);
		setValue(21, businessTripId);
		setValue(22, businessTripDays);
		setValue(23, number);
		setValue(24, year);
		setValue(25, eventId);
	}
}
