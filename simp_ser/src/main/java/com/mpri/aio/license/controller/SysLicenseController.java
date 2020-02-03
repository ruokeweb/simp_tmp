package com.mpri.aio.license.controller;

import com.github.pagehelper.PageInfo;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.license.ZipUtils;
import com.mpri.aio.license.model.LicenseCheckModel;
import com.mpri.aio.license.model.SysLicense;
import com.mpri.aio.license.service.SysLicenseService;
import com.mpri.aio.system.LicenseAuth.ExtendsKeyStoreParam;
import com.mpri.aio.system.LicenseAuth.LicenseExceptionResult;
import com.mpri.aio.system.LicenseAuth.LicenseManagerHolder;
import com.mpri.aio.system.model.SysDict;
import com.mpri.aio.untils.licensefile.model.LicenseFile;
import com.mpri.aio.untils.licensefile.service.LicenseFileService;
import de.schlichtherle.license.*;
import org.apache.tools.zip.ZipOutputStream;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.security.auth.x500.X500Principal;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.prefs.Preferences;

/**
 * @Description: 认证——Controller
 * @Author: daihongbo
 * @project simp
 * @CreateDate: Mon Sep 16 19:24:31 GMT+08:00 2019
 * @Version: v_2.01
 */
@RestController
@RequestMapping("/sys/sysLicense")
@PropertySource(value = "classpath:licensegenerate.properties")
public class SysLicenseController extends BaseController {

    @Value("${serverlicense.PRIVATEALIAS}")
    private String PRIVATEALIAS;
    @Value("${serverlicense.KEYPWD}")
    private String KEYPWD;
    @Value("${serverlicense.STOREPWD}")
    private String STOREPWD;
    @Value("${serverlicense.SUBJECT}")
    private String SUBJECT;
    @Value("${serverlicense.consumerType}")
    private String consumerType;
    @Value("${serverlicense.consumerAmount}")
    private int consumerAmount;
    @Value("${serverlicense.info}")
    private String info;
    @Value("${serverlicense.macAddress}")
    private String macAddress;
    @Value("${serverlicense.CPUSerial}")
    private String CPUSerial;
    @Value("${file.uploadFolder}")
    private String uploadFolder;
    @Value("${file.staticAccessPath}")
    private String staticAccessPath;

    private String issuedTime;
    private String notBefore;
    private String notAfter;
    private String schoolCode;
    private InputStream priPath;

    @Autowired
    private Environment environment;

    @Autowired
    private SysLicenseService sysLicenseService;

    @Autowired
    private LicenseFileService fileService;

    /**
     * X500Princal是一个证书文件的固有格式，详见API
     */
    private final static X500Principal DEFAULTHOLDERANDISSUER = new X500Principal(
            "CN=webber、OU=webber、O=webber、C=china");

    /**
     * 获取认证列表
     */
    @CrossOrigin
    @PostMapping(value = "/list")
    public PageInfo<SysLicense> list(int pageNo, int pageSize, SysLicense sysLicense) {
        PageIo<SysLicense> pageInfo = sysLicenseService.loadByPage(pageNo, pageSize, sysLicense);
        return pageInfo;
    }

    /**
     * 学校名称转义
     */
    @CrossOrigin
    @PostMapping(value = "/getSchoolNameByTypeCode")
    public RestResponse<List<SysDict>> list() {
        List<Map<String, Object>> schoolInfoMaps = sysLicenseService.loadShoolKeyAndValue(null);
        List<SysDict> result = getSchoolSysDictInfo(schoolInfoMaps);
        return new RestResponse<List<SysDict>>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", result);
    }

