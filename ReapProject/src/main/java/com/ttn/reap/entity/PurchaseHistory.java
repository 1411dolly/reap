package com.ttn.reap.entity;

import java.util.Date;

public class PurchaseHistory {
    private Long id;
    private User userId;
    private Item itemId;
    private Date purchaseTimestamp;
    private int purchaseQuantity;

    public PurchaseHistory(Long id, User userId, Item itemId, Date purchaseTimestamp, int purchaseQuantity) {
        this.id = id;
        this.userId = userId;
        this.itemId = itemId;
        this.purchaseTimestamp = purchaseTimestamp;
        this.purchaseQuantity= purchaseQuantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Item getItemId() {
        return itemId;
    }

    public void setItemId(Item itemId) {
        this.itemId = itemId;
    }

    public Date getPurchaseTimestamp() {
        return purchaseTimestamp;
    }

    public void setPurchaseTimestamp(Date purchaseTimestamp) {
        this.purchaseTimestamp = purchaseTimestamp;
    }


    public int getPurchaseQuantity() {
        return purchaseQuantity;
    }

    public void setPurchaseQuantity(int purchaseQuantity) {
        this.purchaseQuantity = purchaseQuantity;
    }

    @Override
    public String toString() {
        return "PurchaseHistory{" +
                "id=" + id +
                ", userId=" + userId +
                ", itemId=" + itemId +
                ", purchaseTimestamp=" + purchaseTimestamp +
                ", purchaseQuantity=" + purchaseQuantity +
                '}';
    }
}
