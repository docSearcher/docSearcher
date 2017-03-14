package com.ustc.dao;

import java.util.List;

import org.apache.lucene.document.Document;
import org.springframework.stereotype.Repository;

import com.ustc.domain.Picinfo;


public interface LuceneDao extends BaseDao<Picinfo> {

	public void addIndex(Document document);

	public List<Picinfo> getDocumentList(String keyword,int start,int rows);
}
