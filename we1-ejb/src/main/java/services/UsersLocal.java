package services;

import javax.ejb.Local;

import entities.User;


@Local
public interface UsersLocal {

	 User authentification(String username,String pwd);
}
