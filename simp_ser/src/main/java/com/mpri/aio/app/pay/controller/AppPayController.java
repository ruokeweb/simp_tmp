package com.mpri.aio.app.pay.controller;

import com.jpay.ext.kit.HttpKit;
import com.jpay.ext.kit.PaymentKit;
import com.jpay.weixin.api.WxPayApi;
import com.mpri.aio.app.pay.model.PayResJson;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.common.utils.StringUtils;
import com.mpri.aio.donation.model.DonRecord;
import com.mpri.aio.donation.service.DonRecordService;
import com.mpri.aio.system.utils.RunSettingParamsUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;
//import com.mpri.aio.schoolmate.aop.aspect.PointLevel;


/**
 * 
 * <p>
 * Title: WeiChatPayController
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author syp
 * @date 2018年9月20日
 */

@RestController
@RequestMapping("/app/weichatpay")
public class AppPayController {

	@Autowired
	private DonRecordService donRecordService;

	private static String SUCCESS = "SUCCESS";
	
//	@Value("${wechat.appId}")
	private String appId;
//	@Value("${wechat.mchId}")
	private String mchId;
//	@Value("${wechat.mchId.key}")
	private String partnerKey;

    @Autowired
    private RunSettingParamsUtils runSettingParamsUtils;
    
	
	/**
	 * 统一下单
	* <p>Title: createUnifiedOrder</p>  
	* <p>Description: </p>  
	* @param request
	* @return RestResponse<PayResJson>
	 */
	@CrossOrigin
	@PostMapping("/createUnifiedOrder")
	public RestResponse<PayResJson> createUnifiedOrder(HttpServletRequest request) {
		PayResJson prj = new PayResJson();
		prj.setSuccess(false);
		String orderId = request.getParameter("orderId");
		// 接受参数(openid)
		String openid = request.getParameter("openid");	
		if (StringUtils.isAnyBlank(orderId, openid)) {
			return new RestResponse<PayResJson>(ExceptionResult.SYS_ERROR, "支付失败,支付所需参数缺失", prj);
		}
		// 这里调用service层根据订单id获取订单数据
		DonRecord donRecord = new DonRecord();
		donRecord.setCustomId(orderId);
		DonRecord resdon = donRecordService.getByCustomId(donRecord);

		if (resdon == null) {
			return new RestResponse<PayResJson>(ExceptionResult.SYS_ERROR, "支付失败,暂时无法获取到您的订单数据,请稍后再试", prj);
		}
		try {

			// 支付金额 **金额不能有小数点,单位是分!!**
			BigDecimal price = resdon.getMoney();
			BigDecimal priceFee=price.multiply(new BigDecimal("100"));
			// 商家订单号
			String orderNo = resdon.getCustomId();

			// 创建 时间戳
			String timeStamp = Long.valueOf(System.currentTimeMillis()).toString();
			// 生成32位随机数
			UUID uuid = UUID.randomUUID();
			String nonceStr = uuid.toString().replaceAll("-", "");
			// 商品描述
			String body = "捐赠-支付订单";
			// 创建hashmap(用户获得签名)
			SortedMap<String, String> paraMap = new TreeMap<String, String>();
			// 设置请求参数(小程序ID)
			paraMap.put("appid", runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.wechat_appId));
			// 设置请求参数(商户号)
			paraMap.put("mch_id", runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.wechat_mchId));
			// 设置请求参数(随机字符串)
			paraMap.put("nonce_str", nonceStr);
			// 设置请求参数(商品描述)
			paraMap.put("body", body);
			// 设置请求参数(商户订单号)
			paraMap.put("out_trade_no", orderNo);
			// 设置请求参数(总金额)
			paraMap.put("total_fee", priceFee.toBigInteger().toString());
			// 设置请求参数(终端IP) 如果是springmvc,或者能获取到request的servlet,用下面这种
			paraMap.put("spbill_create_ip", request.getRemoteAddr());
			// 设置请求参数(通知地址)
			paraMap.put("notify_url", request.getParameter("notify_url"));
			// 设置请求参数(交易类型)
			paraMap.put("trade_type", String.valueOf(WxPayApi.TradeType.JSAPI));
			// 设置请求参数(openid)(在接口文档中 该参数 是否必填项 但是一定要注意 如果交易类型设置成'JSAPI'则必须传入openid)
			paraMap.put("openid", openid);
			// MD5运算生成签名，这里是第一次签名，用于调用统一下单接口
			String sign = PaymentKit.createSign(paraMap, runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.wechat_mchId_key));
			paraMap.put("sign", sign);
			// 统一下单,向微信api发送数据
			String xmlResult = WxPayApi.pushOrder(false, paraMap);
			// 转成xml
			Map<String, String> map = PaymentKit.xmlToMap(xmlResult);
			// 返回状态码
			String return_code = (String) map.get("return_code");
			// 返回给小程序端需要的参数
			Map<String, String> returnMap = new HashMap<String, String>();
			if (SUCCESS.equals(return_code)) {
				// 返回的预付单信息
				returnMap.put("appId", runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.wechat_appId));
				returnMap.put("nonceStr", nonceStr);
				String prepay_id = (String) map.get("prepay_id");
				returnMap.put("package", "prepay_id=" + prepay_id);
				//将预支付订单存储 方便再次支付
				resdon.setCustomTemp(prepay_id);;
				donRecordService.save(resdon);
								
				returnMap.put("signType", "MD5");
				// 这边要将返回的时间戳转化成字符串，不然小程序端调用wx.requestPayment方法会报签名错误
				returnMap.put("timeStamp", timeStamp);
				// 拼接签名需要的参数
				// 再次签名，这个签名用于小程序端调用wx.requesetPayment方法
				String paySign = PaymentKit.createSign(returnMap, 
						runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.wechat_mchId_key)).toUpperCase();
				returnMap.put("paySign", paySign);
				prj.setObj(returnMap);
				prj.setSuccess(true);
				return new RestResponse<PayResJson>(ExceptionResult.REQUEST_SUCCESS, "操作成功", prj);
				
			} else {
				return new RestResponse<PayResJson>(ExceptionResult.SYS_ERROR, getMsgByCode(return_code), prj);
			}
		} catch (Exception e) {
		    System.out.println(e.getMessage());
			return new RestResponse<PayResJson>(ExceptionResult.SYS_ERROR, "微信支付下单失败,请稍后再试", prj);
		}
	}

	/**
	 * 小程序付款失败调用的接口
	* <p>Title: payFail</p>  
	* <p>Description: </p>  
	* @param customid
	* @return RestResponse<String>
	 */
	@CrossOrigin
	@PostMapping("/payFail")
	public RestResponse<String> payFail(String customid) {
		donRecordService.updateBycustomidFail(customid);
		return new RestResponse<String>(ExceptionResult.SYS_ERROR, "支付失败！", "");
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
	public String notify(HttpServletRequest request) {
//
//		// 支付结果通用通知文档: https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_7
		String xmlMsg = HttpKit.readData(request);
		Map<String, String> params = PaymentKit.xmlToMap(xmlMsg);
		String result_code = params.get("result_code");
		String orderId = request.getParameter("out_trade_no");
		Map<String, String> res = new HashMap<String, String>();
		res.put("return_code", SUCCESS);
		res.put("return_msg", "OK");
		// 校验返回来的支付结果,根据已经配置的密钥
		if (PaymentKit.verifyNotify(params, 
				runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.wechat_mchId_key))) {
			if ((SUCCESS).equals(result_code)) {
				DonRecord donRecord = new DonRecord();
				donRecord.setCustomId(orderId);
				donRecordService.updateBycustomid(donRecord);
				return PaymentKit.toXml(res);
			}
		}
		return null;		
	}

	/**
	 * 2019-02-11校友修改
	 * 微信支付成功回调
	* <p>Title: successPay</p>  
	* <p>Description: </p>  
	* @return RestResponse<String>
	 */
