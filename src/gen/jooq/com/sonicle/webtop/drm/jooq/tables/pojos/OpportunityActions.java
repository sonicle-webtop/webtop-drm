/**
 * This class is generated by jOOQ
 */
package com.sonicle.webtop.drm.jooq.tables.pojos;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.5.3"
	},
	comments = "This class is generated by jOOQ"
)
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class OpportunityActions implements java.io.Serializable {

	private static final long serialVersionUID = 452979135;

	private java.lang.Integer      id;
	private java.lang.Integer      opportunityId;
	private java.lang.String       operatorId;
	private java.lang.Integer      statusId;
	private java.lang.String       description;
	private java.lang.String       place;
	private java.lang.String       subsequentActions;
	private java.lang.Integer      activityId;
	private java.lang.Integer      eventId;
	private org.joda.time.DateTime startDate;
	private org.joda.time.DateTime endDate;

	public OpportunityActions() {}

	public OpportunityActions(
		java.lang.Integer      id,
		java.lang.Integer      opportunityId,
		java.lang.String       operatorId,
		java.lang.Integer      statusId,
		java.lang.String       description,
		java.lang.String       place,
		java.lang.String       subsequentActions,
		java.lang.Integer      activityId,
		java.lang.Integer      eventId,
		org.joda.time.DateTime startDate,
		org.joda.time.DateTime endDate
	) {
		this.id = id;
		this.opportunityId = opportunityId;
		this.operatorId = operatorId;
		this.statusId = statusId;
		this.description = description;
		this.place = place;
		this.subsequentActions = subsequentActions;
		this.activityId = activityId;
		this.eventId = eventId;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public java.lang.Integer getId() {
		return this.id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.Integer getOpportunityId() {
		return this.opportunityId;
	}

	public void setOpportunityId(java.lang.Integer opportunityId) {
		this.opportunityId = opportunityId;
	}

	public java.lang.String getOperatorId() {
		return this.operatorId;
	}

	public void setOperatorId(java.lang.String operatorId) {
		this.operatorId = operatorId;
	}

	public java.lang.Integer getStatusId() {
		return this.statusId;
	}

	public void setStatusId(java.lang.Integer statusId) {
		this.statusId = statusId;
	}

	public java.lang.String getDescription() {
		return this.description;
	}

	public void setDescription(java.lang.String description) {
		this.description = description;
	}

	public java.lang.String getPlace() {
		return this.place;
	}

	public void setPlace(java.lang.String place) {
		this.place = place;
	}

	public java.lang.String getSubsequentActions() {
		return this.subsequentActions;
	}

	public void setSubsequentActions(java.lang.String subsequentActions) {
		this.subsequentActions = subsequentActions;
	}

	public java.lang.Integer getActivityId() {
		return this.activityId;
	}

	public void setActivityId(java.lang.Integer activityId) {
		this.activityId = activityId;
	}

	public java.lang.Integer getEventId() {
		return this.eventId;
	}

	public void setEventId(java.lang.Integer eventId) {
		this.eventId = eventId;
	}

	public org.joda.time.DateTime getStartDate() {
		return this.startDate;
	}

	public void setStartDate(org.joda.time.DateTime startDate) {
		this.startDate = startDate;
	}

	public org.joda.time.DateTime getEndDate() {
		return this.endDate;
	}

	public void setEndDate(org.joda.time.DateTime endDate) {
		this.endDate = endDate;
	}
}
