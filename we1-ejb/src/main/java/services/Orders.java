package services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import entities.Order;

/**
 * Session Bean implementation class Orders
 */
@Stateless
@LocalBean
public class Orders implements OrdersRemote, OrdersLocal {

	@PersistenceContext
	private EntityManager entityManager;
    /**
     * Default constructor. 
     */
    public Orders() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public void addOrder(Order order) {
		entityManager.persist(order);
		
	}

	@Override
	public void updateOrder(Order order) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteOrder(Order order) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteOrderById(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Order findOrderById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> findAllOrders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> findAllOwnedOrders(int id) {
		String jpql = "select o FROM Order o where o.userId=:id order by o.orderId DESC";
		Query query = entityManager.createQuery(jpql).setParameter("id", id);
		return query.getResultList();
		
	}

	@Override
	public Order findLastOrderByUserId(int id) {
		 String jpql = "select o FROM Order o where o.userId=:id order by o.orderId DESC";
			Query query = entityManager.createQuery(jpql).setParameter("id", id);
			Order resultList = (Order) query.getResultList().get(0);
			return resultList;
	}

}