    private List<SysDict> getSchoolSysDictInfo(List<Map<String, Object>> schoolInfoMaps) {
        List<SysDict> result = new ArrayList<>();
        if (schoolInfoMaps != null && schoolInfoMaps.size() > 0) {
            for (int i = 0; i < schoolInfoMaps.size(); i++) {
                SysDict sysdict = new SysDict();
                sysdict.setTypeCode("SCHOOLLIST");
                sysdict.setSort(Long.valueOf(i));
                sysdict.setLabel((String) schoolInfoMaps.get(i).get("name"));
                sysdict.setValue((String) schoolInfoMaps.get(i).get("id"));
                result.add(sysdict);
            }
        }
        return result;
    }

    /**
     * 获取当前各个学校正在使用的license
     */
    @CrossOrigin
    @PostMapping(value = "/listAll")
    public PageInfo<SysLicense> listAll(int pageNo, int pageSize, SysLicense sysLicense) {
        PageIo<SysLicense> pageInfo = sysLicenseService.loadByPageAll(pageNo, pageSize, sysLicense);
        return pageInfo;
    }

    /**
     * 获取认证列表-模糊搜索
     */
    @CrossOrigin
    @PostMapping(value = "/listConditon")
    public PageInfo<SysLicense> listConditon(int pageNo, int pageSize, SysLicense sysLicense) {
        PageIo<SysLicense> pageInfo = sysLicenseService.loadByPage(pageNo, pageSize, sysLicense);
        return pageInfo;
    }

    /**
     * 获取认证详细信息
     */
    @CrossOrigin
    @GetMapping(value = "/getLicenseInfo")
    public RestResponse<SysLicense> getLicenseInfo() {
        schoolCode = environment.getProperty("clientlicense.schoolCode");
        String schoolName = environment.getProperty("clientlicense.schoolName");
        String PUBLICALIAS = environment.getProperty("clientlicense.PUBLICALIAS");
        String PUBLICSTOREPWD = environment.getProperty("clientlicense.STOREPWD");
        String PUBLICSUBJECT = environment.getProperty("clientlicense.SUBJECT");


        String licPath = uploadFolder + "/licenseKeystoreClient/" + schoolCode + "/license.lic";
        String storePath = uploadFolder + "/licenseKeystoreClient/" + schoolCode + "/publicKeys.store";

        Date expireDate = null;
        try {
            //获取证书信息
            FileInputStream fileInputStream = new FileInputStream(storePath);
            InputStream pubPathIs = new ByteArrayInputStream(readFileBytes(fileInputStream));
            LicenseManager licenseManager = new LicenseManagerHolder()
                    .getLicenseManager(initLicenseParamsClient(PUBLICSTOREPWD, PUBLICALIAS, pubPathIs, PUBLICSUBJECT));
            licenseManager.install(new File(licPath));
            LicenseContent content = licenseManager.verify();
            expireDate = content.getNotAfter();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        SysLicense sysLicense = new SysLicense();
        sysLicense.setSchoolName(schoolName);
        sysLicense.setExpireDate(expireDate);

        return new RestResponse<SysLicense>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", sysLicense);
    }

    private Map<String, Object> getSchoolInfoMap(List<Map<String, Object>> schoolInfoMaps) {
        Map<String, Object> result = new HashMap<>();
        if (schoolInfoMaps != null && schoolInfoMaps.size() > 0) {
            schoolInfoMaps.forEach(obj -> {
                if (!result.containsKey(obj.get("id"))) {
                    result.put((String) obj.get("id"), obj.get("name"));
                }
            });
        }
        return result;
    }

    /**
     * 返回证书信息需要的参数
     */
    private LicenseParam initLicenseParamsClient(String STOREPWD, String PUBLICALIAS, InputStream pubPathIs, String SUBJECT) {
        Preferences preference = Preferences
                .userNodeForPackage(SysLicenseController.class);
        CipherParam cipherParam = new DefaultCipherParam(STOREPWD);
        KeyStoreParam privateStoreParam = new ExtendsKeyStoreParam(
                SysLicenseController.class, pubPathIs, PUBLICALIAS, STOREPWD, null);
        LicenseParam licenseParams = new DefaultLicenseParam(SUBJECT,
                preference, privateStoreParam, cipherParam);
        return licenseParams;
    }

    /**
     * 增加或者更新认证
     */
    @CrossOrigin
    @PostMapping(value = "/save")
    public RestResponse<String> save(@Validated SysLicense sysLicense) {
        try {
            String code = sysLicense.getSchoolName();
            String priStoreId;
            String pubStoreId;

            SysLicense sysbean = new SysLicense();
            sysbean.setSchoolName(code);
            SysLicense keySyslicenseBean = sysLicenseService.getBeanBySchoolName(sysbean);

            if (keySyslicenseBean != null) {
                priStoreId = keySyslicenseBean.getLicensePri();
                pubStoreId = keySyslicenseBean.getLicensePub();
            } else {
                //首先生成密钥
                String storePath = uploadFolder + "/licenseKeystore/" + code;
                judeDirExists(storePath);
                String pubCer = storePath + "/public.cer";
                String pubStore = storePath + "/publicKeys.store";
                String priStore = storePath + "/privateKeys.store";
                genPrivateKeys(priStore);
                Thread.sleep(300);
                exportPubCer(pubCer, priStore);
                Thread.sleep(300);
                exportPubStore(pubCer, pubStore);
                Thread.sleep(300);

                //上传公钥和私钥
                priStoreId = handleFileUpload(priStore);
                pubStoreId = handleFileUpload(pubStore);
            }
            sysLicense.setLicensePri(priStoreId);
            sysLicense.setLicensePub(pubStoreId);

            sysLicenseService.save(sysLicense);
        } catch (Exception e) {
            return new RestResponse<String>(ExceptionResult.SYS_ERROR, "保存失败！", "");
        }
        return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");
    }

    /**
     * 删除认证（逻辑删除）
     */
    @CrossOrigin
    @PostMapping(value = "/delete")
    public RestResponse<String> delete(SysLicense sysLicense) {
        sysLicenseService.delete(sysLicense);
        return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
    }

    /**
     * 获取认证
     */
    @CrossOrigin
    @PostMapping(value = "/get")
    public RestResponse<SysLicense> get(SysLicense sysLicense) {

        List<Map<String, Object>> schoolInfoMaps = sysLicenseService.loadShoolKeyAndValue(null);
        return new RestResponse<SysLicense>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
                sysLicenseService.get(sysLicense));
    }

