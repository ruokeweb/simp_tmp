package com.mpri.aio.app.act.mapper;

import com.github.pagehelper.Page;
import com.mpri.aio.act.model.ActSelforg;
import com.mpri.aio.act.model.ActSelforgContent;
import com.mpri.aio.app.act.vo.ActSelfVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


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
public interface AppActSelforgContentMapper {


    void insert(ActSelforgContent entity);

    int getNumByUserId(@Param("entity") ActSelforgContent entity);


    Integer getMyActSelfNum(@Param("entity")ActSelforgContent actSelforg);

    Page<ActSelfVo> getActSelfList(@Param("entity") ActSelforgContent actSelforg);
}