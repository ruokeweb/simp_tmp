package com.mpri.aio.friend.model;
import com.mpri.aio.base.model.DataEntity; 

 /**   
 *  
 * @Description:  用户分享
 * @Author:       syp
 * @project       simp   
 * @CreateDate:   Fri Nov 15 10:12:35 GMT+08:00 2019
 * @Version:      v_2.01
 *    
 */
public class FriShare extends DataEntity<FriShare> {

	private static final long serialVersionUID = 1573783924891L;
	
	private String sharePoint;
	private String userId;

	
	public String getSharePoint() {
		return this.sharePoint;
	}
	public void setSharePoint(String sharePoint) {
		this.sharePoint = sharePoint;
	}	
	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}	

}
