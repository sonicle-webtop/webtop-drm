/*
 * Copyright (C) 2017 Sonicle S.r.l.
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License version 3 as published by
 * the Free Software Foundation with the addition of the following permission
 * added to Section 15 as permitted in Section 7(a): FOR ANY PART OF THE COVERED
 * WORK IN WHICH THE COPYRIGHT IS OWNED BY SONICLE, SONICLE DISCLAIMS THE
 * WARRANTY OF NON INFRINGEMENT OF THIRD PARTY RIGHTS.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, see http://www.gnu.org/licenses or write to
 * the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301 USA.
 *
 * You can contact Sonicle S.r.l. at email address sonicle[at]sonicle[dot]com
 *
 * The interactive user interfaces in modified source and object code versions
 * of this program must display Appropriate Legal Notices, as required under
 * Section 5 of the GNU Affero General Public License version 3.
 *
 * In accordance with Section 7(b) of the GNU Affero General Public License
 * version 3, these Appropriate Legal Notices must retain the display of the
 * Sonicle logo and Sonicle copyright notice. If the display of the logo is not
 * reasonably feasible for technical reasons, the Appropriate Legal Notices must
 * display the words "Copyright (C) 2017 Sonicle S.r.l.".
 */
package com.sonicle.webtop.drm.dal;

import com.sonicle.webtop.core.dal.BaseDAO;
import com.sonicle.webtop.core.dal.DAOException;
import com.sonicle.webtop.drm.WorkReportQuery;
import com.sonicle.webtop.drm.bol.OViewWorkReport;
import com.sonicle.webtop.drm.bol.OWorkReport;
import com.sonicle.webtop.drm.jooq.Sequences;
import static com.sonicle.webtop.drm.jooq.Tables.PROFILES;
import static com.sonicle.webtop.drm.jooq.Tables.PROFILES_MEMBERS;
import static com.sonicle.webtop.drm.jooq.Tables.PROFILES_SUPERVISED_USERS;
import static com.sonicle.webtop.drm.jooq.Tables.WORK_REPORTS;
import static com.sonicle.webtop.drm.jooq.Tables.VW_WORK_REPORTS;
import com.sonicle.webtop.drm.jooq.tables.records.WorkReportsRecord;
import java.sql.Connection;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;

/**
 *
 * @author lssndrvs
 */
public class WorkReportDAO extends BaseDAO {

	private final static WorkReportDAO INSTANCE = new WorkReportDAO();

	public static WorkReportDAO getInstance() {
		return INSTANCE;
	}

	public int insert(Connection con, OWorkReport item, DateTime revisionTimestamp) throws DAOException {
		DSLContext dsl = getDSL(con);

		item.setRevisionStatus(OWorkReport.REV_STATUS_NEW);
		item.setRevisionTimestamp(revisionTimestamp);
		item.setRevisionSequence(0);

		WorkReportsRecord record = dsl.newRecord(WORK_REPORTS, item);
		return dsl
				.insertInto(WORK_REPORTS)
				.set(record)
				.execute();
	}		
	
	public List<OViewWorkReport> selectViewWorkReports(Connection con, WorkReportQuery query, String domainId, String userId) throws DAOException {
		DSLContext dsl = getDSL(con);

		Condition searchCndt = ensureViewCondition(query, domainId, userId);

		return dsl
				.select()
				.from(VW_WORK_REPORTS)
				.where(
						VW_WORK_REPORTS.REVISION_STATUS.equal(OViewWorkReport.REV_STATUS_NEW)
						.or(VW_WORK_REPORTS.REVISION_STATUS.equal(OViewWorkReport.REV_STATUS_MODIFIED))
						.and(searchCndt)
				)
				.orderBy(
						VW_WORK_REPORTS.YEAR,
						VW_WORK_REPORTS.NUMBER
				)
				.fetchInto(OViewWorkReport.class);
	}
	
	public OWorkReport selectById(Connection con, String workReportId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(WORK_REPORTS)
				.where(WORK_REPORTS.WORK_REPORT_ID.equal(workReportId))
				.fetchOneInto(OWorkReport.class);
	}

