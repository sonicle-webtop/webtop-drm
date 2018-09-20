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
import com.sonicle.webtop.drm.OpportunityQuery;
import com.sonicle.webtop.drm.bol.OOpportunity;
import com.sonicle.webtop.drm.jooq.Sequences;
import static com.sonicle.webtop.drm.jooq.Tables.OPPORTUNITIES;
import com.sonicle.webtop.drm.jooq.tables.records.OpportunitiesRecord;
import java.sql.Connection;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.DatePart;

/**
 *
 * @author lssndrvs
 */
public class OpportunityDAO extends BaseDAO {

	private final static OpportunityDAO INSTANCE = new OpportunityDAO();

	public static OpportunityDAO getInstance() {
		return INSTANCE;
	}

	public int insert(Connection con, OOpportunity item) throws DAOException {
		DSLContext dsl = getDSL(con);

		OpportunitiesRecord record = dsl.newRecord(OPPORTUNITIES, item);
		return dsl
				.insertInto(OPPORTUNITIES)
				.set(record)
				.execute();
	}

	public List<OOpportunity> selectOpportunities(Connection con, OpportunityQuery query) throws DAOException {
		DSLContext dsl = getDSL(con);

		Condition searchCndt = ensureCondition(query);

		return dsl
				.select()
				.from(OPPORTUNITIES)
				.where(
						searchCndt
				)
				.fetchInto(OOpportunity.class);
	}

