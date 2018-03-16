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
public class LeaveRequestDocumentsRecord extends org.jooq.impl.UpdatableRecordImpl<com.sonicle.webtop.drm.jooq.tables.records.LeaveRequestDocumentsRecord> implements org.jooq.Record7<java.lang.String, java.lang.Integer, org.joda.time.DateTime, java.lang.Short, java.lang.String, java.lang.Integer, java.lang.String> {

	private static final long serialVersionUID = -2145036950;

	/**
	 * Setter for <code>drm.leave_request_documents.leave_request_document_id</code>.
	 */
	public void setLeaveRequestDocumentId(java.lang.String value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>drm.leave_request_documents.leave_request_document_id</code>.
	 */
	public java.lang.String getLeaveRequestDocumentId() {
		return (java.lang.String) getValue(0);
	}

	/**
	 * Setter for <code>drm.leave_request_documents.leave_request_id</code>.
	 */
	public void setLeaveRequestId(java.lang.Integer value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>drm.leave_request_documents.leave_request_id</code>.
	 */
	public java.lang.Integer getLeaveRequestId() {
		return (java.lang.Integer) getValue(1);
	}

	/**
	 * Setter for <code>drm.leave_request_documents.revision_timestamp</code>.
	 */
	public void setRevisionTimestamp(org.joda.time.DateTime value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>drm.leave_request_documents.revision_timestamp</code>.
	 */
	public org.joda.time.DateTime getRevisionTimestamp() {
		return (org.joda.time.DateTime) getValue(2);
	}

	/**
	 * Setter for <code>drm.leave_request_documents.revision_sequence</code>.
	 */
	public void setRevisionSequence(java.lang.Short value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>drm.leave_request_documents.revision_sequence</code>.
	 */
	public java.lang.Short getRevisionSequence() {
		return (java.lang.Short) getValue(3);
	}

	/**
	 * Setter for <code>drm.leave_request_documents.filename</code>.
	 */
	public void setFilename(java.lang.String value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>drm.leave_request_documents.filename</code>.
	 */
	public java.lang.String getFilename() {
		return (java.lang.String) getValue(4);
	}

	/**
	 * Setter for <code>drm.leave_request_documents.size</code>.
	 */
	public void setSize(java.lang.Integer value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>drm.leave_request_documents.size</code>.
	 */
	public java.lang.Integer getSize() {
		return (java.lang.Integer) getValue(5);
	}

	/**
	 * Setter for <code>drm.leave_request_documents.media_tpye</code>.
	 */
	public void setMediaTpye(java.lang.String value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>drm.leave_request_documents.media_tpye</code>.
	 */
	public java.lang.String getMediaTpye() {
		return (java.lang.String) getValue(6);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Record1<java.lang.String> key() {
		return (org.jooq.Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record7 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row7<java.lang.String, java.lang.Integer, org.joda.time.DateTime, java.lang.Short, java.lang.String, java.lang.Integer, java.lang.String> fieldsRow() {
		return (org.jooq.Row7) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row7<java.lang.String, java.lang.Integer, org.joda.time.DateTime, java.lang.Short, java.lang.String, java.lang.Integer, java.lang.String> valuesRow() {
		return (org.jooq.Row7) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field1() {
		return com.sonicle.webtop.drm.jooq.tables.LeaveRequestDocuments.LEAVE_REQUEST_DOCUMENTS.LEAVE_REQUEST_DOCUMENT_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field2() {
		return com.sonicle.webtop.drm.jooq.tables.LeaveRequestDocuments.LEAVE_REQUEST_DOCUMENTS.LEAVE_REQUEST_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<org.joda.time.DateTime> field3() {
		return com.sonicle.webtop.drm.jooq.tables.LeaveRequestDocuments.LEAVE_REQUEST_DOCUMENTS.REVISION_TIMESTAMP;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Short> field4() {
		return com.sonicle.webtop.drm.jooq.tables.LeaveRequestDocuments.LEAVE_REQUEST_DOCUMENTS.REVISION_SEQUENCE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field5() {
		return com.sonicle.webtop.drm.jooq.tables.LeaveRequestDocuments.LEAVE_REQUEST_DOCUMENTS.FILENAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field6() {
		return com.sonicle.webtop.drm.jooq.tables.LeaveRequestDocuments.LEAVE_REQUEST_DOCUMENTS.SIZE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field7() {
		return com.sonicle.webtop.drm.jooq.tables.LeaveRequestDocuments.LEAVE_REQUEST_DOCUMENTS.MEDIA_TPYE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value1() {
		return getLeaveRequestDocumentId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value2() {
		return getLeaveRequestId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.joda.time.DateTime value3() {
		return getRevisionTimestamp();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Short value4() {
		return getRevisionSequence();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value5() {
		return getFilename();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value6() {
		return getSize();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value7() {
		return getMediaTpye();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LeaveRequestDocumentsRecord value1(java.lang.String value) {
		setLeaveRequestDocumentId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LeaveRequestDocumentsRecord value2(java.lang.Integer value) {
		setLeaveRequestId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LeaveRequestDocumentsRecord value3(org.joda.time.DateTime value) {
		setRevisionTimestamp(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LeaveRequestDocumentsRecord value4(java.lang.Short value) {
		setRevisionSequence(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LeaveRequestDocumentsRecord value5(java.lang.String value) {
		setFilename(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LeaveRequestDocumentsRecord value6(java.lang.Integer value) {
		setSize(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LeaveRequestDocumentsRecord value7(java.lang.String value) {
		setMediaTpye(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LeaveRequestDocumentsRecord values(java.lang.String value1, java.lang.Integer value2, org.joda.time.DateTime value3, java.lang.Short value4, java.lang.String value5, java.lang.Integer value6, java.lang.String value7) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached LeaveRequestDocumentsRecord
	 */
	public LeaveRequestDocumentsRecord() {
		super(com.sonicle.webtop.drm.jooq.tables.LeaveRequestDocuments.LEAVE_REQUEST_DOCUMENTS);
	}

	/**
	 * Create a detached, initialised LeaveRequestDocumentsRecord
	 */
	public LeaveRequestDocumentsRecord(java.lang.String leaveRequestDocumentId, java.lang.Integer leaveRequestId, org.joda.time.DateTime revisionTimestamp, java.lang.Short revisionSequence, java.lang.String filename, java.lang.Integer size, java.lang.String mediaTpye) {
		super(com.sonicle.webtop.drm.jooq.tables.LeaveRequestDocuments.LEAVE_REQUEST_DOCUMENTS);

		setValue(0, leaveRequestDocumentId);
		setValue(1, leaveRequestId);
		setValue(2, revisionTimestamp);
		setValue(3, revisionSequence);
		setValue(4, filename);
		setValue(5, size);
		setValue(6, mediaTpye);
	}
}
