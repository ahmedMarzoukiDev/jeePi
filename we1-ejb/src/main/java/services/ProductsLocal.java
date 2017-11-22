package services;

import java.util.List;

import javax.ejb.Local;

import entities.Product;

@Local
public interface ProductsLocal {

	void addProduct(Product product);

	void updateProduct(Product product);

	void deleteProduct(Product product);

	void deleteProductById(int id);

	Product findProductById(int id);

	List<Product> findAllProducts();

	List<Product> findAllOwnedProducts(int userId);

}
