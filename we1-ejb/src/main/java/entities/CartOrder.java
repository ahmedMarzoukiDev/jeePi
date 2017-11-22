package entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class CartOrder implements Serializable {


	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartOrder other = (CartOrder) obj;
		if (cart == null) {
			if (other.cart != null)
				return false;
		} else if (!cart.equals(other.cart))
			return false;
		if (cartOrderId != other.cartOrderId)
			return false;
		return true;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "CartOrder [cartOrderId=" + cartOrderId + ", cart=" + cart + "]";
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cartOrderId;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL,mappedBy="cartPk.cartOrder", orphanRemoval = true)
	private Cart cart;
	
	public int getCartOrderId() {
		return cartOrderId;
	}

	public void setCartOrderId(int cartOrderId) {
		this.cartOrderId = cartOrderId;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	
	
}
