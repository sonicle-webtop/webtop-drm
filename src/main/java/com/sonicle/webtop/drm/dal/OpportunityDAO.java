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
import com.sonicle.webtop.drm.bol.VOpportunityEntry;
import com.sonicle.webtop.drm.jooq.Sequences;
import static com.sonicle.webtop.drm.jooq.Tables.OPPORTUNITIES;
import static com.sonicle.webtop.drm.jooq.Tables.OPPORTUNITY_ACTIONS;
import static com.sonicle.webtop.drm.jooq.Tables.PROFILES;
import static com.sonicle.webtop.drm.jooq.Tables.PROFILES_MEMBERS;
import static com.sonicle.webtop.drm.jooq.Tables.PROFILES_SUPERVISED_USERS;
import com.sonicle.webtop.drm.jooq.tables.records.OpportunitiesRecord;
import java.sql.Connection;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;

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

	public List<OOpportunity> selectOpportunities(Connection con, OpportunityQuery query, String domainId, String userId) throws DAOException {
		DSLContext dsl = getDSL(con);

		Condition searchCndt = ensureCondition(query, dsl, domainId, userId);

		return dsl
				.select()
				.from(OPPORTUNITIES)
				.where(
						searchCndt
				)
				.fetchInto(OOpportunity.class);
	}
	
	public List<VOpportunityEntry> viewOpportunitiesAndActions(Connection con, OpportunityQuery query, String domainId, String userId) throws DAOException {
		DSLContext dsl = getDSL(con);

		Condition searchCndt = ensureCondition(query, dsl, domainId, userId);
		
		Field<Integer> fldOpp = DSL.val(0).as("action_id");
		Field<Integer> fldAct = OPPORTUNITY_ACTIONS.ID.as("action_id");
		Field<LocalDate> fldXDate = DSL.val(new LocalDate(1970, 1, 1)).as("x_date");

		return dsl
				.select(
						OPPORTUNITIES.DOMAIN_ID,
						OPPORTUNITIES.ID,
						fldOpp,
						OPPORTUNITIES.COMPANY_ID,
						OPPORTUNITIES.OPERATOR_ID,
						OPPORTUNITIES.START_DATE,
						fldXDate,
						OPPORTUNITIES.END_DATE,
						OPPORTUNITIES.EXECUTED_WITH,
						OPPORTUNITIES.CUSTOMER_ID,
						OPPORTUNITIES.CUSTOMER_STAT_ID,
						OPPORTUNITIES.SECTOR,
						OPPORTUNITIES.DESCRIPTION,
						OPPORTUNITIES.PLACE,
						OPPORTUNITIES.OBJECTIVE,
						OPPORTUNITIES.CAUSAL_ID,
						OPPORTUNITIES.ACTIVITY_ID,
						OPPORTUNITIES.OBJECTIVE_2,
						OPPORTUNITIES.RESULT,
						OPPORTUNITIES.DISCOVERIES,
						OPPORTUNITIES.CUSTOMER_POTENTIAL,
						OPPORTUNITIES.NOTES,
						OPPORTUNITIES.STATUS_ID,
						OPPORTUNITIES.SIGNED_BY,
						OPPORTUNITIES.SIGNATURE,
						OPPORTUNITIES.SUCCESS
				)
				.from(OPPORTUNITIES)
				.where(
						searchCndt
				)
				.unionAll(
						dsl.select(
								OPPORTUNITIES.DOMAIN_ID,
								OPPORTUNITIES.ID,
								fldAct,
								OPPORTUNITIES.COMPANY_ID,
								OPPORTUNITY_ACTIONS.OPERATOR_ID,
								OPPORTUNITY_ACTIONS.START_DATE,
								OPPORTUNITY_ACTIONS.START_DATE.as("x_date"),
								OPPORTUNITY_ACTIONS.END_DATE,
								OPPORTUNITIES.EXECUTED_WITH,
								OPPORTUNITIES.CUSTOMER_ID,
								OPPORTUNITIES.CUSTOMER_STAT_ID,
								OPPORTUNITIES.SECTOR,
								OPPORTUNITY_ACTIONS.DESCRIPTION,
								OPPORTUNITY_ACTIONS.PLACE,
								OPPORTUNITIES.OBJECTIVE,
								OPPORTUNITIES.CAUSAL_ID,
								OPPORTUNITY_ACTIONS.ACTIVITY_ID,
								OPPORTUNITIES.OBJECTIVE_2,
								OPPORTUNITIES.RESULT,
								OPPORTUNITIES.DISCOVERIES,
								OPPORTUNITIES.CUSTOMER_POTENTIAL,
								OPPORTUNITIES.NOTES,
								OPPORTUNITY_ACTIONS.STATUS_ID,
								OPPORTUNITIES.SIGNED_BY,
								OPPORTUNITIES.SIGNATURE,
								OPPORTUNITIES.SUCCESS
						)
						.from(OPPORTUNITIES)
						.join(
								OPPORTUNITY_ACTIONS
						)
						.on(
								OPPORTUNITY_ACTIONS.OPPORTUNITY_ID.equal(OPPORTUNITIES.ID)
						)
						.where(
								searchCndt
						)
						.orderBy(
								OPPORTUNITY_ACTIONS.START_DATE
						)
				)
				.orderBy(
						OPPORTUNITIES.DOMAIN_ID,
						OPPORTUNITIES.ID,
						fldXDate,
						fldAct
				)
				.fetchInto(VOpportunityEntry.class);
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
				.set(OPPORTUNITIES.START_DATE, item.getStartDate())
				.set(OPPORTUNITIES.END_DATE, item.getEndDate())
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
				.set(OPPORTUNITIES.SUCCESS, item.getSuccess())
				.set(OPPORTUNITIES.EVENT_ID, item.getEventId())
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

	private Condition ensureCondition(OpportunityQuery query, DSLContext dsl, String domainId, String userId) {
		
		Condition searchCndt = null;
		
		if(query.operatorId != null){
			searchCndt = OPPORTUNITIES.OPERATOR_ID.equal(query.operatorId);
		}else{			
			SelectConditionStep<Record1<String>> operators = (SelectConditionStep<Record1<String>>) DSL
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
							PROFILES.DOMAIN_ID.equal(domainId)
							.and(PROFILES_MEMBERS.USER_ID.equal(userId)
					))
					.union(DSL.select(DSL.inline(userId)));
			
			searchCndt = OPPORTUNITIES.OPERATOR_ID.in(operators);
		}
		
		if (query.companyId != null) {
			searchCndt = searchCndt.and(OPPORTUNITIES.COMPANY_ID.equal(query.companyId));
		}
		
		if (query.date != null) {
			if(query.toDate != null){
				searchCndt = searchCndt.and(OPPORTUNITIES.START_DATE.between(query.date.toDateTime(LocalTime.MIDNIGHT), query.toDate.toDateTime(LocalTime.MIDNIGHT).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59)));
				searchCndt = searchCndt.or(OPPORTUNITIES.ID.in(
						dsl.select(OPPORTUNITY_ACTIONS.OPPORTUNITY_ID)
								.from(OPPORTUNITY_ACTIONS)
								.where(OPPORTUNITY_ACTIONS.START_DATE.between(query.date.toDateTime(LocalTime.MIDNIGHT), query.toDate.toDateTime(LocalTime.MIDNIGHT).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59)))
				));
			}else{
				searchCndt = searchCndt.and(OPPORTUNITIES.START_DATE.greaterOrEqual(query.date.toDateTime(LocalTime.MIDNIGHT)));
				searchCndt = searchCndt.or(OPPORTUNITIES.ID.in(
						dsl.select(OPPORTUNITY_ACTIONS.OPPORTUNITY_ID)
								.from(OPPORTUNITY_ACTIONS)
								.where(OPPORTUNITY_ACTIONS.START_DATE.greaterOrEqual(query.date.toDateTime(LocalTime.MIDNIGHT)))
				));
			}
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
		
		if (query.success != null) {
			searchCndt = searchCndt.and(OPPORTUNITIES.SUCCESS.equal(query.success));
		}
		
		return searchCndt;
	}
}
