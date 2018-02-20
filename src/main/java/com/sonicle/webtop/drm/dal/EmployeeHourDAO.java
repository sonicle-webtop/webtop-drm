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
import com.sonicle.webtop.drm.bol.OEmployeeHour;
import com.sonicle.webtop.drm.jooq.Sequences;
import static com.sonicle.webtop.drm.jooq.Tables.EMPLOYEE_HOURS;
import com.sonicle.webtop.drm.jooq.tables.records.EmployeeHoursRecord;
import com.sonicle.webtop.drm.jooq.tables.records.EmployeeProfilesRecord;
import java.sql.Connection;
import java.util.List;
import org.jooq.DSLContext;

/**
 *
 * @author stfnnvl
 */
public class EmployeeHourDAO extends BaseDAO {

	private final static EmployeeHourDAO INSTANCE = new EmployeeHourDAO();

	public static EmployeeHourDAO getInstance() {
		return INSTANCE;
	}

	public Long getSequence(Connection con) throws DAOException {
		DSLContext dsl = getDSL(con);
		Long nextID = dsl.nextval(Sequences.SEQ_EMPLOYEE_HOURS);
		return nextID;
	}
	
	public int insert(Connection con, OEmployeeHour item) throws DAOException {
		DSLContext dsl = getDSL(con);
		EmployeeHoursRecord record = dsl.newRecord(EMPLOYEE_HOURS, item);

		return dsl
				.insertInto(EMPLOYEE_HOURS)
				.set(record)
				.execute();
	}

	public OEmployeeHour selectEmployeeHourById(Connection con, Integer id) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(EMPLOYEE_HOURS)
				.where(
						EMPLOYEE_HOURS.ID.equal(id)
				)
				.fetchOneInto(OEmployeeHour.class);
	}
	
	public List<OEmployeeHour> selectEmployeeHourByEmployeeProfileId(Connection con, Integer employeeProfileId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(EMPLOYEE_HOURS)
				.where(
						EMPLOYEE_HOURS.EMPLOYEE_PROFILE_ID.equal(employeeProfileId)
				)
				.fetchInto(OEmployeeHour.class);
	}

	public int update(Connection con, OEmployeeHour item) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.update(EMPLOYEE_HOURS)
				.set(EMPLOYEE_HOURS._1_E, item.get_1E())
				.set(EMPLOYEE_HOURS._1_U, item.get_1U())
				.set(EMPLOYEE_HOURS._1_H, item.get_1H())
				.set(EMPLOYEE_HOURS._2_E, item.get_2E())
				.set(EMPLOYEE_HOURS._2_U, item.get_2U())
				.set(EMPLOYEE_HOURS._2_H, item.get_2H())
				.set(EMPLOYEE_HOURS._3_E, item.get_3E())
				.set(EMPLOYEE_HOURS._3_U, item.get_3U())
				.set(EMPLOYEE_HOURS._3_H, item.get_3H())
				.set(EMPLOYEE_HOURS._4_E, item.get_4E())
				.set(EMPLOYEE_HOURS._4_U, item.get_4U())
				.set(EMPLOYEE_HOURS._4_H, item.get_4H())
				.set(EMPLOYEE_HOURS._5_E, item.get_5E())
				.set(EMPLOYEE_HOURS._5_U, item.get_5U())
				.set(EMPLOYEE_HOURS._5_H, item.get_5H())
				.set(EMPLOYEE_HOURS._6_E, item.get_6E())
				.set(EMPLOYEE_HOURS._6_U, item.get_6U())
				.set(EMPLOYEE_HOURS._6_H, item.get_6H())
				.set(EMPLOYEE_HOURS._7_E, item.get_7E())
				.set(EMPLOYEE_HOURS._7_U, item.get_7U())
				.set(EMPLOYEE_HOURS._7_H, item.get_7H())
				.where(
						EMPLOYEE_HOURS.ID.equal(item.getId())
				)
				.execute();
	}

	public int deleteById(Connection con, Integer id) {
		DSLContext dsl = getDSL(con);
		return dsl
			.delete(EMPLOYEE_HOURS)
			.where(
					EMPLOYEE_HOURS.ID.equal(id)
			)
			.execute();
	}
	
	public int deleteByEmployeeProfileId(Connection con, Integer employeeProfileId) {
		DSLContext dsl = getDSL(con);
		return dsl
			.delete(EMPLOYEE_HOURS)
			.where(
					EMPLOYEE_HOURS.EMPLOYEE_PROFILE_ID.equal(employeeProfileId)
			)
			.execute();
	}

}
