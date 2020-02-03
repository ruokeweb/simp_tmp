	package com.mpri.aio.enterprise.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.enterprise.mapper.EntIntentionMapper;
import com.mpri.aio.enterprise.model.EntIntention;

/**
 *  
 * @Description:  校友企业校友意向表——Service
 * @Author:       lzq
 * @project 	  simp 
 * @CreateDate:   Thu Feb 14 16:35:23 CST 2019
 * @Version:      v_1.2
 *    
 */
@Service
public class EntIntentionService extends CrudService<EntIntentionMapper, EntIntention>  {

  public PageIo<EntIntention> loadEntAndSchoolByPage(int pageNo, int pageSize, EntIntention entIntention) {
      PageHelper.startPage(pageNo, pageSize);
      List<EntIntention> list = this.mapper.loadEntAndSchoolByPage(entIntention);
      PageIo<EntIntention> pageInfo = new PageIo<EntIntention>(list);
      return pageInfo;
  }
 }