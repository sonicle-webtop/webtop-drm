/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonicle.webtop.drm;

import java.util.LinkedHashMap;

/**
 *
 * @author lssndrvs
 */
public class ProgramNode {

	

	private String id;
	private String iconClass;
	
	

	public ProgramNode(String id, String iconClass) {
		this.id = id;
		this.iconClass = iconClass;
	}

	public String getId() {
		return id;
	}

	public String getIconClass() {
		return iconClass;
	}
	
}
