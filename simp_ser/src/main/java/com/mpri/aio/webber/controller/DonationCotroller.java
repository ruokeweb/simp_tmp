package com.mpri.aio.webber.controller;

import com.github.pagehelper.util.StringUtil;
import com.jpay.ext.kit.HttpKit;
import com.jpay.ext.kit.PaymentKit;
import com.jpay.weixin.api.WxPayApi;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.common.utils.IdGen;
import com.mpri.aio.donation.model.DonProject;
import com.mpri.aio.donation.model.DonRecord;
import com.mpri.aio.donation.model.DonThanks;
import com.mpri.aio.donation.service.DonProjectService;
import com.mpri.aio.donation.service.DonRecordService;
import com.mpri.aio.donation.service.DonThanksService;
import com.mpri.aio.gen.model.GenColumuInfo;
import com.mpri.aio.settings.model.SettingDepartment;
import com.mpri.aio.system.common.GlobalStr;
import com.mpri.aio.system.common.RedisCacheService;
import com.mpri.aio.system.init.InitCache;
import com.mpri.aio.system.model.SysDict;
import com.mpri.aio.system.shiro.JWTUtil;
import com.mpri.aio.system.utils.RunSettingParamsUtils;
import com.mpri.aio.webber.vo.DonProjectVo;
import com.mpri.aio.webber.vo.DonRecordVo;
import com.mpri.aio.webber.vo.DonSchoolmateInfoVo;
import com.mpri.aio.webber.vo.DonationVo;
import freemarker.template.utility.HtmlEscape;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("webber/open/don")
public class DonationCotroller  extends BaseController {

    @Autowired
    private DonProjectService donProjectService;
    @Autowired
    private DonRecordService donRecordService;
    @Autowired
    private DonThanksService donThanksService;
    @Autowired
    private RedisCacheService redisCacheService;
    private static String SUCCESS = "SUCCESS";
//    @Value("${wechat.appId}")
    private String appId;
//    @Value("${wechat.mchId}")
    private String mchId;
//    @Value("${wechat.mchId.key}")
    private String partnerKey;
    
    private Map<String,List<SysDict>> dictCache;
    private  Map<String, SettingDepartment> departBaseCache;
    private Map<String,List<SettingDepartment>> departCache;
    
    @Autowired
    private RunSettingParamsUtils runSettingParamsUtils;
    
    
    private void InitMaps() {
        dictCache= (Map<String,List<SysDict>>)redisCacheService.getCache(new SysDict(), InitCache.DICT_CACHE);
        departBaseCache= (Map<String,SettingDepartment>)redisCacheService.getBaseCache(new SettingDepartment(), InitCache.DEPART_BASE_CACHE);
        departCache =(Map<String,List<SettingDepartment>>)redisCacheService.getCache(new SettingDepartment(),InitCache.DEPART_CACHE);
    }
    /**
     * 获取捐赠致谢列表
     * <p>Title: list</p>
     * <p>Description: </p>
     * @param pageNo
     * @param pageSize
     * @param donThanks
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/Thankslist")
    public RestResponse< PageIo<DonThanks>> list(int pageNo, int pageSize, DonThanks donThanks) {
        if(null ==donThanks )
        {
            donThanks = new DonThanks();

        }
        donThanks.setIsshow(GlobalStr.BOOLEAN_YES);
        PageIo<DonThanks> pageInfo =  donThanksService.loadByPage(pageNo,pageSize,donThanks);
        return new RestResponse< PageIo<DonThanks>>(ExceptionResult.REQUEST_SUCCESS, "捐赠致谢数据获取成功", pageInfo);
    }

    /**
     * 保存捐赠留言
     */
    @PostMapping(value="/saveDonThank")
    public RestResponse< DonThanks> saveDonThanks(DonThanks donThanks , @RequestParam String token) {

        if(null ==donThanks )
        {
            return new RestResponse<DonThanks>(ExceptionResult.REQUEST_SUCCESS, "保存信息不能为空", null);
        }
        if(null == token|| "".equals(token)){
            donThanks.setName("游客");
        }else{
            String username = JWTUtil.getUsername(token);
            String userid = JWTUtil.getUserId(token);
            donThanks.setName(username);
            donThanks.setUserId(userid);
        }
        donThanks = donThanksService.save(donThanks);
        return new RestResponse<DonThanks>(ExceptionResult.REQUEST_SUCCESS, "保存信息成功", donThanks);
    }
    /**
     * 获取所有捐赠项目表单的信息
     * @return
     */
    @PostMapping(value="/getProjectFrom")
    public RestResponse< Map> getProjectFrom(DonProject donProject) {
        this.InitMaps();
        Map map = new HashMap();
        //获取捐赠-币种
        List<SysDict> montytype=(List<SysDict>)dictCache.get(GlobalStr.MONEY_TYPE);
        map.put("MONEY_TYPE",montytype);
        //获取捐赠-项目类型
        List<SysDict> proType=(List<SysDict>)dictCache.get(GlobalStr.PROJECT_TYPE);
        map.put("PROJECT_TYPE",proType);
        //获取捐赠-支付方式
        List<SysDict> payType=(List<SysDict>)dictCache.get(GlobalStr.PAY_STYLE);
        map.put("PAY_STYLE",payType);
        String proType1 = proType.get(0).getValue();
        if(null == donProject){
            donProject = new DonProject();
        }
      /*  if(null ==donProject.getType() ||"".equals(donProject.getType())){
            donProject.setType(proType1);
        }*/
        List<DonProject> list = donProjectService.loadAllListBy(donProject);

        map.put("DonProject",list);

        return new RestResponse<Map>(ExceptionResult.REQUEST_SUCCESS, "捐赠项目获取成功", map);
    }

