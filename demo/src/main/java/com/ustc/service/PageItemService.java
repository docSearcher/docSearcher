package com.ustc.service;

import java.util.List;

import com.ustc.domain.SinglePagePptItem;
import com.ustc.domain.User;

public interface PageItemService {
    
	public Integer getSinglePageItem(String filepath,User user);
	/**
	 * 页面收藏操作
	 * @param page
	 */
	public void savePageItem(SinglePagePptItem page,User user);
	
	/**
	 * 取消对某条记录的删除操作
	 * @param fileName
	 */
	public void removePageItem(User user,String fileName);
	
	/**
	 * 根据用户id获取收藏的pageItem
	 * @param userId
	 * @return
	 */
	public  List<SinglePagePptItem> getListPageItem(Integer userId);
	
	//根据id取消收藏
	public void removeFavorItem(String ids);

	public List<SinglePagePptItem> getListPageItem(Integer userId, Integer page);
}
