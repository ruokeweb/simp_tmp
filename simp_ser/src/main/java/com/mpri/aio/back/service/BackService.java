package com.mpri.aio.back.service;

import com.mpri.aio.back.model.MySqlInfo;
import com.mpri.aio.back.utils.MongodbExport;
import com.mpri.aio.back.utils.MysqlExport;
import org.springframework.stereotype.Service;


@Service
public class BackService {
    public void back() {
//         String jdbcUrl="jdbc:mysql://192.168.120.31:3306/smmp_dev?nullCatalogMeansCurrent=true&useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&serverTimezone=Hongkong&useSSL=false";
//         String user="root";
//         String password="Root123.";
//        MySqlInfo mySqlInfo = new MySqlInfo(jdbcUrl,user,password);
//        MysqlExport mysqlExport = new MysqlExport(mySqlInfo);
//        try {
//            mysqlExport.export();
//        } catch ( Exception e) {
//            System.err.println(e);
//        }
        /*String jdbcUrl="jdbc:mysql://192.168.120.31:3306/smmp_dev?nullCatalogMeansCurrent=true&useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&serverTimezone=Hongkong&useSSL=false";
        String user="root";
        String password="Root123.";
        MySqlInfo mySqlInfo = new MySqlInfo(jdbcUrl,user,password);
        MysqlExport mysqlExport = new MysqlExport(mySqlInfo);
        mysqlExport.runSqlFile();*/
        MongodbExport mongodbExport = new MongodbExport();
        mongodbExport.mongoDbQueryAll();
    }
}
