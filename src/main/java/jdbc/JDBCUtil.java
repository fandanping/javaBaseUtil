package jdbc;
import java.sql.*;
import java.util.*;

/**
 * jdbc工具类
 */
public class JDBCUtil {
    private static String driver=null;
    private static String url=null;
    private static String username=null;
    private static String password=null;

    private Connection conn=null;
    private CallableStatement callableStatement=null;
    private PreparedStatement pst=null;
    private ResultSet rst=null;

    public JDBCUtil(String driver,String url,String username,String password){
        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /**
     * 加载驱动
     * @return
     */
    public Connection getConnection(){
        try {
            Class.forName(driver);
            conn= DriverManager.getConnection(url,username,password);
        } catch (ClassNotFoundException e) {
            System.out.println("加载驱动错误");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }catch(SQLException e){
            System.out.println("获取连接错误");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 关闭所有资源
     */
    private void closeAll() {
         //1. 关闭结果集对象
        if(rst!=null){
            try {
                rst.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        //2. 关闭PreparedStatement对象
        if(pst!=null){
            try {
                pst.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        //3. 关闭CallableStatement 对象
        if (callableStatement != null) {
            try {
                callableStatement.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        // 4. 关闭Connection 对象
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * 插入 更新 删除
     * @param sql sql语句
     * @param params 参数数组，若没有参数则为null
     * @return 受影响的行数
     */
    public int executeUpdate(String sql,Object[] params){
        //受影响的行数
        int affectedLine = 0;
        try {
            //获取连接
            conn=this.getConnection();
            //调用sql
            pst = conn.prepareStatement(sql);
            //参数赋值
            if(params !=null){
                for(int i=0;i<params.length;i++){
                    pst.setObject(i+1,params[i]);
                }
            }
            //执行
            affectedLine = pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }finally {
            closeAll();
        }
        return affectedLine;
    }

    /**
     *查询：将查询结果直接放入ResultSet中
     * @param sql Sql语句
     * @param params 参数数组，若没有参数则为null
     * @return
     */
    private ResultSet executeQueryRS(String sql,Object[] params){
        try {
            //获得连接
            conn=this.getConnection();
            //调用sql
            pst=conn.prepareStatement(sql);
            //参数赋值
            for(int i=0;i<params.length;i++){
                pst.setObject(i+1,params[i]);
            }
            //执行
            rst = pst.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return rst;
    }

    /**
     * 查询：一行一列
     * @param sql
     * @param params
     * @return
     */
    public Object executeQuerySingle(String sql,Object[] params){
        Object object = null;
        try {
            // 获得连接
            conn = this.getConnection();
            // 调用SQL
            pst = conn.prepareStatement(sql);
            // 参数赋值
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    pst.setObject(i + 1, params[i]);
                }
            }
            // 执行
            rst = pst.executeQuery();
            if(rst.next()) {
                object = rst.getObject(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeAll();
        }
        return object;
    }

    /**
     * 获取结果集，并将结果放在List中
     * @param sql sql语句
     * @param params
     * @return list结果集
     */
    public List<Object> executeQuery(String sql,Object[] params){
        //执行sql获取结果集
        ResultSet rs=executeQueryRS(sql,params);
        //创建ResultSetData对象
        ResultSetMetaData rsmd = null;
        //结果集列数
        int columnCount = 0;
        try {
            rsmd = rs.getMetaData();
            //获取结果集列数
            columnCount=rsmd.getColumnCount();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        //创建list
        List<Object> list=new ArrayList<Object>();
        try {
            while(rs.next()){
                Map<String, Object> map = new HashMap<String, Object>();
                for(int i=1;i<columnCount;i++){
                    map.put(rsmd.getColumnLabel(i),rs.getObject(i));
                }
                list.add(map);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            this.closeAll();
        }
        return list;
    }

    /**
     *  批量插入
     *  注意： oracle的preparedStatement批量执行sql时，对参数个数是有上限的（针对不同版本的oracle驱动，这个上限对不同的可能是不同的），
     * 这个参数个数的含义指addBatch的次数*每条sql中的参数个数。
     * 对于Oracle 10g的驱动来说，这个值可能是32768，所以编程时，addBatch的次数*每条sql中的参数个数应该小于这个值，否则报错。
     * @param sql sql语句
     * @param list 设置传入的List列表
     * @param batchNum 每次批量执行多少
     * @return
     */
    public boolean insertBatch(String sql,List<Map<Integer,Object>> list,int batchNum){
        //记录起始时间
        long start = System.currentTimeMillis();
        //记录命令执行失败数
        long faileNum = 0;
        //记录执行commit次数
        long commitNum = 0;
        int[] updateCounts=null;
        try {
            // 1. 获取连接
            conn=this.getConnection();
            //1.1 设置为不自动提交
            conn.setAutoCommit(false);
            // 2. 调用sql
            pst = conn.prepareStatement(sql);
            // 3. 遍历 ，进行sql语句的传参
            for(int i=0;i<list.size();i++){
                Map<Integer,Object> map=list.get(i);
                int initCount=0;
                int count=map.size(); //3
                while(initCount<count){
                    initCount +=1;
                    pst.setObject(initCount,map.get(initCount));
                }
                pst.addBatch();
                if(i%batchNum==0){
                    try {
                        //批量处理
                        pst.executeBatch();
                        conn.commit();
                        //清除pst中参数列表
                       // pst.clearBatch();
                        commitNum++;
                        System.out.println("--commit-- ");
                    }catch(BatchUpdateException bue){
                        faileNum++;
                        updateCounts=bue.getUpdateCounts();
                    }
                }
            }
            pst.executeBatch();
            conn.commit();
           // pst.clearBatch();
            //统计数据
            long end = System.currentTimeMillis();
            System.out.println("每次提交："+batchNum+"条");
            System.out.println("提交次数："+commitNum);
            System.out.println("失败次数："+faileNum);
            System.out.println("批量插入需要时间:"+(end - start)+"ms");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }finally {
            closeAll();
        }
        return true;
    }

    /**
     * 执行批量更新 ,依据一个值进行更新
     */
    public  boolean executeBatch(String sql,Object[] params){
        Boolean flag=false;
        try {
            // 1. 获取连接
            conn=this.getConnection();
            //1.1 设置为不自动提交
            conn.setAutoCommit(false);
            // 2. 创建一个PrepareStatemnet对象来将参数化的sql语句发送到数据库
            pst = conn.prepareStatement(sql);
            //3.将一组参数添加到PrepareStatement对象的批处理命令中
            for (int  i=0;i<params.length;i++){
                    pst.setObject(i+1,params[i]);
                    pst.addBatch();
            }
            //执行批量更新
            pst.executeBatch();
            conn.commit();
            flag = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

}
