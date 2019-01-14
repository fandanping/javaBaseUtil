package engine.domain;


import java.io.Serializable;

public class Condition implements Serializable {
    /**
     * 检索式
     */
    private String Exp;

    /**
     * 分页对象
     */
    private Pagination pagination = new Pagination();

    /**
     * 显示字段
     */
    private String displayFields;

    /**
     * 检索库
     */
    private String dbName;
    /**
     * 排序字段
     */
    private String sortFields;

    public String getExp() {
        return Exp;
    }

    public void setExp(String exp) {
        Exp = exp;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public String getDisplayFields() {
        return displayFields;
    }

    public void setDisplayFields(String displayFields) {
        this.displayFields = displayFields;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getSortFields() {
        return sortFields;
    }

    public void setSortFields(String sortFields) {
        this.sortFields = sortFields;
    }
}
