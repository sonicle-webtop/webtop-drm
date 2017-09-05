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
import com.sonicle.webtop.drm.bol.OBusinessTrip;
import com.sonicle.webtop.drm.bol.OWorkType;
import static com.sonicle.webtop.drm.jooq.Sequences.SEQ_BUSINESS_TRIPS;
import static com.sonicle.webtop.drm.jooq.Tables.BUSINESS_TRIPS;
import com.sonicle.webtop.drm.jooq.tables.records.BusinessTripsRecord;
import java.sql.Connection;
import java.util.List;
import org.jooq.DSLContext;

/**
 *
 * @author stfnnvl
 */
public class BusinessTripDao extends BaseDAO {

	private final static BusinessTripDao INSTANCE = new BusinessTripDao();

	public static BusinessTripDao getInstance() {
		return INSTANCE;
	}

	public Long getSequence(Connection con) throws DAOException {
		DSLContext dsl = getDSL(con);
		Long nextID = dsl.nextval(SEQ_BUSINESS_TRIPS);
		return nextID;
	}

	public List<OBusinessTrip> selectByDomain(Connection con, String domainId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(BUSINESS_TRIPS)
				.where(
						BUSINESS_TRIPS.DOMAIN_ID.equal(domainId)
						.and(
								BUSINESS_TRIPS.REVISION_STATUS.equal(OBusinessTrip.REV_STATUS_MODIFIED)
						)
				)
				.fetchInto(OBusinessTrip.class);
	}

	public int insert(Connection con, OBusinessTrip item) throws DAOException {
		DSLContext dsl = getDSL(con);

		item.setRevisionStatus(OBusinessTrip.REV_STATUS_MODIFIED);

		BusinessTripsRecord record = dsl.newRecord(BUSINESS_TRIPS, item);

		return dsl
				.insertInto(BUSINESS_TRIPS)
				.set(record)
				.execute();
	}

	public int update(Connection con, OBusinessTrip item) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.update(BUSINESS_TRIPS)
				.set(BUSINESS_TRIPS.EXTERNAL_ID, item.getExternalId())
				.set(BUSINESS_TRIPS.DESCRIPTION, item.getDescription())
				.where(
						BUSINESS_TRIPS.BUSINESS_TRIP_ID.equal(item.getBusinessTripId())
				)
				.execute();
	}

	public int deleteById(Connection con, int id) {
		DSLContext dsl = getDSL(con);

		return dsl
				.delete(BUSINESS_TRIPS)
				.where(
						BUSINESS_TRIPS.BUSINESS_TRIP_ID.equal(id)
				)
				.execute();
	}

	public int logicalDelete(Connection con, int businessTripId) {
		DSLContext dsl = getDSL(con);

		return dsl
				.update(BUSINESS_TRIPS)
				.set(BUSINESS_TRIPS.REVISION_STATUS, OBusinessTrip.REV_STATUS_DELETED)
				.where(
						BUSINESS_TRIPS.BUSINESS_TRIP_ID.equal(businessTripId)
				)
				.execute();
	}
}
