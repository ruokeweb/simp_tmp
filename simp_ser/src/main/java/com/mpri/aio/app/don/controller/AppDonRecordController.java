package com.mpri.aio.app.don.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.mpri.aio.app.don.service.AppDonProjectTogetherService;
import com.mpri.aio.app.don.service.AppDonRecordService;
import com.mpri.aio.app.don.service.AppDonService;
import com.mpri.aio.app.mine.service.AppSchoolmateService;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.common.utils.IdGen;
import com.mpri.aio.donation.model.DonProject;
import com.mpri.aio.donation.model.DonProjectTogether;
import com.mpri.aio.donation.model.DonRecord;
import com.mpri.aio.schoolmate.model.SmSchoolmate;
import com.mpri.aio.system.common.GlobalStr;
import com.mpri.aio.system.shiro.JWTUtil;

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
@RequestMapping("/app/don")
public class AppDonRecordController {

   @Autowired
   private AppDonRecordService  appDonRecordService;
    @Autowired
    private AppDonService donService;
    @Autowired
    private AppDonProjectTogetherService appDonProjectTogetherService;
    @Autowired
    private AppSchoolmateService  appSchoolmateService;

   /**
    * 获取某个项目的所有捐赠团体/捐赠人列表
   * <p>Title: list</p>
   * <p>Description: </p>
   * @param pageNo
   * @param pageSize
   * @param donRecord
   * @return PageInfo<DonRecord>
    */
   @CrossOrigin
   @PostMapping(value = "/getDonRecordByPro")
   public PageInfo<DonRecord> getDonRecordByPro(int pageNo,int pageSize,DonRecord donRecord) {
       donRecord.setState(GlobalStr.NORMAL_DON);
       PageIo<DonRecord> pageInfo =  appDonRecordService.loadByPage(pageNo,pageSize,donRecord);
       return pageInfo;
   }

    /**
     * 获取最新的捐赠记录
     * <p>Title: list</p>
     * <p>Description: </p>
     * @param pageNo
     * @param pageSize
     * @param donRecord
     * @return PageInfo<DonRecord>
     */
    @CrossOrigin
    @PostMapping(value = "/getLasterDonRecord")
    public PageInfo<DonRecord> getLasterDonRecord(int pageNo,int pageSize,DonRecord donRecord) {
        donRecord.setState(GlobalStr.NORMAL_DON);
        PageIo<DonRecord> pageInfo =  appDonRecordService.loadByPage(pageNo,pageSize,donRecord);
        return pageInfo;
    }

    /**
     * 统计捐献人数和钱数
     * 统计一起捐数量和钱数
     * @param donRecord
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/myDonList")
    public RestResponse<Map<String, Object>> myDonList(DonRecord donRecord){
        donRecord.setRemark("NORMAL");
        donRecord.setState("NORMAL");
        Map<String, Object> map = new HashMap();
        map.put("perNum", appDonRecordService.getCountByProId(donRecord));
        map.put("perMoneyNum",appDonRecordService.getCountByProIdForMoney(donRecord)==null?0:appDonRecordService.getCountByProIdForMoney(donRecord));
        map.put("temNum", appDonRecordService.getTemPerCount(donRecord));
        map.put("temMoneyNum", appDonRecordService.getTemCountByProId(donRecord)==null?0:appDonRecordService.getTemCountByProId(donRecord));
        return new RestResponse<Map<String, Object>>(ExceptionResult.REQUEST_SUCCESS, "获取捐赠统计数据！", map);
    }

    /**
     * 增加或者更新捐赠记录管理
     * <p>Title: add</p>
     * <p>Description: </p>
     * @param donRecord
     * @return RestResponse<DonRecord>
     */
    @CrossOrigin
   @PostMapping(value = "/save")
   public RestResponse<DonRecord> save(DonRecord donRecord, HttpServletRequest request){
        String token = request.getHeader("Authorization");
        if(null != token && !"".equals(token)){
            String userid = JWTUtil.getUserId(token);
            donRecord.setUserId(userid);
        }
       if(StringUtil.isEmpty(donRecord.getId()) && StringUtil.isEmpty(donRecord.getState())) {
           donRecord.setCustomId(IdGen.uuid());
           donRecord.setState(GlobalStr.WAITING_DON);
       }
//       else {
//           donRecord.setCustomId(IdGen.uuid());
//           donRecord.setState(GlobalStr.NORMAL_DON);
//       }
       donRecord.setTime(new Date());
       appDonRecordService.save(donRecord);
       return new RestResponse<DonRecord>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", donRecord);
   }

