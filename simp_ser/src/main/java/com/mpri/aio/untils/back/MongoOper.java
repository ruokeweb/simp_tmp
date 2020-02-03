package com.mpri.aio.untils.back;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * mongodb备份与还原
 * @author Cary
 * @date 2019年4月8日
 */
@Component
public class MongoOper {
	//备份文件基础路径
	@Value("${back.backpath}")
	private String BACK_PATH;
	
	//备份库名
	@Value("${back.mongo.dbname}")
	private String DB_NAME;
	
	//备份用户名
	@Value("${back.mongo.dbuser}")
	private String DB_USER;

	//备份密码
	@Value("${back.mongo.dbpass}")
	private String DB_PASS;
	
	//db 地址
	@Value("${back.mongo.dburl}")
	private String DB_URL;
	
	//db 地址
	@Value("${back.mongo.mdpath}")
	private String MD_PATH;
	
	//db 地址
	@Value("${back.mongo.mspath}")
	private String MS_PATH;
	
	
	/** 
     * 备份数据库
     * 
     * @param savePath
     * @throws Exception
     */
    public int dbBackup(String id) throws Exception {
        Runtime runtime = Runtime.getRuntime();
        Process process= runtime.exec(MD_PATH+" -h "+DB_URL+" -d "+DB_NAME+" -u "+DB_USER+" -p "+DB_PASS +" -o "+BACK_PATH+"/"+id);
        int i= process.waitFor();
        //mongodump -h 127.0.0.1:27017 -d smmp -u smmprw -p Smmprw123.  -o /opt/backup/11111
//        //win下的日志输出。linux不起效
//        System.out.println("执行代码："+i +"命令：" +MD_PATH+" -h "+DB_URL+" -d "+DB_NAME+" -u "+DB_USER+" -p "+DB_PASS +" -o "+BACK_PATH+"/"+id);
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
        Process process = runtime.exec(MS_PATH+" -h "+DB_URL+" -d "+DB_NAME+" -u "+DB_USER+" -p "+DB_PASS +" "+BACK_PATH+id+"/"+DB_NAME);
        int i= process.waitFor();
        
//        //win下的日志输出。linux不起效
//        System.out.println("执行代码："+i +"命令：" +"C:\\Program Files\\MongoDB\\Server\\4.0\\bin\\mongorestore -h "+DB_URL+" -d "+DB_NAME+" -u "+DB_USER+" -p "+DB_PASS +" "+BACK_PATH+id+"/"+DB_NAME);
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
}
