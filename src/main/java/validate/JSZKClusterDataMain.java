package validate;
import engine.TrsEngine;
import engine.domain.Condition;
import engine.domain.Constant;
import engine.domain.Record;
import engine.domain.TrsResult;
import engine.impl.TrsEngineImpl;
import jdbc.JDBCUtil;
import jdbc.ProUtil;
import thk.analyzer.ThkAnalyzer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 启动类
 */
public class JSZKClusterDataMain {
    private static TrsEngine trsEngine =new TrsEngineImpl ();
    private static String driver;
    private static String url;
    private static String user;
    private static String password;
    private static JDBCUtil jdbcUtil = null;
    static {
        //连接oracle数据库
        ProUtil pro = new ProUtil("/jdbc.properties");
        driver = pro.getPro("driver");
        url = pro.getPro("url");
        user = pro.getPro("user");
        password = pro.getPro("password");
        jdbcUtil = new JDBCUtil(driver,url,user,password);
    }

    /**
     * 从数据库里查询本申请案卷 对比文献案卷列表，对比类型
     * @param referenceCategory
     */
    public static void getAnList(String referenceCategory){
       String sql = "select * from uni_abs_patcit_cn_new_temp t where t.rownum <=2000 and t.reference_category=" + "'"+referenceCategory+"'";
       // String sql = "select * from uni_abs_patcit_cn_new_temp t where t.an='CN201210117872.7'" ;
        //rownum <=2000
        Object[] params={};
        List list = jdbcUtil.executeQuery(sql,params);
        for(int i=0;i<list.size();i++){
            Map<String,Object> o= (Map<String, Object>) list.get(i);
            String oldAn = o.get("AN").toString();
            String newAn = oldAn.substring(0,oldAn.indexOf("."));
            String  citedAn = o.get("CITED_AN").toString();
            JSZKClusterDataMain.readDetailInfoFromTrs(newAn, citedAn, oldAn, referenceCategory );
        }
    }

    /**
     * 从trs CNABS里获取信息
     * @param an
     * @param citedAn
     * @param oldAn
     * @param referenceCategory
     */
    public static  void readDetailInfoFromTrs(String an,String citedAn,String  oldAn,String referenceCategory){
        Map<String, String> patentInfoMap= new HashMap<String,String>();
        Map<String, String> citeInfoMap= new HashMap<String,String>();
        //1.1 设置trs CNABS 查询条件字段
        Condition conditionCNABS = new Condition();
        String searchAn = "nrd_an=('" + an + "' or '"+citedAn+"')";//nrd_an=( 'CN201510493315' or 'CN01128416')
        conditionCNABS.setExp(searchAn);
        conditionCNABS.setDbName(Constant.CNABS_DB);
        conditionCNABS.setDisplayFields(Constant.GK_PN  +","+Constant.IPC_MAIN+"," + Constant.GK_FIELDS + "," + Constant.SQ_FIELDS + "," + Constant.OTHER_FIELDS);
        //1.2 查询
        TrsResult tr = trsEngine.search(conditionCNABS);
        //1.3 获取结果集
        List<Record> recordList = tr.getRecords();
        int size = recordList.size();
        for(int i=0;i<size;i++){
            Map<String, String> assembleData=AssembleData(recordList.get(i).getDataMap());
            if(assembleData.get("NRD_AN").equals(an)){
                patentInfoMap=assembleData;
            }else if(assembleData.get("NRD_AN").equals(citedAn)){
                citeInfoMap=assembleData;
            }
        }
        String patentTI = patentInfoMap.get("TI");
        String patentTIKeywords = JSZKClusterDataMain.getkeywords(patentTI);
        String patentAB = patentInfoMap.get("AB");
        String patentIpcMain = patentInfoMap.get("ipcMain");
        String citedTI = citeInfoMap.get("TI");
        String citedTIKeywords = JSZKClusterDataMain.getkeywords(citedTI);
        String citedAB = citeInfoMap.get("AB");
        String citedIpcMain = citeInfoMap.get("ipcMain");
        String sql="insert into jszk_mark_ti_validate(an,oldan,citedan,patentti,citedti,patenttikeywords,citedtikeyword,patentIpcMain,citedIpcMain,referenceCategory,patentABI,citedABI) values (?,?,?,?,?,?,?,?,?,?,?,?)";
        Object[] params={an,oldAn,citedAn,patentTI,citedTI,patentTIKeywords,citedTIKeywords,patentIpcMain,citedIpcMain,referenceCategory,patentAB,citedAB};
        jdbcUtil.executeUpdate(sql,params);
    }

    /**
     * 输入文本拆词
     * @param text
     * @return
     */
    public static String getkeywords(String text){
            String result = "";
            try {
                List list = ThkAnalyzer.getInstance().analysis(text);
                for(int i=0;i<list.size();i++){
                       String item = list.get(i).toString();
                       result += item.substring(0,item.indexOf(":"))+",";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result.length()== 0?"":result.substring(0,result.length()-1);
    }

    /**
     * 默认取GK（公开）字段数据，若为空，则取SQ（授权）字段数据
     *
     * @param dataMap 检索出的trs行数据
     * @return 组装后的数据
     */
    private static Map<String, String> AssembleData(Map<String, String> dataMap) {
        Map<String, String> resultMap = new HashMap<String, String>();
        String gkPn = dataMap.get(Constant.GK_PN);
        String prefix = Constant.GK_PREFIX;
        if (gkPn == null || gkPn.equals("")) {
            prefix = Constant.SQ_PREFIX;
        }
        String[] mainFields = Constant.MAIN_FIELDS.split(",");
        for (String f : mainFields) {
            resultMap.put(f, dataMap.get(prefix + f));
        }
        String[] otherFields = Constant.OTHER_FIELDS.split(",");
        for (String f : otherFields) {
            resultMap.put(f, dataMap.get(f));
        }
        String ipcMain=dataMap.get(Constant.IPC_MAIN);
        resultMap.put("ipcMain",ipcMain);
        return resultMap;
    }


    public static void main(String[] args){
        //X ,Y ,R,PE,P,A,T,PX,PY,E,PA,L
        String  referenceCategory = "L";
        getAnList (referenceCategory);
    }

}
