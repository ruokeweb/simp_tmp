package com.mpri.aio.schoolmate.mapper;
import org.apache.ibatis.annotations.Mapper;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.schoolmate.model.SmHistorydata;

import java.util.List;


/**   
 *  
 * @Description:  校友历史数据表——DAO
 * @Author:       syp
 * @project 	  exchange_datasource 
 * @CreateDate:   Mon Jan 28 15:54:51 CST 2019
 * @Version:      v_1.0
 *    
 */
@Mapper
public interface SmHistorydataMapper extends CrudMapper<SmHistorydata>{


     List<SmHistorydata> getUserId();
     
     void deleteByUserId(SmHistorydata historydata);
 }