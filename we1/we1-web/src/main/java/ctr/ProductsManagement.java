package ctr;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.ejb.EJB;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.DonutChartModel;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import entities.Product;
import entities.User;
import services.CartsLocal;
import services.ProductsLocal;

@ManagedBean
@ViewScoped
public class ProductsManagement implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EJB
	private CartsLocal cartsService;
	@EJB
	private ProductsLocal productsService;
	private Product product = new Product();
	private List<Product> productList;
	private MapModel emptyModel = new DefaultMapModel();
	private String title;

	private double lat;
	private double lng;
	private String location;
	private UploadedFile file;
	String name;

	public Product getProduct() {
		return product;
	}

	@PostConstruct
	public void init() {

	}

	public void addMarker() {
		Marker marker = new Marker(new LatLng(product.getLat(), product.getLng()), product.getLocation());
		emptyModel.addOverlay(marker);
		lat = marker.getLatlng().getLat();
		lng = marker.getLatlng().getLng();
		location = marker.getTitle();
		AddMarkerView.setLat(lat);
		AddMarkerView.setLng(lng);
		AddMarkerView.setLocation(location);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Marker Added", "Lat:" + lat + ", Lng:" + lng));
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public MapModel getEmptyModel() {
		return emptyModel;
	}

	public void setEmptyModel(MapModel emptyModel) {
		this.emptyModel = emptyModel;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	public List<Product> displayProducts() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		User user = (User) session.getAttribute("user");
		productList = productsService.findAllProducts();

		List<Product> p = new ArrayList<Product>();
		for (Product product : productList) {
			if (product.getUser().getUserId() != user.getUserId()) {
				p.add(product);
			}
		}
		return p;

	}

	public List<Product> displayOwnedProducts() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		User user = (User) session.getAttribute("user");
		productList = productsService.findAllOwnedProducts(user.getUserId());
		return productList;

	}

	public String modifyProduct() {
		product.setInitialQuantity(product.getProductQuantity());
		productsService.updateProduct(product);
		return "displayOwnedProducts?faces-redirect=true";

	}

	public String deleteProduct(int id) {
		
		productsService.deleteProductById(id);
		return "displayOwnedProducts?faces-redirect=true";

	}

	public void handleFileUpload(FileUploadEvent event) {
		name = event.getFile().getFileName();
		FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, message);

		String localPath = "D:" + File.separator + "4eme" + File.separator + "1er semestre" + File.separator + "JEE"
				+ File.separator + "workspace" + File.separator + "we1" + File.separator + "we1-web" + File.separator
				+ "src" + File.separator + "main" + File.separator + "webapp" + File.separator + "resources"
				+ File.separator + "images" + File.separator + name;

		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		String filepath = externalContext.getRealPath("") + File.separator + "resources" + File.separator + "images"
				+ File.separator + name;

		try (

				InputStream input = event.getFile().getInputstream()) {
			OutputStream os = new FileOutputStream(localPath);
			OutputStream osServer = new FileOutputStream(localPath);
			byte[] b = new byte[2048];
			int length;

			while ((length = input.read(b)) != -1) {
				os.write(b, 0, length);
				osServer.write(b, 0, length);
			}

			input.close();
			os.close();
			osServer.close();

		} catch (IOException e) {
			// Show faces message?
		}

	}

	public MapModel displayMap() {
		MapModel simpleModel = new DefaultMapModel();

		LatLng coordProduct = new LatLng(product.getLat(), product.getLng());
		System.out.println("*****************" + coordProduct);
		simpleModel.addOverlay(new Marker(coordProduct, product.getLocation()));
		return simpleModel;

	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public String addProduct() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		User user = (User) session.getAttribute("user");
		product.setUser(user);
		product.setLat(AddMarkerView.getLat());
		product.setLng(AddMarkerView.getLng());
		product.setLocation(AddMarkerView.getLocation());
		product.setImage(name);
		product.setInitialQuantity(product.getProductQuantity());
		product.setDateAdded(Calendar.getInstance().getTime());
		productsService.addProduct(product);
		return "displayOwnedProducts?faces-redirect=true";
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public DonutChartModel initDonutModel() {
		int stockWorth = 0;
		int totalCost = 0;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		User user = (User) session.getAttribute("user");
		List<Product> pl = new ArrayList<Product>();
		pl = productsService.findAllOwnedProducts(user.getUserId());
		for (Product product : pl) {
			stockWorth = (int) (stockWorth + (product.getInitialQuantity() * product.getUnitPrice()));
			totalCost = (int) (totalCost + (product.getInitialQuantity() * product.getUnitCost()));
		}
		DonutChartModel gainPerte = new DonutChartModel();
		Map<String, Number> circle1 = new LinkedHashMap<String, Number>();
		circle1.put("stock worth", stockWorth);
		circle1.put("total cost", totalCost);
		gainPerte.addCircle(circle1);
		gainPerte.setTitle("stockWorth/total cost");
		gainPerte.setLegendPosition("e");
		gainPerte.setSliceMargin(5);
		gainPerte.setShowDataLabels(true);
		gainPerte.setDataFormat("value");
		gainPerte.setShadow(false);
		return gainPerte;
	}

	public BarChartModel initBarModel() {
		BarChartModel sellingRate = new BarChartModel();
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		User user = (User) session.getAttribute("user");
		List<Product> pl = new ArrayList<Product>();
		pl = productsService.findAllOwnedProducts(user.getUserId());
		sellingRate.setTitle("selling rate");
		ChartSeries sr = new ChartSeries();
		sr.setLabel("selling rate");
		int per=0;
		for (Product product : pl) {
		per = (int)((product.getProductQuantity()/product.getInitialQuantity())*100);
			sr.set(product.getProductName(), per);
		}

		sellingRate.addSeries(sr);

		return sellingRate;
	}

}