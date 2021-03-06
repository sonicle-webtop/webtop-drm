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
public class OpportunityActionInterlocutors extends org.jooq.impl.TableImpl<com.sonicle.webtop.drm.jooq.tables.records.OpportunityActionInterlocutorsRecord> {

	private static final long serialVersionUID = 1917945640;

	/**
	 * The reference instance of <code>drm.opportunity_action_interlocutors</code>
	 */
	public static final com.sonicle.webtop.drm.jooq.tables.OpportunityActionInterlocutors OPPORTUNITY_ACTION_INTERLOCUTORS = new com.sonicle.webtop.drm.jooq.tables.OpportunityActionInterlocutors();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<com.sonicle.webtop.drm.jooq.tables.records.OpportunityActionInterlocutorsRecord> getRecordType() {
		return com.sonicle.webtop.drm.jooq.tables.records.OpportunityActionInterlocutorsRecord.class;
	}

	/**
	 * The column <code>drm.opportunity_action_interlocutors.id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.OpportunityActionInterlocutorsRecord, java.lang.Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>drm.opportunity_action_interlocutors.opportunity_action_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.OpportunityActionInterlocutorsRecord, java.lang.Integer> OPPORTUNITY_ACTION_ID = createField("opportunity_action_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>drm.opportunity_action_interlocutors.contact_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.OpportunityActionInterlocutorsRecord, java.lang.Integer> CONTACT_ID = createField("contact_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * Create a <code>drm.opportunity_action_interlocutors</code> table reference
	 */
	public OpportunityActionInterlocutors() {
		this("opportunity_action_interlocutors", null);
	}

	/**
	 * Create an aliased <code>drm.opportunity_action_interlocutors</code> table reference
	 */
	public OpportunityActionInterlocutors(java.lang.String alias) {
		this(alias, com.sonicle.webtop.drm.jooq.tables.OpportunityActionInterlocutors.OPPORTUNITY_ACTION_INTERLOCUTORS);
	}

	private OpportunityActionInterlocutors(java.lang.String alias, org.jooq.Table<com.sonicle.webtop.drm.jooq.tables.records.OpportunityActionInterlocutorsRecord> aliased) {
		this(alias, aliased, null);
	}

	private OpportunityActionInterlocutors(java.lang.String alias, org.jooq.Table<com.sonicle.webtop.drm.jooq.tables.records.OpportunityActionInterlocutorsRecord> aliased, org.jooq.Field<?>[] parameters) {
		super(alias, com.sonicle.webtop.drm.jooq.Drm.DRM, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.OpportunityActionInterlocutorsRecord> getPrimaryKey() {
		return com.sonicle.webtop.drm.jooq.Keys.OPPORTUNITY_ACTION_INTERLOCUTORS_PKEY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.OpportunityActionInterlocutorsRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.OpportunityActionInterlocutorsRecord>>asList(com.sonicle.webtop.drm.jooq.Keys.OPPORTUNITY_ACTION_INTERLOCUTORS_PKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public com.sonicle.webtop.drm.jooq.tables.OpportunityActionInterlocutors as(java.lang.String alias) {
		return new com.sonicle.webtop.drm.jooq.tables.OpportunityActionInterlocutors(alias, this);
	}

	/**
	 * Rename this table
	 */
	public com.sonicle.webtop.drm.jooq.tables.OpportunityActionInterlocutors rename(java.lang.String name) {
		return new com.sonicle.webtop.drm.jooq.tables.OpportunityActionInterlocutors(name, null);
	}
}