    /**
     * 获取认证学校信息
     */
    @CrossOrigin
    @PostMapping(value = "/getSchoolList")
    public PageInfo<SysDict> getSchoolList(SysDict sysDict) {
        String label = sysDict.getLabel();
        List<Map<String, Object>> schoolInfoMaps = sysLicenseService.loadShoolKeyAndValue(label);
        List<SysDict> sysDictList = getSchoolSysDictInfo(schoolInfoMaps);
        PageIo<SysDict> pageInfo = new PageIo<SysDict>(sysDictList);
        return pageInfo;

    }


    //    private void setParam(){
//        // 获取参数
//        PRIVATEALIAS = environment.getProperty("serverlicense.PRIVATEALIAS");
//        KEYPWD = environment.getProperty("serverlicense.KEYPWD");
//        STOREPWD = environment.getProperty("serverlicense.STOREPWD");
//        SUBJECT = environment.getProperty("serverlicense.SUBJECT");
//        licPath = environment.getProperty("serverlicense.licPath");
//        priPath = environment.getProperty("serverlicense.priPath");
//
//        issuedTime = environment.getProperty("serverlicense.issuedTime");
//        notBefore = environment.getProperty("serverlicense.notBefore");
//        notAfter = environment.getProperty("serverlicense.notAfter");
//        consumerType = environment.getProperty("serverlicense.consumerType");
//        consumerAmount = Integer.valueOf(environment.getProperty("serverlicense.consumerAmount"));
//        info = environment.getProperty("serverlicense.info");
//        //schoolCode = environment.getProperty("serverlicense.schoolCode");
//    }

