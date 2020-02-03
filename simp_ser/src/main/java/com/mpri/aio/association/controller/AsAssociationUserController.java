package com.mpri.aio.association.controller;

import com.github.pagehelper.PageInfo;
import com.mpri.aio.association.model.AsAssociation;
import com.mpri.aio.association.model.AsAssociationUser;
import com.mpri.aio.association.service.AsAssociationService;
import com.mpri.aio.association.service.AsAssociationUserService;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.logs.Logs;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.common.response.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *  
 * @Description:  校友会对应关系表——Controller
 * @Author:       lzq
 * @project 	  smmp 
 * @CreateDate:   Thu Feb 21 13:24:13 CST 2019
 * @Version:      v_1.2
 */

@RestController
@RequestMapping("/as/asAssociationUser")
public class AsAssociationUserController extends BaseController {
	
	@Autowired
	private AsAssociationUserService asAssociationUserService;
    @Autowired
	private AsAssociationService  asAssociationService;
		
	/**
	 * 获取校友会对应关系表列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param asAssociationUser
	* @return
	 */

	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<AsAssociationUser> list(int pageNo,int pageSize,AsAssociationUser asAssociationUser) {
//		PageIo<AsAssociationUser> pageInfo =  asAssociationUserService.loadByPage(pageNo,pageSize,asAssociationUser);
			if (null == asAssociationUser ){
				asAssociationUser = new AsAssociationUser();
			}
			if("root".equals(asAssociationUser.getAssociationId()) || "ROOT".equals(asAssociationUser.getAssociationId()))
			{
				asAssociationUser.setAssociationId("");
			}

		PageIo<AsAssociationUser> pageInfo =  asAssociationUserService.loadSchoolByPage(pageNo,pageSize,asAssociationUser);
		return pageInfo;
	}
	
	
	/**
	 * 增加或者更新校友会对应关系表
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param asAssociationUser
	* @return
	 */

	@Logs(value = "校友会对应关系修改",type ="UPDATE")
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(AsAssociationUser asAssociationUser){
        AsAssociationUser asAssuser= new AsAssociationUser();
        asAssuser.setAssociationId(asAssociationUser.getAssociationId());
        asAssuser.setSchoomateId(asAssociationUser.getSchoomateId());
        List<AsAssociationUser> list = asAssociationUserService.loadAllListBy(asAssuser);
        if((null==asAssociationUser.getId()  || "".equals(asAssociationUser.getId()))&& null!=list && list.size()>0)
        {
            return new RestResponse<String>(ExceptionResult.DATA_USED, "已经存在对应关系！", "");
        }
		asAssociationUserService.save(asAssociationUser);
        //添加校友和校友会关系后，校友会成员加一
		AsAssociation asAssociation = new AsAssociation();
		asAssociation.setId(asAssociationUser.getAssociationId());
		asAssociation = asAssociationService.get(asAssociation);
		int sum = asAssociation.getSum();
		asAssociation.setSum(sum+1);
		asAssociationService.save(asAssociation);

		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");
	}	
	
	/**
	 * 删除校友会对应关系表（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param asAssociationUser
	* @return
	 */
	@Logs(value = "校友会对应关系删除",type ="DELETE")
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(AsAssociationUser asAssociationUser) {
		asAssociationUserService.delete(asAssociationUser);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 *  获取校友会对应关系表
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param asAssociationUser
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<AsAssociationUser> get(AsAssociationUser asAssociationUser) {
		return new RestResponse<AsAssociationUser>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				asAssociationUserService.get(asAssociationUser));	
	}
		
}