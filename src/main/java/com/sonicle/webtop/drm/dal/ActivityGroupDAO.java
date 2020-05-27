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
import com.sonicle.webtop.drm.bol.OActivityGroup;
import com.sonicle.webtop.drm.jooq.tables.records.ActivitiesGroupsRecord;
import java.sql.Connection;
import java.util.List;
import org.jooq.DSLContext;
import static com.sonicle.webtop.drm.jooq.Sequences.SEQ_ACTIVITIES_GROUPS;
import static com.sonicle.webtop.drm.jooq.Tables.ACTIVITIES_GROUPS;

/**
 *
 * @author dnllr
 */
public class ActivityGroupDAO extends BaseDAO {
	private final static ActivityGroupDAO INSTANCE = new ActivityGroupDAO();

	public static ActivityGroupDAO getInstance() {
		return INSTANCE;
	}

	public Long getSequence(Connection con) throws DAOException {
		DSLContext dsl = getDSL(con);
		Long nextID = dsl.nextval(SEQ_ACTIVITIES_GROUPS);
		return nextID;
	}

	public List<OActivityGroup> selectByActivity(Connection con, int activityId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(ACTIVITIES_GROUPS)
				.where(ACTIVITIES_GROUPS.ACTIVITY_ID.equal(activityId)
				)
				.fetchInto(OActivityGroup.class);
	}

	public int insert(Connection con, OActivityGroup item) throws DAOException {
		DSLContext dsl = getDSL(con);
		ActivitiesGroupsRecord record = dsl.newRecord(ACTIVITIES_GROUPS, item);

		return dsl
				.insertInto(ACTIVITIES_GROUPS)
				.set(record)
				.execute();
	}

	public int deleteById(Connection con, int associationId) {
		DSLContext dsl = getDSL(con);

		return dsl
				.delete(ACTIVITIES_GROUPS)
				.where(ACTIVITIES_GROUPS.ASSOCIATION_ID.equal(associationId)
				)
				.execute();
	}
	
	public int deleteByActivity(Connection con, int activityId) {
		DSLContext dsl = getDSL(con);

		return dsl
				.delete(ACTIVITIES_GROUPS)
				.where(ACTIVITIES_GROUPS.ACTIVITY_ID.equal(activityId)
				)
				.execute();
	}
}