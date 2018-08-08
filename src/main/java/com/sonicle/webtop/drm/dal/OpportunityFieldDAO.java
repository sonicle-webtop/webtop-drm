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
import com.sonicle.webtop.drm.bol.OOpportunityField;
import static com.sonicle.webtop.drm.jooq.Tables.OPPORTUNITY_FIELDS;
import java.sql.Connection;
import java.util.List;
import org.jooq.DSLContext;
import static org.jooq.impl.DSL.*;

/**
 *
 * @author lssndrvs
 */
public class OpportunityFieldDAO extends BaseDAO {

	private final static OpportunityFieldDAO INSTANCE = new OpportunityFieldDAO();

	public static OpportunityFieldDAO getInstance() {
		return INSTANCE;
	}

	public List<OOpportunityField> selectByDomain(Connection con, String domainId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(OPPORTUNITY_FIELDS)
				.where(
						OPPORTUNITY_FIELDS.DOMAIN_ID.equal(domainId)
				)
				.fetchInto(OOpportunityField.class);
	}
	
	public List<OOpportunityField> selectByDomainIdTabId(Connection con, String domainId, String tabId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(OPPORTUNITY_FIELDS)
				.where(
						OPPORTUNITY_FIELDS.DOMAIN_ID.equal(domainId)
				)
				.and(
						OPPORTUNITY_FIELDS.TAB_ID.equal(tabId)
				)
				.orderBy(
						OPPORTUNITY_FIELDS.ORDER
				)
				.fetchInto(OOpportunityField.class);
	}

	public int insertByDomainIdDefault(Connection con, String domainId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.insertInto(OPPORTUNITY_FIELDS)
				.select(
						select(
							inline(domainId).as("domain_id")
						)
						.select(
							OPPORTUNITY_FIELDS.TAB_ID,
							OPPORTUNITY_FIELDS.FIELD_ID,
							OPPORTUNITY_FIELDS.VISIBLE,
							OPPORTUNITY_FIELDS.REQUIRED,
							OPPORTUNITY_FIELDS.ORDER,
							OPPORTUNITY_FIELDS.LABEL
						)
						.from(OPPORTUNITY_FIELDS)
						.where(OPPORTUNITY_FIELDS.DOMAIN_ID.equal("*"))
				)
				.execute();
	}

	public int deleteByDomainId(Connection con, String domainId) {
		DSLContext dsl = getDSL(con);

		return dsl
				.delete(OPPORTUNITY_FIELDS)
				.where(
						OPPORTUNITY_FIELDS.DOMAIN_ID.equal(domainId)
				)
				.execute();
	}

	public int update(Connection con, OOpportunityField item) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.update(OPPORTUNITY_FIELDS)
				.set(OPPORTUNITY_FIELDS.VISIBLE, item.getVisible())
				.set(OPPORTUNITY_FIELDS.REQUIRED, item.getRequired())
				.set(OPPORTUNITY_FIELDS.ORDER, item.getOrder())
				.set(OPPORTUNITY_FIELDS.LABEL, item.getLabel())
				.where(
						OPPORTUNITY_FIELDS.DOMAIN_ID.equal(item.getDomainId())
				)
				.and(
						OPPORTUNITY_FIELDS.TAB_ID.equal(item.getTabId())
				)
				.and(
						OPPORTUNITY_FIELDS.FIELD_ID.equal(item.getFieldId())
				)
				.execute();
	}
}
