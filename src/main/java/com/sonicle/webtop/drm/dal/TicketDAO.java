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
import com.sonicle.webtop.drm.TicketQuery;
import com.sonicle.webtop.drm.bol.ODocStatus;
import com.sonicle.webtop.drm.bol.OTicket;
import com.sonicle.webtop.drm.bol.OViewTicket;
import com.sonicle.webtop.drm.jooq.Sequences;
import static com.sonicle.webtop.drm.jooq.Tables.PROFILES;
import static com.sonicle.webtop.drm.jooq.Tables.PROFILES_MEMBERS;
import static com.sonicle.webtop.drm.jooq.Tables.PROFILES_SUPERVISED_USERS;
import static com.sonicle.webtop.drm.jooq.Tables.TICKETS;
import static com.sonicle.webtop.drm.jooq.tables.VwTickets.VW_TICKETS;
import com.sonicle.webtop.drm.jooq.tables.records.TicketsRecord;
import java.sql.Connection;
import org.joda.time.LocalTime;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;

/**
 *
 * @author dnllr
 */
public class TicketDAO extends BaseDAO {

	private final static TicketDAO INSTANCE = new TicketDAO();

	public static TicketDAO getInstance() {
		return INSTANCE;
	}	
    
	public List<OViewTicket> selectViewTickets(Connection con, TicketQuery query, String domainId, String userId) throws DAOException {
		DSLContext dsl = getDSL(con);

		Condition searchCndt = ensureViewCondition(query, domainId, userId);
        
		return dsl
				.select()
				.from(VW_TICKETS)
				.where(
						(searchCndt)
				)
				.orderBy(
						VW_TICKETS.DATE.desc()
				)
				.fetchInto(OViewTicket.class);
	}
    
	private Condition ensureViewCondition(TicketQuery query, String domainId, String userId) {		
		Condition searchCndt = null;
		
		if(query.fromOperatorId != null){
			searchCndt = VW_TICKETS.FROM_OPERATOR_ID.equal(query.fromOperatorId);
		} else {
			searchCndt = DSL.trueCondition();
		}/* else{			
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
			
			searchCndt = VW_TICKETS.FROM_OPERATOR_ID.in(operators);
		} */
		
		if(query.toOperatorId != null){
			searchCndt = searchCndt.and(VW_TICKETS.TO_OPERATOR_ID.equal(query.toOperatorId));
		}else{	
            if (query.allTicket == null || query.allTicket == false) {
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

                searchCndt = searchCndt.and(VW_TICKETS.TO_OPERATOR_ID.in(operators));
            }
		}
		
        if (query.ticketId != null) {
            searchCndt = searchCndt.and(VW_TICKETS.TICKET_ID.equal(query.ticketId));
        }
        
		if (domainId != null) {
			searchCndt = searchCndt.and(VW_TICKETS.DOMAIN_ID.equal(domainId));
		}
		
		if (query.companyId != null) {
			searchCndt = searchCndt.and(VW_TICKETS.COMPANY_ID.equal(query.companyId));
		}
		
		if (!StringUtils.isEmpty(query.customerId)) {
			searchCndt = searchCndt.and(VW_TICKETS.CUSTOMER_ID.equal(query.customerId));
		}
		
		if (!StringUtils.isEmpty(query.customerStatId)) {
			searchCndt = searchCndt.and(VW_TICKETS.CUSTOMER_STAT_ID.equal(query.customerStatId));
		}
		
		if (query.startDate != null) {
			searchCndt = searchCndt.and(VW_TICKETS.DATE.greaterOrEqual(query.startDate.toDateTime(new LocalTime(0,0,0))));			
		}
		
		if (query.endDate != null) {
			searchCndt = searchCndt.and(VW_TICKETS.DATE.lessOrEqual(query.endDate.toDateTime(new LocalTime(23,59,59))));
		}
		
		if (query.ticketCategoryId != null) {
			searchCndt = searchCndt.and(VW_TICKETS.TICKET_CATEGORY_ID.equal(query.ticketCategoryId));
		}
		
		if (!StringUtils.isEmpty(query.title)) {
			searchCndt = searchCndt.and(VW_TICKETS.TITLE.like("%" + query.title + "%"));
		}

		if (query.statusId != null) {
			searchCndt = searchCndt.and(VW_TICKETS.STATUS_ID.equal(query.statusId));
		}

		if (query.priorityId != null) {
			searchCndt = searchCndt.and(VW_TICKETS.PRIORITY_ID.equal(query.priorityId));
		}
		
		if (query.opened != null && query.opened) {
			searchCndt = searchCndt.and(VW_TICKETS.TYPE.equal(ODocStatus.STATUS_OPEN));
		}
		
		return searchCndt;
	}
	
