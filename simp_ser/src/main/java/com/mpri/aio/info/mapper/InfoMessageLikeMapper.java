package com.mpri.aio.info.mapper;
import org.apache.ibatis.annotations.Mapper;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.info.model.InfoMessageLike;


 /**   
 *  
 * @Description:  信息点赞表表——DAO
 * @Author:       syp
 * @project 	  smmp 
 * @CreateDate:   Fri Dec 14 13:55:56 CST 2018
 * @Version:      v_1.2
 *    
 */
@Mapper
public interface InfoMessageLikeMapper extends CrudMapper<InfoMessageLike>{

	/**
	 * 获取此条评论是否被此人点赞
	 */
	int msgLikes (InfoMessageLike infoMessageLike);
	
	/**
	 * 删除此条点赞
	 */
	void disLikeMes (InfoMessageLike infoMessageLike);
}