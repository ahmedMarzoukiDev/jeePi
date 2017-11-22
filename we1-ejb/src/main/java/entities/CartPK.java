package entities;

import java.io.Serializable;


import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Embeddable
public class CartPK implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user; 
	
	@ManyToOne 
	@JoinColumn(name = "productId")
	private Product product;

	@OneToOne
	@JoinColumn(name="cartOrderId")
	private CartOrder cartOrder;
	
	public User getUser() {
		return user;
	}
	public CartOrder getCartOrder() {
		return cartOrder;
	}
	public void setCartOrder(CartOrder cartOrder) {
		this.cartOrder = cartOrder;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {

		this.product = product;
	}
	
	
}
