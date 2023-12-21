package controllers;

import java.util.ArrayList;
import java.util.Optional;

import models.Order;
import models.Order.OrderStatus;
import models.User;
import models.User.UserRole;
import repositories.OrderRepository;
import repositories.OrderRepositoryProxy;
import repositories.OrderRepositoryProxy.OrderClassifiedByStatusReturnDatatype;

public class OrderController {

	private OrderRepositoryProxy orderRepoProxy;
    private OrderRepository orderRepository;

    public OrderController(){
        orderRepository = new OrderRepository();
		orderRepoProxy = new OrderRepositoryProxy();
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

    public ArrayList<Order> getByUserId (int _userId) {
        return orderRepository.getByUserId(_userId);
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

	public OrderClassifiedByStatusReturnDatatype getOrders(UserRole role) {
		return orderRepoProxy.getOrder(role);
	}

	public Boolean serveOrder (Order order) {
		if(order.getOrderStatus().equals(OrderStatus.PREPARED)) {
			order.setOrderStatus(OrderStatus.SERVED);
			return orderRepository.put(order);
		}
		return false;
	}

	public Boolean prepareOrder (Order order) {
		if(order.getOrderStatus().equals(OrderStatus.PENDING)) {
			order.setOrderStatus(OrderStatus.PREPARED);
			return orderRepository.put(order);
		}
		return false;
	}

	public Boolean validateDeleteOrder (Integer _idOfAnObjectToBeDeleted) {
		Optional<Order> order = getOrderById(_idOfAnObjectToBeDeleted);

		if (order.isPresent()) {
			return orderRepository.delete(_idOfAnObjectToBeDeleted);
		}

		return false;
	}

	public Optional<Order> getOrderById (Integer _id) {
		return orderRepository.getById(_id);
	}

}
