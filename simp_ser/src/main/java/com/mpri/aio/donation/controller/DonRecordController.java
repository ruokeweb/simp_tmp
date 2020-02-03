package com.mpri.aio.donation.controller;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.logs.Logs;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.common.utils.DateUtils;
import com.mpri.aio.common.utils.FileUtils;
import com.mpri.aio.common.utils.IdGen;
import com.mpri.aio.donation.model.DonProject;
import com.mpri.aio.donation.model.DonRecord;
import com.mpri.aio.donation.model.DonRecordVo;
import com.mpri.aio.donation.service.DonRecordService;
import com.mpri.aio.donation.utils.RecordExportHandler;
import com.mpri.aio.donation.vo.DonStatement;
import com.mpri.aio.schoolmate.utils.ExcelUtil;
import com.mpri.aio.system.common.GlobalStr;
import com.mpri.aio.untils.export.WordExportUtils;

/**
 *  
 * @Description:  捐赠记录管理——Controller
 * @Author:       LZQ
 * @project 	  AIO 
 * @CreateDate:   Wed Aug 29 15:09:37 CST 2018
 * @Version:      v_1.0
 *    
 */
@RestController
@RequestMapping("/don/donRecord")
public class DonRecordController extends BaseController {
	
	@Autowired
	private DonRecordService donRecordService;

	@Autowired
	private RecordExportHandler recordExportHandler;

	@Value("${file.uploadFolder}")
	private String uploadFolder;

	@Value("${file.staticAccessPath}")
	private String staticAccessPath;
	/**
	 * 获取捐赠记录管理列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param donRecord
	* @return PageInfo<DonRecord>
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<DonRecord> list(int pageNo,int pageSize,DonRecord donRecord) {
		donRecord.setState(GlobalStr.NORMAL_DON);
		PageIo<DonRecord> pageInfo =  donRecordService.loadByPage(pageNo,pageSize,donRecord);
		return pageInfo;
	}
	
	
	/**
	 * 获取捐赠记录管理列表(只查询单表)
	 * <p>Title: list</p>
	 * <p>Description: </p>
	 * @param pageNo
	 * @param pageSize
	 * @param donRecord
	 * @return PageInfo<DonRecord>
	 */
	@CrossOrigin
	@PostMapping(value = "/loadByPage")
	public PageInfo<DonRecord> loadByPage(int pageNo,int pageSize,DonRecord donRecord) {
		donRecord.setState(GlobalStr.NORMAL_DON);
		PageIo<DonRecord> pageInfo =  donRecordService.loadByPage(pageNo,pageSize,donRecord);
		return pageInfo;
	}
	
	
	

	/**
	 * 获取捐赠记录中没有捐赠项目的
	 * <p>Title: list</p>
	 * <p>Description: </p>
	 * @param pageNo
	 * @param pageSize
	 * @param donRecord
	 * @return PageInfo<DonRecord>
	 */
	@CrossOrigin
	@PostMapping(value = "/loadRecordBy")
	public PageInfo<DonRecord> loadRecordBy(int pageNo,int pageSize,DonRecord donRecord) {
		donRecord.setState(GlobalStr.NORMAL_DON);
		PageIo<DonRecord> pageInfo =  donRecordService.loadRecordBy(pageNo,pageSize,donRecord);
		return pageInfo;
	}
	

	
	@CrossOrigin
	@PostMapping(value = "/loadRecordCountBy")
	public RestResponse<Integer> loadRecordCountBy(DonRecord donRecord) {
		donRecord.setState(GlobalStr.NORMAL_DON);
		Integer res =  donRecordService.loadRecordCountBy(donRecord);
		if(res > 0) {
			return new RestResponse<Integer>(ExceptionResult.NO_PERMISSION, "存在没有归档的记录，请联系项目人员进行归档", res);	
		}
		return new RestResponse<Integer>(ExceptionResult.REQUEST_SUCCESS, "可以进行对账操作！", res);
	}
	
	
	/**
	 * 获取捐赠记录管理列表(只查询单表)
	 * <p>Title: list</p>
	 * <p>Description: </p>
	 * @param pageNo
	 * @param pageSize
	 * @param donRecord
	 * @return PageInfo<DonRecord>
	 */
	@CrossOrigin
	@PostMapping(value = "/getPageInfo")
	public PageInfo<DonRecord> getPageInfo(int pageNo,int pageSize,DonRecord donRecord) {
		donRecord.setState(GlobalStr.NORMAL_DON);
		//PageIo<DonRecord> pageInfo =  donRecordService.loadByPage(pageNo,pageSize,donRecord);
		PageIo<DonRecord> pageInfo =  donRecordService.getPageInfo(pageNo,pageSize,donRecord);
		return pageInfo;
	}
	
