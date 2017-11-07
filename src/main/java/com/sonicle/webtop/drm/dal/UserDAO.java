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

import com.sonicle.webtop.core.bol.OUser;
import com.sonicle.webtop.core.dal.BaseDAO;
import com.sonicle.webtop.core.dal.DAOException;
import static com.sonicle.webtop.core.jooq.core.Tables.USERS;
import static com.sonicle.webtop.drm.jooq.Tables.PROFILES;
import static com.sonicle.webtop.drm.jooq.Tables.PROFILES_MEMBERS;
import static com.sonicle.webtop.drm.jooq.Tables.PROFILES_SUPERVISED_USERS;

import java.sql.Connection;
import java.util.List;
import org.jooq.DSLContext;

/**
 *
 * @author lssndrvs
 */
public class UserDAO extends BaseDAO {
	
	private final static UserDAO INSTANCE = new UserDAO();

	public static UserDAO getInstance() {
		return INSTANCE;
	}
	
	public List<String> selectUserSupervisedByDomain(Connection con, String domain, String user) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
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
						PROFILES.DOMAIN_ID.equal(domain)
						.and(PROFILES_MEMBERS.USER_ID.equal(user)
				))
				.groupBy(
					PROFILES_SUPERVISED_USERS.USER_ID
				)
				.fetchInto(String.class);
	}
	
}
