package services;

import java.util.List;

import javax.ejb.Local;

import entities.Cart;
import entities.StatType;

@Local
public interface CartsLocal {

	boolean addCart(Cart cart);

	void updateCart(Cart cart);

	void deleteCart(Cart cart);

	void deleteCartById(int iduser,int idproduct);

	List<Cart> findCartById(int iduser,int idproduct);
	
	List<Cart> getUserCart(int iduser);
	List<Cart> getOwnedProductsPerOrder(int iduser);
	Cart findCartByIdInOrder(int iduser,int idproduct,int orderId);
	void updateStat(StatType stat,int iduser,int idproduct);
	List<Cart> getSaledProducts(int iduser);

}
