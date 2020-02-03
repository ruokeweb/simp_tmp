package com.mpri.aio.settings.service;

import com.mpri.aio.common.utils.IdGen;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.settings.model.PointLoginSign;
import com.mpri.aio.settings.mapper.PointLoginSignMapper;

import java.util.Date;

/**
 *  
 * @Description:  校友登陆签到表——Service
 * @Author:       zdl
 * @project 	  smmp 
 * @CreateDate:   Tue Feb 26 10:45:42 CST 2019
 * @Version:      v_1.2
 *    
 */
@Service
public class PointLoginSignService extends CrudService<PointLoginSignMapper, PointLoginSign>  {

    /**
    * 判断今天是否是第一次登录或签到
    * <p>Title: isFirstLogin</p>
    * <p>Description: </p>
    * @return
    * */
    public PointLoginSign isFirstLoginOrSign(PointLoginSign loginSign) {
        return mapper.isFirstLogin(loginSign);
    }

     /**
      * 保存每一天第一次登录记录
      */
    public void saveLoginSign(String userId){
        PointLoginSign pointLoginSign = new PointLoginSign();
        pointLoginSign.setUserId(userId);
        PointLoginSign firstLoginOrSign = isFirstLoginOrSign(pointLoginSign);
        if(firstLoginOrSign==null){
            pointLoginSign.setId(IdGen.uuid());
            pointLoginSign.setCreateDate(new Date());
            mapper.insert(pointLoginSign);
        }
        System.out.println(firstLoginOrSign);
    }

}