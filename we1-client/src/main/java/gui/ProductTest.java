package gui;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import entities.Cart;
import entities.CartOrder;
import entities.Order;
import entities.Product;
import entities.User;
import services.*;
public class ProductTest {

	public static void main(String[] args) throws NamingException  {
		// TODO Auto-generated method stub
		
		
		System.out.println("houniii111");
			Context context = new InitialContext();
			ProductsRemote productService = (ProductsRemote) context.lookup("we1-ear/we1-ejb/Products!services.ProductsRemote");
			
			Product p = new Product();
			p.setProductName("ahmed");
			p.setProductDescription("test2");
			p.setUnitPrice(13);
			p.setProductQuantity(2);
			p.setLat(22.33);
			p.setLng(22.33);
			p.setLocation("test");
			User u = new User();
			u.setUserId(1);
			p.setUser(u);
			Product p2 = new Product();
			p2.setProductName("samir");
			p2.setProductDescription("test2");
			p2.setUnitPrice(13);
			p2.setProductQuantity(2);
			p2.setUser(u);
			Product p3 = new Product();
			p3.setProductName("mohsen");
			p3.setProductDescription("test2");
			p3.setUnitPrice(13);
			p3.setProductQuantity(2);
			p3.setUser(u);
			Product p4 = new Product();
			p4.setProductName("jiji");
			p4.setProductDescription("test2");
			p4.setUnitPrice(13);
			p4.setProductQuantity(2);
			p4.setUser(u);
			Product p5 = new Product();
			p5.setProductName("haifa");
			p5.setProductDescription("test2");
			p5.setUnitPrice(13);
			p5.setProductQuantity(2);
			p5.setUser(u);
			productService.addProduct(p);
			productService.addProduct(p2);
			productService.addProduct(p3);
			productService.addProduct(p4);
			productService.addProduct(p5);
			
	}

}
