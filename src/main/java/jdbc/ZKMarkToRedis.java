package jdbc;

import Redis.JedisPoolUtil;
import Redis.JedisPoolUtilSingle;
import com.google.gson.Gson;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ZKMarkToRedis {
    private static String driver;
    private static String url;
    private static String user;
    private static String password;
    public static void main(String[] args){
        Gson gson = new Gson();
        //连接oracle数据库
        ProUtil pro=new ProUtil("/jdbc.properties");
        driver= pro.getPro("driver");
        url= pro.getPro("url");
        user= pro.getPro("user");
        password= pro.getPro("password");
        JDBCUtil jdbcUtil=new JDBCUtil(driver,url,user,password);
        //连接reids
        JedisCluster jedis = JedisPoolUtil.getJedis();
        //Jedis jedis = JedisPoolUtilSingle.getJedis();
        //查询标引集合
        String sql="select * from jszk_mark_dic";
        Object[] params={};
        List list = jdbcUtil.executeQuery(sql,params);
        for(int i=0;i<list.size();i++){
            Map<String,Object> o= (Map<String, Object>) list.get(i);
  /*          System.out.println(o);
            System.out.println(o.get("AN"));
            System.out.println(o.get("TIWORDS"));
            System.out.println(o.get("OTHERWORDS"));
            System.out.println(o.get("INVTYPE"));*/
            Map<String,String> markMap=new HashMap<String,String>();
            markMap.put("zkAn",o.get("AN").toString());
            markMap.put("zkType",o.get("INVTYPE").toString());
            markMap.put("zkTiWord",o.get("TIWORDS") == null? "":o.get("TIWORDS").toString());
            markMap.put("zkOthersWord",o.get("OTHERWORDS")== null? "":o.get("OTHERWORDS").toString());
            jedis.set("zk"+o.get("AN"), gson.toJson(markMap));
            System.out.println(jedis.get("zk"+o.get("AN")));
        }
    }

}
