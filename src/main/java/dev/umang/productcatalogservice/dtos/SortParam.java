package dev.umang.productcatalogservice.dtos;

/*
Within all results
sort by description, sort by price
electronics 300
electronics 250
 */
public class SortParam {
    private String paramName;
    private SortType order;

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public SortType getOrder() {
        return order;
    }

    public void setOrder(SortType order) {
        this.order = order;
    }


}
