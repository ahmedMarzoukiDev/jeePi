package services;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entities.User;

/**
 * Session Bean implementation class Users
 */
@Stateless
public class Users implements UsersRemote, UsersLocal {

	@PersistenceContext
	private EntityManager entityManager;
    /**
     * Default constructor. 
     */
    public Users() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public User authentification(String username, String pwd) {
		String jpql = "SELECT z FROM User z WHERE z.name=:username and z.password=:pwd";
		Query query = entityManager.createQuery(jpql);
		query.setParameter("username", username);
		query.setParameter("pwd", pwd);
		User user= null;
		try {
		  user=(User)query.getSingleResult();

	
} catch (Exception e) {
	// TODO: handle exception
}

		return user;
	}

}
