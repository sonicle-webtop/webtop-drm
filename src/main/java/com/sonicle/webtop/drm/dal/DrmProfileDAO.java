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
import com.sonicle.webtop.drm.bol.ODrmGroup;
import com.sonicle.webtop.drm.bol.ODrmProfile;
import static com.sonicle.webtop.drm.jooq.Tables.PROFILES;
import static com.sonicle.webtop.drm.jooq.Tables.PROFILES_MEMBERS;
import com.sonicle.webtop.drm.jooq.tables.ProfilesMembers;
import com.sonicle.webtop.drm.jooq.tables.records.ProfilesRecord;
import java.sql.Connection;
import java.util.List;
import org.apache.commons.lang3.BooleanUtils;
import org.jooq.DSLContext;

/**
 *
 * @author lssndrvs
 */
public class DrmProfileDAO extends BaseDAO {

	private final static DrmProfileDAO INSTANCE = new DrmProfileDAO();

	public static DrmProfileDAO getInstance() {
		return INSTANCE;
	}

	public int insert(Connection con, ODrmProfile item) throws DAOException {
		DSLContext dsl = getDSL(con);
		ProfilesRecord record = dsl.newRecord(PROFILES, item);

		return dsl
				.insertInto(PROFILES)
				.set(record)
				.execute();
	}

	public List<ODrmProfile> selectProfileByDomain(Connection con, String domainId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(PROFILES)
				.where(
						PROFILES.DOMAIN_ID.equal(domainId)
				)
				.orderBy(PROFILES.DESCRIPTION)
				.fetchInto(ODrmProfile.class);
	}

	public ODrmProfile selectProfileTypeByUserId(Connection con, String domainId, String userId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(PROFILES)
				.join(PROFILES_MEMBERS)
				.on(
						PROFILES_MEMBERS.PROFILE_ID.equal(PROFILES.PROFILE_ID)
				).where(
						PROFILES.DOMAIN_ID.equal(domainId)
						.and(
								PROFILES_MEMBERS.USER_ID.like(userId)
						)
				)
				.fetchOneInto(ODrmProfile.class);
	}

	public ODrmProfile selectById(Connection con, String profileId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(PROFILES)
				.where(PROFILES.PROFILE_ID.equal(profileId))
				.fetchOneInto(ODrmProfile.class);
	}

	public int update(Connection con, ODrmProfile item) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.update(PROFILES)
				.set(PROFILES.DESCRIPTION, item.getDescription())
				.where(
						PROFILES.PROFILE_ID.equal(item.getProfileId())
				)
				.execute();
	}

	public int deleteById(Connection con, String profileId) {
		DSLContext dsl = getDSL(con);
		return dsl
			.delete(PROFILES)
			.where(
					PROFILES.PROFILE_ID.equal(profileId)
			)
			.execute();
	}

}
