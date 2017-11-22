package services;

import java.util.List;

import javax.ejb.Remote;

import entities.Order;

@Remote
public interface OrdersRemote {
	void addOrder(Order order);

	void updateOrder(Order order);

	void deleteOrder(Order order);

	void deleteOrderById(int id);

	Order findOrderById(int id);

	Order findLastOrderByUserId(int id);

	List<Order> findAllOrders();

	List<Order> findAllOwnedOrders(int id);
}
