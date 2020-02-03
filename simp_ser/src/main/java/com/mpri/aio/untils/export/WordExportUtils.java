package com.mpri.aio.untils.export;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.codec.binary.Base64;
import org.bson.types.Binary;

import java.io.*;
import java.util.Map;
import java.net.HttpURLConnection;
import java.net.URL;

public class WordExportUtils {

    /**
     * @param dataMap
     *            word中需要展示的动态数据，用map集合来保存
     * @param templateName
     *            word模板名称，例如：teample.ftl
     * @param filePath
     *            文件生成的目标路径，例如：D:/
     * @param fileName
     *            生成的文件名称
     */
    public static void createWord(Map dataMap, String templateName,
                                  String filePath, String fileName) {
        try {
            // 创建配置实例
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);;

            // 设置编码
            configuration.setDefaultEncoding("UTF-8");

            // ftl模板文件
            configuration.setClassForTemplateLoading(WordExportUtils.class,
                    "/templates/");

            // 获取模板
            Template template = configuration.getTemplate(templateName);

            // 输出文件
            File outFile = new File(filePath + File.separator + fileName+".doc");

            // 如果输出目标文件夹不存在，则创建
            if (!outFile.getParentFile().exists()) {
                outFile.getParentFile().mkdirs();
            }

            // 将模板和数据模型合并生成文件
            Writer out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(outFile), "UTF-8"));

            // 生成文件
            template.process(dataMap, out);

            // 关闭流
            out.flush();
            out.close();

        } catch (Exception e) {
//            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }


    /**
     * 将图片转换为BASE64为字符串
     * @param binary
     * @return
     * @throws IOException
     */
    public static String getImageStringByByte(Binary binary) {
        byte[] data = binary.getData();
        return data != null ? new String(Base64.encodeBase64(data)) : "";
    }

    public static String getImageBase(String src) {
        if (src == null || src == "") {
            return "";
        }
        File file = new File(src);
        if (!file.exists()) {
            return "";
        }
        FileInputStream in = null;
        byte[] data = null;
        try {
            in = new FileInputStream(file);
            if(in.available() > 0) {
                data = new byte[in.available()];
                int res = in.read(data);
            }
    		in.close();
        } catch (IOException e) {
//            e.printStackTrace();
        }finally {
		}
        return data != null ? new String(Base64.encodeBase64(data)) : "";
    }

}