	public int update(Connection con, OWorkReport item, DateTime revisionTimestamp) throws DAOException {
		DSLContext dsl = getDSL(con);
		item.setRevisionStatus(OWorkReport.REV_STATUS_MODIFIED);
		item.setRevisionTimestamp(revisionTimestamp);
		return dsl
				.update(WORK_REPORTS)
				.set(WORK_REPORTS.NUMBER, item.getNumber())
				.set(WORK_REPORTS.YEAR, item.getYear())
				.set(WORK_REPORTS.COMPANY_ID, item.getCompanyId())
				.set(WORK_REPORTS.OPERATOR_ID, item.getOperatorId())
				.set(WORK_REPORTS.REVISION_STATUS, item.getRevisionStatus())
				.set(WORK_REPORTS.REVISION_TIMESTAMP, item.getRevisionTimestamp())
				.set(WORK_REPORTS.REVISION_SEQUENCE, item.getRevisionSequence())
				.set(WORK_REPORTS.DOC_STATUS_ID, item.getDocStatusId())
				.set(WORK_REPORTS.CONTACT_ID, item.getContactId())
				.set(WORK_REPORTS.CUSTOMER_ID, item.getCustomerId())
				.set(WORK_REPORTS.CUSTOMER_STAT_ID, item.getCustomerStatId())
				.set(WORK_REPORTS.CONTACT_ID, item.getContactId())
				.set(WORK_REPORTS.FROM_DATE, item.getFromDate())
				.set(WORK_REPORTS.TO_DATE, item.getToDate())
				.set(WORK_REPORTS.REFERENCE_NO, item.getReferenceNo())
				.set(WORK_REPORTS.CAUSAL_ID, item.getCausalId())
				.set(WORK_REPORTS.DDT_NO, item.getDdtNo())
				.set(WORK_REPORTS.DDT_DATE, item.getDdtDate())
				.set(WORK_REPORTS.NOTES, item.getNotes())
				.set(WORK_REPORTS.DESCRIPTION, item.getDescription())
				.set(WORK_REPORTS.APPLY_SIGNATURE, item.getApplySignature())
				.set(WORK_REPORTS.CHARGE_TO, item.getChargeTo())
				.set(WORK_REPORTS.FREE_SUPPORT, item.getFreeSupport())
				.set(WORK_REPORTS.BUSINESS_TRIP_ID, item.getBusinessTripId())
				.set(WORK_REPORTS.BUSINESS_TRIP_DAYS, item.getBusinessTripDays())
				.set(WORK_REPORTS.EVENT_ID, item.getEventId())
				.set(WORK_REPORTS.TIMETABLE_HOURS, item.getTimetableHours())
				.set(WORK_REPORTS.DOMAIN_ID, item.getDomainId())
				.where(
						WORK_REPORTS.WORK_REPORT_ID.equal(item.getWorkReportId())
				)
				.execute();
	}

	public int deleteById(Connection con, String workReportId) {
		DSLContext dsl = getDSL(con);
		return dsl
				.delete(WORK_REPORTS)
				.where(
						WORK_REPORTS.WORK_REPORT_ID.equal(workReportId)
				)
				.execute();
	}

	public int logicalDelete(Connection con, String workReportId, DateTime revisionTimestamp) {
		DSLContext dsl = getDSL(con);
		return dsl
				.update(WORK_REPORTS)
				.set(WORK_REPORTS.REVISION_STATUS, OWorkReport.REV_STATUS_DELETED)
				.set(WORK_REPORTS.REVISION_TIMESTAMP, revisionTimestamp)
				.where(
						WORK_REPORTS.WORK_REPORT_ID.equal(workReportId)
				)
				.execute();
	}

	public Long getWorkReportSequence(Connection con) throws DAOException {
		DSLContext dsl = getDSL(con);
		Long nextID = dsl.nextval(Sequences.SEQ_WORK_REPORTS_COUNT);
		return nextID;
	}

