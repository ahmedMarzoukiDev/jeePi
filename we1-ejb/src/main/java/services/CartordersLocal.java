package services;

import javax.ejb.Local;

import entities.Cart;
import entities.CartOrder;

@Local
public interface CartordersLocal {
	public void addCartOrder(CartOrder co,Cart cart);
	public CartOrder findOrderById(int id);
	public void deleteCartOrderById(int cartOrderId);
}
