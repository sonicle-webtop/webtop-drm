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
import com.sonicle.webtop.drm.bol.OTicketCategory;
import static com.sonicle.webtop.drm.jooq.Sequences.SEQ_TICKET_CATEGORIES;
import static com.sonicle.webtop.drm.jooq.Tables.TICKET_CATEGORIES;
import com.sonicle.webtop.drm.jooq.tables.records.TicketCategoriesRecord;
import java.sql.Connection;
import java.util.List;
import org.jooq.DSLContext;

/**
 *
 * @author dnllr
 */
public class TicketCategoryDAO extends BaseDAO {

	private final static TicketCategoryDAO INSTANCE = new TicketCategoryDAO();

	public static TicketCategoryDAO getInstance() {
		return INSTANCE;
	}

	public Long getSequence(Connection con) throws DAOException {
		DSLContext dsl = getDSL(con);
		Long nextID = dsl.nextval(SEQ_TICKET_CATEGORIES);
		return nextID;
	}

	public List<OTicketCategory> selectByDomain(Connection con, String domainId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(TICKET_CATEGORIES)
				.where(
						TICKET_CATEGORIES.DOMAIN_ID.equal(domainId)
						.and(
								TICKET_CATEGORIES.REVISION_STATUS.equal(OTicketCategory.REV_STATUS_MODIFIED)
						)
				)
				.fetchInto(OTicketCategory.class);
	}
	
	public OTicketCategory selectById(Connection con, String domainId, int ticketCategoryId) throws DAOException {
		DSLContext dsl = getDSL(con);
		
		return dsl
			.select()
			.from(TICKET_CATEGORIES)
			.where(
				TICKET_CATEGORIES.DOMAIN_ID.equal(domainId)
				.and(
						TICKET_CATEGORIES.TICKET_CATEGORY_ID.equal(ticketCategoryId)
				)
			)
			.fetchOneInto(OTicketCategory.class);
	}

	public int insert(Connection con, OTicketCategory item) throws DAOException {
		DSLContext dsl = getDSL(con);

		item.setRevisionStatus(OTicketCategory.REV_STATUS_MODIFIED);

		TicketCategoriesRecord record = dsl.newRecord(TICKET_CATEGORIES, item);

		return dsl
				.insertInto(TICKET_CATEGORIES)
				.set(record)
				.execute();
	}

	public int update(Connection con, OTicketCategory item) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.update(TICKET_CATEGORIES)
				.set(TICKET_CATEGORIES.EXTERNAL_ID, item.getExternalId())
				.set(TICKET_CATEGORIES.DESCRIPTION, item.getDescription())
				.where(
						TICKET_CATEGORIES.TICKET_CATEGORY_ID.equal(item.getTicketCategoryId())
				)
				.execute();
	}

	public int deleteById(Connection con, int id) {
		DSLContext dsl = getDSL(con);

		return dsl
				.delete(TICKET_CATEGORIES)
				.where(
						TICKET_CATEGORIES.TICKET_CATEGORY_ID.equal(id)
				)
				.execute();
	}

	public int logicalDelete(Connection con, int ticketCategoryId) {
		DSLContext dsl = getDSL(con);

		return dsl
				.update(TICKET_CATEGORIES)
				.set(TICKET_CATEGORIES.REVISION_STATUS, OTicketCategory.REV_STATUS_DELETED)
				.where(
						TICKET_CATEGORIES.TICKET_CATEGORY_ID.equal(ticketCategoryId)
				)
				.execute();
	}
}