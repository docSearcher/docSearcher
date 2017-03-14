package com.ustc.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.List;

import org.apache.poi.hslf.usermodel.HSLFShape;
import org.apache.poi.hslf.usermodel.HSLFSlide;
import org.apache.poi.hslf.usermodel.HSLFTextParagraph;
import org.apache.poi.hslf.usermodel.HSLFTextRun;
import org.apache.poi.hslf.usermodel.HSLFTextShape;

public class SlideResolve {
	public static void resovePng(HSLFSlide slide, Dimension pgsize, int width, int height, int slidenum) {
		List<HSLFShape> shape = slide.getShapes();
		for (HSLFShape hslfShape : shape) {
			// 如果是表单同样要考虑将内容字体设置成中文
			if (hslfShape instanceof HSLFTextShape) {
				HSLFTextShape txtshape = (HSLFTextShape) hslfShape;
				System.out.println("text:" + txtshape.getText());
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
			System.out.println((new StringBuilder()).append("Rendering slide ").append(slide.getSlideNumber())
					.append(title != null ? (new StringBuilder()).append(": ").append(title).toString() : "")
					.toString());
			BufferedImage img = new BufferedImage(width, height, 1);
			BufferedImage img_press = new BufferedImage((int) (width * 0.2), (int) (height * 0.2), 1);

			Graphics2D graphics = img.createGraphics();
			Graphics2D graphics_press = img_press.createGraphics();
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
			graphics.setPaint(Color.white);
			graphics_press.setPaint(Color.white);
			graphics.fill(new java.awt.geom.Rectangle2D.Float(0.0F, 0.0F, width, height));
			graphics.scale((double) width / (double) pgsize.width, (double) height / (double) pgsize.height);
			slide.draw(graphics);
			graphics_press.fill(new java.awt.geom.Rectangle2D.Float(0.0F, 0.0F, width * 0.2F, height * 0.2F));
			graphics_press.scale((double) width * 0.2 / (double) pgsize.width,
					(double) height * 0.2 / (double) pgsize.height);
			slide.draw(graphics_press);
		}
	}
}
