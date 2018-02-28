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
import org.jooq.DSLContext;

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

}
