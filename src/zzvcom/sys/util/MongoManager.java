package zzvcom.sys.util;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.MongoException;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

public class MongoManager {
    public static MongoClient mongoClient = null;

/*	public static void init() {
	    ReadProperties readProperties = new ReadProperties();
	    //配置mongodb内置连接池参数
        int port = Integer.valueOf(readProperties.readPropertiesInfo("port"));
        int socketTimeout = Integer.valueOf(readProperties.readPropertiesInfo("socketTimeout"));
        int connectionsPerHost = Integer.valueOf(readProperties.readPropertiesInfo("connectionsPerHost"));
        int connectTimeout = Integer.valueOf(readProperties.readPropertiesInfo("connectTimeout"));
        String docIp = readProperties.readPropertiesInfo("docIp");
        int maxWaitTime = Integer.valueOf(readProperties.readPropertiesInfo("maxWaitTime"));
        MongoOptions mo = new MongoOptions();
        mo.socketTimeout = socketTimeout;
        mo.connectionsPerHost = connectionsPerHost;
        mo.connectTimeout = connectTimeout;
        mo.maxWaitTime = maxWaitTime;
        try {
            mongo = new Mongo(new ServerAddress(docIp, port), mo);
        }  catch (MongoException e) {
            System.err.println("Connection refused!");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Connection refused!");
            e.printStackTrace();
        }
	}*/
	public static void init() {
	    ReadProperties readProperties = new ReadProperties();
	    //配置mongodb内置连接池参数
	    String host = readProperties.readPropertiesInfo("docIp");
        int port = Integer.valueOf(readProperties.readPropertiesInfo("port"));
        String user = readProperties.readPropertiesInfo("username");
        String pwd = readProperties.readPropertiesInfo("password");
        String database = readProperties.readPropertiesInfo("database");
        int socketTimeout = Integer.valueOf(readProperties.readPropertiesInfo("socketTimeout"));
        int connectionsPerHost = Integer.valueOf(readProperties.readPropertiesInfo("connectionsPerHost"));
        int connectTimeout = Integer.valueOf(readProperties.readPropertiesInfo("connectTimeout"));        
        int maxWaitTime = Integer.valueOf(readProperties.readPropertiesInfo("maxWaitTime"));
        
        try {

        	MongoClientOptions options = new MongoClientOptions.Builder().connectTimeout(socketTimeout).connectionsPerHost(connectionsPerHost).connectTimeout(connectTimeout).maxWaitTime(maxWaitTime).build();
			MongoCredential credential = MongoCredential.createCredential(user, database, pwd.toCharArray());
			List<MongoCredential> credentiallist = new ArrayList<MongoCredential>() ;
			credentiallist.add(credential);
			ServerAddress address = new ServerAddress(host, port);
			
	
			mongoClient = new MongoClient(address,credentiallist,options);
        	System.out.println(mongoClient);
        	
        }  catch (MongoException e) {
            System.err.println("Connection refused!");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Connection refused!");
            e.printStackTrace();
        }
	}
	
	/**
     * @description：获取评价数据库auxsys
     * @time： 2015-11-4
     * @author：donghaoyu
     * @return 
     */
    public  static MongoDatabase getDb() {
        if (mongoClient == null) {
            System.err.println("The connection has been closed. Reconnection!");
            init();
        }
        MongoDatabase auxsysDb = mongoClient.getDatabase("auxsys");
        return auxsysDb;
    }
}
