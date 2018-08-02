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
public class OpportunityActionInterlocutorsRecord extends org.jooq.impl.UpdatableRecordImpl<com.sonicle.webtop.drm.jooq.tables.records.OpportunityActionInterlocutorsRecord> implements org.jooq.Record3<java.lang.Integer, java.lang.Integer, java.lang.Integer> {

	private static final long serialVersionUID = -996133745;

	/**
	 * Setter for <code>drm.opportunity_action_interlocutors.id</code>.
	 */
	public void setId(java.lang.Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>drm.opportunity_action_interlocutors.id</code>.
	 */
	public java.lang.Integer getId() {
		return (java.lang.Integer) getValue(0);
	}

	/**
	 * Setter for <code>drm.opportunity_action_interlocutors.opportunity_action_id</code>.
	 */
	public void setOpportunityActionId(java.lang.Integer value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>drm.opportunity_action_interlocutors.opportunity_action_id</code>.
	 */
	public java.lang.Integer getOpportunityActionId() {
		return (java.lang.Integer) getValue(1);
	}

	/**
	 * Setter for <code>drm.opportunity_action_interlocutors.contact_id</code>.
	 */
	public void setContactId(java.lang.Integer value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>drm.opportunity_action_interlocutors.contact_id</code>.
	 */
	public java.lang.Integer getContactId() {
		return (java.lang.Integer) getValue(2);
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
	// Record3 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row3<java.lang.Integer, java.lang.Integer, java.lang.Integer> fieldsRow() {
		return (org.jooq.Row3) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row3<java.lang.Integer, java.lang.Integer, java.lang.Integer> valuesRow() {
		return (org.jooq.Row3) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field1() {
		return com.sonicle.webtop.drm.jooq.tables.OpportunityActionInterlocutors.OPPORTUNITY_ACTION_INTERLOCUTORS.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field2() {
		return com.sonicle.webtop.drm.jooq.tables.OpportunityActionInterlocutors.OPPORTUNITY_ACTION_INTERLOCUTORS.OPPORTUNITY_ACTION_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field3() {
		return com.sonicle.webtop.drm.jooq.tables.OpportunityActionInterlocutors.OPPORTUNITY_ACTION_INTERLOCUTORS.CONTACT_ID;
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
	public java.lang.Integer value2() {
		return getOpportunityActionId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value3() {
		return getContactId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OpportunityActionInterlocutorsRecord value1(java.lang.Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OpportunityActionInterlocutorsRecord value2(java.lang.Integer value) {
		setOpportunityActionId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OpportunityActionInterlocutorsRecord value3(java.lang.Integer value) {
		setContactId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OpportunityActionInterlocutorsRecord values(java.lang.Integer value1, java.lang.Integer value2, java.lang.Integer value3) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached OpportunityActionInterlocutorsRecord
	 */
	public OpportunityActionInterlocutorsRecord() {
		super(com.sonicle.webtop.drm.jooq.tables.OpportunityActionInterlocutors.OPPORTUNITY_ACTION_INTERLOCUTORS);
	}

	/**
	 * Create a detached, initialised OpportunityActionInterlocutorsRecord
	 */
	public OpportunityActionInterlocutorsRecord(java.lang.Integer id, java.lang.Integer opportunityActionId, java.lang.Integer contactId) {
		super(com.sonicle.webtop.drm.jooq.tables.OpportunityActionInterlocutors.OPPORTUNITY_ACTION_INTERLOCUTORS);

		setValue(0, id);
		setValue(1, opportunityActionId);
		setValue(2, contactId);
	}
}
