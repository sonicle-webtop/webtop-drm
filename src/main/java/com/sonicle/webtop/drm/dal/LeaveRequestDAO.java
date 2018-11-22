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
import com.sonicle.webtop.drm.LeaveRequestQuery;
import com.sonicle.webtop.drm.bol.OLeaveRequest;
import com.sonicle.webtop.drm.jooq.Sequences;
import static com.sonicle.webtop.drm.jooq.Sequences.SEQ_LEAVE_REQUESTS;
import static com.sonicle.webtop.drm.jooq.Tables.LEAVE_REQUESTS;
import com.sonicle.webtop.drm.jooq.tables.records.LeaveRequestsRecord;
import java.sql.Connection;
import java.util.List;
import org.joda.time.DateTime;
import org.jooq.Condition;
import org.jooq.DSLContext;

/**
 *
 * @author lssndrvs
 */
public class LeaveRequestDAO extends BaseDAO {

	private final static LeaveRequestDAO INSTANCE = new LeaveRequestDAO();

	public static LeaveRequestDAO getInstance() {
		return INSTANCE;
	}

	public Long getSequence(Connection con) throws DAOException {
		DSLContext dsl = getDSL(con);
		Long nextID = dsl.nextval(SEQ_LEAVE_REQUESTS);
		return nextID;
	}
	
	public int insert(Connection con, OLeaveRequest item, DateTime revisionTimestamp) throws DAOException {
		DSLContext dsl = getDSL(con);

		item.setEmployeeReqTimestamp(revisionTimestamp);

		LeaveRequestsRecord record = dsl.newRecord(LEAVE_REQUESTS, item);
		return dsl
				.insertInto(LEAVE_REQUESTS)
				.set(record)
				.execute();
	}

	public List<OLeaveRequest> selectLeaveRequests(Connection con, LeaveRequestQuery query, String domainId, boolean isManager) throws DAOException {
		DSLContext dsl = getDSL(con);

		Condition searchCndt = ensureCondition(query, isManager);

		return dsl
				.select()
				.from(LEAVE_REQUESTS)
				.where(
						searchCndt
				).and(
						LEAVE_REQUESTS.DOMAIN_ID.equal(domainId)
				)
				.fetchInto(OLeaveRequest.class);
	}
	
	public List<OLeaveRequest> selectLeaveRequestsForManager(Connection con, LeaveRequestQuery query, String domainId) throws DAOException {
		DSLContext dsl = getDSL(con);

		Condition searchCndt = ensureConditionForManager(query);

		return dsl
				.select()
				.from(LEAVE_REQUESTS)
				.where(
						searchCndt
				).and(
						LEAVE_REQUESTS.DOMAIN_ID.equal(domainId)
				)
				.fetchInto(OLeaveRequest.class);
	}

	public OLeaveRequest selectById(Connection con, Integer leaveRequestId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(LEAVE_REQUESTS)
				.where(LEAVE_REQUESTS.LEAVE_REQUEST_ID.equal(leaveRequestId))
				.fetchOneInto(OLeaveRequest.class);
	}

	public int update(Connection con, OLeaveRequest item) throws DAOException {
		DSLContext dsl = getDSL(con);
		
		return dsl
				.update(LEAVE_REQUESTS)
				.set(LEAVE_REQUESTS.TYPE, item.getType())
				.set(LEAVE_REQUESTS.FROM_DATE, item.getFromDate())
				.set(LEAVE_REQUESTS.TO_DATE, item.getToDate())
				.set(LEAVE_REQUESTS.FROM_HOUR, item.getFromHour())
				.set(LEAVE_REQUESTS.TO_HOUR, item.getToHour())
				.set(LEAVE_REQUESTS.STATUS, item.getStatus())
				.set(LEAVE_REQUESTS.NOTES, item.getNotes())
				.set(LEAVE_REQUESTS.EMPLOYEE_REQ_TIMESTAMP, item.getEmployeeReqTimestamp())
				.set(LEAVE_REQUESTS.MANAGER_RESP_TIMESTAMP, item.getManagerRespTimestamp())
				.set(LEAVE_REQUESTS.RESULT, item.getResult())
				.set(LEAVE_REQUESTS.EMPLOYEE_CANC_REQ, item.getEmployeeCancReq())
				.set(LEAVE_REQUESTS.EMPLOYEE_CANC_REQ_TIMESTAMP, item.getEmployeeCancReqTimestamp())
				.set(LEAVE_REQUESTS.MANAGER_CANC_RESP_TIMETAMP, item.getManagerCancRespTimetamp())
				.set(LEAVE_REQUESTS.TO_DATE, item.getToDate())
				.set(LEAVE_REQUESTS.CANC_REASON, item.getCancReason())
				.set(LEAVE_REQUESTS.CANC_RESULT, item.getCancResult())
				.set(LEAVE_REQUESTS.EVENT_ID, item.getEventId())
				.where(
						LEAVE_REQUESTS.LEAVE_REQUEST_ID.equal(item.getLeaveRequestId())
				)
				.execute();
	}

