package ctr;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import entities.User;
import services.UsersLocal;


@ManagedBean
@SessionScoped
public class Identity {
	@EJB
	private UsersLocal userService;
	private User user = new User();
	private boolean isLogged ;

	public String logout() {
		isLogged = false;
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/userviews/login?faces-redirect=true";
	}
	public String doLogin() {
		String navigateTo = "";
		User userLoggedIn = userService.authentification(user.getName(), user.getPassword());
		if (userLoggedIn != null) {
			isLogged = true;
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			session.setAttribute("user", userLoggedIn);
				navigateTo = "/pages/productviews/displayOwnedProducts?faces-redirect=true";
			
		} else {
			navigateTo = "erreur?faces-redirect=true";
		
			System.err.println("not");
		}
		return navigateTo;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public boolean isLogged() {
		return isLogged;
	}

	public void setLogged(boolean isLogged) {
		this.isLogged = isLogged;
	}
}
