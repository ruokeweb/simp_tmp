package com.mpri.aio.message.controller;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.logs.Logs;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.common.utils.FileUtils;
import com.mpri.aio.donation.model.DonRecord;
import com.mpri.aio.message.model.MesGroup;
import com.mpri.aio.message.model.MesMessage;
import com.mpri.aio.message.service.MesMessageService;
import com.mpri.aio.message.vo.MesGroupSchoolmates;
import com.mpri.aio.schoolmate.model.SmSchoolmate;
import com.mpri.aio.schoolmate.service.SmSchoolmateService;
import com.mpri.aio.schoolmate.utils.ExcelUtil;
import com.mpri.aio.system.common.GlobalStr;
import com.mpri.aio.system.common.RedisCacheService;
import com.mpri.aio.system.common.UserInfoUtil;
import com.mpri.aio.system.init.InitCache;
import com.mpri.aio.system.model.SysUser;
import com.mpri.aio.system.service.SysUserService;

/**
 * 
 * @Description: 通知消息表——Controller
 * @Author: syp
 * @project smmp
 * @CreateDate: Mon Nov 12 17:31:22 CST 2018
 * @Version: v_1.02
 * 
 */
@RestController
@RequestMapping("/mes/mesMessage")
public class MesMessageController extends BaseController {

	@Autowired
	private MesMessageService mesMessageService;

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private RedisCacheService redisCacheService;

	@Autowired
	private SmSchoolmateService schoolmateService;
	
	@Value("${file.uploadFolder}")
	private String uploadFolder;

	@Value("${file.staticAccessPath}")
	private String staticAccessPath;

	/**
	 * 获取通知消息表列表
	 * <p>
	 * Title: list
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param mesMessage
	 * @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<MesMessage> list(int pageNo, int pageSize, MesMessage mesMessage) {
		PageIo<MesMessage> pageInfo = mesMessageService.loadByPage(pageNo, pageSize, mesMessage);
		return pageInfo;
	}

	/**
	 * 增加或者更新通知消息表
	 * <p>
	 * Title: add
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param mesMessage
	 * @return
	 */
	@Logs(value = "通知消息编辑", type = "UPDATE")
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<MesMessage> save(MesMessage mesMessage, HttpServletRequest request) {
		String sysUserId = UserInfoUtil.getUserId(request);
		mesMessage.setSendUserId(sysUserId);
		if (StringUtil.isNotEmpty(mesMessage.getReceiveUserName())) {
			SysUser sysUser = new SysUser();
			sysUser.setUsername(mesMessage.getReceiveUserName());
			SysUser receiveUser = sysUserService.getPwdByUsername(sysUser);
			if (null != receiveUser) {
				mesMessage.setReceiveUserId(receiveUser.getId());
			}
		}
		mesMessage.setStatus(mesMessageService.judgeDate(mesMessage));
		mesMessage = mesMessageService.save(mesMessage);
		// 短信或者手机发送
//		mesMessageService.sendMes(mesMessage);
		List<MesGroupSchoolmates> schoolmates = mesMessageService.getReceives(mesMessage);
		mesMessage.setPreSendCount(schoolmates.size());
		return new RestResponse<MesMessage>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", mesMessage);
	}

	
	/**
	 * 	校友卡发送消息获取接受人数
	 * @return
	 */
	@CrossOrigin
	@PostMapping(value = "/sendSmCardMesReceive")
	public RestResponse<MesMessage> sendSmCardMesReceive(MesMessage mesMessage){
		List<MesGroupSchoolmates> schoolmates = mesMessageService.getReceives(mesMessage);
		mesMessage.setPreSendCount(schoolmates.size());
		return new RestResponse<MesMessage>(ExceptionResult.REQUEST_SUCCESS, "获取人数成功", mesMessage); 
	}
	
	
	@CrossOrigin
	@PostMapping(value = "/exportsendusers")
	public RestResponse<String> exportSendUsers(HttpServletResponse response,MesMessage mesMessage) {
		if(StringUtil.isNotEmpty(mesMessage.getId())) {
			mesMessage = mesMessageService.get(mesMessage);
		}
		List<MesGroupSchoolmates> schoolmates = mesMessageService.getReceives(mesMessage);
		try {
			Workbook wb = ExcelUtil.exportExcelFile(schoolmates, MesGroupSchoolmates.class, "通知消息", "sheet1");
			String fileName = String.valueOf(System.currentTimeMillis());
			// 创建目录
			Boolean flag = FileUtils.createDirectory(uploadFolder);
			System.out.println(flag ? "创建目录成功" : "该目录已存在");
			FileOutputStream output = new FileOutputStream(uploadFolder + fileName + ".xls");
			wb.write(output);
			return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "导出成功！",
					staticAccessPath.replaceAll("\\*", "") + fileName + ".xls");
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println(e.getMessage());
			return new RestResponse<String>(ExceptionResult.SYS_ERROR, "导出失败！", "");
		}
	}

	@Logs(value = "通知消息发送", type = "OTHER")
	@CrossOrigin
	@PostMapping(value = "/sendMes")
	public RestResponse<String> sendMes(MesMessage mesMessage) {
		mesMessage = mesMessageService.get(mesMessage);
		// 短信或者手机发送
		mesMessageService.sendMes(mesMessage);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "后台发送中！", "");
	}

	/**
	 * 删除通知消息表（逻辑删除）
	 * <p>
	 * Title: delete
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param mesMessage
	 * @return
	 */
	@Logs(value = "通知消息删除", type = "DELETE")
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(MesMessage mesMessage) {
		mesMessageService.delete(mesMessage);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}

	/**
	 * 获取通知消息表
	 * <p>
	 * Title: get
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param mesMessage
	 * @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<MesMessage> get(MesMessage mesMessage) {
		MesMessage remesMessage = mesMessageService.get(mesMessage);
		if (StringUtil.isNotEmpty(remesMessage.getReceiveUserId())) {
			SmSchoolmate schoolmate = new SmSchoolmate();
			schoolmate.setUserId(remesMessage.getReceiveUserId());
			List<SmSchoolmate> list = schoolmateService.loadAllListBy(schoolmate);
			if (list.size() != 0) {
				remesMessage.setReceiveUserName(list.get(0).getName());
			}
		}

		return new RestResponse<MesMessage>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", remesMessage);
	}

	/**
	 * 获取信息组缓存
	 * 
	 * @return
	 */
	public List<MesGroup> getMesGroupsCache() {
		redisCacheService.getBaseCache(new MesGroup(), InitCache.MESG_BASE_CACHE);
		Map<String, MesGroup> map = new HashMap<String, MesGroup>();

		List<MesGroup> list = new ArrayList<MesGroup>();
		for (Map.Entry<String, MesGroup> entry : map.entrySet()) {
			list.add(entry.getValue());
		}
		return list;
	}

}