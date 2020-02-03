package com.mpri.aio.message.service;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.message.model.MesGroupUser;
import com.mpri.aio.schoolmate.model.SmSchoolmate;
import com.mpri.aio.message.mapper.MesGroupUserMapper;

 /**   
 *  
 * @Description:  信息组用户关系表——Service
 * @Author:       syp
 * @project 	  smmp 
 * @CreateDate:   Mon Nov 12 17:28:51 CST 2018
 * @Version:      v_1.02
 *    
 */
@Service
public class MesGroupUserService extends CrudService<MesGroupUserMapper, MesGroupUser>  {

	
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param entity
	 * @return
	 */
	public PageIo<SmSchoolmate> loadSmByPage(int pageNo, int pageSize,SmSchoolmate schoolmate) {
        PageHelper.startPage(pageNo, pageSize);    
        Page<SmSchoolmate> pageList=mapper.loadSmByPage(schoolmate);
        PageIo<SmSchoolmate> pageInfo = new PageIo<>(pageList);
		return pageInfo;
	}
}