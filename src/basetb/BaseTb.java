package basetb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.bson.Document;

import zzvcom.sys.util.MongoManager;
import zzvcom.sys.util.ReadProperties;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.ReadPreference;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

/**
 * @description： 资源评价oracle数据库数据向mongodb数据库同步
 * @time： 2015-12-7
 * @author： donghaoyu
 */
public class BaseTb {
    private Connection connection;
    private MongoCollection<Document> commentsCollection;
    private MongoCollection<Document> appCollection;
    private MongoCollection<Document> voteCollection;
    private MongoCollection<Document> voteResultCollection;
    
    /*
     * 1、链接oracle数据库、mongodb数据库
     * 2、组织mongodb数据库数据幷写入
     */
    public static void main(String[] args){
        BaseTb baseTb = new BaseTb();
        baseTb.appBaseTb();
        baseTb.commentsBaseTb();
        //baseTb.voteBaseTb();
    }
    
    /**
     * @description：评论数据同步
     * @time： 2015-12-7
     * @author：donghaoyu 
     */
    public void commentsBaseTb(){
        //配置数据库连接
        Connection connection = this.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            String sql = 
                "select t.moduleid,\n" +
                "       t.code,\n" + 
                "       (select name from dirtree where code = t.code and  rownum=1)codename,\n" +  
                "       t.infoid,\n" + 
                "       t.infotitle,\n" + 
                "       t.userid,\n" + 
                "       t.username,\n" + 
                "       t.userip,\n" + 
                "       t.area,\n" + 
                "       t.content,\n" + 
                "       t.postdate,\n" + 
                "       t.remark,\n" + 
                "       t.bk1,\n" + 
                "       t.result,\n" + 
                "       t.autocheck\n" + 
                "  from comments t\n" + 
                " where moduleid in\n" + 
                "       (select distinct t.moduleid\n" + 
                "          from comments t\n" + 
                "         where t.postdate >= to_date('20120101', 'yyyy/MM/dd'))\n" + 
                "   and postdate >= to_date('20120101', 'yyyy/MM/dd')";
            resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                Document documents = new Document();
                String appid = resultSet.getString("MODULEID");
                documents.put("appid", appid);
                
                String dircode = resultSet.getString("CODE");
                documents.put("dircode", dircode);
                
                String dirname = resultSet.getString("codename");
                documents.put("dirname", dirname);
                
                String infoid = resultSet.getString("INFOID");
                documents.put("infoid", infoid);
                
                String infotitle = resultSet.getString("INFOTITLE");
                documents.put("infotitle", infotitle);
                
                String userid = resultSet.getString("USERID");
                documents.put("userid", userid);
                
                String username = resultSet.getString("USERNAME");
                documents.put("username", username);
                
                String area = resultSet.getString("AREA");
                documents.put("area", area);
                
                String ip = resultSet.getString("USERIP");
                documents.put("ip", ip);
                
                String content = resultSet.getString("CONTENT");
                documents.put("content", content);
                
                String createtime = resultSet.getString("POSTDATE");
                documents.put("createtime", createtime);
                
                String status = resultSet.getString("AUTOCHECK");
                documents.put("status", status);
                
                String point = resultSet.getString("RESULT");
                documents.put("point", point);
                
                String remark1 = resultSet.getString("REMARK");
                documents.put("remark1", remark1);
                
                String remark2 = resultSet.getString("BK1");
                documents.put("remark2", remark2);
                this.getCommentsCollection().insertOne(documents);
            }
            
            resultSet.close();//数据库先开后关
            statement.close();
            connection.close();//关闭数据库
            System.out.println("*****************评论老数据导入完成********************");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * @description：应用数据同步
     * @time： 2015-12-7
     * @author：donghaoyu 
     */
    public  void appBaseTb(){
        //获取数据库表数据
        Connection connection = this.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            String sql = 
                "select id, name, remark\n" +
                "  from systemmodel t\n" + 
                " where id in (select distinct moduleid\n" + 
                "                from comments\n" + 
                "               where moduleid in\n" + 
                "                     (select distinct t.moduleid\n" + 
                "                        from comments t\n" + 
                "                       where t.postdate >= to_date('20120101', 'yyyy/MM/dd'))\n" + 
                "                 and postdate >= to_date('20120101', 'yyyy/MM/dd'))";
            resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                Document documents = new Document();
                //判断该应用id是否应经存在，若存在则跳过，否则创建
                String id = resultSet.getString("ID");
                if(!this.appSfcz(id)){
                    documents.put("id", id);
                    
                    String name = resultSet.getString("NAME");
                    documents.put("name", name);
                    
                    String remark = resultSet.getString("REMARK");
                    documents.put("remark", remark);
                    this.getAppCollection().insertOne(documents);
                }
            }
            resultSet.close();//数据库先开后关
            statement.close();
            connection.close();//关闭数据库
            System.out.println("**********************应用老数据导入完成*****************");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * @description：判断应用id是否存在
     * @time： 2015-12-7
     * @author：donghaoyu
     * @param appid
     * @return 
     */
    public boolean appSfcz(String appid){
        boolean sfcz = true;
        Document doc = new Document();
        doc.put("id", appid);
        MongoCursor<Document> mongoCursor = this.getAppCollection().find(doc).iterator();
        if(!mongoCursor.hasNext()){
            sfcz = false;
        }
        return sfcz;
    }
    
