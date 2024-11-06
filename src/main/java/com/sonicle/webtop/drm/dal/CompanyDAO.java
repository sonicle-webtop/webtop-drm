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
import com.sonicle.webtop.drm.bol.OCompany;
import static com.sonicle.webtop.drm.jooq.Sequences.SEQ_COMPANIES;
import static com.sonicle.webtop.drm.jooq.Tables.COMPANIES;
import static com.sonicle.webtop.drm.jooq.Tables.COMPANIES_USERS;
import com.sonicle.webtop.drm.jooq.tables.records.CompaniesRecord;
import java.sql.Connection;
import java.util.List;
import org.jooq.DSLContext;

/**
 *
 * @author lssndrvs
 */
public class CompanyDAO extends BaseDAO{
	
	private final static CompanyDAO INSTANCE = new CompanyDAO();

	public static CompanyDAO getInstance() {
		return INSTANCE;
	}
	
	public Long getSequence(Connection con) throws DAOException {
		DSLContext dsl = getDSL(con);
		Long nextID = dsl.nextval(SEQ_COMPANIES);
		return nextID;
	}
	
	public int insertCompany(Connection con,OCompany item) throws DAOException {
		DSLContext dsl = getDSL(con);
		CompaniesRecord record = dsl.newRecord(COMPANIES,item);
		
		return dsl
				.insertInto(COMPANIES)
				.set(record)
				.execute();
	}

	public List<OCompany> selectCompaniesByDomainUser(Connection con, String domain, String user) throws DAOException{
		DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(COMPANIES)
				.join(COMPANIES_USERS).on(
					COMPANIES.COMPANY_ID.equal(COMPANIES_USERS.COMPANY_ID)
				)
				.where(COMPANIES.DOMAIN_ID.equal(domain))
				.and(COMPANIES_USERS.USER_ID.equal(user))
				.orderBy(COMPANIES.COMPANY_ID)
				.fetchInto(OCompany.class);
	}
	
	public List<OCompany> selectCompaniesByDomain(Connection con, String domain) throws DAOException{
		DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(COMPANIES)
				.where(COMPANIES.DOMAIN_ID.equal(domain))
				.orderBy(COMPANIES.COMPANY_ID)
				.fetchInto(OCompany.class);
	}

	public OCompany selectById(Connection con, int companyId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
			.select()
			.from(COMPANIES)
			.where(COMPANIES.COMPANY_ID.equal(companyId))
			.fetchOneInto(OCompany.class);
	}
	
	public int update(Connection con, OCompany item) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
			.update(COMPANIES)
			.set(COMPANIES.REVISION_STATUS,item.getRevisionStatus())
			.set(COMPANIES.NAME,item.getName())
			.set(COMPANIES.ADDRESS,item.getAddress())
			.set(COMPANIES.POSTAL_CODE,item.getPostalCode())
			.set(COMPANIES.CITY,item.getCity())
			.set(COMPANIES.STATE,item.getState())
			.set(COMPANIES.PHONE,item.getPhone())
			.set(COMPANIES.FAX,item.getFax())
			.set(COMPANIES.VAT,item.getVat())
			.set(COMPANIES.TAX_CODE,item.getTaxCode())
			.set(COMPANIES.REA,item.getRea())	
			.set(COMPANIES.BUSINESS_REGISTER,item.getBusinessRegister())
			.set(COMPANIES.FOOTER_COLUMNS,item.getFooterColumns())
			.set(COMPANIES.FOOTER_COLUMN_LEFT,item.getFooterColumnLeft())
			.set(COMPANIES.FOOTER_COLUMN_RIGHT,item.getFooterColumnRight())
			.where(
				COMPANIES.COMPANY_ID.equal(item.getCompanyId())
			)
			.execute();
	}

	public int deleteById(Connection con, int companyId ) {
		DSLContext dsl = getDSL(con);
		return dsl
			.delete(COMPANIES)
			.where(
				COMPANIES.COMPANY_ID.equal(companyId)
			)
			.execute();
	}
	
}
