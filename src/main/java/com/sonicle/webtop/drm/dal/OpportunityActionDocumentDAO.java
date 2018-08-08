
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
import com.sonicle.webtop.drm.bol.OOpportunityActionDocument;
import static com.sonicle.webtop.drm.jooq.Tables.OPPORTUNITY_ACTION_DOCUMENTS;
import com.sonicle.webtop.drm.jooq.tables.records.OpportunityActionDocumentsRecord;
import java.sql.Connection;
import java.util.List;
import org.jooq.DSLContext;

/**
 *
 * @author lssndrvs
 */
public class OpportunityActionDocumentDAO extends BaseDAO {

	private final static OpportunityActionDocumentDAO INSTANCE = new OpportunityActionDocumentDAO();

	public static OpportunityActionDocumentDAO getInstance() {
		return INSTANCE;
	}

	public List<OOpportunityActionDocument> selectByOpportunityAction(Connection con, int opportunityActionId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(OPPORTUNITY_ACTION_DOCUMENTS)
				.where(
						OPPORTUNITY_ACTION_DOCUMENTS.OPPORTUNITY_ACTION_ID.equal(opportunityActionId)
				)
				.fetchInto(OOpportunityActionDocument.class);
	}
	
	public OOpportunityActionDocument select(Connection con, String id) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(OPPORTUNITY_ACTION_DOCUMENTS)
				.where(
						OPPORTUNITY_ACTION_DOCUMENTS.ID.equal(id)
				)
				.fetchOneInto(OOpportunityActionDocument.class);
	}

	public int insert(Connection con, OOpportunityActionDocument item) throws DAOException {
		DSLContext dsl = getDSL(con);

		OpportunityActionDocumentsRecord record = dsl.newRecord(OPPORTUNITY_ACTION_DOCUMENTS, item);

		return dsl
				.insertInto(OPPORTUNITY_ACTION_DOCUMENTS)
				.set(record)
				.execute();
	}

	public int deleteById(Connection con, String id) {
		DSLContext dsl = getDSL(con);

		return dsl
				.delete(OPPORTUNITY_ACTION_DOCUMENTS)
				.where(
						OPPORTUNITY_ACTION_DOCUMENTS.ID.equal(id)
				)
				.execute();
	}
	
	public int deleteByOpportunityAction(Connection con, int opportunityActionId) {
		DSLContext dsl = getDSL(con);

		return dsl
				.delete(OPPORTUNITY_ACTION_DOCUMENTS)
				.where(
						OPPORTUNITY_ACTION_DOCUMENTS.OPPORTUNITY_ACTION_ID.equal(opportunityActionId)
				)
				.execute();
	}

}
