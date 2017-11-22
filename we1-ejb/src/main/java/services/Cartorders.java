package services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entities.Cart;
import entities.CartOrder;
import entities.Product;

/**
 * Session Bean implementation class Cartorders
 */
@Stateless
public class Cartorders implements CartordersRemote, CartordersLocal {

	@PersistenceContext
	private EntityManager entityManager;
    /**
     * Default constructor. 
     */
    public Cartorders() {
        // TODO Auto-generated constructor stub
    }
	@Override
	public void addCartOrder(CartOrder co,Cart cart) {
		
		 String jpql = "select c FROM Cart c where c.cartPk.user.userId=:iduser and c.cartPk.product.productId=:idproduct and c.order IS NULL";
			Query query = entityManager.createQuery(jpql).setParameter("iduser", cart.getCartPk().getUser().getUserId()).setParameter("idproduct", cart.getCartPk().getProduct().getProductId());
			List<Cart> resultList = query.getResultList();
		if (resultList.isEmpty()) {

			entityManager.persist(co);
		}

	}
	@Override
	public void deleteCartOrderById(int cartOrderId) {
		
		entityManager.remove(findOrderById(cartOrderId));
		
	}
	@Override
	public CartOrder findOrderById(int id) {
		return entityManager.find(CartOrder.class, id);
	}

}
