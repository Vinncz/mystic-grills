package repositories;

import models.User;
import models.Order.OrderStatus;

import java.util.ArrayList;
import models.Order;

public class OrderRepositoryProxy {

    public class OrderClassifiedByStatusReturnDatatype {
        private ArrayList<Order> pendingOrders;
        private ArrayList<Order> preparedOrders;
        private ArrayList<Order> servedOrders;

        public OrderClassifiedByStatusReturnDatatype (ArrayList<Order> _pendings, ArrayList<Order> _prepareds, ArrayList<Order> _serveds) {
            this.pendingOrders = _pendings;
            this.preparedOrders = _prepareds;
            this.servedOrders = _serveds;
        }

        public ArrayList<Order> getPendingOrders () {
            return this.pendingOrders;
        }
        
        public ArrayList<Order> getPreparedOrders () {
            return this.preparedOrders;
        }
        
        public ArrayList<Order> getServedOrders () {
        	return this.servedOrders;
        }
    }

    private OrderRepository orderRepo = new OrderRepository();

    public OrderRepositoryProxy () {}

    public OrderClassifiedByStatusReturnDatatype getOrder (User.UserRole userRole) {

        if (userRole.equals(User.UserRole.CHEF)) {
            return new OrderClassifiedByStatusReturnDatatype(
            		orderRepo.getByStatus(OrderStatus.PENDING), 
            		null, null
            );

        } else if (userRole.equals(User.UserRole.WAITER)) {
        	return new OrderClassifiedByStatusReturnDatatype(
            		orderRepo.getByStatus(OrderStatus.PENDING), 
            		orderRepo.getByStatus(OrderStatus.PREPARED),
            		null
            );
        } else if (userRole.equals(User.UserRole.CASHIER)) {
        	return new OrderClassifiedByStatusReturnDatatype(
        			orderRepo.getByStatus(OrderStatus.PENDING), 
        			orderRepo.getByStatus(OrderStatus.PREPARED),
        			orderRepo.getByStatus(OrderStatus.SERVED)
    		);
        }

        return new OrderClassifiedByStatusReturnDatatype(null, null, null);
    }

}
