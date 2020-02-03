package com.mpri.aio.act.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mpri.aio.act.mapper.ActContentMapper;
import com.mpri.aio.act.model.ActContent;
import com.mpri.aio.act.model.ActSignVo;
import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.common.page.PageIo;

/**   
 *  
 * @Description:  活动——Service
 * @Author:       cary
 * @project 	  simp 
 * @CreateDate:   Fri Mar 01 13:30:13 CST 2019
 * @Version:      v_1.2
 *    
 */
@Service
public class ActContentService extends CrudService<ActContentMapper, ActContent>  {

    @Transactional(readOnly = false)
    public void insertList(List<ActContent> list) {
        mapper.insertList(list);
    }
	public PageIo<ActContent> loadContent(int pageNo, int pageSize,ActContent actContent) {
        //先通过actid 查询是否有表单
            PageHelper.startPage(pageNo, pageSize);
            Page<ActContent> pageList=mapper.loadContentNoSetting(actContent);
            PageIo<ActContent> pageInfo = new PageIo<>(pageList);
            return pageInfo;      
	}

    public int getNumBy(ActContent actContent) {
        return mapper.getNumBy(actContent);
    }
    
    public List<ActSignVo> loadActSign(int num){
    	return  mapper.loadActSign(num);
    }
}