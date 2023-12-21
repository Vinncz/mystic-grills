package repositories;

import models.User;
import models.Order.OrderStatus;

import java.util.ArrayList;
import models.Order;

public class OrderRepositoryProxy {

    public class OrderClassifiedByStatusReturnDatatype {
        private ArrayList<Order> pendingOrders;
        private ArrayList<Order> preparedOrders;

        public OrderClassifiedByStatusReturnDatatype (ArrayList<Order> _pendings, ArrayList<Order> _prepareds) {
            this.pendingOrders = _pendings;
            this.preparedOrders = _prepareds;
        }

        public ArrayList<Order> getPendingOrders () {
            return this.pendingOrders;
        }
        
        public ArrayList<Order> getPreparedOrders () {
            return this.preparedOrders;
        }
    }

    private OrderRepository orderRepo = new OrderRepository();

    public OrderRepositoryProxy () {}

    public ArrayList<Order> getOrder (User.UserRole userRole) {
        Order.OrderStatus status;
        ArrayList<Order> retrievedData = new ArrayList<>();

        if (userRole.equals(User.UserRole.CHEF)) {
            status = OrderStatus.PENDING;

        } else if (userRole.equals(User.UserRole.WAITER)) {
            status = OrderStatus.PREPARED;
            retrievedData = orderRepo.getByStatus(status);
            status = OrderStatus.PENDING;
            retrievedData.addAll(orderRepo.getByStatus(status));
        }

        return retrievedData;
    }

}
