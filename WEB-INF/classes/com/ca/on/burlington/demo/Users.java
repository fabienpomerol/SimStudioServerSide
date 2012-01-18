/**
 * 
 */
package com.ca.on.burlington.demo;

import java.io.Serializable;

/**
 * @author Bonnard
 *
 */
public class Users implements Serializable {

	/**
	 * 
	 */
	private	static final long serialVersionUID = 1L;
	private int userId = 0;  
	private String loginName = null ;  
	private String loginPassword = null ; 
	
	
	public Users() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}


	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}


	/**
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}


	/**
	 * @param loginName the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}


	/**
	 * @return the loginPassword
	 */
	public String getLoginPassword() {
		return loginPassword;
	}


	/**
	 * @param loginPassword the loginPassword to set
	 */
	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

}
