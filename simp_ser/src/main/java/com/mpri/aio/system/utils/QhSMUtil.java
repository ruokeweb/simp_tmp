package com.mpri.aio.system.utils;

import com.alibaba.fastjson.JSONObject;
import com.mpri.aio.system.common.GlobalStr;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;


/**
 * 青海大学
 * <p>
 * Title: EmcSmsUtils
 * </p>
 * <p>
 * Description:
 * </p>
 *
 * @author syp
 * @date 2018年11月20日
 */
@Component
public class QhSMUtil {

    @Autowired
    private FreeMarkerConfigurer configurer;
    //短信tittle
    private static final String TITTLE = "TITTLE";
    //短信类型
    private static final Integer ISCRON= 0; //0 及时短信 1 延时

//    @Value("${sms.serviceUrl}")
    String serviceUrl ;
//    @Value("${sms.accountID}")
    String accountID ;
//    @Value("${sms.accountKey}")
    String accountKey ;

    @Autowired
    private RunSettingParamsUtils runSettingParamsUtils;
    
    /**
     * 短信发送（外部）
     *
     * @return
     */
    private String sendmsg(String title, String content,int isCron, String sendTime,String typecode,  String extReceiver ) {

        HttpPost httpPost = new HttpPost(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.sms_serviceUrl) + "/interfaces/msg/");
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();

            params.add(new BasicNameValuePair("ucode", runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.sms_accountID)));
            params.add(new BasicNameValuePair("api_key", runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.sms_accountKey)));
            params.add(new BasicNameValuePair("content", content));
            params.add(new BasicNameValuePair("mobiles", extReceiver));

            HttpEntity httpEntity = new UrlEncodedFormEntity(params, "UTF-8");
            httpPost.setEntity(httpEntity);
            CloseableHttpResponse response = client.execute(httpPost);
            String str1 = EntityUtils.toString(response.getEntity());
			JSONObject data = JSONObject.parseObject(str1);
			if(null != data.getInteger("success")){
			    return GlobalStr.SUCCESS;
            }
            return GlobalStr.ERRO;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "发送失败";
        } finally {
            httpPost.releaseConnection();
        }
    }

    /**
     * 发送短信
     * <p>Title: sendSms</p>
     * <p>Description: </p>
     * @throws TemplateException
     * @throws IOException
     */
    public void sendSms(String tosend, String templateName,Map<String, Object> map) throws IOException, TemplateException {
        if(!templateName.endsWith(".ftl"))
        {
            templateName=templateName+".ftl";
        }
        Template template = configurer.getConfiguration().getTemplate(templateName);
        String html = FreeMarkerTemplateUtils
                .processTemplateIntoString(template, map);

        this.sendmsg(TITTLE, html, ISCRON,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format( new Date()),
                "",tosend       );

    }

    /**
     * 发送短信
     * <p>Title: main</p>
     * <p>Description: </p>
     */
    public String sendSms(String tosend,String content) {

        String res = this.sendmsg(TITTLE, content, ISCRON,null,"",tosend );
        System.out.println("青海大学短信发送结果======"+res);
        return res;
    }


    public static void main(String[] args) {
        // 外部用户：输入手机号
//		List outAddresses = new ArrayList();
//		outAddresses.add("17629261881");
//		String result = sendmsg("测试", "会议内容如下: 1111.", 0,"短信","","ydxy-mobile",outAddresses, new ArrayList()
//				);
//        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format( new Date()));
    }

}