	public OTicket selectById(Connection con, String ticketId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
			.select()
			.from(TICKETS)
			.where(
				TICKETS.TICKET_ID.equal(ticketId)
			)
			.fetchOneInto(OTicket.class);
	}
	
	public int insert(Connection con, OTicket item) throws DAOException {
		DSLContext dsl = getDSL(con);

		TicketsRecord record = dsl.newRecord(TICKETS, item);
		return dsl
				.insertInto(TICKETS)
				.set(record)
				.execute();
	}
	
	public int update(Connection con, OTicket item) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.update(TICKETS)
				.set(TICKETS.COMPANY_ID, item.getCompanyId())
				.set(TICKETS.FROM_OPERATOR_ID, item.getFromOperatorId())
				.set(TICKETS.TO_OPERATOR_ID, item.getToOperatorId())
				.set(TICKETS.CUSTOMER_ID, item.getCustomerId())
				.set(TICKETS.CUSTOMER_STAT_ID, item.getCustomerStatId())
				.set(TICKETS.TICKET_CATEGORY_ID, item.getTicketCategoryId())
				.set(TICKETS.DATE, item.getDate())
				.set(TICKETS.TIMEZONE, item.getTimezone())
				.set(TICKETS.TITLE, item.getTitle())
				.set(TICKETS.DESCRIPTION, item.getDescription())
				.set(TICKETS.EVENT_ID, item.getEventId())
				.set(TICKETS.DOMAIN_ID, item.getDomainId())
				.set(TICKETS.STATUS_ID, item.getStatusId())
				.set(TICKETS.PRIORITY_ID, item.getPriorityId())
				.set(TICKETS.RELEASE, item.getRelease())
				.set(TICKETS.ENVIRONMENT, item.getEnvironment())
				.set(TICKETS.SUGGESTION, item.getSuggestion())
				.set(TICKETS.SIMULATION, item.getSimulation())
				.where(
					TICKETS.TICKET_ID.equal(item.getTicketId())
				)
				.execute();
	}
	
	public int deleteById(Connection con, String ticketId) {
		DSLContext dsl = getDSL(con);
		return dsl
				.delete(TICKETS)
				.where(
					TICKETS.TICKET_ID.equal(ticketId)
				)
				.execute();
	}
	
	public Long getSequence(Connection con) throws DAOException {
		DSLContext dsl = getDSL(con);
		Long nextID = dsl.nextval(Sequences.SEQ_TICKETS);
		return nextID;
	}
	
	public int closeById(Connection con, String ticketId, String defaultCloseStatusId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.update(TICKETS)
				.set(TICKETS.STATUS_ID, Integer.valueOf(defaultCloseStatusId))
				.where(
					TICKETS.TICKET_ID.equal(ticketId)
				)
				.execute();		
	}
	
	public OViewTicket selectViewById(Connection con, String ticketId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
			.select()
			.from(VW_TICKETS)
			.where(
				VW_TICKETS.TICKET_ID.equal(ticketId)
			)
			.fetchOneInto(OViewTicket.class);
	}

	public List<OTicket> selectByNumberCustomer(Connection con, String number, String domainId, String customerId) throws DAOException {
		DSLContext dsl = getDSL(con);
		Condition searchCndt = null;
		
		if (customerId != null) {
			searchCndt = TICKETS.CUSTOMER_ID.equal(customerId);
		} else {
			searchCndt = DSL.trueCondition();			
		}
		
		return dsl
			.select()
			.from(TICKETS)
			.where(
				TICKETS.DOMAIN_ID.equal(domainId)				
				.and(TICKETS.NUMBER.likeIgnoreCase(number))
				.and(searchCndt)
			).orderBy(
                TICKETS.NUMBER.desc()
            )
			.fetchInto(OTicket.class);
	}
	
}
