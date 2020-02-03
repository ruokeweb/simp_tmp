package com.mpri.aio.system.model;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.mpri.aio.association.model.AsAssociation;
import com.mpri.aio.base.model.DataEntity;
/**
 * 用户实体类-JSR380校验
 * @author Cary
 * @Date  2018年7月31日
 */
public class SysUser extends DataEntity<SysUser> {

	private static final long serialVersionUID = 1L;

	//用户名
    @NotNull(message="用户名不能为空")
    @Size(min = 2, max = 64, message = "用户名长度必须大于 2 且小于 64 字")
    private String username;

    //密码
    private String password;

    //安全码
    private String safecode;
    
    //昵称
    @NotNull(message="昵称不能为空")
    @Size(min = 1, max = 64, message = "昵称长度必须大于 2 且小于 64 字")
    private String virtualName;
    
    //虚拟头像
    private String virtualPhoto;

    //电话
    private String mobile;

    //邮件
    //@Email(message="邮件不能为空")
    private String email;
    
    //用户类型
    @NotNull(message="用户类型不能为空")
    private String userType;

    //用户来源
    private String userSource;
    
    //证件号码
    private String idcard;
    
    //更新标记
    private String updateFlag;
    
    //所属学校
    private String fromSchool;
    
    /*角色列表*/
    private List<SysRole> roleList;
    
    
    /*校友会id*/
    private String asId;

    private int count;
    
    /*校友会列表*/
    private List<AsAssociation> asList;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSafecode() {
		return safecode;
	}

	public void setSafecode(String safecode) {
		this.safecode = safecode;
	}

	public String getVirtualName() {
		return virtualName;
	}

	public void setVirtualName(String virtualName) {
		this.virtualName = virtualName;
	}

	public String getVirtualPhoto() {
		return virtualPhoto;
	}

	public void setVirtualPhoto(String virtualPhoto) {
		this.virtualPhoto = virtualPhoto;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserSource() {
		return userSource;
	}

	public void setUserSource(String userSource) {
		this.userSource = userSource;
	}

	public List<SysRole> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<SysRole> roleList) {
		this.roleList = roleList;
	}
	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getAsId() {
		return asId;
	}

	public void setAsId(String asId) {
		this.asId = asId;
	}

	public List<AsAssociation> getAsList() {
		return asList;
	}

	public void setAsList(List<AsAssociation> asList) {
		this.asList = asList;
	}

	public String getUpdateFlag() {
		return updateFlag;
	}

	public void setUpdateFlag(String updateFlag) {
		this.updateFlag = updateFlag;
	}

	public String getFromSchool() {
		return fromSchool;
	}

	public void setFromSchool(String fromSchool) {
		this.fromSchool = fromSchool;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	
	
	

}