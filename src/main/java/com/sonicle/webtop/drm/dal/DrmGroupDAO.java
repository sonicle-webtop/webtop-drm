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
import com.sonicle.webtop.drm.bol.OCompany;
import com.sonicle.webtop.drm.bol.ODrmGroup;
import static com.sonicle.webtop.drm.jooq.Tables.GROUPS;
import static com.sonicle.webtop.drm.jooq.Tables.GROUPS;
import com.sonicle.webtop.drm.jooq.tables.records.CompaniesRecord;
import com.sonicle.webtop.drm.jooq.tables.records.GroupsRecord;
import java.sql.Connection;
import java.util.List;
import org.jooq.DSLContext;

/**
 *
 * @author stfnnvl
 */
public class DrmGroupDAO extends BaseDAO{
	
	private final static DrmGroupDAO INSTANCE = new DrmGroupDAO();

	public static DrmGroupDAO getInstance() {
		return INSTANCE;
	}
	
	public int insert(Connection con,ODrmGroup item) throws DAOException {
		DSLContext dsl = getDSL(con);
		GroupsRecord record = dsl.newRecord(GROUPS,item);
		
		return dsl
				.insertInto(GROUPS)
				.set(record)
				.execute();
	}

	public List<ODrmGroup> selectGroupsByDomain(Connection con, String domainId) throws DAOException{
			DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(GROUPS)
				.where(
						GROUPS.DOMAIN_ID.equal(domainId)
				)
				.fetchInto(ODrmGroup.class);
	}
	public List<ODrmGroup> selectGroupsByDomainCategory(Connection con, String domainId, String groupCategory) throws DAOException{
			DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(GROUPS)
				.where(
						GROUPS.DOMAIN_ID.equal(domainId)
						.and(
								GROUPS.GROUP_CATEGORY.equal(groupCategory)
						)
				)
				.fetchInto(ODrmGroup.class);
	}
	
	public ODrmGroup selectById(Connection con, String groupId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
			.select()
			.from(GROUPS)
			.where(GROUPS.GROUP_ID.equal(groupId))
			.fetchOneInto(ODrmGroup.class);
	}
	
	public int update(Connection con, ODrmGroup item) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
			.update(GROUPS)
 			.set(GROUPS.NAME,item.getName())
			.set(GROUPS.DESCRIPTION,item.getDescription())
			.set(GROUPS.GROUP_TYPE,item.getGroupType())
			.set(GROUPS.SUPERVISIOR_USER_ID,item.getSupervisiorUserId())
			.set(GROUPS.CUSTOMER_ID,item.getCustomerId())
			.where(
				GROUPS.GROUP_ID.equal(item.getGroupId())
			)
			.execute();
	}

	public int deleteById(Connection con, String groupId ) {
		DSLContext dsl = getDSL(con);
		return dsl
			.delete(GROUPS)
			.where(
				GROUPS.GROUP_ID.equal(groupId)
			)
			.execute();
	}
	
}
