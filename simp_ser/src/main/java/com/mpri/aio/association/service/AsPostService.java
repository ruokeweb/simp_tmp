package com.mpri.aio.association.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.mpri.aio.association.mapper.AsPostMapper;
import com.mpri.aio.association.model.AsPost;
import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.common.page.PageIo;

/**
 *  
 * @Description:  校友会任职信息——Service
 * @Author:       lzq
 * @project 	  smmp 
 * @CreateDate:   Thu Feb 21 13:26:24 CST 2019
 * @Version:      v_1.2
 *    
 */
@Service
public class AsPostService extends CrudService<AsPostMapper, AsPost>  {

  public PageIo<AsPost> loadSchoolByPage(int pageNo, int pageSize,AsPost asPost){
      PageHelper.startPage(pageNo, pageSize);
      List<AsPost> pageList =  this.mapper.loadSchoolByPage(asPost);
      PageIo<AsPost> pageInfo = new PageIo<AsPost>(pageList);
      return pageInfo;
  }
  
}