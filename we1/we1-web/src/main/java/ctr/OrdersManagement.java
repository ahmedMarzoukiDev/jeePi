package ctr;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.sun.mail.smtp.SMTPTransport;

import java.io.InputStream;
import java.io.OutputStream;

import entities.Cart;
import entities.Order;
import entities.Product;
import entities.StatType;
import entities.User;
import services.CartsLocal;
import services.OrdersLocal;
import services.ProductsLocal;

@ManagedBean
@ViewScoped
public class OrdersManagement {

	private List<Cart> cartL = new ArrayList<Cart>();
	private List<Order> orderList = new ArrayList<Order>();
	private static String contractName;
	private Cart cart = new Cart();
	@PostConstruct
	public void init() throws AddressException, MessagingException{
		notif();
	}
	@EJB
	private ProductsLocal productsService;
	
	
	private void notif() throws AddressException, MessagingException{
		 
		
		HttpSession session1 = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		User user = (User) session1.getAttribute("user");
		if (displaySales().size()>0) {
			Properties props = System.getProperties();
	        props.put("mail.smtps.host","smtp.gmail.com");
	        props.put("mail.smtps.auth","true");
	        Session session = Session.getInstance(props, null);
	        Message msg = new MimeMessage(session);
	        msg.setFrom(new InternetAddress("gprodbou6@gmail.com"));;
	        msg.setRecipients(Message.RecipientType.TO,
	        InternetAddress.parse("ahmed.marzouki@esprit.tn", false));
	        msg.setSubject("Heisann "+System.currentTimeMillis());
	        msg.setText("Med vennlig hilsennTov Are Jacobsen");
	        msg.setHeader("X-Mailer", "Tov Are's program");
	        msg.setSentDate(new Date());
	        SMTPTransport t =
	            (SMTPTransport)session.getTransport("smtps");
	        t.connect("smtp.gmail.com", "gprodbou6@gmail.com", "bou62971995");
	        t.sendMessage(msg, msg.getAllRecipients());
	        System.out.println("Response: " + t.getLastServerResponse());
	        t.close();
	
		}
		
			}
	
	
	public StreamedContent downloadContract(int id) {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		String name = id + ".pdf";
		String filepath = externalContext.getRealPath("") + File.separator + "resources" + File.separator + "img"
				+ File.separator + name;

		InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(filepath);
		StreamedContent file;
		file = new DefaultStreamedContent(stream, "application/pdf", name);
		return file;
	}

	public List<Order> displayOrderList() {
		orderList = orderService.findAllOwnedOrders(1);

		return orderList;
	}

