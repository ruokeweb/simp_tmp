package com.mpri.aio.back.utils;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongodbExport {
    public void mongoDbQueryAll(){
        //1.创建链接
        MongoClient client = new MongoClient(new MongoClientURI("mongodb://smmprw:Smmprw123.@192.168.120.31:27017/?authSource=smmp&ssh=true"));
        //2.打开数据库smmp
        MongoDatabase db = client.getDatabase("smmp");
        //3.获取集合
        MongoCollection<Document> collection = db.getCollection("file");
        //4.查询获取文档集合
        FindIterable<Document> documents = collection.find();
        //5.循环遍历
        for (Document document : documents) {
            System.out.println(document);
        }
        //6.关闭连接
        client.close();
    }
}
