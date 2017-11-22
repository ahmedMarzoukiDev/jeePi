package services;

import java.util.List;

import javax.ejb.Local;

import entities.Order;

@Local
public interface OrdersLocal {

	void addOrder(Order order);

	void updateOrder(Order order);

	void deleteOrder(Order order);

	void deleteOrderById(int id);
	
	Order findLastOrderByUserId(int id);

	Order findOrderById(int id);

	List<Order> findAllOrders();

	List<Order> findAllOwnedOrders(int id);
}
