package com.ustc.service;

import java.util.List;

import org.apache.lucene.document.Document;

import com.ustc.domain.Picinfo;
import com.ustc.viewDomain.ViewSearcherItem;

public interface LuceneService {
	
	public void addLuceneIndex(Document document);
	
	public List<ViewSearcherItem> searchList(String keywords,int start,int rows);
}
