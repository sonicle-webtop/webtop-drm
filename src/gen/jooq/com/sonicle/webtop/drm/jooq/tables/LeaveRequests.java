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
public class LeaveRequests extends org.jooq.impl.TableImpl<com.sonicle.webtop.drm.jooq.tables.records.LeaveRequestsRecord> {

	private static final long serialVersionUID = 2018953687;

	/**
	 * The reference instance of <code>drm.leave_requests</code>
	 */
	public static final com.sonicle.webtop.drm.jooq.tables.LeaveRequests LEAVE_REQUESTS = new com.sonicle.webtop.drm.jooq.tables.LeaveRequests();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<com.sonicle.webtop.drm.jooq.tables.records.LeaveRequestsRecord> getRecordType() {
		return com.sonicle.webtop.drm.jooq.tables.records.LeaveRequestsRecord.class;
	}

	/**
	 * The column <code>drm.leave_requests.leave_request_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.LeaveRequestsRecord, java.lang.Integer> LEAVE_REQUEST_ID = createField("leave_request_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>drm.leave_requests.domain_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.LeaveRequestsRecord, java.lang.String> DOMAIN_ID = createField("domain_id", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false), this, "");

	/**
	 * The column <code>drm.leave_requests.company_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.LeaveRequestsRecord, java.lang.Integer> COMPANY_ID = createField("company_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>drm.leave_requests.user_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.LeaveRequestsRecord, java.lang.String> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.VARCHAR.length(36).nullable(false), this, "");

	/**
	 * The column <code>drm.leave_requests.manager_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.LeaveRequestsRecord, java.lang.String> MANAGER_ID = createField("manager_id", org.jooq.impl.SQLDataType.VARCHAR.length(36).nullable(false), this, "");

	/**
	 * The column <code>drm.leave_requests.type</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.LeaveRequestsRecord, java.lang.String> TYPE = createField("type", org.jooq.impl.SQLDataType.VARCHAR.length(1).nullable(false), this, "");

	/**
	 * The column <code>drm.leave_requests.from_date</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.LeaveRequestsRecord, org.joda.time.LocalDate> FROM_DATE = createField("from_date", org.jooq.impl.SQLDataType.DATE.nullable(false), this, "", new com.sonicle.webtop.core.jooq.LocalDateConverter());

	/**
	 * The column <code>drm.leave_requests.to_date</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.LeaveRequestsRecord, org.joda.time.LocalDate> TO_DATE = createField("to_date", org.jooq.impl.SQLDataType.DATE.nullable(false), this, "", new com.sonicle.webtop.core.jooq.LocalDateConverter());

	/**
	 * The column <code>drm.leave_requests.from_hour</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.LeaveRequestsRecord, java.lang.String> FROM_HOUR = createField("from_hour", org.jooq.impl.SQLDataType.VARCHAR.length(5), this, "");

	/**
	 * The column <code>drm.leave_requests.to_hour</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.LeaveRequestsRecord, java.lang.String> TO_HOUR = createField("to_hour", org.jooq.impl.SQLDataType.VARCHAR.length(5), this, "");

	/**
	 * The column <code>drm.leave_requests.status</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.LeaveRequestsRecord, java.lang.String> STATUS = createField("status", org.jooq.impl.SQLDataType.VARCHAR.length(1).nullable(false), this, "");

	/**
	 * The column <code>drm.leave_requests.notes</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.LeaveRequestsRecord, java.lang.String> NOTES = createField("notes", org.jooq.impl.SQLDataType.CLOB, this, "");

	/**
	 * The column <code>drm.leave_requests.employee_req_timestamp</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.LeaveRequestsRecord, org.joda.time.DateTime> EMPLOYEE_REQ_TIMESTAMP = createField("employee_req_timestamp", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "", new com.sonicle.webtop.core.jooq.DateTimeConverter());

	/**
	 * The column <code>drm.leave_requests.manager_resp_timestamp</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.LeaveRequestsRecord, org.joda.time.DateTime> MANAGER_RESP_TIMESTAMP = createField("manager_resp_timestamp", org.jooq.impl.SQLDataType.TIMESTAMP, this, "", new com.sonicle.webtop.core.jooq.DateTimeConverter());

	/**
	 * The column <code>drm.leave_requests.result</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.LeaveRequestsRecord, java.lang.Boolean> RESULT = createField("result", org.jooq.impl.SQLDataType.BOOLEAN, this, "");

	/**
	 * The column <code>drm.leave_requests.employee_canc_req</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.LeaveRequestsRecord, java.lang.Boolean> EMPLOYEE_CANC_REQ = createField("employee_canc_req", org.jooq.impl.SQLDataType.BOOLEAN, this, "");

	/**
	 * The column <code>drm.leave_requests.employee_canc_req_timestamp</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.LeaveRequestsRecord, org.joda.time.DateTime> EMPLOYEE_CANC_REQ_TIMESTAMP = createField("employee_canc_req_timestamp", org.jooq.impl.SQLDataType.TIMESTAMP, this, "", new com.sonicle.webtop.core.jooq.DateTimeConverter());

	/**
	 * The column <code>drm.leave_requests.manager_canc_resp_timetamp</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.LeaveRequestsRecord, org.joda.time.DateTime> MANAGER_CANC_RESP_TIMETAMP = createField("manager_canc_resp_timetamp", org.jooq.impl.SQLDataType.TIMESTAMP, this, "", new com.sonicle.webtop.core.jooq.DateTimeConverter());

	/**
	 * The column <code>drm.leave_requests.canc_reason</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.LeaveRequestsRecord, java.lang.String> CANC_REASON = createField("canc_reason", org.jooq.impl.SQLDataType.CLOB, this, "");

	/**
	 * The column <code>drm.leave_requests.canc_result</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.LeaveRequestsRecord, java.lang.Boolean> CANC_RESULT = createField("canc_result", org.jooq.impl.SQLDataType.BOOLEAN, this, "");

	/**
	 * The column <code>drm.leave_requests.event_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.LeaveRequestsRecord, java.lang.Integer> EVENT_ID = createField("event_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * Create a <code>drm.leave_requests</code> table reference
	 */
	public LeaveRequests() {
		this("leave_requests", null);
	}

