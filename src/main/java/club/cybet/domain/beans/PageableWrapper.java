package club.cybet.domain.beans;

/**
 * helb-portal-api (club.cybet.domain.beans)
 * Created by: cybet
 * On: 09 Aug, 2018 8/9/18 2:47 PM
 **/
public class PageableWrapper {

    private String domain;
    private Integer currentPage;
    private Integer pageSize;
    private Long totalCount;
    private String nextPage;
    private Object records;

    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Object getRecords() {
        return records;
    }

    public void setRecords(Object records) {
        this.records = records;
    }
}
