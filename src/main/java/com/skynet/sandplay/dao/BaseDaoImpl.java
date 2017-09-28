package com.skynet.sandplay.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.skynet.sandplay.dao.interfaces.IBaseDao;
import com.skynet.sandplay.service.QuerySettable;

@Repository("baseDao")
public class BaseDaoImpl <T, PK extends Serializable> implements IBaseDao<T, PK>{  
    
  private Class<T> entityClass;  
  protected SessionFactory sessionFactory;  
    
  public BaseDaoImpl() {  
      this.entityClass = null;  
      Class<?> c = getClass();  
      Type type = c.getGenericSuperclass();
      if (type instanceof ParameterizedType) {  
          Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();  
          this.entityClass = (Class<T>) parameterizedType[0];  
      }  
  }  
    
  @Resource  
  public void setSessionFactory(SessionFactory sessionFactory) {  
      this.sessionFactory = sessionFactory;  
  }  

  protected Session getSession() {  
      return sessionFactory.getCurrentSession();  
  }  

  public T get(PK id) {  
//      Assert.assertNotNull( "id is required", id); 
	  Session session = getSession();
      return (T) session.get(entityClass, id);  
  }  

  public PK save(T entity) {  
//      Assert.assertNotNull("entity is required", entity);  
	  Session session = getSession();
      PK id = (PK) session.save(entity);  
      session.flush();
      return id;
  }  
  
  public List<T> list(String hql, QuerySettable callback) {
	  Query query = getSession().createQuery(hql);
	  if(callback != null) {
		  callback.setQuery(query);
	  }
	  return query.list();
  }

  public boolean update(T entity) {
	 try{
		Session session = getSession();
		session.update(entity);
		session.flush();
		 return true;
	 }catch (Exception e) {
		 e.printStackTrace();
		 return false;
	 }
  }
  
  public boolean delete(PK pk) {
	  try{
			Session session = getSession();
			Object object = session.get(entityClass, pk);
			if(object != null) {
				session.delete(object);
			}
			session.flush();
			 return true;
		 }catch (Exception e) {
			 e.printStackTrace();
			 return false;
		 }
  }
}
