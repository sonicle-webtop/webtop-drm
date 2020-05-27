/**
 * This class is generated by jOOQ
 */
package com.sonicle.webtop.drm.jooq.tables;

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
public class Tickets extends org.jooq.impl.TableImpl<com.sonicle.webtop.drm.jooq.tables.records.TicketsRecord> {

	private static final long serialVersionUID = 798680824;

	/**
	 * The reference instance of <code>drm.tickets</code>
	 */
	public static final com.sonicle.webtop.drm.jooq.tables.Tickets TICKETS = new com.sonicle.webtop.drm.jooq.tables.Tickets();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<com.sonicle.webtop.drm.jooq.tables.records.TicketsRecord> getRecordType() {
		return com.sonicle.webtop.drm.jooq.tables.records.TicketsRecord.class;
	}

	/**
	 * The column <code>drm.tickets.ticket_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.TicketsRecord, java.lang.String> TICKET_ID = createField("ticket_id", org.jooq.impl.SQLDataType.VARCHAR.length(36).nullable(false), this, "");

	/**
	 * The column <code>drm.tickets.domain_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.TicketsRecord, java.lang.String> DOMAIN_ID = createField("domain_id", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false), this, "");

	/**
	 * The column <code>drm.tickets.company_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.TicketsRecord, java.lang.Integer> COMPANY_ID = createField("company_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>drm.tickets.from_operator_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.TicketsRecord, java.lang.String> FROM_OPERATOR_ID = createField("from_operator_id", org.jooq.impl.SQLDataType.VARCHAR.length(36).nullable(false), this, "");

	/**
	 * The column <code>drm.tickets.to_operator_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.TicketsRecord, java.lang.String> TO_OPERATOR_ID = createField("to_operator_id", org.jooq.impl.SQLDataType.VARCHAR.length(36).nullable(false), this, "");

	/**
	 * The column <code>drm.tickets.customer_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.TicketsRecord, java.lang.String> CUSTOMER_ID = createField("customer_id", org.jooq.impl.SQLDataType.VARCHAR.length(36).nullable(false), this, "");

	/**
	 * The column <code>drm.tickets.customer_stat_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.TicketsRecord, java.lang.String> CUSTOMER_STAT_ID = createField("customer_stat_id", org.jooq.impl.SQLDataType.VARCHAR.length(36).nullable(false), this, "");

	/**
	 * The column <code>drm.tickets.title</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.TicketsRecord, java.lang.String> TITLE = createField("title", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "");

	/**
	 * The column <code>drm.tickets.description</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.TicketsRecord, java.lang.String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.VARCHAR.length(2048), this, "");

	/**
	 * The column <code>drm.tickets.ticket_category_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.TicketsRecord, java.lang.Integer> TICKET_CATEGORY_ID = createField("ticket_category_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>drm.tickets.event_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.TicketsRecord, java.lang.Integer> EVENT_ID = createField("event_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>drm.tickets.date</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.TicketsRecord, org.joda.time.DateTime> DATE = createField("date", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "", new com.sonicle.webtop.core.jooq.DateTimeConverter());

	/**
	 * The column <code>drm.tickets.timezone</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.TicketsRecord, java.lang.String> TIMEZONE = createField("timezone", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false), this, "");

	/**
	 * The column <code>drm.tickets.priority_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.TicketsRecord, java.lang.String> PRIORITY_ID = createField("priority_id", org.jooq.impl.SQLDataType.VARCHAR.length(1), this, "");

	/**
	 * The column <code>drm.tickets.status_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.TicketsRecord, java.lang.Integer> STATUS_ID = createField("status_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>drm.tickets.release</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.TicketsRecord, java.lang.String> RELEASE = createField("release", org.jooq.impl.SQLDataType.VARCHAR.length(50), this, "");

	/**
	 * The column <code>drm.tickets.environment</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.TicketsRecord, java.lang.String> ENVIRONMENT = createField("environment", org.jooq.impl.SQLDataType.VARCHAR.length(200), this, "");

	/**
	 * The column <code>drm.tickets.suggestion</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.TicketsRecord, java.lang.String> SUGGESTION = createField("suggestion", org.jooq.impl.SQLDataType.VARCHAR.length(2048), this, "");

	/**
	 * The column <code>drm.tickets.simulation</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.TicketsRecord, java.lang.String> SIMULATION = createField("simulation", org.jooq.impl.SQLDataType.VARCHAR.length(2048), this, "");

	/**
	 * The column <code>drm.tickets.number</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.TicketsRecord, java.lang.String> NUMBER = createField("number", org.jooq.impl.SQLDataType.VARCHAR.length(12), this, "");

	/**
	 * Create a <code>drm.tickets</code> table reference
	 */
	public Tickets() {
		this("tickets", null);
	}

	/**
	 * Create an aliased <code>drm.tickets</code> table reference
	 */
	public Tickets(java.lang.String alias) {
		this(alias, com.sonicle.webtop.drm.jooq.tables.Tickets.TICKETS);
	}

	private Tickets(java.lang.String alias, org.jooq.Table<com.sonicle.webtop.drm.jooq.tables.records.TicketsRecord> aliased) {
		this(alias, aliased, null);
	}

	private Tickets(java.lang.String alias, org.jooq.Table<com.sonicle.webtop.drm.jooq.tables.records.TicketsRecord> aliased, org.jooq.Field<?>[] parameters) {
		super(alias, com.sonicle.webtop.drm.jooq.Drm.DRM, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.TicketsRecord> getPrimaryKey() {
		return com.sonicle.webtop.drm.jooq.Keys.TICKETS_PKEY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.TicketsRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.TicketsRecord>>asList(com.sonicle.webtop.drm.jooq.Keys.TICKETS_PKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public com.sonicle.webtop.drm.jooq.tables.Tickets as(java.lang.String alias) {
		return new com.sonicle.webtop.drm.jooq.tables.Tickets(alias, this);
	}

	/**
	 * Rename this table
	 */
	public com.sonicle.webtop.drm.jooq.tables.Tickets rename(java.lang.String name) {
		return new com.sonicle.webtop.drm.jooq.tables.Tickets(name, null);
	}
}
