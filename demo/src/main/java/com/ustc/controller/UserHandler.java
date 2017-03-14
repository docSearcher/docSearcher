package com.ustc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ustc.domain.Picinfo;
import com.ustc.domain.User;
import com.ustc.exception.UserAlreadyExistsException;
import com.ustc.service.LuceneService;
import com.ustc.service.User_Service;
import com.ustc.utils.GloablMessage;
import com.ustc.utils.GloablNames;
import com.ustc.utils.ResponseUtils;
import com.ustc.validator.UserValidator;
import com.ustc.viewDomain.ViewSearcherItem;
import com.ustc.viewDomain.ViewUser;

@Controller
@RequestMapping("/user")
public class UserHandler {

	@Autowired
	private User_Service userService;
	@Autowired
	private LuceneService luceneService;

	/**
	 * 在控制器一端可以添加验证非空操作
	 * 
	 * @param binder
	 */
	@InitBinder
	public void initBinder(DataBinder binder) {
		binder.setValidator(new UserValidator());
	}

	/*
	 * 是否直接用json数据格式返回，多种操作实现返回json格式的数据 ,produces="application/json"
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public @ResponseBody Object regist(@Validated @RequestBody User user, BindingResult br,
			HttpServletRequest request) {
		String action = request.getParameter("action");
		if (br.hasErrors()) {
			return "/user/login";
		} else {
			if ("activate".equals(action)) {
				// 激活操作
				return "";
			} else {
				userService.regist(user);
				Map<String, String> jsonObject = new HashMap<String, String>();
				jsonObject.put("msg", "注册成功");
				return jsonObject;
			}

		}
		// userService.regist(user);

	}

	@RequestMapping(value = "/login/get", method = RequestMethod.GET)
	public @ResponseBody Object search(@RequestParam("keyword") String keyword,
			@RequestParam(value = "page", required = false, defaultValue = "1") String page) {
		Map<Object, Object> jsonObject = new HashMap<Object, Object>();
		// 在安卓端做处理即可，设置定时传递参数
		int current = Integer.parseInt(page);
		int firstResult = (current - 1) * GloablNames.PAGESIZE;
		int maxResult = current * GloablNames.PAGESIZE;
		List<ViewSearcherItem> list = luceneService.searchList(keyword, firstResult, maxResult);
		int totalPage = list.size() / GloablNames.PAGESIZE;
		jsonObject.put("list", list);
		return jsonObject;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody Object login(@RequestParam("phone") String phone, @RequestParam("pwd") String password,
			HttpSession session) {
		if (phone.toString().trim() == "" || phone == null || password == "" || password == null) {
			return "/user/login";
		} else {
			User user = userService.login(phone, password);
			session.setAttribute("user", user);
			Map<Object, Object> jsonObject = new HashMap<Object, Object>();
			if (!user.getActiveId().equals("1")) {
				jsonObject.put("msg", "用户未激活，请先激活");
				return jsonObject;
			} else {
				session.setAttribute("user", user);
				// 設置sessionId,登录将这个id传给客户端，如果session过期要重新进行登录
				session.setAttribute("tokenId", session.getId());
				jsonObject.put("msg", "注册成功");
				jsonObject.put("tokenId", session.getId());
				jsonObject.put("user", user);
				return jsonObject;
			}

		}
	}

	@RequestMapping(value = "/loginOut", method = RequestMethod.GET)
	public String loginOut(HttpServletRequest request) {
		request.getSession().invalidate();
		// 注销同时要把token 的id设置为null
		return "redirect:/user/login";
	}
	// 获取上传文件列表信息
	/*
	 * @RequestMapping(value ="/{user_id}",method =RequestMethod.GET) public
	 * String getFileList(@PathVariable("user_id") String user_id){
	 * 
	 * return "redirect:/user/login"; }
	 */

	/**
	 * 手机用户注册
	 * 
	 * @param user
	 * @param request
	 * @param response
	 * @version 2016年12月21日 下午8:28:31
	 * @author 田飞
	 */
	@RequestMapping(value = "/phone/register", method = RequestMethod.POST)
	public void phoneLogin(@Validated @RequestBody User user, BindingResult br, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> jsonObject = new HashMap<String, Object>();
		ResponseUtils utils = new ResponseUtils();
		if (br.hasErrors()) {
			// jsonObject.put("", value)
			jsonObject.put("code", 400);
			jsonObject.put("msg", "注册信息不能为空");
		} else {
			try {
				userService.regist(user);
				jsonObject.put("code", 200);
				jsonObject.put("msg", "注册成功");
				jsonObject.put("user", new ViewUser(user));
			} catch (UserAlreadyExistsException e) {
				jsonObject.put("code", 300);
				jsonObject.put("msg", GloablMessage.USER_NAME_ALREADY_EXISTS);
				jsonObject.put("user", null);
			}

		}
		utils.renderJson(response, jsonObject);
	}

