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
public class TicketsRecord extends org.jooq.impl.UpdatableRecordImpl<com.sonicle.webtop.drm.jooq.tables.records.TicketsRecord> implements org.jooq.Record20<java.lang.String, java.lang.String, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer, org.joda.time.DateTime, java.lang.String, java.lang.String, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String> {

	private static final long serialVersionUID = 2054537057;

	/**
	 * Setter for <code>drm.tickets.ticket_id</code>.
	 */
	public void setTicketId(java.lang.String value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>drm.tickets.ticket_id</code>.
	 */
	public java.lang.String getTicketId() {
		return (java.lang.String) getValue(0);
	}

	/**
	 * Setter for <code>drm.tickets.domain_id</code>.
	 */
	public void setDomainId(java.lang.String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>drm.tickets.domain_id</code>.
	 */
	public java.lang.String getDomainId() {
		return (java.lang.String) getValue(1);
	}

	/**
	 * Setter for <code>drm.tickets.company_id</code>.
	 */
	public void setCompanyId(java.lang.Integer value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>drm.tickets.company_id</code>.
	 */
	public java.lang.Integer getCompanyId() {
		return (java.lang.Integer) getValue(2);
	}

	/**
	 * Setter for <code>drm.tickets.from_operator_id</code>.
	 */
	public void setFromOperatorId(java.lang.String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>drm.tickets.from_operator_id</code>.
	 */
	public java.lang.String getFromOperatorId() {
		return (java.lang.String) getValue(3);
	}

	/**
	 * Setter for <code>drm.tickets.to_operator_id</code>.
	 */
	public void setToOperatorId(java.lang.String value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>drm.tickets.to_operator_id</code>.
	 */
	public java.lang.String getToOperatorId() {
		return (java.lang.String) getValue(4);
	}

	/**
	 * Setter for <code>drm.tickets.customer_id</code>.
	 */
	public void setCustomerId(java.lang.String value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>drm.tickets.customer_id</code>.
	 */
	public java.lang.String getCustomerId() {
		return (java.lang.String) getValue(5);
	}

	/**
	 * Setter for <code>drm.tickets.customer_stat_id</code>.
	 */
	public void setCustomerStatId(java.lang.String value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>drm.tickets.customer_stat_id</code>.
	 */
	public java.lang.String getCustomerStatId() {
		return (java.lang.String) getValue(6);
	}

	/**
	 * Setter for <code>drm.tickets.title</code>.
	 */
	public void setTitle(java.lang.String value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>drm.tickets.title</code>.
	 */
	public java.lang.String getTitle() {
		return (java.lang.String) getValue(7);
	}

	/**
	 * Setter for <code>drm.tickets.description</code>.
	 */
	public void setDescription(java.lang.String value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>drm.tickets.description</code>.
	 */
	public java.lang.String getDescription() {
		return (java.lang.String) getValue(8);
	}

	/**
	 * Setter for <code>drm.tickets.ticket_category_id</code>.
	 */
	public void setTicketCategoryId(java.lang.Integer value) {
		setValue(9, value);
	}

	/**
	 * Getter for <code>drm.tickets.ticket_category_id</code>.
	 */
	public java.lang.Integer getTicketCategoryId() {
		return (java.lang.Integer) getValue(9);
	}

	/**
	 * Setter for <code>drm.tickets.event_id</code>.
	 */
	public void setEventId(java.lang.Integer value) {
		setValue(10, value);
	}

	/**
	 * Getter for <code>drm.tickets.event_id</code>.
	 */
	public java.lang.Integer getEventId() {
		return (java.lang.Integer) getValue(10);
	}

	/**
	 * Setter for <code>drm.tickets.date</code>.
	 */
	public void setDate(org.joda.time.DateTime value) {
		setValue(11, value);
	}

	/**
	 * Getter for <code>drm.tickets.date</code>.
	 */
	public org.joda.time.DateTime getDate() {
		return (org.joda.time.DateTime) getValue(11);
	}

	/**
	 * Setter for <code>drm.tickets.timezone</code>.
	 */
	public void setTimezone(java.lang.String value) {
		setValue(12, value);
	}

	/**
	 * Getter for <code>drm.tickets.timezone</code>.
	 */
	public java.lang.String getTimezone() {
		return (java.lang.String) getValue(12);
	}

	/**
	 * Setter for <code>drm.tickets.priority_id</code>.
	 */
	public void setPriorityId(java.lang.String value) {
		setValue(13, value);
	}

	/**
	 * Getter for <code>drm.tickets.priority_id</code>.
	 */
	public java.lang.String getPriorityId() {
		return (java.lang.String) getValue(13);
	}

	/**
	 * Setter for <code>drm.tickets.status_id</code>.
	 */
	public void setStatusId(java.lang.Integer value) {
		setValue(14, value);
	}

	/**
	 * Getter for <code>drm.tickets.status_id</code>.
	 */
	public java.lang.Integer getStatusId() {
		return (java.lang.Integer) getValue(14);
	}

	/**
	 * Setter for <code>drm.tickets.release</code>.
	 */
	public void setRelease(java.lang.String value) {
		setValue(15, value);
	}

	/**
	 * Getter for <code>drm.tickets.release</code>.
	 */
	public java.lang.String getRelease() {
		return (java.lang.String) getValue(15);
	}

	/**
	 * Setter for <code>drm.tickets.environment</code>.
	 */
	public void setEnvironment(java.lang.String value) {
		setValue(16, value);
	}

	/**
	 * Getter for <code>drm.tickets.environment</code>.
	 */
	public java.lang.String getEnvironment() {
		return (java.lang.String) getValue(16);
	}

	/**
	 * Setter for <code>drm.tickets.suggestion</code>.
	 */
	public void setSuggestion(java.lang.String value) {
		setValue(17, value);
	}

	/**
	 * Getter for <code>drm.tickets.suggestion</code>.
	 */
	public java.lang.String getSuggestion() {
		return (java.lang.String) getValue(17);
	}

	/**
	 * Setter for <code>drm.tickets.simulation</code>.
	 */
	public void setSimulation(java.lang.String value) {
		setValue(18, value);
	}

	/**
	 * Getter for <code>drm.tickets.simulation</code>.
	 */
	public java.lang.String getSimulation() {
		return (java.lang.String) getValue(18);
	}

	/**
	 * Setter for <code>drm.tickets.number</code>.
	 */
	public void setNumber(java.lang.String value) {
		setValue(19, value);
	}

	/**
	 * Getter for <code>drm.tickets.number</code>.
	 */
	public java.lang.String getNumber() {
		return (java.lang.String) getValue(19);
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
	// Record20 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row20<java.lang.String, java.lang.String, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer, org.joda.time.DateTime, java.lang.String, java.lang.String, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String> fieldsRow() {
		return (org.jooq.Row20) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row20<java.lang.String, java.lang.String, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer, org.joda.time.DateTime, java.lang.String, java.lang.String, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String> valuesRow() {
		return (org.jooq.Row20) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field1() {
		return com.sonicle.webtop.drm.jooq.tables.Tickets.TICKETS.TICKET_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field2() {
		return com.sonicle.webtop.drm.jooq.tables.Tickets.TICKETS.DOMAIN_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field3() {
		return com.sonicle.webtop.drm.jooq.tables.Tickets.TICKETS.COMPANY_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field4() {
		return com.sonicle.webtop.drm.jooq.tables.Tickets.TICKETS.FROM_OPERATOR_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field5() {
		return com.sonicle.webtop.drm.jooq.tables.Tickets.TICKETS.TO_OPERATOR_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field6() {
		return com.sonicle.webtop.drm.jooq.tables.Tickets.TICKETS.CUSTOMER_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field7() {
		return com.sonicle.webtop.drm.jooq.tables.Tickets.TICKETS.CUSTOMER_STAT_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field8() {
		return com.sonicle.webtop.drm.jooq.tables.Tickets.TICKETS.TITLE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field9() {
		return com.sonicle.webtop.drm.jooq.tables.Tickets.TICKETS.DESCRIPTION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field10() {
		return com.sonicle.webtop.drm.jooq.tables.Tickets.TICKETS.TICKET_CATEGORY_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field11() {
		return com.sonicle.webtop.drm.jooq.tables.Tickets.TICKETS.EVENT_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<org.joda.time.DateTime> field12() {
		return com.sonicle.webtop.drm.jooq.tables.Tickets.TICKETS.DATE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field13() {
		return com.sonicle.webtop.drm.jooq.tables.Tickets.TICKETS.TIMEZONE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field14() {
		return com.sonicle.webtop.drm.jooq.tables.Tickets.TICKETS.PRIORITY_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field15() {
		return com.sonicle.webtop.drm.jooq.tables.Tickets.TICKETS.STATUS_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field16() {
		return com.sonicle.webtop.drm.jooq.tables.Tickets.TICKETS.RELEASE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field17() {
		return com.sonicle.webtop.drm.jooq.tables.Tickets.TICKETS.ENVIRONMENT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field18() {
		return com.sonicle.webtop.drm.jooq.tables.Tickets.TICKETS.SUGGESTION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field19() {
		return com.sonicle.webtop.drm.jooq.tables.Tickets.TICKETS.SIMULATION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field20() {
		return com.sonicle.webtop.drm.jooq.tables.Tickets.TICKETS.NUMBER;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value1() {
		return getTicketId();
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
		return getFromOperatorId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value5() {
		return getToOperatorId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value6() {
		return getCustomerId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value7() {
		return getCustomerStatId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value8() {
		return getTitle();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value9() {
		return getDescription();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value10() {
		return getTicketCategoryId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value11() {
		return getEventId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.joda.time.DateTime value12() {
		return getDate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value13() {
		return getTimezone();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value14() {
		return getPriorityId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value15() {
		return getStatusId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value16() {
		return getRelease();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value17() {
		return getEnvironment();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value18() {
		return getSuggestion();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value19() {
		return getSimulation();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value20() {
		return getNumber();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TicketsRecord value1(java.lang.String value) {
		setTicketId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TicketsRecord value2(java.lang.String value) {
		setDomainId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TicketsRecord value3(java.lang.Integer value) {
		setCompanyId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TicketsRecord value4(java.lang.String value) {
		setFromOperatorId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TicketsRecord value5(java.lang.String value) {
		setToOperatorId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TicketsRecord value6(java.lang.String value) {
		setCustomerId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TicketsRecord value7(java.lang.String value) {
		setCustomerStatId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TicketsRecord value8(java.lang.String value) {
		setTitle(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TicketsRecord value9(java.lang.String value) {
		setDescription(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TicketsRecord value10(java.lang.Integer value) {
		setTicketCategoryId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TicketsRecord value11(java.lang.Integer value) {
		setEventId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TicketsRecord value12(org.joda.time.DateTime value) {
		setDate(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TicketsRecord value13(java.lang.String value) {
		setTimezone(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TicketsRecord value14(java.lang.String value) {
		setPriorityId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TicketsRecord value15(java.lang.Integer value) {
		setStatusId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TicketsRecord value16(java.lang.String value) {
		setRelease(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TicketsRecord value17(java.lang.String value) {
		setEnvironment(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TicketsRecord value18(java.lang.String value) {
		setSuggestion(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TicketsRecord value19(java.lang.String value) {
		setSimulation(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TicketsRecord value20(java.lang.String value) {
		setNumber(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TicketsRecord values(java.lang.String value1, java.lang.String value2, java.lang.Integer value3, java.lang.String value4, java.lang.String value5, java.lang.String value6, java.lang.String value7, java.lang.String value8, java.lang.String value9, java.lang.Integer value10, java.lang.Integer value11, org.joda.time.DateTime value12, java.lang.String value13, java.lang.String value14, java.lang.Integer value15, java.lang.String value16, java.lang.String value17, java.lang.String value18, java.lang.String value19, java.lang.String value20) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached TicketsRecord
	 */
	public TicketsRecord() {
		super(com.sonicle.webtop.drm.jooq.tables.Tickets.TICKETS);
	}

	/**
	 * Create a detached, initialised TicketsRecord
	 */
	public TicketsRecord(java.lang.String ticketId, java.lang.String domainId, java.lang.Integer companyId, java.lang.String fromOperatorId, java.lang.String toOperatorId, java.lang.String customerId, java.lang.String customerStatId, java.lang.String title, java.lang.String description, java.lang.Integer ticketCategoryId, java.lang.Integer eventId, org.joda.time.DateTime date, java.lang.String timezone, java.lang.String priorityId, java.lang.Integer statusId, java.lang.String release, java.lang.String environment, java.lang.String suggestion, java.lang.String simulation, java.lang.String number) {
		super(com.sonicle.webtop.drm.jooq.tables.Tickets.TICKETS);

		setValue(0, ticketId);
		setValue(1, domainId);
		setValue(2, companyId);
		setValue(3, fromOperatorId);
		setValue(4, toOperatorId);
		setValue(5, customerId);
		setValue(6, customerStatId);
		setValue(7, title);
		setValue(8, description);
		setValue(9, ticketCategoryId);
		setValue(10, eventId);
		setValue(11, date);
		setValue(12, timezone);
		setValue(13, priorityId);
		setValue(14, statusId);
		setValue(15, release);
		setValue(16, environment);
		setValue(17, suggestion);
		setValue(18, simulation);
		setValue(19, number);
	}
}
