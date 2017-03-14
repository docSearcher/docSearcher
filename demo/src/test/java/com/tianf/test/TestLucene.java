package com.tianf.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.ustc.dao.LuceneDao;
import com.ustc.dao.impl.LuceneDaoImpl;
import com.ustc.domain.Picinfo;
import com.ustc.service.LuceneService;
import com.ustc.service.impl.LuceneServiceImpl;
import com.ustc.utils.ToolLuceneUtils;

public class TestLucene {
 
	private LuceneDao luceneDao = new LuceneDaoImpl();
	//private LuceneService luceneService = new LuceneServiceImpl();
	@Test
	public void testCreateIndex(){
		Picinfo picInfo = new Picinfo();
		picInfo.setFileName("springmvc");
		picInfo.setFileContent("这是配置第一个springmvc项目的设想");
		picInfo.setFilePageNumber(55);
		picInfo.setFileUrl("c:\\demo\\haha");
		luceneDao.addIndex(ToolLuceneUtils.pptInfotoDocument(picInfo));
		System.out.println("生成成功");
	}
	
	@Test
	public void testSearch(){
		String keyword ="设想";
		Map<Object, Object> jsonObject = new HashMap<Object,Object>();
		List<Picinfo> list = luceneDao.getDocumentList(keyword);
		jsonObject.put("list", list);
		for(Picinfo picinfo:list){
			System.out.println(picinfo.getFileContent());
		}
             //jsonObject;
	}
}