	/**
	 * 增加或者更新捐赠记录管理
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param donRecord
	* @return RestResponse<DonRecord>
	 */
	@Logs(value = "捐赠记录编辑",type ="UPDATE")
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<DonRecord> save(DonRecord donRecord){
		if(StringUtil.isEmpty(donRecord.getId())) {
			donRecordService.fileRecord(donRecord);
		}else {
			donRecordService.updateRecord(donRecord);	
		}
		return new RestResponse<DonRecord>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", donRecord);
	}	
	
	/**
	 * 删除捐赠记录管理（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param donRecord
	* @return RestResponse<String>
	 */
	@Logs(value = "捐赠记录删除",type ="DELETE")
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(DonRecord donRecord) {
		donRecordService.deleteRecord(donRecord);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取捐赠记录管理
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param donRecord
	* @return RestResponse<DonRecord>
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<DonRecord> get(DonRecord donRecord) {
		return new RestResponse<DonRecord>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", donRecordService.get(donRecord));
	}
	
	
	/**
	 * 获取捐赠金额
	* <p>Title: getMoneySum</p>  
	* <p>Description: </p>  
	* @param donRecord
	* @return RestResponse<String>
	 */
	@CrossOrigin
	@PostMapping(value = "/getUserDontion")
    public RestResponse<String> getMoneySum(DonRecord donRecord) {
	    String moneyNum = donRecordService.getMoneySum(donRecord);
        return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", moneyNum);
    }
    
	/**
	 * 根据项目id 获取捐赠成功的记录数
	* <p>Title: getCountByProId</p>  
	* <p>Description: </p>  
	* @param donRecord
	* @return RestResponse<Integer>
	 */
	@CrossOrigin
	@PostMapping(value = "/getCountByProId")
	public RestResponse<Integer> getCountByProId(DonRecord donRecord){
		Integer count = donRecordService.getCountByProId(donRecord);
		return new RestResponse<Integer>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", count);
	}
	@CrossOrigin
	@PostMapping(value = "/exportRecordDatas")
	public RestResponse<String> exportsmDatas(HttpServletResponse response){
		DonRecord donRecord = new DonRecord();
		donRecord.setState(GlobalStr.NORMAL_DON);
		List<DonRecord> donRecordList = donRecordService.loadAllListBy(donRecord);
		 List<DonRecord> exportlist = recordExportHandler.handlerExport(donRecordList);
		 try {
			 Workbook wb = ExcelUtil.exportExcelFile(exportlist,DonRecord.class,"捐赠信息","sheet1");
			 String fileName = String.valueOf(System.currentTimeMillis());
			 //创建目录
			 Boolean flag = FileUtils.createDirectory(uploadFolder);
			 System.out.println(flag?"创建目录成功":"该目录已存在");
			 FileOutputStream output=new FileOutputStream(uploadFolder+ fileName+".xls");
			 wb.write(output);
			 return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "导出成功！", staticAccessPath.replaceAll("\\*", "") +fileName+".xls");
		 } catch (Exception e) {
			 //e.printStackTrace();
			 System.out.println(e.getMessage());
			 return new RestResponse<String>(ExceptionResult.SYS_ERROR, "导出失败！", "");
		 }
	}