	/**
	 * Create an aliased <code>drm.leave_requests</code> table reference
	 */
	public LeaveRequests(java.lang.String alias) {
		this(alias, com.sonicle.webtop.drm.jooq.tables.LeaveRequests.LEAVE_REQUESTS);
	}

	private LeaveRequests(java.lang.String alias, org.jooq.Table<com.sonicle.webtop.drm.jooq.tables.records.LeaveRequestsRecord> aliased) {
		this(alias, aliased, null);
	}

	private LeaveRequests(java.lang.String alias, org.jooq.Table<com.sonicle.webtop.drm.jooq.tables.records.LeaveRequestsRecord> aliased, org.jooq.Field<?>[] parameters) {
		super(alias, com.sonicle.webtop.drm.jooq.Drm.DRM, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.LeaveRequestsRecord> getPrimaryKey() {
		return com.sonicle.webtop.drm.jooq.Keys.LEAVE_REQUESTS_PKEY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.LeaveRequestsRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.LeaveRequestsRecord>>asList(com.sonicle.webtop.drm.jooq.Keys.LEAVE_REQUESTS_PKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public com.sonicle.webtop.drm.jooq.tables.LeaveRequests as(java.lang.String alias) {
		return new com.sonicle.webtop.drm.jooq.tables.LeaveRequests(alias, this);
	}

	/**
	 * Rename this table
	 */
	public com.sonicle.webtop.drm.jooq.tables.LeaveRequests rename(java.lang.String name) {
		return new com.sonicle.webtop.drm.jooq.tables.LeaveRequests(name, null);
	}
}
