package com.aptech.project2.Model;

import java.time.LocalDateTime;

public class Payment {
    private int id;
    private String memberId;
    private double subtotal;
    private double discount;
    private double total;
    private LocalDateTime dateTime;

    public Payment() {
    }

    public Payment(int id, String memberId, double subtotal, double discount, double total, LocalDateTime dateTime) {
        this.id = id;
        this.memberId = memberId;
        this.subtotal = subtotal;
        this.discount = discount;
        this.total = total;
        this.dateTime = dateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
