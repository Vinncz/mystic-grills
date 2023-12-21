package controllers;

import java.util.ArrayList;
import java.util.Optional;

import models.MenuItem;
import models.OrderItem;
import repositories.OrderItemRepository;

public class OrderItemController {
    
    private OrderItemRepository orderItemRepository;

    public OrderItemController(){
        orderItemRepository = new OrderItemRepository();
    }

    
    public OrderItem post(Integer _orderId, MenuItem _menuItem , Integer _quantity){
        
        OrderItem orderItem = new OrderItem();

        orderItem.setOrderId(_orderId);
        orderItem.setMenuItem(_menuItem);
        orderItem.setQuantity(_quantity);

        return orderItemRepository.post(orderItem);
    } 
    
    public Boolean put(Integer _orderItemId, Integer _orderId, MenuItem _menuItem , Integer _quantity){
        
        Optional<OrderItem> orderItem = orderItemRepository.getById(_orderId);

        if(orderItem == null){
            return false;
        }

        OrderItem updatedOrderItem = orderItem.get();

        updatedOrderItem.setOrderItemId(_orderItemId);
        updatedOrderItem.setOrderId(_orderId);
        updatedOrderItem.setMenuItem(_menuItem);
        updatedOrderItem.setQuantity(_quantity);

        return orderItemRepository.put(updatedOrderItem);
    }

    public Boolean delete(Integer _orderItemId){

        return orderItemRepository.delete(_orderItemId);
    }

    public ArrayList<OrderItem> getAll(){
        return orderItemRepository.getAll();
    }

}
