package org.when.salary.context.domain;

public class OrderLine {
    private Integer quantity;
    private Integer deliveredVolume;

    public OrderLine(Integer quantity, Integer deliveredVolume) {
        this.quantity = quantity;
        this.deliveredVolume = deliveredVolume;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getDeliveredVolume() {
        return deliveredVolume;
    }
}
