package com.mpri.aio.act.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mpri.aio.act.model.ActSignVo;
import com.mpri.aio.common.page.PageIo;
import org.springframework.stereotype.Service;
import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.act.model.ActSelforgContent;
import com.mpri.aio.act.mapper.ActSelforgContentMapper;

import java.util.List;

/**
 *  
 * @Description:  值年返校内容——Service
 * @Author:       cary
 * @project 	  simp 
 * @CreateDate:   Mon May 27 15:50:50 GMT+08:00 2019
 * @Version:      v_1.2
 *    
 */
@Service
public class ActSelforgContentService extends CrudService<ActSelforgContentMapper, ActSelforgContent>  {

    public PageIo<ActSelforgContent> loadByPageAndName(int pageNo, int pageSize, ActSelforgContent actSelforgContent) {
        PageHelper.startPage(pageNo, pageSize);
        Page<ActSelforgContent> pageList=mapper.loadByPageAndName(actSelforgContent);
        PageIo<ActSelforgContent> pageInfo = new PageIo<ActSelforgContent>(pageList);
        return pageInfo;
    }
    public List<ActSignVo> loadActSign(int num){
        return  mapper.loadActSign(num);
    }
 }