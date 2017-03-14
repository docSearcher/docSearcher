package com.ustc.exception;

public class UserLoginFailedException extends RuntimeException {

	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 登录异常操作
	 */
	public UserLoginFailedException(String message){
		super(message);
	}

}
