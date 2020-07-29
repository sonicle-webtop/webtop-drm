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
import com.sonicle.webtop.drm.JobQuery;
import com.sonicle.webtop.drm.bol.OJob;
import com.sonicle.webtop.drm.bol.OViewJob;
import com.sonicle.webtop.drm.jooq.Sequences;
import static com.sonicle.webtop.drm.jooq.Tables.ACTIVITIES;
import static com.sonicle.webtop.drm.jooq.Tables.PROFILES;
import static com.sonicle.webtop.drm.jooq.Tables.PROFILES_MEMBERS;
import static com.sonicle.webtop.drm.jooq.Tables.PROFILES_SUPERVISED_USERS;
import static com.sonicle.webtop.drm.jooq.Tables.JOBS;
import static com.sonicle.webtop.drm.jooq.tables.VwJobs.VW_JOBS;
import com.sonicle.webtop.drm.jooq.tables.records.JobsRecord;
import java.sql.Connection;
import org.joda.time.LocalTime;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.jooq.Field;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 *
 * @author dnllr
 */
public class JobDAO extends BaseDAO {

	private final static JobDAO INSTANCE = new JobDAO();

	public static JobDAO getInstance() {
		return INSTANCE;
	}	
	
	public List<OViewJob> selectViewJobs(Connection con, JobQuery query, String domainId, String userId) throws DAOException {
		DSLContext dsl = getDSL(con);

		Condition searchCndt = ensureViewCondition(query, domainId, userId);

		return dsl
				.select()
				.from(VW_JOBS)
				.where(
						(searchCndt)
				)
				.orderBy(
						VW_JOBS.END_DATE.desc()
				)
				.fetchInto(OViewJob.class);
	}
	
	private Condition ensureViewCondition(JobQuery query, String domainId, String userId) {
		
		Condition searchCndt = null;
		
		if(query.operatorId != null){
			searchCndt = VW_JOBS.OPERATOR_ID.equal(query.operatorId);
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
			
			searchCndt = VW_JOBS.OPERATOR_ID.in(operators);
		}
		
		if (domainId != null) {
			searchCndt = searchCndt.and(VW_JOBS.DOMAIN_ID.equal(domainId));
		}
		
		if (query.companyId != null) {
			searchCndt = searchCndt.and(VW_JOBS.COMPANY_ID.equal(query.companyId));
		}
		
		if (!StringUtils.isEmpty(query.customerId)) {
			searchCndt = searchCndt.and(VW_JOBS.CUSTOMER_ID.equal(query.customerId));
		}
		
		if (!StringUtils.isEmpty(query.customerStatId)) {
			searchCndt = searchCndt.and(VW_JOBS.CUSTOMER_STAT_ID.equal(query.customerStatId));
		}
		
		if (query.startDate != null) {
			searchCndt = searchCndt.and(VW_JOBS.START_DATE.greaterOrEqual(query.startDate.toDateTime(new LocalTime(0,0,0))));			
		}
		
		if (query.endDate != null) {
			searchCndt = searchCndt.and(VW_JOBS.END_DATE.lessOrEqual(query.endDate.toDateTime(new LocalTime(23,59,59))));
		}
		
		if (query.activityId != null) {
			searchCndt = searchCndt.and(VW_JOBS.ACTIVITY_ID.equal(query.activityId));
		}
		
		if (!StringUtils.isEmpty(query.title)) {
			searchCndt = searchCndt.and(VW_JOBS.TITLE.like("%" + query.title + "%"));
		}

		return searchCndt;
	}
	
	public OJob selectById(Connection con, String jobId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
			.select()
			.from(JOBS)
			.where(
				JOBS.JOB_ID.equal(jobId)
			)
			.fetchOneInto(OJob.class);
	}
	
	public int insert(Connection con, OJob item) throws DAOException {
		DSLContext dsl = getDSL(con);

		JobsRecord record = dsl.newRecord(JOBS, item);
		return dsl
				.insertInto(JOBS)
				.set(record)
				.execute();
	}
	
