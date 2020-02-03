package com.mpri.aio.untils.export;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.handler.inter.IExcelExportServer;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @Auther: daihongbo
 * @Description:
 */
public class ExcelExportUtils {

    private static Logger logger = LoggerFactory.getLogger(ExcelExportUtils.class);
    /**
     * 功能描述：复杂导出Excel，包括文件名以及表名,不创建表头
     * @param list 导出的实体类
     * @param title 表头名称
     * @param sheetName sheet表名
     * @param pojoClass 映射的实体类
     * @param filePath 文件下载路径
     * @return
     */
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String filePath) {
        Boolean flag = ExcelExportUtils.createDirectory(filePath.substring(0,filePath.lastIndexOf("/")));
        System.out.println(flag?"创建目录成功":"该目录已存在");
        ExportParams exportParams = new ExportParams(title, sheetName, ExcelType.XSSF);
        exportParams.setStyle(ExcelStyleUtil.class);
        exportParams.setType(ExcelType.XSSF);
        defaultBigExport(list, pojoClass, filePath, exportParams);
//        ExportParams exportParams = new ExportParams(title, sheetName, ExcelType.HSSF);
//        exportParams.setStyle(ExcelStyleUtil.class);
//        defaultExport(list, pojoClass, filePath, exportParams);
    }

    /**
     * 功能描述：Map 集合导出
     * @param list 实体集合
     * @param fileName 导出的文件名称
     * @return
     */
    public static void exportExcel(List<Map<String, Object>> list, String fileName, String filePath) {
        defaultExport(list, fileName, filePath);
    }

    /**
     * 功能描述：默认导出方法
     * @param list 导出的实体集合
     * @param pojoClass pojo实体
     * @param exportParams ExportParams封装实体
     * @return
     */
    public static void defaultExport(List<?> list, Class<?> pojoClass,String filePath, ExportParams exportParams) {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
        if (workbook != null) {
            downLoadExcel(filePath, workbook);
        }
    }
    /**
     * 大数据量导出
     */
    public static void exportBigExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String filePath) {
        Boolean flag = ExcelExportUtils.createDirectory(filePath.substring(0,filePath.lastIndexOf("/")));
        System.out.println(flag?"创建目录成功":"该目录已存在");
        ExportParams exportParams = new ExportParams(title, sheetName, ExcelType.XSSF);
        exportParams.setType(ExcelType.XSSF);
        exportParams.setStyle(ExcelStyleUtil.class);
        defaultBigExport(list, pojoClass, filePath, exportParams);
    }

    /**
     * 大数据量导出
     * @param list
     * @param pojoClass
     * @param filePath
     * @param exportParams
     */
    public static void defaultBigExport(List<?> list, Class<?> pojoClass,String filePath, ExportParams exportParams) {
        Workbook workbook = ExcelExportUtil.exportBigExcel(exportParams, pojoClass, new IExcelExportServer() {
            @Override
            public List<Object> selectListForExcelExport(Object o, int i) {
                if(i<=list.size()){
                    return (List<Object>) list.get(i-1);
                }
                return null;
            }
        },"");
        if (workbook != null) {
            downLoadExcel(filePath, workbook);
        }
    }

    /**
     * 功能描述：Excel导出
     * @param workbook Excel对象
     * @return
     */
    private static void downLoadExcel(String filePath, Workbook workbook) {
        try {
            FileOutputStream output=new FileOutputStream(filePath);
            workbook.write(output);
        } catch (IOException e) {
            throw new  RuntimeException(e);
        }
    }

    /**
     * 功能描述：默认导出方法
     * @param list 导出的实体集合
     * @param fileName 导出的文件名
     * @return
     */
    private static void defaultExport(List<Map<String, Object>> list, String fileName, String filePath) {
        Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);
        if (workbook != null) ;
        downLoadExcel(filePath, workbook);
    }


    /**
     * 功能描述：根据文件路径来导入Excel
     * @param filePath 文件路径
     * @param titleRows 表标题的行数
     * @param headerRows 表头行数
     * @param pojoClass Excel实体类
     * @return
     */
    public static <T> List<T> importExcel(String filePath, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
        //判断文件是否存在
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(new File(filePath), pojoClass, params);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("模板不能为空");
        } catch (Exception e) {
           // e.printStackTrace();

        }
        return list;
    }

    /**
     * 功能描述：根据接收的Excel文件来导入Excel,并封装成实体类
     * @param file 上传的文件
     * @param titleRows 表标题的行数
     * @param headerRows 表头行数
     * @param pojoClass Excel实体类
     * @return
     */
    public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
        if (file == null) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("excel文件不能为空");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());

        }
        return list;
    }

    public static boolean createDirectory(String descDirName) {
        String descDirNames = descDirName;
        if (!descDirName.endsWith(File.separator)) {
            descDirNames = descDirName + File.separator;
        }

        File descDir = new File(descDirNames);
        if (descDir.exists()) {
            logger.debug("目录 " + descDirNames + " 已存在!");
            return false;
        } else if (descDir.mkdirs()) {
            logger.debug("目录 " + descDirNames + " 创建成功!");
            return true;
        } else {
            logger.debug("目录 " + descDirNames + " 创建失败!");
            return false;
        }
    }

}

