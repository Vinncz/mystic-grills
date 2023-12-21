package controllers;

import java.util.ArrayList;
import java.util.Optional;

import models.Order;
import models.User;
import repositories.OrderRepository;

public class OrderController {
    
    private OrderRepository orderRepository;

    public OrderController(){
        orderRepository = new OrderRepository();
    }

    public Order post(User _user, Order.OrderStatus _orderStatus, String _orderDate , Double _orderTotal){
        
        Order order = new Order();
        
        order.setOrderUser(_user);
        order.setOrderStatus(_orderStatus);
        order.setOrderDate(_orderDate);
        order.setOrderTotal(_orderTotal);
        
        return orderRepository.post(order);
    }

    public Boolean put(Integer _orderId,User _user, Order.OrderStatus _orderStatus, String _orderDate , Double _orderTotal){
        
        Order updatedOrder = new Order();

        updatedOrder.setOrderId(_orderId);
        updatedOrder.setOrderUser(_user);
        updatedOrder.setOrderStatus(_orderStatus);
        updatedOrder.setOrderDate(_orderDate);
        updatedOrder.setOrderTotal(_orderTotal);
    
        return orderRepository.put(updatedOrder);
    }

    public Boolean delete(Integer _orderId){
        return orderRepository.delete(_orderId);
    }

    public ArrayList<Order> getAll(){
        return orderRepository.getAll();
    }

    public Optional<Order> getById(Integer _orderId){
        return orderRepository.getById(_orderId);
    }

}
