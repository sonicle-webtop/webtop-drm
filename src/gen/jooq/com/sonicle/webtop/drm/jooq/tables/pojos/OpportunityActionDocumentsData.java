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
public class OpportunityActionDocumentsData implements java.io.Serializable {

	private static final long serialVersionUID = -869312444;

	private java.lang.String opportunityActionDocumentId;
	private byte[]           bytes;

	public OpportunityActionDocumentsData() {}

	public OpportunityActionDocumentsData(
		java.lang.String opportunityActionDocumentId,
		byte[]           bytes
	) {
		this.opportunityActionDocumentId = opportunityActionDocumentId;
		this.bytes = bytes;
	}

	public java.lang.String getOpportunityActionDocumentId() {
		return this.opportunityActionDocumentId;
	}

	public void setOpportunityActionDocumentId(java.lang.String opportunityActionDocumentId) {
		this.opportunityActionDocumentId = opportunityActionDocumentId;
	}

	public byte[] getBytes() {
		return this.bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
}
