package com.ustc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ustc.domain.SinglePagePptItem;
import com.ustc.domain.User;
import com.ustc.service.PageItemService;
import com.ustc.service.User_Service;
import com.ustc.utils.ResponseUtils;

@Controller
public class FileItemHandle {
	@Autowired
	private PageItemService pageItemService;
	@Autowired
	private User_Service userService;

	/**
	 * 获取收藏列表
	 * 
	 * @param user_id
	 * @return
	 */
	public @ResponseBody Object listItem(@RequestParam("user_id") Integer user_id,
			@RequestParam(value = "page", required = false, defaultValue = "1") Integer page) {
		Map<String, Object> jsonObject = new HashMap<String, Object>();
		if (user_id != null) {
			List<SinglePagePptItem> listItem = pageItemService.getListPageItem(user_id);
			jsonObject.put("pptItem", listItem);
		}
		return jsonObject;
	}

	/**
	 * 收藏条目
	 * 
	 * @param pageItem
	 * @param token
	 * @param request
	 * @param response
	 * @version 2016年12月22日下午8:00:39
	 * @author 田飞
	 */
	@RequestMapping(value = "favor/{token}", method = RequestMethod.POST)
	public void favorItem(@RequestParam("big_cover") String filePath, @RequestParam("file_content") String itemContent,
			@PathVariable("token") String token, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> jsonObject = new HashMap<String, Object>();
		ResponseUtils utils = new ResponseUtils();
		SinglePagePptItem pageItem = new SinglePagePptItem();
		pageItem.setFilePath(filePath);
		pageItem.setItemContent(itemContent);
		User user = userService.getUserToken(token);
		if (user == null) {
			jsonObject.put("code", "400");
			jsonObject.put("msg", "用户未登录，请先登录");
		} else {
			// 判断是否已经收藏，若已经收藏，收藏失败
			Integer items = this.pageItemService.getSinglePageItem(filePath, user);
			if (items == 1) {
				jsonObject.put("msg", "已收藏，请勿重复收藏");
				jsonObject.put("code", "400");
			} else {
				pageItem.setItemId(null);
				pageItemService.savePageItem(pageItem, user);
				jsonObject.put("msg", "收藏成功");
				jsonObject.put("code", "200");
			}

		}
		utils.renderJson(response, jsonObject);
	}

	/**
	 * 取消收藏条目,根据用户取消
	 * @param token
	 * @param fileName
	 * @param request
	 * @param response
	 * @version 2016年12月22日下午8:00:53
	 * @author 田飞
	 */
	@RequestMapping(value="favors/{token}", method = RequestMethod.POST)
	public void cancelFavorItem(@PathVariable("token") String token,@RequestParam("big_cover") String filePath,
			@RequestParam("file_content") String itemContent,
			HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> jsonObject = new HashMap<String,Object>();
		ResponseUtils utils  = new ResponseUtils();
		User user  = userService.getUserToken(token);
		if(user == null){
			jsonObject.put("status", "400");
			jsonObject.put("msg", "用户未登录，请先登录");
		}else{
			Integer items = this.pageItemService.getSinglePageItem(filePath, user);
			if(items ==0){
				jsonObject.put("status", "400");
				jsonObject.put("msg", "请先收藏");
			}
			else{
				pageItemService.removePageItem(user,filePath);
				//pageItemService.removeFavorItem(id);
				jsonObject.put("msg", "取消收藏");
				jsonObject.put("status", "300");
			}
			
		}
		utils.renderJson(response,jsonObject);
	}

	// TODO
	/**
	 * 获取收藏列表,分页获取数据,考虑总页数
	 * 
	 * @param token
	 * @param request
	 * @param response
	 * @version 2016年12月22日下午8:01:12
	 * @author 田飞
	 */
	@RequestMapping(value = "favor/{token}", method = RequestMethod.GET)
	public void getFavorList(@PathVariable("token") String token,
			@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> jsonObject = new HashMap<String, Object>();
		ResponseUtils utils = new ResponseUtils();
		User user = userService.getUserToken(token);
		if (user == null) {
			jsonObject.put("status", 400);
			jsonObject.put("msg", "用户未登录，请先登录");
		} else {
			List<SinglePagePptItem> listItem = pageItemService.getListPageItem(user.getUserId(), page);
			jsonObject.put("pptPageItem", listItem);
			jsonObject.put("status", 200);
		}

		utils.renderJson(response, jsonObject);
	}
}
