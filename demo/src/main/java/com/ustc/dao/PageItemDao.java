package com.ustc.dao;

import java.util.List;

import com.ustc.domain.SinglePagePptItem;
import com.ustc.domain.User;

public interface PageItemDao extends BaseDao<SinglePagePptItem> {

	//保存操作
	public void savePageItem(SinglePagePptItem page);
	
	public Integer getEntityPageItem(String filePath,Integer id);
	//删除操作,根据url或者文件名进行删除
	public void removePageItem(User user,String fileName);
	
	//根据hql获取列表
	public List<SinglePagePptItem> getPageListItem(Integer ids);

	public List<SinglePagePptItem> getPageListItem(Integer userId, Integer firstRows, Integer lastRows);
}
