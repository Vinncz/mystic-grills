package controllers;

import java.util.ArrayList;
import java.util.Optional;

import models.Order;
import models.Order.OrderStatus;
import models.User.UserRole;
import repositories.OrderRepository;
import repositories.OrderRepositoryProxy;
import repositories.OrderRepositoryProxy.OrderClassifiedByStatusReturnDatatype;

public class OrderController {

	private OrderRepository orderRepo;
	private OrderRepositoryProxy orderRepoProxy;
	
	public OrderController() {
		this.orderRepo = new OrderRepository();
		this.orderRepoProxy = new OrderRepositoryProxy();
	}
	
	public Optional<Order> getOrderById (Integer _id) {
		return orderRepo.getById(_id);
	}
	
	public ArrayList<Order> getAll () {
		return orderRepo.getAll();
	}
	
	public OrderClassifiedByStatusReturnDatatype getOrders(UserRole role) {
		return orderRepoProxy.getOrder(role);
	}
	
	public Boolean validateDeleteOrder (Integer _idOfAnObjectToBeDeleted) {
		Optional<Order> order = getOrderById(_idOfAnObjectToBeDeleted);
		
		if (order.isPresent()) {
			return orderRepo.delete(_idOfAnObjectToBeDeleted);
		}
		
		return false;
	}
	
	public Boolean prepareOrder (Order order) {
		if(order.getOrderStatus().equals(OrderStatus.PENDING)) {			
			order.setOrderStatus(OrderStatus.PREPARED);
			return orderRepo.put(order);
		}
		return false;
	}
	
	public Boolean serveOrder (Order order) {
		if(order.getOrderStatus().equals(OrderStatus.PREPARED)) {			
			order.setOrderStatus(OrderStatus.SERVED);
			return orderRepo.put(order);
		}
		return false;
	}
	
	public Order post (Order _objectToBeInserted) {
		return orderRepo.post(_objectToBeInserted);
	}
	
	public Boolean put (Order _replacementObject) {
		return orderRepo.put(_replacementObject);
	}
	
	public Boolean delete (Integer _idOfAnObjectToBeDeleted) {
		return orderRepo.delete(_idOfAnObjectToBeDeleted);
	}
}
