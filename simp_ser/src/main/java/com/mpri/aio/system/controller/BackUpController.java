package com.mpri.aio.system.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.untils.back.BackUp;
import com.mpri.aio.untils.back.JsonOper;
import com.mpri.aio.untils.back.MongoOper;
import com.mpri.aio.untils.back.MysqlOper;

@RestController
@RequestMapping("sys/backup")
public class BackUpController {

	@Autowired
	MysqlOper mysqlOper;
	
	@Autowired
	MongoOper mongoOper;
	
	@Autowired
	JsonOper jo;
	
	
	@CrossOrigin
	@PostMapping(value = "/list")
	public RestResponse<List<BackUp>> list() {
		RestResponse<List<BackUp>> info=null;
		try {
			List<BackUp> list=jo.loadBack();
			return new RestResponse<List<BackUp>>(ExceptionResult.REQUEST_SUCCESS,"", list);
		} catch (IOException e) {
			 e.printStackTrace();
		}
		return info;
	
	}
	
	
	@CrossOrigin
	@PostMapping(value = "/back")
	public RestResponse<String> back(String type){
		int back=0;
		try {
			back=jo.saveBack(type);
		} catch (Exception e) {
			 e.printStackTrace();
			back++;

		}
		if(back>0) {
			return new RestResponse<String>(ExceptionResult.SYS_ERROR, "数据备份失败！", "");
		}else {
			return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "数据备份成功！", "");
		}
		
	}	
	
	@CrossOrigin
	@PostMapping(value = "/recoverMysql")
	public RestResponse<String> recoverMysql(String id) {
		
		try {
			mysqlOper.dbRecover(id);
			return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "数据库恢复成功！", "");
		} catch (Exception e) {
			//// ex.printStackTrace();;
			return new RestResponse<String>(ExceptionResult.SYS_ERROR, "数据库恢复失败!", "");
		}
	}
	
	@CrossOrigin
	@PostMapping(value = "/recoverMongo")
	public RestResponse<String> recoverMongo(String id) {
		try {
			mongoOper.dbRecover(id);
			return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "文件备份恢复成功！", "");
		} catch (Exception e) {
			//// ex.printStackTrace();;
			return new RestResponse<String>(ExceptionResult.SYS_ERROR, "文件备份恢复失败!", "");
		}

	}
	
	
	
	/**
	 * 删除备份组
	 * @param ids
	 * @return
	 */
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(String id) {
		try {
			jo.delBack(id);
		} catch (IOException e) {
			//// ex.printStackTrace();;
		}

		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	

	
}
