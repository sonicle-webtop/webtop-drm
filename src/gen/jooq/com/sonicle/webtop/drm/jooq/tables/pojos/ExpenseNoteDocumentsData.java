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
public class ExpenseNoteDocumentsData implements java.io.Serializable {

	private static final long serialVersionUID = 448752773;

	private java.lang.String expenseNoteDocumentId;
	private byte[]           bytes;

	public ExpenseNoteDocumentsData() {}

	public ExpenseNoteDocumentsData(
		java.lang.String expenseNoteDocumentId,
		byte[]           bytes
	) {
		this.expenseNoteDocumentId = expenseNoteDocumentId;
		this.bytes = bytes;
	}

	public java.lang.String getExpenseNoteDocumentId() {
		return this.expenseNoteDocumentId;
	}

	public void setExpenseNoteDocumentId(java.lang.String expenseNoteDocumentId) {
		this.expenseNoteDocumentId = expenseNoteDocumentId;
	}

	public byte[] getBytes() {
		return this.bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
}
