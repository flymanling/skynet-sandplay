package com.skynet.sandplay.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository("baseDao")
public class BaseDaoImpl <T, PK extends Serializable> implements IBaseDao<T, PK>{  
    
  private Class<T> entityClass;  
  protected SessionFactory sessionFactory;  
    
  public BaseDaoImpl() {  
      this.entityClass = null;  
      Class<?> c = getClass();  
      System.out.println("class : " + c);
      Type type = c.getGenericSuperclass();
      System.out.println("type : " + type);
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
	  System.out.println("session: " + session);
	  System.out.println("id: " + id);
	  System.out.println(entityClass);
      return (T) session.get(entityClass, id);  
  }  

  public PK save(T entity) {  
//      Assert.assertNotNull("entity is required", entity);  
	  System.out.println(entityClass);
      return (PK) getSession().save(entity);  
  }  

}
