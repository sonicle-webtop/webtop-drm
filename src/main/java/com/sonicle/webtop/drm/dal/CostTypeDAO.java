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
import com.sonicle.webtop.drm.bol.OCostType;
import com.sonicle.webtop.drm.jooq.Sequences;
import static com.sonicle.webtop.drm.jooq.Tables.COST_TYPES;
import com.sonicle.webtop.drm.jooq.tables.records.CostTypesRecord;
import java.sql.Connection;
import java.util.List;
import org.joda.time.DateTime;
import org.jooq.DSLContext;

/**
 *
 * @author lssndrvs
 */
public class CostTypeDAO extends BaseDAO {

	private final static CostTypeDAO INSTANCE = new CostTypeDAO();

	public static CostTypeDAO getInstance() {
		return INSTANCE;
	}
	
	public Long getSequence(Connection con) throws DAOException {
		DSLContext dsl = getDSL(con);
		Long nextID = dsl.nextval(Sequences.SEQ_COST_TYPES);
		return nextID;
	}

	public List<OCostType> selectByDomain(Connection con, String domainId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(COST_TYPES)
				.where(
						COST_TYPES.DOMAIN_ID.equal(domainId)
				)
				.fetchInto(OCostType.class);
	}
	
	public OCostType selectByIdDomainId(Connection con, int id, String domainId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(COST_TYPES)
				.where(
						COST_TYPES.DOMAIN_ID.equal(domainId)
				).and(
						COST_TYPES.ID.equal(id)
				)
				.fetchOneInto(OCostType.class);
	}

	public int insert(Connection con, OCostType item) throws DAOException {
		DSLContext dsl = getDSL(con);

		CostTypesRecord record = dsl.newRecord(COST_TYPES, item);

		return dsl
				.insertInto(COST_TYPES)
				.set(record)
				.execute();
	}
	
	public int updateByIdDomainId(Connection con, OCostType item) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.update(COST_TYPES)
				.set(COST_TYPES.DESCRIPTION, item.getDescription())
				.set(COST_TYPES.MAX_IMPORT, item.getMaxImport())
				.set(COST_TYPES.COST_TYPE, item.getCostType())
				.set(COST_TYPES.WITH_OTHERS, item.getWithOthers())
				.set(COST_TYPES.PER_PERSON, item.getPerPerson())
				.set(COST_TYPES.KM, item.getKm())
				.set(COST_TYPES.ADVANCE_PAYMENT, item.getAdvancePayment())
				.set(COST_TYPES.EXCHANGE, item.getExchange())
				.where(
						COST_TYPES.DOMAIN_ID.equal(item.getDomainId())
				).and(
						COST_TYPES.ID.equal(item.getId())
				)
				.execute();
	}

	public int deleteByDomainId(Connection con, String domainId) {
		DSLContext dsl = getDSL(con);

		return dsl
				.delete(COST_TYPES)
				.where(
						COST_TYPES.DOMAIN_ID.equal(domainId)
				)
				.execute();
	}
	
	public int deleteByIdDomainId(Connection con, int id, String domainId) {
		DSLContext dsl = getDSL(con);

		return dsl
				.delete(COST_TYPES)
				.where(
						COST_TYPES.DOMAIN_ID.equal(domainId)
				).and(
						COST_TYPES.ID.equal(id)
				)
				.execute();
	}
}
