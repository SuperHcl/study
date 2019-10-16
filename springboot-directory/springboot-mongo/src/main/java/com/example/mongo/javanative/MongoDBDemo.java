package com.example.mongo.javanative;

import com.mongodb.BasicDBObject;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author: Hucl
 * @date: 2019/10/14 11:10
 * @description: 原始的mongodb 的java客户端
 */
public class MongoDBDemo {


    private MongoClient client;

    @Before
    public void init() {
        client = MongoClients.create("mongodb://47.94.98.106:27017");
    }

    @Test
    public void connectionDB() {
        MongoDatabase database = client.getDatabase("hcl");
        System.out.println("connect successful---"+ database.getName());
    }

    @Test
    public void createConnection() {
        MongoDatabase database = client.getDatabase("hcl");
        database.createCollection("Mongo_test");
        System.out.println("创建集合成功");
    }

    @Test
    public void getConnection() {
        MongoDatabase database = client.getDatabase("hcl");
        MongoCollection<Document> hcl = database.getCollection("mycollection");
        System.out.println("获取集合成功 "+hcl.getNamespace());
        MongoIterable<String> collectionNames = database.listCollectionNames();
        for (String collectionName : collectionNames) {
            System.out.println("collection name : " + collectionName);
        }
    }

    @Test
    public void insertDocument() {
        MongoDatabase database = client.getDatabase("test");
        MongoCollection<Document> mycollection = database.getCollection("mycollection");
        Document document = new Document("name", "James");
        document.append("age", 34);
        document.append("sex", "男");

        Document document1 = new Document("name", "Wade");
        document1.append("age", 36);
        document1.append("sex", "男");

        Document document2 = new Document("name", "cp3");
        document1.append("age", 32);
        document1.append("sex", "女");

        List<Document> documents = new ArrayList<>();
        documents.add(document);
        documents.add(document1);
        documents.add(document2);

        mycollection.insertMany(documents);
        System.out.println("文档插入成功");
    }

    @Test
    public void findDocuments() {
        MongoDatabase database = client.getDatabase("hcl");
        MongoCollection<Document> collection = database.getCollection("mycollection");

        FindIterable<Document> documents = collection.find();
        for (Document document : documents) {
            System.out.println("---for循环遍历documents---");
            System.out.println(document.toJson() + "\t");
        }


        MongoCursor<Document> iterator = documents.iterator();
        while (iterator.hasNext()) {
            Document next = iterator.next();
            System.out.println("---iterator循环遍历documents---");
            System.out.println(next.toJson());
        }
    }


    @Test
    public void updateDocuments() {
        MongoDatabase database = client.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("mycollection");
        collection.updateMany(Filters.eq("age", 36), new Document("age", 20));

        FindIterable<Document> documents = collection.find();
        for (Document document : documents) {
            System.out.println(document.toJson());
        }
    }

    @Test
    public void deleteDocuments() {
        MongoDatabase database = client.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("mycollection");
        // 删除符合条件的第一个文档
        collection.deleteOne(Filters.eq("age", 20));
        collection.deleteOne(Filters.eq("name", "James"));
        // 删除符合条件的所有文档
        collection.deleteMany(Filters.eq("sex", "男"));

        MongoCursor<Document> iterator = collection.find().iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next().toJson());
        }

    }

    @Test
    public void testQuery1() {
        MongoDatabase database = client.getDatabase("test");
        MongoCollection<Document> mycollection = database.getCollection("mycollection");
        Document first = mycollection.find().first();
        System.out.println(first.get("name"));

        Document qdoc = new Document();
        qdoc.append("_id", 0);  // 0是不包含字段
        qdoc.append("name", 1); // 1是包含字段

        FindIterable<Document> projection = mycollection.find().projection(qdoc);
        for (Document document : projection) {
            System.out.println(document.toJson());
        }
    }

    @Test
    public void testQuery2() {
        MongoDatabase database = client.getDatabase("test");
        MongoCollection<Document> myCollection = database.getCollection("myCollection");

        Document document = new Document();
        document.append("age", -1);
        FindIterable<Document> documents = myCollection.find().sort(document);
        for (Document document1 : documents) {
            System.out.println(document1.toJson());
        }
    }

    @Test
    public void testFilters1() {
        MongoDatabase database = client.getDatabase("hcl");

        MongoCollection<Document> mycollection = database.getCollection("mycollection");
        // 获取name=zhaoyun的document
        FindIterable<Document> documents = mycollection.find(Filters.eq("name", "zhaoyun"));
        for (Document document : documents) {
            System.out.println(document.toJson());
        }
    }

    @Test
    public void testFilters2() {
        MongoDatabase database = client.getDatabase("test");

        MongoCollection<Document> collection = database.getCollection("myCollection");

        // 获取age大于3的记录 或者 name=zhaoyun
        FindIterable<Document> documents = collection.find(Filters.or(Filters.eq("name", "zhaoyun"),
                Filters.gt("age", 3)));

        for (Document document : documents) {
            System.out.println(document.toJson());
        }
    }


    /**
     * 模糊查询
     */
    @Test
    public void testLike() {
        MongoDatabase database = client.getDatabase("hcl");
        MongoCollection<Document> collection = database.getCollection("mycollection");
        // 利用正则表达式，模糊匹配 包含zhaoyun
        Pattern compile = Pattern.compile("^.*zhaoyun.*$", Pattern.CASE_INSENSITIVE);

        // 利用BasicDBObject可以实现模糊查询和分页查询
        BasicDBObject query = new BasicDBObject();
        query.put("name", compile);
        FindIterable<Document> documents = collection.find(query);
        for (Document document : documents) {
            System.out.println(document.toJson());
        }
    }

    /**
     * 分页查询
     */
    @Test
    public void testPage() {
        MongoDatabase database = client.getDatabase("hcl");

        MongoCollection<Document> myCollection = database.getCollection("mycollection");
        //排序
        BasicDBObject sort = new BasicDBObject();
        //按年龄倒序
        sort.put("age", -1);
        //获得总条数
        System.out.println("共计："+myCollection.countDocuments()+"条记录");

        FindIterable<Document> documents = myCollection.find().sort(sort).skip(0).limit(3);
        for (Document document : documents) {
            System.out.println(document.toJson());
        }
    }


}