    /**
     * 生成证书
     */
    //@CrossOrigin
    //@PostMapping(value = "/create")
//    public RestResponse<String> create(SysLicense sysLicense) {
//        //setParam();
//        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        SysLicense sl = sysLicenseService.get(sysLicense);
//        String licensePri = sl.getLicensePri();
//        notBefore = format.format(sl.getCreateDate());
//        issuedTime = format.format(sl.getCreateDate());
//        notAfter = format.format(sl.getExpireDate());
//        schoolCode = sl.getSchoolName();
//
//        Optional<LicenseFile> fileObj = fileService.getFileById(licensePri);
//        LicenseContent licenseContent;
//        String licPath = uploadFolder + "/licenseKeystore/" + schoolCode + "/license.lic";
//        try {
//            if (fileObj.isPresent()) {
//                Binary content = fileObj.get().getContent();
//                priPath = new ByteArrayInputStream(content.getData());
//            }
//            /************** 证书发布者端执行 ******************/
//            LicenseManager licenseManager = new LicenseManagerHolder()
//                    .getLicenseManager(initLicenseParams0());
//            File file = new File(licPath);
//            if (!file.exists()) {
//                file.createNewFile();
//            }
//            licenseContent = createLicenseContent();
//            licenseManager.store(licenseContent, new File(licPath));
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("客户端证书生成失败!");
//            return new RestResponse<String>(ExceptionResult.SYS_ERROR, "客户端证书生成失败,请检查证书相关参数", "证书生成失败,请检查证书相关参数");
//        }
//        System.out.println("服务器端生成证书成功!");
//        return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "服务器端生成证书成功", "证书生成成功，\n 请在以下目录查看证书：" + licPath);
//    }


