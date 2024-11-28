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
import com.sonicle.webtop.drm.bol.OTimetableReport;
import com.sonicle.webtop.drm.jooq.Sequences;
import static com.sonicle.webtop.drm.jooq.Tables.TIMETABLE_REPORT_TEMP;
import com.sonicle.webtop.drm.jooq.tables.records.TimetableReportTempRecord;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.jooq.DSLContext;

/**
 *
 * @author lssndrvs
 */
public class TimetableReportDAO extends BaseDAO{
	
	private final static TimetableReportDAO INSTANCE = new TimetableReportDAO();

	public static TimetableReportDAO getInstance() {
		return INSTANCE;
	}
	
	public int insert(Connection con, OTimetableReport item) throws DAOException {
		DSLContext dsl = getDSL(con);
		TimetableReportTempRecord record = dsl.newRecord(TIMETABLE_REPORT_TEMP, item);
		
		return dsl
				.insertInto(TIMETABLE_REPORT_TEMP)
				.set(record)
				.execute();
	}
	
	public int update(Connection con, OTimetableReport item) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.update(TIMETABLE_REPORT_TEMP)
				.set(TIMETABLE_REPORT_TEMP.WORKING_HOURS, item.getWorkingHours())
				.set(TIMETABLE_REPORT_TEMP.OVERTIME, item.getOvertime())
				.set(TIMETABLE_REPORT_TEMP.PAID_LEAVE, item.getPaidLeave())
				.set(TIMETABLE_REPORT_TEMP.UNPAID_LEAVE, item.getUnpaidLeave())
				.set(TIMETABLE_REPORT_TEMP.HOLIDAY, item.getHoliday())
				.set(TIMETABLE_REPORT_TEMP.MEDICAL_VISIT, item.getMedicalVisit())
				.set(TIMETABLE_REPORT_TEMP.CONTRACTUAL, item.getContractual())
				.set(TIMETABLE_REPORT_TEMP.CAUSAL, item.getCausal())
				.set(TIMETABLE_REPORT_TEMP.HOUR, item.getHour())
				.set(TIMETABLE_REPORT_TEMP.DETAIL, item.getDetail())
				.set(TIMETABLE_REPORT_TEMP.NOTE, item.getNote())
				.set(TIMETABLE_REPORT_TEMP.SICKNESS, item.getSickness())
				.set(TIMETABLE_REPORT_TEMP.OTHER, item.getOther())
				.set(TIMETABLE_REPORT_TEMP.TICKET, item.getTicket())
				.set(TIMETABLE_REPORT_TEMP.CAUSAL_ID, item.getCausalId())
				.where(
						TIMETABLE_REPORT_TEMP.ID.equal(item.getId())
				)
				.execute();
	}
	
	public List<OTimetableReport> selectByDomainIdUserIdMonthYear(Connection con, String domainId, String userId, LocalDate fD, LocalDate tD) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(TIMETABLE_REPORT_TEMP)
				.where(TIMETABLE_REPORT_TEMP.DOMAIN_ID.equal(domainId))
				.and(TIMETABLE_REPORT_TEMP.USER_ID.equal(userId))
				.and(TIMETABLE_REPORT_TEMP.DATE.between(fD.toLocalDateTime(LocalTime.MIDNIGHT), tD.toLocalDateTime(LocalTime.MIDNIGHT).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59)))
				.orderBy(
						TIMETABLE_REPORT_TEMP.USER_ID,
						TIMETABLE_REPORT_TEMP.TARGET_USER_ID,
						TIMETABLE_REPORT_TEMP.DATE
				)
				.fetchInto(OTimetableReport.class);
	}
	
	public List<OTimetableReport> selectByDomainIdUserIdCompanyIdMonthYear(Connection con, String domainId, String userId, Integer companyId, LocalDate fD, LocalDate tD) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(TIMETABLE_REPORT_TEMP)
				.where(TIMETABLE_REPORT_TEMP.DOMAIN_ID.equal(domainId))
				.and(TIMETABLE_REPORT_TEMP.USER_ID.equal(userId))
				.and(TIMETABLE_REPORT_TEMP.COMPANY_ID.equal(companyId))
				.and(TIMETABLE_REPORT_TEMP.DATE.between(fD.toLocalDateTime(LocalTime.MIDNIGHT), tD.toLocalDateTime(LocalTime.MIDNIGHT).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59)))
				.orderBy(
						TIMETABLE_REPORT_TEMP.USER_ID,
						TIMETABLE_REPORT_TEMP.TARGET_USER_ID,
						TIMETABLE_REPORT_TEMP.DATE
				)
				.fetchInto(OTimetableReport.class);
	}
	
	public int deleteByDomainIdUserIdMonthYear(Connection con, String domainId, String userId, LocalDate fD, LocalDate tD) {
		DSLContext dsl = getDSL(con);
		return dsl
				.delete(TIMETABLE_REPORT_TEMP)
				.where(
						TIMETABLE_REPORT_TEMP.DOMAIN_ID.equal(domainId)
				).and(
						TIMETABLE_REPORT_TEMP.USER_ID.equal(userId)
				)
				.and(
						TIMETABLE_REPORT_TEMP.DATE.between(fD.toLocalDateTime(LocalTime.MIDNIGHT), tD.toLocalDateTime(LocalTime.MIDNIGHT).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59))
				)
				.execute();
	}
	
	public List<OTimetableReport> selectByDomainIdUserIdTargetUserIdMonthYear(Connection con, String domainId, String userId, String targetUserId, LocalDate fD, LocalDate tD) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(TIMETABLE_REPORT_TEMP)
				.where(TIMETABLE_REPORT_TEMP.DOMAIN_ID.equal(domainId))
				.and(TIMETABLE_REPORT_TEMP.USER_ID.equal(userId))
				.and(TIMETABLE_REPORT_TEMP.TARGET_USER_ID.equal(targetUserId))
				.and(TIMETABLE_REPORT_TEMP.DATE.between(fD.toLocalDateTime(LocalTime.MIDNIGHT), tD.toLocalDateTime(LocalTime.MIDNIGHT).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59)))
				.orderBy(
						TIMETABLE_REPORT_TEMP.USER_ID,
						TIMETABLE_REPORT_TEMP.TARGET_USER_ID,
						TIMETABLE_REPORT_TEMP.DATE
				)
				.fetchInto(OTimetableReport.class);
	}
	
	public List<OTimetableReport> selectByDomainIdUserIdCompanyIdTargetUserIdMonthYear(Connection con, String domainId, String userId, Integer companyId, String targetUserId, LocalDate fD, LocalDate tD) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(TIMETABLE_REPORT_TEMP)
				.where(TIMETABLE_REPORT_TEMP.DOMAIN_ID.equal(domainId))
				.and(TIMETABLE_REPORT_TEMP.USER_ID.equal(userId))
				.and(TIMETABLE_REPORT_TEMP.COMPANY_ID.equal(companyId))
				.and(TIMETABLE_REPORT_TEMP.TARGET_USER_ID.equal(targetUserId))
				.and(TIMETABLE_REPORT_TEMP.DATE.between(fD.toLocalDateTime(LocalTime.MIDNIGHT), tD.toLocalDateTime(LocalTime.MIDNIGHT).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59)))
				.orderBy(
						TIMETABLE_REPORT_TEMP.USER_ID,
						TIMETABLE_REPORT_TEMP.TARGET_USER_ID,
						TIMETABLE_REPORT_TEMP.DATE
				)
				.fetchInto(OTimetableReport.class);
	}
	
	public int deleteByDomainIdUserIdTargetUserIdMonthYear(Connection con, String domainId, String userId, String targetUserId, LocalDate fD, LocalDate tD) {
		DSLContext dsl = getDSL(con);
		return dsl
				.delete(TIMETABLE_REPORT_TEMP)
				.where(
						TIMETABLE_REPORT_TEMP.DOMAIN_ID.equal(domainId)
				).and(
						TIMETABLE_REPORT_TEMP.USER_ID.equal(userId)
				).and(
						TIMETABLE_REPORT_TEMP.TARGET_USER_ID.equal(targetUserId)
				)
				.and(
						TIMETABLE_REPORT_TEMP.DATE.between(fD.toLocalDateTime(LocalTime.MIDNIGHT), tD.toLocalDateTime(LocalTime.MIDNIGHT).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59))
				)
				.execute();
	}
	
	public Long getTimetableReportTempSequence(Connection con) throws DAOException {
		DSLContext dsl = getDSL(con);
		Long nextID = dsl.nextval(Sequences.SEQ_TIMETABLE_REPORT_TEMP);
		return nextID;
	}
	
	public void restartTimetableReportTempSequence(Connection con) throws DAOException {
		DSLContext dsl = getDSL(con);
		dsl.alterSequence(Sequences.SEQ_TIMETABLE_REPORT_TEMP).restart().execute();
	}
}
