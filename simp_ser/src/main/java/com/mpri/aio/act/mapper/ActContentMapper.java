package com.mpri.aio.act.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.mpri.aio.act.model.ActContent;
import com.mpri.aio.act.model.ActSignVo;
import com.mpri.aio.base.mapper.CrudMapper;


/**   
 *  
 * @Description:  活动——DAO
 * @Author:       cary
 * @project 	  simp 
 * @CreateDate:   Fri Mar 01 13:30:13 CST 2019
 * @Version:      v_1.2
 *    
 */
@Mapper
public interface ActContentMapper extends CrudMapper<ActContent>{

    void insertList(List<ActContent> list);

	public Page<ActContent> loadContent(ActContent actContent);

    int getNumBy(@Param("entity")ActContent actContent);

    Page<ActContent> loadContentNoSetting(ActContent actContent);
    
    List<ActSignVo> loadActSign(@Param("num") int num);
}

