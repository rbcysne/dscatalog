package com.devsup.dscatalog.dto;

public class UserInsertDTO extends UserDTO {

	private static final long serialVersionUID = 1L;

	private String password;
	
	public UserInsertDTO() {
		
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
