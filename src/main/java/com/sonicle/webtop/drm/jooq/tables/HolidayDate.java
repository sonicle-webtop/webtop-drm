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
public class HolidayDate extends org.jooq.impl.TableImpl<com.sonicle.webtop.drm.jooq.tables.records.HolidayDateRecord> {

	private static final long serialVersionUID = -772911914;

	/**
	 * The reference instance of <code>drm.holiday_date</code>
	 */
	public static final com.sonicle.webtop.drm.jooq.tables.HolidayDate HOLIDAY_DATE = new com.sonicle.webtop.drm.jooq.tables.HolidayDate();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<com.sonicle.webtop.drm.jooq.tables.records.HolidayDateRecord> getRecordType() {
		return com.sonicle.webtop.drm.jooq.tables.records.HolidayDateRecord.class;
	}

	/**
	 * The column <code>drm.holiday_date.holiday_date_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.HolidayDateRecord, java.math.BigInteger> HOLIDAY_DATE_ID = createField("holiday_date_id", org.jooq.impl.SQLDataType.DECIMAL_INTEGER.precision(38).nullable(false), this, "");

	/**
	 * The column <code>drm.holiday_date.description</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.HolidayDateRecord, java.lang.String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false), this, "");

	/**
	 * The column <code>drm.holiday_date.date</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.HolidayDateRecord, org.joda.time.DateTime> DATE = createField("date", org.jooq.impl.SQLDataType.TIMESTAMP, this, "", new com.sonicle.webtop.core.jooq.DateTimeConverter());

	/**
	 * The column <code>drm.holiday_date.domain_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.HolidayDateRecord, java.lang.String> DOMAIN_ID = createField("domain_id", org.jooq.impl.SQLDataType.VARCHAR.length(30), this, "");

	/**
	 * Create a <code>drm.holiday_date</code> table reference
	 */
	public HolidayDate() {
		this("holiday_date", null);
	}

	/**
	 * Create an aliased <code>drm.holiday_date</code> table reference
	 */
	public HolidayDate(java.lang.String alias) {
		this(alias, com.sonicle.webtop.drm.jooq.tables.HolidayDate.HOLIDAY_DATE);
	}

	private HolidayDate(java.lang.String alias, org.jooq.Table<com.sonicle.webtop.drm.jooq.tables.records.HolidayDateRecord> aliased) {
		this(alias, aliased, null);
	}

	private HolidayDate(java.lang.String alias, org.jooq.Table<com.sonicle.webtop.drm.jooq.tables.records.HolidayDateRecord> aliased, org.jooq.Field<?>[] parameters) {
		super(alias, com.sonicle.webtop.drm.jooq.Drm.DRM, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.HolidayDateRecord> getPrimaryKey() {
		return com.sonicle.webtop.drm.jooq.Keys.HOLIDAY_DATE_PKEY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.HolidayDateRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.HolidayDateRecord>>asList(com.sonicle.webtop.drm.jooq.Keys.HOLIDAY_DATE_PKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public com.sonicle.webtop.drm.jooq.tables.HolidayDate as(java.lang.String alias) {
		return new com.sonicle.webtop.drm.jooq.tables.HolidayDate(alias, this);
	}

	/**
	 * Rename this table
	 */
	public com.sonicle.webtop.drm.jooq.tables.HolidayDate rename(java.lang.String name) {
		return new com.sonicle.webtop.drm.jooq.tables.HolidayDate(name, null);
	}
}
