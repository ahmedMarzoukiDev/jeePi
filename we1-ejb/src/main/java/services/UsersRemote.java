package services;

import javax.ejb.Remote;

import entities.User;

@Remote
public interface UsersRemote {

	User authentification(String username,String pwd);
}
