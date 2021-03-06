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
public class DocStatusesRecord extends org.jooq.impl.UpdatableRecordImpl<com.sonicle.webtop.drm.jooq.tables.records.DocStatusesRecord> implements org.jooq.Record5<java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.Boolean> {

	private static final long serialVersionUID = 1104871803;

	/**
	 * Setter for <code>drm.doc_statuses.doc_status_id</code>.
	 */
	public void setDocStatusId(java.lang.Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>drm.doc_statuses.doc_status_id</code>.
	 */
	public java.lang.Integer getDocStatusId() {
		return (java.lang.Integer) getValue(0);
	}

	/**
	 * Setter for <code>drm.doc_statuses.name</code>.
	 */
	public void setName(java.lang.String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>drm.doc_statuses.name</code>.
	 */
	public java.lang.String getName() {
		return (java.lang.String) getValue(1);
	}

	/**
	 * Setter for <code>drm.doc_statuses.description</code>.
	 */
	public void setDescription(java.lang.String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>drm.doc_statuses.description</code>.
	 */
	public java.lang.String getDescription() {
		return (java.lang.String) getValue(2);
	}

	/**
	 * Setter for <code>drm.doc_statuses.type</code>.
	 */
	public void setType(java.lang.String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>drm.doc_statuses.type</code>.
	 */
	public java.lang.String getType() {
		return (java.lang.String) getValue(3);
	}

	/**
	 * Setter for <code>drm.doc_statuses.built_in</code>.
	 */
	public void setBuiltIn(java.lang.Boolean value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>drm.doc_statuses.built_in</code>.
	 */
	public java.lang.Boolean getBuiltIn() {
		return (java.lang.Boolean) getValue(4);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Record1<java.lang.Integer> key() {
		return (org.jooq.Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record5 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row5<java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.Boolean> fieldsRow() {
		return (org.jooq.Row5) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row5<java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.Boolean> valuesRow() {
		return (org.jooq.Row5) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field1() {
		return com.sonicle.webtop.drm.jooq.tables.DocStatuses.DOC_STATUSES.DOC_STATUS_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field2() {
		return com.sonicle.webtop.drm.jooq.tables.DocStatuses.DOC_STATUSES.NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field3() {
		return com.sonicle.webtop.drm.jooq.tables.DocStatuses.DOC_STATUSES.DESCRIPTION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field4() {
		return com.sonicle.webtop.drm.jooq.tables.DocStatuses.DOC_STATUSES.TYPE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Boolean> field5() {
		return com.sonicle.webtop.drm.jooq.tables.DocStatuses.DOC_STATUSES.BUILT_IN;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value1() {
		return getDocStatusId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value2() {
		return getName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value3() {
		return getDescription();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value4() {
		return getType();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Boolean value5() {
		return getBuiltIn();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DocStatusesRecord value1(java.lang.Integer value) {
		setDocStatusId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DocStatusesRecord value2(java.lang.String value) {
		setName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DocStatusesRecord value3(java.lang.String value) {
		setDescription(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DocStatusesRecord value4(java.lang.String value) {
		setType(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DocStatusesRecord value5(java.lang.Boolean value) {
		setBuiltIn(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DocStatusesRecord values(java.lang.Integer value1, java.lang.String value2, java.lang.String value3, java.lang.String value4, java.lang.Boolean value5) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached DocStatusesRecord
	 */
	public DocStatusesRecord() {
		super(com.sonicle.webtop.drm.jooq.tables.DocStatuses.DOC_STATUSES);
	}

	/**
	 * Create a detached, initialised DocStatusesRecord
	 */
	public DocStatusesRecord(java.lang.Integer docStatusId, java.lang.String name, java.lang.String description, java.lang.String type, java.lang.Boolean builtIn) {
		super(com.sonicle.webtop.drm.jooq.tables.DocStatuses.DOC_STATUSES);

		setValue(0, docStatusId);
		setValue(1, name);
		setValue(2, description);
		setValue(3, type);
		setValue(4, builtIn);
	}
}
