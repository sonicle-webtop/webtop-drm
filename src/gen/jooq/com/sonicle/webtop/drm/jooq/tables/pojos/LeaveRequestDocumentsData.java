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
public class LeaveRequestDocumentsData implements java.io.Serializable {

	private static final long serialVersionUID = -939513155;

	private java.lang.String leaveRequestDocumentId;
	private byte[]           bytes;

	public LeaveRequestDocumentsData() {}

	public LeaveRequestDocumentsData(
		java.lang.String leaveRequestDocumentId,
		byte[]           bytes
	) {
		this.leaveRequestDocumentId = leaveRequestDocumentId;
		this.bytes = bytes;
	}

	public java.lang.String getLeaveRequestDocumentId() {
		return this.leaveRequestDocumentId;
	}

	public void setLeaveRequestDocumentId(java.lang.String leaveRequestDocumentId) {
		this.leaveRequestDocumentId = leaveRequestDocumentId;
	}

	public byte[] getBytes() {
		return this.bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
}
