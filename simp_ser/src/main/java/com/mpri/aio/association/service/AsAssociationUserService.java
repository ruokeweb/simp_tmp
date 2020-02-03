package com.mpri.aio.association.service;

import com.github.pagehelper.PageHelper;
import com.mpri.aio.association.mapper.AsAssociationUserMapper;
import com.mpri.aio.association.model.AsAssociationUser;
import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.common.page.PageIo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  
 * @Description:  校友会对应关系表——Service
 * @Author:       lzq
 * @project 	  smmp 
 * @CreateDate:   Thu Feb 21 13:24:13 CST 2019
 * @Version:      v_1.2
 *    
 */
@Service
public class AsAssociationUserService extends CrudService<AsAssociationUserMapper, AsAssociationUser>  {

 public PageIo<AsAssociationUser> loadSchoolByPage(int pageNo,int pageSize,AsAssociationUser asAssociationUser){
   PageHelper.startPage(pageNo, pageSize);
   List<AsAssociationUser> pageList =  this.mapper.loadSchoolByPage(asAssociationUser);
   PageIo<AsAssociationUser> pageInfo = new PageIo<AsAssociationUser>(pageList);
  return pageInfo;
  }

}