    /**
     * 获取所有捐赠项目列表
     * @param pageNo
     * @param pageSize
     * @return
     */
    @PostMapping(value="/getProjectList")
    public RestResponse< PageIo<DonProject>> getProjectList(@RequestParam int pageNo, @RequestParam int pageSize,DonProject  donProject) {
        this.InitMaps();
        if (0==pageSize)
            pageSize =10;
//        list = donProjectService.loadAllListByStatus(donProject);
        //获取捐赠项目状态
        PageIo<DonProject> pageInfo = donProjectService.loadByPage( pageNo,  pageSize, donProject);
        List<DonProject> list=pageInfo.getList();
        if(null!=list &&! list.isEmpty() ){
            for (DonProject don:list) {
                don.setStatus(setFormatDictValue(don.getStatus(),GlobalStr.PROJECT_STATUS));

            }
        }
        return new RestResponse< PageIo<DonProject>>(ExceptionResult.REQUEST_SUCCESS, "捐赠项目获取成功", pageInfo);
    }
    /**
     * 获取所有捐赠项目列表
     * @param pageNo
     * @param pageSize
     * @return
     */
    @PostMapping(value="/getProjectListNew")
    public RestResponse< PageIo<DonProject>> getProjectListNew(@RequestBody DonProjectVo donProjectVo) {
        this.InitMaps();
        DonProject donProject = donProjectVo.getDonProject();
        int pageNo = donProjectVo.getPageNo();
        int pageSize = donProjectVo.getPageSize();
        if (0==pageSize)
            pageSize =10;
        if(donProject==null){
            donProject= new DonProject();
        }
//        list = donProjectService.loadAllListByStatus(donProject);
        //获取捐赠项目状态
        PageIo<DonProject> pageInfo = donProjectService.loadByPage( pageNo,  pageSize, donProject);
        List<DonProject> list=pageInfo.getList();
        if(null!=list &&! list.isEmpty() ){
            for (DonProject don:list) {
                don.setStatus(setFormatDictValue(don.getStatus(),GlobalStr.PROJECT_STATUS));
                don.setType(setFormatDictValue(don.getType(),GlobalStr.PROJECT_TYPE));
                don.setTopic(setFormatDictValue(don.getTopic(),GlobalStr.BOOLEAN_TYPE));
                don.setContent(StringEscapeUtils.unescapeHtml4(don.getContent()));
                don.setSummary(StringEscapeUtils.unescapeHtml4(don.getSummary()));
            }
        }
        return new RestResponse< PageIo<DonProject>>(ExceptionResult.REQUEST_SUCCESS, "捐赠项目获取成功", pageInfo);
    }

