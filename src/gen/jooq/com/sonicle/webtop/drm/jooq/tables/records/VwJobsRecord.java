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
public class VwJobsRecord extends org.jooq.impl.TableRecordImpl<com.sonicle.webtop.drm.jooq.tables.records.VwJobsRecord> implements org.jooq.Record21<java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.String, org.joda.time.DateTime, org.joda.time.DateTime, java.lang.String, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.String> {

	private static final long serialVersionUID = 1098188443;

	/**
	 * Setter for <code>drm.vw_jobs.job_id</code>.
	 */
	public void setJobId(java.lang.String value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>drm.vw_jobs.job_id</code>.
	 */
	public java.lang.String getJobId() {
		return (java.lang.String) getValue(0);
	}

	/**
	 * Setter for <code>drm.vw_jobs.operator_id</code>.
	 */
	public void setOperatorId(java.lang.String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>drm.vw_jobs.operator_id</code>.
	 */
	public java.lang.String getOperatorId() {
		return (java.lang.String) getValue(1);
	}

	/**
	 * Setter for <code>drm.vw_jobs.operator_description</code>.
	 */
	public void setOperatorDescription(java.lang.String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>drm.vw_jobs.operator_description</code>.
	 */
	public java.lang.String getOperatorDescription() {
		return (java.lang.String) getValue(2);
	}

	/**
	 * Setter for <code>drm.vw_jobs.customer_id</code>.
	 */
	public void setCustomerId(java.lang.String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>drm.vw_jobs.customer_id</code>.
	 */
	public java.lang.String getCustomerId() {
		return (java.lang.String) getValue(3);
	}

	/**
	 * Setter for <code>drm.vw_jobs.customer_description</code>.
	 */
	public void setCustomerDescription(java.lang.String value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>drm.vw_jobs.customer_description</code>.
	 */
	public java.lang.String getCustomerDescription() {
		return (java.lang.String) getValue(4);
	}

	/**
	 * Setter for <code>drm.vw_jobs.customer_stat_id</code>.
	 */
	public void setCustomerStatId(java.lang.String value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>drm.vw_jobs.customer_stat_id</code>.
	 */
	public java.lang.String getCustomerStatId() {
		return (java.lang.String) getValue(5);
	}

	/**
	 * Setter for <code>drm.vw_jobs.customer_stat_description</code>.
	 */
	public void setCustomerStatDescription(java.lang.String value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>drm.vw_jobs.customer_stat_description</code>.
	 */
	public java.lang.String getCustomerStatDescription() {
		return (java.lang.String) getValue(6);
	}

	/**
	 * Setter for <code>drm.vw_jobs.activity_id</code>.
	 */
	public void setActivityId(java.lang.Integer value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>drm.vw_jobs.activity_id</code>.
	 */
	public java.lang.Integer getActivityId() {
		return (java.lang.Integer) getValue(7);
	}

	/**
	 * Setter for <code>drm.vw_jobs.activity_description</code>.
	 */
	public void setActivityDescription(java.lang.String value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>drm.vw_jobs.activity_description</code>.
	 */
	public java.lang.String getActivityDescription() {
		return (java.lang.String) getValue(8);
	}

	/**
	 * Setter for <code>drm.vw_jobs.start_date</code>.
	 */
	public void setStartDate(org.joda.time.DateTime value) {
		setValue(9, value);
	}

	/**
	 * Getter for <code>drm.vw_jobs.start_date</code>.
	 */
	public org.joda.time.DateTime getStartDate() {
		return (org.joda.time.DateTime) getValue(9);
	}

	/**
	 * Setter for <code>drm.vw_jobs.end_date</code>.
	 */
	public void setEndDate(org.joda.time.DateTime value) {
		setValue(10, value);
	}

	/**
	 * Getter for <code>drm.vw_jobs.end_date</code>.
	 */
	public org.joda.time.DateTime getEndDate() {
		return (org.joda.time.DateTime) getValue(10);
	}

	/**
	 * Setter for <code>drm.vw_jobs.timezone</code>.
	 */
	public void setTimezone(java.lang.String value) {
		setValue(11, value);
	}

	/**
	 * Getter for <code>drm.vw_jobs.timezone</code>.
	 */
	public java.lang.String getTimezone() {
		return (java.lang.String) getValue(11);
	}

	/**
	 * Setter for <code>drm.vw_jobs.company_id</code>.
	 */
	public void setCompanyId(java.lang.Integer value) {
		setValue(12, value);
	}

	/**
	 * Getter for <code>drm.vw_jobs.company_id</code>.
	 */
	public java.lang.Integer getCompanyId() {
		return (java.lang.Integer) getValue(12);
	}

	/**
	 * Setter for <code>drm.vw_jobs.company_description</code>.
	 */
	public void setCompanyDescription(java.lang.String value) {
		setValue(13, value);
	}

	/**
	 * Getter for <code>drm.vw_jobs.company_description</code>.
	 */
	public java.lang.String getCompanyDescription() {
		return (java.lang.String) getValue(13);
	}

	/**
	 * Setter for <code>drm.vw_jobs.title</code>.
	 */
	public void setTitle(java.lang.String value) {
		setValue(14, value);
	}

	/**
	 * Getter for <code>drm.vw_jobs.title</code>.
	 */
	public java.lang.String getTitle() {
		return (java.lang.String) getValue(14);
	}

	/**
	 * Setter for <code>drm.vw_jobs.ticket_id</code>.
	 */
	public void setTicketId(java.lang.String value) {
		setValue(15, value);
	}

	/**
	 * Getter for <code>drm.vw_jobs.ticket_id</code>.
	 */
	public java.lang.String getTicketId() {
		return (java.lang.String) getValue(15);
	}

	/**
	 * Setter for <code>drm.vw_jobs.domain_id</code>.
	 */
	public void setDomainId(java.lang.String value) {
		setValue(16, value);
	}

	/**
	 * Getter for <code>drm.vw_jobs.domain_id</code>.
	 */
	public java.lang.String getDomainId() {
		return (java.lang.String) getValue(16);
	}

	/**
	 * Setter for <code>drm.vw_jobs.number</code>.
	 */
	public void setNumber(java.lang.String value) {
		setValue(17, value);
	}

	/**
	 * Getter for <code>drm.vw_jobs.number</code>.
	 */
	public java.lang.String getNumber() {
		return (java.lang.String) getValue(17);
	}

	/**
	 * Setter for <code>drm.vw_jobs.description</code>.
	 */
	public void setDescription(java.lang.String value) {
		setValue(18, value);
	}

	/**
	 * Getter for <code>drm.vw_jobs.description</code>.
	 */
	public java.lang.String getDescription() {
		return (java.lang.String) getValue(18);
	}

	/**
	 * Setter for <code>drm.vw_jobs.causal_id</code>.
	 */
	public void setCausalId(java.lang.Integer value) {
		setValue(19, value);
	}

	/**
	 * Getter for <code>drm.vw_jobs.causal_id</code>.
	 */
	public java.lang.Integer getCausalId() {
		return (java.lang.Integer) getValue(19);
	}

	/**
	 * Setter for <code>drm.vw_jobs.causal_description</code>.
	 */
	public void setCausalDescription(java.lang.String value) {
		setValue(20, value);
	}

	/**
	 * Getter for <code>drm.vw_jobs.causal_description</code>.
	 */
	public java.lang.String getCausalDescription() {
		return (java.lang.String) getValue(20);
	}

	// -------------------------------------------------------------------------
	// Record21 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row21<java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.String, org.joda.time.DateTime, org.joda.time.DateTime, java.lang.String, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.String> fieldsRow() {
		return (org.jooq.Row21) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row21<java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.String, org.joda.time.DateTime, org.joda.time.DateTime, java.lang.String, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.String> valuesRow() {
		return (org.jooq.Row21) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field1() {
		return com.sonicle.webtop.drm.jooq.tables.VwJobs.VW_JOBS.JOB_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field2() {
		return com.sonicle.webtop.drm.jooq.tables.VwJobs.VW_JOBS.OPERATOR_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field3() {
		return com.sonicle.webtop.drm.jooq.tables.VwJobs.VW_JOBS.OPERATOR_DESCRIPTION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field4() {
		return com.sonicle.webtop.drm.jooq.tables.VwJobs.VW_JOBS.CUSTOMER_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field5() {
		return com.sonicle.webtop.drm.jooq.tables.VwJobs.VW_JOBS.CUSTOMER_DESCRIPTION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field6() {
		return com.sonicle.webtop.drm.jooq.tables.VwJobs.VW_JOBS.CUSTOMER_STAT_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field7() {
		return com.sonicle.webtop.drm.jooq.tables.VwJobs.VW_JOBS.CUSTOMER_STAT_DESCRIPTION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field8() {
		return com.sonicle.webtop.drm.jooq.tables.VwJobs.VW_JOBS.ACTIVITY_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field9() {
		return com.sonicle.webtop.drm.jooq.tables.VwJobs.VW_JOBS.ACTIVITY_DESCRIPTION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<org.joda.time.DateTime> field10() {
		return com.sonicle.webtop.drm.jooq.tables.VwJobs.VW_JOBS.START_DATE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<org.joda.time.DateTime> field11() {
		return com.sonicle.webtop.drm.jooq.tables.VwJobs.VW_JOBS.END_DATE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field12() {
		return com.sonicle.webtop.drm.jooq.tables.VwJobs.VW_JOBS.TIMEZONE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field13() {
		return com.sonicle.webtop.drm.jooq.tables.VwJobs.VW_JOBS.COMPANY_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field14() {
		return com.sonicle.webtop.drm.jooq.tables.VwJobs.VW_JOBS.COMPANY_DESCRIPTION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field15() {
		return com.sonicle.webtop.drm.jooq.tables.VwJobs.VW_JOBS.TITLE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field16() {
		return com.sonicle.webtop.drm.jooq.tables.VwJobs.VW_JOBS.TICKET_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field17() {
		return com.sonicle.webtop.drm.jooq.tables.VwJobs.VW_JOBS.DOMAIN_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field18() {
		return com.sonicle.webtop.drm.jooq.tables.VwJobs.VW_JOBS.NUMBER;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field19() {
		return com.sonicle.webtop.drm.jooq.tables.VwJobs.VW_JOBS.DESCRIPTION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field20() {
		return com.sonicle.webtop.drm.jooq.tables.VwJobs.VW_JOBS.CAUSAL_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field21() {
		return com.sonicle.webtop.drm.jooq.tables.VwJobs.VW_JOBS.CAUSAL_DESCRIPTION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value1() {
		return getJobId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value2() {
		return getOperatorId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value3() {
		return getOperatorDescription();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value4() {
		return getCustomerId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value5() {
		return getCustomerDescription();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value6() {
		return getCustomerStatId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value7() {
		return getCustomerStatDescription();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value8() {
		return getActivityId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value9() {
		return getActivityDescription();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.joda.time.DateTime value10() {
		return getStartDate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.joda.time.DateTime value11() {
		return getEndDate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value12() {
		return getTimezone();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value13() {
		return getCompanyId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value14() {
		return getCompanyDescription();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value15() {
		return getTitle();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value16() {
		return getTicketId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value17() {
		return getDomainId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value18() {
		return getNumber();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value19() {
		return getDescription();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value20() {
		return getCausalId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value21() {
		return getCausalDescription();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public VwJobsRecord value1(java.lang.String value) {
		setJobId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public VwJobsRecord value2(java.lang.String value) {
		setOperatorId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public VwJobsRecord value3(java.lang.String value) {
		setOperatorDescription(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public VwJobsRecord value4(java.lang.String value) {
		setCustomerId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public VwJobsRecord value5(java.lang.String value) {
		setCustomerDescription(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public VwJobsRecord value6(java.lang.String value) {
		setCustomerStatId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public VwJobsRecord value7(java.lang.String value) {
		setCustomerStatDescription(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public VwJobsRecord value8(java.lang.Integer value) {
		setActivityId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public VwJobsRecord value9(java.lang.String value) {
		setActivityDescription(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public VwJobsRecord value10(org.joda.time.DateTime value) {
		setStartDate(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public VwJobsRecord value11(org.joda.time.DateTime value) {
		setEndDate(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public VwJobsRecord value12(java.lang.String value) {
		setTimezone(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public VwJobsRecord value13(java.lang.Integer value) {
		setCompanyId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public VwJobsRecord value14(java.lang.String value) {
		setCompanyDescription(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public VwJobsRecord value15(java.lang.String value) {
		setTitle(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public VwJobsRecord value16(java.lang.String value) {
		setTicketId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public VwJobsRecord value17(java.lang.String value) {
		setDomainId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public VwJobsRecord value18(java.lang.String value) {
		setNumber(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public VwJobsRecord value19(java.lang.String value) {
		setDescription(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public VwJobsRecord value20(java.lang.Integer value) {
		setCausalId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public VwJobsRecord value21(java.lang.String value) {
		setCausalDescription(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public VwJobsRecord values(java.lang.String value1, java.lang.String value2, java.lang.String value3, java.lang.String value4, java.lang.String value5, java.lang.String value6, java.lang.String value7, java.lang.Integer value8, java.lang.String value9, org.joda.time.DateTime value10, org.joda.time.DateTime value11, java.lang.String value12, java.lang.Integer value13, java.lang.String value14, java.lang.String value15, java.lang.String value16, java.lang.String value17, java.lang.String value18, java.lang.String value19, java.lang.Integer value20, java.lang.String value21) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached VwJobsRecord
	 */
	public VwJobsRecord() {
		super(com.sonicle.webtop.drm.jooq.tables.VwJobs.VW_JOBS);
	}

	/**
	 * Create a detached, initialised VwJobsRecord
	 */
	public VwJobsRecord(java.lang.String jobId, java.lang.String operatorId, java.lang.String operatorDescription, java.lang.String customerId, java.lang.String customerDescription, java.lang.String customerStatId, java.lang.String customerStatDescription, java.lang.Integer activityId, java.lang.String activityDescription, org.joda.time.DateTime startDate, org.joda.time.DateTime endDate, java.lang.String timezone, java.lang.Integer companyId, java.lang.String companyDescription, java.lang.String title, java.lang.String ticketId, java.lang.String domainId, java.lang.String number, java.lang.String description, java.lang.Integer causalId, java.lang.String causalDescription) {
		super(com.sonicle.webtop.drm.jooq.tables.VwJobs.VW_JOBS);

		setValue(0, jobId);
		setValue(1, operatorId);
		setValue(2, operatorDescription);
		setValue(3, customerId);
		setValue(4, customerDescription);
		setValue(5, customerStatId);
		setValue(6, customerStatDescription);
		setValue(7, activityId);
		setValue(8, activityDescription);
		setValue(9, startDate);
		setValue(10, endDate);
		setValue(11, timezone);
		setValue(12, companyId);
		setValue(13, companyDescription);
		setValue(14, title);
		setValue(15, ticketId);
		setValue(16, domainId);
		setValue(17, number);
		setValue(18, description);
		setValue(19, causalId);
		setValue(20, causalDescription);
	}
}
