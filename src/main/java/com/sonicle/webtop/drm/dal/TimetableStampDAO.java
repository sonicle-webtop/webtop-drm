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
import com.sonicle.webtop.drm.TimetableStampQuery;
import com.sonicle.webtop.drm.bol.OTimetableReport;
import com.sonicle.webtop.drm.bol.OTimetableStamp;
import static com.sonicle.webtop.drm.jooq.Sequences.SEQ_TIMETABLE_STAMP;
import static com.sonicle.webtop.drm.jooq.Tables.TIMETABLE_STAMP;
import static com.sonicle.webtop.drm.jooq.Tables.EMPLOYEE_PROFILES;
import static com.sonicle.webtop.drm.jooq.Tables.TIMETABLE_SETTINGS;
import static com.sonicle.webtop.drm.jooq.Tables.COMPANIES_USERS;
import com.sonicle.webtop.drm.jooq.tables.records.TimetableStampRecord;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.impl.DSL;

/**
 *
 * @author lssndrvs
 */
public class TimetableStampDAO extends BaseDAO{
	
	private final static TimetableStampDAO INSTANCE = new TimetableStampDAO();

	public static TimetableStampDAO getInstance() {
		return INSTANCE;
	}
	
	public Long getSequence(Connection con) throws DAOException {
		DSLContext dsl = getDSL(con);
		Long nextID = dsl.nextval(SEQ_TIMETABLE_STAMP);
		return nextID;
	}
	
	public int insert(Connection con, OTimetableStamp item) throws DAOException {
		DSLContext dsl = getDSL(con);
		TimetableStampRecord record = dsl.newRecord(TIMETABLE_STAMP, item);
		
		return dsl
				.insertInto(TIMETABLE_STAMP)
				.set(record)
				.execute();
	}
	
