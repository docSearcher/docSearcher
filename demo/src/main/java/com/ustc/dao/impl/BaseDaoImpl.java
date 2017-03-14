package com.ustc.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;

import com.ustc.dao.BaseDao;

public class BaseDaoImpl<T> implements BaseDao<T> {
    @Autowired
	private SessionFactory sessionFactory;
    
    public Class<T> entityType;
    @SuppressWarnings("unchecked")
	public BaseDaoImpl(){
    	Class<?> superclass = this.getClass().getSuperclass();
    	Type type = this.getClass().getGenericSuperclass();
    	
    	ParameterizedType ptype = (ParameterizedType) type;
    	
    	Type[] arguments = ptype.getActualTypeArguments();
    	//获取到实体类型即可
    	entityType = (Class<T>) arguments[0];
    }
    
    public Session getSession(){
    	return sessionFactory.getCurrentSession();
    }
    
	@Override
	public List<T> getListHql(String hql, Object... param) {
		// TODO Auto-generated method stub
		return getQuery(hql, param).list();
	}
    
	//设置分页查询效果
	public List<T> getListSql(String sql ,Object...param){
		return getSqlQuery(sql,param).list();
	}
	
	public List<T> getPageListSql(String sql ,Integer page,Integer rows,Object...param){
		return getSqlQuery(sql,param).setFirstResult(0).setMaxResults(10).list();
	}

	@Override
	public void updateEntityByHql(String hql, Object... param) {
		// TODO Auto-generated method stub
		getQuery(hql,param).executeUpdate();
	}

	@Override
	public void updateEntity(T t) {
		// TODO Auto-generated method stub
		getSession().update(t);
	}

	@Override
	public void removeEntity(T t) {
		// TODO Auto-generated method stub
		getSession().delete(t);
	}

	@Override
	public void removeEntityById(String id) {
		// TODO Auto-generated method stub
		ClassMetadata classMetadata = sessionFactory.getClassMetadata(entityType);
		String idName = classMetadata.getIdentifierPropertyName();
		String simpleName = entityType.getSimpleName();
		String hql = "delete from" +simpleName+"e where e."+idName+"=?";
		getQuery(hql, id).executeUpdate();
	}

	@Override
	public void saveEntity(T t) {
		// TODO Auto-generated method stub
		getSession().save(t);
	}

	@Override
	public T getEntityByHql(String hql, Object... param) {
		// TODO Auto-generated method stub
		return (T) getQuery(hql, param).uniqueResult();
	}

	
	@Override
	public T getEntityById(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 根据hql进行查询操作
	 * @param hql
	 * @param param
	 * @return
	 */
	public Query getQuery(String hql,Object...param){
		Query query = getSession().createQuery(hql);
		if(param != null){
			for(int i=0; i<param.length; i++){
				Object val = param[i];
				query.setParameter(i, val);
			}
		}
		return query;
	}
	
	/**
	 * 根据sql语句进行查询操作
	 * @param sql
	 * @param param
	 * @return
	 */
	public SQLQuery getSqlQuery(String sql,Object...param){
		SQLQuery query = getSession().createSQLQuery(sql);
		if(param != null){
			for(int i=0; i<param.length; i++){
				Object val = param[i];
				query.setParameter(i, val);
			}
		}
		//转为实体类
		return (SQLQuery) query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
	}

}
