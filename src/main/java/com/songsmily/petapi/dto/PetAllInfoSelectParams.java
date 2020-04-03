package com.songsmily.petapi.dto;

public class PetAllInfoSelectParams {
    Integer currentPage;
    Integer pageSize;
    String areaFilter;
    Integer petStatus;
    Integer petCardStatus;
    Integer petImmunityStatus;

    @Override
    public String toString() {
        return "PetAllInfoSelectParams{" +
                "currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", areaFilter='" + areaFilter + '\'' +
                ", petStatus=" + petStatus +
                ", petCardStatus=" + petCardStatus +
                ", petImmunityStatus=" + petImmunityStatus +
                '}';
    }

    public Integer getPetCardStatus() {
        return petCardStatus;
    }

    public void setPetCardStatus(Integer petCardStatus) {
        this.petCardStatus = petCardStatus;
    }

    public Integer getPetImmunityStatus() {
        return petImmunityStatus;
    }

    public void setPetImmunityStatus(Integer petImmunityStatus) {
        this.petImmunityStatus = petImmunityStatus;
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

    public String getAreaFilter() {
        return areaFilter;
    }

    public void setAreaFilter(String areaFilter) {
        this.areaFilter = areaFilter;
    }

    public Integer getPetStatus() {
        return petStatus;
    }

    public void setPetStatus(Integer petStatus) {
        this.petStatus = petStatus;
    }
}