//	@PointLevel(code ="DON_FIRST")
	@CrossOrigin
	@PostMapping("/successPay")
	public RestResponse<String> successPay(String orderId) {
		DonRecord donRecord = new DonRecord();
		donRecord.setCustomId(orderId);
		donRecordService.updateBycustomid(donRecord);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "捐款成功！", "");
	}
	
	
	/**
	 * 再次支付
	* <p>Title: queryOrder</p>  
	* <p>Description: </p>  
	* @return RestResponse<PayResJson>
	 */
	@CrossOrigin
	@PostMapping("/againPay")
	public RestResponse<PayResJson> againPay(String  orderId) {
		PayResJson prj = new PayResJson();
		// 这里调用service层根据订单id获取订单数据
		DonRecord donRecord = new DonRecord();
		donRecord.setCustomId(orderId);
		DonRecord resdon = donRecordService.getByCustomId(donRecord);
		if (resdon == null) {
			return new RestResponse<PayResJson>(ExceptionResult.SYS_ERROR, "支付失败,暂时无法获取到您的订单数据,请稍后再试", prj);
		}else {
			// 创建 时间戳
			String timeStamp = Long.valueOf(System.currentTimeMillis()).toString();
			// 生成32位随机数
			UUID uuid = UUID.randomUUID();
			String nonceStr = uuid.toString().replaceAll("-", "");
			Map<String, String> returnMap = new HashMap<String, String>();
			// 返回的预付单信息
			returnMap.put("appId", runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.wechat_appId));
			returnMap.put("nonceStr",nonceStr);
			String prepay_id = resdon.getCustomTemp();
			returnMap.put("package", "prepay_id=" + prepay_id);
			returnMap.put("signType", "MD5");
			// 这边要将返回的时间戳转化成字符串，不然小程序端调用wx.requestPayment方法会报签名错误
			returnMap.put("timeStamp", timeStamp);
			// 拼接签名需要的参数
			// MD5运算生成签名，这里是第一次签名，用于调用统一下单接口
			// 再次签名，这个签名用于小程序端调用wx.requesetPayment方法
			String paySign = PaymentKit.createSign(returnMap, 
					runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.wechat_mchId_key)).toUpperCase();
			returnMap.put("paySign", paySign);
			prj.setObj(returnMap);
			prj.setSuccess(true);
			return new RestResponse<PayResJson>(ExceptionResult.REQUEST_SUCCESS, "操作成功", prj);
		}
	}
	
	
	/**
	 * 判断返回的return_code,给前端相应的提示
	 * 
	 * @param return_code
	 * @return
	 * @author syp
	 * @time 2018年7月9日17:53:13
	 * @return String
	 */
	private String getMsgByCode(String return_code) {
		switch (return_code) {
		case "NOTENOUGH":
			return "您的账户余额不足";
		case "ORDERPAID":
			return "该订单已支付完成,请勿重复支付";
		case "ORDERCLOSED":
			return "当前订单已关闭，请重新下单";
		case "SYSTEMERROR":
			return "系统超时，请重新支付";
		case "OUT_TRADE_NO_USED":
			return "请勿重复提交该订单";
		default:
			return "网络正在开小差,请稍后再试";
		}
	}

}
