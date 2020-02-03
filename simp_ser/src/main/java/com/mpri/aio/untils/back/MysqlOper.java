package com.mpri.aio.untils.back;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

/**
 * mysql备份与还原
 * @author Cary
 * @date 2019年4月8日
 */
@Component
public class MysqlOper {
	//备份文件基础路径
	@Value("${back.backpath}")
	private String BACK_PATH;
	
	//mysqldump的执行路径
	@Value("${back.mysql.mdpath}")
	private String MD_PATH;
	
	//mysql的执行路径
	@Value("${back.mysql.mypath}")
	private String MY_PATH;

	//备份库名
	@Value("${back.mysql.backbase}")
	private String BACK_BASE;
	
	/** 
     * 备份数据库
     * 
     * @param savePath
     * @throws Exception
     */
    public int dbBackup(String id) throws Exception {
        Runtime runtime = Runtime.getRuntime();
        Process process= runtime.exec(MD_PATH+" --defaults-extra-file="+getConfigPath()+" --add-drop-database --skip-extended-insert --single-transaction -B "+ BACK_BASE + " --result-file="+BACK_PATH+"/"+id+"/"+id+".sql");
        int i= process.waitFor();
        
        //win下的日志输出。linux不起效
//       System.out.println("执行代码："+i +"命令：" +MD_PATH+" --defaults-extra-file="+getConfigPath()+" --add-drop-database --skip-extended-insert --single-transaction -B "+ BACK_BASE + " --result-file="+BACK_PATH+"/"+id+"/"+id+".sql");
//        FileInputStream errorStream = (FileInputStream)process.getErrorStream();
//        InputStreamReader isr = new InputStreamReader(errorStream,"gbk");//读取
//        System.out.println(isr.getEncoding());
//        BufferedReader bufr = new BufferedReader(isr);//缓冲
//        String line = null;
//        while((line =bufr.readLine())!=null) {
//            System.out.println("错误："+line);
//        }
//        isr.close();
        // 直接命令
        // /usr/bin/mysqldump --defaults-extra-file=/opt/backup/mysql.cnf --skip-extended-insert  --single-transaction smmp>/opt/backup/123.sql
        // mysqldump --defaults-extra-file=d:/my.cnf  --skip-extended-insert  --single-transaction smmp > d:/bak_test.txt
        
        return i;

    }

  
    /**
     * 执行sql文件
     * 
     * @param savePath
     * @throws Exception
     */
    public int dbRecover(String id) throws Exception {
        // 获取操作数据库的相关属性

        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec(MY_PATH+" --defaults-extra-file="+getConfigPath()+" --execute=\"source "+BACK_PATH+"/"+id+"/"+id+".sql\"");
        int i= process.waitFor();
        
//        //win下的日志输出。linux不起效
//        System.out.println("执行代码："+i +"命令：" +MY_PATH+" --defaults-extra-file="+CONFIG_PATH+" --execute=\"source "+BACK_PATH+id+".sql\"");
//        FileInputStream errorStream = (FileInputStream)process.getErrorStream();
//        InputStreamReader isr = new InputStreamReader(errorStream,"gbk");//读取
//        System.out.println(isr.getEncoding());
//        BufferedReader bufr = new BufferedReader(isr);//缓冲
//        String line = null;
//        while((line =bufr.readLine())!=null) {
//            System.out.println("错误："+line);
//        }
//        isr.close();

        return i;
    }

	public static String getConfigPath() throws IOException {

		ClassPathResource resource = new ClassPathResource("mysql.cnf");
		File file=resource.getFile();
		String configPath=file.getAbsolutePath();
		
		return configPath;

	}   
	

}
