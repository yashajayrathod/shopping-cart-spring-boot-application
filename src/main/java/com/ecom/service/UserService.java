package com.ecom.service;

import java.util.List;

import com.ecom.model.UserDtls;

public interface UserService{
	
	public UserDtls saveUser(UserDtls user);
	public UserDtls getUserByEmail(String email);
	public List<UserDtls> getUser(String role);
	public Boolean updateAccountStatus(Integer id, Boolean status);
	public void increaseFailedAttempt(UserDtls user);
	public void userAccountLock(UserDtls user);
	public boolean unlockAccountTimeExpired(UserDtls user);
	public void restAttempt(int userId);
	public void updateUserResetToken(String email, String resetToken);
	public UserDtls getUserByToken(String token);
	public UserDtls updateUser(UserDtls user);

}
