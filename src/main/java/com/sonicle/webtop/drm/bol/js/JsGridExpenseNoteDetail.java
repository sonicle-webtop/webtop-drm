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
package com.sonicle.webtop.drm.bol.js;

import com.sonicle.webtop.drm.model.ExpenseNoteDetail;
import java.math.BigDecimal;
import org.joda.time.LocalDate;

/**
 *
 * @author lssndrvs
 */
public class JsGridExpenseNoteDetail {
	
	public Integer id;
	public Integer expenseNoteId;
	public String operatorId;
	public Integer companyId;
	public Integer typeId;
	public BigDecimal total;
	public LocalDate date;
	public Boolean paymentCompany;
	public Boolean invoice;
	public String invoiceNumber;
	public String withOthers;
	public String customerId;
	public BigDecimal km;
	public String currency;
	public BigDecimal change;
	public String description;
	public BigDecimal totalDoc;
	public String currencyDoc;
	
	public JsGridExpenseNoteDetail(ExpenseNoteDetail eND) {
		this.id = eND.getId();
		this.expenseNoteId = eND.getExpenseNoteId();
		this.operatorId = eND.getOperatorId();
		this.companyId = eND.getCompanyId();
		this.typeId = eND.getTypeId();
		this.total = eND.getTotal();
		this.date = eND.getDate();
		this.paymentCompany = eND.getPaymentCompany();
		this.invoice = eND.getInvoice();
		this.invoiceNumber = eND.getInvoiceNumber();
		this.withOthers = eND.getWithOthers();
		this.customerId = eND.getCustomerId();
		this.km = eND.getKm();
		this.currency = eND.getCurrency();
		this.change = eND.getChange();
		this.description = eND.getDescription();
		this.totalDoc = eND.getTotalDoc();
		this.currencyDoc = eND.getCurrency();
	}
	
	public static ExpenseNoteDetail createExpenseNoteDetail(JsGridExpenseNoteDetail js) {

		ExpenseNoteDetail eND = new ExpenseNoteDetail();
		eND.setId(js.id);
		eND.setExpenseNoteId(js.expenseNoteId);
		eND.setOperatorId(js.operatorId);
		eND.setCompanyId(js.companyId);
		eND.setTypeId(js.typeId);
		eND.setTotal(js.total);
		eND.setDate(js.date);
		eND.setPaymentCompany(js.paymentCompany);
		eND.setInvoice(js.invoice);
		eND.setInvoiceNumber(js.invoiceNumber);
		eND.setWithOthers(js.withOthers);
		eND.setCustomerId(js.customerId);
		eND.setKm(js.km);
		eND.setCurrency(js.currency);
		eND.setChange(js.change);
		eND.setDescription(js.description);
		eND.setTotalDoc(js.totalDoc);
		eND.setCurrencyDoc(js.currencyDoc);

		return eND;
	}
}
