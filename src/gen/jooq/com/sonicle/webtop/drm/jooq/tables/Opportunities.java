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
public class Opportunities extends org.jooq.impl.TableImpl<com.sonicle.webtop.drm.jooq.tables.records.OpportunitiesRecord> {

	private static final long serialVersionUID = 2060075116;

	/**
	 * The reference instance of <code>drm.opportunities</code>
	 */
	public static final com.sonicle.webtop.drm.jooq.tables.Opportunities OPPORTUNITIES = new com.sonicle.webtop.drm.jooq.tables.Opportunities();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<com.sonicle.webtop.drm.jooq.tables.records.OpportunitiesRecord> getRecordType() {
		return com.sonicle.webtop.drm.jooq.tables.records.OpportunitiesRecord.class;
	}

	/**
	 * The column <code>drm.opportunities.id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.OpportunitiesRecord, java.lang.Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>drm.opportunities.domain_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.OpportunitiesRecord, java.lang.String> DOMAIN_ID = createField("domain_id", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false), this, "");

	/**
	 * The column <code>drm.opportunities.company_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.OpportunitiesRecord, java.lang.Integer> COMPANY_ID = createField("company_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>drm.opportunities.operator_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.OpportunitiesRecord, java.lang.String> OPERATOR_ID = createField("operator_id", org.jooq.impl.SQLDataType.VARCHAR.length(36).nullable(false), this, "");

	/**
	 * The column <code>drm.opportunities.date</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.OpportunitiesRecord, org.joda.time.LocalDate> DATE = createField("date", org.jooq.impl.SQLDataType.DATE.nullable(false), this, "", new com.sonicle.webtop.core.jooq.LocalDateConverter());

	/**
	 * The column <code>drm.opportunities.from_hour</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.OpportunitiesRecord, java.lang.String> FROM_HOUR = createField("from_hour", org.jooq.impl.SQLDataType.VARCHAR.length(5), this, "");

	/**
	 * The column <code>drm.opportunities.to_hour</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.OpportunitiesRecord, java.lang.String> TO_HOUR = createField("to_hour", org.jooq.impl.SQLDataType.VARCHAR.length(5), this, "");

	/**
	 * The column <code>drm.opportunities.executed_with</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.OpportunitiesRecord, java.lang.String> EXECUTED_WITH = createField("executed_with", org.jooq.impl.SQLDataType.VARCHAR.length(36), this, "");

	/**
	 * The column <code>drm.opportunities.customer_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.OpportunitiesRecord, java.lang.String> CUSTOMER_ID = createField("customer_id", org.jooq.impl.SQLDataType.VARCHAR.length(36), this, "");

	/**
	 * The column <code>drm.opportunities.customer_stat_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.OpportunitiesRecord, java.lang.String> CUSTOMER_STAT_ID = createField("customer_stat_id", org.jooq.impl.SQLDataType.VARCHAR.length(36), this, "");

	/**
	 * The column <code>drm.opportunities.sector</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.OpportunitiesRecord, java.lang.String> SECTOR = createField("sector", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "");

	/**
	 * The column <code>drm.opportunities.description</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.OpportunitiesRecord, java.lang.String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "");

	/**
	 * The column <code>drm.opportunities.place</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.OpportunitiesRecord, java.lang.String> PLACE = createField("place", org.jooq.impl.SQLDataType.VARCHAR.length(36), this, "");

	/**
	 * The column <code>drm.opportunities.objective</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.OpportunitiesRecord, java.lang.String> OBJECTIVE = createField("objective", org.jooq.impl.SQLDataType.CLOB, this, "");

	/**
	 * The column <code>drm.opportunities.causal_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.OpportunitiesRecord, java.lang.Integer> CAUSAL_ID = createField("causal_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>drm.opportunities.activity_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.OpportunitiesRecord, java.lang.Integer> ACTIVITY_ID = createField("activity_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>drm.opportunities.objective_2</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.OpportunitiesRecord, java.lang.String> OBJECTIVE_2 = createField("objective_2", org.jooq.impl.SQLDataType.CLOB, this, "");

	/**
	 * The column <code>drm.opportunities.result</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.OpportunitiesRecord, java.lang.String> RESULT = createField("result", org.jooq.impl.SQLDataType.CLOB, this, "");

	/**
	 * The column <code>drm.opportunities.discoveries</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.OpportunitiesRecord, java.lang.String> DISCOVERIES = createField("discoveries", org.jooq.impl.SQLDataType.CLOB, this, "");

	/**
	 * The column <code>drm.opportunities.customer_potential</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.OpportunitiesRecord, java.lang.String> CUSTOMER_POTENTIAL = createField("customer_potential", org.jooq.impl.SQLDataType.CLOB, this, "");

	/**
	 * The column <code>drm.opportunities.notes</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.OpportunitiesRecord, java.lang.String> NOTES = createField("notes", org.jooq.impl.SQLDataType.CLOB, this, "");

	/**
	 * The column <code>drm.opportunities.status_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.OpportunitiesRecord, java.lang.Integer> STATUS_ID = createField("status_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>drm.opportunities.signed_by</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.OpportunitiesRecord, java.lang.String> SIGNED_BY = createField("signed_by", org.jooq.impl.SQLDataType.VARCHAR.length(36), this, "");

	/**
	 * The column <code>drm.opportunities.signature</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.OpportunitiesRecord, java.lang.Boolean> SIGNATURE = createField("signature", org.jooq.impl.SQLDataType.BOOLEAN, this, "");

	/**
	 * The column <code>drm.opportunities.won</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.OpportunitiesRecord, java.lang.Boolean> WON = createField("won", org.jooq.impl.SQLDataType.BOOLEAN, this, "");

	/**
	 * Create a <code>drm.opportunities</code> table reference
	 */
	public Opportunities() {
		this("opportunities", null);
	}

	/**
	 * Create an aliased <code>drm.opportunities</code> table reference
	 */
	public Opportunities(java.lang.String alias) {
		this(alias, com.sonicle.webtop.drm.jooq.tables.Opportunities.OPPORTUNITIES);
	}

	private Opportunities(java.lang.String alias, org.jooq.Table<com.sonicle.webtop.drm.jooq.tables.records.OpportunitiesRecord> aliased) {
		this(alias, aliased, null);
	}

	private Opportunities(java.lang.String alias, org.jooq.Table<com.sonicle.webtop.drm.jooq.tables.records.OpportunitiesRecord> aliased, org.jooq.Field<?>[] parameters) {
		super(alias, com.sonicle.webtop.drm.jooq.Drm.DRM, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.OpportunitiesRecord> getPrimaryKey() {
		return com.sonicle.webtop.drm.jooq.Keys.OPPORTUNITY_PKEY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.OpportunitiesRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.OpportunitiesRecord>>asList(com.sonicle.webtop.drm.jooq.Keys.OPPORTUNITY_PKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public com.sonicle.webtop.drm.jooq.tables.Opportunities as(java.lang.String alias) {
		return new com.sonicle.webtop.drm.jooq.tables.Opportunities(alias, this);
	}

	/**
	 * Rename this table
	 */
	public com.sonicle.webtop.drm.jooq.tables.Opportunities rename(java.lang.String name) {
		return new com.sonicle.webtop.drm.jooq.tables.Opportunities(name, null);
	}
}
