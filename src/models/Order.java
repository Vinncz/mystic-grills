package models;

import java.util.ArrayList;

public class Order {

    public enum OrderStatus {
        PAID("Paid"),
        PENDING("Pending"),
        PREPARED("Prepared"),
        SERVED("Served");
    
        private final String stringValue;
    
        OrderStatus(String stringValue) {
            this.stringValue = stringValue;
        }
    
        public String getStringValue() {
            return stringValue;
        }
    }

    private Integer orderId;
    private User orderUser;
    private ArrayList<OrderItem> orderItems;
    private String orderStatus;
    private String orderDate;
    private Double orderTotal;

    public String getOrderDate() {
        return orderDate;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public ArrayList<OrderItem> getOrderItems() {
        return orderItems;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public Double getOrderTotal() {
        return orderTotal;
    }

    public User getOrderUser() {
        return orderUser;
    }

    public void setOrderId (int _orderId) {
        this.orderId = _orderId;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public void setOrderItems(ArrayList<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus.getStringValue();
    }

    public void setOrderTotal(Double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public void setOrderUser(User orderUser) {
        this.orderUser = orderUser;
    }

}
