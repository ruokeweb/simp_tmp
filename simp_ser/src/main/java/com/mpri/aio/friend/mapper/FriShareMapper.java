package com.mpri.aio.friend.mapper;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.friend.model.FriShare;


 /**   
 *  
 * @Description:  用户分享——DAO
 * @Author:       syp
 * @project 	  simp 
 * @CreateDate:   Fri Nov 15 10:12:35 GMT+08:00 2019
 * @Version:      v_2.01
 *    
 */
@Mapper
public interface FriShareMapper extends CrudMapper<FriShare>{

	List<Map> findCountByDay(FriShare friShare);
}