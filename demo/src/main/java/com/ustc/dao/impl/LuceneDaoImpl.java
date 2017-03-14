package com.ustc.dao.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.Version;
import org.springframework.stereotype.Repository;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.ustc.dao.LuceneDao;
import com.ustc.domain.Picinfo;
import com.ustc.domain.Uploadfileinfo;
import com.ustc.utils.ToolLuceneUtils;

@Repository
public class LuceneDaoImpl extends BaseDaoImpl<Picinfo> implements LuceneDao {
    private  IndexWriter indexWriter;
    private  IndexSearcher indexSearcher ;
    
	@Override
	public void addIndex(Document document) {
		// TODO Auto-generated method stub
		    indexWriter = ToolLuceneUtils.getIndexWriter();
        try {
			indexWriter.addDocument(document);
			ToolLuceneUtils.closeIndexWriter(indexWriter);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}

	@Override
	public List<Picinfo> getDocumentList(String keyword,int start,int rows)  {
		// TODO Auto-generated method stub
		indexSearcher  = ToolLuceneUtils.getIndexSerarcher();
		List<Picinfo> picList = new ArrayList<Picinfo>();
		QueryParser  queryParse = new QueryParser(Version.LATEST,"fileContent",ToolLuceneUtils.getAnalyzer());
		try {
			Query q = queryParse.parse(keyword);
			TopDocs hits = indexSearcher.search(q ,start+rows);
			//判断总的数据条数，给定查询的数据条数为1000条
			int totalhits = indexSearcher.search(q, 1000).scoreDocs.length;
			ScoreDoc[] scoreDocs = hits.scoreDocs;
			int endResult = Math.min(scoreDocs.length, start+rows);
			for(int i=start;i<endResult;i++){
				int docId =scoreDocs[i].doc;  
				Document doc = indexSearcher.doc(docId);
				Picinfo picinfo = ToolLuceneUtils.documnetToPicInfo(doc);
				picList.add(picinfo);
				//picinfo.set
			}
			
			//可以考虑是否高亮处理
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return picList;
	}

}
