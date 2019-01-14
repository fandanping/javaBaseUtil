package engine.impl;

import com.eprobiti.trs.TRSConnection;
import com.eprobiti.trs.TRSConstant;
import com.eprobiti.trs.TRSException;
import com.eprobiti.trs.TRSResultSet;
import com.trs.zl.TRSSearch;
import engine.TrsEngine;
import engine.domain.*;
import org.apache.commons.io.IOUtils;
import org.omg.CORBA.SystemException;
import java.io.InputStream;

/**
 * trs引擎实现
 *
 */
public class TrsEngineImpl implements TrsEngine {

    private String hostip = "10.51.52.59";

    private String port = "8888";

    private String username = "system";

    private String password = "manager";

    private String userId = "";

    private TRSConnection createConnection() throws TRSException {
        TRSConnection conn = TRSSearch.getTRSConnection(userId, hostip, port, username, password);
        conn.SetExtendOption("HITPOINTMODE", "1");
        conn.SetExtendOption("RETRYMISCOL", "$ZERO");
        conn.SetExtendOption("SORTMISCOL", "1");
        conn.SetExtendOption("SORTNOCASE", "1");
        conn.setAutoExtend("", "", "", 0);
        return conn;
    }

    private void closeConnection(TRSConnection conn) {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    @Override
    public TrsResult search(Condition condition) throws SystemException {
        TRSConnection conn = null;
        TrsResult rs = new TrsResult();
        TRSResultSet result = null;
        try{
            conn = createConnection();
            result = TRSSearch.executeSelect(conn, condition.getDbName(), condition.getExp(),
                    condition.getSortFields(), "", "BI", TRSConstant.TCM_IDEOSINGLE | TRSConstant.TCM_LIFOSPARE, TRSConstant.TCE_NOTHIT, false);
           //降序排列
            //result.sortResult("-"+condition.getSortFields(),true);
            result.setReadOption("READOPTION", null, TRSConstant.TCM_BAN64KFILE);
            result.setCutSize(0, true);
            result.setReadOptions(TRSConstant.TCE_NOTHIT, condition.getDisplayFields(), ",");
            Pagination pagination = condition.getPagination();
            int pageSize = pagination.getSize(), pageStart = pagination.getStart();
            int total = new Long(result.getRecordCount()).intValue();
            if (pageSize == 0) {
                pageSize = total;
            }
            result.setBufferSize(pageSize, pageSize);
            result.setErrorMode(1, "-");
            pagination.setTotal(total);
            rs.setPagination(pagination);
            if (pageStart + pageSize >= total) {
                rs.setLast(true);
            }
            while (result.moveTo(0, pageStart++)) {
                String[] fields = condition.getDisplayFields().split(",");
                if (pageSize-- > 0) {
                    Record record = new Record();
                    for (String f : fields) {
                        String r = "";
                        if(f.equals(Constant.CLMS) || f.equals(Constant.DESC) || f.equals(Constant.GK_PREFIX + Constant.AB) || f.equals(Constant.SQ_PREFIX + Constant.AB)){
                            InputStream is = result.getBinaryStream(f, 0);
                            if(is != null){
                                r = IOUtils.toString(is, "UTF-8");
                            }
                        }else{
                            r = result.getString(f, "red");
                            r = TRSSearch.dealTRSRSString(r);
                        }
                        record.getDataMap().put(f, r);
                    }
                    rs.getRecords().add(record);
                }else{
                    break;
                }
            }
        }catch(Exception e){

        }finally {
            result.close();
            closeConnection(conn);
        }
        return rs;
    }

}
