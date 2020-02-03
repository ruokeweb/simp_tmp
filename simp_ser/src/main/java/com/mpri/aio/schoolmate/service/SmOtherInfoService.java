package com.mpri.aio.schoolmate.service;

import org.springframework.stereotype.Service;
import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.schoolmate.model.SmOtherInfo;
import com.mpri.aio.schoolmate.mapper.SmOtherInfoMapper;

 /**   
 *  
 * @Description:  校友卡其他信息——Service
 * @Author:       Clown
 * @project 	  simp 
 * @CreateDate:   Mon Mar 04 17:51:29 CST 2019
 * @Version:      v_2.0
 *    
 */
@Service
public class SmOtherInfoService extends CrudService<SmOtherInfoMapper, SmOtherInfo>  {

	public void deleteByUserId(SmOtherInfo info) {
		mapper.deleteByUserId(info);
	}
}