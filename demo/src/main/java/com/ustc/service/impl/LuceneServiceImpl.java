package com.ustc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ustc.dao.LuceneDao;
import com.ustc.domain.Picinfo;
import com.ustc.service.LuceneService;
import com.ustc.viewDomain.ViewSearcherItem;

@Service
public class LuceneServiceImpl implements LuceneService {
    @Autowired
	private LuceneDao luceneDao;
	@Override
	public void addLuceneIndex(Document document) {
		// TODO Auto-generated method stub
		luceneDao.addIndex(document);

	}

	@Override
	public List<ViewSearcherItem> searchList(String keywords,int start,int rows) {
		// TODO Auto-generated method stub
		List<Picinfo> list = new ArrayList<Picinfo>();
		
		List<ViewSearcherItem> items = new ArrayList<ViewSearcherItem>();
		list = luceneDao.getDocumentList(keywords,start,rows);
		for(Picinfo pinInfo : list){
			//对搜索出来的内容进行处理
			if(pinInfo != null && pinInfo.getFileContent().trim().length()>0){
				if(pinInfo.getFileContent().trim().length()>30)
				{
					pinInfo.setFileContent(pinInfo.getFileContent().substring(0, 30)+"...");
				}
				ViewSearcherItem item = new ViewSearcherItem(pinInfo);
				items.add(item);
			}
			
		}
		return items;
	}

}
