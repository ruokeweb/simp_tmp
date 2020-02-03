package com.mpri.aio.donation.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.donation.model.DonProject;
import com.mpri.aio.donation.mapper.DonProjectMapper;

 /**   
 *  
 * @Description:  捐赠项目管理——Service
 * @Author:       LZQ
 * @project 	  AIO 
 * @CreateDate:   Wed Aug 29 15:22:22 CST 2018
 * @Version:      v_1.0
 *    
 */
@Service
public class DonProjectService extends CrudService<DonProjectMapper, DonProject>  {

	/**
	 * 获取未结束的项目
	* <p>Title: loadAllListByStatus</p>  
	* <p>Description: </p>  
	* @param donProject
	* @return
	 */
	public List<DonProject> loadAllListByStatus(DonProject donProject){
		return mapper.loadAllListByStatus(donProject);
	}
}