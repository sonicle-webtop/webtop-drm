/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
Ext.define('Sonicle.webtop.drm.model.ServiceVars', {
	extend: 'WTA.sdk.model.ServiceVars',
	fields: [
		WTF.field('useStatisticCustomer', 'boolean', false),
		WTF.field('printDaysTransfert', 'boolean', false), 
		WTF.field('printTransfertDescription', 'boolean', false),
		WTF.field('printSignature', 'boolean', false),
		WTF.field('roundingHour', 'int', true),
		WTF.field('tracking', 'boolean', true),
		WTF.field('mailTracking', 'boolean', true),
		WTF.field('cloudTracking', 'boolean', true),
		WTF.field('defaultApplySignature', 'boolean', true),
		WTF.field('defaultChargeTo', 'boolean', true),
		WTF.field('defaultFreeSupport', 'boolean', true),
		WTF.field('workReportDefaultStatus', 'string', true),
		WTF.field('opportunityDefaultStatus', 'string', true)
	]
});