    /**
     * 更新捐赠记录的支付状态
     * @param donRecord
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/updateState")
    public RestResponse<String> updateState(DonRecord donRecord){

        this.appDonRecordService.updateBycustomid(donRecord);
        //支付成功需要更新个人信息的捐赠记录
        donRecord = this.appDonRecordService.get(donRecord);

        //更新个人信息
        SmSchoolmate smSchoolmate = new SmSchoolmate();
        smSchoolmate.setUserId(donRecord.getUserId());
        appSchoolmateService.getByUserId(smSchoolmate);
        BigDecimal donFee = new BigDecimal("0.00");
        if(null == smSchoolmate.getDonFee()){
            donFee =donRecord.getMoney();
        }else {
            donFee = smSchoolmate.getDonFee().add(donRecord.getMoney());
        }
        smSchoolmate.setDonFee(donFee);
        appSchoolmateService.saveinfo(smSchoolmate);
        //更新捐赠项目
        DonProject donProject =  new DonProject();
        donProject.setId(donRecord.getDonProjectId().toString());
        donProject = donService.getById(donProject);
        Double money = 0.00D;
        if(null == donProject.getGotMoney()){
            money =donRecord.getMoney().doubleValue();
        }else {
            money = donProject.getGotMoney() + donRecord.getMoney().doubleValue();
        }
        donProject.setGotMoney(money);
        int num = 1;
        if(null != donProject.getDonatingNum()){
            num =donProject.getDonatingNum()+1;
        }
        donProject.setDonatingNum(num);
        donService.updateMoneyAndPer(donProject);
        //更新一起捐
        if (null != donRecord.getTogetherId() && !"".equals(donRecord.getTogetherId())){
           DonProjectTogether donProjectTogether = new DonProjectTogether();
            donProjectTogether.setId(donRecord.getTogetherId());
            donProjectTogether = appDonProjectTogetherService.getById(donProjectTogether);
            Double tmoney = null == donProjectTogether.getGotMoney()? 0.00:donProjectTogether.getGotMoney();
            donProjectTogether.setGotMoney(tmoney+(Double) donRecord.getMoney().doubleValue());
            int tnum = null == donProjectTogether.getPersonNum()? 0: Integer.valueOf(donProjectTogether.getPersonNum());
            donProjectTogether.setPersonNum(String.valueOf(tnum+1));
            donProjectTogether.setStatus("SUCCESS");
            appDonProjectTogetherService.save(donProjectTogether);
        }
        return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");
    }

    /**
     * 根据id获取捐赠记录的信息
     * @param donRecord
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/getId")
    public RestResponse<DonRecord> getId(DonRecord donRecord){
        donRecord = this.appDonRecordService.get(donRecord);
        return new RestResponse<DonRecord>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", donRecord);
    }


    /**
     * 获取某个项目的所有捐赠记录
     * <p>Title: list</p>
     * <p>Description: </p>
     * @param pageNo
     * @param pageSize
     * @param donRecord
     * @return PageInfo<DonRecord>
     */
    @CrossOrigin
    @PostMapping(value = "/getmyDonRecordByPro")
    public PageInfo<DonRecord> getmyDonRecordByPro(int pageNo,int pageSize,DonRecord donRecord, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        // 当前时间
        String userid = JWTUtil.getUserId(token);
        donRecord.setUserId(userid);
        PageIo<DonRecord> pageInfo =  appDonRecordService.loadByPage(pageNo,pageSize,donRecord);
        return pageInfo;
    }

    /**
     * 分页获取一起捐的捐赠记录
     * <p>Title: list</p>
     * <p>Description: </p>
     * @param pageNo
     * @param pageSize
     * @param donRecord
     * @return PageInfo<DonRecord>
     */
    @CrossOrigin
    @PostMapping(value = "/loadTeamByPage")
    public PageInfo<DonRecord> loadTeamByPage(int pageNo,int pageSize,DonRecord donRecord, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        // 当前token过期时间
        String userid = JWTUtil.getUserId(token);
        donRecord.setUserId(userid);
        PageIo<DonRecord> pageInfo =  appDonRecordService.loadTeamByPage(pageNo,pageSize,donRecord);
        return pageInfo;
    }
    /**
     *  获取个人捐赠总数和捐款总次数
     */
    @CrossOrigin
    @PostMapping(value = "/getProDon")
    public RestResponse<Map<String, Object>> getmyDonRecordByPro(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String userid = JWTUtil.getUserId(token);

        DonRecord donRecord = new DonRecord();
        donRecord.setUserId(userid);
        donRecord.setRemark("NORMAL");
        donRecord.setState(GlobalStr.NORMAL_DON);
        Map<String, Object> map = new HashMap();
        map.put("myDonMoney", appDonRecordService.getPerCountMoney(donRecord));
        map.put("myDonNo",appDonRecordService.getPerCount(donRecord));

        return new RestResponse<Map<String, Object>>(ExceptionResult.REQUEST_SUCCESS, "获取捐赠统计数据！", map);

    }
}