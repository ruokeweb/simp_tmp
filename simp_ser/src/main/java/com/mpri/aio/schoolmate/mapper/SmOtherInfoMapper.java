package com.mpri.aio.schoolmate.mapper;
import org.apache.ibatis.annotations.Mapper;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.schoolmate.model.SmOtherInfo;


 /**   
 *  
 * @Description:  校友卡其他信息——DAO
 * @Author:       Clown
 * @project 	  simp 
 * @CreateDate:   Mon Mar 04 17:51:29 CST 2019
 * @Version:      v_2.0
 *    
 */
@Mapper
public interface SmOtherInfoMapper extends CrudMapper<SmOtherInfo>{

	void deleteByUserId(SmOtherInfo info);
}