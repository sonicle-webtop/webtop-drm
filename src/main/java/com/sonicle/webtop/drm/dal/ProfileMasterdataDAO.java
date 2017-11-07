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
import com.sonicle.webtop.drm.bol.OCompanyUser;
import com.sonicle.webtop.drm.bol.OProfileMasterdata;
import static com.sonicle.webtop.drm.jooq.Sequences.SEQ_PROFILES_MASTERDATA;
import static com.sonicle.webtop.drm.jooq.Tables.PROFILES_MASTERDATA;
import static com.sonicle.webtop.drm.jooq.Tables.PROFILES_MEMBERS;
import com.sonicle.webtop.drm.jooq.tables.records.ProfilesMasterdataRecord;
import java.sql.Connection;
import java.util.List;
import org.jooq.DSLContext;

/**
 *
 * @author stfnnvl
 */
public class ProfileMasterdataDAO extends BaseDAO {

	private final static ProfileMasterdataDAO INSTANCE = new ProfileMasterdataDAO();

	public static ProfileMasterdataDAO getInstance() {
		return INSTANCE;
	}

	public Long getSequence(Connection con) throws DAOException {
		DSLContext dsl = getDSL(con);
		Long nextID = dsl.nextval(SEQ_PROFILES_MASTERDATA);
		return nextID;
	}

	public List<OProfileMasterdata> selectByProfile(Connection con, String profileId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(PROFILES_MASTERDATA)
				.where(
						PROFILES_MASTERDATA.PROFILE_ID.equal(profileId)
				)
				.fetchInto(OProfileMasterdata.class);
	}

	public int insert(Connection con, OProfileMasterdata item) throws DAOException {
		DSLContext dsl = getDSL(con);
		ProfilesMasterdataRecord record = dsl.newRecord(PROFILES_MASTERDATA, item);

		return dsl
				.insertInto(PROFILES_MASTERDATA)
				.set(record)
				.execute();
	}

	public int deleteById(Connection con, int id) {
		DSLContext dsl = getDSL(con);

		return dsl
				.delete(PROFILES_MASTERDATA)
				.where(
						PROFILES_MASTERDATA.ID.equal(id)
				)
				.execute();
	}

	public int deleteByProfile(Connection con, String profileId) {
		DSLContext dsl = getDSL(con);

		return dsl
				.delete(PROFILES_MASTERDATA)
				.where(
						PROFILES_MASTERDATA.PROFILE_ID.equal(profileId)
				)
				.execute();
	}
	
	public List<String> selectCustomersByProfileUser(Connection con, String userId) {
		DSLContext dsl = getDSL(con);
		
		return dsl
			.select(PROFILES_MASTERDATA.MASTER_DATA_ID)
			.from(PROFILES_MASTERDATA)
			.join(PROFILES_MEMBERS).on(
				PROFILES_MASTERDATA.PROFILE_ID.equal(PROFILES_MEMBERS.PROFILE_ID)
			)
			.where(
					PROFILES_MEMBERS.USER_ID.equal(userId)
			)
			.fetchInto(String.class);
	}
	
	public String checkCustomersByProfileUser(Connection con, String realCustomerId, String userId) {
		DSLContext dsl = getDSL(con);
		
		return dsl
			.select(PROFILES_MASTERDATA.ID)
			.from(PROFILES_MASTERDATA)
			.join(PROFILES_MEMBERS).on(
				PROFILES_MASTERDATA.PROFILE_ID.equal(PROFILES_MEMBERS.PROFILE_ID)
			)
			.where(
				PROFILES_MASTERDATA.MASTER_DATA_ID.equal(realCustomerId)
			).and(
				PROFILES_MEMBERS.USER_ID.equal(userId)
			)
			.fetchOneInto(String.class);
	}
}