	/**
	 * 按日统计每日捐赠
	 * @param response
	 * @param donProject (只是用下项目的开始时间和结束时间两个字段)
	 * @return
	 */
	@CrossOrigin
	@PostMapping(value = "/exportRecordByDay")
	public RestResponse<String> exportRecordByDay(DonProject donProject, HttpServletResponse response){
		 List<DonRecordVo> donRecordList = donRecordService.loadAllListByDay(donProject);
		 BigDecimal money =donRecordService.loadAllmoneyByDay(donProject);
		 if(money==null){
			 money=new BigDecimal(0);
		 }
		Date startdate = donProject.getStartdate();
		SimpleDateFormat f= new SimpleDateFormat("yyyy-MM-dd");
		String format = f.format(startdate);
		// List<DonRecordVo> exportlist = recordVoExportHandler.handlerExport(donRecordList);
		 try {
			 Workbook wb = ExcelUtil.exportExcelFile(donRecordList,DonRecordVo.class,format+" 共计捐赠"+money+"元","sheet1");
			 String fileName = String.valueOf(System.currentTimeMillis());
			 //创建目录
			 Boolean flag = FileUtils.createDirectory(uploadFolder);
			 System.out.println(flag?"创建目录成功":"该目录已存在");
			 FileOutputStream output=new FileOutputStream(uploadFolder+ fileName+".xls");
			 wb.write(output);
			 return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "导出成功！", staticAccessPath.replaceAll("\\*", "") +fileName+".xls");
		 } catch (Exception e) {
			 //e.printStackTrace();
			 System.out.println(e.getMessage());
			 return new RestResponse<String>(ExceptionResult.SYS_ERROR, "导出失败！", "");
		 }
	}
	
	/**
	 * 导出对账信息
	 */
	@CrossOrigin
	@PostMapping(value = "/exportStatement")
	public RestResponse<String> exportStatement(DonRecord donRecord, HttpServletResponse response){
		
		List<DonRecordVo> donRecordList = donRecordService.loadListByDate(donRecord);
		
		donRecordList = donRecordService.addOrder(donRecordList);
		/** 文件名称 */		
		String fileName = DateUtils.getDate("yyyy-MM-dd");
		/** 组装数据 */
		Map<String, Object> map = new HashMap<String, Object>();
//		//測試數據
//		List<DonProDetail> donProDetails = new ArrayList<DonProDetail>();
//		DonProDetail donProDetail = new DonProDetail();
//		donProDetail.setMoney(new BigDecimal(123.12));
//		donProDetail.setName("我是捐赠项目一");
//		donProDetail.setOrder(1);
//		donProDetail.setTimes(10);
//		donProDetails.add(donProDetail);

		//今日收钱总数
		BigDecimal countMoney = donRecordService.loadCountByDate(donRecord);
		
		List<DonStatement> donRecords = donRecordService.loadRecordByDate(donRecord);
		
		donRecords = donRecordService.handleDonRecords(donRecords,countMoney);
		
//		DonStatement donStatement = new DonStatement();
//		donStatement.setMoney(new BigDecimal(12.12));
//		donStatement.setCount(new BigDecimal(123.12));
//		donStatement.setName("小王");
//		donStatement.setProName("我是捐赠项目一");
//		donStatement.setProNo("BJDX-JT-001"); 
//		donStatement.setStatus("已出账");
//		donRecords.add(donStatement);
		
		map.put("donProDetails", donRecordList);
		map.put("donRecords", donRecords);
		/** 生成word */
		WordExportUtils.createWord(map, "statement.ftl", uploadFolder, fileName);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "导出成功！", staticAccessPath.replaceAll("\\*", "") +fileName+".doc");
	}
	
	/**
	 * 退款操作
	 */
	@CrossOrigin
	@PostMapping(value = "/refund")
	public RestResponse<String> refund(DonRecord donRecord){
		donRecord.setState(GlobalStr.REFUND_DON);
		donRecordService.save(donRecord);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "退款成功！", "");
	}
	
	
	/**
	 * 归档操作
	 */
	@CrossOrigin
	@PostMapping(value = "/fileRecord")
	public RestResponse<String> fileRecord(DonRecord donRecord){
		donRecordService.fileRecord(donRecord);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "归档成功！", "");
	}
	

}