	private Condition ensureCondition(WorkReportQuery query, String domainId, String userId) {
		
		Condition searchCndt = null;
		
		if(query.operatorId != null){
			searchCndt = WORK_REPORTS.OPERATOR_ID.equal(query.operatorId);
		}else{			
			SelectConditionStep<Record1<String>> operators = (SelectConditionStep<Record1<String>>) DSL
				.select(
						PROFILES_SUPERVISED_USERS.USER_ID
					)
					.from(PROFILES)
					.join(PROFILES_MEMBERS).on(
						PROFILES.PROFILE_ID.equal(PROFILES_MEMBERS.PROFILE_ID)
					)
					.join(PROFILES_SUPERVISED_USERS).on(
						PROFILES.PROFILE_ID.equal(PROFILES_SUPERVISED_USERS.PROFILE_ID)
					)
					.where(
							PROFILES.DOMAIN_ID.equal(domainId)
							.and(PROFILES_MEMBERS.USER_ID.equal(userId)
					))
					.union(DSL.select(DSL.inline(userId)));
			
			searchCndt = WORK_REPORTS.OPERATOR_ID.in(operators);
		}
		
		if (query.companyId != null) {
			searchCndt = searchCndt.and(WORK_REPORTS.COMPANY_ID.equal(query.companyId));
		}
		
		if (!StringUtils.isEmpty(query.customerId)) {
			searchCndt = searchCndt.and(WORK_REPORTS.CUSTOMER_ID.equal(query.customerId));
		}
		
		if (!StringUtils.isEmpty(query.customerStatId)) {
			searchCndt = searchCndt.and(WORK_REPORTS.CUSTOMER_STAT_ID.equal(query.customerStatId));
		}
		
		if (query.fromDate != null) {
			searchCndt = searchCndt.and(WORK_REPORTS.FROM_DATE.greaterOrEqual(query.fromDate));
		}

		if (query.toDate != null) {
			searchCndt = searchCndt.and(WORK_REPORTS.TO_DATE.lessOrEqual(query.toDate));
		}
		
		if (!StringUtils.isEmpty(query.referenceNo)) {
			searchCndt = searchCndt.and(WORK_REPORTS.REFERENCE_NO.like("%" + query.referenceNo + "%"));
		}
		
		if (query.causalId != null) {
			searchCndt = searchCndt.and(WORK_REPORTS.CAUSAL_ID.equal(query.causalId));
		}
		
		if (query.businessTripId != null) {
			searchCndt = searchCndt.and(WORK_REPORTS.BUSINESS_TRIP_ID.equal(query.businessTripId));
		}
		
		if (!StringUtils.isEmpty(query.description)) {
			searchCndt = searchCndt.and(WORK_REPORTS.DESCRIPTION.like("%" + query.description + "%"));
		}
		
		if (!StringUtils.isEmpty(query.notes)) {
			searchCndt = searchCndt.and(WORK_REPORTS.NOTES.like("%" + query.notes + "%"));
		}
		
		if (query.docStatusId != null) {
			searchCndt = searchCndt.and(WORK_REPORTS.DOC_STATUS_ID.equal(query.docStatusId));
		}
		
		if (query.chargeTo != null) {
			searchCndt = searchCndt.and(WORK_REPORTS.CHARGE_TO.equal(query.chargeTo));
		}
		
		if (query.year != null) {
			searchCndt = searchCndt.and(WORK_REPORTS.YEAR.equal(query.year));
		}
		
		if (query.number != null) {
			searchCndt = searchCndt.and(WORK_REPORTS.NUMBER.equal(query.number));
		}

		return searchCndt;
	}
	
