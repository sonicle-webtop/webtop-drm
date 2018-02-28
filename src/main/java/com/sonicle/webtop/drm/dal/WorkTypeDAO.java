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
import com.sonicle.webtop.drm.bol.OWorkReportRow;
import com.sonicle.webtop.drm.bol.OWorkType;
import static com.sonicle.webtop.drm.jooq.Sequences.SEQ_WORK_TYPES;
import static com.sonicle.webtop.drm.jooq.Tables.WORK_TYPES;
import com.sonicle.webtop.drm.jooq.tables.records.WorkReportsRowsRecord;
import com.sonicle.webtop.drm.jooq.tables.records.WorkTypesRecord;
import java.sql.Connection;
import java.util.List;
import org.jooq.DSLContext;

/**
 *
 * @author lssndrvs
 */
public class WorkTypeDAO extends BaseDAO {

	private final static WorkTypeDAO INSTANCE = new WorkTypeDAO();

	public static WorkTypeDAO getInstance() {
		return INSTANCE;
	}

	public Long getSequence(Connection con) throws DAOException {
		DSLContext dsl = getDSL(con);
		Long nextID = dsl.nextval(SEQ_WORK_TYPES);
		return nextID;
	}

	public List<OWorkType> selectByDomain(Connection con, String domainId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(WORK_TYPES)
				.where(
						WORK_TYPES.DOMAIN_ID.equal(domainId)
						.and(
								WORK_TYPES.REVISION_STATUS.equal(OWorkType.REV_STATUS_MODIFIED)
						)
				)
				.fetchInto(OWorkType.class);
	}
	
	public OWorkType selectById(Connection con, String domainId, int workTypeId) throws DAOException {
		DSLContext dsl = getDSL(con);
		
		return dsl
			.select()
			.from(WORK_TYPES)
			.where(
				WORK_TYPES.DOMAIN_ID.equal(domainId)
				.and(
						WORK_TYPES.WORK_TYPE_ID.equal(workTypeId)
				)
			)
			.fetchOneInto(OWorkType.class);
	}

	public int insert(Connection con, OWorkType item) throws DAOException {
		DSLContext dsl = getDSL(con);

		item.setRevisionStatus(OWorkType.REV_STATUS_MODIFIED);

		WorkTypesRecord record = dsl.newRecord(WORK_TYPES, item);

		return dsl
				.insertInto(WORK_TYPES)
				.set(record)
				.execute();
	}

	public int update(Connection con, OWorkType item) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.update(WORK_TYPES)
				.set(WORK_TYPES.EXTERNAL_ID, item.getExternalId())
				.set(WORK_TYPES.DESCRIPTION, item.getDescription())
				.where(
						WORK_TYPES.WORK_TYPE_ID.equal(item.getWorkTypeId())
				)
				.execute();
	}

	public int deleteById(Connection con, int id) {
		DSLContext dsl = getDSL(con);

		return dsl
				.delete(WORK_TYPES)
				.where(
						WORK_TYPES.WORK_TYPE_ID.equal(id)
				)
				.execute();
	}

	public int logicalDelete(Connection con, int workTypeId) {
		DSLContext dsl = getDSL(con);

		return dsl
				.update(WORK_TYPES)
				.set(WORK_TYPES.REVISION_STATUS, OWorkType.REV_STATUS_DELETED)
				.where(
						WORK_TYPES.WORK_TYPE_ID.equal(workTypeId)
				)
				.execute();
	}
}
