package com.ustc.dao;

import java.util.List;

public interface BaseDao<T> {
     /*
      * 1.保存操作（独立保存）
      * 2.更新操作
      * 3.查询操作，根据用户id
      * 4.lucene检索操作
      * 5.删除操作
      */
    
	/**
	 * 根据hql语句查询List
	 * @param sql
	 * @param param
	 * @return
	 */
	List<T> getListHql(String hql,Object...param);
	
	/**
	 * 根据hql语句查询单个实体类
	 * @param hql
	 * @param param
	 * @return
	 */
	T getEntityByHql(String hql,Object...param);
	
	/**
	 * 根据id查询单个实体类
	 * @param id
	 * @return
	 */
	T getEntityById(String id);
	
	/**
	 * 根据hql语句对其进行更新操作
	 * @param hql
	 * @param param
	 */
	void updateEntityByHql(String hql,Object...param);
	
	/**
	 * 根据实体对对象进行更新
	 * @param t
	 */
	void updateEntity(T t);
	
	/**
	 * 删除实体对象
	 * @param t
	 */
	void removeEntity(T t);
	
	/**
	 * 根据id对实体类进行删除操作
	 * @param id
	 */
	void removeEntityById(String id);
	
	/**
	 * 对实体类进行保存操作
	 * @param t
	 */
	void saveEntity(T t);
	
	
	
	
	
}
