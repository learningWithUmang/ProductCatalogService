package dev.umang.productcatalogservice.dtos;

import org.springframework.data.domain.Sort;

public class SortParam {

    private String sortBy;
    private SortDirection sortDirection;

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public SortDirection getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(SortDirection sortDirection) {
        this.sortDirection = sortDirection;
    }
}
