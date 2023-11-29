package models;

public class OrderItem {

    private Integer orderItemId;
    private Integer orderId;
    private MenuItem menuItem;
    private Integer quantity;

    public void setOrderItemId (Integer _orderItemId) {
        this.orderItemId = _orderItemId;
    }

    public Integer getOrderItemId(){
        return this.orderItemId;
    }

    public void setOrderId (Integer _orderId) {
        this.orderId = _orderId;
    }

    public Integer getOrderId(){
        return this.orderId;
    }

    public void setMenuItem(MenuItem _menuItem){
        this.menuItem = _menuItem;
    }

    public MenuItem getMenuItem(){
        return this.menuItem;
    }

    public void setQuantity(Integer _quantity){
        this.quantity = _quantity;
    }

    public Integer getQuantity(){
        return this.quantity;
    }
}
