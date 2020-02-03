package com.mpri.aio.settings.model;
import java.util.List;

import com.mpri.aio.base.model.DataEntity; 

 /**   
 *  
 * @Description:  星级设置
 * @Author:       syp
 * @project       sm   
 * @CreateDate:   Wed Sep 12 10:52:29 CST 2018
 * @Version:      v_1.0
 *    
 */
public class SmStarSminfo extends DataEntity<SmStarSminfo> {

	private static final long serialVersionUID = 1536720741346L;
	
	private String smStarId;
	private String smSminfoId;
	private String smSminfoCode;
	private SmStar smStar;
	
	public SmStar getSmStar() {
		return smStar;
	}
	public void setSmStar(SmStar smStar) {
		this.smStar = smStar;
	}
	private List<String> startInfoCodes;
	
	
	public List<String> getStartInfoCodes() {
		return startInfoCodes;
	}
	public void setStartInfoCodes(List<String> startInfoCodes) {
		this.startInfoCodes = startInfoCodes;
	}
	public String getSmStarId() {
		return this.smStarId;
	}
	public void setSmStarId(String smStarId) {
		this.smStarId = smStarId;
	}	
	public String getSmSminfoId() {
		return this.smSminfoId;
	}
	public void setSmSminfoId(String smSminfoId) {
		this.smSminfoId = smSminfoId;
	}	
	public String getSmSminfoCode() {
		return this.smSminfoCode;
	}
	public void setSmSminfoCode(String smSminfoCode) {
		this.smSminfoCode = smSminfoCode;
	}	

}
