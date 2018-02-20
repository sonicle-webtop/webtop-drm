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
public class EmployeeProfiles extends org.jooq.impl.TableImpl<com.sonicle.webtop.drm.jooq.tables.records.EmployeeProfilesRecord> {

	private static final long serialVersionUID = 847943323;

	/**
	 * The reference instance of <code>drm.employee_profiles</code>
	 */
	public static final com.sonicle.webtop.drm.jooq.tables.EmployeeProfiles EMPLOYEE_PROFILES = new com.sonicle.webtop.drm.jooq.tables.EmployeeProfiles();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<com.sonicle.webtop.drm.jooq.tables.records.EmployeeProfilesRecord> getRecordType() {
		return com.sonicle.webtop.drm.jooq.tables.records.EmployeeProfilesRecord.class;
	}

	/**
	 * The column <code>drm.employee_profiles.id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.EmployeeProfilesRecord, java.lang.Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>drm.employee_profiles.domain_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.EmployeeProfilesRecord, java.lang.String> DOMAIN_ID = createField("domain_id", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false), this, "");

	/**
	 * The column <code>drm.employee_profiles.user_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.EmployeeProfilesRecord, java.lang.String> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.VARCHAR.length(36).nullable(false), this, "");

	/**
	 * The column <code>drm.employee_profiles.number</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.EmployeeProfilesRecord, java.lang.String> NUMBER = createField("number", org.jooq.impl.SQLDataType.VARCHAR.length(50), this, "");

	/**
	 * The column <code>drm.employee_profiles.tolerance</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.EmployeeProfilesRecord, java.lang.String> TOLERANCE = createField("tolerance", org.jooq.impl.SQLDataType.VARCHAR.length(50), this, "");

	/**
	 * The column <code>drm.employee_profiles.extraordinary</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.EmployeeProfilesRecord, java.lang.Boolean> EXTRAORDINARY = createField("extraordinary", org.jooq.impl.SQLDataType.BOOLEAN, this, "");

	/**
	 * The column <code>drm.employee_profiles.only_presence</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.EmployeeProfilesRecord, java.lang.Boolean> ONLY_PRESENCE = createField("only_presence", org.jooq.impl.SQLDataType.BOOLEAN, this, "");

	/**
	 * Create a <code>drm.employee_profiles</code> table reference
	 */
	public EmployeeProfiles() {
		this("employee_profiles", null);
	}

	/**
	 * Create an aliased <code>drm.employee_profiles</code> table reference
	 */
	public EmployeeProfiles(java.lang.String alias) {
		this(alias, com.sonicle.webtop.drm.jooq.tables.EmployeeProfiles.EMPLOYEE_PROFILES);
	}

	private EmployeeProfiles(java.lang.String alias, org.jooq.Table<com.sonicle.webtop.drm.jooq.tables.records.EmployeeProfilesRecord> aliased) {
		this(alias, aliased, null);
	}

	private EmployeeProfiles(java.lang.String alias, org.jooq.Table<com.sonicle.webtop.drm.jooq.tables.records.EmployeeProfilesRecord> aliased, org.jooq.Field<?>[] parameters) {
		super(alias, com.sonicle.webtop.drm.jooq.Drm.DRM, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.EmployeeProfilesRecord> getPrimaryKey() {
		return com.sonicle.webtop.drm.jooq.Keys.EMPLOYEE_PROFILES_PKEY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.EmployeeProfilesRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.EmployeeProfilesRecord>>asList(com.sonicle.webtop.drm.jooq.Keys.EMPLOYEE_PROFILES_PKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public com.sonicle.webtop.drm.jooq.tables.EmployeeProfiles as(java.lang.String alias) {
		return new com.sonicle.webtop.drm.jooq.tables.EmployeeProfiles(alias, this);
	}

	/**
	 * Rename this table
	 */
	public com.sonicle.webtop.drm.jooq.tables.EmployeeProfiles rename(java.lang.String name) {
		return new com.sonicle.webtop.drm.jooq.tables.EmployeeProfiles(name, null);
	}
}