    /**
     * 获取捐赠项目的信息
     * @param donProject
     * @return
     */
    @PostMapping(value="/getProject")
    public RestResponse<DonProject> getProject(DonProject donProject) {
        this.InitMaps();
        if(null == donProject){
            return new RestResponse<DonProject>(ExceptionResult.NOT_FOUND, "获取数据失败，请重试", donProject);
        }else{

            donProject = donProjectService.get(donProject);
            if(donProject!=null){
                donProject.setStatus(setFormatDictValue(donProject.getStatus(),GlobalStr.PROJECT_STATUS));
            }

        }
        return new RestResponse<DonProject>(ExceptionResult.REQUEST_SUCCESS, "捐献信息成功", donProject);
    }
    /**
     * 获取捐赠项目的信息
     * @param donProject
     * @return
     */
    @PostMapping(value="/getProjectNew")
    public RestResponse<DonProject> getProjectNew(@RequestBody DonProject donProject) {
        this.InitMaps();
        if(null == donProject){
            return new RestResponse<DonProject>(ExceptionResult.NOT_FOUND, "获取数据失败，请重试", donProject);
        }else{

            donProject = donProjectService.get(donProject);
            if(donProject!=null){
                donProject.setStatus(setFormatDictValue(donProject.getStatus(),GlobalStr.PROJECT_STATUS));
                donProject.setType(setFormatDictValue(donProject.getType(),GlobalStr.PROJECT_TYPE));
                donProject.setTopic(setFormatDictValue(donProject.getTopic(),GlobalStr.BOOLEAN_TYPE));
                donProject.setContent(StringEscapeUtils.unescapeHtml4(donProject.getContent()));
                donProject.setSummary(StringEscapeUtils.unescapeHtml4(donProject.getSummary()));
            }

        }
        return new RestResponse<DonProject>(ExceptionResult.REQUEST_SUCCESS, "捐献信息成功", donProject);
    }

    /**
     * 获取捐赠记录列表
     * @param pageNo
     * @param pageSize
     * @return
     */
    @PostMapping(value="/getRecordList")
    public RestResponse< PageIo<DonRecord>> getRecordList(int pageNo, int pageSize, DonRecord  donRecord ) {
        this.InitMaps();
        if (0==pageSize)
            pageSize =10;
//      list = donProjectService.loadAllListByStatus(donProject);
        PageIo<DonRecord> pageInfo = donRecordService.loadByPage(pageNo, pageSize,donRecord );
        List<DonRecord> list=pageInfo.getList();
        if(list!=null&&!list.isEmpty()){
            for (DonRecord don:list) {
                don.setMoneyType(setFormatDictValue(don.getMoneyType(),GlobalStr.MONEY_TYPE));
                don.setStyle(setFormatDictValue(don.getStyle(),GlobalStr.PAY_STYLE));
                don.setDonType(setFormatDictValue(don.getDonType(),GlobalStr.DONATION_TYPE));
            }

        }
        pageInfo.setList(list);
        return new RestResponse< PageIo<DonRecord>>(ExceptionResult.REQUEST_SUCCESS, "捐赠项目获取成功", pageInfo);
    }

