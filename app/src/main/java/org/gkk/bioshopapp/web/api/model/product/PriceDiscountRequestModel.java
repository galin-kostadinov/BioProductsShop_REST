package org.gkk.bioshopapp.web.api.model.product;

import java.time.LocalDateTime;

public class PriceDiscountRequestModel {

    private Integer discount;

    private String fromDate;

    private String toDate;

    public PriceDiscountRequestModel() {
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }
}