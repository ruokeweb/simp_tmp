package com.mpri.aio.webber.controller;

import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.schoolmate.model.SmFamous;
import com.mpri.aio.schoolmate.service.SmFamousService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
*
* @Description:  校友网知名校友
* @Author:       zdl
* @project 	  smmp
* @CreateDate:   Fri Mar 01 13:28:24 CST 2019
* @Version:      v_1.2
*
*/

@CrossOrigin
@RestController
@RequestMapping("/webber/open/famous")
public class
FamousController extends BaseController {

   @Autowired
   private SmFamousService smFamousService;

   /**
    * 获取知名校友管理列表
   * <p>Title: list</p>
   * <p>Description: </p>
   * @param pageNo
   * @param pageSize
   * @param smFamous
   * @return
    */
   @PostMapping(value = "/list")
   public RestResponse<PageIo<SmFamous>> list(int pageNo, int pageSize, SmFamous smFamous) {

       PageIo<SmFamous> pageInfo =  smFamousService.loadByPage(pageNo,pageSize,smFamous);
       if (0==pageSize)
           pageSize =10;
       return new RestResponse< PageIo<SmFamous>>(ExceptionResult.REQUEST_SUCCESS, "知名校友列表获取成功", pageInfo);
   }
   /**
    * 根据权重随机获取
   * <p>Title: list</p>
   * <p>Description: </p>
   * @param pageSize
   * @param smFamous
   * @return
    */
   @PostMapping(value = "/getRandList")
   public RestResponse<PageIo<SmFamous>> getRandList( int pageSize, SmFamous smFamous) {

       PageIo<SmFamous> pageInfo =  smFamousService.getRandList(0,pageSize,smFamous);
       if (0==pageSize)
           pageSize =10;
       return new RestResponse< PageIo<SmFamous>>(ExceptionResult.REQUEST_SUCCESS, "知名校友列表获取成功", pageInfo);
   }


    /**
    * 获取知名校友管理
   * <p>Title: get</p>
   * <p>Description: </p>
   * @param smFamous
   * @return
    */
   @CrossOrigin
   @PostMapping(value = "/get")
   public RestResponse<SmFamous> get(SmFamous smFamous) {
       return new RestResponse<SmFamous>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
               smFamousService.get(smFamous));
   }

}