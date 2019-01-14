package engine.domain;

import java.io.Serializable;

/**
 * 分页对象
 * @name zhengchj
 * @email zhengchj@neusoft.com
 */

public class Pagination implements Serializable {
    private int start;
    private int size;
    private int total;
    private int pageNumber;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
}
