package services;

import javax.ejb.Remote;

import entities.Cart;
import entities.CartOrder;

@Remote
public interface CartordersRemote {
	public void addCartOrder(CartOrder co,Cart cart);
	public CartOrder findOrderById(int id);
	public void deleteCartOrderById(int cartOrderId);
}
