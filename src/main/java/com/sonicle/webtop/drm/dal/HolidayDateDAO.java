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
import com.sonicle.webtop.drm.bol.OHolidayDate;
import com.sonicle.webtop.drm.bol.OWorkReportRow;
import com.sonicle.webtop.drm.bol.OWorkType;
import static com.sonicle.webtop.drm.jooq.Tables.HOLIDAY_DATE;
import com.sonicle.webtop.drm.jooq.tables.records.HolidayDateRecord;
import com.sonicle.webtop.drm.jooq.tables.records.WorkReportsRowsRecord;
import com.sonicle.webtop.drm.jooq.tables.records.WorkTypesRecord;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

/**
 *
 * @author lssndrvs
 */
public class HolidayDateDAO extends BaseDAO {

	private final static HolidayDateDAO INSTANCE = new HolidayDateDAO();

	public static HolidayDateDAO getInstance() {
		return INSTANCE;
	}

	public List<OHolidayDate> selectByDomain(Connection con, String domainId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(HOLIDAY_DATE)
				.where(
						HOLIDAY_DATE.DOMAIN_ID.equal(domainId)
				)
				.orderBy(HOLIDAY_DATE.DATE.asc())
				.fetchInto(OHolidayDate.class);
	}
	
	/*
	public Map<LocalDate, List<OHolidayDate>> selectByDomainMap(Connection con, String domainId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(HOLIDAY_DATE)
				.where(
						HOLIDAY_DATE.DOMAIN_ID.equal(domainId)
				)
				.orderBy(HOLIDAY_DATE.DATE.asc())
				.fetchGroups(HOLIDAY_DATE.DATE, OHolidayDate.class);
	}
	*/
	
	public OHolidayDate selectByDomainDate(Connection con, String domainId, DateTime date) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(HOLIDAY_DATE)
				.where(
						HOLIDAY_DATE.DOMAIN_ID.equal(domainId)
				).and(
						HOLIDAY_DATE.DATE.eq(date.toLocalDate())
				)
				.fetchOneInto(OHolidayDate.class);
	}
	
	public OHolidayDate selectByDomainDateWithoutYear(Connection con, String domainId, DateTime date) throws DAOException {
		String pattern = "MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);		
		
		DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(HOLIDAY_DATE)
				.where(
						HOLIDAY_DATE.DOMAIN_ID.equal(domainId)
				).and(
						DSL.field("SUBSTRING(CAST({0} AS VARCHAR), 6)", String.class, HOLIDAY_DATE.DATE).eq(sdf.format(date.toDate()))
				)
				.fetchOneInto(OHolidayDate.class);
	}	
	
	public int insert(Connection con, OHolidayDate item) throws DAOException {
		DSLContext dsl = getDSL(con);

		HolidayDateRecord record = dsl.newRecord(HOLIDAY_DATE, item);

		return dsl
				.insertInto(HOLIDAY_DATE)
				.set(record)
				.execute();
	}

	public int updateByDomainId(Connection con, OHolidayDate item) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.update(HOLIDAY_DATE)
				.set(HOLIDAY_DATE.DATE, item.getDate())
				.set(HOLIDAY_DATE.DESCRIPTION, item.getDescription())
				.where(
						HOLIDAY_DATE.DOMAIN_ID.equal(item.getDomainId())
				)
				.execute();
	}
	
	public int updateByDomainIdDate(Connection con, OHolidayDate item) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.update(HOLIDAY_DATE)
				.set(HOLIDAY_DATE.DESCRIPTION, item.getDescription())
				.where(
						HOLIDAY_DATE.DOMAIN_ID.equal(item.getDomainId())
				).and(
						HOLIDAY_DATE.DATE.eq(item.getDate())
				)
				.execute();
	}

	public int deleteByDomainId(Connection con, String domainId) {
		DSLContext dsl = getDSL(con);

		return dsl
				.delete(HOLIDAY_DATE)
				.where(
						HOLIDAY_DATE.DOMAIN_ID.equal(domainId)
				)
				.execute();
	}
	
	public int deleteByDomainIdDate(Connection con, String domainId, DateTime date) {
		DSLContext dsl = getDSL(con);

		return dsl
				.delete(HOLIDAY_DATE)
				.where(
						HOLIDAY_DATE.DOMAIN_ID.equal(domainId)
				).and(
						HOLIDAY_DATE.DATE.eq(date.toLocalDate())
				)
				.execute();
	}
}
