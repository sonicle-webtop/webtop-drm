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
import com.sonicle.webtop.drm.bol.OJobAttachment;
import com.sonicle.webtop.drm.bol.OJobAttachmentData;
import static com.sonicle.webtop.drm.jooq.Tables.JOB_ATTACHMENTS;
import static com.sonicle.webtop.drm.jooq.Tables.JOB_ATTACHMENTS_DATA;
import com.sonicle.webtop.drm.jooq.tables.records.JobAttachmentsRecord;
import java.sql.Connection;
import java.util.List;
import org.joda.time.DateTime;
import org.jooq.DSLContext;

/**
 *
 * @author dnllr
 */
public class JobAttachmentDAO extends BaseDAO {

	private final static JobAttachmentDAO INSTANCE = new JobAttachmentDAO();

	public static JobAttachmentDAO getInstance() {
		return INSTANCE;
	}

	public List<OJobAttachment> selectByJob(Connection con, String jobId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(JOB_ATTACHMENTS)
				.where(
						JOB_ATTACHMENTS.JOB_ID.equal(jobId)
				)
				.fetchInto(OJobAttachment.class);
	}
	
	public OJobAttachment select(Connection con, String jobAttachmentId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(JOB_ATTACHMENTS)
				.where(
						JOB_ATTACHMENTS.JOB_ATTACHMENT_ID.equal(jobAttachmentId)
				)
				.fetchOneInto(OJobAttachment.class);
	}
	
	public OJobAttachmentData selectBytes(Connection con, String attachmentId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
			.select(
				JOB_ATTACHMENTS_DATA.BYTES
			)
			.from(JOB_ATTACHMENTS_DATA)
			.where(JOB_ATTACHMENTS_DATA.JOB_ATTACHMENT_ID.equal(attachmentId))
			.fetchOneInto(OJobAttachmentData.class);
	}

	public int insert(Connection con, OJobAttachment item, DateTime revisionTimestamp) throws DAOException {
		DSLContext dsl = getDSL(con);
		
		item.setRevisionTimestamp(revisionTimestamp);
		item.setRevisionSequence((short)0);
		
		JobAttachmentsRecord record = dsl.newRecord(JOB_ATTACHMENTS, item);

		return dsl
				.insertInto(JOB_ATTACHMENTS)
				.set(record)
				.execute();
	}
	
	public int insertBytes(Connection con, String attachmentId, byte[] bytes) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
			.insertInto(JOB_ATTACHMENTS_DATA)
			.set(JOB_ATTACHMENTS_DATA.JOB_ATTACHMENT_ID, attachmentId)
			.set(JOB_ATTACHMENTS_DATA.BYTES, bytes)
			.execute();
	}
	
	public OJobAttachmentData selectBytesById(Connection con, String attachmentId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
			.select(JOB_ATTACHMENTS_DATA.BYTES)
			.from(JOB_ATTACHMENTS_DATA)
			.where(JOB_ATTACHMENTS_DATA.JOB_ATTACHMENT_ID.equal(attachmentId))
			.fetchOneInto(OJobAttachmentData.class);
	}

	public int update(Connection con, OJobAttachment item, DateTime revisionTimestamp) throws DAOException {
		DSLContext dsl = getDSL(con);
		item.setRevisionTimestamp(revisionTimestamp);
		return dsl
			.update(JOB_ATTACHMENTS)
			.set(JOB_ATTACHMENTS.FILENAME, item.getFilename())
			.set(JOB_ATTACHMENTS.SIZE, item.getSize())
			.set(JOB_ATTACHMENTS.MEDIA_TYPE, item.getMediaType())
			.where(
				JOB_ATTACHMENTS.JOB_ATTACHMENT_ID.equal(item.getJobAttachmentId())
			)
			.execute();
	}
	
	public int deleteById(Connection con, String id) {
		DSLContext dsl = getDSL(con);

		return dsl
				.delete(JOB_ATTACHMENTS)
				.where(
						JOB_ATTACHMENTS.JOB_ATTACHMENT_ID.equal(id)
				)
				.execute();
	}
	
	public int deleteBytesById(Connection con, String attachmentId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
			.delete(JOB_ATTACHMENTS_DATA)
			.where(JOB_ATTACHMENTS_DATA.JOB_ATTACHMENT_ID.equal(attachmentId))
			.execute();
	}

}