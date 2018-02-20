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
public class EmployeeProfilesRecord extends org.jooq.impl.UpdatableRecordImpl<com.sonicle.webtop.drm.jooq.tables.records.EmployeeProfilesRecord> implements org.jooq.Record7<java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Boolean, java.lang.Boolean> {

	private static final long serialVersionUID = 894857297;

	/**
	 * Setter for <code>drm.employee_profiles.id</code>.
	 */
	public void setId(java.lang.Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>drm.employee_profiles.id</code>.
	 */
	public java.lang.Integer getId() {
		return (java.lang.Integer) getValue(0);
	}

	/**
	 * Setter for <code>drm.employee_profiles.domain_id</code>.
	 */
	public void setDomainId(java.lang.String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>drm.employee_profiles.domain_id</code>.
	 */
	public java.lang.String getDomainId() {
		return (java.lang.String) getValue(1);
	}

	/**
	 * Setter for <code>drm.employee_profiles.user_id</code>.
	 */
	public void setUserId(java.lang.String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>drm.employee_profiles.user_id</code>.
	 */
	public java.lang.String getUserId() {
		return (java.lang.String) getValue(2);
	}

	/**
	 * Setter for <code>drm.employee_profiles.number</code>.
	 */
	public void setNumber(java.lang.String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>drm.employee_profiles.number</code>.
	 */
	public java.lang.String getNumber() {
		return (java.lang.String) getValue(3);
	}

	/**
	 * Setter for <code>drm.employee_profiles.tolerance</code>.
	 */
	public void setTolerance(java.lang.String value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>drm.employee_profiles.tolerance</code>.
	 */
	public java.lang.String getTolerance() {
		return (java.lang.String) getValue(4);
	}

	/**
	 * Setter for <code>drm.employee_profiles.extraordinary</code>.
	 */
	public void setExtraordinary(java.lang.Boolean value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>drm.employee_profiles.extraordinary</code>.
	 */
	public java.lang.Boolean getExtraordinary() {
		return (java.lang.Boolean) getValue(5);
	}

	/**
	 * Setter for <code>drm.employee_profiles.only_presence</code>.
	 */
	public void setOnlyPresence(java.lang.Boolean value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>drm.employee_profiles.only_presence</code>.
	 */
	public java.lang.Boolean getOnlyPresence() {
		return (java.lang.Boolean) getValue(6);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Record3<java.lang.Integer, java.lang.String, java.lang.String> key() {
		return (org.jooq.Record3) super.key();
	}

	// -------------------------------------------------------------------------
	// Record7 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row7<java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Boolean, java.lang.Boolean> fieldsRow() {
		return (org.jooq.Row7) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row7<java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Boolean, java.lang.Boolean> valuesRow() {
		return (org.jooq.Row7) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field1() {
		return com.sonicle.webtop.drm.jooq.tables.EmployeeProfiles.EMPLOYEE_PROFILES.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field2() {
		return com.sonicle.webtop.drm.jooq.tables.EmployeeProfiles.EMPLOYEE_PROFILES.DOMAIN_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field3() {
		return com.sonicle.webtop.drm.jooq.tables.EmployeeProfiles.EMPLOYEE_PROFILES.USER_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field4() {
		return com.sonicle.webtop.drm.jooq.tables.EmployeeProfiles.EMPLOYEE_PROFILES.NUMBER;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field5() {
		return com.sonicle.webtop.drm.jooq.tables.EmployeeProfiles.EMPLOYEE_PROFILES.TOLERANCE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Boolean> field6() {
		return com.sonicle.webtop.drm.jooq.tables.EmployeeProfiles.EMPLOYEE_PROFILES.EXTRAORDINARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Boolean> field7() {
		return com.sonicle.webtop.drm.jooq.tables.EmployeeProfiles.EMPLOYEE_PROFILES.ONLY_PRESENCE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value1() {
		return getId();
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
	public java.lang.String value3() {
		return getUserId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value4() {
		return getNumber();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value5() {
		return getTolerance();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Boolean value6() {
		return getExtraordinary();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Boolean value7() {
		return getOnlyPresence();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EmployeeProfilesRecord value1(java.lang.Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EmployeeProfilesRecord value2(java.lang.String value) {
		setDomainId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EmployeeProfilesRecord value3(java.lang.String value) {
		setUserId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EmployeeProfilesRecord value4(java.lang.String value) {
		setNumber(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EmployeeProfilesRecord value5(java.lang.String value) {
		setTolerance(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EmployeeProfilesRecord value6(java.lang.Boolean value) {
		setExtraordinary(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EmployeeProfilesRecord value7(java.lang.Boolean value) {
		setOnlyPresence(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EmployeeProfilesRecord values(java.lang.Integer value1, java.lang.String value2, java.lang.String value3, java.lang.String value4, java.lang.String value5, java.lang.Boolean value6, java.lang.Boolean value7) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached EmployeeProfilesRecord
	 */
	public EmployeeProfilesRecord() {
		super(com.sonicle.webtop.drm.jooq.tables.EmployeeProfiles.EMPLOYEE_PROFILES);
	}

	/**
	 * Create a detached, initialised EmployeeProfilesRecord
	 */
	public EmployeeProfilesRecord(java.lang.Integer id, java.lang.String domainId, java.lang.String userId, java.lang.String number, java.lang.String tolerance, java.lang.Boolean extraordinary, java.lang.Boolean onlyPresence) {
		super(com.sonicle.webtop.drm.jooq.tables.EmployeeProfiles.EMPLOYEE_PROFILES);

		setValue(0, id);
		setValue(1, domainId);
		setValue(2, userId);
		setValue(3, number);
		setValue(4, tolerance);
		setValue(5, extraordinary);
		setValue(6, onlyPresence);
	}
}
