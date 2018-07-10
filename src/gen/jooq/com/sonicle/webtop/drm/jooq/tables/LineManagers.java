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
public class LineManagers extends org.jooq.impl.TableImpl<com.sonicle.webtop.drm.jooq.tables.records.LineManagersRecord> {

	private static final long serialVersionUID = 1189527156;

	/**
	 * The reference instance of <code>drm.line_managers</code>
	 */
	public static final com.sonicle.webtop.drm.jooq.tables.LineManagers LINE_MANAGERS = new com.sonicle.webtop.drm.jooq.tables.LineManagers();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<com.sonicle.webtop.drm.jooq.tables.records.LineManagersRecord> getRecordType() {
		return com.sonicle.webtop.drm.jooq.tables.records.LineManagersRecord.class;
	}

	/**
	 * The column <code>drm.line_managers.domain_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.LineManagersRecord, java.lang.String> DOMAIN_ID = createField("domain_id", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false), this, "");

	/**
	 * The column <code>drm.line_managers.user_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.LineManagersRecord, java.lang.String> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.VARCHAR.length(36).nullable(false), this, "");

	/**
	 * Create a <code>drm.line_managers</code> table reference
	 */
	public LineManagers() {
		this("line_managers", null);
	}

	/**
	 * Create an aliased <code>drm.line_managers</code> table reference
	 */
	public LineManagers(java.lang.String alias) {
		this(alias, com.sonicle.webtop.drm.jooq.tables.LineManagers.LINE_MANAGERS);
	}

	private LineManagers(java.lang.String alias, org.jooq.Table<com.sonicle.webtop.drm.jooq.tables.records.LineManagersRecord> aliased) {
		this(alias, aliased, null);
	}

	private LineManagers(java.lang.String alias, org.jooq.Table<com.sonicle.webtop.drm.jooq.tables.records.LineManagersRecord> aliased, org.jooq.Field<?>[] parameters) {
		super(alias, com.sonicle.webtop.drm.jooq.Drm.DRM, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.LineManagersRecord> getPrimaryKey() {
		return com.sonicle.webtop.drm.jooq.Keys.LINE_MANAGERS_PKEY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.LineManagersRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.LineManagersRecord>>asList(com.sonicle.webtop.drm.jooq.Keys.LINE_MANAGERS_PKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public com.sonicle.webtop.drm.jooq.tables.LineManagers as(java.lang.String alias) {
		return new com.sonicle.webtop.drm.jooq.tables.LineManagers(alias, this);
	}

	/**
	 * Rename this table
	 */
	public com.sonicle.webtop.drm.jooq.tables.LineManagers rename(java.lang.String name) {
		return new com.sonicle.webtop.drm.jooq.tables.LineManagers(name, null);
	}
}