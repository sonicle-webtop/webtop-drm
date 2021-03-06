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
public class OpportunityDocumentsRecord extends org.jooq.impl.UpdatableRecordImpl<com.sonicle.webtop.drm.jooq.tables.records.OpportunityDocumentsRecord> implements org.jooq.Record7<java.lang.String, java.lang.Integer, org.joda.time.DateTime, java.lang.Short, java.lang.String, java.lang.Long, java.lang.String> {

	private static final long serialVersionUID = 1197930011;

	/**
	 * Setter for <code>drm.opportunity_documents.id</code>.
	 */
	public void setId(java.lang.String value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>drm.opportunity_documents.id</code>.
	 */
	public java.lang.String getId() {
		return (java.lang.String) getValue(0);
	}

	/**
	 * Setter for <code>drm.opportunity_documents.opportunity_id</code>.
	 */
	public void setOpportunityId(java.lang.Integer value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>drm.opportunity_documents.opportunity_id</code>.
	 */
	public java.lang.Integer getOpportunityId() {
		return (java.lang.Integer) getValue(1);
	}

	/**
	 * Setter for <code>drm.opportunity_documents.revision_timestamp</code>.
	 */
	public void setRevisionTimestamp(org.joda.time.DateTime value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>drm.opportunity_documents.revision_timestamp</code>.
	 */
	public org.joda.time.DateTime getRevisionTimestamp() {
		return (org.joda.time.DateTime) getValue(2);
	}

	/**
	 * Setter for <code>drm.opportunity_documents.revision_sequence</code>.
	 */
	public void setRevisionSequence(java.lang.Short value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>drm.opportunity_documents.revision_sequence</code>.
	 */
	public java.lang.Short getRevisionSequence() {
		return (java.lang.Short) getValue(3);
	}

	/**
	 * Setter for <code>drm.opportunity_documents.filename</code>.
	 */
	public void setFilename(java.lang.String value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>drm.opportunity_documents.filename</code>.
	 */
	public java.lang.String getFilename() {
		return (java.lang.String) getValue(4);
	}

	/**
	 * Setter for <code>drm.opportunity_documents.size</code>.
	 */
	public void setSize(java.lang.Long value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>drm.opportunity_documents.size</code>.
	 */
	public java.lang.Long getSize() {
		return (java.lang.Long) getValue(5);
	}

	/**
	 * Setter for <code>drm.opportunity_documents.media_type</code>.
	 */
	public void setMediaType(java.lang.String value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>drm.opportunity_documents.media_type</code>.
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
	public org.jooq.Row7<java.lang.String, java.lang.Integer, org.joda.time.DateTime, java.lang.Short, java.lang.String, java.lang.Long, java.lang.String> fieldsRow() {
		return (org.jooq.Row7) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row7<java.lang.String, java.lang.Integer, org.joda.time.DateTime, java.lang.Short, java.lang.String, java.lang.Long, java.lang.String> valuesRow() {
		return (org.jooq.Row7) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field1() {
		return com.sonicle.webtop.drm.jooq.tables.OpportunityDocuments.OPPORTUNITY_DOCUMENTS.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field2() {
		return com.sonicle.webtop.drm.jooq.tables.OpportunityDocuments.OPPORTUNITY_DOCUMENTS.OPPORTUNITY_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<org.joda.time.DateTime> field3() {
		return com.sonicle.webtop.drm.jooq.tables.OpportunityDocuments.OPPORTUNITY_DOCUMENTS.REVISION_TIMESTAMP;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Short> field4() {
		return com.sonicle.webtop.drm.jooq.tables.OpportunityDocuments.OPPORTUNITY_DOCUMENTS.REVISION_SEQUENCE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field5() {
		return com.sonicle.webtop.drm.jooq.tables.OpportunityDocuments.OPPORTUNITY_DOCUMENTS.FILENAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Long> field6() {
		return com.sonicle.webtop.drm.jooq.tables.OpportunityDocuments.OPPORTUNITY_DOCUMENTS.SIZE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field7() {
		return com.sonicle.webtop.drm.jooq.tables.OpportunityDocuments.OPPORTUNITY_DOCUMENTS.MEDIA_TYPE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value1() {
		return getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value2() {
		return getOpportunityId();
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
	public OpportunityDocumentsRecord value1(java.lang.String value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OpportunityDocumentsRecord value2(java.lang.Integer value) {
		setOpportunityId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OpportunityDocumentsRecord value3(org.joda.time.DateTime value) {
		setRevisionTimestamp(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OpportunityDocumentsRecord value4(java.lang.Short value) {
		setRevisionSequence(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OpportunityDocumentsRecord value5(java.lang.String value) {
		setFilename(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OpportunityDocumentsRecord value6(java.lang.Long value) {
		setSize(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OpportunityDocumentsRecord value7(java.lang.String value) {
		setMediaType(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OpportunityDocumentsRecord values(java.lang.String value1, java.lang.Integer value2, org.joda.time.DateTime value3, java.lang.Short value4, java.lang.String value5, java.lang.Long value6, java.lang.String value7) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached OpportunityDocumentsRecord
	 */
	public OpportunityDocumentsRecord() {
		super(com.sonicle.webtop.drm.jooq.tables.OpportunityDocuments.OPPORTUNITY_DOCUMENTS);
	}

	/**
	 * Create a detached, initialised OpportunityDocumentsRecord
	 */
	public OpportunityDocumentsRecord(java.lang.String id, java.lang.Integer opportunityId, org.joda.time.DateTime revisionTimestamp, java.lang.Short revisionSequence, java.lang.String filename, java.lang.Long size, java.lang.String mediaType) {
		super(com.sonicle.webtop.drm.jooq.tables.OpportunityDocuments.OPPORTUNITY_DOCUMENTS);

		setValue(0, id);
		setValue(1, opportunityId);
		setValue(2, revisionTimestamp);
		setValue(3, revisionSequence);
		setValue(4, filename);
		setValue(5, size);
		setValue(6, mediaType);
	}
}