	public String create() throws DocumentException, FileNotFoundException {

		Order o1 = new Order();
		o1 = orderService.findLastOrderByUserId(1);

		String name = o1.getOrderId() + ".pdf";
		contractName = name;
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		String filepath = externalContext.getRealPath("") + File.separator + "resources" + File.separator + "img"
				+ File.separator + name;
		System.out.println(filepath);

		Document document = new Document();
		File myFile = new File(filepath);
		myFile.setWritable(true);
		myFile.setReadable(true);
		myFile.setExecutable(true);

		FileOutputStream contractFile = new FileOutputStream(myFile);

		PdfWriter pdfWriter = PdfWriter.getInstance(document, contractFile);

		document.open();
		Paragraph paragraph = new Paragraph();
		paragraph.add("Purchase contract");
		paragraph.setAlignment(Element.ALIGN_CENTER);

		Paragraph otherParagraph = new Paragraph();
		double Somme = 0;
		for (Cart cart : o1.getCarts()) {
			Somme = Somme + (cart.getCartPk().getProduct().getUnitPrice() * cart.getQuantity());
			otherParagraph.add(cart.getCartPk().getProduct().getProductName());
			otherParagraph.add("\t \t \t");
			otherParagraph.add(Double.toString(cart.getCartPk().getProduct().getUnitPrice()));
			otherParagraph.add("\t \t \t");
			otherParagraph.add(Integer.toString(cart.getQuantity()));
			otherParagraph.add("\t \t \t");
			otherParagraph.add(Double.toString(cart.getCartPk().getProduct().getUnitPrice() * cart.getQuantity()));
			otherParagraph.add("\n");
		}
		otherParagraph.add(Double.toString(Somme));

		document.add(otherParagraph);

		document.close();

		return "displayOrderList?faces-redirect=true";

	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@EJB
	private OrdersLocal orderService;
	@EJB
	private CartsLocal cartService;
	private Order order = new Order();

	public void addOrder(List<Cart> cartList) throws IOException {
		setCartL(cartList);
		///////////////// add order///////////////////////
		Order o = new Order();
		int userId = cartList.get(0).getCartPk().getUser().getUserId();
		o.setUserId(userId);
		o.setContrat(contractName);
		o.setSign(order.getSign());
		orderService.addOrder(o);
		///////////////// end add order///////////////////
		//////////////// get last added order////////////////
		Order o1 = new Order();
		o1 = orderService.findLastOrderByUserId(userId);
		for (Cart cart : cartList) {
			cart.setOrder(o1);
			cartService.updateCart(cart);
		}
		FacesContext.getCurrentInstance().getExternalContext().redirect("/we1-web/pages/orderviews/displayOrders.jsf");

	}
	// A PDF to download

	/**
	 * This method reads PDF from the URL and writes it back as a response.
	 * 
	 * @throws IOException
	 */
	public void downloadPdf(int orderId) throws IOException {
		// Get the FacesContext
		FacesContext facesContext = FacesContext.getCurrentInstance();

		// Get HTTP response
		HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

		// Set response headers
		response.reset(); // Reset the response in the first place
		response.setHeader("Content-Type", "application/pdf"); // Set only the
																// content type

		// Open response output stream
		OutputStream responseOutputStream = response.getOutputStream();
		System.out.println(
				"gggggggggggggggggggg::::" + FacesContext.getCurrentInstance().getExternalContext().getRealPath(""));
		String PDF_URL = "http://localhost:18080/we1-web/resources/img/" + orderId + ".pdf";

		// Read PDF contents
		URL url = new URL(PDF_URL);
		InputStream pdfInputStream = url.openStream();

		// Read PDF contents and write them to the output
		byte[] bytesBuffer = new byte[2048];
		int bytesRead;
		while ((bytesRead = pdfInputStream.read(bytesBuffer)) > 0) {
			responseOutputStream.write(bytesBuffer, 0, bytesRead);
		}

		// Make sure that everything is out
		responseOutputStream.flush();

		// Close both streams
		pdfInputStream.close();
		responseOutputStream.close();

		// JSF doc:
		// Signal the JavaServer Faces implementation that the HTTP response for
		// this request has already been generated
		// (such as an HTTP redirect), and that the request processing lifecycle
		// should be terminated
		// as soon as the current phase is completed.
		facesContext.responseComplete();

	}

	public List<Cart> getCartL() {
		return cartL;
	}

	public void setCartL(List<Cart> cartL) {
		this.cartL = cartL;
	}

	public List<Cart> displaySales() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		User user = (User) session.getAttribute("user");
		List<Cart> sales = new ArrayList<Cart>();
		sales = cartService.getOwnedProductsPerOrder(user.getUserId());
		return sales;

	}

	public List<Cart> displaySaled() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		User user = (User) session.getAttribute("user");
		List<Cart> sales = new ArrayList<Cart>();
		sales = cartService.getSaledProducts(user.getUserId());
		return sales;

	}

	public String updateStat(int userId, int productId,int orderId) {
		System.out.println(cart.getStat());
		System.out.println("product id :"+productId);
		System.out.println("user Id :"+userId);
		System.out.println("orderId :"+orderId);
		Cart c = new Cart();
		c = cartService.findCartByIdInOrder(userId, productId,orderId);
		c.setStat(cart.getStat());
		cartService.updateStat(c.getStat(), c.getCartPk().getUser().getUserId(),
				c.getCartPk().getProduct().getProductId());

		
		return "Sales?faces-redirect=true";
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

}
