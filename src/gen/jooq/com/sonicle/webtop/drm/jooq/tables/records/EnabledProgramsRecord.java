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
public class EnabledProgramsRecord extends org.jooq.impl.UpdatableRecordImpl<com.sonicle.webtop.drm.jooq.tables.records.EnabledProgramsRecord> implements org.jooq.Record5<java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Boolean> {

	private static final long serialVersionUID = 1326979752;

	/**
	 * Setter for <code>drm.enabled_programs.domain_id</code>.
	 */
	public void setDomainId(java.lang.String value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>drm.enabled_programs.domain_id</code>.
	 */
	public java.lang.String getDomainId() {
		return (java.lang.String) getValue(0);
	}

	/**
	 * Setter for <code>drm.enabled_programs.group_id</code>.
	 */
	public void setGroupId(java.lang.String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>drm.enabled_programs.group_id</code>.
	 */
	public java.lang.String getGroupId() {
		return (java.lang.String) getValue(1);
	}

	/**
	 * Setter for <code>drm.enabled_programs.program_id</code>.
	 */
	public void setProgramId(java.lang.String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>drm.enabled_programs.program_id</code>.
	 */
	public java.lang.String getProgramId() {
		return (java.lang.String) getValue(2);
	}

	/**
	 * Setter for <code>drm.enabled_programs.parent_id</code>.
	 */
	public void setParentId(java.lang.String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>drm.enabled_programs.parent_id</code>.
	 */
	public java.lang.String getParentId() {
		return (java.lang.String) getValue(3);
	}

	/**
	 * Setter for <code>drm.enabled_programs.read_only</code>.
	 */
	public void setReadOnly(java.lang.Boolean value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>drm.enabled_programs.read_only</code>.
	 */
	public java.lang.Boolean getReadOnly() {
		return (java.lang.Boolean) getValue(4);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Record2<java.lang.String, java.lang.String> key() {
		return (org.jooq.Record2) super.key();
	}

	// -------------------------------------------------------------------------
	// Record5 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row5<java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Boolean> fieldsRow() {
		return (org.jooq.Row5) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row5<java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Boolean> valuesRow() {
		return (org.jooq.Row5) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field1() {
		return com.sonicle.webtop.drm.jooq.tables.EnabledPrograms.ENABLED_PROGRAMS.DOMAIN_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field2() {
		return com.sonicle.webtop.drm.jooq.tables.EnabledPrograms.ENABLED_PROGRAMS.GROUP_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field3() {
		return com.sonicle.webtop.drm.jooq.tables.EnabledPrograms.ENABLED_PROGRAMS.PROGRAM_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field4() {
		return com.sonicle.webtop.drm.jooq.tables.EnabledPrograms.ENABLED_PROGRAMS.PARENT_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Boolean> field5() {
		return com.sonicle.webtop.drm.jooq.tables.EnabledPrograms.ENABLED_PROGRAMS.READ_ONLY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value1() {
		return getDomainId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value2() {
		return getGroupId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value3() {
		return getProgramId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value4() {
		return getParentId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Boolean value5() {
		return getReadOnly();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EnabledProgramsRecord value1(java.lang.String value) {
		setDomainId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EnabledProgramsRecord value2(java.lang.String value) {
		setGroupId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EnabledProgramsRecord value3(java.lang.String value) {
		setProgramId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EnabledProgramsRecord value4(java.lang.String value) {
		setParentId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EnabledProgramsRecord value5(java.lang.Boolean value) {
		setReadOnly(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EnabledProgramsRecord values(java.lang.String value1, java.lang.String value2, java.lang.String value3, java.lang.String value4, java.lang.Boolean value5) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached EnabledProgramsRecord
	 */
	public EnabledProgramsRecord() {
		super(com.sonicle.webtop.drm.jooq.tables.EnabledPrograms.ENABLED_PROGRAMS);
	}

	/**
	 * Create a detached, initialised EnabledProgramsRecord
	 */
	public EnabledProgramsRecord(java.lang.String domainId, java.lang.String groupId, java.lang.String programId, java.lang.String parentId, java.lang.Boolean readOnly) {
		super(com.sonicle.webtop.drm.jooq.tables.EnabledPrograms.ENABLED_PROGRAMS);

		setValue(0, domainId);
		setValue(1, groupId);
		setValue(2, programId);
		setValue(3, parentId);
		setValue(4, readOnly);
	}
}