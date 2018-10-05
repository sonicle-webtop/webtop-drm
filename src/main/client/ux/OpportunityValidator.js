/*
 * Sonicle ExtJs UX
 * Copyright (C) 2015 Sonicle S.r.l.
 * sonicle@sonicle.com
 * http://www.sonicle.com
 */
Ext.define('Sonicle.webtop.drm.ux.OpportunityValidator', {
	extend: 'Ext.data.validator.Presence',
	alias: 'data.validator.wtdrmdynpresence',
	
	config: {
		/**
		 * @cfg {String} ifField
		 * The field name of the field whose {@link#ifValues values} come from.
		 */
		requiredFieldGroupName: null,
		
		/**
		 * @cfg {String} ifField
		* The field name of the field whose {@link#ifValues values} come from.
		 */
		requiredFieldName: null	
	},
	
	validate: function(v, rec) {
		var me = this,
				group = WT.getVar("com.sonicle.webtop.drm","opportunityRequiredFields")[me.getRequiredFieldGroupName()],
				field = null;
		
		Ext.each(group, function(element){
			if(element.field === me.getRequiredFieldName()){
				field = element;
				return false;
			}
		});
		
		if(!field.required) return true;
		else return me.callParent(arguments);
	}
});