	@RequestMapping(value = "/phoneRegister", method = RequestMethod.POST)
	public void phoneRegister(@RequestParam("user_name") String userName, @RequestParam("phone") String phone,
			@RequestParam("pwd") String password, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> jsonObject = new HashMap<String, Object>();
		ResponseUtils utils = new ResponseUtils();
		User user = new User();
		if (userName.trim() == "" || userName == null || password.trim() == "" || password == null || phone.trim() == ""
				|| phone == null) {
			jsonObject.put("code", 400);
			jsonObject.put("msg", "注册信息不能为空");
		} else {
			user.setPassword(password);
			user.setMobile(phone);
			user.setUserName(userName);
			try {
				userService.regist(user);
				jsonObject.put("code", 200);
				jsonObject.put("msg", "注册成功");
				jsonObject.put("user", new ViewUser(user));
			} catch (UserAlreadyExistsException e) {
				jsonObject.put("code", 300);
				jsonObject.put("msg", GloablMessage.USER_NAME_ALREADY_EXISTS);
				jsonObject.put("user", null);
			}
		}

		utils.renderJson(response, jsonObject);
	}

	/**
	 * 设置用户serverId 手机用户登录
	 * 
	 * @param username
	 * @param password
	 * @param request
	 * @param response
	 * @version 2016年12月21日下午11:32:57
	 * @author 田飞
	 */
	@RequestMapping(value = "/phoneLogin", method = RequestMethod.POST)
	public void doLogin(@RequestParam("phone") String phone, @RequestParam("pwd") String password,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> jsonObject = new HashMap<String, Object>();
		ResponseUtils utils = new ResponseUtils();
		if (null != password && password != "") {
			password = DigestUtils.md5Hex(password);
		}
		User user = userService.login(phone, password);

		if (user != null) {

			jsonObject.put("user", new ViewUser(user));
			jsonObject.put("code", 200);
			jsonObject.put("msg", "登录成功");
		} else {
			jsonObject.put("code", 400);
			jsonObject.put("msg", "用户名或密码错误");
		}

		utils.renderJson(response, jsonObject);
	}

	// 刷新页面操作
	@RequestMapping(value = "{token}", method = RequestMethod.GET)
	public void queryByToken(@PathVariable("token") String token, HttpServletResponse response) {
		Map<String, Object> jsonObject = new HashMap<String, Object>();
		ResponseUtils utils = new ResponseUtils();
		User user = userService.getUserToken(token);
		if (user != null) {
			jsonObject.put("user", new ViewUser(user));
			jsonObject.put("code", 200);
			jsonObject.put("msg", "刷新成功");
		} else {
			jsonObject.put("user", new ViewUser(user));
			jsonObject.put("code", 400);
			jsonObject.put("msg", "刷新失败，用户未登录");
		}
		utils.renderJson(response, jsonObject);
	}

	// 注销操作，要返回数据
	@RequestMapping(value = "/loginout", method = RequestMethod.GET)
	public void doLoginOut(@RequestParam(value = "token", required = false, defaultValue = "123456789") String token,
			HttpServletRequest request, HttpServletResponse response) {
		request.getSession().invalidate();
		Map<String, Object> jsonObject = new HashMap<String, Object>();
		ResponseUtils utils = new ResponseUtils();
		User user = userService.getUserToken(token);
		if (user == null) {
			// jsonObject.put("user", new ViewUser(user));
			jsonObject.put("code", 200);
			jsonObject.put("msg", "退出成功");
		}
		utils.renderJson(response, jsonObject);
	}

	// 修改密码，确保用户名是存在的
	@RequestMapping(value = "{token}", method = RequestMethod.POST)
	public void updatePassword(@PathVariable("token") String token, @RequestParam("old_pwd") String oldPwd,
			@RequestParam("new_pwd") String password, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> jsonObject = new HashMap<String, Object>();
		ResponseUtils utils = new ResponseUtils();
		User user = userService.getUserToken(token);
		if (null != user) {
			if (password != null && password != "")
				password = DigestUtils.md5Hex(password);
			user.setPassword(password);
			user = userService.updatePassword(user);
			jsonObject.put("msg", "修改成功");
			jsonObject.put("user", new ViewUser(user));
			jsonObject.put("code", 200);
		} else {
			jsonObject.put("msg", "修改失败");
			jsonObject.put("code", 400);
		}
		utils.renderJson(response, jsonObject);
	}
	//TODO
	//删除用户，用户权限不同
	public void userManager(){
		
	}

}
