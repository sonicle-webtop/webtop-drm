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

import com.sonicle.commons.EnumUtils;
import com.sonicle.webtop.core.dal.BaseDAO;
import com.sonicle.webtop.core.dal.DAOException;
import com.sonicle.webtop.drm.ActivityType;
import com.sonicle.webtop.drm.bol.OActivity;
import static com.sonicle.webtop.drm.jooq.Sequences.SEQ_ACTIVITIES;
import static com.sonicle.webtop.drm.jooq.Tables.ACTIVITIES;
import com.sonicle.webtop.drm.jooq.tables.records.ActivitiesRecord;
import java.sql.Connection;
import java.util.List;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

/**
 *
 * @author dnllr
 */
public class ActivityDAO extends BaseDAO{
	private final static ActivityDAO INSTANCE = new ActivityDAO();

	public static ActivityDAO getInstance() {
		return INSTANCE;
	}
	
	public Long getSequence(Connection con) throws DAOException {
		DSLContext dsl = getDSL(con);
		Long nextID = dsl.nextval(SEQ_ACTIVITIES);
		return nextID;
	}
	
	public int insert(Connection con, OActivity item) throws DAOException {
		DSLContext dsl = getDSL(con);
		ActivitiesRecord record = dsl.newRecord(ACTIVITIES,item);
		
		return dsl
			.insertInto(ACTIVITIES)
			.set(record)
			.execute();
	}

	public List<OActivity> selectByDomain(Connection con, String domainId, ActivityType type) throws DAOException{
		DSLContext dsl = getDSL(con);
			
		Condition searchCndt = ensureViewCondition(type);

		return dsl
			.select()
			.from(ACTIVITIES)
			.where(
				ACTIVITIES.DOMAIN_ID.equal(domainId)
				.and(searchCndt)
			)
			.fetchInto(OActivity.class);
	}

	private Condition ensureViewCondition(ActivityType type) {
		if (ActivityType.JOBS.equals(type)) {
			return ACTIVITIES.JOBS.isTrue();
			
		} else if (ActivityType.OPPORTUNITIES.equals(type)) {
			return ACTIVITIES.OPPORTUNITIES.isTrue();
			
		} else {
			return DSL.trueCondition();
		}		
	}
	
	public OActivity selectById(Connection con, int activityId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
			.select()
			.from(ACTIVITIES)
			.where(ACTIVITIES.ACTIVITY_ID.equal(activityId))
			.fetchOneInto(OActivity.class);
	}
	
	public int update(Connection con, OActivity item) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
			.update(ACTIVITIES)
			.set(ACTIVITIES.EXTERNAL_ID, item.getExternalId())
			.set(ACTIVITIES.DESCRIPTION, item.getDescription())
			.set(ACTIVITIES.CUSTOMER, item.getCustomer())
			.set(ACTIVITIES.TIMETABLE, item.getTimetable())
			.set(ACTIVITIES.JOBS, item.getJobs())
			.set(ACTIVITIES.OPPORTUNITIES, item.getOpportunities())
			.set(ACTIVITIES.EXPORTABLE, item.getExportable())
			.where(
				ACTIVITIES.ACTIVITY_ID.equal(item.getActivityId())
			)
			.execute();
	}

	public int deleteById(Connection con, int activityId) {
		DSLContext dsl = getDSL(con);
		return dsl
			.delete(ACTIVITIES)
			.where(
				ACTIVITIES.ACTIVITY_ID.equal(activityId)
			)
			.execute();
	}
	
}
