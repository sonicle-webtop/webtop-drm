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
public class VwWorkReportsRecord extends org.jooq.impl.TableRecordImpl<com.sonicle.webtop.drm.jooq.tables.records.VwWorkReportsRecord> {

	private static final long serialVersionUID = 2228360;

	/**
	 * Setter for <code>drm.vw_work_reports.work_report_id</code>.
	 */
	public void setWorkReportId(java.lang.String value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>drm.vw_work_reports.work_report_id</code>.
	 */
	public java.lang.String getWorkReportId() {
		return (java.lang.String) getValue(0);
	}

	/**
	 * Setter for <code>drm.vw_work_reports.domain_id</code>.
	 */
	public void setDomainId(java.lang.String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>drm.vw_work_reports.domain_id</code>.
	 */
	public java.lang.String getDomainId() {
		return (java.lang.String) getValue(1);
	}

	/**
	 * Setter for <code>drm.vw_work_reports.number</code>.
	 */
	public void setNumber(java.lang.Integer value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>drm.vw_work_reports.number</code>.
	 */
	public java.lang.Integer getNumber() {
		return (java.lang.Integer) getValue(2);
	}

	/**
	 * Setter for <code>drm.vw_work_reports.year</code>.
	 */
	public void setYear(java.lang.Integer value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>drm.vw_work_reports.year</code>.
	 */
	public java.lang.Integer getYear() {
		return (java.lang.Integer) getValue(3);
	}

	/**
	 * Setter for <code>drm.vw_work_reports.reference_no</code>.
	 */
	public void setReferenceNo(java.lang.String value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>drm.vw_work_reports.reference_no</code>.
	 */
	public java.lang.String getReferenceNo() {
		return (java.lang.String) getValue(4);
	}

	/**
	 * Setter for <code>drm.vw_work_reports.operator_id</code>.
	 */
	public void setOperatorId(java.lang.String value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>drm.vw_work_reports.operator_id</code>.
	 */
	public java.lang.String getOperatorId() {
		return (java.lang.String) getValue(5);
	}

	/**
	 * Setter for <code>drm.vw_work_reports.operator_description</code>.
	 */
	public void setOperatorDescription(java.lang.String value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>drm.vw_work_reports.operator_description</code>.
	 */
	public java.lang.String getOperatorDescription() {
		return (java.lang.String) getValue(6);
	}

	/**
	 * Setter for <code>drm.vw_work_reports.customer_id</code>.
	 */
	public void setCustomerId(java.lang.String value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>drm.vw_work_reports.customer_id</code>.
	 */
	public java.lang.String getCustomerId() {
		return (java.lang.String) getValue(7);
	}

	/**
	 * Setter for <code>drm.vw_work_reports.customer_description</code>.
	 */
	public void setCustomerDescription(java.lang.String value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>drm.vw_work_reports.customer_description</code>.
	 */
	public java.lang.String getCustomerDescription() {
		return (java.lang.String) getValue(8);
	}

	/**
	 * Setter for <code>drm.vw_work_reports.customer_stat_id</code>.
	 */
	public void setCustomerStatId(java.lang.String value) {
		setValue(9, value);
	}

	/**
	 * Getter for <code>drm.vw_work_reports.customer_stat_id</code>.
	 */
	public java.lang.String getCustomerStatId() {
		return (java.lang.String) getValue(9);
	}

	/**
	 * Setter for <code>drm.vw_work_reports.customer_stat_description</code>.
	 */
	public void setCustomerStatDescription(java.lang.String value) {
		setValue(10, value);
	}

	/**
	 * Getter for <code>drm.vw_work_reports.customer_stat_description</code>.
	 */
	public java.lang.String getCustomerStatDescription() {
		return (java.lang.String) getValue(10);
	}

	/**
	 * Setter for <code>drm.vw_work_reports.causal_id</code>.
	 */
	public void setCausalId(java.lang.Integer value) {
		setValue(11, value);
	}

	/**
	 * Getter for <code>drm.vw_work_reports.causal_id</code>.
	 */
	public java.lang.Integer getCausalId() {
		return (java.lang.Integer) getValue(11);
	}

	/**
	 * Setter for <code>drm.vw_work_reports.causal_description</code>.
	 */
	public void setCausalDescription(java.lang.String value) {
		setValue(12, value);
	}

	/**
	 * Getter for <code>drm.vw_work_reports.causal_description</code>.
	 */
	public java.lang.String getCausalDescription() {
		return (java.lang.String) getValue(12);
	}

	/**
	 * Setter for <code>drm.vw_work_reports.from_date</code>.
	 */
	public void setFromDate(org.joda.time.LocalDate value) {
		setValue(13, value);
	}

	/**
	 * Getter for <code>drm.vw_work_reports.from_date</code>.
	 */
	public org.joda.time.LocalDate getFromDate() {
		return (org.joda.time.LocalDate) getValue(13);
	}

	/**
	 * Setter for <code>drm.vw_work_reports.to_date</code>.
	 */
	public void setToDate(org.joda.time.LocalDate value) {
		setValue(14, value);
	}

	/**
	 * Getter for <code>drm.vw_work_reports.to_date</code>.
	 */
	public org.joda.time.LocalDate getToDate() {
		return (org.joda.time.LocalDate) getValue(14);
	}

	/**
	 * Setter for <code>drm.vw_work_reports.business_trip_id</code>.
	 */
	public void setBusinessTripId(java.lang.Integer value) {
		setValue(15, value);
	}

	/**
	 * Getter for <code>drm.vw_work_reports.business_trip_id</code>.
	 */
	public java.lang.Integer getBusinessTripId() {
		return (java.lang.Integer) getValue(15);
	}

	/**
	 * Setter for <code>drm.vw_work_reports.business_trip_description</code>.
	 */
	public void setBusinessTripDescription(java.lang.String value) {
		setValue(16, value);
	}

	/**
	 * Getter for <code>drm.vw_work_reports.business_trip_description</code>.
	 */
	public java.lang.String getBusinessTripDescription() {
		return (java.lang.String) getValue(16);
	}

	/**
	 * Setter for <code>drm.vw_work_reports.revision_status</code>.
	 */
	public void setRevisionStatus(java.lang.String value) {
		setValue(17, value);
	}

	/**
	 * Getter for <code>drm.vw_work_reports.revision_status</code>.
	 */
	public java.lang.String getRevisionStatus() {
		return (java.lang.String) getValue(17);
	}

	/**
	 * Setter for <code>drm.vw_work_reports.charge_to</code>.
	 */
	public void setChargeTo(java.lang.Boolean value) {
		setValue(18, value);
	}

	/**
	 * Getter for <code>drm.vw_work_reports.charge_to</code>.
	 */
	public java.lang.Boolean getChargeTo() {
		return (java.lang.Boolean) getValue(18);
	}

	/**
	 * Setter for <code>drm.vw_work_reports.free_support</code>.
	 */
	public void setFreeSupport(java.lang.Boolean value) {
		setValue(19, value);
	}

	/**
	 * Getter for <code>drm.vw_work_reports.free_support</code>.
	 */
	public java.lang.Boolean getFreeSupport() {
		return (java.lang.Boolean) getValue(19);
	}

	/**
	 * Setter for <code>drm.vw_work_reports.company_id</code>.
	 */
	public void setCompanyId(java.lang.Integer value) {
		setValue(20, value);
	}

	/**
	 * Getter for <code>drm.vw_work_reports.company_id</code>.
	 */
	public java.lang.Integer getCompanyId() {
		return (java.lang.Integer) getValue(20);
	}

	/**
	 * Setter for <code>drm.vw_work_reports.company_description</code>.
	 */
	public void setCompanyDescription(java.lang.String value) {
		setValue(21, value);
	}

	/**
	 * Getter for <code>drm.vw_work_reports.company_description</code>.
	 */
	public java.lang.String getCompanyDescription() {
		return (java.lang.String) getValue(21);
	}

	/**
	 * Setter for <code>drm.vw_work_reports.doc_status_id</code>.
	 */
	public void setDocStatusId(java.lang.Integer value) {
		setValue(22, value);
	}

	/**
	 * Getter for <code>drm.vw_work_reports.doc_status_id</code>.
	 */
	public java.lang.Integer getDocStatusId() {
		return (java.lang.Integer) getValue(22);
	}

	/**
	 * Setter for <code>drm.vw_work_reports.doc_status_description</code>.
	 */
	public void setDocStatusDescription(java.lang.String value) {
		setValue(23, value);
	}

	/**
	 * Getter for <code>drm.vw_work_reports.doc_status_description</code>.
	 */
	public java.lang.String getDocStatusDescription() {
		return (java.lang.String) getValue(23);
	}

	/**
	 * Setter for <code>drm.vw_work_reports.description</code>.
	 */
	public void setDescription(java.lang.String value) {
		setValue(24, value);
	}

	/**
	 * Getter for <code>drm.vw_work_reports.description</code>.
	 */
	public java.lang.String getDescription() {
		return (java.lang.String) getValue(24);
	}

	/**
	 * Setter for <code>drm.vw_work_reports.notes</code>.
	 */
	public void setNotes(java.lang.String value) {
		setValue(25, value);
	}

	/**
	 * Getter for <code>drm.vw_work_reports.notes</code>.
	 */
	public java.lang.String getNotes() {
		return (java.lang.String) getValue(25);
	}

	/**
	 * Setter for <code>drm.vw_work_reports.tot_hours</code>.
	 */
	public void setTotHours(java.lang.Long value) {
		setValue(26, value);
	}

	/**
	 * Getter for <code>drm.vw_work_reports.tot_hours</code>.
	 */
	public java.lang.Long getTotHours() {
		return (java.lang.Long) getValue(26);
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached VwWorkReportsRecord
	 */
	public VwWorkReportsRecord() {
		super(com.sonicle.webtop.drm.jooq.tables.VwWorkReports.VW_WORK_REPORTS);
	}

	/**
	 * Create a detached, initialised VwWorkReportsRecord
	 */
	public VwWorkReportsRecord(java.lang.String workReportId, java.lang.String domainId, java.lang.Integer number, java.lang.Integer year, java.lang.String referenceNo, java.lang.String operatorId, java.lang.String operatorDescription, java.lang.String customerId, java.lang.String customerDescription, java.lang.String customerStatId, java.lang.String customerStatDescription, java.lang.Integer causalId, java.lang.String causalDescription, org.joda.time.LocalDate fromDate, org.joda.time.LocalDate toDate, java.lang.Integer businessTripId, java.lang.String businessTripDescription, java.lang.String revisionStatus, java.lang.Boolean chargeTo, java.lang.Boolean freeSupport, java.lang.Integer companyId, java.lang.String companyDescription, java.lang.Integer docStatusId, java.lang.String docStatusDescription, java.lang.String description, java.lang.String notes, java.lang.Long totHours) {
		super(com.sonicle.webtop.drm.jooq.tables.VwWorkReports.VW_WORK_REPORTS);

		setValue(0, workReportId);
		setValue(1, domainId);
		setValue(2, number);
		setValue(3, year);
		setValue(4, referenceNo);
		setValue(5, operatorId);
		setValue(6, operatorDescription);
		setValue(7, customerId);
		setValue(8, customerDescription);
		setValue(9, customerStatId);
		setValue(10, customerStatDescription);
		setValue(11, causalId);
		setValue(12, causalDescription);
		setValue(13, fromDate);
		setValue(14, toDate);
		setValue(15, businessTripId);
		setValue(16, businessTripDescription);
		setValue(17, revisionStatus);
		setValue(18, chargeTo);
		setValue(19, freeSupport);
		setValue(20, companyId);
		setValue(21, companyDescription);
		setValue(22, docStatusId);
		setValue(23, docStatusDescription);
		setValue(24, description);
		setValue(25, notes);
		setValue(26, totHours);
	}
}
