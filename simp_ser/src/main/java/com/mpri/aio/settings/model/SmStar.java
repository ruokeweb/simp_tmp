package com.mpri.aio.settings.model;
import java.util.ArrayList;
import java.util.List;

import com.mpri.aio.base.model.DataEntity; 

 /**   
 *  
 * @Description:  星级
 * @Author:       syp
 * @project       sm   
 * @CreateDate:   Wed Sep 12 10:53:53 CST 2018
 * @Version:      v_1.0
 *    
 */
public class SmStar extends DataEntity<SmStar> {

	private static final long serialVersionUID = 1536720825575L;
	
	private String name;
	private Integer infoLevel;
	private String type;

	private List<SmStarSminfo> smStarSminfos = new ArrayList<SmStarSminfo>();
	
	
	public List<SmStarSminfo> getSmStarSminfos() {
		return smStarSminfos;
	}
	public void setSmStarSminfos(List<SmStarSminfo> smStarSminfos) {
		this.smStarSminfos = smStarSminfos;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}	
	public Integer getInfoLevel() {
		return this.infoLevel;
	}
	public void setInfoLevel(Integer infoLevel) {
		this.infoLevel = infoLevel;
	}	
	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}	

}