	public int update(Connection con, OJob item) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.update(JOBS)
				.set(JOBS.COMPANY_ID, item.getCompanyId())
				.set(JOBS.OPERATOR_ID, item.getOperatorId())
				.set(JOBS.CUSTOMER_ID, item.getCustomerId())
				.set(JOBS.CUSTOMER_STAT_ID, item.getCustomerStatId())
				.set(JOBS.ACTIVITY_ID, item.getActivityId())
				.set(JOBS.START_DATE, item.getStartDate())
				.set(JOBS.END_DATE, item.getEndDate())
				.set(JOBS.TIMEZONE, item.getTimezone())
				.set(JOBS.TITLE, item.getTitle())
				.set(JOBS.DESCRIPTION, item.getDescription())
				.set(JOBS.EVENT_ID, item.getEventId())
				.set(JOBS.DOMAIN_ID, item.getDomainId())
				.set(JOBS.TICKET_ID, item.getTicketId())
				.set(JOBS.CAUSAL_ID, item.getCausalId())
				.where(
						JOBS.JOB_ID.equal(item.getJobId())
				)
				.execute();
	}
	
	public int deleteById(Connection con, String jobId) {
		DSLContext dsl = getDSL(con);
		return dsl
			.delete(JOBS)
			.where(
					JOBS.JOB_ID.equal(jobId)
			)
			.execute();
	}
	
	public List<OViewJob> selectViewJobsByTicketId(Connection con, String ticketId, String domainId) throws DAOException {
		DSLContext dsl = getDSL(con);

		return dsl
				.select()
				.from(VW_JOBS)
				.where(
						(VW_JOBS.DOMAIN_ID.equal(domainId)
						.and(VW_JOBS.TICKET_ID.equal(ticketId)))
				)
				.orderBy(
						VW_JOBS.END_DATE.desc()
				)
				.fetchInto(OViewJob.class);
	}
	
	public Long getSequence(Connection con) throws DAOException {
		DSLContext dsl = getDSL(con);
		Long nextID = dsl.nextval(Sequences.SEQ_JOBS);
		return nextID;
	}
	
	public int associateTicket(Connection con, String jobId, String ticketId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
			.update(JOBS)
			.set(JOBS.TICKET_ID, ticketId)
			.where(
				JOBS.JOB_ID.equal(jobId)
			)
			.execute();
	}
	
	public List<OJob> getJobsByDomainUserDateRange(Connection con, String domainId, Integer companyId, String userId, Integer fromDay, Integer month, Integer year) {
		DSLContext dsl = getDSL(con);
		
		Field<Integer> jobHours = DSL.field("((DATE_PART('day', {0} - {1}) * 24 + DATE_PART('hour', {0} - {1})) * 60 + DATE_PART('minute', {0} - {1}))", Integer.class, JOBS.END_DATE, JOBS.START_DATE);
		
		return dsl
			.select(
				JOBS.DOMAIN_ID, 
				JOBS.COMPANY_ID,
				JOBS.OPERATOR_ID, 
				JOBS.START_DATE,
				jobHours.as("job_hours")
			)
			.from(
					JOBS
			).join(
					ACTIVITIES
			).on(
					ACTIVITIES.DOMAIN_ID.equal(JOBS.DOMAIN_ID).and(ACTIVITIES.ACTIVITY_ID.eq(JOBS.ACTIVITY_ID))
			)
			.where(
					JOBS.DOMAIN_ID.equal(domainId)
			)
			.and(
					JOBS.OPERATOR_ID.equal(userId)
			)
			.and(
					JOBS.START_DATE.between(new DateTime().withYear(year).withMonthOfYear(month).withDayOfMonth(fromDay).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).toDateTime(), new DateTime().withYear(year).withMonthOfYear(month).dayOfMonth().withMaximumValue().withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).toDateTime())
			)
			.and(
					JOBS.COMPANY_ID.equal(companyId)
			)
			.and(
					ACTIVITIES.TIMETABLE.eq(Boolean.TRUE)
			)
			.orderBy(
					JOBS.OPERATOR_ID, 
					JOBS.START_DATE
			)
			.fetchInto(OJob.class);
	}
	
}
