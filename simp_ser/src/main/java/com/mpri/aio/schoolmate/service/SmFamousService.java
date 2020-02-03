package com.mpri.aio.schoolmate.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mpri.aio.common.page.PageIo;
import org.springframework.stereotype.Service;
import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.schoolmate.model.SmFamous;
import com.mpri.aio.schoolmate.mapper.SmFamousMapper;

 /**   
 *  
 * @Description:  知名校友管理——Service
 * @Author:       zdl
 * @project 	  smmp 
 * @CreateDate:   Fri Mar 01 13:28:24 CST 2019
 * @Version:      v_1.2
 *    
 */
@Service
public class SmFamousService extends CrudService<SmFamousMapper, SmFamous>  {


     public PageIo<SmFamous> getRandList(int pageNo, int pageSize, SmFamous smFamous) {
             PageHelper.startPage(pageNo, pageSize);
             Page<SmFamous> pageList = this.mapper.getRandList(smFamous);
             PageIo<SmFamous> pageInfo = new PageIo(pageList);
             return pageInfo;
     }
 }