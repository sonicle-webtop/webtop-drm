/* 
 * Copyright (C) 2014 Sonicle S.r.l.
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
 * display the words "Copyright (C) 2014 Sonicle S.r.l.".
 */
package com.sonicle.webtop.drm.dal;

import com.sonicle.webtop.drm.bol.OCompanyPicture;
import static com.sonicle.webtop.drm.jooq.Tables.COMPANIES_PICTURES;
import com.sonicle.webtop.drm.jooq.tables.records.CompaniesPicturesRecord;
import com.sonicle.webtop.core.dal.BaseDAO;
import com.sonicle.webtop.core.dal.DAOException;
import java.sql.Connection;
import org.jooq.DSLContext;

/**
 *
 * @author malbinola
 */
public class CompanyPictureDAO extends BaseDAO {
	private final static CompanyPictureDAO INSTANCE = new CompanyPictureDAO();
	public static CompanyPictureDAO getInstance() {
		return INSTANCE;
	}
	
	public boolean hasPicture(Connection con, int contactId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
			.selectCount()
			.from(COMPANIES_PICTURES)
			.where(
				COMPANIES_PICTURES.COMPANY_ID.equal(contactId)
			)
			.fetchOne(0, Integer.class) == 1;
	}
	
	public OCompanyPicture select(Connection con, int contactId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
			.select()
			.from(COMPANIES_PICTURES)
			.where(
				COMPANIES_PICTURES.COMPANY_ID.equal(contactId)
			)
			.fetchOneInto(OCompanyPicture.class);
	}
	
	public int insert(Connection con, OCompanyPicture item) throws DAOException {
		DSLContext dsl = getDSL(con);
		CompaniesPicturesRecord record = dsl.newRecord(COMPANIES_PICTURES, item);
		return dsl
			.insertInto(COMPANIES_PICTURES)
			.set(record)
			.execute();
	}
	
	public int delete(Connection con, int contactId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
			.delete(COMPANIES_PICTURES)
			.where(
				COMPANIES_PICTURES.COMPANY_ID.equal(contactId)
			)
			.execute();
	}
}
