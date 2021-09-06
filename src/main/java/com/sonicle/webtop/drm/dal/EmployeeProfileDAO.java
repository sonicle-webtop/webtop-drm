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
import com.sonicle.webtop.drm.bol.OEmployeeProfile;
import com.sonicle.webtop.drm.jooq.Sequences;
import static com.sonicle.webtop.drm.jooq.Tables.EMPLOYEE_PROFILES;
import com.sonicle.webtop.drm.jooq.tables.records.EmployeeProfilesRecord;
import java.sql.Connection;
import java.util.List;
import org.jooq.DSLContext;

/**
 *
 * @author lssndrvs
 */
public class EmployeeProfileDAO extends BaseDAO {

	private final static EmployeeProfileDAO INSTANCE = new EmployeeProfileDAO();

	public static EmployeeProfileDAO getInstance() {
		return INSTANCE;
	}
	
	public Long getSequence(Connection con) throws DAOException {
		DSLContext dsl = getDSL(con);
		Long nextID = dsl.nextval(Sequences.SEQ_EMPLOYEE_PROFILES);
		return nextID;
	}

	public int insert(Connection con, OEmployeeProfile item) throws DAOException {
		DSLContext dsl = getDSL(con);
		EmployeeProfilesRecord record = dsl.newRecord(EMPLOYEE_PROFILES, item);

		return dsl
				.insertInto(EMPLOYEE_PROFILES)
				.set(record)
				.execute();
	}

	public List<OEmployeeProfile> selectEmployeeProfileByDomain(Connection con, String domainId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(EMPLOYEE_PROFILES)
				.where(
						EMPLOYEE_PROFILES.DOMAIN_ID.equal(domainId)
				)
                .orderBy(EMPLOYEE_PROFILES.USER_ID.asc())
				.fetchInto(OEmployeeProfile.class);
	}
	
	public OEmployeeProfile selectEmployeeProfileByDomainUser(Connection con, String domainId, String userId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(EMPLOYEE_PROFILES)
				.where(
						EMPLOYEE_PROFILES.DOMAIN_ID.equal(domainId)
				)
				.and(
						EMPLOYEE_PROFILES.USER_ID.equal(userId)
				)
				.fetchOneInto(OEmployeeProfile.class);
	}

	public OEmployeeProfile selectEmployeeProfileById(Connection con, Integer id) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(EMPLOYEE_PROFILES)
				.where(
						EMPLOYEE_PROFILES.ID.equal(id)
				)
				.fetchOneInto(OEmployeeProfile.class);
	}

	public OEmployeeProfile selectEmployeeProfileByNumber(Connection con, String number) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(EMPLOYEE_PROFILES)
				.where(
						EMPLOYEE_PROFILES.NUMBER.equal(number)
				)
				.fetchOneInto(OEmployeeProfile.class);
	}
	
	public List<OEmployeeProfile> selectEmployeeProfilesByNumbers(Connection con, List<String> numbers) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(EMPLOYEE_PROFILES)
				.where(
						EMPLOYEE_PROFILES.NUMBER.in(numbers)
				)
				.fetchInto(OEmployeeProfile.class);
	}
	
	public int update(Connection con, OEmployeeProfile item) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.update(EMPLOYEE_PROFILES)
				.set(EMPLOYEE_PROFILES.EXTRAORDINARY, item.getExtraordinary())
				.set(EMPLOYEE_PROFILES.ONLY_PRESENCE, item.getOnlyPresence())
				.set(EMPLOYEE_PROFILES.NUMBER, item.getNumber())
				.set(EMPLOYEE_PROFILES.TOLERANCE, item.getTolerance())
				.set(EMPLOYEE_PROFILES.USER_ID, item.getUserId())
				.set(EMPLOYEE_PROFILES.HOUR_PROFILE_ID, item.getHourProfileId())
				.where(
						EMPLOYEE_PROFILES.ID.equal(item.getId())
				)
				.execute();
	}

	public int deleteById(Connection con, Integer id) {
		DSLContext dsl = getDSL(con);
		return dsl
			.delete(EMPLOYEE_PROFILES)
			.where(
					EMPLOYEE_PROFILES.ID.equal(id)
			)
			.execute();
	}

}
