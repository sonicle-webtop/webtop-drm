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

import com.sonicle.webtop.drm.model.OpportunityAction;
import com.sonicle.webtop.drm.model.OpportunityActionDocument;
import com.sonicle.webtop.drm.model.OpportunityActionInterlocutor;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author lssndrvs
 */
public class JsOpportunityAction {

	public Integer id;
	public Integer opportunityId;
	public String operatorId;
	public Integer statusId;
	public String startDate;
	public String endDate;
	public String description;
	public String place;
	public String subsequentActions;
	public Integer activityId;
	public String eventId;	
	
	private List<JsGridOpportunityActionInterlocutor> actionInterlocutors = new ArrayList();
	public List<Document> actionDocuments = new ArrayList();

	public JsOpportunityAction(OpportunityAction oAct) {
		
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
		
		this.id = oAct.getId();
		this.opportunityId = oAct.getOpportunityId();
		this.operatorId = oAct.getOperatorId();
		this.statusId = oAct.getStatusId();
		this.startDate = formatter.print(oAct.getStartDate());
		this.endDate = formatter.print(oAct.getEndDate());
		this.description = oAct.getDescription();
		this.place = oAct.getPlace();
		this.subsequentActions = oAct.getSubsequentActions();
		this.activityId = oAct.getActivityId();
		this.eventId = oAct.getEventId();

		for (OpportunityActionInterlocutor oActInt : oAct.getActionInterlocutors()) {
			this.actionInterlocutors.add(new JsGridOpportunityActionInterlocutor(oActInt));
		}
		
		actionDocuments = new ArrayList<>(oAct.getActionDocuments().size());
		for (OpportunityActionDocument doc : oAct.getActionDocuments()) {
			Document jsdoc = new Document();
			jsdoc.id = doc.getId();
			jsdoc.name = doc.getFileName();
			jsdoc.size = doc.getSize();
			actionDocuments.add(jsdoc);
		}
	}

	public static OpportunityAction createOpportunityAction(JsOpportunityAction js) {
		
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

		OpportunityAction oAct = new OpportunityAction();
		oAct.setId(js.id);
		oAct.setOpportunityId(js.opportunityId);
		oAct.setOperatorId(js.operatorId);
		oAct.setStatusId(js.statusId);
		oAct.setStartDate(formatter.parseDateTime(js.startDate));
		oAct.setEndDate(formatter.parseDateTime(js.endDate));
		oAct.setDescription(js.description);
		oAct.setPlace(js.place);
		oAct.setSubsequentActions(js.subsequentActions);
		oAct.setActivityId(js.activityId);
		oAct.setEventId(js.eventId);
		
		for (JsGridOpportunityActionInterlocutor jsGridOppActInt : js.actionInterlocutors) {

			oAct.getActionInterlocutors().add(JsGridOpportunityActionInterlocutor.createOpportunityActionInterlocutor(jsGridOppActInt));

		}
		
		// Attachment needs to be treated outside this class in order to have complete access to their streams

		return oAct;
	}
	
	public static class Document {
		public String id;
		public String name;
		public Long size;
		public String _uplId;
	}
}
