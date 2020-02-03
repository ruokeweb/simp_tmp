package com.mpri.aio.donation.service;

import org.springframework.stereotype.Service;
import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.donation.model.DonTemplate;
import com.mpri.aio.donation.mapper.DonTemplateMapper;

 /**   
 *  
 * @Description:  捐赠证书模板表——Service
 * @Author:       lzq
 * @project 	  smmp 
 * @CreateDate:   Wed Feb 20 13:57:44 CST 2019
 * @Version:      v_1.2
 *    
 */
@Service
public class DonTemplateService extends CrudService<DonTemplateMapper, DonTemplate>  {


}