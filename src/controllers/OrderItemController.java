package controllers;

import java.util.ArrayList;
import java.util.Optional;

import models.MenuItem;
import models.OrderItem;
import repositories.OrderItemRepository;

public class OrderItemController {

	private OrderItemRepository orderItemRepo;
	private MenuItemController mic;
	
	public OrderItemController () {
		this.orderItemRepo = new OrderItemRepository();
		this.mic = new MenuItemController();
	}
	
	public OrderItem newOrderItem(Integer _orderId, MenuItem _menuitem) {{
		OrderItem oi = new OrderItem();
		oi.setOrderId(_orderId);
		oi.setMenuItem(_menuitem);
		oi.setQuantity(1);
		
		return oi;
	}
		
	}
	
	public ArrayList<OrderItem> getByOrderId (Integer _orderId) {
		return orderItemRepo.getByOrderId(_orderId);
	}
	
	public OrderItem getByMenuItemID (Integer _orderId, Integer _menuItemID) {
		Optional<MenuItem> menuItemHolder = mic.getById(_menuItemID);
		
		if(menuItemHolder.isPresent()) {
			OrderItem oi = new OrderItem();
			oi.setOrderId(_orderId);
			oi.setMenuItem(menuItemHolder.get());
			oi.setQuantity(1);
			
			return oi;
		}
		
		return null;
	}
	
	public Boolean validateDeleteOrderItem (Integer _idOfAnObjectToBeDeleted) {
		Optional<OrderItem> orderItem = getOrderItemByID(_idOfAnObjectToBeDeleted);
		
		if(orderItem.isPresent()) {
			return orderItemRepo.delete(_idOfAnObjectToBeDeleted);
		}
		
		return false;
	}
	
	public OrderItem addOrderItems(OrderItem _objectToBeInserted) {
		return orderItemRepo.post(_objectToBeInserted);
	}
	
	public Boolean updateChanges (OrderItem _objectToBeInserted) {
		Optional<OrderItem> orderItem = getOrderItemByID(_objectToBeInserted.getOrderItemId());
		if(orderItem.isPresent()) {
			return orderItemRepo.put(_objectToBeInserted);			
		}
		
		return false;
	}
	
	public OrderItem insertOrderItem (OrderItem _objectToBeInserted) {
		return orderItemRepo.post(_objectToBeInserted);
	}
	
	public Optional<OrderItem> getOrderItemByID(Integer _id){
		return orderItemRepo.getById(_id);
	}
	
	public OrderItem post (OrderItem _objectToBeInserted) {
		return orderItemRepo.post(_objectToBeInserted);
	}
	
	public Boolean put (OrderItem _replacementObject) {
		return orderItemRepo.put(_replacementObject);
	}
	
	public Boolean delete (Integer _idOfAnObjectToBeDeleted) {
		return orderItemRepo.delete(_idOfAnObjectToBeDeleted);
	}
	
}
