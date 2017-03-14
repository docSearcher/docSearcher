package com.ustc.dao.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.ustc.dao.PageItemDao;
import com.ustc.domain.SinglePagePptItem;
import com.ustc.domain.User;

@Repository
public class PageItemDaoImpl extends BaseDaoImpl<SinglePagePptItem> implements PageItemDao {

	@Override
	public void savePageItem(SinglePagePptItem page) {
		// TODO Auto-generated method stub
		super.saveEntity(page);
	}

	@Override
	public void removePageItem(User user,String fileName) {
		// TODO Auto-generated method stub
		Set<User>  userList  = new HashSet<User>();
		userList.add(user);
		String hql = "From SinglePagePptItem page where page.filePath =?";
		SinglePagePptItem pageItem = (SinglePagePptItem) getQuery(hql, fileName).uniqueResult();
		pageItem.setUser(userList);
		removeEntity(pageItem);

	}
	
	public Integer getEntityPageItem(String filePath,Integer id){
		String sql = "SELECT p.* FROM SinglePagepptItem p INNER JOIN User_Pptitem up ON (up.item_id = p.item_id)"
				+ "INNER JOIN User u ON(u.user_id = up.user_id) WHERE u.user_id =? and p.filepath =?";
		List<SinglePagePptItem> item = super.getListSql(sql, id,filePath);
		if(item ==null){
			return 0;
		}else
		return item.size();
		
		
	}

	@Override
	public List<SinglePagePptItem> getPageListItem(Integer ids) {
		// TODO Auto-generated method stub
		//初始偏移量0,10条，10,10,20,10,判断页面与总数据之间的关系，是否可以进行翻页处理的
		
		String sql = "SELECT p.* FROM SinglePagepptItem p INNER JOIN User_Pptitem up ON (up.item_id = p.item_id)"
				+ "INNER JOIN User u ON(u.user_id = up.user_id) WHERE u.user_id =? order by p.updatetime desc";
		List<SinglePagePptItem> item = super.getListSql(sql, ids);
		return item;
	}

	
	@Override
	public List<SinglePagePptItem> getPageListItem(Integer ids, Integer firstRows, Integer lastRows) {
		String sql = "SELECT p.* FROM SinglePagepptItem p INNER JOIN User_Pptitem up ON (up.item_id = p.item_id)"
				+ "INNER JOIN User u ON(u.user_id = up.user_id) WHERE u.user_id =? order by p.updatetime desc";
		List<SinglePagePptItem> item = super.getPageListSql(sql,firstRows,lastRows, ids);
		return item;
	}

}
