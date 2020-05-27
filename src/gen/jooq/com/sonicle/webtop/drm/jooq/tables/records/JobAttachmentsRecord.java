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
public class JobAttachmentsRecord extends org.jooq.impl.UpdatableRecordImpl<com.sonicle.webtop.drm.jooq.tables.records.JobAttachmentsRecord> implements org.jooq.Record7<java.lang.String, java.lang.String, org.joda.time.DateTime, java.lang.Short, java.lang.String, java.lang.Long, java.lang.String> {

	private static final long serialVersionUID = -388903008;

	/**
	 * Setter for <code>drm.job_attachments.job_attachment_id</code>.
	 */
	public void setJobAttachmentId(java.lang.String value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>drm.job_attachments.job_attachment_id</code>.
	 */
	public java.lang.String getJobAttachmentId() {
		return (java.lang.String) getValue(0);
	}

	/**
	 * Setter for <code>drm.job_attachments.job_id</code>.
	 */
	public void setJobId(java.lang.String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>drm.job_attachments.job_id</code>.
	 */
	public java.lang.String getJobId() {
		return (java.lang.String) getValue(1);
	}

	/**
	 * Setter for <code>drm.job_attachments.revision_timestamp</code>.
	 */
	public void setRevisionTimestamp(org.joda.time.DateTime value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>drm.job_attachments.revision_timestamp</code>.
	 */
	public org.joda.time.DateTime getRevisionTimestamp() {
		return (org.joda.time.DateTime) getValue(2);
	}

	/**
	 * Setter for <code>drm.job_attachments.revision_sequence</code>.
	 */
	public void setRevisionSequence(java.lang.Short value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>drm.job_attachments.revision_sequence</code>.
	 */
	public java.lang.Short getRevisionSequence() {
		return (java.lang.Short) getValue(3);
	}

	/**
	 * Setter for <code>drm.job_attachments.filename</code>.
	 */
	public void setFilename(java.lang.String value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>drm.job_attachments.filename</code>.
	 */
	public java.lang.String getFilename() {
		return (java.lang.String) getValue(4);
	}

	/**
	 * Setter for <code>drm.job_attachments.size</code>.
	 */
	public void setSize(java.lang.Long value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>drm.job_attachments.size</code>.
	 */
	public java.lang.Long getSize() {
		return (java.lang.Long) getValue(5);
	}

	/**
	 * Setter for <code>drm.job_attachments.media_type</code>.
	 */
	public void setMediaType(java.lang.String value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>drm.job_attachments.media_type</code>.
	 */
	public java.lang.String getMediaType() {
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
	public org.jooq.Row7<java.lang.String, java.lang.String, org.joda.time.DateTime, java.lang.Short, java.lang.String, java.lang.Long, java.lang.String> fieldsRow() {
		return (org.jooq.Row7) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row7<java.lang.String, java.lang.String, org.joda.time.DateTime, java.lang.Short, java.lang.String, java.lang.Long, java.lang.String> valuesRow() {
		return (org.jooq.Row7) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field1() {
		return com.sonicle.webtop.drm.jooq.tables.JobAttachments.JOB_ATTACHMENTS.JOB_ATTACHMENT_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field2() {
		return com.sonicle.webtop.drm.jooq.tables.JobAttachments.JOB_ATTACHMENTS.JOB_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<org.joda.time.DateTime> field3() {
		return com.sonicle.webtop.drm.jooq.tables.JobAttachments.JOB_ATTACHMENTS.REVISION_TIMESTAMP;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Short> field4() {
		return com.sonicle.webtop.drm.jooq.tables.JobAttachments.JOB_ATTACHMENTS.REVISION_SEQUENCE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field5() {
		return com.sonicle.webtop.drm.jooq.tables.JobAttachments.JOB_ATTACHMENTS.FILENAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Long> field6() {
		return com.sonicle.webtop.drm.jooq.tables.JobAttachments.JOB_ATTACHMENTS.SIZE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field7() {
		return com.sonicle.webtop.drm.jooq.tables.JobAttachments.JOB_ATTACHMENTS.MEDIA_TYPE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value1() {
		return getJobAttachmentId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value2() {
		return getJobId();
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
	public java.lang.Long value6() {
		return getSize();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value7() {
		return getMediaType();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JobAttachmentsRecord value1(java.lang.String value) {
		setJobAttachmentId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JobAttachmentsRecord value2(java.lang.String value) {
		setJobId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JobAttachmentsRecord value3(org.joda.time.DateTime value) {
		setRevisionTimestamp(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JobAttachmentsRecord value4(java.lang.Short value) {
		setRevisionSequence(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JobAttachmentsRecord value5(java.lang.String value) {
		setFilename(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JobAttachmentsRecord value6(java.lang.Long value) {
		setSize(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JobAttachmentsRecord value7(java.lang.String value) {
		setMediaType(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JobAttachmentsRecord values(java.lang.String value1, java.lang.String value2, org.joda.time.DateTime value3, java.lang.Short value4, java.lang.String value5, java.lang.Long value6, java.lang.String value7) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached JobAttachmentsRecord
	 */
	public JobAttachmentsRecord() {
		super(com.sonicle.webtop.drm.jooq.tables.JobAttachments.JOB_ATTACHMENTS);
	}

	/**
	 * Create a detached, initialised JobAttachmentsRecord
	 */
	public JobAttachmentsRecord(java.lang.String jobAttachmentId, java.lang.String jobId, org.joda.time.DateTime revisionTimestamp, java.lang.Short revisionSequence, java.lang.String filename, java.lang.Long size, java.lang.String mediaType) {
		super(com.sonicle.webtop.drm.jooq.tables.JobAttachments.JOB_ATTACHMENTS);

		setValue(0, jobAttachmentId);
		setValue(1, jobId);
		setValue(2, revisionTimestamp);
		setValue(3, revisionSequence);
		setValue(4, filename);
		setValue(5, size);
		setValue(6, mediaType);
	}
}
