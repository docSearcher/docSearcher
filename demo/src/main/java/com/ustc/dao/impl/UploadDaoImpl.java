package com.ustc.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ustc.dao.UploadDao;
import com.ustc.domain.Uploadfileinfo;

@Repository
public class UploadDaoImpl extends BaseDaoImpl<Uploadfileinfo> implements UploadDao {

	@Override
	public void saveFileInfo(Uploadfileinfo uploadFile) {
		// TODO Auto-generated method stub
		saveEntity(uploadFile);
	}
    //总数据条数
	@Override
	public List<Uploadfileinfo> getListFile(Integer ids) {
		// TODO Auto-generated method stub

		String sql = "SELECT fileinfo.* FROM UploadFileInfo fileinfo LEFT JOIN USER u ON u.user_id "
				+ "=fileinfo.`user_id` WHERE u.user_id= ?";
		List<Uploadfileinfo>  fileList = getListSql(sql, ids);		
		return fileList;
	}
    //获取分页数据
	@Override
	public List<Uploadfileinfo> getListFile(Integer ids, Integer firstRows,Integer lastRows) {
		String sql = "SELECT fileinfo.* FROM UploadFileInfo fileinfo LEFT JOIN USER u ON u.user_id "
				+ "=fileinfo.`user_id` WHERE u.user_id= ? order by fileinfo.fileuploadtime desc";
		List<Uploadfileinfo>  fileList = super.getPageListSql(sql,firstRows,lastRows, ids);		
		return fileList;
	}
}
