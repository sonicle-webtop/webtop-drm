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
public class ActivitiesGroups extends org.jooq.impl.TableImpl<com.sonicle.webtop.drm.jooq.tables.records.ActivitiesGroupsRecord> {

	private static final long serialVersionUID = 2045536693;

	/**
	 * The reference instance of <code>drm.activities_groups</code>
	 */
	public static final com.sonicle.webtop.drm.jooq.tables.ActivitiesGroups ACTIVITIES_GROUPS = new com.sonicle.webtop.drm.jooq.tables.ActivitiesGroups();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<com.sonicle.webtop.drm.jooq.tables.records.ActivitiesGroupsRecord> getRecordType() {
		return com.sonicle.webtop.drm.jooq.tables.records.ActivitiesGroupsRecord.class;
	}

	/**
	 * The column <code>drm.activities_groups.association_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.ActivitiesGroupsRecord, java.lang.Integer> ASSOCIATION_ID = createField("association_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>drm.activities_groups.activity_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.ActivitiesGroupsRecord, java.lang.Integer> ACTIVITY_ID = createField("activity_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>drm.activities_groups.group_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.ActivitiesGroupsRecord, java.lang.String> GROUP_ID = createField("group_id", org.jooq.impl.SQLDataType.VARCHAR.length(36).nullable(false), this, "");

	/**
	 * Create a <code>drm.activities_groups</code> table reference
	 */
	public ActivitiesGroups() {
		this("activities_groups", null);
	}

	/**
	 * Create an aliased <code>drm.activities_groups</code> table reference
	 */
	public ActivitiesGroups(java.lang.String alias) {
		this(alias, com.sonicle.webtop.drm.jooq.tables.ActivitiesGroups.ACTIVITIES_GROUPS);
	}

	private ActivitiesGroups(java.lang.String alias, org.jooq.Table<com.sonicle.webtop.drm.jooq.tables.records.ActivitiesGroupsRecord> aliased) {
		this(alias, aliased, null);
	}

	private ActivitiesGroups(java.lang.String alias, org.jooq.Table<com.sonicle.webtop.drm.jooq.tables.records.ActivitiesGroupsRecord> aliased, org.jooq.Field<?>[] parameters) {
		super(alias, com.sonicle.webtop.drm.jooq.Drm.DRM, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Identity<com.sonicle.webtop.drm.jooq.tables.records.ActivitiesGroupsRecord, java.lang.Integer> getIdentity() {
		return com.sonicle.webtop.drm.jooq.Keys.IDENTITY_ACTIVITIES_GROUPS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.ActivitiesGroupsRecord> getPrimaryKey() {
		return com.sonicle.webtop.drm.jooq.Keys.ACTIVITIES_GROUPS_PKEY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.ActivitiesGroupsRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.ActivitiesGroupsRecord>>asList(com.sonicle.webtop.drm.jooq.Keys.ACTIVITIES_GROUPS_PKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public com.sonicle.webtop.drm.jooq.tables.ActivitiesGroups as(java.lang.String alias) {
		return new com.sonicle.webtop.drm.jooq.tables.ActivitiesGroups(alias, this);
	}

	/**
	 * Rename this table
	 */
	public com.sonicle.webtop.drm.jooq.tables.ActivitiesGroups rename(java.lang.String name) {
		return new com.sonicle.webtop.drm.jooq.tables.ActivitiesGroups(name, null);
	}
}
