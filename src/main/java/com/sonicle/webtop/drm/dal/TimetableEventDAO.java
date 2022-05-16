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
import com.sonicle.webtop.drm.bol.OTimetableEvent;
import static com.sonicle.webtop.drm.jooq.Sequences.SEQ_TIMETABLE_EVENT;
import static com.sonicle.webtop.drm.jooq.Tables.TIMETABLE_EVENTS;
import com.sonicle.webtop.drm.jooq.tables.records.TimetableEventsRecord;
import java.sql.Connection;
import java.util.List;
import org.joda.time.DateTime;
import org.jooq.DSLContext;

/**
 *
 * @author lssndrvs
 */
public class TimetableEventDAO extends BaseDAO {

	private final static TimetableEventDAO INSTANCE = new TimetableEventDAO();

	public static TimetableEventDAO getInstance() {
		return INSTANCE;
	}

	public Long getSequence(Connection con) throws DAOException {
		DSLContext dsl = getDSL(con);
		Long nextID = dsl.nextval(SEQ_TIMETABLE_EVENT);
		return nextID;
	}
	
	public int insert(Connection con, OTimetableEvent item) throws DAOException {
		DSLContext dsl = getDSL(con);

		TimetableEventsRecord record = dsl.newRecord(TIMETABLE_EVENTS, item);
		return dsl
				.insertInto(TIMETABLE_EVENTS)
				.set(record)
				.execute();
	}

	public List<OTimetableEvent> getEventsByDomainUserDateRange(Connection con, String domainId, Integer companyId, String userId, Integer fromDay, Integer month, Integer year) {
		DSLContext dsl = getDSL(con);
		return dsl
				.select(
						TIMETABLE_EVENTS.DOMAIN_ID, 
						TIMETABLE_EVENTS.COMPANY_ID,
						TIMETABLE_EVENTS.USER_ID, 
						TIMETABLE_EVENTS.DATE,
						TIMETABLE_EVENTS.HOUR,
						TIMETABLE_EVENTS.TYPE
				)
				.from(
						TIMETABLE_EVENTS
				)
				.where(
						TIMETABLE_EVENTS.DOMAIN_ID.equal(domainId)
				)
				.and(
						TIMETABLE_EVENTS.USER_ID.equal(userId)
				)
				.and(
						TIMETABLE_EVENTS.DATE.between(new DateTime().withYear(year).withMonthOfYear(month).withDayOfMonth(fromDay).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).toLocalDate(), new DateTime().withYear(year).withMonthOfYear(month).dayOfMonth().withMaximumValue().withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).toLocalDate())
				)
				.and(
						TIMETABLE_EVENTS.COMPANY_ID.equal(companyId)
				)
				.groupBy(
						TIMETABLE_EVENTS.DOMAIN_ID, 
						TIMETABLE_EVENTS.COMPANY_ID,
						TIMETABLE_EVENTS.USER_ID, 
						TIMETABLE_EVENTS.DATE,
						TIMETABLE_EVENTS.HOUR,
						TIMETABLE_EVENTS.TYPE,
						TIMETABLE_EVENTS.LEAVE_REQUEST_ID
				)
				.orderBy(
						TIMETABLE_EVENTS.USER_ID, 
						TIMETABLE_EVENTS.DATE
				)
				.fetchInto(OTimetableEvent.class);
	}
	
	public int deleteById(Connection con, Integer id) {
		DSLContext dsl = getDSL(con);
		return dsl
				.delete(TIMETABLE_EVENTS)
				.where(
						TIMETABLE_EVENTS.TIMETABLE_EVENT_ID.equal(id)
				)
				.execute();
	}
	
	public int deleteByLeaveRequestId(Connection con, Integer leaveRequestId) {
		DSLContext dsl = getDSL(con);
		return dsl
				.delete(TIMETABLE_EVENTS)
				.where(
						TIMETABLE_EVENTS.LEAVE_REQUEST_ID.equal(leaveRequestId)
				)
				.execute();
	}
}
