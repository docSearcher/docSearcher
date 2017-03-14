package com.ustc.service.impl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.poi.hslf.usermodel.HSLFShape;
import org.apache.poi.hslf.usermodel.HSLFSlide;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.hslf.usermodel.HSLFTextParagraph;
import org.apache.poi.hslf.usermodel.HSLFTextRun;
import org.apache.poi.hslf.usermodel.HSLFTextShape;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;
import com.ustc.dao.LuceneDao;
import com.ustc.dao.UploadDao;
import com.ustc.domain.Dividepresspicinfo;
import com.ustc.domain.Picinfo;
import com.ustc.domain.SinglePagePptItem;
import com.ustc.domain.Uploadfileinfo;
import com.ustc.domain.User;
import com.ustc.exception.FileNotExistException;
import com.ustc.service.UploadService;
import com.ustc.utils.ConvertDate;
import com.ustc.utils.GloablNames;
import com.ustc.utils.ToolLuceneUtils;

@Service
public class UploadFileServiceImpl implements UploadService {
    @Autowired
	private UploadDao uploadDao;
    @Autowired
    private LuceneDao luceneDao;
	@Override
	public void uploadFile(MultipartFile mutipartFile,String absolutePath,User user) {
		// TODO Auto-generated method stub
		MultipartFile file =mutipartFile;
		Uploadfileinfo uploadInfo = new Uploadfileinfo();
		if (file != null) {
			String fileName = file.getOriginalFilename();
			Long size = file.getSize();
			StringBuilder sb = new StringBuilder(absolutePath);
			String path = "";
			if(System.getProperties().getProperty("os.name").toLowerCase().startsWith("win"))
			    path = sb.append("upload\\").append(ConvertDate.convertDate()).toString();
			else
				path = sb.append("upload/").append(ConvertDate.convertDate()).toString();	
			File files = new File(path, UUID.randomUUID().toString().replace("-", "")
					+ fileName.substring(fileName.lastIndexOf(".")));
			/*
			 * 将文件进行读取操作,文件读入到新文件中
			 */
			if (!files.exists())
				files.getParentFile().mkdirs();
			try {
				file.transferTo(files);
				uploadInfo.setFileUploadtime(new Date());
				uploadInfo.setUser(user);
				uploadInfo.setFileSize(size.toString());
				//uploadInfo.setFileUrl(path);
				uploadInfo.setFileUrl(files.getAbsolutePath());
				uploadInfo.setFileName(fileName);
				uploadInfo.setFileFormats(fileName.substring(fileName.lastIndexOf(".")));
				
				//对文件进行解析处理
				FileInputStream fis = new FileInputStream(files);
				int slidenum = -1;
				float scale = 1.0F;
				
				if(fileName.contains("ppt") && !fileName.contains("pptx")){
					HSLFSlideShow ppt = new HSLFSlideShow(fis);
					fis.close();
					Dimension pgsize = ppt.getPageSize();
					
					int width = (int) ((float) pgsize.width * scale);
					int height = (int) ((float) pgsize.height * scale);
					/*Set<Picinfo> picSet =new HashSet<Picinfo>();
					Set<Dividepresspicinfo> picPressSet = new HashSet<Dividepresspicinfo>();
					*/Iterator iterator = ppt.getSlides().iterator();
					do {
						Picinfo picInfo = new Picinfo();
						StringBuilder pageText = new StringBuilder().append("");
						Dividepresspicinfo pressinfo = new Dividepresspicinfo();						
						if (!iterator.hasNext())
							break;
						HSLFSlide slide = (HSLFSlide) iterator.next();
						List<HSLFShape> shape = slide.getShapes();
						for (HSLFShape hslfShape : shape) {
							// 如果是表单同样要考虑将内容字体设置成中文
							if (hslfShape instanceof HSLFTextShape) {
								HSLFTextShape txtshape = (HSLFTextShape) hslfShape;								
								System.out.println("text:" + txtshape.getText());
								pageText.append(txtshape.getText()== null?"":txtshape.getText());
								for (HSLFTextParagraph textPara : txtshape.getTextParagraphs()) {
									List<HSLFTextRun> textRunList = textPara.getTextRuns();
									for (HSLFTextRun textRun : textRunList) {
										textRun.setFontFamily("宋体");
									}
								}
							}
						}						
						if (slidenum == -1 || slidenum == slide.getSlideNumber()) {
							String title = slide.getTitle();
							System.out
									.println((new StringBuilder()).append("Rendering slide ").append(slide.getSlideNumber())
											.append(title != null
													? (new StringBuilder()).append(": ").append(title).toString() : "")
											.toString());
							BufferedImage img = new BufferedImage(width, height, 1);
							BufferedImage img_press = new BufferedImage((int) (width * 0.4), (int) (height * 0.4), 1);

							Graphics2D graphics = img.createGraphics();
							Graphics2D graphics_press = img_press.createGraphics();
							graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
							graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
							graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
									RenderingHints.VALUE_INTERPOLATION_BICUBIC);
							graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
									RenderingHints.VALUE_FRACTIONALMETRICS_ON);
							graphics.setPaint(Color.white);
							graphics_press.setPaint(Color.white);
							graphics.fill(new java.awt.geom.Rectangle2D.Float(0.0F, 0.0F, width, height));
							graphics.scale((double) width / (double) pgsize.width,
									(double) height / (double) pgsize.height);
							slide.draw(graphics);
							graphics_press
									.fill(new java.awt.geom.Rectangle2D.Float(0.0F, 0.0F, width * 0.4F, height * 0.4F));
							graphics_press.scale((double) width * 0.4 / (double) pgsize.width,
									(double) height * 0.4 / (double) pgsize.height);
							slide.draw(graphics_press);
							String fname = absolutePath ;
							String dbpath = "";
							if(System.getProperties().getProperty("os.name").toLowerCase().startsWith("win")){
							    dbpath = new StringBuilder().append("upload\\").append(ConvertDate.convertDate()).append("\\").append(UUID.randomUUID().toString().replace("-", ""))
										.append(".png").toString();
								fname = fname + dbpath;
							}
							else{
								//数据库存储路径
								dbpath = new StringBuilder().append("upload/").append(ConvertDate.convertDate()).append("/").append(UUID.randomUUID().toString().replace("-", ""))
										.append(".png").toString();
							    //绝对路径
								fname = fname + dbpath	;
							}
							String dbpressName = dbpath.replace(".png", "_press.png");
							//压缩图片绝对路径
							String pressName = fname.replace(".png", "_press.png");
							FileOutputStream out = new FileOutputStream(fname);
							
							FileOutputStream out_press = new FileOutputStream(pressName);
							ImageIO.write(img, "png", out);

							ImageIO.write(img_press, "png", out_press);
							out.close();
							out_press.close();
							//picInfo.setFileName(fname.substring(fname.lastIndexOf("\\")+1));
							picInfo.setFileContent(pageText.toString());
							picInfo.setFilePageNumber(slide.getSlideNumber());
							//设置数据库存储路径
							picInfo.setFileUrl(dbpath);
							if(user!=null)
							picInfo.setCreateBy(user.getUserId()==null?"":user.getUserId().toString());
							picInfo.setCreateTime(new Date());
							
							//pressinfo.setFilename(fname.substring(fname.lastIndexOf("\\")+1).replace(".png", "_press.png"));
							//压缩图片的数据库存储路径
							pressinfo.setFileUrl(dbpressName);
							if(user!=null)
							pressinfo.setCreateBy(user.getUserId()==null?"":user.getUserId().toString());
							pressinfo.setCreateTime(new Date());	
						}
						//对数据做索引操作
						luceneDao.addIndex(ToolLuceneUtils.pptInfotoDocument(picInfo));
						
						uploadInfo.getPicinfos().add(picInfo);
						uploadInfo.getDividepresspicinfos().add(pressinfo);
					} while (true);					
				}

				if(fileName.contains("pptx")){
					
				}
				//对文件进行统一保存操作
				uploadDao.saveEntity(uploadInfo);
				//数据保存完以后再单独做lucene索引操作
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//return files;
		}
		else
		{
			throw  new FileNotExistException("文件不存在");
		}
				
		
      
	}

	@Override
	public void createPng(File file,String fileName,String path,User user) {
		// TODO Auto-generated method stub

	}
	
	
	public File imageToPdf(String[] paths,String absolutePath) {
		Document document = new Document(new Rectangle(720, 540));
		File file = new File("imagesPDF.pdf");
		try {
			PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();
			if(paths != null && paths.length >0){
				for(String path :paths){
					if(path!=null && path.contains("http://115.159.152.87:8080/demo")){
						if(System.getProperties().getProperty("os.name").toLowerCase().startsWith("win")){
							path = path.substring(32).replace("/", "\\");
						}else
							path = path.substring(32);
					}	
					path = absolutePath +path;
					document.newPage();
					Image image = Image.getInstance(path);
					image.setAlignment(Image.MIDDLE);
					image.scalePercent(90);
					document.add(image);
				}
			}
		} catch (DocumentException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if(document != null)
				document.close();
		}
		return file;
	}
	
	public List<Uploadfileinfo> getPageListItem(Integer ids){
		List<Uploadfileinfo> item = uploadDao.getListFile(ids);
		
		return item;
	}

	@Override
	public List<Uploadfileinfo> getPageListItem(Integer ids, Integer page) {
		Integer firstRows = GloablNames.PAGESIZE *(page -1);
		Integer lastRows = GloablNames.PAGESIZE *page;		
		List<Uploadfileinfo> item = uploadDao.getListFile(ids,firstRows,lastRows);
		return item;
	}

}
