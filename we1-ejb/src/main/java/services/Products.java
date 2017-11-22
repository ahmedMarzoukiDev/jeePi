package services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entities.Product;


/**
 * Session Bean implementation class Products
 */
@Stateless
public class Products implements ProductsRemote, ProductsLocal {
	@PersistenceContext
	private EntityManager entityManager;

    /**
     * Default constructor. 
     */
    public Products() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public void addProduct(Product product) {
		// TODO Auto-generated method stub
		entityManager.persist(product);
	}

	@Override
	public void updateProduct(Product product) {
		// TODO Auto-generated method stub
		entityManager.merge(product);
		
	}

	@Override
	public void deleteProduct(Product product) {
		
		entityManager.remove(product);
		
	}

	@Override
	public void deleteProductById(int id) {
		// TODO Auto-generated method stub
		entityManager.remove(findProductById(id));
		
	}

	@Override
	public Product findProductById(int id) {
		// TODO Auto-generated method stub
		return entityManager.find(Product.class, id);
	}

	@Override
	public List<Product> findAllProducts() {
		// TODO Auto-generated method stub
		String jpql = "SELECT u FROM Product u ";
		Query query = entityManager.createQuery(jpql);
		List<Product> resultList = query.getResultList();
		return resultList;
	}

	@Override
	public List<Product> findAllOwnedProducts(int userId) {
		String jpql = "SELECT u FROM Product u where u.user.userId =:userId";
		Query query = entityManager.createQuery(jpql).setParameter("userId", userId);
		List<Product> resultList = query.getResultList();
		return resultList;
	}

}
