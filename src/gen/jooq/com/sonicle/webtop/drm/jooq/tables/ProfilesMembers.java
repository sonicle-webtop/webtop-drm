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
public class ProfilesMembers extends org.jooq.impl.TableImpl<com.sonicle.webtop.drm.jooq.tables.records.ProfilesMembersRecord> {

	private static final long serialVersionUID = -728235927;

	/**
	 * The reference instance of <code>drm.profiles_members</code>
	 */
	public static final com.sonicle.webtop.drm.jooq.tables.ProfilesMembers PROFILES_MEMBERS = new com.sonicle.webtop.drm.jooq.tables.ProfilesMembers();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<com.sonicle.webtop.drm.jooq.tables.records.ProfilesMembersRecord> getRecordType() {
		return com.sonicle.webtop.drm.jooq.tables.records.ProfilesMembersRecord.class;
	}

	/**
	 * The column <code>drm.profiles_members.id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.ProfilesMembersRecord, java.lang.Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>drm.profiles_members.profile_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.ProfilesMembersRecord, java.lang.String> PROFILE_ID = createField("profile_id", org.jooq.impl.SQLDataType.VARCHAR.length(36).nullable(false), this, "");

	/**
	 * The column <code>drm.profiles_members.user_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.ProfilesMembersRecord, java.lang.String> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.VARCHAR.length(36).nullable(false), this, "");

	/**
	 * Create a <code>drm.profiles_members</code> table reference
	 */
	public ProfilesMembers() {
		this("profiles_members", null);
	}

	/**
	 * Create an aliased <code>drm.profiles_members</code> table reference
	 */
	public ProfilesMembers(java.lang.String alias) {
		this(alias, com.sonicle.webtop.drm.jooq.tables.ProfilesMembers.PROFILES_MEMBERS);
	}

	private ProfilesMembers(java.lang.String alias, org.jooq.Table<com.sonicle.webtop.drm.jooq.tables.records.ProfilesMembersRecord> aliased) {
		this(alias, aliased, null);
	}

	private ProfilesMembers(java.lang.String alias, org.jooq.Table<com.sonicle.webtop.drm.jooq.tables.records.ProfilesMembersRecord> aliased, org.jooq.Field<?>[] parameters) {
		super(alias, com.sonicle.webtop.drm.jooq.Drm.DRM, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Identity<com.sonicle.webtop.drm.jooq.tables.records.ProfilesMembersRecord, java.lang.Integer> getIdentity() {
		return com.sonicle.webtop.drm.jooq.Keys.IDENTITY_PROFILES_MEMBERS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.ProfilesMembersRecord> getPrimaryKey() {
		return com.sonicle.webtop.drm.jooq.Keys.PROFILES_MEMBERS_PKEY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.ProfilesMembersRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.ProfilesMembersRecord>>asList(com.sonicle.webtop.drm.jooq.Keys.PROFILES_MEMBERS_PKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public com.sonicle.webtop.drm.jooq.tables.ProfilesMembers as(java.lang.String alias) {
		return new com.sonicle.webtop.drm.jooq.tables.ProfilesMembers(alias, this);
	}

	/**
	 * Rename this table
	 */
	public com.sonicle.webtop.drm.jooq.tables.ProfilesMembers rename(java.lang.String name) {
		return new com.sonicle.webtop.drm.jooq.tables.ProfilesMembers(name, null);
	}
}
