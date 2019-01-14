package engine.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * trs结果集
 * @name zhengchj
 * @email zhengchj@neusoft.com
 */
public class TrsResult implements Serializable {
    /**
     * 分页对象
     */
    private Pagination pagination;

    private boolean last = false;

    /**
     * 数据集合
     */
    List<Record> records = new ArrayList<Record>();

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }
}
