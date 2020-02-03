package com.mpri.aio.info.mapper;
import org.apache.ibatis.annotations.Mapper;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.info.model.InfoMessage;
import com.mpri.aio.info.model.InfoMessageLike;


 /**   
 *  
 * @Description:  信息留言表——DAO
 * @Author:       syp
 * @project 	  smmp 
 * @CreateDate:   Fri Dec 14 09:44:55 CST 2018
 * @Version:      v_1.2
 *    
 */
@Mapper
public interface InfoMessageMapper extends CrudMapper<InfoMessage>{

	/**
	 * 更新点赞数
	 */
	void updateLikeNum (InfoMessageLike infoMessageLike);
}