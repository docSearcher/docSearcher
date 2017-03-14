package com.ustc.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class ResponseUtils {
	public void render(HttpServletResponse response,String contentType,Object object){
		Gson gson = new Gson();
		response.setContentType(contentType);		
		try {
			PrintWriter writer =null;
			writer = response.getWriter();
			writer.write(gson.toJson(object));
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//发送json
	public void renderJson(HttpServletResponse response,Object object){
		render(response,"application/json;charset =UTF-8",object);
	}
	
	//发送xml
	public void renderXML(HttpServletResponse response,Object object){
		render(response,"application/xml;charset =UTF-8",object);
	}
	//发送文本
	public void renderPlain(HttpServletResponse response,Object object){
		render(response,"application/plain;charset =UTF-8",object);
	}
}
