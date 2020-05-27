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
public class VwWorkReports implements java.io.Serializable {

	private static final long serialVersionUID = -1682558998;

	private java.lang.String        workReportId;
	private java.lang.String        domainId;
	private java.lang.Integer       number;
	private java.lang.Integer       year;
	private java.lang.String        referenceNo;
	private java.lang.String        operatorId;
	private java.lang.String        operatorDescription;
	private java.lang.String        customerId;
	private java.lang.String        customerDescription;
	private java.lang.String        customerStatId;
	private java.lang.String        customerStatDescription;
	private java.lang.Integer       causalId;
	private java.lang.String        causalDescription;
	private org.joda.time.LocalDate fromDate;
	private org.joda.time.LocalDate toDate;
	private java.lang.Integer       businessTripId;
	private java.lang.String        businessTripDescription;
	private java.lang.String        revisionStatus;
	private java.lang.Boolean       chargeTo;
	private java.lang.Boolean       freeSupport;
	private java.lang.Integer       companyId;
	private java.lang.String        companyDescription;
	private java.lang.Integer       docStatusId;
	private java.lang.String        docStatusDescription;
	private java.lang.String        description;
	private java.lang.String        notes;
	private java.lang.Long          totHours;

	public VwWorkReports() {}

	public VwWorkReports(
		java.lang.String        workReportId,
		java.lang.String        domainId,
		java.lang.Integer       number,
		java.lang.Integer       year,
		java.lang.String        referenceNo,
		java.lang.String        operatorId,
		java.lang.String        operatorDescription,
		java.lang.String        customerId,
		java.lang.String        customerDescription,
		java.lang.String        customerStatId,
		java.lang.String        customerStatDescription,
		java.lang.Integer       causalId,
		java.lang.String        causalDescription,
		org.joda.time.LocalDate fromDate,
		org.joda.time.LocalDate toDate,
		java.lang.Integer       businessTripId,
		java.lang.String        businessTripDescription,
		java.lang.String        revisionStatus,
		java.lang.Boolean       chargeTo,
		java.lang.Boolean       freeSupport,
		java.lang.Integer       companyId,
		java.lang.String        companyDescription,
		java.lang.Integer       docStatusId,
		java.lang.String        docStatusDescription,
		java.lang.String        description,
		java.lang.String        notes,
		java.lang.Long          totHours
	) {
		this.workReportId = workReportId;
		this.domainId = domainId;
		this.number = number;
		this.year = year;
		this.referenceNo = referenceNo;
		this.operatorId = operatorId;
		this.operatorDescription = operatorDescription;
		this.customerId = customerId;
		this.customerDescription = customerDescription;
		this.customerStatId = customerStatId;
		this.customerStatDescription = customerStatDescription;
		this.causalId = causalId;
		this.causalDescription = causalDescription;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.businessTripId = businessTripId;
		this.businessTripDescription = businessTripDescription;
		this.revisionStatus = revisionStatus;
		this.chargeTo = chargeTo;
		this.freeSupport = freeSupport;
		this.companyId = companyId;
		this.companyDescription = companyDescription;
		this.docStatusId = docStatusId;
		this.docStatusDescription = docStatusDescription;
		this.description = description;
		this.notes = notes;
		this.totHours = totHours;
	}

	public java.lang.String getWorkReportId() {
		return this.workReportId;
	}

	public void setWorkReportId(java.lang.String workReportId) {
		this.workReportId = workReportId;
	}

	public java.lang.String getDomainId() {
		return this.domainId;
	}

	public void setDomainId(java.lang.String domainId) {
		this.domainId = domainId;
	}

	public java.lang.Integer getNumber() {
		return this.number;
	}

	public void setNumber(java.lang.Integer number) {
		this.number = number;
	}

	public java.lang.Integer getYear() {
		return this.year;
	}

	public void setYear(java.lang.Integer year) {
		this.year = year;
	}

	public java.lang.String getReferenceNo() {
		return this.referenceNo;
	}

	public void setReferenceNo(java.lang.String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public java.lang.String getOperatorId() {
		return this.operatorId;
	}

	public void setOperatorId(java.lang.String operatorId) {
		this.operatorId = operatorId;
	}

	public java.lang.String getOperatorDescription() {
		return this.operatorDescription;
	}

	public void setOperatorDescription(java.lang.String operatorDescription) {
		this.operatorDescription = operatorDescription;
	}

	public java.lang.String getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(java.lang.String customerId) {
		this.customerId = customerId;
	}

	public java.lang.String getCustomerDescription() {
		return this.customerDescription;
	}

	public void setCustomerDescription(java.lang.String customerDescription) {
		this.customerDescription = customerDescription;
	}

	public java.lang.String getCustomerStatId() {
		return this.customerStatId;
	}

	public void setCustomerStatId(java.lang.String customerStatId) {
		this.customerStatId = customerStatId;
	}

	public java.lang.String getCustomerStatDescription() {
		return this.customerStatDescription;
	}

	public void setCustomerStatDescription(java.lang.String customerStatDescription) {
		this.customerStatDescription = customerStatDescription;
	}

	public java.lang.Integer getCausalId() {
		return this.causalId;
	}

	public void setCausalId(java.lang.Integer causalId) {
		this.causalId = causalId;
	}

	public java.lang.String getCausalDescription() {
		return this.causalDescription;
	}

	public void setCausalDescription(java.lang.String causalDescription) {
		this.causalDescription = causalDescription;
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

	public java.lang.Integer getBusinessTripId() {
		return this.businessTripId;
	}

	public void setBusinessTripId(java.lang.Integer businessTripId) {
		this.businessTripId = businessTripId;
	}

	public java.lang.String getBusinessTripDescription() {
		return this.businessTripDescription;
	}

	public void setBusinessTripDescription(java.lang.String businessTripDescription) {
		this.businessTripDescription = businessTripDescription;
	}

	public java.lang.String getRevisionStatus() {
		return this.revisionStatus;
	}

	public void setRevisionStatus(java.lang.String revisionStatus) {
		this.revisionStatus = revisionStatus;
	}

	public java.lang.Boolean getChargeTo() {
		return this.chargeTo;
	}

	public void setChargeTo(java.lang.Boolean chargeTo) {
		this.chargeTo = chargeTo;
	}

	public java.lang.Boolean getFreeSupport() {
		return this.freeSupport;
	}

	public void setFreeSupport(java.lang.Boolean freeSupport) {
		this.freeSupport = freeSupport;
	}

	public java.lang.Integer getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(java.lang.Integer companyId) {
		this.companyId = companyId;
	}

	public java.lang.String getCompanyDescription() {
		return this.companyDescription;
	}

	public void setCompanyDescription(java.lang.String companyDescription) {
		this.companyDescription = companyDescription;
	}

	public java.lang.Integer getDocStatusId() {
		return this.docStatusId;
	}

	public void setDocStatusId(java.lang.Integer docStatusId) {
		this.docStatusId = docStatusId;
	}

	public java.lang.String getDocStatusDescription() {
		return this.docStatusDescription;
	}

	public void setDocStatusDescription(java.lang.String docStatusDescription) {
		this.docStatusDescription = docStatusDescription;
	}

	public java.lang.String getDescription() {
		return this.description;
	}

	public void setDescription(java.lang.String description) {
		this.description = description;
	}

	public java.lang.String getNotes() {
		return this.notes;
	}

	public void setNotes(java.lang.String notes) {
		this.notes = notes;
	}

	public java.lang.Long getTotHours() {
		return this.totHours;
	}

	public void setTotHours(java.lang.Long totHours) {
		this.totHours = totHours;
	}
}
