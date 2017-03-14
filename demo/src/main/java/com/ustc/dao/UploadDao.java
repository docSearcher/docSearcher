package com.ustc.dao;

import java.util.List;

import com.ustc.domain.Uploadfileinfo;

public interface UploadDao extends BaseDao<Uploadfileinfo>{
	/**
	 * 保存上传的文件信息
	 * @param uploadFile
	 */
	public void saveFileInfo(Uploadfileinfo uploadFile);
	
	/**
	 * 查看本用户上传的文件信息
	 * @param ids
	 * @return
	 */
	public List<Uploadfileinfo> getListFile(Integer ids);

	public List<Uploadfileinfo> getListFile(Integer ids, Integer firstRows,Integer lastRows);
}
