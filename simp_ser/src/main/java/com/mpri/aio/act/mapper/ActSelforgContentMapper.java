package com.mpri.aio.act.mapper;
import com.github.pagehelper.Page;
import com.mpri.aio.act.model.ActSignVo;
import org.apache.ibatis.annotations.Mapper;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.act.model.ActSelforgContent;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 *  
 * @Description:  值年返校内容——DAO
 * @Author:       cary
 * @project 	  simp 
 * @CreateDate:   Mon May 27 15:50:50 GMT+08:00 2019
 * @Version:      v_1.2
 *    
 */
@Mapper
public interface ActSelforgContentMapper extends CrudMapper<ActSelforgContent>{


     Page<ActSelforgContent> loadByPageAndName(@Param("entity") ActSelforgContent actSelforgContent);

     List<ActSignVo> loadActSign(@Param("num") int num);
}