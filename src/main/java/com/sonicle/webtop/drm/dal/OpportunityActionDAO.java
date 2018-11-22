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
import com.sonicle.webtop.drm.bol.OOpportunityAction;
import static com.sonicle.webtop.drm.jooq.Sequences.SEQ_OPPORTUNITY_ACTIONS;
import static com.sonicle.webtop.drm.jooq.Tables.DOC_STATUSES;
import static com.sonicle.webtop.drm.jooq.Tables.OPPORTUNITY_ACTIONS;
import com.sonicle.webtop.drm.jooq.tables.records.OpportunityActionsRecord;
import java.sql.Connection;
import java.util.List;
import org.jooq.DSLContext;

/**
 *
 * @author lssndrvs
 */
public class OpportunityActionDAO extends BaseDAO {

	private final static OpportunityActionDAO INSTANCE = new OpportunityActionDAO();

	public static OpportunityActionDAO getInstance() {
		return INSTANCE;
	}

	public Long getSequence(Connection con) throws DAOException {
		DSLContext dsl = getDSL(con);
		Long nextID = dsl.nextval(SEQ_OPPORTUNITY_ACTIONS);
		return nextID;
	}

	public OOpportunityAction select(Connection con, int id) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(OPPORTUNITY_ACTIONS)
				.where(
						OPPORTUNITY_ACTIONS.ID.equal(id)
				)
				.fetchOneInto(OOpportunityAction.class);
	}
	
	public List<OOpportunityAction> selectByOpportunity(Connection con, int id) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(OPPORTUNITY_ACTIONS)
				.where(
						OPPORTUNITY_ACTIONS.OPPORTUNITY_ID.equal(id)
				)
				.fetchInto(OOpportunityAction.class);
	}
	
	public int countByStatusOpenOpportunityId(Connection con, int id) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.selectCount()
				.from(OPPORTUNITY_ACTIONS)
				.join(
					DOC_STATUSES
				)
				.on(
						OPPORTUNITY_ACTIONS.STATUS_ID.equal(DOC_STATUSES.DOC_STATUS_ID)
				)
				.where(
						OPPORTUNITY_ACTIONS.OPPORTUNITY_ID.equal(id)
				)
				.and(
						DOC_STATUSES.TYPE.equal("O")
				)
				.fetchOneInto(int.class);
	}

	public int insert(Connection con, OOpportunityAction item) throws DAOException {
		DSLContext dsl = getDSL(con);

		OpportunityActionsRecord record = dsl.newRecord(OPPORTUNITY_ACTIONS, item);

		return dsl
				.insertInto(OPPORTUNITY_ACTIONS)
				.set(record)
				.execute();
	}

	public int update(Connection con, OOpportunityAction item) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.update(OPPORTUNITY_ACTIONS)
				.set(OPPORTUNITY_ACTIONS.OPERATOR_ID, item.getOperatorId())
				.set(OPPORTUNITY_ACTIONS.STATUS_ID, item.getStatusId())
				.set(OPPORTUNITY_ACTIONS.DATE, item.getDate())
				.set(OPPORTUNITY_ACTIONS.FROM_HOUR, item.getFromHour())
				.set(OPPORTUNITY_ACTIONS.TO_HOUR, item.getToHour())
				.set(OPPORTUNITY_ACTIONS.DESCRIPTION, item.getDescription())
				.set(OPPORTUNITY_ACTIONS.PLACE, item.getPlace())
				.set(OPPORTUNITY_ACTIONS.SUBSEQUENT_ACTIONS, item.getSubsequentActions())
				.set(OPPORTUNITY_ACTIONS.ACTIVITY_ID, item.getActivityId())
				.set(OPPORTUNITY_ACTIONS.EVENT_ID, item.getEventId())
				.where(
						OPPORTUNITY_ACTIONS.ID.equal(item.getId())
				)
				.execute();
	}

	public int deleteById(Connection con, int id) {
		DSLContext dsl = getDSL(con);

		return dsl
				.delete(OPPORTUNITY_ACTIONS)
				.where(
						OPPORTUNITY_ACTIONS.ID.equal(id)
				)
				.execute();
	}

	public int deleteByOpportunity(Connection con, int id) {
		DSLContext dsl = getDSL(con);

		return dsl
				.delete(OPPORTUNITY_ACTIONS)
				.where(
						OPPORTUNITY_ACTIONS.OPPORTUNITY_ID.equal(id)
				)
				.execute();
	}
}
