package com.mpri.aio.system.LicenseAuth;

import com.mpri.aio.license.model.LicenseCheckModel;
import de.schlichtherle.license.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.prefs.Preferences;

/**
 * 验证license文件
 */
@Component
public class VerifyLicense {
    private Logger logger = LoggerFactory.getLogger(VerifyLicense.class);

    private  String PUBLICALIAS;
    private  String STOREPWD;
    private  String SUBJECT;
    private  String licPath;
    private InputStream pubPathIs;
    private  String schoolCode;
    private String uploadFolder;


    public  static  final  ThreadLocal<String>  endDateFlag = new ThreadLocal<>();
    public  static  final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    private void setParam(Environment environment){
        // 获取参数
        PUBLICALIAS = environment.getProperty("clientlicense.PUBLICALIAS");
        STOREPWD = environment.getProperty("clientlicense.STOREPWD");
        SUBJECT = environment.getProperty("clientlicense.SUBJECT");
        licPath = environment.getProperty("clientlicense.licPath");
        schoolCode = environment.getProperty("clientlicense.schoolCode");
        uploadFolder = environment.getProperty("file.uploadFolder");
    }

    public boolean verify(Environment environment) {

        /************** 证书使用者端执行 ******************/
        setParam(environment);
        String licPath = uploadFolder + "/licenseKeystoreClient/" + schoolCode+"/license.lic" ;
        String storePath = uploadFolder + "/licenseKeystoreClient/" + schoolCode+"/publicKeys.store" ;

        //判断公钥或者证书是否存在
        File licfile = new File(licPath);
        File storefile = new File(storePath);

        if(!licfile.exists()) {
            return false;
        }else if(!storefile.exists()){
            return false;
        }

        try {
            pubPathIs = new FileInputStream(storePath);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        LicenseManager licenseManager = new LicenseManagerHolder()
                .getLicenseManager(initLicenseParams());
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 安装证书
        try {
            LicenseContent licenseContent = licenseManager.install(new File(licPath));
            System.out.println("客户端安装证书成功!");
            logger.info(MessageFormat.format("证书校验通过，证书有效期：{0} - {1}",format.format(licenseContent.getNotBefore()),format.format(licenseContent.getNotAfter())));
        } catch (Exception e) {
            System.out.println("客户端证书安装失败!");
            return false;
        }
        // 验证证书
        try {
            LicenseContent verify = licenseManager.verify();

            if(!schoolCode.equals((String)verify.getExtra())){
                System.out.println("客户端证书验证失效!");
                return false;
            }

//            LicenseCheckModel licenseModel = (LicenseCheckModel)verify.getExtra();
//            if(!schoolCode.equals(licenseModel.getShoolCode())){
//                System.out.println("客户端证书验证失效!");
//                return false;
//            }

            //TODO 需添加license唯一性验证参数 mac cpuserial 等
//            String clientMacAddress = MacCpuSeriaUtils.getMAC();
//            String clientCPUSerial = MacCpuSeriaUtils.getCPUId();
//
//            if(!clientCPUSerial.equals(licenseModel.getCPUSerial()) && !clientMacAddress.equals(licenseModel.getMacAddress())){
//                System.out.println("客户端证书验证失效!");
//                return false;
//            }

            Date notAfter = verify.getNotAfter();
            String endDate = format.format(notAfter);
            endDateFlag.set(endDate);

            System.out.println("客户端验证证书成功!");
            logger.info(MessageFormat.format("证书校验通过，证书有效期：{0} - {1}",format.format(verify.getNotBefore()),format.format(verify.getNotAfter())));
        } catch (Exception e) {
            System.out.println("客户端证书验证失效!");
            return false;
        }
        return true;
    }

    // 返回验证证书需要的参数
    private  LicenseParam initLicenseParams() {
        Preferences preference = Preferences
                .userNodeForPackage(VerifyLicense.class);
        CipherParam cipherParam = new DefaultCipherParam(STOREPWD);
        KeyStoreParam privateStoreParam = new ExtendsKeyStoreParam(
                VerifyLicense.class, pubPathIs, PUBLICALIAS, STOREPWD, null);
        LicenseParam licenseParams = new DefaultLicenseParam(SUBJECT,
                preference, privateStoreParam, cipherParam);
        return licenseParams;
    }



    /**
     * 获取两个日期（不含时分秒）相差的天数，不包含今天
     */
    public static int dateBetween(Date startDate, String endDate) throws ParseException {
        Date dateStart = dateParse(dateFormat(startDate, DATE_PATTERN), DATE_PATTERN);
        Date dateEnd = dateParse(endDate, DATE_PATTERN);
        return (int) ((dateEnd.getTime() - dateStart.getTime())/1000/60/60/24);
    }

    /**
     * 获取两个日期（不含时分秒）相差的天数，包含今天
     */
    public static int dateBetweenIncludeToday(Date startDate, String endDate) throws ParseException {
        return dateBetween(startDate, endDate) + 1;
    }

    /**
     *字符串解析成时间对象
     */
    public static Date dateParse(String dateTimeString, String pattern) throws ParseException{
        if(StringUtils.isBlank(pattern)){
            pattern = VerifyLicense.DATE_PATTERN;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.parse(dateTimeString);
    }
    /**
     * 时间格式化成字符串
     */
    public static String dateFormat(Date date, String pattern) throws ParseException{
        if(StringUtils.isBlank(pattern)){
            pattern = VerifyLicense.DATE_PATTERN;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

}
