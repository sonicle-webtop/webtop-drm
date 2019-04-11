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
import com.sonicle.webtop.drm.ExpenseNoteQuery;
import com.sonicle.webtop.drm.bol.OExpenseNote;
import com.sonicle.webtop.drm.jooq.Sequences;
import static com.sonicle.webtop.drm.jooq.Tables.EXPENSE_NOTE;
import com.sonicle.webtop.drm.jooq.tables.records.ExpenseNoteRecord;
import java.sql.Connection;
import java.util.List;
import org.jooq.Condition;
import org.jooq.DSLContext;

/**
 *
 * @author lssndrvs
 */
public class ExpenseNoteDAO extends BaseDAO {

	private final static ExpenseNoteDAO INSTANCE = new ExpenseNoteDAO();

	public static ExpenseNoteDAO getInstance() {
		return INSTANCE;
	}

	public int insert(Connection con, OExpenseNote item) throws DAOException {
		DSLContext dsl = getDSL(con);

		ExpenseNoteRecord record = dsl.newRecord(EXPENSE_NOTE, item);
		return dsl
				.insertInto(EXPENSE_NOTE)
				.set(record)
				.execute();
	}

	public OExpenseNote selectById(Connection con, int id) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(EXPENSE_NOTE)
				.where(EXPENSE_NOTE.ID.equal(id))
				.fetchOneInto(OExpenseNote.class);
	}
	
	public List<OExpenseNote> selectExpenseNotes(Connection con, ExpenseNoteQuery query) throws DAOException {
		DSLContext dsl = getDSL(con);

		Condition searchCndt = ensureCondition(query);

		return dsl
				.select()
				.from(EXPENSE_NOTE)
				.where(
						searchCndt
				)
				.orderBy(
						EXPENSE_NOTE.FROM_DATE,
						EXPENSE_NOTE.STATUS
				)
				.fetchInto(OExpenseNote.class);
	}

	public int update(Connection con, OExpenseNote item) throws DAOException {
		DSLContext dsl = getDSL(con);
		
		return dsl
				.update(EXPENSE_NOTE)
				.set(EXPENSE_NOTE.OPERATOR_ID, item.getOperatorId())
				.set(EXPENSE_NOTE.COMPANY_ID, item.getCompanyId())
				.set(EXPENSE_NOTE.FROM_DATE, item.getFromDate())
				.set(EXPENSE_NOTE.TO_DATE, item.getToDate())
				.set(EXPENSE_NOTE.TOT_CURRENCY, item.getTotCurrency())
				.set(EXPENSE_NOTE.CURRENCY, item.getCurrency())
				.set(EXPENSE_NOTE.DESCRIPTION, item.getDescription())
				.set(EXPENSE_NOTE.STATUS, item.getStatus())
				.where(
						EXPENSE_NOTE.ID.equal(item.getId())
				)
				.execute();
	}

	public int deleteById(Connection con, int id) {
		DSLContext dsl = getDSL(con);
		return dsl
				.delete(EXPENSE_NOTE)
				.where(
						EXPENSE_NOTE.ID.equal(id)
				)
				.execute();
	}

	public Long getExpenseNoteSequence(Connection con) throws DAOException {
		DSLContext dsl = getDSL(con);
		
		Long nextID = dsl.nextval(Sequences.SEQ_EXPENSE_NOTES);
		
		return nextID;
	}
	
	private Condition ensureCondition(ExpenseNoteQuery query) {
								
		Condition searchCndt = EXPENSE_NOTE.OPERATOR_ID.equal(query.operatorId);
		
		if (query.companyId != null) {
			searchCndt = searchCndt.and(EXPENSE_NOTE.COMPANY_ID.equal(query.companyId));
		}
		
		if (query.fromDate != null) {
			searchCndt = searchCndt.and(EXPENSE_NOTE.FROM_DATE.greaterOrEqual(query.fromDate));
		}

		if (query.toDate != null) {
			searchCndt = searchCndt.and(EXPENSE_NOTE.TO_DATE.lessOrEqual(query.toDate));
		}
		
		if (query.statusId != null) {
			searchCndt = searchCndt.and(EXPENSE_NOTE.STATUS.equal(query.statusId));
		}

		return searchCndt;
	}
}
