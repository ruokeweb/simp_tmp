package com.mpri.aio.system.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.system.mapper.SysUserMapper;
import com.mpri.aio.system.model.SysUser;

/**
* 用户信息接口实现
* @author Cary
* @date 2018年7月22日
*/
@Service
public class SysUserService extends CrudService<SysUserMapper, SysUser>  {
	

    
	/**
	 * 保存用户信息的重写 
	 * @param username
	 * @return
	 */
    @Override
	@CachePut(value = "userCache", key ="#sysUser.username")
	public SysUser save(SysUser sysUser) {
		super.save(sysUser);
		return mapper.getSysUserByUsername(sysUser);
	} 

	/**
	 * 根据用户名获取用户对象
	 * @param username
	 * @return
	 */
	@Cacheable(value = "userCache", key ="#username")
	public SysUser getSysUserByUsername(String username) {
		SysUser sysUser=new SysUser();
		sysUser.setUsername(username);
		return mapper.getSysUserByUsername(sysUser);
	}
	
	/**
	 * 删除用户并删除该用户缓存
	 */
	@CacheEvict(value = "userCache", key ="#sysUser.username")
	@Transactional(readOnly = false)
	public void deleteUser(SysUser sysUser) {
		mapper.delete(sysUser);
		mapper.deleteUserRole(sysUser);
	}
		

	/**
	 * 获取既有用户数
	 * @param username
	 * @return
	 */
	public int getUserNum(String username) {
		SysUser sysUser=new SysUser();
		sysUser.setUsername(username);
		return mapper.getUserNum(sysUser);
	}
	
    /**
     * 插入用户和角色信息
     * <p>Title: insertUserRole</p>  
     * <p>Description: </p>  
     * @param sysUser
     */
	@Transactional(readOnly = false)
	public void insertUserRole(SysUser sysUser) {
		mapper.deleteUserRole(sysUser);
		mapper.insertUserRole(sysUser);
	}
	
	/**
     * 插入用户和默认校友会
     * <p>Title: insertUserAs</p>  
     * <p>Description: </p>  
     * @param sysUser
     */
	public void insertDfAs(SysUser sysUser) {
		mapper.insertDfAs(sysUser);
	}
	
    /**
     * 删除用户和角色信息
     * <p>Title: insertUserRole</p>  
     * <p>Description: </p>  
     * @param sysUser
     */
	@Transactional(readOnly = false)
	public void deleteUserRole(SysUser sysUser) {
		mapper.deleteUserRole(sysUser);
	}

	/**
	 * 根据用户名获取密码
	 * @param username
	 * @return
	 */
	public SysUser getPwdByUsername(SysUser sysUser) {
		return mapper.getSysUserByUsername(sysUser);
	}
	
//	@Transactional(rollbackFor=Exception.class)
//	public void saveUsers(List<SysUser> userList) {
//		for(SysUser su:userList) {
//			mapper.insert(su);	
//		}
//		if (true) {
//	        throw new RuntimeException("save 抛异常了");
//	    }
//	}
	
	/**
	 * 
	 * <p>Title: judgeEmailOrPhone</p>  
	 * <p>Description: </p>  
	 * @return
	 */
	public SysUser judgeEmailOrPhone(SysUser sysUser) {
		SysUser res = getPwdByUsername(sysUser);
		if(null != res) {
			return res;
		}
		return null;
	}
	
	
	
	/**
	 * 判断验证码是否正确
	 * <p>Title: judgeCode</p>  
	 * <p>Description: </p>  
	 * @param username
	 * @param code
	 * @return
	 */
	public Boolean judgeCode(String username,String code,Map<String,String> codeMap) {
		String res =  codeMap.get(username);
		if(null != res && res.equals(code)) {
			return true;
		}		
		return false;
	}
	
	
	/**
	 * 查询需要更新的的用户
	 */
	
	public List<SysUser> loadAllByUpdate(@Param("entity")SysUser sysUser){
		return mapper.loadAllByUpdate(sysUser);
	}
	/**
	 * 保存传输的数据
	 */
	public void transferSave (SysUser user) {
		mapper.transferSave(user);
	}
	


	public List<SysUser> loadAllUpdatePassword(SysUser sysUser) {
		return  this.mapper.loadAllUpdatePassword(sysUser);
	}

	public void updateSynchronizedUser(SysUser user) {
		this.mapper.updateSynchronizedUser(user);
	}
	
}