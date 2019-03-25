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
import static com.sonicle.webtop.drm.jooq.Tables.WORK_REPORTS_ROWS;
import static com.sonicle.webtop.drm.jooq.Tables.WORK_REPORTS;
import static com.sonicle.webtop.drm.jooq.Tables.WORK_TYPES;
import com.sonicle.webtop.drm.model.WorkReport;
import com.sonicle.webtop.drm.model.WorkReportSummary;
import java.sql.Connection;
import java.util.List;
import org.joda.time.LocalDate;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

/**
 *
 * @author lssndrvs
 */
public class WorkReportSummaryDAO extends BaseDAO {

	private final static WorkReportSummaryDAO INSTANCE = new WorkReportSummaryDAO();

	public static WorkReportSummaryDAO getInstance() {
		return INSTANCE;
	}
	
	public List<WorkReportSummary> selectWorkReportSummaryByCompanyFromDateToDateStatus(Connection con, List<String> users, Integer companyId, LocalDate from, LocalDate to, Integer docStatusId) throws DAOException {
		DSLContext dsl = getDSL(con);
		
		Condition searchCndt = ensureCondition(companyId, from, to, docStatusId);
		
		return dsl
				.select(
						DSL.sum(WORK_REPORTS_ROWS.DURATION).as("duration"),
						WORK_TYPES.DESCRIPTION,
						WORK_REPORTS.OPERATOR_ID,
						WORK_REPORTS.CUSTOMER_ID,
						WORK_REPORTS.COMPANY_ID
				)
				.from(WORK_REPORTS_ROWS)
				.join(WORK_TYPES).on(WORK_REPORTS_ROWS.WORK_TYPE_ID.equal(WORK_TYPES.WORK_TYPE_ID))
				.join(WORK_REPORTS).on(WORK_REPORTS_ROWS.WORK_REPORT_ID.equal(WORK_REPORTS.WORK_REPORT_ID))
				.where(
					WORK_REPORTS.OPERATOR_ID.in(users)
				)
				.and(
						searchCndt
				)
				.groupBy(
						WORK_TYPES.DESCRIPTION,
						WORK_REPORTS.OPERATOR_ID,
						WORK_REPORTS.CUSTOMER_ID,
						WORK_REPORTS.COMPANY_ID
				)
				.orderBy(
						WORK_REPORTS.OPERATOR_ID,
						WORK_REPORTS.CUSTOMER_ID,
						WORK_TYPES.DESCRIPTION
				)
				.fetchInto(WorkReportSummary.class);
	}
	
	public List<WorkReportSummary> selectWorkReportSummaryByUserCompanyFromDateToDateStatus(Connection con, String operatorId, Integer companyId, LocalDate from, LocalDate to, Integer docStatusId) throws DAOException {
		DSLContext dsl = getDSL(con);
		Condition searchCndt = ensureCondition(companyId, from, to, docStatusId);
		
		return dsl
				.select(
						DSL.sum(WORK_REPORTS_ROWS.DURATION).as("duration"),
						WORK_TYPES.DESCRIPTION,
						WORK_REPORTS.OPERATOR_ID,
						WORK_REPORTS.CUSTOMER_ID,
						WORK_REPORTS.COMPANY_ID
				)
				.from(WORK_REPORTS_ROWS)
				.join(WORK_TYPES).on(WORK_REPORTS_ROWS.WORK_TYPE_ID.equal(WORK_TYPES.WORK_TYPE_ID))
				.join(WORK_REPORTS).on(WORK_REPORTS_ROWS.WORK_REPORT_ID.equal(WORK_REPORTS.WORK_REPORT_ID))
				.where(
					WORK_REPORTS.OPERATOR_ID.equal(operatorId)
				)
				.and(
						searchCndt
				)
				.groupBy(
						WORK_TYPES.DESCRIPTION,
						WORK_REPORTS.OPERATOR_ID,
						WORK_REPORTS.CUSTOMER_ID,
						WORK_REPORTS.COMPANY_ID
				)
				.orderBy(
						WORK_REPORTS.OPERATOR_ID,
						WORK_REPORTS.CUSTOMER_ID,
						WORK_TYPES.DESCRIPTION
				)
				.fetchInto(WorkReportSummary.class);
	}
	
	private Condition ensureCondition(Integer companyId, LocalDate from, LocalDate to, Integer docStatusId) {
								
		Condition searchCndt = WORK_REPORTS.COMPANY_ID.equal(companyId)
				.and(WORK_REPORTS.FROM_DATE.between(from, to))
				.and(WORK_REPORTS.REVISION_STATUS.notEqual(EnumUtils.toSerializedName(WorkReport.RevisionStatus.DELETED)));
		
		if (docStatusId != null) {
			searchCndt = searchCndt.and(WORK_REPORTS.DOC_STATUS_ID.equal(docStatusId));
		}

		return searchCndt;
	}
}
