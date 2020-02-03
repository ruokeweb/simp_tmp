package com.mpri.aio.ranking.model;
import com.mpri.aio.base.model.DataEntity; 

 /**   
 *  
 * @Description:  校友捐赠次数归纳表
 * @Author:       syp
 * @project       simp   
 * @CreateDate:   Mon Nov 18 11:16:06 GMT+08:00 2019
 * @Version:      v_2.01
 *    
 */
public class ChaDonTime extends DataEntity<ChaDonTime> {

	private static final long serialVersionUID = 1574046924953L;
	
	private String userId;
	private String year;
	private String month;
	private Integer donTimes;

	

	public String getUserId() {
		return userId;
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
	public Integer getDonTimes() {
		return this.donTimes;
	}
	public void setDonTimes(Integer donTimes) {
		this.donTimes = donTimes;
	}	

}
