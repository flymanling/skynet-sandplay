package com.skynet.sandplay.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.skynet.sandplay.dao.interfaces.IBaseDao;
import com.skynet.sandplay.model.BaseEntity;
import com.skynet.sandplay.service.interfaces.IBaseService;

//@Transactional
@Service("baseService")
public class BaseServiceImpl<T extends BaseEntity, PK extends Serializable> implements IBaseService<T, PK> {

	private IBaseDao<T, PK> baseDao;  
	
	public IBaseDao<T, PK> getBaseDao() {  
        return baseDao;  
    }  
      
	@Resource
    public void setBaseDao(IBaseDao<T, PK> baseDao) {  
        this.baseDao = baseDao;  
    }  
  
    public T get(PK id) {  
        return baseDao.get(id);  
    }  
      
    public PK save(T entity) { 
    	entity.setCreateDate(new Date());
    	entity.setModifyDate(new Date());
        return baseDao.save(entity);  
    }  
    
    public List<T> list(String hql, QuerySettable callback) {
    	return baseDao.list(hql, callback);
    }
    
    public boolean update(T entity) {
    	return baseDao.update(entity);
    }
    
    public boolean delete(PK pk) {
    	return baseDao.delete(pk);
    }
}
