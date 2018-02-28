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
import com.sonicle.webtop.drm.bol.ODocStatus;
import static com.sonicle.webtop.drm.jooq.Sequences.SEQ_DOC_STATUSES;
import static com.sonicle.webtop.drm.jooq.Tables.DOC_STATUSES;
import com.sonicle.webtop.drm.jooq.tables.records.DocStatusesRecord;
import java.sql.Connection;
import java.util.List;
import org.jooq.DSLContext;

/**
 *
 * @author lssndrvs
 */
public class DocStatusDAO extends BaseDAO{
	
	private final static DocStatusDAO INSTANCE = new DocStatusDAO();

	public static DocStatusDAO getInstance() {
		return INSTANCE;
	}
	
	public Long getSequence(Connection con) throws DAOException {
		DSLContext dsl = getDSL(con);
		Long nextID = dsl.nextval(SEQ_DOC_STATUSES);
		return nextID;
	}
	
	public int insertDocStatus(Connection con,ODocStatus item) throws DAOException {
		DSLContext dsl = getDSL(con);
		DocStatusesRecord record = dsl.newRecord(DOC_STATUSES,item);
		
		return dsl
				.insertInto(DOC_STATUSES)
				.set(record)
				.execute();
	}

	public List<ODocStatus> selectStatuses(Connection con) throws DAOException{
			DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(DOC_STATUSES)
				.fetchInto(ODocStatus.class);
	}

	public ODocStatus selectById(Connection con, int docStatusId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
			.select()
			.from(DOC_STATUSES)
			.where(DOC_STATUSES.DOC_STATUS_ID.equal(docStatusId))
			.fetchOneInto(ODocStatus.class);
	}
	
	public int update(Connection con, ODocStatus item) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
			.update(DOC_STATUSES)
			.set(DOC_STATUSES.NAME,item.getName())
			.set(DOC_STATUSES.DESCRIPTION,item.getDescription())
			.set(DOC_STATUSES.TYPE,item.getType())
			//.set(DOC_STATUSES.BUILT_IN,item.getBuiltIn())
			.where(
				DOC_STATUSES.DOC_STATUS_ID.equal(item.getDocStatusId())
			)
			.execute();
	}

	public int deleteById(Connection con, int docStatusId ) {
		DSLContext dsl = getDSL(con);
		return dsl
			.delete(DOC_STATUSES)
			.where(
				DOC_STATUSES.DOC_STATUS_ID.equal(docStatusId)
			)
			.execute();
	}
	
}