	public int deleteById(Connection con, Integer id) {
		DSLContext dsl = getDSL(con);
		return dsl
				.delete(LEAVE_REQUESTS)
				.where(
						LEAVE_REQUESTS.LEAVE_REQUEST_ID.equal(id)
				)
				.execute();
	}

	public Long getLeaveRequestSequence(Connection con) throws DAOException {
		DSLContext dsl = getDSL(con);
		Long nextID = dsl.nextval(Sequences.SEQ_LEAVE_REQUESTS);
		return nextID;
	}

	private Condition ensureCondition(LeaveRequestQuery query, boolean isManager) {
								
		Condition searchCndt = (isManager) ? LEAVE_REQUESTS.MANAGER_ID.equal(query.userId) : LEAVE_REQUESTS.USER_ID.equal(query.userId);
		
		if (query.companyId != null) {
			searchCndt = searchCndt.and(LEAVE_REQUESTS.COMPANY_ID.equal(query.companyId));
		}
		
		if (query.fromDate != null) {
			searchCndt = searchCndt.and(LEAVE_REQUESTS.FROM_DATE.greaterOrEqual(query.fromDate));
		}

		if (query.toDate != null) {
			searchCndt = searchCndt.and(LEAVE_REQUESTS.TO_DATE.lessOrEqual(query.toDate));
		}
		
		if (query.type  != null) {
			searchCndt = searchCndt.and(LEAVE_REQUESTS.TYPE.equal(query.type));
		}
		
		if (query.status != null) {
			searchCndt = searchCndt.and(LEAVE_REQUESTS.STATUS.equal(query.status));
		}
		
		if (query.result != null) {
			searchCndt = searchCndt.and(LEAVE_REQUESTS.RESULT.equal(query.result));
		}

		return searchCndt;
	}
	
	private Condition ensureConditionForManager(LeaveRequestQuery query) {
								
		Condition searchCndt = LEAVE_REQUESTS.MANAGER_ID.notEqual(query.userId); 
		searchCndt = searchCndt.and(LEAVE_REQUESTS.USER_ID.equal(query.userId));
		
		if (query.companyId != null) {
			searchCndt = searchCndt.and(LEAVE_REQUESTS.COMPANY_ID.equal(query.companyId));
		}
		
		if (query.fromDate != null) {
			searchCndt = searchCndt.and(LEAVE_REQUESTS.FROM_DATE.greaterOrEqual(query.fromDate));
		}

		if (query.toDate != null) {
			searchCndt = searchCndt.and(LEAVE_REQUESTS.TO_DATE.lessOrEqual(query.toDate));
		}
		
		if (query.type  != null) {
			searchCndt = searchCndt.and(LEAVE_REQUESTS.TYPE.equal(query.type));
		}
		
		if (query.status != null) {
			searchCndt = searchCndt.and(LEAVE_REQUESTS.STATUS.equal(query.status));
		}
		
		if (query.result != null) {
			searchCndt = searchCndt.and(LEAVE_REQUESTS.RESULT.equal(query.result));
		}

		return searchCndt;
	}

	public int updateRequestCancellation(Connection con, Integer leaveRequestId, String cancellationReason, DateTime revisionTimestamp) {
		DSLContext dsl = getDSL(con);
		
		return dsl
				.update(LEAVE_REQUESTS)
				.set(LEAVE_REQUESTS.EMPLOYEE_CANC_REQ, true)
				.set(LEAVE_REQUESTS.EMPLOYEE_CANC_REQ_TIMESTAMP, revisionTimestamp)
				.set(LEAVE_REQUESTS.CANC_REASON, cancellationReason)
				.where(
						LEAVE_REQUESTS.LEAVE_REQUEST_ID.equal(leaveRequestId)
				)
				.execute();
	}
}
