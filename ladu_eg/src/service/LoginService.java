package service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import db.UserAccount;

public class LoginService {
	private UserAccount user;

	public LoginService(){
	
	}

	

	public UserAccount getUser(){
	return user;
	}
	/*
	 * parooli hashimine
	 */
	
	public String hashPassword(String password){
		String hashedPassword=null;
		if(password==null) return null;
		try {
			MessageDigest digest=MessageDigest.getInstance("MD5");
			digest.update(password.getBytes(), 0, password.length());
			hashedPassword=new BigInteger(1,digest.digest()).toString(16);
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			
		}
		
		return hashedPassword;
	}

}
