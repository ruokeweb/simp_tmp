package com.mpri.aio.ranking.model;
import com.mpri.aio.base.model.DataEntity; 

 /**   
 *  
 * @Description:  校友分享归纳表
 * @Author:       syp
 * @project       simp   
 * @CreateDate:   Mon Nov 18 11:19:42 GMT+08:00 2019
 * @Version:      v_2.01
 *    
 */
public class ChaShare extends DataEntity<ChaShare> {

	private static final long serialVersionUID = 1574047137193L;
	
	private String userId;
	private String year;
	private String month;
	private Integer shareTimes;

	
	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}	
	public String getYear() {
		return this.year;
	}
	public void setYear(String year) {
		this.year = year;
	}	
	public String getMonth() {
		return this.month;
	}
	public void setMonth(String month) {
		this.month = month;
	}	
	public Integer getShareTimes() {
		return this.shareTimes;
	}
	public void setShareTimes(Integer shareTimes) {
		this.shareTimes = shareTimes;
	}	

}
