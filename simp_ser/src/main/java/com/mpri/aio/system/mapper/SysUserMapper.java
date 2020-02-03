package com.mpri.aio.system.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.system.model.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户信息mapper
 * @author Cary
 * @Date  2018年7月31日
 */
@Mapper
public interface SysUserMapper extends CrudMapper<SysUser>{
	/**
	 * 根据用户名获取用户
	 * @param username
	 * @return
	 */
    SysUser getSysUserByUsername(SysUser sysUser);
    
    /**
     * 插入用户和角色信息
    * <p>Title: insertUserRole</p>  
    * <p>Description: </p>  
    * @param sysUser
     */
    void insertUserRole(SysUser sysUser);
    
    /**
     * 插入用户和校友会
     * <p>Title: insertUserAs</p>  
     * <p>Description: </p>  
     * @param sysUser
     */
    void insertDfAs(SysUser sysUser);
    
    /**
     * 删除用户关联角色
    * <p>Title: deleteUserRole</p>  
    * <p>Description: </p>  
    * @param sysUser
     */
    void deleteUserRole(SysUser sysUser);
    
    /**
     * 获取既有用户数
     * @param sysUser
     * @return
     */
    int getUserNum(SysUser sysUser);

    List<SysUser> loadAllUpdatePassword(@Param("entity")SysUser sysUser);

	void updateSynchronizedUser(SysUser user);
	
	
	/**
	 * 保存传输的数据
	 */
	void transferSave (SysUser user);
	
	
	/**
	 * 获取校友
	 */
	List<SysUser> loadAllByUpdate(@Param("entity")SysUser sysUser);
	
}