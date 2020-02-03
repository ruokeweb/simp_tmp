package com.mpri.aio.ranking.model;
import com.mpri.aio.base.model.DataEntity; 

 /**   
 *  
 * @Description:  校友捐赠钱数归纳表
 * @Author:       syp
 * @project       simp   
 * @CreateDate:   Mon Nov 18 11:14:20 GMT+08:00 2019
 * @Version:      v_2.01
 *    
 */
public class ChaDonMoney extends DataEntity<ChaDonMoney> {

	private static final long serialVersionUID = 1574046817463L;
	
	private String userId;
	private String year;
	private String month;
	private Double donMoney;

	
	
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
	public Double getDonMoney() {
		return this.donMoney;
	}
	public void setDonMoney(Double donMoney) {
		this.donMoney = donMoney;
	}	

}
