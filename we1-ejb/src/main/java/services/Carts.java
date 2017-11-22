package services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import entities.Cart;
import entities.StatType;

/**
 * Session Bean implementation class Carts
 */
@Stateless
public class Carts implements CartsRemote, CartsLocal {
	@PersistenceContext
	private EntityManager entityManager;

    /**
     * Default constructor. 
     */
    public Carts() {
        // TODO Auto-generated constructor stub
    }
    
	@Override
	public boolean addCart(Cart cart) {
		if (findCartById(cart.getCartPk().getUser().getUserId(), cart.getCartPk().getProduct().getProductId()).isEmpty()) {
			entityManager.persist(cart);
			return true;
		}
		else{
			List<Cart> listCart = findCartById(cart.getCartPk().getUser().getUserId(), cart.getCartPk().getProduct().getProductId());
		
            if (listCart.get(0).getQuantity()+cart.getQuantity()<listCart.get(0).getCartPk().getProduct().getProductQuantity()) {
				
            	listCart.get(0).setQuantity(listCart.get(0).getQuantity()+cart.getQuantity());
        		updateCart(listCart.get(0));
    			return true;
            }
	
		}
		return false;
	}

	@Override
	public void updateCart(Cart cart) {
		entityManager.merge(cart);
	}

	@Override
	public void deleteCart(Cart cart) {
		entityManager.remove(cart);
		
	}

	@Override
	public void deleteCartById(int iduser,int idproduct) {
		String jpql = "delete FROM Cart c where c.cartPk.user.userId=:iduser and c.cartPk.product.productId=:idproduct and c.order IS NULL";
		Query query = entityManager.createQuery(jpql).setParameter("iduser", iduser).setParameter("idproduct", idproduct);
		query.executeUpdate();
		
	}
	@Override
	public void updateStat(StatType stat,int iduser,int idproduct) {
		String jpql = "update Cart c set c.stat =:stat where c.cartPk.user.userId=:iduser and c.cartPk.product.productId=:idproduct and c.order IS NOT NULL and c.stat!='approved'";
		Query query = entityManager.createQuery(jpql).setParameter("stat", stat).setParameter("iduser", iduser).setParameter("idproduct", idproduct);
		query.executeUpdate();
		
	}
	@Override
	public List<Cart> findCartById(int iduser,int idproduct) {
		// TODO Auto-generated method stub
		 String jpql = "select c FROM Cart c where c.cartPk.user.userId=:iduser and c.cartPk.product.productId=:idproduct and c.order IS NULL";
		Query query = entityManager.createQuery(jpql).setParameter("iduser", iduser).setParameter("idproduct", idproduct);
		List<Cart> resultList = query.getResultList();
		return resultList;
	}
	@Override
	public Cart findCartByIdInOrder(int iduser,int idproduct,int orderId) {
		// TODO Auto-generated method stub
		 String jpql = "select c FROM Cart c where c.cartPk.user.userId=:iduser and c.cartPk.product.productId=:idproduct and c.order.orderId =:orderId ";
		Query query = entityManager.createQuery(jpql).setParameter("iduser", iduser).setParameter("idproduct", idproduct).setParameter("orderId", orderId);
		Cart resultList = (Cart) query.getResultList().get(0);
		return resultList;
	}
	@Override
	public List<Cart> getUserCart(int iduser) {
		 String jpql = "select c FROM Cart c where c.cartPk.user.userId=:iduser and c.order IS NULL";
			Query query = entityManager.createQuery(jpql).setParameter("iduser", iduser);
			List<Cart> resultList = query.getResultList();
			return resultList;
	}
	
	@Override
	public List<Cart> getOwnedProductsPerOrder(int iduser) {
		 String jpql = "select c FROM Cart c where c.cartPk.product.user.userId=:iduser and c.order.orderId IS NOT NULL and c.stat !='approved' order by(c.order.orderId)";
			Query query = entityManager.createQuery(jpql).setParameter("iduser", iduser);
			List<Cart> resultList = query.getResultList();
			return resultList;
	}
	@Override
	public List<Cart> getSaledProducts(int iduser) {
		 String jpql = "select c FROM Cart c where c.cartPk.product.user.userId=:iduser and c.order.orderId IS NOT NULL and c.stat ='approved' order by(c.order.orderId)";
			Query query = entityManager.createQuery(jpql).setParameter("iduser", iduser);
			List<Cart> resultList = query.getResultList();
			return resultList;
	}

}
