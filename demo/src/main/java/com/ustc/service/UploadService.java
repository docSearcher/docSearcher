package com.ustc.service;

import java.io.File;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ustc.domain.SinglePagePptItem;
import com.ustc.domain.Uploadfileinfo;
import com.ustc.domain.User;

public interface UploadService {
	
	public void uploadFile(MultipartFile mutipartFile,String path,User user);
	
	//public void saveFileInfo();
	
	public void createPng(File file,String fileName,String path,User user);
	
	public File imageToPdf(String[] paths,String absolutePath);
	
	public List<Uploadfileinfo> getPageListItem(Integer ids);

	public List<Uploadfileinfo> getPageListItem(Integer userId, Integer page);
}
