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
import com.sonicle.webtop.drm.bol.OWorkReportSetting;
import com.sonicle.webtop.drm.jooq.Sequences;
import static com.sonicle.webtop.drm.jooq.Tables.WORK_REPORTS_SETTINGS;
import com.sonicle.webtop.drm.jooq.tables.records.WorkReportsSettingsRecord;
import java.sql.Connection;
import java.util.List;
import org.jooq.DSLContext;
import static org.jooq.impl.DSL.*;

/**
 *
 * @author stfnnvl
 */
public class WorkReportSettingDAO extends BaseDAO {

	private final static WorkReportSettingDAO INSTANCE = new WorkReportSettingDAO();

	public static WorkReportSettingDAO getInstance() {
		return INSTANCE;
	}

	public Long getSequence(Connection con) throws DAOException {
		DSLContext dsl = getDSL(con);
		Long nextID = dsl.nextval(Sequences.SEQ_WORK_REPORTS_SETTINGS);
		return nextID;
	}

	public int insert(Connection con, OWorkReportSetting item) throws DAOException {
		DSLContext dsl = getDSL(con);
		WorkReportsSettingsRecord record = dsl.newRecord(WORK_REPORTS_SETTINGS, item);

		return dsl
				.insertInto(WORK_REPORTS_SETTINGS)
				.set(record)
				.execute();
	}

	public List<OWorkReportSetting> selectWorkReports(Connection con) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(WORK_REPORTS_SETTINGS)
				.fetchInto(OWorkReportSetting.class);
	}

	public OWorkReportSetting selectByDomainId(Connection con, String domainId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.select()
				.from(WORK_REPORTS_SETTINGS)
				.where(WORK_REPORTS_SETTINGS.DOMAIN_ID.equal(domainId))
				.fetchOneInto(OWorkReportSetting.class);
	}

	public int update(Connection con, OWorkReportSetting item) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
				.update(WORK_REPORTS_SETTINGS)
				.set(WORK_REPORTS_SETTINGS.WARRANTY_TEXT, item.getWarrantyText())
				.where(
						WORK_REPORTS_SETTINGS.WORK_REPORT_SETTING_ID.equal(item.getWorkReportSettingId())
				)
				.execute();
	}

	public int deleteById(Connection con, int id) {
		DSLContext dsl = getDSL(con);
		return dsl
				.delete(WORK_REPORTS_SETTINGS)
				.where(
						WORK_REPORTS_SETTINGS.WORK_REPORT_SETTING_ID.equal(id)
				)
				.execute();
	}

	public void setWorkReportSequence(Connection con, int value) {
		DSLContext dsl = getDSL(con);

		dsl.alterSequence(Sequences.SEQ_WORK_REPORTS_COUNT).restartWith(Long.valueOf(value)).execute();
	}

	public Long getWorkReportSequence(Connection con) {

		DSLContext dsl = getDSL(con);


		return dsl.select(fieldByName("last_value"))
				.from(tableByName(Sequences.SEQ_WORK_REPORTS_COUNT.getSchema().getName(),Sequences.SEQ_WORK_REPORTS_COUNT.getName()))
				.fetchOne(0,Long.class);
	}

}
