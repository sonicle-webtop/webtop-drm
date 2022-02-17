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
public class Causals extends org.jooq.impl.TableImpl<com.sonicle.webtop.drm.jooq.tables.records.CausalsRecord> {

	private static final long serialVersionUID = -895749147;

	/**
	 * The reference instance of <code>drm.causals</code>
	 */
	public static final com.sonicle.webtop.drm.jooq.tables.Causals CAUSALS = new com.sonicle.webtop.drm.jooq.tables.Causals();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<com.sonicle.webtop.drm.jooq.tables.records.CausalsRecord> getRecordType() {
		return com.sonicle.webtop.drm.jooq.tables.records.CausalsRecord.class;
	}

	/**
	 * The column <code>drm.causals.id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.CausalsRecord, java.lang.String> ID = createField("id", org.jooq.impl.SQLDataType.VARCHAR.length(5).nullable(false), this, "");

	/**
	 * The column <code>drm.causals.description</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.CausalsRecord, java.lang.String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "");

	/**
	 * The column <code>drm.causals.external_code</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.CausalsRecord, java.lang.String> EXTERNAL_CODE = createField("external_code", org.jooq.impl.SQLDataType.VARCHAR.length(5).nullable(false), this, "");

	/**
	 * Create a <code>drm.causals</code> table reference
	 */
	public Causals() {
		this("causals", null);
	}

	/**
	 * Create an aliased <code>drm.causals</code> table reference
	 */
	public Causals(java.lang.String alias) {
		this(alias, com.sonicle.webtop.drm.jooq.tables.Causals.CAUSALS);
	}

	private Causals(java.lang.String alias, org.jooq.Table<com.sonicle.webtop.drm.jooq.tables.records.CausalsRecord> aliased) {
		this(alias, aliased, null);
	}

	private Causals(java.lang.String alias, org.jooq.Table<com.sonicle.webtop.drm.jooq.tables.records.CausalsRecord> aliased, org.jooq.Field<?>[] parameters) {
		super(alias, com.sonicle.webtop.drm.jooq.Drm.DRM, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.CausalsRecord> getPrimaryKey() {
		return com.sonicle.webtop.drm.jooq.Keys.TIMETABLE_CAUSALS_PKEY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.CausalsRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.CausalsRecord>>asList(com.sonicle.webtop.drm.jooq.Keys.TIMETABLE_CAUSALS_PKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public com.sonicle.webtop.drm.jooq.tables.Causals as(java.lang.String alias) {
		return new com.sonicle.webtop.drm.jooq.tables.Causals(alias, this);
	}

	/**
	 * Rename this table
	 */
	public com.sonicle.webtop.drm.jooq.tables.Causals rename(java.lang.String name) {
		return new com.sonicle.webtop.drm.jooq.tables.Causals(name, null);
	}
}