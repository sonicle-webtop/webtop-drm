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
public class OpportunityActions extends org.jooq.impl.TableImpl<com.sonicle.webtop.drm.jooq.tables.records.OpportunityActionsRecord> {

	private static final long serialVersionUID = -1713872418;

	/**
	 * The reference instance of <code>drm.opportunity_actions</code>
	 */
	public static final com.sonicle.webtop.drm.jooq.tables.OpportunityActions OPPORTUNITY_ACTIONS = new com.sonicle.webtop.drm.jooq.tables.OpportunityActions();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<com.sonicle.webtop.drm.jooq.tables.records.OpportunityActionsRecord> getRecordType() {
		return com.sonicle.webtop.drm.jooq.tables.records.OpportunityActionsRecord.class;
	}

	/**
	 * The column <code>drm.opportunity_actions.id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.OpportunityActionsRecord, java.lang.Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>drm.opportunity_actions.opportunity_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.OpportunityActionsRecord, java.lang.Integer> OPPORTUNITY_ID = createField("opportunity_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>drm.opportunity_actions.operator_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.OpportunityActionsRecord, java.lang.String> OPERATOR_ID = createField("operator_id", org.jooq.impl.SQLDataType.VARCHAR.length(36).nullable(false), this, "");

	/**
	 * The column <code>drm.opportunity_actions.status_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.OpportunityActionsRecord, java.lang.Integer> STATUS_ID = createField("status_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>drm.opportunity_actions.description</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.OpportunityActionsRecord, java.lang.String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "");

	/**
	 * The column <code>drm.opportunity_actions.place</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.OpportunityActionsRecord, java.lang.String> PLACE = createField("place", org.jooq.impl.SQLDataType.VARCHAR.length(36), this, "");

	/**
	 * The column <code>drm.opportunity_actions.subsequent_actions</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.OpportunityActionsRecord, java.lang.String> SUBSEQUENT_ACTIONS = createField("subsequent_actions", org.jooq.impl.SQLDataType.CLOB, this, "");

	/**
	 * The column <code>drm.opportunity_actions.activity_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.OpportunityActionsRecord, java.lang.Integer> ACTIVITY_ID = createField("activity_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>drm.opportunity_actions.event_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.OpportunityActionsRecord, java.lang.Integer> EVENT_ID = createField("event_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>drm.opportunity_actions.start_date</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.OpportunityActionsRecord, org.joda.time.DateTime> START_DATE = createField("start_date", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "", new com.sonicle.webtop.core.jooq.DateTimeConverter());

	/**
	 * The column <code>drm.opportunity_actions.end_date</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.OpportunityActionsRecord, org.joda.time.DateTime> END_DATE = createField("end_date", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "", new com.sonicle.webtop.core.jooq.DateTimeConverter());

	/**
	 * Create a <code>drm.opportunity_actions</code> table reference
	 */
	public OpportunityActions() {
		this("opportunity_actions", null);
	}

	/**
	 * Create an aliased <code>drm.opportunity_actions</code> table reference
	 */
	public OpportunityActions(java.lang.String alias) {
		this(alias, com.sonicle.webtop.drm.jooq.tables.OpportunityActions.OPPORTUNITY_ACTIONS);
	}

	private OpportunityActions(java.lang.String alias, org.jooq.Table<com.sonicle.webtop.drm.jooq.tables.records.OpportunityActionsRecord> aliased) {
		this(alias, aliased, null);
	}

	private OpportunityActions(java.lang.String alias, org.jooq.Table<com.sonicle.webtop.drm.jooq.tables.records.OpportunityActionsRecord> aliased, org.jooq.Field<?>[] parameters) {
		super(alias, com.sonicle.webtop.drm.jooq.Drm.DRM, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.OpportunityActionsRecord> getPrimaryKey() {
		return com.sonicle.webtop.drm.jooq.Keys.OPPORTUNITY_ACTIONS_PKEY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.OpportunityActionsRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.OpportunityActionsRecord>>asList(com.sonicle.webtop.drm.jooq.Keys.OPPORTUNITY_ACTIONS_PKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public com.sonicle.webtop.drm.jooq.tables.OpportunityActions as(java.lang.String alias) {
		return new com.sonicle.webtop.drm.jooq.tables.OpportunityActions(alias, this);
	}

	/**
	 * Rename this table
	 */
	public com.sonicle.webtop.drm.jooq.tables.OpportunityActions rename(java.lang.String name) {
		return new com.sonicle.webtop.drm.jooq.tables.OpportunityActions(name, null);
	}
}
