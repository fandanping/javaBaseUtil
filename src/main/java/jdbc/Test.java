package jdbc;

import sun.security.tools.keytool.Resources;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static oracle.net.aso.C01.p;

/**
 * 测试类
 */
public class Test {
    private static String driver;
    private static String url;
    private static String user;
    private static String password;
    public static void main(String [] args){
        ProUtil pro=new ProUtil("/jdbc.properties");
        driver= pro.getPro("driver");
        System.out.println(driver);
        url= pro.getPro("url");
        user= pro.getPro("user");
        password= pro.getPro("password");
        JDBCUtil jdbcUtil=new JDBCUtil(driver,url,user,password);
        //插入一条
        String sql="insert into test(addressid,city,provice)values(?,?,?)";
        Object[] params={3,"大连","辽宁"};
        jdbcUtil.executeUpdate(sql,params);
        //查询集合
         /*
        String sql="select * from test where provice=?";
        Object[] params={"辽宁"};
        List list = jdbcUtil.executeQuery(sql,params);
        for(int i=0;i<list.size();i++){
               Map<String,Object> o= (Map<String, Object>) list.get(i);
                  System.out.println(o);
                  System.out.println(o.get("CITY"));
                  System.out.println(o.get("ADDRESSID"));

        }
        */
         //批量插入
        /*  String sql="insert into test(addressid,city,provice) values(?,?,?)";
          List<Map<Integer,Object>> list=new ArrayList<Map<Integer,Object>>();
          Map<Integer,Object> map=new HashMap<>();
          for(int i=0;i<1000000;i++){
                      map.put(1,i)  ;
                         map.put(2,"as2")  ;
                            map.put(3,"as3")  ;
                            list.add(map);
          }
        jdbcUtil.insertBatch(sql,list,8000);*/
        //批量更新
    /*    String sql="update test set city='newcity' where city=?";
        List<Map<Integer,Object>> list=new ArrayList<Map<Integer,Object>>();
        Object[] params={"as2"};
        jdbcUtil.executeBatch(sql,params);*/

    }
}
