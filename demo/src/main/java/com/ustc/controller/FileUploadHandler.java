package com.ustc.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.ustc.domain.SinglePagePptItem;
import com.ustc.domain.Uploadfileinfo;
import com.ustc.domain.User;
import com.ustc.service.UploadService;
import com.ustc.service.User_Service;
import com.ustc.utils.GloablMessage;
import com.ustc.utils.GloablNames;
import com.ustc.utils.ResponseUtils;

/*@RequestMapping("upload")*/
@Controller
@RequestMapping("/dofile")
public class FileUploadHandler {
    @Autowired
	private UploadService uploadService;
    @Autowired
    private User_Service userService;
	/**
	 * 异步上传文件不需要返回参数
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 * @createTime 2016年12月18日
	 * @author 田飞
	 */
    //MultipartFile[] files,
    @RequestMapping(value = "/supload", method = RequestMethod.POST)
	public @ResponseBody String Upload(HttpServletRequest request, HttpServletResponse response)
			throws IllegalStateException, IOException {
    	String message =null;
		//获取项目部署路径
		try{
			String path = request.getSession().getServletContext().getRealPath("/upload");
		   //String path = "c:\\demo\\";
			User user = (User) request.getSession().getAttribute("user");
			// 创建一个通用的多部分解析器
			CommonsMultipartResolver commonsMutipart = new CommonsMultipartResolver(
					request.getSession().getServletContext());
			if (commonsMutipart.isMultipart(request)) {
				MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
				Iterator<String> iterator = multipart.getFileNames();
				while (iterator.hasNext()) {
					MultipartFile file = multipart.getFile(iterator.next());
					uploadService.uploadFile(file, path,user);
					
				}

			}
			//上传成功返回,返回的各种格式的处理，json，xml，text；
			return "上传成功";
		}
		catch(Exception e){
			//上传解析失败处理
			message = "上传出现异常";
		}
		
		return message;
	}
    /**
     * 下载文件使用post处理，由于传递的参数比较大
     * @param filePath
     * @param response
     * 创建时间：2016年12月02日
     * 作者：田飞
     */
    @RequestMapping(value = "/supload/get", method = RequestMethod.GET)
    public void downloadFile(@RequestParam("filePath") String[] filePath,HttpServletRequest request,HttpServletResponse response){
    	OutputStream out = null;
    	String path =request.getSession().getServletContext().getRealPath("/");
    	File file = uploadService.imageToPdf(filePath,path);
    	try {
			response.reset();
			response.setContentType("application/octet-stream; charset=utf-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
			out = response.getOutputStream();
			out.write(FileUtils.readFileToByteArray(file));
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
    	
    	/**
    	 * JSONObject jo = new JSONObjct();
    	 * jo.put("");
    	 * response.getWriter.writer(jo.toString());
    	 */
    	
    }
    
    @RequestMapping(value = "/{user_id}/get", method = RequestMethod.GET)
    public @ResponseBody Object getFileList(@PathVariable("user_id") Integer user_id){
    	Map<String, Object> jsonObject = new HashMap<String, Object>();
		if (user_id != null) {
			List<Uploadfileinfo> listItem = uploadService.getPageListItem(user_id);
			jsonObject.put("pptItem", listItem);
		}
		return jsonObject;
    }
    /**
     * 上传文件
     * @param token
     * @param request
     * @param response
     * @version 2016年12月22日下午12:06:00
     * @author 田飞
     */
    @RequestMapping(value="{token}",method = RequestMethod.POST)
    public void uploadFile(@PathVariable("token") String token,
    		HttpServletRequest request, HttpServletResponse response){
    	Map<String, Object> jsonObject = new HashMap<String,Object>();
		ResponseUtils utils  = new ResponseUtils();
		User user  = userService.getUserToken(token);
		if(null == user){
			jsonObject.put("status", "400");
			jsonObject.put("msg", "用户未登录，请先登录");
		}else
		{
			try{
				String path = request.getSession().getServletContext().getRealPath("/");
				//String path = "c:\\demo\\";				
				// 创建一个通用的多部分解析器
				CommonsMultipartResolver commonsMutipart = new CommonsMultipartResolver(
						request.getSession().getServletContext());
				if (commonsMutipart.isMultipart(request)) {
					MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
					Iterator<String> iterator = multipart.getFileNames();
					while (iterator.hasNext()) {
						MultipartFile file = multipart.getFile(iterator.next());
						uploadService.uploadFile(file, path,user);						
					}
				}
				//上传成功返回,返回的各种格式的处理，json，xml，text；
				jsonObject.put("status", "200");
				jsonObject.put("msg", "上传成功");
			}
			catch(Exception e){
				//上传解析失败处理
				jsonObject.put("status", "400");
				jsonObject.put("msg", "上传异常，请重新上传");
			}
		}
		utils.renderJson(response, jsonObject);
    }
    /**
     * 下载文件，传过来的路径会否是绝对路径
     * @param token
     * @param filePath
     * @param response
     * @version 2016年12月22日下午12:06:17
     * @author 田飞
     */
    @RequestMapping(value="/downfile/{token}",method = RequestMethod.POST)
    public void downFile(@PathVariable("token") String token,@RequestParam("filePath") String[] filePath,HttpServletRequest request,HttpServletResponse response){
    	Map<String, Object> jsonObject = new HashMap<String,Object>();
    	String path =request.getSession().getServletContext().getRealPath("/");
		ResponseUtils utils  = new ResponseUtils();
		User user  = userService.getUserToken(token);
		if(null == user){
			jsonObject.put("status", "400");
			jsonObject.put("msg", "用户未登录，请先登录");
		}else{
			OutputStream out = null;
	    	File file = uploadService.imageToPdf(filePath,path);
	    	try {
				response.reset();
				response.setContentType("application/octet-stream; charset=utf-8");
				response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
				out = response.getOutputStream();
				out.write(FileUtils.readFileToByteArray(file));
				out.flush();
			} catch (IOException e) {
				//e.printStackTrace();
				jsonObject.put("status", "400");
				jsonObject.put("msg", "下载异常，请重新下载");
				utils.renderJson(response, jsonObject);
			} finally {
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
    }
    //TODO
    //获取上传文件列表，同样分页获取数据
    @RequestMapping(value="{token}",method = RequestMethod.GET)
    public void getUploadFileList(@PathVariable("token") String token,HttpServletResponse response,
    		@RequestParam(value ="page",required =false ,defaultValue ="1")Integer page){
    	Map<String, Object> jsonObject = new HashMap<String,Object>();
		ResponseUtils utils  = new ResponseUtils();
		User user  = userService.getUserToken(token);
		if(null == user){
			jsonObject.put("status", "400");
			jsonObject.put("msg", "用户未登录，请先登录");
		}else{
			List<Uploadfileinfo> listItem = uploadService.getPageListItem(user.getUserId(),page);
			jsonObject.put("pptFileItem", listItem);
			jsonObject.put("status", "200");
			jsonObject.put("msg", "成功获取数据列表");
		}
		utils.renderJson(response, jsonObject);
    }

    /**
     * 用户头像上传
     * @param pic
     * @param request
     * @param response
     * @version 2016年12月22日下午3:49:27
     * @author 田飞
     */
    @RequestMapping(value="upload/uploadPic",method = RequestMethod.POST)
    public void uploadPic(@RequestParam(value= "file" ,required =false) MultipartFile pic,HttpServletRequest request,HttpServletResponse response){
    	Map<String, Object> jsonObject = new HashMap<String,Object>();
		ResponseUtils utils  = new ResponseUtils();
    	String ext = FilenameUtils.getExtension(pic.getOriginalFilename());
    	DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    	String format = df.format(new Date());
    	
    	Random r = new Random();
    	for(int i=0 ;i<3; i++){
    		format += r.nextInt(10);
    	}
    	String path = request.getSession().getServletContext().getRealPath("upload");
    	String fileName = format+"."+ext;
    	File targetFile  = new File(path,fileName);
    	
    	if(!targetFile .exists()){  
    		targetFile .mkdirs();  
        }
    	
    	try {
			pic.transferTo(targetFile);
			jsonObject.put("status", "300");
			jsonObject.put("msg", "上传成功");
			jsonObject.put("url", fileName);
    	}catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
    		jsonObject.put("status", "400");
			jsonObject.put("msg", "上传异常，请重新上传");
		}
    	
    	utils.renderJson(response, jsonObject);
    }
}
