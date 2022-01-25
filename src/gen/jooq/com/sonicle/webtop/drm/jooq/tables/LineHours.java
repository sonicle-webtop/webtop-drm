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
public class LineHours extends org.jooq.impl.TableImpl<com.sonicle.webtop.drm.jooq.tables.records.LineHoursRecord> {

	private static final long serialVersionUID = -918709141;

	/**
	 * The reference instance of <code>drm.line_hours</code>
	 */
	public static final com.sonicle.webtop.drm.jooq.tables.LineHours LINE_HOURS = new com.sonicle.webtop.drm.jooq.tables.LineHours();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<com.sonicle.webtop.drm.jooq.tables.records.LineHoursRecord> getRecordType() {
		return com.sonicle.webtop.drm.jooq.tables.records.LineHoursRecord.class;
	}

	/**
	 * The column <code>drm.line_hours.id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.LineHoursRecord, java.lang.Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>drm.line_hours.domain_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.LineHoursRecord, java.lang.String> DOMAIN_ID = createField("domain_id", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false), this, "");

	/**
	 * The column <code>drm.line_hours.hour_profile_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.LineHoursRecord, java.lang.Integer> HOUR_PROFILE_ID = createField("hour_profile_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>drm.line_hours.line_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.LineHoursRecord, java.lang.Integer> LINE_ID = createField("line_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>drm.line_hours.1_e</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.LineHoursRecord, java.lang.String> _1_E = createField("1_e", org.jooq.impl.SQLDataType.VARCHAR.length(5), this, "");

	/**
	 * The column <code>drm.line_hours.1_u</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.LineHoursRecord, java.lang.String> _1_U = createField("1_u", org.jooq.impl.SQLDataType.VARCHAR.length(5), this, "");

	/**
	 * The column <code>drm.line_hours.1_h</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.LineHoursRecord, java.lang.String> _1_H = createField("1_h", org.jooq.impl.SQLDataType.VARCHAR.length(5), this, "");

	/**
	 * The column <code>drm.line_hours.2_e</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.LineHoursRecord, java.lang.String> _2_E = createField("2_e", org.jooq.impl.SQLDataType.VARCHAR.length(5), this, "");

	/**
	 * The column <code>drm.line_hours.2_u</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.LineHoursRecord, java.lang.String> _2_U = createField("2_u", org.jooq.impl.SQLDataType.VARCHAR.length(5), this, "");

	/**
	 * The column <code>drm.line_hours.2_h</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.LineHoursRecord, java.lang.String> _2_H = createField("2_h", org.jooq.impl.SQLDataType.VARCHAR.length(5), this, "");

	/**
	 * The column <code>drm.line_hours.3_e</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.LineHoursRecord, java.lang.String> _3_E = createField("3_e", org.jooq.impl.SQLDataType.VARCHAR.length(5), this, "");

	/**
	 * The column <code>drm.line_hours.3_u</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.LineHoursRecord, java.lang.String> _3_U = createField("3_u", org.jooq.impl.SQLDataType.VARCHAR.length(5), this, "");

	/**
	 * The column <code>drm.line_hours.3_h</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.LineHoursRecord, java.lang.String> _3_H = createField("3_h", org.jooq.impl.SQLDataType.VARCHAR.length(5), this, "");

	/**
	 * The column <code>drm.line_hours.4_e</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.LineHoursRecord, java.lang.String> _4_E = createField("4_e", org.jooq.impl.SQLDataType.VARCHAR.length(5), this, "");

	/**
	 * The column <code>drm.line_hours.4_u</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.LineHoursRecord, java.lang.String> _4_U = createField("4_u", org.jooq.impl.SQLDataType.VARCHAR.length(5), this, "");

	/**
	 * The column <code>drm.line_hours.4_h</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.LineHoursRecord, java.lang.String> _4_H = createField("4_h", org.jooq.impl.SQLDataType.VARCHAR.length(5), this, "");

	/**
	 * The column <code>drm.line_hours.5_e</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.LineHoursRecord, java.lang.String> _5_E = createField("5_e", org.jooq.impl.SQLDataType.VARCHAR.length(5), this, "");

	/**
	 * The column <code>drm.line_hours.5_u</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.LineHoursRecord, java.lang.String> _5_U = createField("5_u", org.jooq.impl.SQLDataType.VARCHAR.length(5), this, "");

	/**
	 * The column <code>drm.line_hours.5_h</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.LineHoursRecord, java.lang.String> _5_H = createField("5_h", org.jooq.impl.SQLDataType.VARCHAR.length(5), this, "");

	/**
	 * The column <code>drm.line_hours.6_e</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.LineHoursRecord, java.lang.String> _6_E = createField("6_e", org.jooq.impl.SQLDataType.VARCHAR.length(5), this, "");

	/**
	 * The column <code>drm.line_hours.6_u</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.LineHoursRecord, java.lang.String> _6_U = createField("6_u", org.jooq.impl.SQLDataType.VARCHAR.length(5), this, "");

	/**
	 * The column <code>drm.line_hours.6_h</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.LineHoursRecord, java.lang.String> _6_H = createField("6_h", org.jooq.impl.SQLDataType.VARCHAR.length(5), this, "");

	/**
	 * The column <code>drm.line_hours.7_e</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.LineHoursRecord, java.lang.String> _7_E = createField("7_e", org.jooq.impl.SQLDataType.VARCHAR.length(5), this, "");

	/**
	 * The column <code>drm.line_hours.7_u</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.LineHoursRecord, java.lang.String> _7_U = createField("7_u", org.jooq.impl.SQLDataType.VARCHAR.length(5), this, "");

	/**
	 * The column <code>drm.line_hours.7_h</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.LineHoursRecord, java.lang.String> _7_H = createField("7_h", org.jooq.impl.SQLDataType.VARCHAR.length(5), this, "");

	/**
	 * Create a <code>drm.line_hours</code> table reference
	 */
	public LineHours() {
		this("line_hours", null);
	}

	/**
	 * Create an aliased <code>drm.line_hours</code> table reference
	 */
	public LineHours(java.lang.String alias) {
		this(alias, com.sonicle.webtop.drm.jooq.tables.LineHours.LINE_HOURS);
	}

	private LineHours(java.lang.String alias, org.jooq.Table<com.sonicle.webtop.drm.jooq.tables.records.LineHoursRecord> aliased) {
		this(alias, aliased, null);
	}

	private LineHours(java.lang.String alias, org.jooq.Table<com.sonicle.webtop.drm.jooq.tables.records.LineHoursRecord> aliased, org.jooq.Field<?>[] parameters) {
		super(alias, com.sonicle.webtop.drm.jooq.Drm.DRM, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.LineHoursRecord> getPrimaryKey() {
		return com.sonicle.webtop.drm.jooq.Keys.EMPLOYEE_HOURS_PKEY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.LineHoursRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.LineHoursRecord>>asList(com.sonicle.webtop.drm.jooq.Keys.EMPLOYEE_HOURS_PKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public com.sonicle.webtop.drm.jooq.tables.LineHours as(java.lang.String alias) {
		return new com.sonicle.webtop.drm.jooq.tables.LineHours(alias, this);
	}

	/**
	 * Rename this table
	 */
	public com.sonicle.webtop.drm.jooq.tables.LineHours rename(java.lang.String name) {
		return new com.sonicle.webtop.drm.jooq.tables.LineHours(name, null);
	}
}
