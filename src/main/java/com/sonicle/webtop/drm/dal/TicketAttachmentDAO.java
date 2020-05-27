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
package com.sonicle.webtop.drm.dal;

import com.sonicle.webtop.core.dal.BaseDAO;
import com.sonicle.webtop.core.dal.DAOException;
import com.sonicle.webtop.drm.bol.OTicketAttachment;
import com.sonicle.webtop.drm.bol.OTicketAttachmentData;
import static com.sonicle.webtop.drm.jooq.Tables.TICKET_ATTACHMENTS;
import static com.sonicle.webtop.drm.jooq.Tables.TICKET_ATTACHMENTS_DATA;
import com.sonicle.webtop.drm.jooq.tables.records.TicketAttachmentsRecord;
import java.sql.Connection;
import java.util.List;
import org.joda.time.DateTime;
import org.jooq.DSLContext;

/**
 *
 * @author dnllr
 */
public class TicketAttachmentDAO extends BaseDAO {

	private final static TicketAttachmentDAO INSTANCE = new TicketAttachmentDAO();

	public static TicketAttachmentDAO getInstance() {
		return INSTANCE;
	}

	public List<OTicketAttachment> selectByTicket(Connection con, String tcktId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(TICKET_ATTACHMENTS)
				.where(
						TICKET_ATTACHMENTS.TICKET_ID.equal(tcktId)
				)
				.fetchInto(OTicketAttachment.class);
	}
	
	public OTicketAttachment select(Connection con, String tcktAttachmentId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(TICKET_ATTACHMENTS)
				.where(
						TICKET_ATTACHMENTS.TICKET_ATTACHMENT_ID.equal(tcktAttachmentId)
				)
				.fetchOneInto(OTicketAttachment.class);
	}
	
	public OTicketAttachmentData selectBytes(Connection con, String attachmentId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
			.select(
				TICKET_ATTACHMENTS_DATA.BYTES
			)
			.from(TICKET_ATTACHMENTS_DATA)
			.where(TICKET_ATTACHMENTS_DATA.TICKET_ATTACHMENT_ID.equal(attachmentId))
			.fetchOneInto(OTicketAttachmentData.class);
	}

	public int insert(Connection con, OTicketAttachment item, DateTime revisionTimestamp) throws DAOException {
		DSLContext dsl = getDSL(con);
		
		item.setRevisionTimestamp(revisionTimestamp);
		item.setRevisionSequence((short)0);
		
		TicketAttachmentsRecord record = dsl.newRecord(TICKET_ATTACHMENTS, item);

		return dsl
				.insertInto(TICKET_ATTACHMENTS)
				.set(record)
				.execute();
	}
	
	public int insertBytes(Connection con, String attachmentId, byte[] bytes) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
			.insertInto(TICKET_ATTACHMENTS_DATA)
			.set(TICKET_ATTACHMENTS_DATA.TICKET_ATTACHMENT_ID, attachmentId)
			.set(TICKET_ATTACHMENTS_DATA.BYTES, bytes)
			.execute();
	}
	
	public OTicketAttachmentData selectBytesById(Connection con, String attachmentId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
			.select(TICKET_ATTACHMENTS_DATA.BYTES)
			.from(TICKET_ATTACHMENTS_DATA)
			.where(TICKET_ATTACHMENTS_DATA.TICKET_ATTACHMENT_ID.equal(attachmentId))
			.fetchOneInto(OTicketAttachmentData.class);
	}

	public int update(Connection con, OTicketAttachment item, DateTime revisionTimestamp) throws DAOException {
		DSLContext dsl = getDSL(con);
		item.setRevisionTimestamp(revisionTimestamp);
		return dsl
			.update(TICKET_ATTACHMENTS)
			.set(TICKET_ATTACHMENTS.FILENAME, item.getFilename())
			.set(TICKET_ATTACHMENTS.SIZE, item.getSize())
			.set(TICKET_ATTACHMENTS.MEDIA_TYPE, item.getMediaType())
			.where(
				TICKET_ATTACHMENTS.TICKET_ATTACHMENT_ID.equal(item.getTicketAttachmentId())
			)
			.execute();
	}
	
	public int deleteById(Connection con, String id) {
		DSLContext dsl = getDSL(con);

		return dsl
				.delete(TICKET_ATTACHMENTS)
				.where(
						TICKET_ATTACHMENTS.TICKET_ATTACHMENT_ID.equal(id)
				)
				.execute();
	}
	
	public int deleteBytesById(Connection con, String attachmentId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
			.delete(TICKET_ATTACHMENTS_DATA)
			.where(TICKET_ATTACHMENTS_DATA.TICKET_ATTACHMENT_ID.equal(attachmentId))
			.execute();
	}

}