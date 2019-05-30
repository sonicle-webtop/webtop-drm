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
package com.sonicle.webtop.drm.bol.model;

import com.sonicle.webtop.core.CoreManager;
import com.sonicle.webtop.core.app.WT;
import com.sonicle.webtop.core.sdk.UserProfile;
import com.sonicle.webtop.core.sdk.UserProfileId;
import com.sonicle.webtop.core.sdk.WTException;
import com.sonicle.webtop.drm.DrmManager;
import com.sonicle.webtop.drm.DrmServiceSettings;
import static com.sonicle.webtop.drm.Service.logger;
import com.sonicle.webtop.drm.model.CompanyPicture;
import com.sonicle.webtop.drm.model.ExpenseNote;
import com.sonicle.webtop.drm.model.ExpenseNoteDetail;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import javax.imageio.ImageIO;

/**
 *
 * @author lssndrvs
 */
public class RBExpenseNote {
	
	public Integer id;
	public String operatorId;
	public String operatorDescription;
	public Integer companyId;
	public String companyDescription;
	public Date fromDate;
	public Date toDate;
	public BigDecimal totCurrency;
	public String currency;
	public String description;
	
	public ArrayList<RBExpenseNoteDetail> enDetails;
	
	public String footer;
	public Image picture;

	public RBExpenseNote(CoreManager coreMgr, DrmManager drmMgr, ExpenseNote en, DrmServiceSettings ss, CompanyPicture picture) throws WTException, IOException {		
		this.id = en.getId();
		this.operatorId = en.getOperatorId();
		this.operatorDescription = lookupOperator(en.getOperatorProfileId(drmMgr.getTargetProfileId().getDomainId()));
		this.companyId = en.getCompanyId();
		this.companyDescription = drmMgr.getCompany(en.getCompanyId()).getName();
		this.fromDate = en.getFromDate().toDate();
		this.toDate = en.getToDate().toDate();
		this.totCurrency = en.getTotCurrency();
		this.currency = en.getCurrency();
		this.description = en.getDescription();
		
		enDetails = new ArrayList<>();
		for(ExpenseNoteDetail enD : en.getDetails()){
			enDetails.add(new RBExpenseNoteDetail(coreMgr, drmMgr, enD));
		}
		
		this.footer = drmMgr.getCompany(en.getCompanyId()).getFooterColumnLeft();
		
		if(picture != null) {
			try {
				try(ByteArrayInputStream bais = new ByteArrayInputStream(picture.getBytes())) {
					this.picture = ImageIO.read(bais);
				}
			} catch (IOException ex) {
				logger.error("Error in ExpenseNote Picture Configuration ", ex);
			}
		}
	}
	
	private String lookupOperator(UserProfileId operatorProfile){
		UserProfile.Data ud = WT.getUserData(operatorProfile);
		return (ud != null) ? ud.getDisplayName() : operatorProfile.toString();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorDescription() {
		return operatorDescription;
	}

	public void setOperatorDescription(String operatorDescription) {
		this.operatorDescription = operatorDescription;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getCompanyDescription() {
		return companyDescription;
	}

	public void setCompanyDescription(String companyDescription) {
		this.companyDescription = companyDescription;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public BigDecimal getTotCurrency() {
		return totCurrency;
	}

	public void setTotCurrency(BigDecimal totCurrency) {
		this.totCurrency = totCurrency;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ArrayList<RBExpenseNoteDetail> getEnDetails() {
		return enDetails;
	}

	public void setEnDetails(ArrayList<RBExpenseNoteDetail> enDetails) {
		this.enDetails = enDetails;
	}

	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}

	public Image getPicture() {
		return picture;
	}

	public void setPicture(Image picture) {
		this.picture = picture;
	}
}
