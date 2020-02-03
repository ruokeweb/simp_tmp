package com.mpri.aio.message.model;
import com.mpri.aio.base.model.DataEntity; 

 /**   
 *  
 * @Description:  消息模板表
 * @Author:       Clown
 * @project       simp   
 * @CreateDate:   Thu Mar 07 18:43:59 CST 2019
 * @Version:      v_2.0
 *    
 */
public class MesTemplate extends DataEntity<MesTemplate> {

	private static final long serialVersionUID = 1551955415589L;
	
	private String name;
	private String backgroupImg;
	private String type;
	private String content;

	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}	
	public String getBackgroupImg() {
		return this.backgroupImg;
	}
	public void setBackgroupImg(String backgroupImg) {
		this.backgroupImg = backgroupImg;
	}	
	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}	
	public String getContent() {
		return this.content;
	}
	public void setContent(String content) {
		this.content = content;
	}	

}
