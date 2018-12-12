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

import com.sonicle.webtop.drm.model.Opportunity;
import com.sonicle.webtop.drm.model.OpportunityDocument;
import com.sonicle.webtop.drm.model.OpportunityInterlocutor;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.LocalDate;

/**
 *
 * @author lssndrvs
 */
public class JsOpportunity {

	public Integer id;
	public String domainId;
	public Integer companyId;
	public String operatorId;
	public LocalDate date;
	public String fromHour;
	public String toHour;
	public String executedWith;
	public String customerId;
	public String customerStatId;
	public String sector;
	public String description;
	public String place;
	public String objective;
	public Integer causalId;
	public Integer activityId;
	public String objective2;
	public String result;
	public String discoveries;
	public String customerPotential;
	public String notes;
	public Integer statusId;
	public String signedBy;
	public Boolean signature;
	public Boolean success;
	public Integer eventId;

	public List<JsGridOpportunityInterlocutor> interlocutors = new ArrayList();
	public List<Document> documents = new ArrayList();

	public JsOpportunity(Opportunity o) {
		this.id = o.getId();
		this.domainId = o.getDomainId();
		this.companyId = o.getCompanyId();
		this.operatorId = o.getOperatorId();
		this.date = o.getDate();
		this.fromHour = o.getFromHour();
		this.toHour = o.getToHour();
		this.executedWith = o.getExecutedWith();
		this.customerId = o.getCustomerId();
		this.customerStatId = o.getCustomerStatId();
		this.sector = o.getSector();
		this.description = o.getDescription();
		this.place = o.getPlace();
		this.objective = o.getObjective();
		this.causalId = o.getCausalId();
		this.activityId = o.getActivityId();
		this.objective2 = o.getObjective2();
		this.result = o.getResult();
		this.discoveries = o.getDiscoveries();
		this.customerPotential = o.getCustomerPotential();
		this.notes = o.getNotes();
		this.statusId = o.getStatusId();
		this.signedBy = o.getSignedBy();
		this.signature = o.getSignature();
		this.success = o.getSuccess();
		this.eventId = o.getEventId();

		for (OpportunityInterlocutor oInt : o.getInterlocutors()) {
			this.interlocutors.add(new JsGridOpportunityInterlocutor(oInt));
		}
		
		documents = new ArrayList<>(o.getDocuments().size());
		for (OpportunityDocument doc : o.getDocuments()) {
			Document jsdoc = new Document();
			jsdoc.id = doc.getId();
			jsdoc.name = doc.getFileName();
			jsdoc.size = doc.getSize();
			documents.add(jsdoc);
		}
	}

	public static Opportunity createOpportunity(JsOpportunity js) {

		Opportunity o = new Opportunity();
		o.setId(js.id);
		o.setDomainId(js.domainId);
		o.setCompanyId(js.companyId);
		o.setOperatorId(js.operatorId);
		o.setDate(js.date);
		o.setFromHour(js.fromHour);
		o.setToHour(js.toHour);
		o.setExecutedWith(js.executedWith);
		o.setCustomerId(js.customerId);
		o.setCustomerStatId(js.customerStatId);
		o.setSector(js.sector);
		o.setDescription(js.description);
		o.setPlace(js.place);
		o.setObjective(js.objective);
		o.setCausalId(js.causalId);
		o.setActivityId(js.activityId);
		o.setObjective2(js.objective2);
		o.setResult(js.result);
		o.setDiscoveries(js.discoveries);
		o.setCustomerPotential(js.customerPotential);
		o.setNotes(js.notes);
		o.setStatusId(js.statusId);
		o.setSignedBy(js.signedBy);
		o.setSignature(js.signature);
		o.setSuccess(js.success);
		o.setEventId(js.eventId);

		for (JsGridOpportunityInterlocutor jsGridOppInt : js.interlocutors) {

			o.getInterlocutors().add(JsGridOpportunityInterlocutor.createOpportunityInterlocutor(jsGridOppInt));

		}
		
		// Attachment needs to be treated outside this class in order to have complete access to their streams

		return o;
	}
	
	public static class Document {
		public String id;
		public String name;
		public Long size;
		public String _uplId;
	}
}