	public OOpportunity selectById(Connection con, int id) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(OPPORTUNITIES)
				.where(OPPORTUNITIES.ID.equal(id))
				.fetchOneInto(OOpportunity.class);
	}

	public int update(Connection con, OOpportunity item) throws DAOException {
		DSLContext dsl = getDSL(con);
		
		return dsl
				.update(OPPORTUNITIES)
				.set(OPPORTUNITIES.COMPANY_ID, item.getCompanyId())
				.set(OPPORTUNITIES.OPERATOR_ID, item.getOperatorId())
				.set(OPPORTUNITIES.DATE, item.getDate())
				.set(OPPORTUNITIES.FROM_HOUR, item.getFromHour())
				.set(OPPORTUNITIES.TO_HOUR, item.getToHour())
				.set(OPPORTUNITIES.EXECUTED_WITH, item.getExecutedWith())
				.set(OPPORTUNITIES.CUSTOMER_ID, item.getCustomerId())
				.set(OPPORTUNITIES.CUSTOMER_STAT_ID, item.getCustomerStatId())
				.set(OPPORTUNITIES.SECTOR, item.getSector())
				.set(OPPORTUNITIES.DESCRIPTION, item.getDescription())
				.set(OPPORTUNITIES.PLACE, item.getPlace())
				.set(OPPORTUNITIES.OBJECTIVE, item.getObjective())
				.set(OPPORTUNITIES.CAUSAL_ID, item.getCausalId())
				.set(OPPORTUNITIES.ACTIVITY_ID, item.getActivityId())
				.set(OPPORTUNITIES.OBJECTIVE_2, item.getObjective_2())
				.set(OPPORTUNITIES.RESULT, item.getResult())
				.set(OPPORTUNITIES.DISCOVERIES, item.getDiscoveries())
				.set(OPPORTUNITIES.CUSTOMER_POTENTIAL, item.getCustomerPotential())
				.set(OPPORTUNITIES.NOTES, item.getNotes())
				.set(OPPORTUNITIES.STATUS_ID, item.getStatusId())
				.set(OPPORTUNITIES.SIGNED_BY, item.getSignedBy())
				.set(OPPORTUNITIES.SIGNATURE, item.getSignature())
				.set(OPPORTUNITIES.WON, item.getWon())
				.where(
						OPPORTUNITIES.ID.equal(item.getId())
				)
				.execute();
	}

	public int deleteById(Connection con, int id) {
		DSLContext dsl = getDSL(con);
		return dsl
				.delete(OPPORTUNITIES)
				.where(
						OPPORTUNITIES.ID.equal(id)
				)
				.execute();
	}

	public Long getOpportunitySequence(Connection con) throws DAOException {
		DSLContext dsl = getDSL(con);
		
		Long nextID = dsl.nextval(Sequences.SEQ_OPPORTUNITIES);
		
		return nextID;
	}

	private Condition ensureCondition(OpportunityQuery query) {
								
		Condition searchCndt = OPPORTUNITIES.OPERATOR_ID.equal(query.operatorId);
		
		if (query.companyId != null) {
			searchCndt = searchCndt.and(OPPORTUNITIES.COMPANY_ID.equal(query.companyId));
		}
		
		if (query.date != null) {
			searchCndt = searchCndt.and(OPPORTUNITIES.DATE.greaterOrEqual(query.date));
		}
		
		if (!StringUtils.isEmpty(query.fromHour)) {
			searchCndt = searchCndt.and(OPPORTUNITIES.FROM_HOUR.equal(query.fromHour));
		}
		
		if (!StringUtils.isEmpty(query.toHour)) {
			searchCndt = searchCndt.and(OPPORTUNITIES.TO_HOUR.equal(query.toHour));
		}
		
		if (!StringUtils.isEmpty(query.executedWith)) {
			searchCndt = searchCndt.and(OPPORTUNITIES.EXECUTED_WITH.equal(query.executedWith));
		}
		
		if (!StringUtils.isEmpty(query.customerId)) {
			searchCndt = searchCndt.and(OPPORTUNITIES.CUSTOMER_ID.equal(query.customerId));
		}
		
		if (!StringUtils.isEmpty(query.customerStatId)) {
			searchCndt = searchCndt.and(OPPORTUNITIES.CUSTOMER_STAT_ID.equal(query.customerStatId));
		}
		
		if (!StringUtils.isEmpty(query.sector)) {
			searchCndt = searchCndt.and(OPPORTUNITIES.SECTOR.equal(query.sector));
		}
		
		if (!StringUtils.isEmpty(query.description)) {
			searchCndt = searchCndt.and(OPPORTUNITIES.DESCRIPTION.equal(query.description));
		}
		
		if (!StringUtils.isEmpty(query.place)) {
			searchCndt = searchCndt.and(OPPORTUNITIES.PLACE.equal(query.place));
		}
		
		if (!StringUtils.isEmpty(query.objective)) {
			searchCndt = searchCndt.and(OPPORTUNITIES.OBJECTIVE.equal(query.objective));
		}
		
		if (query.causalId != null) {
			searchCndt = searchCndt.and(OPPORTUNITIES.CAUSAL_ID.equal(query.causalId));
		}
		
		if (query.activityId != null) {
			searchCndt = searchCndt.and(OPPORTUNITIES.ACTIVITY_ID.equal(query.activityId));
		}

		if (!StringUtils.isEmpty(query.objective2)) {
			searchCndt = searchCndt.and(OPPORTUNITIES.OBJECTIVE_2.equal(query.objective2));
		}
		
		if (!StringUtils.isEmpty(query.result)) {
			searchCndt = searchCndt.and(OPPORTUNITIES.RESULT.equal(query.result));
		}
		
		if (!StringUtils.isEmpty(query.discoveries)) {
			searchCndt = searchCndt.and(OPPORTUNITIES.DISCOVERIES.equal(query.description));
		}
		
		if (!StringUtils.isEmpty(query.customerPotential)) {
			searchCndt = searchCndt.and(OPPORTUNITIES.CUSTOMER_POTENTIAL.equal(query.customerPotential));
		}
		
		if (!StringUtils.isEmpty(query.notes)) {
			searchCndt = searchCndt.and(OPPORTUNITIES.NOTES.equal(query.notes));
		}
		
		if (query.statusId != null) {
			searchCndt = searchCndt.and(OPPORTUNITIES.STATUS_ID.equal(query.statusId));
		}
		
		if (!StringUtils.isEmpty(query.signedBy)) {
			searchCndt = searchCndt.and(OPPORTUNITIES.SIGNED_BY.equal(query.signedBy));
		}

		if (query.signature != null) {
			searchCndt = searchCndt.and(OPPORTUNITIES.SIGNATURE.equal(query.signature));
		}
		
		if (query.won != null) {
			searchCndt = searchCndt.and(OPPORTUNITIES.WON.equal(query.won));
		}
		
		return searchCndt;
	}
}