    /**
     * 获取捐赠记录列表
     * @return
     */
    @PostMapping(value="/getRecordListNew")
    public RestResponse< PageIo<DonRecord>> getRecordListNew(@RequestBody DonRecordVo donRecordVo) {
        this.InitMaps();
        DonRecord donRecord = donRecordVo.getDonRecord();
        int pageNo = donRecordVo.getPageNo();
        int pageSize = donRecordVo.getPageSize();

        if (0==pageSize)
            pageSize =10;
        if(donRecord==null){
            donRecord = new DonRecord();
        }
        donRecord.setState(GlobalStr.NORMAL);
//      list = donProjectService.loadAllListByStatus(donProject);
        PageIo<DonRecord> pageInfo = donRecordService.getListNoSchoolmate(pageNo, pageSize,donRecord );
        List<DonRecord> list=pageInfo.getList();
        if(list!=null&&!list.isEmpty()){
            for (DonRecord don:list) {
                don.setMoneyType(setFormatDictValue(don.getMoneyType(),GlobalStr.MONEY_TYPE));
                don.setStyle(setFormatDictValue(don.getStyle(),GlobalStr.PAY_STYLE));
                don.setDonType(setFormatDictValue(don.getDonType(),GlobalStr.DONATION_TYPE));
            }

        }
        pageInfo.setList(list);
        return new RestResponse< PageIo<DonRecord>>(ExceptionResult.REQUEST_SUCCESS, "捐赠项目获取成功", pageInfo);
    }

    @PostMapping(value="/getRecord")
    public RestResponse< PageIo<DonRecord>> loadByPageSchool(int pageNo, int pageSize, DonRecord  donRecord ) {
        if(null ==donRecord )
        {
            donRecord = new DonRecord();
        }
        if (0==pageSize)
            pageSize =10;
        InitMaps();
//      list = donProjectService.loadAllListByStatus(donProject);
        PageIo<DonRecord> pageinfo = donRecordService.loadByPageSchool(pageNo, pageSize,donRecord );
        List<DonRecord> list=pageinfo.getList();
        if(null!=list &&! list.isEmpty() ){
            for (DonRecord don:list) {
                if(null != don.getSmSchoolmate() && null != don.getSmSchoolmate().getSmEducation()&& null != don.getSmSchoolmate().getSmEducation().getSpecialty())
                {
                      String specialty = setFormatDepartValue(don.getSmSchoolmate().getSmEducation().getSpecialty());
                    if(!StringUtil.isEmpty(specialty)) {

                        don.getSmSchoolmate().getSmEducation().setSpecialty(specialty);
                    }else{
                        don.getSmSchoolmate().getSmEducation().setSpecialty("");
                    }
                }
            }
        }

        return new RestResponse< PageIo<DonRecord>>(ExceptionResult.REQUEST_SUCCESS, "捐赠记录获取成功", pageinfo);
    }

    @PostMapping(value="/getOne")
    public RestResponse< DonRecord> getOne(DonRecord  donRecord ) {

        return new RestResponse< DonRecord>(ExceptionResult.REQUEST_SUCCESS, "捐赠记录获取成功", donRecordService.get(donRecord));
    }

    /**
     * 保存捐赠信息
     */
    @PostMapping(value="/saveRecord")
    public RestResponse< DonRecord> saveDonRecord(DonRecord  donRecord ) {

        if(null ==donRecord )
        {
            return new RestResponse<DonRecord>(ExceptionResult.REQUEST_SUCCESS, "保存信息不能为空", null);
        }

//        if(null != donRecord && "".equals(donRecord.getParamA())){
//            String userId = JWTUtil.getUserId(donRecord.getParamA());
//            donRecord.setUserId(userId);
//        }
        UUID uuid = UUID.randomUUID();
        String nonceStr = uuid.toString().replaceAll("-", "");
        donRecord.setCustomId(nonceStr);
        donRecord.setState(GlobalStr.WAITING_DON);
        donRecord = donRecordService.save(donRecord);

        return new RestResponse< DonRecord>(ExceptionResult.REQUEST_SUCCESS, "保存成功", donRecord);
    }
    /**
     * 保存捐赠信息
     */
    @PostMapping(value="/saveRecordNew")
    public RestResponse< DonRecord> saveRecordNew(@RequestBody DonRecord  donRecord,HttpServletRequest httpServletRequest ) {
        String token = httpServletRequest.getHeader("Authorization");
        String username = JWTUtil.getUsername(token);
        String userId = JWTUtil.getUserId(token);
        if(null ==donRecord )
        {
            return new RestResponse<DonRecord>(ExceptionResult.REQUEST_SUCCESS, "保存信息不能为空", null);
        }
        donRecord.setUserId(userId);
        UUID uuid = UUID.randomUUID();
        String nonceStr = uuid.toString().replaceAll("-", "");
        donRecord.setCustomId(nonceStr);
        donRecord.setState(GlobalStr.WAITING_DON);
        donRecord = donRecordService.save(donRecord);

        return new RestResponse< DonRecord>(ExceptionResult.REQUEST_SUCCESS, "保存成功", donRecord);
    }
    /**
     * 注册用户并保存捐赠信息
     */
    @PostMapping(value="/saveSchoolmateAndRecord")
    public RestResponse< DonRecord> saveSchoolmateAndRecord(HttpServletRequest httpServletRequest) {

        DonRecord donRecord = donRecordService.saveSchoolmateAndRecord(httpServletRequest);
        return new RestResponse< DonRecord>(ExceptionResult.REQUEST_SUCCESS, "保存成功", donRecord);
    }
    /**
     * 注册用户并保存捐赠信息
     */
    @PostMapping(value="/saveSchoolmateAndRecordNew")
    public RestResponse< DonRecord> saveSchoolmateAndRecordNew(@RequestBody DonSchoolmateInfoVo donSchoolmateInfoVo) {

        DonRecord donRecord = donRecordService.saveSchoolmateAndRecordNew(donSchoolmateInfoVo);
        return new RestResponse< DonRecord>(ExceptionResult.REQUEST_SUCCESS, "保存成功", donRecord);
    }



