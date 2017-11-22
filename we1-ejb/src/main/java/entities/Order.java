package entities;

import java.io.Serializable;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Order
 *
 */
@Entity
@Table(name="Orders")
public class Order implements Serializable {
	   



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((carts == null) ? 0 : carts.hashCode());
		result = prime * result + ((contrat == null) ? 0 : contrat.hashCode());
		result = prime * result + orderId;
		result = prime * result + ((sign == null) ? 0 : sign.hashCode());
		result = prime * result + userId;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (carts == null) {
			if (other.carts != null)
				return false;
		} else if (!carts.equals(other.carts))
			return false;
		if (contrat == null) {
			if (other.contrat != null)
				return false;
		} else if (!contrat.equals(other.contrat))
			return false;
		if (orderId != other.orderId)
			return false;
		if (sign == null) {
			if (other.sign != null)
				return false;
		} else if (!sign.equals(other.sign))
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", contrat=" + contrat + ", sign=" + sign + ", userId=" + userId
				+ ", carts=" + carts + "]";
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int orderId;
	private String contrat;
	private String sign;

	private int userId;
	@OneToMany(cascade = {CascadeType.ALL },fetch = FetchType.EAGER,mappedBy="order")
	private List<Cart> carts = new ArrayList<Cart>();
	
	public List<Cart> getCarts() {
		return carts;
	}
	public void setCarts(List<Cart> carts) {
		this.carts = carts;
	}

	private static final long serialVersionUID = 1L;

	public Order() {
		super();
	}   
	public int getOrderId() {
		return this.orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}   
	public String getContrat() {
		return this.contrat;
	}

	public void setContrat(String contrat) {
		this.contrat = contrat;
	}   
	public String getSign() {
		return this.sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}   

	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
   
}
