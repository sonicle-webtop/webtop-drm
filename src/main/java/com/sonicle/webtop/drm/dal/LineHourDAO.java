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
import com.sonicle.webtop.drm.bol.OLineHour;
import com.sonicle.webtop.drm.jooq.Sequences;
import static com.sonicle.webtop.drm.jooq.Tables.LINE_HOURS;
import com.sonicle.webtop.drm.jooq.tables.records.LineHoursRecord;
import java.sql.Connection;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record4;
import org.jooq.Table;
import org.jooq.impl.DSL;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.name;
import static org.jooq.impl.DSL.table;
/**
 *
 * @author lssndrvs
 */
public class LineHourDAO extends BaseDAO {

	private final static LineHourDAO INSTANCE = new LineHourDAO();

	public static LineHourDAO getInstance() {
		return INSTANCE;
	}

	public Long getSequence(Connection con) throws DAOException {
		DSLContext dsl = getDSL(con);
		Long nextID = dsl.nextval(Sequences.SEQ_LINE_HOURS);
		return nextID;
	}
	
	public int insert(Connection con, OLineHour item) throws DAOException {
		DSLContext dsl = getDSL(con);
		LineHoursRecord record = dsl.newRecord(LINE_HOURS, item);

		return dsl
				.insertInto(LINE_HOURS)
				.set(record)
				.execute();
	}

