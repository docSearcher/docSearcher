package com.ustc.viewDomain;

import com.ustc.domain.Picinfo;
import com.ustc.utils.GloablNames;

public class ViewSearcherItem {
	public static final int PRE_CODE = 10000000;
	// private Integer item_id;
	private String medium_cover;
	private String big_cover;
	private String file_content;

	

	public ViewSearcherItem(Picinfo picinfo) {
		// this.item_id = picinfo.getPicInfoId() +PRE_CODE;
		if (System.getProperties().getProperty("os.name").toLowerCase().startsWith("win")) {
			this.big_cover = GloablNames.SERVERPATH + picinfo.getFileUrl().replace("\\", "/");
			this.medium_cover = GloablNames.SERVERPATH
					+ picinfo.getFileUrl().replace(".png", "_press.png").replace("\\", "/");
			this.file_content = picinfo.getFileContent();

		} else {
			this.big_cover = GloablNames.SERVERPATH + picinfo.getFileUrl();
			this.medium_cover = GloablNames.SERVERPATH + picinfo.getFileUrl().replace(".png", "_press.png");
			this.file_content = picinfo.getFileContent();
		}
	}

	/*
	 * public Integer getItem_id() { return item_id; }
	 * 
	 * public void setItem_id(Integer item_id) { this.item_id = item_id; }
	 */

	public String getMedium_cover() {
		return medium_cover;
	}

	public void setMedium_cover(String medium_cover) {
		this.medium_cover = medium_cover;
	}

	public String getBig_cover() {
		return big_cover;
	}

	public void setBig_cover(String big_cover) {
		this.big_cover = big_cover;
	}
	
	public String getFile_content() {
		return file_content;
	}

	public void setFile_content(String file_content) {
		this.file_content = file_content;
	}

}
