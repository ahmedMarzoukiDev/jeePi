package ctr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

import entities.Cart;
import entities.CartOrder;
import entities.Product;
import entities.StatType;
import entities.User;
import services.CartordersLocal;
import services.CartsLocal;
import services.ProductsLocal;

@ManagedBean
@ViewScoped	
public class CartsManagement {
	@EJB
	private CartsLocal cartsService;
	@EJB
	private ProductsLocal productsService;
	@EJB
	private CartordersLocal cartOrdersService;
	private Cart cart = new Cart();
	private String popupMessage;
	private DualListModel<Cart> cartsList;
	private List<Cart> cartSource = new ArrayList<Cart>();
    private List<Cart> cartTarget = new ArrayList<Cart>();
	
    @PostConstruct
    public void Init(){
    	 cartSource = cartsService.getUserCart(1);
         cartsList = new DualListModel<Cart>(cartSource, cartTarget);
    }
    
    
	public List<Cart> getCartSource() {
		return cartSource;
	}
	public void setCartSource(List<Cart> cartSource) {
		this.cartSource = cartSource;
	}
	public List<Cart> getCartTarget() {
		return cartTarget;
	}
	public void setCartTarget(List<Cart> cartTarget) {
		this.cartTarget = cartTarget;
	}
	public CartsManagement() {
		// TODO Auto-generated constructor stub
	}
	public void addCart(int productId) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		User user = (User) session.getAttribute("user");
		Product product = new Product();
		product = productsService.findProductById(productId);
		cart.getCartPk().setProduct(product);
		cart.setQuantity(1);
		cart.getCartPk().setUser(user);
		cart.setStat(StatType.pending);
        CartOrder co = new CartOrder();
		cartOrdersService.addCartOrder(co,cart);
		cart.getCartPk().setCartOrder(co);
		
		if (cartsService.addCart(cart)) {
			
			
		}else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info!", "Le stock a été épuisé."));

		}
		FacesContext context = FacesContext.getCurrentInstance();
        
        context.addMessage(null, new FacesMessage("Successful",  "the product : "+product.getProductName()+" is successfuly added to your cart") );
		
	}
	
	public void onTransfer(TransferEvent event) {
        StringBuilder builder = new StringBuilder();
        for(Object item : event.getItems()) {
            builder.append(((Cart) item).getCartPk().getProduct().getProductName()).append("<br />");   
        }
         
        FacesMessage msg = new FacesMessage();
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        msg.setSummary("Items Transferred");
        msg.setDetail(builder.toString());
         
        FacesContext.getCurrentInstance().addMessage(null, msg);
    } 
	

	public void updateQuantity(int quantity,int productId,int userId) throws IOException{
		Cart c = new Cart();
		c = cartsService.findCartById(userId, productId).get(0);
		if(productsService.findProductById(productId).getProductQuantity()<quantity){
			FacesContext context = FacesContext.getCurrentInstance();
	        
	        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"error",  "introduced quantity : "+quantity+" exceeds quantity in stock :"+productsService.findProductById(productId).getProductQuantity()) );
	        
		}else{
			c.setQuantity(quantity);
			cartsService.updateCart(c);	
		}
		
	}
	
	public String deleteProductFromCart(int productId,int userId,int cartOrderId)
	{
		cartOrdersService.deleteCartOrderById(cartOrderId);
		cartsService.deleteCartById(userId, productId);
		return"displayCart?faces-redirect=true";
	}

	
	public Double totalProductPrice(int quantity,Double unitPrice)
	{
		return quantity*unitPrice;
	}
	
	public Cart getCart() {
		return cart;
	}
	public void setCart(Cart cart) {
		this.cart = cart;
	}
	public String getPopupMessage() {
		return popupMessage;
	}
	public void setPopupMessage(String popupMessage) {
		this.popupMessage = popupMessage;
	}
	public DualListModel<Cart> getCartsList() {
		return cartsList;
	}
	public void setCartsList(DualListModel<Cart> cartsList) {
		this.cartsList = cartsList;
	}


}