    @PostMapping(value = "getDepartment")
    public RestResponse< List<SettingDepartment>> getDepartment(SettingDepartment  settingDepartment ) {
        this.InitMaps();
        List<SettingDepartment> settingDepartments = departCache.get(settingDepartment.getId());
        if("0".equals(settingDepartment.getId())){
            settingDepartments =  departCache.get(settingDepartments.get(0).getId());
        }


        return new RestResponse< List<SettingDepartment>>(ExceptionResult.REQUEST_SUCCESS, "获取成功", settingDepartments);
    }
    @PostMapping(value = "getDepartmentNew")
    public RestResponse< List<SettingDepartment>> getDepartmentNew(@RequestBody SettingDepartment  settingDepartment ) {
        this.InitMaps();
        List<SettingDepartment> settingDepartments = departCache.get(settingDepartment.getId());
        if("0".equals(settingDepartment.getId())){
            settingDepartments =  departCache.get(settingDepartments.get(0).getId());
        }


        return new RestResponse< List<SettingDepartment>>(ExceptionResult.REQUEST_SUCCESS, "获取成功", settingDepartments);
    }
    private String setFormatDictValue(String value,String typeCode) {
        if(!StringUtil.isEmpty(value)) {
            //key: redis内key的规则：type:value
            List<SysDict> sds=(List<SysDict>)dictCache.get(typeCode);
            for(SysDict dict : sds) {
                if(value.equals(dict.getValue())) {
                    return dict.getLabel();
                }
            }
        }
        return "";
    }

    private String setFormatDepartValue(String value) {
        if(!StringUtil.isEmpty(value)) {
            return departBaseCache.get(value).getName();
        }
        return "";
    }

