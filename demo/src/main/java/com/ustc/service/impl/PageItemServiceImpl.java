package com.ustc.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ustc.dao.PageItemDao;
import com.ustc.domain.SinglePagePptItem;
import com.ustc.domain.User;
import com.ustc.service.PageItemService;
import com.ustc.utils.GloablNames;
@Service
public class PageItemServiceImpl implements PageItemService {
    @Autowired
	private PageItemDao pageItemDao;
    
    public Integer getSinglePageItem(String filepath,User user){
    	return pageItemDao.getEntityPageItem(filepath, user.getUserId());
    }
	@Override
	public void savePageItem(SinglePagePptItem page, User user) {
		// TODO Auto-generated method stub
		Set<User>  userList  = new HashSet<User>();
		userList.add(user);
		page.setUser(userList);
        pageItemDao.savePageItem(page);
	}

	@Override
	public void removePageItem(User user,String fileName) {
		// TODO Auto-generated method stub
		pageItemDao.removePageItem(user,fileName);
	}

	@Override
	public List<SinglePagePptItem> getListPageItem(Integer userId) {
		// TODO Auto-generated method stub
		List<SinglePagePptItem> item = pageItemDao.getPageListItem(userId);
		return item;
	}

	@Override
	public void removeFavorItem(String ids) {
		// TODO Auto-generated method stub
		pageItemDao.removeEntityById(ids);
		//return false;
	}

	@Override
	public List<SinglePagePptItem> getListPageItem(Integer userId, Integer page) {
		Integer firstRows = GloablNames.PAGESIZE *(page -1);
		Integer lastRows = GloablNames.PAGESIZE *page;
		List<SinglePagePptItem> item = pageItemDao.getPageListItem(userId,firstRows,lastRows);
		return item;
	}

}
