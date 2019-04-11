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
import com.sonicle.webtop.drm.bol.OExpenseNoteDetail;
import static com.sonicle.webtop.drm.jooq.Sequences.SEQ_EXPENSE_NOTE_DETAILS;
import static com.sonicle.webtop.drm.jooq.Tables.EXPENSE_NOTE_DETAIL;
import com.sonicle.webtop.drm.jooq.tables.records.ExpenseNoteDetailRecord;
import java.sql.Connection;
import java.util.List;
import org.jooq.DSLContext;

/**
 *
 * @author lssndrvs
 */
public class ExpenseNoteDetailDAO extends BaseDAO {

	private final static ExpenseNoteDetailDAO INSTANCE = new ExpenseNoteDetailDAO();

	public static ExpenseNoteDetailDAO getInstance() {
		return INSTANCE;
	}

	public Long getSequence(Connection con) throws DAOException {
		DSLContext dsl = getDSL(con);
		Long nextID = dsl.nextval(SEQ_EXPENSE_NOTE_DETAILS);
		return nextID;
	}

	public OExpenseNoteDetail select(Connection con, int id) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(EXPENSE_NOTE_DETAIL)
				.where(
						EXPENSE_NOTE_DETAIL.ID.equal(id)
				)
				.fetchOneInto(OExpenseNoteDetail.class);
	}
	
	public List<OExpenseNoteDetail> selectByExpenseNote(Connection con, int id) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(EXPENSE_NOTE_DETAIL)
				.where(
						EXPENSE_NOTE_DETAIL.EXPENSE_NOTE_ID.equal(id)
				)
				.fetchInto(OExpenseNoteDetail.class);
	}

	public int insert(Connection con, OExpenseNoteDetail item) throws DAOException {
		DSLContext dsl = getDSL(con);

		ExpenseNoteDetailRecord record = dsl.newRecord(EXPENSE_NOTE_DETAIL, item);

		return dsl
				.insertInto(EXPENSE_NOTE_DETAIL)
				.set(record)
				.execute();
	}

	public int update(Connection con, OExpenseNoteDetail item) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.update(EXPENSE_NOTE_DETAIL)
				.set(EXPENSE_NOTE_DETAIL.OPERATOR_ID, item.getOperatorId())
				.set(EXPENSE_NOTE_DETAIL.COMPANY_ID, item.getCompanyId())
				.set(EXPENSE_NOTE_DETAIL.TYPE_ID, item.getTypeId())
				.set(EXPENSE_NOTE_DETAIL.TOTAL, item.getTotal())
				.set(EXPENSE_NOTE_DETAIL.DATE, item.getDate())
				.set(EXPENSE_NOTE_DETAIL.PAYMENT_COMPANY, item.getPaymentCompany())
				.set(EXPENSE_NOTE_DETAIL.INVOICE, item.getInvoice())
				.set(EXPENSE_NOTE_DETAIL.INVOICE_NUMBER, item.getInvoiceNumber())
				.set(EXPENSE_NOTE_DETAIL.WITH_OTHERS, item.getWithOthers())
				.set(EXPENSE_NOTE_DETAIL.CUSTOMER_ID, item.getCustomerId())
				.set(EXPENSE_NOTE_DETAIL.KM, item.getKm())
				.set(EXPENSE_NOTE_DETAIL.CURRENCY, item.getCurrency())
				.set(EXPENSE_NOTE_DETAIL.CHANGE, item.getChange())
				.set(EXPENSE_NOTE_DETAIL.DESCRIPTION, item.getDescription())
				.set(EXPENSE_NOTE_DETAIL.TOTAL_DOC, item.getTotalDoc())
				.set(EXPENSE_NOTE_DETAIL.CURRENCY_DOC, item.getCurrencyDoc())
				.where(
						EXPENSE_NOTE_DETAIL.ID.equal(item.getId())
				)
				.execute();
	}

	public int deleteById(Connection con, int id) {
		DSLContext dsl = getDSL(con);

		return dsl
				.delete(EXPENSE_NOTE_DETAIL)
				.where(
						EXPENSE_NOTE_DETAIL.ID.equal(id)
				)
				.execute();
	}

	public int deleteByExpenseNote(Connection con, int id) {
		DSLContext dsl = getDSL(con);

		return dsl
				.delete(EXPENSE_NOTE_DETAIL)
				.where(
						EXPENSE_NOTE_DETAIL.EXPENSE_NOTE_ID.equal(id)
				)
				.execute();
	}
}
