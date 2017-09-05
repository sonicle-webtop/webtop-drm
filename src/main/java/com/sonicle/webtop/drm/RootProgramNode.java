/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonicle.webtop.drm;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author stfnnvl
 */
public class RootProgramNode extends ProgramNode {

	private List<ProgramNode> subPrograms = new ArrayList();

	public RootProgramNode(String id, String iconClass) {
		super(id, iconClass);
	}
	
	public void addSubPrograms(String id, String iconClass){
		subPrograms.add(new ProgramNode(id, iconClass));
	}
	
	public List<ProgramNode> getSubPrograms(){
		return subPrograms;
	}
}
