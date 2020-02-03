package com.mpri.aio.settings.model;
import com.mpri.aio.base.model.DataEntity; 

 /**   
 *  
 * @Description:  页面信息配置
 * @Author:       zdl
 * @project       simp   
 * @CreateDate:   Tue May 28 14:54:02 GMT+08:00 2019
 * @Version:      v_1.2
 *    
 */
public class SettingPage extends DataEntity<SettingPage> {

	private static final long serialVersionUID = 1559026414030L;
	
	private String name;
	private String code;
	private String content;

	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;

	}	
	public String getCode() {
		return this.code;
	}
	public void setCode(String code) {
		this.code = code;
		//指定为缓存编码
		super.setCacheKey(this.code);
	}	
	public String getContent() {
		return this.content;
	}
	public void setContent(String content) {
		this.content = content;
	}	

}
