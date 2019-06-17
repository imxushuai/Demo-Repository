package com.imxushuai.mongodb.nativeapi;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import jdk.nashorn.internal.runtime.regexp.RegExpMatcher;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import lombok.extern.slf4j.Slf4j;
import org.bson.BSON;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.regex.Pattern;

/**
 * MongoDB 原生JAVA API
 */
@Slf4j
public class NativeMongoDB {

    private MongoClient client;

    private final String HOST = "192.168.136.104";
    private final int PORT = 27017;

    private MongoDatabase mongoDatabase;

    @Before
    public void connectMongoDB() {
        try {
            // 创建Java Client，如果端口是27017可以省略第二个参数
            client = new MongoClient(HOST, PORT);
            // 获取数据库，这个数据库是我提前在mongodb中创建好的
            mongoDatabase = client.getDatabase("imxushuai");
            log.info("Connect to mongoDB successfully, connect database name is [{}]", mongoDatabase.getName());
        } catch (Exception e) {
            log.error("Connect to mongoDB failure", e);
        }
    }

    @After
    public void close() {
        if (client != null) {
            client.close();
        }
    }

    @Test
    public void createCollection() {
        // 创建Collection
        mongoDatabase.createCollection("imxushuai collection 1");
        mongoDatabase.createCollection("imxushuai collection 2");
    }

    @Test
    public void createDocument() {
        // 获取集合
        MongoCollection<Document> collection = mongoDatabase.getCollection("imxushuai collection 1");
        /*
         * 创建文档
         *  方式1：document.append(String key, Object value);
         *  方式2：new Document(Map<String, Object> map);
         */
        Document document = new Document();
        document.append("name", "jerry")
                .append("age", "20")
                .append("address", "US");
        // 插入多个, 插入单个 collection.insertOne(document);
        collection.insertMany(Collections.singletonList(document));
    }

    @Test
    public void findAllByDocument() {
        MongoCollection<Document> collection = mongoDatabase.getCollection("imxushuai collection 1");
        FindIterable<Document> documents = collection.find();
        for (Document document : documents) {
            System.out.println(document.toString());
        }
    }

    @Test
    public void findByParams() {
        MongoCollection<Document> collection = mongoDatabase.getCollection("imxushuai collection 1");
        // 构建查询条件按
        BasicDBObject params = new BasicDBObject("name", "imxushuai");
        // 查询
        FindIterable<Document> documents = collection.find(params);
        for (Document document : documents) {
            System.out.println(document.toString());
        }
    }

    @Test
    public void update() {
        MongoCollection<Document> collection = mongoDatabase.getCollection("imxushuai collection 1");
        // 修改age为25的地址为 China ChengDu
        collection.updateMany(
                Filters.eq("age", "25"),
                new Document("$set", new Document("address", "China ChengDu"))
        );
    }

    @Test
    public void delete() {
        MongoCollection<Document> collection = mongoDatabase.getCollection("imxushuai collection 1");
        // 删除所有符合条件的文档
        // 我这里删除address中的值包含China的数据,为了测试我又新增了一条数据
        collection.deleteMany(Filters.regex("address", Pattern.compile("^.*China.*$", Pattern.CASE_INSENSITIVE)));
    }


}
