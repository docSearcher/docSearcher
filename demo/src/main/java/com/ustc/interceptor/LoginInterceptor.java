package com.ustc.interceptor;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;

public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handle) throws Exception {
		//直接在这里面进行过滤的处理，公有资源的处理可以让未登录用户进行操作处理
		/*
		 * 2.静态资源要保证可以查看的,放行的通过
		 */
		if(handle instanceof DefaultServletHttpRequestHandler)
			return true;
		/*
		 * 添加可以使用公用操作的url（直接在配置文件中拦截两个操作（文件下载，文件上传））
		 */
		Set<String> publicReset = new HashSet<String>();
		publicReset.add("/user/login");
		publicReset.add("/person/save.do");
		publicReset.add("/login");
		publicReset.add("/user/login/get");
		publicReset.add("/supload");
		publicReset.add("/upload/get");
		publicReset.add("/supload/get");
		//publicReset.add("/2c96f18358b51db10158b51e2c9a0000/get");
		String path = request.getServletPath();
		if(publicReset.contains(path)){
			return true;
		}
		HttpSession session = request.getSession();
		
		Object user = session.getAttribute("username");
		if(user == null){
			response.sendRedirect("/user/login");
            return false;
			//throw new Exception();
			//需要跳转到登录界面return false ;
		}			
		return true;
	}

}