	private Condition ensureViewCondition(WorkReportQuery query, String domainId, String userId) {
		
		Condition searchCndt = null;
		
		if (query.operatorId != null){
			searchCndt = VW_WORK_REPORTS.OPERATOR_ID.equal(query.operatorId);
		} else {			
			SelectConditionStep<Record1<String>> operators = (SelectConditionStep<Record1<String>>) DSL
				.select(
						PROFILES_SUPERVISED_USERS.USER_ID
					)
					.from(PROFILES)
					.join(PROFILES_MEMBERS).on(
						PROFILES.PROFILE_ID.equal(PROFILES_MEMBERS.PROFILE_ID)
					)
					.join(PROFILES_SUPERVISED_USERS).on(
						PROFILES.PROFILE_ID.equal(PROFILES_SUPERVISED_USERS.PROFILE_ID)
					)
					.where(
							PROFILES.DOMAIN_ID.equal(domainId)
							.and(PROFILES_MEMBERS.USER_ID.equal(userId)
					))
					.union(DSL.select(DSL.inline(userId)));
			
			searchCndt = VW_WORK_REPORTS.OPERATOR_ID.in(operators);
		}
		
		if (domainId != null) {
			searchCndt = searchCndt.and(VW_WORK_REPORTS.DOMAIN_ID.equal(domainId));
		}
		
		if (query.companyId != null) {
			searchCndt = searchCndt.and(VW_WORK_REPORTS.COMPANY_ID.equal(query.companyId));
		}
		
		if (!StringUtils.isEmpty(query.customerId)) {
			searchCndt = searchCndt.and(VW_WORK_REPORTS.CUSTOMER_ID.equal(query.customerId));
		}
		
		if (!StringUtils.isEmpty(query.customerStatId)) {
			searchCndt = searchCndt.and(VW_WORK_REPORTS.CUSTOMER_STAT_ID.equal(query.customerStatId));
		}
		
		if (query.fromDate != null) {
			searchCndt = searchCndt.and(VW_WORK_REPORTS.FROM_DATE.greaterOrEqual(query.fromDate));
		}

		if (query.toDate != null) {
			searchCndt = searchCndt.and(VW_WORK_REPORTS.TO_DATE.lessOrEqual(query.toDate));
		}
		
		if (!StringUtils.isEmpty(query.referenceNo)) {
			searchCndt = searchCndt.and(VW_WORK_REPORTS.REFERENCE_NO.like("%" + query.referenceNo + "%"));
		}
		
		if (query.causalId != null) {
			searchCndt = searchCndt.and(VW_WORK_REPORTS.CAUSAL_ID.equal(query.causalId));
		}
		
		if (query.businessTripId != null) {
			searchCndt = searchCndt.and(VW_WORK_REPORTS.BUSINESS_TRIP_ID.equal(query.businessTripId));
		}
		
		if (!StringUtils.isEmpty(query.description)) {
			searchCndt = searchCndt.and(VW_WORK_REPORTS.DESCRIPTION.like("%" + query.description + "%"));
		}
		
		if (!StringUtils.isEmpty(query.notes)) {
			searchCndt = searchCndt.and(VW_WORK_REPORTS.NOTES.like("%" + query.notes + "%"));
		}
		
		if (query.docStatusId != null) {
			searchCndt = searchCndt.and(VW_WORK_REPORTS.DOC_STATUS_ID.equal(query.docStatusId));
		}
		
		if (query.chargeTo != null) {
			searchCndt = searchCndt.and(VW_WORK_REPORTS.CHARGE_TO.equal(query.chargeTo));
		}
		
		if (query.year != null) {
			searchCndt = searchCndt.and(VW_WORK_REPORTS.YEAR.equal(query.year));
		}
		
		if (query.number != null) {
			searchCndt = searchCndt.and(VW_WORK_REPORTS.NUMBER.equal(query.number));
		}

		return searchCndt;
	}
	
	public List<OWorkReport> selectWorkReports(Connection con, WorkReportQuery query, String domainId, String userId) throws DAOException {
		DSLContext dsl = getDSL(con);

		Condition searchCndt = ensureViewCondition(query, domainId, userId);

		return dsl
				.select()
				.from(WORK_REPORTS)
				.where(
						WORK_REPORTS.REVISION_STATUS.equal(OWorkReport.REV_STATUS_NEW)
						.or(WORK_REPORTS.REVISION_STATUS.equal(OWorkReport.REV_STATUS_MODIFIED))
						.and(searchCndt)
				)
				.orderBy(
						WORK_REPORTS.YEAR,
						WORK_REPORTS.NUMBER
				)
				.fetchInto(OWorkReport.class);
	}
	
	public List<OWorkReport> getWorkReportsByDomainUserDateRange(Connection con, String domainId, Integer companyId, String userId, Integer fromDay, Integer month, Integer year) {
		DSLContext dsl = getDSL(con);
		return dsl
				.select(
						WORK_REPORTS.DOMAIN_ID, 
						WORK_REPORTS.COMPANY_ID,
						WORK_REPORTS.OPERATOR_ID, 
						WORK_REPORTS.FROM_DATE,
						WORK_REPORTS.TIMETABLE_HOURS
				)
				.from(
						WORK_REPORTS
				)
				.where(
						WORK_REPORTS.DOMAIN_ID.equal(domainId)
				)
				.and(
						WORK_REPORTS.OPERATOR_ID.equal(userId)
				)
				.and(
						WORK_REPORTS.FROM_DATE.between(new DateTime().withYear(year).withMonthOfYear(month).withDayOfMonth(fromDay).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).toLocalDate(), new DateTime().withYear(year).withMonthOfYear(month).dayOfMonth().withMaximumValue().withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).toLocalDate())
				)
				.and(
						WORK_REPORTS.COMPANY_ID.equal(companyId)
				)
				.orderBy(
						WORK_REPORTS.OPERATOR_ID, 
						WORK_REPORTS.FROM_DATE
				)
				.fetchInto(OWorkReport.class);
	}
}