	public OLineHour selectLineHourById(Connection con, Integer id) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(LINE_HOURS)
				.where(
						LINE_HOURS.ID.equal(id)
				)
				.fetchOneInto(OLineHour.class);
	}
	
	public List<OLineHour> selectLineHourByHourProfileId(Connection con, Integer hourProfileId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(LINE_HOURS)
				.where(
						LINE_HOURS.HOUR_PROFILE_ID.equal(hourProfileId)
				)
				.fetchInto(OLineHour.class);
	}
	
	public String selectSumLineHourByHourProfileIdDayOfWeek(Connection con, Integer hourProfileId, Integer dayOfWeek) throws DAOException {
		DSLContext dsl = getDSL(con);
		
		return dsl
				.select(
						DSL.sum(getDayFieldByDayOfWeek(dayOfWeek).cast(Integer.class))
				)
				.from(LINE_HOURS)
				.where(
						LINE_HOURS.HOUR_PROFILE_ID.equal(hourProfileId)
				)
				.fetchOneInto(String.class);
	}

	public int update(Connection con, OLineHour item) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.update(LINE_HOURS)
				.set(LINE_HOURS._1_E, item.get_1E())
				.set(LINE_HOURS._1_U, item.get_1U())
				.set(LINE_HOURS._1_H, item.get_1H())
				.set(LINE_HOURS._2_E, item.get_2E())
				.set(LINE_HOURS._2_U, item.get_2U())
				.set(LINE_HOURS._2_H, item.get_2H())
				.set(LINE_HOURS._3_E, item.get_3E())
				.set(LINE_HOURS._3_U, item.get_3U())
				.set(LINE_HOURS._3_H, item.get_3H())
				.set(LINE_HOURS._4_E, item.get_4E())
				.set(LINE_HOURS._4_U, item.get_4U())
				.set(LINE_HOURS._4_H, item.get_4H())
				.set(LINE_HOURS._5_E, item.get_5E())
				.set(LINE_HOURS._5_U, item.get_5U())
				.set(LINE_HOURS._5_H, item.get_5H())
				.set(LINE_HOURS._6_E, item.get_6E())
				.set(LINE_HOURS._6_U, item.get_6U())
				.set(LINE_HOURS._6_H, item.get_6H())
				.set(LINE_HOURS._7_E, item.get_7E())
				.set(LINE_HOURS._7_U, item.get_7U())
				.set(LINE_HOURS._7_H, item.get_7H())
				.where(
						LINE_HOURS.ID.equal(item.getId())
				)
				.execute();
	}

	public int deleteById(Connection con, Integer id) {
		DSLContext dsl = getDSL(con);
		return dsl
			.delete(LINE_HOURS)
			.where(
					LINE_HOURS.ID.equal(id)
			)
			.execute();
	}
	
	public int deleteByHourProfileId(Connection con, Integer hourProfileId) {
		DSLContext dsl = getDSL(con);
		return dsl
			.delete(LINE_HOURS)
			.where(
					LINE_HOURS.HOUR_PROFILE_ID.equal(hourProfileId)
			)
			.execute();
	}
	
	public int selectHourRangeIntersectionWithLineHoursInMinutes(
			Connection con,
			Integer hourProfileId,
			LocalDate d,   // "YYYY-MM-DD"
			String timeFrom,  // "HH:mm"
			String timeTo     // "HH:mm"
	) throws DAOException {
		return selectHourRangeIntersectionWithLineHoursInMinutes(con, hourProfileId, d, timeFrom, timeTo, DateTimeZone.getDefault());
	}

	// Overload that allows explicit timezone (helpful around DST transitions)
	public int selectHourRangeIntersectionWithLineHoursInMinutes(
			Connection con,
			Integer hourProfileId,
			LocalDate d,
			String timeFrom,
			String timeTo,
			DateTimeZone zone
	) throws DAOException {
		try {
			DSLContext dsl = getDSL(con);

			// Parse inputs with Joda
			final LocalTime pFrom = LocalTime.parse(timeFrom);    // "HH:mm"
			final LocalTime pTo   = LocalTime.parse(timeTo);      // "HH:mm"

			// 1 = Monday ... 7 = Sunday
			final int i = d.dayOfWeek().get(); // Joda returns 1..7 with Monday=1

			// jOOQ table/fields
			final Table<?> LINE_HOURS = table(name("drm", "line_hours"));
			final Field<Integer> HOUR_PROFILE_ID = field(name("drm", "line_hours", "hour_profile_id"), Integer.class);
			final Field<Integer> LINE_ID         = field(name("drm", "line_hours", "line_id"), Integer.class);
			final Field<String>  F_E             = field(name("drm", "line_hours", i + "_e"), String.class);
			final Field<String>  F_U             = field(name("drm", "line_hours", i + "_u"), String.class);

			// Fetch needed rows
			final List<Record4<Integer, Integer, String, String>> rows = dsl
				.select(HOUR_PROFILE_ID, LINE_ID, F_E, F_U)
				.from(LINE_HOURS)
				.where(HOUR_PROFILE_ID.eq(hourProfileId))
				.fetch();

			// Compose permission interval as instants (DateTime) in the chosen zone
			final LocalDateTime pFromLdt = d.toLocalDateTime(pFrom);
			final LocalDateTime pToLdt   = (pTo.compareTo(pFrom) >= 0)
					? d.toLocalDateTime(pTo)
					: d.plusDays(1).toLocalDateTime(pTo);

			final DateTime pFromTs = pFromLdt.toDateTime(zone);
			final DateTime pToTs   = pToLdt.toDateTime(zone);

			long totalSeconds = 0L;

			for (Record4<Integer, Integer, String, String> r : rows) {
				final String sFrom = r.value3();
				final String sTo   = r.value4();
				if (sFrom == null || sTo == null) continue; // no range for that line/day

				final LocalTime rFrom = LocalTime.parse(sFrom);
				final LocalTime rTo   = LocalTime.parse(sTo);

				final LocalDateTime rFromLdt = d.toLocalDateTime(rFrom);
				final LocalDateTime rToLdt   = (rTo.compareTo(rFrom) >= 0)
						? d.toLocalDateTime(rTo)
						: d.plusDays(1).toLocalDateTime(rTo);

				final DateTime rFromTs = rFromLdt.toDateTime(zone);
				final DateTime rToTs   = rToLdt.toDateTime(zone);

				// Overlap = max(0, min(pTo, rTo) - max(pFrom, rFrom))
				final DateTime start = (pFromTs.isAfter(rFromTs)) ? pFromTs : rFromTs;
				final DateTime end   = (pToTs.isBefore(rToTs))     ? pToTs   : rToTs;

				if (end.isAfter(start)) {
					final Duration dur = new Duration(start, end);
					totalSeconds += dur.getStandardSeconds();
				}
			}

			// Return minutes
			return (int)(totalSeconds / 60);

		} catch (Exception ex) {
			throw new DAOException("Error computing hour range intersection (Joda-Time)", ex);
		}
	}

	private Field<String> getDayFieldByDayOfWeek(int dayOfWeek){		
		switch(dayOfWeek){
			case 1:
				return LINE_HOURS._1_H;
			case 2: 
				return LINE_HOURS._2_H;
			case 3: 
				return LINE_HOURS._3_H;
			case 4: 
				return LINE_HOURS._4_H;
			case 5: 
				return LINE_HOURS._5_H;
			case 6: 
				return LINE_HOURS._6_H;
			case 7: 
				return LINE_HOURS._7_H;
			case 0: 
				return LINE_HOURS._7_H;
			default: 
				return LINE_HOURS._1_H;
		}
	}

}
