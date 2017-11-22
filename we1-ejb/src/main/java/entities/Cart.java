package entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Cart
 *
 */
@Entity
public class Cart implements Serializable {

	

	

	

	
	private static final long serialVersionUID = 1L;

	

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@Id
	private CartPK cartPk;
	private int quantity;
	@ManyToOne
	@JoinColumn(name="orderId")
	private Order order;
	public StatType getStat() {
		return stat;
	}

	public void setStat(StatType stat) {
		this.stat = stat;
	}

	@Enumerated(EnumType.STRING)
	private StatType stat;
	
	
	public CartPK getCartPk() {
		return cartPk;
	}

	public void setCartPk(CartPK cartPk) {
		this.cartPk = cartPk;
	}

	public Cart() {
		super();
		cartPk = new CartPK();
	}   
	
	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
	
   
}
