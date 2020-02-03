package com.mpri.aio.donation.mapper;
import org.apache.ibatis.annotations.Mapper;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.donation.model.DonTemplate;


 /**   
 *  
 * @Description:  捐赠证书模板表——DAO
 * @Author:       lzq
 * @project 	  smmp 
 * @CreateDate:   Wed Feb 20 13:57:44 CST 2019
 * @Version:      v_1.2
 *    
 */
@Mapper
public interface DonTemplateMapper extends CrudMapper<DonTemplate>{

	
}