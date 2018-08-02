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
import com.sonicle.webtop.drm.bol.OOpportunityDocument;
import static com.sonicle.webtop.drm.jooq.Tables.OPPORTUNITY_DOCUMENTS;
import com.sonicle.webtop.drm.jooq.tables.records.OpportunityDocumentsRecord;
import java.sql.Connection;
import java.util.List;
import org.jooq.DSLContext;

/**
 *
 * @author lssndrvs
 */
public class OpportunityDocumentDAO extends BaseDAO {

	private final static OpportunityDocumentDAO INSTANCE = new OpportunityDocumentDAO();

	public static OpportunityDocumentDAO getInstance() {
		return INSTANCE;
	}

	public List<OOpportunityDocument> selectByOpportunity(Connection con, int opportunityId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(OPPORTUNITY_DOCUMENTS)
				.where(
						OPPORTUNITY_DOCUMENTS.OPPORTUNITY_ID.equal(opportunityId)
				)
				.fetchInto(OOpportunityDocument.class);
	}
	
	public OOpportunityDocument select(Connection con, String id) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(OPPORTUNITY_DOCUMENTS)
				.where(
						OPPORTUNITY_DOCUMENTS.ID.equal(id)
				)
				.fetchOneInto(OOpportunityDocument.class);
	}

	public int insert(Connection con, OOpportunityDocument item) throws DAOException {
		DSLContext dsl = getDSL(con);

		OpportunityDocumentsRecord record = dsl.newRecord(OPPORTUNITY_DOCUMENTS, item);

		return dsl
				.insertInto(OPPORTUNITY_DOCUMENTS)
				.set(record)
				.execute();
	}

	public int deleteById(Connection con, String id) {
		DSLContext dsl = getDSL(con);

		return dsl
				.delete(OPPORTUNITY_DOCUMENTS)
				.where(
						OPPORTUNITY_DOCUMENTS.ID.equal(id)
				)
				.execute();
	}
	
	public int deleteByOpportunity(Connection con, int opportunityId) {
		DSLContext dsl = getDSL(con);

		return dsl
				.delete(OPPORTUNITY_DOCUMENTS)
				.where(
						OPPORTUNITY_DOCUMENTS.OPPORTUNITY_ID.equal(opportunityId)
				)
				.execute();
	}

}
