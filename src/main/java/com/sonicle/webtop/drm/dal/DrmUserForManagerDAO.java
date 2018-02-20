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
import com.sonicle.webtop.drm.bol.ODrmLineManagerUsers;
import static com.sonicle.webtop.drm.jooq.Tables.LINE_MANAGER_USERS;
import com.sonicle.webtop.drm.jooq.tables.records.LineManagerUsersRecord;
import java.sql.Connection;
import org.jooq.DSLContext;

/**
 *
 * @author stfnnvl
 */
public class DrmUserForManagerDAO extends BaseDAO {

	private final static DrmUserForManagerDAO INSTANCE = new DrmUserForManagerDAO();

	public static DrmUserForManagerDAO getInstance() {
		return INSTANCE;
	}
	
	public int insert(Connection con, ODrmLineManagerUsers item) throws DAOException {
		DSLContext dsl = getDSL(con);
		LineManagerUsersRecord record = dsl.newRecord(LINE_MANAGER_USERS, item);

		return dsl
				.insertInto(LINE_MANAGER_USERS)
				.set(record)
				.execute();
	}

	public int deleteByDomainIdLineManagerUserId(Connection con, String domainId, String userId) {
		DSLContext dsl = getDSL(con);

		return dsl
				.delete(LINE_MANAGER_USERS)
				.where(
						LINE_MANAGER_USERS.DOMAIN_ID.equal(domainId)
				).
				and(
						LINE_MANAGER_USERS.LINE_MANAGER_USER_ID.equal(userId)
				)
				.execute();
	}
	
	public int deleteByDomainIdLineManagerUserIdUserId(Connection con, String domainId, String lineManagerUserId, String userId) {
		DSLContext dsl = getDSL(con);

		return dsl
				.delete(LINE_MANAGER_USERS)
				.where(
						LINE_MANAGER_USERS.DOMAIN_ID.equal(domainId)
				).
				and(
						LINE_MANAGER_USERS.LINE_MANAGER_USER_ID.equal(lineManagerUserId)
				).
				and(
						LINE_MANAGER_USERS.USER_ID.equal(userId)
				)
				.execute();
	}

	public Iterable<ODrmLineManagerUsers> listByDomainLineManagerUserId(Connection con, String domainId, String userId) {
		DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(LINE_MANAGER_USERS)
				.where(
						LINE_MANAGER_USERS.DOMAIN_ID.equal(domainId)
				).and(
						LINE_MANAGER_USERS.LINE_MANAGER_USER_ID.equal(userId)
				)
				.fetchInto(ODrmLineManagerUsers.class);
	}
	
	public ODrmLineManagerUsers selectByDomainUserId(Connection con, String domainId, String userId) {
		DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(LINE_MANAGER_USERS)
				.where(
						LINE_MANAGER_USERS.DOMAIN_ID.equal(domainId)
				).and(
						LINE_MANAGER_USERS.USER_ID.equal(userId)
				)
				.fetchOneInto(ODrmLineManagerUsers.class);
	}
}
