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
public class TicketAttachments extends org.jooq.impl.TableImpl<com.sonicle.webtop.drm.jooq.tables.records.TicketAttachmentsRecord> {

	private static final long serialVersionUID = 1584020030;

	/**
	 * The reference instance of <code>drm.ticket_attachments</code>
	 */
	public static final com.sonicle.webtop.drm.jooq.tables.TicketAttachments TICKET_ATTACHMENTS = new com.sonicle.webtop.drm.jooq.tables.TicketAttachments();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<com.sonicle.webtop.drm.jooq.tables.records.TicketAttachmentsRecord> getRecordType() {
		return com.sonicle.webtop.drm.jooq.tables.records.TicketAttachmentsRecord.class;
	}

	/**
	 * The column <code>drm.ticket_attachments.ticket_attachment_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.TicketAttachmentsRecord, java.lang.String> TICKET_ATTACHMENT_ID = createField("ticket_attachment_id", org.jooq.impl.SQLDataType.VARCHAR.length(36).nullable(false), this, "");

	/**
	 * The column <code>drm.ticket_attachments.ticket_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.TicketAttachmentsRecord, java.lang.String> TICKET_ID = createField("ticket_id", org.jooq.impl.SQLDataType.VARCHAR.length(36).nullable(false), this, "");

	/**
	 * The column <code>drm.ticket_attachments.revision_timestamp</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.TicketAttachmentsRecord, org.joda.time.DateTime> REVISION_TIMESTAMP = createField("revision_timestamp", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "", new com.sonicle.webtop.core.jooq.DateTimeConverter());

	/**
	 * The column <code>drm.ticket_attachments.revision_sequence</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.TicketAttachmentsRecord, java.lang.Short> REVISION_SEQUENCE = createField("revision_sequence", org.jooq.impl.SQLDataType.SMALLINT.nullable(false), this, "");

	/**
	 * The column <code>drm.ticket_attachments.filename</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.TicketAttachmentsRecord, java.lang.String> FILENAME = createField("filename", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false), this, "");

	/**
	 * The column <code>drm.ticket_attachments.size</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.TicketAttachmentsRecord, java.lang.Long> SIZE = createField("size", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

	/**
	 * The column <code>drm.ticket_attachments.media_type</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.drm.jooq.tables.records.TicketAttachmentsRecord, java.lang.String> MEDIA_TYPE = createField("media_type", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false), this, "");

	/**
	 * Create a <code>drm.ticket_attachments</code> table reference
	 */
	public TicketAttachments() {
		this("ticket_attachments", null);
	}

	/**
	 * Create an aliased <code>drm.ticket_attachments</code> table reference
	 */
	public TicketAttachments(java.lang.String alias) {
		this(alias, com.sonicle.webtop.drm.jooq.tables.TicketAttachments.TICKET_ATTACHMENTS);
	}

	private TicketAttachments(java.lang.String alias, org.jooq.Table<com.sonicle.webtop.drm.jooq.tables.records.TicketAttachmentsRecord> aliased) {
		this(alias, aliased, null);
	}

	private TicketAttachments(java.lang.String alias, org.jooq.Table<com.sonicle.webtop.drm.jooq.tables.records.TicketAttachmentsRecord> aliased, org.jooq.Field<?>[] parameters) {
		super(alias, com.sonicle.webtop.drm.jooq.Drm.DRM, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.TicketAttachmentsRecord> getPrimaryKey() {
		return com.sonicle.webtop.drm.jooq.Keys.TICKET_ATTACHMENTS_PKEY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.TicketAttachmentsRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.TicketAttachmentsRecord>>asList(com.sonicle.webtop.drm.jooq.Keys.TICKET_ATTACHMENTS_PKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public com.sonicle.webtop.drm.jooq.tables.TicketAttachments as(java.lang.String alias) {
		return new com.sonicle.webtop.drm.jooq.tables.TicketAttachments(alias, this);
	}

	/**
	 * Rename this table
	 */
	public com.sonicle.webtop.drm.jooq.tables.TicketAttachments rename(java.lang.String name) {
		return new com.sonicle.webtop.drm.jooq.tables.TicketAttachments(name, null);
	}
}
