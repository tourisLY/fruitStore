package com.example.fruitstore.entity;

public class CartSummary {
    private int totalCount; // 总数量
    private float totalAmount; // 总金额

    public CartSummary(int totalCount, float totalAmount) {
        this.totalCount = totalCount;
        this.totalAmount = totalAmount;
    }

    // Getter 和 Setter
    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }
}
