package com.ustc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ustc.domain.Picinfo;
import com.ustc.service.LuceneService;
import com.ustc.utils.GloablNames;
import com.ustc.utils.ResponseUtils;
import com.ustc.viewDomain.ViewSearcherItem;
@Controller
public class FileSearchController {
    @Autowired
	private LuceneService luceneService;
	
	@RequestMapping(value="/search",method=RequestMethod.GET)
	public void search(@RequestParam(value ="keyword",required = false, defaultValue = "to,get,hello,over" ) String keyword,@RequestParam(value = "page", required = false, defaultValue = "1")  Integer page,
			HttpServletResponse response){
		Map<String, Object> jsonObject = new HashMap<String,Object>();
		ResponseUtils utils  = new ResponseUtils();
		//int current = Integer.parseInt(page);
		int current = page;
		int firstResult = (current -1)*GloablNames.PAGESIZE;
		int maxResult = GloablNames.PAGESIZE;
		List<ViewSearcherItem> list = luceneService.searchList(keyword,firstResult,maxResult);
		
		int totalPage = (firstResult+list.size())/GloablNames.PAGESIZE;
		if(list.size()% GloablNames.PAGESIZE == 0){
			totalPage = (firstResult+list.size())/GloablNames.PAGESIZE;
		}
		else{
			totalPage = totalPage + 1;
		}
		int totalCount = list.size();
		
		if(null == list || list.size()<1){
			jsonObject.put("code", 300);
			jsonObject.put("msg", "未查询到记录");
			jsonObject.put("current_page",0);
			jsonObject.put("total_count",0);
			jsonObject.put("count_per_page", GloablNames.PAGESIZE);
			jsonObject.put("total_page",0);
			jsonObject.put("items", null);
		}else{
			jsonObject.put("code", 200);
			jsonObject.put("msg", "成功获取");
			jsonObject.put("current_page",current);
			jsonObject.put("total_count",totalCount);
			jsonObject.put("count_per_page", GloablNames.PAGESIZE);
			jsonObject.put("total_page",totalPage);
			jsonObject.put("items", list);
			System.out.println("记录数"+list.size());
		}
		utils.renderJson(response, jsonObject);
	}
}
