package com.ustc.controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hslf.record.EscherTextboxWrapper;
import org.apache.poi.hslf.usermodel.HSLFShape;
import org.apache.poi.hslf.usermodel.HSLFSlide;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.hslf.usermodel.HSLFTable;
import org.apache.poi.hslf.usermodel.HSLFTableCell;
import org.apache.poi.hslf.usermodel.HSLFTextParagraph;
import org.apache.poi.hslf.usermodel.HSLFTextRun;
import org.apache.poi.hslf.usermodel.HSLFTextShape;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;

@Controller
public class FileUploadController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * 抓取文件,前台传入文件@RequestParam(value="filename") MultipartFile[] file_s,
	 */
	@RequestMapping(value = "/lupload", method = RequestMethod.POST)
	public @ResponseBody Object Upload(HttpServletRequest request, HttpServletResponse response)
			throws IllegalStateException, IOException {
		// 创建一个通用的多部分解析器
		CommonsMultipartResolver commonsMutipart = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (commonsMutipart.isMultipart(request)) {
			MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
			Iterator<String> iterator = multipart.getFileNames();
			while (iterator.hasNext()) {
				MultipartFile file = multipart.getFile(iterator.next());
				if (file != null) {
					String fileName = file.getOriginalFilename();
					// 文件存在以日期为单位
					String path_1 = getClass().getResource("/").getPath();
					String path = request.getSession().getServletContext().getRealPath("/");
					path = path + "upload/" + convertDate();
					File files = new File(path, UUID.randomUUID().toString().replace("-", "")
							+ fileName.substring(fileName.lastIndexOf(".")));
					/*
					 * 将文件进行读取操作,文件读入到新文件中
					 */
					if (!files.exists())
						files.getParentFile().mkdirs();
					/*
					 * byte[] bytes = file.getBytes(); BufferedOutputStream
					 * buffStream = new BufferedOutputStream(new
					 * FileOutputStream(files)); buffStream.write(bytes);
					 * buffStream.close();
					 */
					file.transferTo(files);
					files.getAbsolutePath();
					// 上传多个文件的处理,是否可以文件传完立马返回信息,将文件解析成jpg,
					FileInputStream fis = new FileInputStream(files);
					createPic(fis, fileName, request);
					fis.close();
					// BufferedReader buf = new BufferedReader(new
					// InputStreamReader(fis));

				}
			}
		}
		Map<String, String> jsonObject = new HashMap<String, String>();
		jsonObject.put("msg", "上传成功");
		return jsonObject;

		/*
		 * try{ //正常上传文件 }catch(Exception e){ //出现上传异常以后执行 }finally{
		 * //文件上传成功后对文件做一些列操作 }
		 */
	}

	/*
	 * 下载文件
	 */
	/*
	 * public void DownLoad(){
	 * 
	 * }
	 */
	public void createPic(FileInputStream fis, String fileName, HttpServletRequest request) {
		// 对于ppt和pptx要进行分别处理
		int slidenum = -1;
		float scale = 1.0F;
		String pageText = "";
		String path = request.getSession().getServletContext().getRealPath("/");
		if (fileName.contains("pptx")) {

		} else {
			try {
				HSLFSlideShow ppt = new HSLFSlideShow(fis);

				fis.close();
				Dimension pgsize = ppt.getPageSize();
				int width = (int) ((float) pgsize.width * scale);
				int height = (int) ((float) pgsize.height * scale);
				Iterator iterator = ppt.getSlides().iterator();
				do {
					if (!iterator.hasNext())
						break;
					HSLFSlide slide = (HSLFSlide) iterator.next();
					List<HSLFShape> shape = slide.getShapes();
					for (HSLFShape hslfShape : shape) {
						// 如果是表单同样要考虑将内容字体设置成中文
						/*if (hslfShape instanceof HSLFTable) {
							HSLFTable table = (HSLFTable) hslfShape;
							int column = table.getNumberOfColumns();
							int row = table.getNumberOfRows();
							for (int i = 0; i < row; i++) {
								for (int j = 0; j < column; j++) {
									HSLFTableCell tableCell = table.getCell(i, j);
									for (HSLFTextParagraph textPara : tableCell.getTextParagraphs()) {
										List<HSLFTextRun> textRunList = textPara.getTextRuns();
										for (HSLFTextRun textRun : textRunList) {
											// String s = textRun.getRawText();
											textRun.setFontFamily("宋体");
										}
									}
									//tableCell.getTextParagraphs()
									//tableCell.getTextParagraphs().get(0).getTextRuns().get(0).setFontFamily("宋体");
								}
							}
						}*/
						if (hslfShape instanceof HSLFTextShape) {
							HSLFTextShape txtshape = (HSLFTextShape) hslfShape;
							// System.out.println("txtshape" + (i+1) + ":" +
							// txtshape.getShapeName());
							System.out.println("text:" + txtshape.getText());
                            pageText += txtshape.getText();
							for (HSLFTextParagraph textPara : txtshape.getTextParagraphs()) {
								List<HSLFTextRun> textRunList = textPara.getTextRuns();
								for (HSLFTextRun textRun : textRunList) {
									// String s = textRun.getRawText();
									textRun.setFontFamily("宋体");
								}
							}
						}
					}
					/*
					 * Iterator iterators = slide.getShapes().iterator(); if
					 * (!iterators.hasNext()) break; else{ HSLFTextShape
					 * hslfText = (HSLFTextShape)iterators.next();
					 * //hslfText.getText()
					 * 
					 * }
					 */
					// slide.getSlideRecord();

					if (slidenum == -1 || slidenum == slide.getSlideNumber()) {
						String title = slide.getTitle();
						System.out
								.println((new StringBuilder()).append("Rendering slide ").append(slide.getSlideNumber())
										.append(title != null
												? (new StringBuilder()).append(": ").append(title).toString() : "")
										.toString());
						BufferedImage img = new BufferedImage(width, height, 1);
						BufferedImage img_press = new BufferedImage((int) (width * 0.2), (int) (height * 0.2), 1);

						Graphics2D graphics = img.createGraphics();
						Graphics2D graphics_press = img_press.createGraphics();
						// Graphics2D graphics_press =
						// img_press.createGraphics();
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
								.fill(new java.awt.geom.Rectangle2D.Float(0.0F, 0.0F, width * 0.2F, height * 0.2F));
						graphics_press.scale((double) width * 0.2 / (double) pgsize.width,
								(double) height * 0.2 / (double) pgsize.height);
						slide.draw(graphics_press);
						String fname = path + "upload\\" + convertDate() + "\\";
						fname = fname + new StringBuilder().append(UUID.randomUUID().toString().replace("-", ""))
								.append(".png");
						/*
						 * path = path +"upload/"+convertDate(); StringBuilder
						 * stringBuilder = new StringBuilder(path);
						 * stringBuilder.append(UUID.randomUUID().toString().
						 * replace("-", "")).append(".png"); String fname =
						 * stringBuilder.toString();
						 */

						// File files = new File(path
						// ,UUID.randomUUID().toString().replace("-",
						// "")+fileName.substring(fileName.lastIndexOf(".")));

						String pressName = fname.replace(".png", "_press.png");
						FileOutputStream out = new FileOutputStream(fname);
						FileOutputStream out_press = new FileOutputStream(pressName);
						ImageIO.write(img, "png", out);

						ImageIO.write(img_press, "png", out_press);
						out.close();
						out_press.close();
					}
				} while (true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public String createPath() {
		return "";
	}

	public String convertDate() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dateString = sdf.format(date);
		return dateString;
	}

	// 后台对上传的文件进行处理
	/*
	 * public boolean validFile(){ return false; }
	 */
	// 可以解决文件下载问题
	// ResponseEntity<byte[]>
	/*
	 * 传文件id过来进行处理即可 先将文件写入到ppt中,然后再进行下载
	 */
	@RequestMapping(value = "/upload/get", method = RequestMethod.GET)
	public void download(HttpServletResponse response) throws IOException, Exception {
		String path = "C:\\demo\\indexDirupload\\20161205\\0a5a32db155542cfbe26454984f67f7c.png";
		Document document = new Document(new Rectangle(720, 540));
		File file = new File("imagesPDF.pdf");
		PdfWriter.getInstance(document, new FileOutputStream(file));
		document.open();
		int i = 1;
		while (i < 5) {
			document.newPage();
			Image image = Image.getInstance(path);
			image.setAlignment(Image.MIDDLE);
			image.scalePercent(90);
			document.add(image);
			i++;
		}
		document.close();
		OutputStream out = null;
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

		
		/* HttpHeaders headers = new HttpHeaders(); String fileName=new
		 String("imagePDF.pdf".getBytes("UTF-8"),"iso-8859-1");//为了解决中文名称乱码问题
		 headers.setContentDispositionFormData("attachment", fileName);
		 headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); 
		 return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
		 }*/
	}
}
