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
public class OpportunityFields implements java.io.Serializable {

	private static final long serialVersionUID = -2073334739;

	private java.lang.String  domainId;
	private java.lang.String  tabId;
	private java.lang.String  fieldId;
	private java.lang.Boolean visible;
	private java.lang.Boolean required;
	private java.lang.Integer order;
	private java.lang.String  label;
	private java.lang.Boolean showOnGrid;

	public OpportunityFields() {}

	public OpportunityFields(
		java.lang.String  domainId,
		java.lang.String  tabId,
		java.lang.String  fieldId,
		java.lang.Boolean visible,
		java.lang.Boolean required,
		java.lang.Integer order,
		java.lang.String  label,
		java.lang.Boolean showOnGrid
	) {
		this.domainId = domainId;
		this.tabId = tabId;
		this.fieldId = fieldId;
		this.visible = visible;
		this.required = required;
		this.order = order;
		this.label = label;
		this.showOnGrid = showOnGrid;
	}

	public java.lang.String getDomainId() {
		return this.domainId;
	}

	public void setDomainId(java.lang.String domainId) {
		this.domainId = domainId;
	}

	public java.lang.String getTabId() {
		return this.tabId;
	}

	public void setTabId(java.lang.String tabId) {
		this.tabId = tabId;
	}

	public java.lang.String getFieldId() {
		return this.fieldId;
	}

	public void setFieldId(java.lang.String fieldId) {
		this.fieldId = fieldId;
	}

	public java.lang.Boolean getVisible() {
		return this.visible;
	}

	public void setVisible(java.lang.Boolean visible) {
		this.visible = visible;
	}

	public java.lang.Boolean getRequired() {
		return this.required;
	}

	public void setRequired(java.lang.Boolean required) {
		this.required = required;
	}

	public java.lang.Integer getOrder() {
		return this.order;
	}

	public void setOrder(java.lang.Integer order) {
		this.order = order;
	}

	public java.lang.String getLabel() {
		return this.label;
	}

	public void setLabel(java.lang.String label) {
		this.label = label;
	}

	public java.lang.Boolean getShowOnGrid() {
		return this.showOnGrid;
	}

	public void setShowOnGrid(java.lang.Boolean showOnGrid) {
		this.showOnGrid = showOnGrid;
	}
}