	public int update(Connection con, OTimetableStamp item) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
			.update(TIMETABLE_STAMP)
			.set(TIMETABLE_STAMP.EXIT,item.getExit())
			.where(
				TIMETABLE_STAMP.ID.equal(item.getId())
			)
			.execute();
	}
	
	public List<OTimetableStamp> getDailyStampsByDomainUserIdType(Connection con, String domainId, String userId, String type) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(TIMETABLE_STAMP)
				.where(
						TIMETABLE_STAMP.DOMAIN_ID.equal(domainId)
				)
				.and(
						TIMETABLE_STAMP.USER_ID.equal(userId)
				)
				.and(
						TIMETABLE_STAMP.TYPE.equal(type)
				)
				.and(
						TIMETABLE_STAMP.ENTRANCE.between(new DateTime().withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0), new DateTime().withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59))
				)
				.fetchInto(OTimetableStamp.class);
	}
	
	public List<OTimetableStamp> getDailyStampsByDomainUserId(Connection con, String domainId, String userId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(TIMETABLE_STAMP)
				.where(
						TIMETABLE_STAMP.DOMAIN_ID.equal(domainId)
				)
				.and(
						TIMETABLE_STAMP.USER_ID.equal(userId)
				)
				.and(
						TIMETABLE_STAMP.ENTRANCE.between(new DateTime().withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0), new DateTime().withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59))
				)
				.fetchInto(OTimetableStamp.class);
	}
	
	public List<OTimetableStamp> getStampsListByDomainUserDateRange(Connection con, String domainId, String operatorId, Integer month, Integer year) throws DAOException {
		DSLContext dsl = getDSL(con);
		
		return dsl
				.select()
				.from(TIMETABLE_STAMP)
				.where(
						TIMETABLE_STAMP.DOMAIN_ID.equal(domainId)
				)
				.and(
						TIMETABLE_STAMP.USER_ID.equal(operatorId)
				)
				.and(
						TIMETABLE_STAMP.ENTRANCE.between(new DateTime().withYear(year).withMonthOfYear(month).withDayOfMonth(1).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0), new DateTime().withYear(year).withMonthOfYear(month).dayOfMonth().withMaximumValue().withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59))
				)
				.and(
						TIMETABLE_STAMP.EXIT.between(new DateTime().withYear(year).withMonthOfYear(month).withDayOfMonth(1).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0), new DateTime().withYear(year).withMonthOfYear(month).dayOfMonth().withMaximumValue().withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59))
				)
				.fetchInto(OTimetableStamp.class);
	}
	
	public List<OTimetableReport> getStampsByDomainUserDateRange(Connection con, String domainId, Integer companyId, String userId, Integer fromDay, Integer month, Integer year) throws DAOException {
		DSLContext dsl = getDSL(con);
		
		Field<Integer> workingHours = DSL.field("((DATE_PART('day', {0} - {1}) * 24 + DATE_PART('hour', {0} - {1})) * 60 + DATE_PART('minute', {0} - {1}))", Integer.class, TIMETABLE_STAMP.EXIT, TIMETABLE_STAMP.ENTRANCE);
		Field<String> detail = DSL.field("'e ' || to_char({0}, 'HH24:MI') || ' u ' || to_char({1}, 'HH24:MI') || ' '", String.class, TIMETABLE_STAMP.ENTRANCE, TIMETABLE_STAMP.EXIT);
		
		return dsl
				.select(
						TIMETABLE_STAMP.DOMAIN_ID, 
						COMPANIES_USERS.COMPANY_ID,
						TIMETABLE_STAMP.USER_ID.as("target_user_id"), 
						TIMETABLE_STAMP.ENTRANCE.as("date"),
						workingHours.as("working_hours"),
						detail.as("detail")
				)
				.from(TIMETABLE_STAMP)
				.leftOuterJoin(
						EMPLOYEE_PROFILES
				)
				.on(
						EMPLOYEE_PROFILES.USER_ID.equal(TIMETABLE_STAMP.USER_ID)
				)
				.join(
						TIMETABLE_SETTINGS
				)
				.on(
						TIMETABLE_SETTINGS.DOMAIN_ID.equal(TIMETABLE_STAMP.DOMAIN_ID)
				)
				.join(
						COMPANIES_USERS
				)
				.on(
						COMPANIES_USERS.USER_ID.equal(TIMETABLE_STAMP.USER_ID)
				)
				.where(
						TIMETABLE_STAMP.DOMAIN_ID.equal(domainId)
				)
				.and(
						TIMETABLE_STAMP.USER_ID.equal(userId)
				)
				.and(
						TIMETABLE_STAMP.ENTRANCE.between(new DateTime().withYear(year).withMonthOfYear(month).withDayOfMonth(fromDay).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0), new DateTime().withYear(year).withMonthOfYear(month).dayOfMonth().withMaximumValue().withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59))
				)
				.and(
						TIMETABLE_STAMP.EXIT.between(new DateTime().withYear(year).withMonthOfYear(month).withDayOfMonth(fromDay).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0), new DateTime().withYear(year).withMonthOfYear(month).dayOfMonth().withMaximumValue().withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59))
				)
				.and(
						TIMETABLE_STAMP.TYPE.in(OTimetableStamp.TYPE_MAIN, OTimetableStamp.TYPE_SPECIAL)
				)
				.and(
						COMPANIES_USERS.COMPANY_ID.equal(companyId)
				)
				.orderBy(TIMETABLE_STAMP.USER_ID, TIMETABLE_STAMP.ENTRANCE)
				.fetchInto(OTimetableReport.class);
	}
	
	public int deleteById(Connection con, Integer id) {
		DSLContext dsl = getDSL(con);
		return dsl
			.delete(TIMETABLE_STAMP)
			.where(
					TIMETABLE_STAMP.ID.equal(id)
			)
			.execute();
	}
	
	public int deleteRangeByUserIds(Connection con, String domainId, List<String> userIds, DateTime fromDate, DateTime toDate) {
		DSLContext dsl = getDSL(con);
		return dsl
			.delete(TIMETABLE_STAMP)
			.where(
				TIMETABLE_STAMP.DOMAIN_ID.eq(domainId).and(
					TIMETABLE_STAMP.USER_ID.in(userIds)
				).and(
					TIMETABLE_STAMP.ENTRANCE.between(fromDate, toDate).or(
						TIMETABLE_STAMP.EXIT.between(fromDate, toDate)
					)
				)
			)
			.execute();
	}
	
	public int deleteRange(Connection con, String domainId, DateTime fromDate, DateTime toDate) {
		DSLContext dsl = getDSL(con);
		return dsl
			.delete(TIMETABLE_STAMP)
			.where(
				TIMETABLE_STAMP.DOMAIN_ID.eq(domainId).and(
					TIMETABLE_STAMP.ENTRANCE.between(fromDate, toDate).or(
						TIMETABLE_STAMP.EXIT.between(fromDate, toDate)
					)
				)
			)
			.execute();
	}
}
