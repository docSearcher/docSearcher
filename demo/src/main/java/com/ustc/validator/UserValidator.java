package com.ustc.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.ustc.domain.User;

public class UserValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors error) {
		// TODO Auto-generated method stub
		ValidationUtils.rejectIfEmpty(error,"userName",null,"Username is empty.");
		User user = (User)obj;
		if(null==user.getPassword()||"".equals(user.getPassword())){
			error.rejectValue("password",null,"Password is empty.");
		}
		if(null==user.getMobile()||"".equals(user.getMobile().trim())){
			error.rejectValue("phone",null,"phone is empty.");
		}
	}

}
