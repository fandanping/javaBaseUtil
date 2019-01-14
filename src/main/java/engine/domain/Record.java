package engine.domain;

import java.io.Serializable;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * trs数据对象
 * @name zhengchj
 * @email zhengchj@neusoft.com
 */

public class Record implements Serializable {
    /**
     * 存储的trs数据
     */
    Map<String, String> dataMap = new HashMap<String, String>();

    public Map<String, String> getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map<String, String> dataMap) {
        this.dataMap = dataMap;
    }
}
