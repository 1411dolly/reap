package com.ttn.reap.entity;

public class Item {
    private Long id;
    private String itemName;
    private int itemValue;
    private String imageSource;
    private int quantity;

    public Item(Long id, String itemName, int itemValue, String imageSource, int quantity) {
        this.id = id;
        this.itemName = itemName;
        this.itemValue = itemValue;
        this.imageSource = imageSource;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemValue() {
        return itemValue;
    }

    public void setItemValue(int itemValue) {
        this.itemValue = itemValue;
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", itemValue=" + itemValue +
                ", imageSource='" + imageSource + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}