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
public class VwWorkReports extends org.jooq.impl.TableImpl<com.sonicle.webtop.drm.jooq.tables.records.VwWorkReportsRecord> {

	private static final long serialVersionUID = 339628362;

	/**
	 * The reference instance of <code>drm.vw_work_reports</code>
	 */
	public static final com.sonicle.webtop.drm.jooq.tables.VwWorkReports VW_WORK_REPORTS = new com.sonicle.webtop.drm.jooq.tables.VwWorkReports();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<com.sonicle.webtop.drm.jooq.tables.records.VwWorkReportsRecord> getRecordType() {
		return com.sonicle.webtop.drm.jooq.tables.records.VwWorkReportsRecord.class;
	}

	/**
	 * The column <code>drm.vw_work_reports.work_report_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.VwWorkReportsRecord, java.lang.String> WORK_REPORT_ID = createField("work_report_id", org.jooq.impl.SQLDataType.VARCHAR.length(36), this, "");

	/**
	 * The column <code>drm.vw_work_reports.domain_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.VwWorkReportsRecord, java.lang.String> DOMAIN_ID = createField("domain_id", org.jooq.impl.SQLDataType.VARCHAR.length(20), this, "");

	/**
	 * The column <code>drm.vw_work_reports.number</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.VwWorkReportsRecord, java.lang.Integer> NUMBER = createField("number", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>drm.vw_work_reports.year</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.VwWorkReportsRecord, java.lang.Integer> YEAR = createField("year", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>drm.vw_work_reports.reference_no</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.VwWorkReportsRecord, java.lang.String> REFERENCE_NO = createField("reference_no", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "");

	/**
	 * The column <code>drm.vw_work_reports.operator_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.VwWorkReportsRecord, java.lang.String> OPERATOR_ID = createField("operator_id", org.jooq.impl.SQLDataType.VARCHAR.length(36), this, "");

	/**
	 * The column <code>drm.vw_work_reports.operator_description</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.VwWorkReportsRecord, java.lang.String> OPERATOR_DESCRIPTION = createField("operator_description", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "");

	/**
	 * The column <code>drm.vw_work_reports.customer_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.VwWorkReportsRecord, java.lang.String> CUSTOMER_ID = createField("customer_id", org.jooq.impl.SQLDataType.VARCHAR.length(36), this, "");

	/**
	 * The column <code>drm.vw_work_reports.customer_description</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.VwWorkReportsRecord, java.lang.String> CUSTOMER_DESCRIPTION = createField("customer_description", org.jooq.impl.SQLDataType.VARCHAR.length(50), this, "");

	/**
	 * The column <code>drm.vw_work_reports.customer_stat_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.VwWorkReportsRecord, java.lang.String> CUSTOMER_STAT_ID = createField("customer_stat_id", org.jooq.impl.SQLDataType.VARCHAR.length(36), this, "");

	/**
	 * The column <code>drm.vw_work_reports.customer_stat_description</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.VwWorkReportsRecord, java.lang.String> CUSTOMER_STAT_DESCRIPTION = createField("customer_stat_description", org.jooq.impl.SQLDataType.VARCHAR.length(50), this, "");

	/**
	 * The column <code>drm.vw_work_reports.causal_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.VwWorkReportsRecord, java.lang.Integer> CAUSAL_ID = createField("causal_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>drm.vw_work_reports.causal_description</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.VwWorkReportsRecord, java.lang.String> CAUSAL_DESCRIPTION = createField("causal_description", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "");

	/**
	 * The column <code>drm.vw_work_reports.from_date</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.VwWorkReportsRecord, org.joda.time.LocalDate> FROM_DATE = createField("from_date", org.jooq.impl.SQLDataType.DATE, this, "", new com.sonicle.webtop.core.jooq.LocalDateConverter());

	/**
	 * The column <code>drm.vw_work_reports.to_date</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.VwWorkReportsRecord, org.joda.time.LocalDate> TO_DATE = createField("to_date", org.jooq.impl.SQLDataType.DATE, this, "", new com.sonicle.webtop.core.jooq.LocalDateConverter());

	/**
	 * The column <code>drm.vw_work_reports.business_trip_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.VwWorkReportsRecord, java.lang.Integer> BUSINESS_TRIP_ID = createField("business_trip_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>drm.vw_work_reports.business_trip_description</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.VwWorkReportsRecord, java.lang.String> BUSINESS_TRIP_DESCRIPTION = createField("business_trip_description", org.jooq.impl.SQLDataType.VARCHAR.length(50), this, "");

	/**
	 * The column <code>drm.vw_work_reports.revision_status</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.VwWorkReportsRecord, java.lang.String> REVISION_STATUS = createField("revision_status", org.jooq.impl.SQLDataType.VARCHAR.length(1), this, "");

	/**
	 * The column <code>drm.vw_work_reports.charge_to</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.VwWorkReportsRecord, java.lang.Boolean> CHARGE_TO = createField("charge_to", org.jooq.impl.SQLDataType.BOOLEAN, this, "");

	/**
	 * The column <code>drm.vw_work_reports.free_support</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.VwWorkReportsRecord, java.lang.Boolean> FREE_SUPPORT = createField("free_support", org.jooq.impl.SQLDataType.BOOLEAN, this, "");

	/**
	 * The column <code>drm.vw_work_reports.company_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.VwWorkReportsRecord, java.lang.Integer> COMPANY_ID = createField("company_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>drm.vw_work_reports.company_description</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.VwWorkReportsRecord, java.lang.String> COMPANY_DESCRIPTION = createField("company_description", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "");

	/**
	 * The column <code>drm.vw_work_reports.doc_status_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.VwWorkReportsRecord, java.lang.Integer> DOC_STATUS_ID = createField("doc_status_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>drm.vw_work_reports.doc_status_description</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.VwWorkReportsRecord, java.lang.String> DOC_STATUS_DESCRIPTION = createField("doc_status_description", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "");

	/**
	 * The column <code>drm.vw_work_reports.description</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.VwWorkReportsRecord, java.lang.String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.VARCHAR.length(2048), this, "");

	/**
	 * The column <code>drm.vw_work_reports.notes</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.VwWorkReportsRecord, java.lang.String> NOTES = createField("notes", org.jooq.impl.SQLDataType.VARCHAR.length(1024), this, "");

	/**
	 * The column <code>drm.vw_work_reports.tot_hours</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.VwWorkReportsRecord, java.lang.Long> TOT_HOURS = createField("tot_hours", org.jooq.impl.SQLDataType.BIGINT, this, "");

	/**
	 * Create a <code>drm.vw_work_reports</code> table reference
	 */
	public VwWorkReports() {
		this("vw_work_reports", null);
	}

	/**
	 * Create an aliased <code>drm.vw_work_reports</code> table reference
	 */
	public VwWorkReports(java.lang.String alias) {
		this(alias, com.sonicle.webtop.drm.jooq.tables.VwWorkReports.VW_WORK_REPORTS);
	}

	private VwWorkReports(java.lang.String alias, org.jooq.Table<com.sonicle.webtop.drm.jooq.tables.records.VwWorkReportsRecord> aliased) {
		this(alias, aliased, null);
	}

	private VwWorkReports(java.lang.String alias, org.jooq.Table<com.sonicle.webtop.drm.jooq.tables.records.VwWorkReportsRecord> aliased, org.jooq.Field<?>[] parameters) {
		super(alias, com.sonicle.webtop.drm.jooq.Drm.DRM, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public com.sonicle.webtop.drm.jooq.tables.VwWorkReports as(java.lang.String alias) {
		return new com.sonicle.webtop.drm.jooq.tables.VwWorkReports(alias, this);
	}

	/**
	 * Rename this table
	 */
	public com.sonicle.webtop.drm.jooq.tables.VwWorkReports rename(java.lang.String name) {
		return new com.sonicle.webtop.drm.jooq.tables.VwWorkReports(name, null);
	}
}