    /**
     * 导出证书
     */
    @CrossOrigin
    @PostMapping(value = "/exportLicense")
    public RestResponse<String> exportLicense(SysLicense sysLicense, HttpServletResponse response) {
        //setParam();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SysLicense sl = sysLicenseService.get(sysLicense);
        String licensePri = sl.getLicensePri();
        String licensePub = sl.getLicensePub();
        notBefore = format.format(sl.getCreateDate());
        issuedTime = format.format(sl.getCreateDate());
        notAfter = format.format(sl.getExpireDate());
        schoolCode = sl.getSchoolName();

        if(new Date().compareTo(sl.getExpireDate()) != -1 ){
            return new RestResponse<String>(LicenseExceptionResult.LICENSE_ERROR, "导出失败！", "");
        }

        Optional<LicenseFile> fileObj = fileService.getFileById(licensePri);
        LicenseContent licenseContent;
        String licPath = uploadFolder + "/licenseKeystore/" + schoolCode + "/license.lic";
        try {
            if (fileObj.isPresent()) {
                Binary content = fileObj.get().getContent();
                priPath = new ByteArrayInputStream(content.getData());
            }
            /************** 证书发布者端执行 ******************/
            LicenseManager licenseManager = new LicenseManagerHolder()
                    .getLicenseManager(initLicenseParams0());
            File file = new File(licPath);
            if (!file.exists()) {
                file.createNewFile();
            }
            licenseContent = createLicenseContent();
            licenseManager.store(licenseContent, new File(licPath));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("客户端证书生成失败!");
            return new RestResponse<String>(ExceptionResult.SYS_ERROR, "导出失败！", "");
        }

        //获取从mongdb公钥
        String pubStorePath = uploadFolder + "/licenseKeystore/" + schoolCode;
        Optional<LicenseFile> pubStorefile = fileService.getFileById(licensePub);
        if (pubStorefile.isPresent()) {
            Binary content = pubStorefile.get().getContent();
            String name = pubStorefile.get().getName();
            handleFileUploadLicense(content.getData(), name, pubStorePath);
            pubStorePath = pubStorePath + "/" + name;
        }

        //downLoadZipFile(response,licPath,pubStorePath);
        //Object o = compressedFile(licPath, pubStorePath);
        String zipId = batchDownLoadDatumList(response, new File(licPath), new File(pubStorePath));


        System.out.println("服务器端生成证书成功!");
        return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "导出成功", zipId);
    }


    /**
     * 返回生成证书时需要的参数
     */
    private LicenseParam initLicenseParams0() {
        Preferences preference = Preferences
                .userNodeForPackage(SysLicenseController.class);
        // 设置对证书内容加密的对称密码
        CipherParam cipherParam = new DefaultCipherParam(STOREPWD);
        // 参数1,2从哪个Class.getResource()获得密钥库;参数3密钥库的别名;参数4密钥库存储密码;参数5密钥库密码
//		KeyStoreParam privateStoreParam = new DefaultKeyStoreParam(
//				SysLicenseController.class, priPath, PRIVATEALIAS, STOREPWD, KEYPWD);
        KeyStoreParam privateStoreParam = new ExtendsKeyStoreParam(
                SysLicenseController.class, priPath, PRIVATEALIAS, STOREPWD, KEYPWD);
        LicenseParam licenseParams = new DefaultLicenseParam(SUBJECT,
                preference, privateStoreParam, cipherParam);
        return licenseParams;
    }

    /**
     * 从外部表单拿到证书的内容
     */
    public LicenseContent createLicenseContent() {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        LicenseContent content = null;
        content = new LicenseContent();
        content.setSubject(SUBJECT);
        content.setHolder(DEFAULTHOLDERANDISSUER);
        content.setIssuer(DEFAULTHOLDERANDISSUER);
        try {
            content.setIssued(format.parse(issuedTime));
            content.setNotBefore(format.parse(notBefore));
            content.setNotAfter(format.parse(notAfter));
        } catch (ParseException e) {
            //e.printStackTrace();
        }
        content.setConsumerType(consumerType);
        content.setConsumerAmount(consumerAmount);
        content.setInfo(info);

        // 扩展需要校验的参数
        LicenseCheckModel licenseCheckModel = new LicenseCheckModel();
        licenseCheckModel.setShoolCode(schoolCode);
        licenseCheckModel.setMacAddress(macAddress);
        licenseCheckModel.setCPUSerial(CPUSerial);
        content.setExtra(licenseCheckModel);

        return content;
    }


    /**
     * 返回验证证书需要的参数
     */
    private LicenseParam initLicenseParams(String PUBLICALIAS, String STOREPWD, String SUBJECT, InputStream pubPathIs) {
        Preferences preference = Preferences
                .userNodeForPackage(SysLicenseController.class);
        CipherParam cipherParam = new DefaultCipherParam(STOREPWD);
        KeyStoreParam privateStoreParam = new ExtendsKeyStoreParam(
                SysLicenseController.class, pubPathIs, PUBLICALIAS, STOREPWD, null);
        LicenseParam licenseParams = new DefaultLicenseParam(SUBJECT,
                preference, privateStoreParam, cipherParam);
        return licenseParams;
    }


    /**
     * dos 命令行执行方法
     */
    public void execCommand(String[] arstringCommand) {
        for (int i = 0; i < arstringCommand.length; i++) {
            System.out.print(arstringCommand[i] + " ");
        }
        try {
            Runtime.getRuntime().exec(arstringCommand);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void execCommand(String arstringCommand) {
        try {
            Runtime.getRuntime().exec(arstringCommand);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 生成私钥
     */
    public void genPrivateKeys(String priStore) {
        String[] arstringCommand = new String[]{
                "cmd ", "/k",
                "start", // cmd Shell命令

                "keytool",
                "-genkeypair",
                "-alias",
                "privatekey",
                "-keyalg",
                "DSA",
                "-keysize",
                "1024",
                "-keystore",
                priStore,
                "-validity",
                "36500",
                "-dname",
                "CN=webber,OU=webber,O=webber,L=xian,ST=shanxi,C=china",
                "-storepass",
                STOREPWD,
                "-keypass",
                KEYPWD,
                "-v"
        };
        execCommand(arstringCommand);
    }

    /**
     * 导出证书文件
     */
    public void exportPubCer(String pubCer, String priStore) {

        String[] arstringCommand = new String[]{
                "cmd ", "/k",
                "start",

                "keytool",
                "-export",
                "-alias",
                "privatekey",
                "-file",
                pubCer,
                "-keystore",
                priStore,
                "-storepass",
                STOREPWD
        };
        execCommand(arstringCommand);
    }

    /**
     * 导出公钥文件
     */
    public void exportPubStore(String pubCer, String pubStore) {
        String arstringCommand = "cmd  /k   echo   y   |  keytool -import  -alias  publickey   -file  " + pubCer + "  -keystore  " + pubStore + "  -storepass " + STOREPWD ;
        execCommand(arstringCommand);
    }

    /**
     * 判断文件夹是否存在
     */
    public static void judeDirExists(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            if (file.isDirectory()) {
                System.out.println("dir exists");
            } else {
                System.out.println("the same name file exists, can not create dir");
            }
        } else {
            System.out.println("dir not exists, create it ...");
            file.mkdirs();
        }

    }

    public String handleFileUpload(String filePath) {
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            //获取上传文件基础参数
            String type = "application/octet-stream";
            long size = fileInputStream.available();
            byte[] contentByte = readFileBytes(fileInputStream);
            //转储为mongo对象
            LicenseFile f = new LicenseFile(filePath.substring(filePath.lastIndexOf("/") + 1), type, size, new Binary(contentByte));
            LicenseFile ff = fileService.saveFile(f);
            System.out.println("上传成功");
            return ff.getId();
        } catch (Exception ex) {
            System.out.println("上传失败");
        }
        return "";
    }

    public byte[] readFileBytes(InputStream is) {
        byte[] data = null;
        try {
            if (is.available() == 0) {//严谨起见,一定要加上这个判断,不要返回data[]长度为0的数组指针
            	is.close();
                return data;
            }else {
                data = new byte[is.available()];
                int res = is.read(data);
                is.close();
                return data;
            }
        } catch (IOException e) {
            return data;
        }
    }


    public String batchDownLoadDatumList(HttpServletResponse response, File licfile, File pubfile) {
        try {
            //压缩文件
            List<File> files = new ArrayList<>();
            files.add(licfile);
            files.add(pubfile);

            String fileName = "licenseinfo.zip";
            //在服务器端创建打包下载的临时文件
            String globalUploadPath = uploadFolder + "/licenseZip";
            judeDirExists(globalUploadPath);

            String outFilePath = globalUploadPath + "/"+ fileName;
            File file = new File(outFilePath);
            //文件输出流
            FileOutputStream outStream = new FileOutputStream(file);
            //压缩流
            ZipOutputStream toClient = new ZipOutputStream(outStream);
            //设置压缩文件内的字符编码，不然会变成乱码
            toClient.setEncoding("utf-8");
            ZipUtils.zipFile(files, toClient);
            toClient.close();
            outStream.close();
            //ZipUtils.downloadZip(file, response);
            //ZipUtils.downloadZipUpdate(file, response);
            return handleFileUpload(outFilePath);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    /**
     * byte[] 写到文件
     */
    public void handleFileUploadLicense(byte[] byt, String name, String storePath) {

        String pubStore = storePath + "/" + name;
        try {
            judeDirExists(storePath);
            FileOutputStream fos = new FileOutputStream(pubStore);
            InputStream inputStream = new ByteArrayInputStream(byt);

            byte[] bytes = new byte[1024];
            int byteCount;
            while ((byteCount = inputStream.read(bytes)) != -1) {
                fos.write(bytes, 0, byteCount);
            }
            fos.close();
            inputStream.close();
            System.out.println("pubStore复制成功");
        } catch (Exception ex) {
            System.out.println("复制失败");
        }

    }


}