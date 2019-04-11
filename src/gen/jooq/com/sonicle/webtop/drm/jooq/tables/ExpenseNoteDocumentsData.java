/**
 * This class is generated by jOOQ
 */
package com.sonicle.webtop.drm.jooq.tables;

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
public class ExpenseNoteDocumentsData extends org.jooq.impl.TableImpl<com.sonicle.webtop.drm.jooq.tables.records.ExpenseNoteDocumentsDataRecord> {

	private static final long serialVersionUID = 1859423041;

	/**
	 * The reference instance of <code>drm.expense_note_documents_data</code>
	 */
	public static final com.sonicle.webtop.drm.jooq.tables.ExpenseNoteDocumentsData EXPENSE_NOTE_DOCUMENTS_DATA = new com.sonicle.webtop.drm.jooq.tables.ExpenseNoteDocumentsData();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<com.sonicle.webtop.drm.jooq.tables.records.ExpenseNoteDocumentsDataRecord> getRecordType() {
		return com.sonicle.webtop.drm.jooq.tables.records.ExpenseNoteDocumentsDataRecord.class;
	}

	/**
	 * The column <code>drm.expense_note_documents_data.expense_note_document_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.ExpenseNoteDocumentsDataRecord, java.lang.String> EXPENSE_NOTE_DOCUMENT_ID = createField("expense_note_document_id", org.jooq.impl.SQLDataType.VARCHAR.length(36).nullable(false), this, "");

	/**
	 * The column <code>drm.expense_note_documents_data.bytes</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.ExpenseNoteDocumentsDataRecord, byte[]> BYTES = createField("bytes", org.jooq.impl.SQLDataType.BLOB.nullable(false), this, "");

	/**
	 * Create a <code>drm.expense_note_documents_data</code> table reference
	 */
	public ExpenseNoteDocumentsData() {
		this("expense_note_documents_data", null);
	}

	/**
	 * Create an aliased <code>drm.expense_note_documents_data</code> table reference
	 */
	public ExpenseNoteDocumentsData(java.lang.String alias) {
		this(alias, com.sonicle.webtop.drm.jooq.tables.ExpenseNoteDocumentsData.EXPENSE_NOTE_DOCUMENTS_DATA);
	}

	private ExpenseNoteDocumentsData(java.lang.String alias, org.jooq.Table<com.sonicle.webtop.drm.jooq.tables.records.ExpenseNoteDocumentsDataRecord> aliased) {
		this(alias, aliased, null);
	}

	private ExpenseNoteDocumentsData(java.lang.String alias, org.jooq.Table<com.sonicle.webtop.drm.jooq.tables.records.ExpenseNoteDocumentsDataRecord> aliased, org.jooq.Field<?>[] parameters) {
		super(alias, com.sonicle.webtop.drm.jooq.Drm.DRM, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.ExpenseNoteDocumentsDataRecord> getPrimaryKey() {
		return com.sonicle.webtop.drm.jooq.Keys.EXPENSE_NOTE_DOCUMENTS_DATA_PKEY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.ExpenseNoteDocumentsDataRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.ExpenseNoteDocumentsDataRecord>>asList(com.sonicle.webtop.drm.jooq.Keys.EXPENSE_NOTE_DOCUMENTS_DATA_PKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.ForeignKey<com.sonicle.webtop.drm.jooq.tables.records.ExpenseNoteDocumentsDataRecord, ?>> getReferences() {
		return java.util.Arrays.<org.jooq.ForeignKey<com.sonicle.webtop.drm.jooq.tables.records.ExpenseNoteDocumentsDataRecord, ?>>asList(com.sonicle.webtop.drm.jooq.Keys.EXPENSE_NOTE_DOCUMENTS_DATA__EXPENSE_NOTE_DOCUMENTS_DATA_EXPENSE_NOTE_DOCUMENT_ID_FKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public com.sonicle.webtop.drm.jooq.tables.ExpenseNoteDocumentsData as(java.lang.String alias) {
		return new com.sonicle.webtop.drm.jooq.tables.ExpenseNoteDocumentsData(alias, this);
	}

	/**
	 * Rename this table
	 */
	public com.sonicle.webtop.drm.jooq.tables.ExpenseNoteDocumentsData rename(java.lang.String name) {
		return new com.sonicle.webtop.drm.jooq.tables.ExpenseNoteDocumentsData(name, null);
	}
}