    /**
     * 组件支付接口
     * @param request
     * @return
     */
    @CrossOrigin
    @PostMapping("/createUnifiedOrder")
    public RestResponse<String> createUnifiedOrder(HttpServletRequest request) {
        String orderId = request.getParameter("orderId");
        DonRecord donRecord = new DonRecord();
        donRecord.setCustomId(orderId);
        DonRecord resdon = donRecordService.getByCustomId(donRecord);
        BigDecimal price = resdon.getMoney();
        BigDecimal priceFee=price.multiply(new BigDecimal("100"));
        UUID uuid = UUID.randomUUID();
        String nonceStr = uuid.toString().replaceAll("-", "");
        String order_price = priceFee.toBigInteger().toString(); // 价格   注意：价格的单位是分

        String body = "捐赠-支付订单";   // 商品名称
        String out_trade_no =  resdon.getCustomId(); // 订单号

        // 获取发起电脑 ip
        String spbill_create_ip = request.getRemoteAddr();
        // 回调接口
        String trade_type = "NATIVE";
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("appid", runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.wechat_appId));
        packageParams.put("mch_id", runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.wechat_mchId));
        packageParams.put("nonce_str", nonceStr);
        packageParams.put("body", body);
        packageParams.put("out_trade_no", out_trade_no);
        packageParams.put("total_fee", order_price);
        packageParams.put("spbill_create_ip", spbill_create_ip);
        packageParams.put("notify_url", request.getParameter("notify_url"));
        packageParams.put("trade_type", trade_type);
        String sign = PaymentKit.createSign(packageParams, runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.wechat_mchId_key));
        packageParams.put("sign", sign.toUpperCase());
        String xmlResult = WxPayApi.pushOrder(false, packageParams);
        // 转成xml
        Map<String, String> map = PaymentKit.xmlToMap(xmlResult);

        String urlCode = (String) map.get("code_url");
        return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "操作成功", urlCode);
    }

    /**
     * 组件支付新的
     * @param request
     * @return
     */
    @CrossOrigin
    @PostMapping("/createUnifiedOrderNew")
    public RestResponse<String> createUnifiedOrderNew(HttpServletRequest request, @RequestBody DonationVo donationVo) {
        String orderId = donationVo.getOrderId();
        DonRecord donRecord = new DonRecord();
        donRecord.setCustomId(orderId);
        DonRecord resdon = donRecordService.getByCustomId(donRecord);
        BigDecimal price = resdon.getMoney();
        BigDecimal priceFee=price.multiply(new BigDecimal("100"));
        UUID uuid = UUID.randomUUID();
        String nonceStr = uuid.toString().replaceAll("-", "");
        String order_price = priceFee.toBigInteger().toString(); // 价格   注意：价格的单位是分

        String body = "捐赠-支付订单";   // 商品名称
        String out_trade_no =  resdon.getCustomId(); // 订单号

        // 获取发起电脑 ip
        String spbill_create_ip = request.getRemoteAddr();
        // 回调接口
        String trade_type = "NATIVE";
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("appid", runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.wechat_appId));
        packageParams.put("mch_id", runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.wechat_mchId));
        packageParams.put("nonce_str", nonceStr);
        packageParams.put("body", body);
        packageParams.put("out_trade_no", out_trade_no);
        packageParams.put("total_fee", order_price);
        packageParams.put("spbill_create_ip", spbill_create_ip);
        packageParams.put("notify_url", donationVo.getNotify_url());
        packageParams.put("trade_type", trade_type);
        String sign = PaymentKit.createSign(packageParams, runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.wechat_mchId_key));
        packageParams.put("sign", sign.toUpperCase());
        String xmlResult = WxPayApi.pushOrder(false, packageParams);
        // 转成xml
        Map<String, String> map = PaymentKit.xmlToMap(xmlResult);

        String urlCode = (String) map.get("code_url");
        return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "操作成功", urlCode);
    }

    /**
     * 微信支付成功回调(外网情况下测试才能成功)
     * <p>Title: notify</p>
     * <p>Description: </p>
     * @param request
     * @return String
     */
    @CrossOrigin
    @PostMapping("/notify")
    public String notify(HttpServletRequest request)  {
//
//		// 支付结果通用通知文档: https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_7

            String xmlMsg = HttpKit.readData(request);
            Map<String, String> params = PaymentKit.xmlToMap(xmlMsg);
            String result_code = params.get("result_code");
            String orderId = params.get("out_trade_no");
            Map<String, String> res = new HashMap<String, String>();
            res.put("return_code", GlobalStr.SUCCESS);
            res.put("return_msg", "OK");
            // 校验返回来的支付结果,根据已经配置的密钥
        try {
            if (PaymentKit.verifyNotify(params, runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.wechat_mchId_key))) {
                if (SUCCESS.equalsIgnoreCase(result_code)) {
                    DonRecord donRecord = new DonRecord();
                    donRecord.setCustomId(orderId);
                    donRecordService.updateBycustomid(donRecord);
                    return PaymentKit.toXml(res);
                }
            }

        }catch (Exception e){
            System.err.println(e);
        }
        return null;
    }
}
