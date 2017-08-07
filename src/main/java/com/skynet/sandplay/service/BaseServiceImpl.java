package com.skynet.sandplay.service;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.skynet.sandplay.dao.IBaseDao;
import com.skynet.sandplay.model.BaseEntity;

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
}
