package com.songsmily.petapi.dto;

public class BlogSelectParams {
    private Integer pageSize;
    private Integer currentPage;
    private String activeType;
    private Integer hotTag;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public String getActiveType() {
        return activeType;
    }

    public void setActiveType(String activeType) {
        this.activeType = activeType;
    }

    public Integer getHotTag() {
        return hotTag;
    }

    public void setHotTag(Integer hotTag) {
        this.hotTag = hotTag;
    }
}
