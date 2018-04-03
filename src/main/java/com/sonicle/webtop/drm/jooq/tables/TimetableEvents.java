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
public class TimetableEvents extends org.jooq.impl.TableImpl<com.sonicle.webtop.drm.jooq.tables.records.TimetableEventsRecord> {

	private static final long serialVersionUID = -1124226362;

	/**
	 * The reference instance of <code>drm.timetable_events</code>
	 */
	public static final com.sonicle.webtop.drm.jooq.tables.TimetableEvents TIMETABLE_EVENTS = new com.sonicle.webtop.drm.jooq.tables.TimetableEvents();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<com.sonicle.webtop.drm.jooq.tables.records.TimetableEventsRecord> getRecordType() {
		return com.sonicle.webtop.drm.jooq.tables.records.TimetableEventsRecord.class;
	}

	/**
	 * The column <code>drm.timetable_events.timetable_event_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.TimetableEventsRecord, java.lang.Integer> TIMETABLE_EVENT_ID = createField("timetable_event_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>drm.timetable_events.domain_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.TimetableEventsRecord, java.lang.String> DOMAIN_ID = createField("domain_id", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false), this, "");

	/**
	 * The column <code>drm.timetable_events.company_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.TimetableEventsRecord, java.lang.Integer> COMPANY_ID = createField("company_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>drm.timetable_events.user_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.TimetableEventsRecord, java.lang.String> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.VARCHAR.length(36).nullable(false), this, "");

	/**
	 * The column <code>drm.timetable_events.type</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.TimetableEventsRecord, java.lang.String> TYPE = createField("type", org.jooq.impl.SQLDataType.VARCHAR.length(1).nullable(false), this, "");

	/**
	 * The column <code>drm.timetable_events.date</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.TimetableEventsRecord, org.joda.time.LocalDate> DATE = createField("date", org.jooq.impl.SQLDataType.DATE.nullable(false), this, "", new com.sonicle.webtop.core.jooq.LocalDateConverter());

	/**
	 * The column <code>drm.timetable_events.hour</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.TimetableEventsRecord, java.lang.String> HOUR = createField("hour", org.jooq.impl.SQLDataType.VARCHAR.length(36).nullable(false), this, "");

	/**
	 * Create a <code>drm.timetable_events</code> table reference
	 */
	public TimetableEvents() {
		this("timetable_events", null);
	}

	/**
	 * Create an aliased <code>drm.timetable_events</code> table reference
	 */
	public TimetableEvents(java.lang.String alias) {
		this(alias, com.sonicle.webtop.drm.jooq.tables.TimetableEvents.TIMETABLE_EVENTS);
	}

	private TimetableEvents(java.lang.String alias, org.jooq.Table<com.sonicle.webtop.drm.jooq.tables.records.TimetableEventsRecord> aliased) {
		this(alias, aliased, null);
	}

	private TimetableEvents(java.lang.String alias, org.jooq.Table<com.sonicle.webtop.drm.jooq.tables.records.TimetableEventsRecord> aliased, org.jooq.Field<?>[] parameters) {
		super(alias, com.sonicle.webtop.drm.jooq.Drm.DRM, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.TimetableEventsRecord> getPrimaryKey() {
		return com.sonicle.webtop.drm.jooq.Keys.TIMETABLE_EVENTS_PKEY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.TimetableEventsRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.TimetableEventsRecord>>asList(com.sonicle.webtop.drm.jooq.Keys.TIMETABLE_EVENTS_PKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public com.sonicle.webtop.drm.jooq.tables.TimetableEvents as(java.lang.String alias) {
		return new com.sonicle.webtop.drm.jooq.tables.TimetableEvents(alias, this);
	}

	/**
	 * Rename this table
	 */
	public com.sonicle.webtop.drm.jooq.tables.TimetableEvents rename(java.lang.String name) {
		return new com.sonicle.webtop.drm.jooq.tables.TimetableEvents(name, null);
	}
}