    /**
     * @description：投票数据同步
     * @time： 2015-12-7
     * @author：donghaoyu 
     */
    public void voteBaseTb(){
      //配置数据库连接
        Connection connection = this.getConnection();
        //获取数据库表数据
        Statement statement = null;
        ResultSet resultSet_vote = null;
        ResultSet resultSet_result = null;
        ResultSet resultSet_option = null;
        try {
            statement = connection.createStatement();
            
            String sql_vote = "";
            resultSet_vote = statement.executeQuery(sql_vote);
            while(resultSet_vote.next()){
                Document documents = new Document();
                documents.put("title", resultSet_vote.getString(0));
                documents.put("createtime", resultSet_vote.getString(1));
                documents.put("status", resultSet_vote.getString(2));
                documents.put("remark", resultSet_vote.getString(0));
                String optionSql = "";
                resultSet_option = statement.executeQuery(optionSql);
                Document options = new Document();
                while(resultSet_option.next()){
                    documents.put("optionid", resultSet_option.getString(1));
                    documents.put("optionname", resultSet_option.getString(2));
                    documents.put("optionorder", resultSet_option.getString(0));
                    documents.put("optionremark", resultSet_option.getString(1));
                    documents.put("results", resultSet_option.getString(2));
                }
                documents.put("options", options);
                this.voteCollection.insertOne(documents);
            }
            
            String sql_result = "";
            resultSet_result = statement.executeQuery(sql_result);
            while(resultSet_result.next()){
                Document documents = new Document();
                documents.put("appid", resultSet_result.getString(0));
                documents.put("dircode", resultSet_result.getString(1));
                documents.put("dirname", resultSet_result.getString(2));
                documents.put("infoid", resultSet_result.getString(0));
                documents.put("infotilte", resultSet_result.getString(0));
                documents.put("area", resultSet_result.getString(1));
                documents.put("updatetime", resultSet_result.getString(2));
                documents.put("voteid", resultSet_result.getString(0));
                String optionSql = "";
                resultSet_option = statement.executeQuery(optionSql);
                Document options = new Document();
                while(resultSet_option.next()){
                    documents.put("optionid", resultSet_option.getString(1));
                    documents.put("optionname", resultSet_option.getString(2));
                    documents.put("optionorder", resultSet_option.getString(0));
                    documents.put("optionremark", resultSet_option.getString(1));
                    documents.put("results", resultSet_option.getString(2));
                }
                documents.put("options", options);
                this.voteResultCollection.insertOne(documents);
            }
            resultSet_vote.close();
            resultSet_result.close();
            resultSet_option.close();
            statement.close();
            connection.close();//关闭数据库
            System.out.println("*******************投票老数据导入完成******************");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public Connection getConnection(){
      //定义一个连接对象
      Connection conn=null;
      //定义连接数据库的URL资源
      ReadProperties readProperties = new ReadProperties();
      String url = readProperties.readPropertiesInfo("jdbc_url");
      //String url="jdbc:oracle:thin:@58.22.107.16:1521:youjiao";
      //定义连接数据库的用户名称与密码
      String username=readProperties.readPropertiesInfo("username");
      String password=readProperties.readPropertiesInfo("password");
      //加载数据库连接驱动
      String className="oracle.jdbc.driver.OracleDriver";
      try {
          Class.forName(className);
      } catch (ClassNotFoundException e) {
          e.printStackTrace();
      }
      //获取数据库的连接对象
      try {
          conn=DriverManager.getConnection(url,username,password);
          System.out.println("数据库连接建立成功...");
      } catch (SQLException e) {
          e.printStackTrace();
      }
      //返回连接对象
      return conn;
    }
    public void setConnection(Connection connection) {
        this.connection = connection;
    } 

    public MongoCollection<Document> getCommentsCollection() {
    	MongoDatabase auxsysDb = MongoManager.getDb();
    	commentsCollection = auxsysDb.getCollection("auxsys_comments");
        return commentsCollection;
    }

    public void setCommentsCollection(MongoCollection<Document> commentsCollection) {
        this.commentsCollection = commentsCollection;
    }

    public MongoCollection<Document> getAppCollection() {
    	MongoDatabase auxsysDb = MongoManager.getDb();
        appCollection = auxsysDb.getCollection("auxsys_app");
        return appCollection;
    }

    public void setAppCollection(MongoCollection<Document>  appCollection) {
        this.appCollection = appCollection;
    }
    
    public MongoCollection<Document> getVoteResultCollection() {
    	MongoDatabase auxsysDb = MongoManager.getDb();
        voteResultCollection = auxsysDb.getCollection("auxsys_result");
        return voteResultCollection;
    }
    public void setVoteResultCollection(MongoCollection<Document> voteResultCollection) {
        this.voteResultCollection = voteResultCollection;
    }

    public MongoCollection<Document> getVoteCollection() {
    	MongoDatabase auxsysDb = MongoManager.getDb();
        voteCollection = auxsysDb.getCollection("auxsys_vote");
        return voteCollection;
    }

    public void setVoteCollection(MongoCollection<Document> voteCollection) {
        this.voteCollection = voteCollection;
    }

}
