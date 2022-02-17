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
public class TicketsSettingsRecord extends org.jooq.impl.UpdatableRecordImpl<com.sonicle.webtop.drm.jooq.tables.records.TicketsSettingsRecord> implements org.jooq.Record2<java.lang.Integer, java.lang.String> {

	private static final long serialVersionUID = -1645105834;

	/**
	 * Setter for <code>drm.tickets_settings.ticket_setting_id</code>.
	 */
	public void setTicketSettingId(java.lang.Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>drm.tickets_settings.ticket_setting_id</code>.
	 */
	public java.lang.Integer getTicketSettingId() {
		return (java.lang.Integer) getValue(0);
	}

	/**
	 * Setter for <code>drm.tickets_settings.domain_id</code>.
	 */
	public void setDomainId(java.lang.String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>drm.tickets_settings.domain_id</code>.
	 */
	public java.lang.String getDomainId() {
		return (java.lang.String) getValue(1);
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
	// Record2 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row2<java.lang.Integer, java.lang.String> fieldsRow() {
		return (org.jooq.Row2) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row2<java.lang.Integer, java.lang.String> valuesRow() {
		return (org.jooq.Row2) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field1() {
		return com.sonicle.webtop.drm.jooq.tables.TicketsSettings.TICKETS_SETTINGS.TICKET_SETTING_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field2() {
		return com.sonicle.webtop.drm.jooq.tables.TicketsSettings.TICKETS_SETTINGS.DOMAIN_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value1() {
		return getTicketSettingId();
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
	public TicketsSettingsRecord value1(java.lang.Integer value) {
		setTicketSettingId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TicketsSettingsRecord value2(java.lang.String value) {
		setDomainId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TicketsSettingsRecord values(java.lang.Integer value1, java.lang.String value2) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached TicketsSettingsRecord
	 */
	public TicketsSettingsRecord() {
		super(com.sonicle.webtop.drm.jooq.tables.TicketsSettings.TICKETS_SETTINGS);
	}

	/**
	 * Create a detached, initialised TicketsSettingsRecord
	 */
	public TicketsSettingsRecord(java.lang.Integer ticketSettingId, java.lang.String domainId) {
		super(com.sonicle.webtop.drm.jooq.tables.TicketsSettings.TICKETS_SETTINGS);

		setValue(0, ticketSettingId);
		setValue(1, domainId);
	}
}