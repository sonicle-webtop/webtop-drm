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
package com.sonicle.webtop.drm;

import com.sonicle.webtop.core.sdk.BaseServiceSettings;
import static com.sonicle.webtop.drm.DrmSettings.*;

/**
 *
 * @author stfnnvl
 */
public class DrmServiceSettings extends BaseServiceSettings {

	public DrmServiceSettings(String serviceId, String domainId) {
		super(serviceId, domainId);
	}

	public boolean getUseStatisticCustomer() {
		return getBoolean(USE_STATISTIC_CUSTOMER, Boolean.FALSE); //ricerca prima a livello di servizio poi di dominio
	}

	public void setUseStatisticCustomer(boolean enabled) {
		setBoolean(USE_STATISTIC_CUSTOMER, enabled); //imposta il settings a livello di dominio
	}

	public boolean getPrintDaysTransfert() {
		return getBoolean(PRINT_DAYS_TRANSFERT, Boolean.FALSE); //ricerca prima a livello di servizio poi di dominio
	}

	public void setPrintDaysTransfert(boolean enabled) {
		setBoolean(PRINT_DAYS_TRANSFERT, enabled); //imposta il settings a livello di dominio
	}

	public boolean getPrintTransfertDescription() {
		return getBoolean(PRINT_TRANSFERT_DESCRIPTION, Boolean.FALSE); //ricerca prima a livello di servizio poi di dominio
	}

	public void setPrintTransfertDescription(boolean enabled) {
		setBoolean(PRINT_TRANSFERT_DESCRIPTION, enabled); //imposta il settings a livello di dominio
	}

	public boolean getPrintSignature() {
		return getBoolean(PRINT_SIGNATURE, Boolean.FALSE); //ricerca prima a livello di servizio poi di dominio
	}

	public void setPrintSignature(boolean enabled) {
		setBoolean(PRINT_SIGNATURE, enabled); //imposta il settings a livello di dominio
	}

	public Integer getRoundingHour() {
		return getInteger(ROUNDING_HOUR, -1); //ricerca prima a livello di servizio poi di dominio
	}

	public void setRoundingHour(Integer rounding) {
		setInteger(ROUNDING_HOUR, rounding == null ? -1 : rounding); //imposta il settings a livello di dominio
	}

	public boolean getTracking() {
		return getBoolean(TRACKING, Boolean.FALSE); //ricerca prima a livello di servizio poi di dominio
	}

	public void setTracking(boolean enabled) {
		setBoolean(TRACKING, enabled); //imposta il settings a livello di dominio
	}

	public boolean getMailTracking() {
		return getBoolean(MAIL_TRACKING, Boolean.FALSE); //ricerca prima a livello di servizio poi di dominio
	}

	public void setMailTracking(boolean enabled) {
		setBoolean(MAIL_TRACKING, enabled); //imposta il settings a livello di dominio
	}

	public boolean getCloudTracking() {
		return getBoolean(CLOUD_TRACKING, Boolean.FALSE); //ricerca prima a livello di servizio poi di dominio
	}

	public void setCloudTracking(boolean enabled) {
		setBoolean(CLOUD_TRACKING, enabled); //imposta il settings a livello di dominio
	}

}
