/**
 * This class is generated by jOOQ
 */
package com.sonicle.webtop.drm.jooq.tables.records;

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
public class ExpenseNoteDetailDocumentsDataRecord extends org.jooq.impl.UpdatableRecordImpl<com.sonicle.webtop.drm.jooq.tables.records.ExpenseNoteDetailDocumentsDataRecord> implements org.jooq.Record2<java.lang.String, byte[]> {

	private static final long serialVersionUID = -2109748447;

	/**
	 * Setter for <code>drm.expense_note_detail_documents_data.expense_note_detail_document_id</code>.
	 */
	public void setExpenseNoteDetailDocumentId(java.lang.String value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>drm.expense_note_detail_documents_data.expense_note_detail_document_id</code>.
	 */
	public java.lang.String getExpenseNoteDetailDocumentId() {
		return (java.lang.String) getValue(0);
	}

	/**
	 * Setter for <code>drm.expense_note_detail_documents_data.bytes</code>.
	 */
	public void setBytes(byte[] value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>drm.expense_note_detail_documents_data.bytes</code>.
	 */
	public byte[] getBytes() {
		return (byte[]) getValue(1);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Record1<java.lang.String> key() {
		return (org.jooq.Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record2 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row2<java.lang.String, byte[]> fieldsRow() {
		return (org.jooq.Row2) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row2<java.lang.String, byte[]> valuesRow() {
		return (org.jooq.Row2) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field1() {
		return com.sonicle.webtop.drm.jooq.tables.ExpenseNoteDetailDocumentsData.EXPENSE_NOTE_DETAIL_DOCUMENTS_DATA.EXPENSE_NOTE_DETAIL_DOCUMENT_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<byte[]> field2() {
		return com.sonicle.webtop.drm.jooq.tables.ExpenseNoteDetailDocumentsData.EXPENSE_NOTE_DETAIL_DOCUMENTS_DATA.BYTES;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value1() {
		return getExpenseNoteDetailDocumentId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte[] value2() {
		return getBytes();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ExpenseNoteDetailDocumentsDataRecord value1(java.lang.String value) {
		setExpenseNoteDetailDocumentId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ExpenseNoteDetailDocumentsDataRecord value2(byte[] value) {
		setBytes(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ExpenseNoteDetailDocumentsDataRecord values(java.lang.String value1, byte[] value2) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached ExpenseNoteDetailDocumentsDataRecord
	 */
	public ExpenseNoteDetailDocumentsDataRecord() {
		super(com.sonicle.webtop.drm.jooq.tables.ExpenseNoteDetailDocumentsData.EXPENSE_NOTE_DETAIL_DOCUMENTS_DATA);
	}

	/**
	 * Create a detached, initialised ExpenseNoteDetailDocumentsDataRecord
	 */
	public ExpenseNoteDetailDocumentsDataRecord(java.lang.String expenseNoteDetailDocumentId, byte[] bytes) {
		super(com.sonicle.webtop.drm.jooq.tables.ExpenseNoteDetailDocumentsData.EXPENSE_NOTE_DETAIL_DOCUMENTS_DATA);

		setValue(0, expenseNoteDetailDocumentId);
		setValue(1, bytes);
	}
}