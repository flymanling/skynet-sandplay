package com.skynet.sandplay.dao.interfaces;

import java.io.Serializable;
import java.util.List;

import com.skynet.sandplay.service.QuerySettable;

public interface IBaseDao <T, PK extends Serializable>{

	 /** 
     * 根据ID获取实体对象. 
     *  
     * @param id 
     *            记录ID 
     * @return 实体对象 
     */  
    public T get(PK id);  
      
    /** 
     * 保存实体对象. 
     *  
     * @param entity 
     *            对象 
     * @return ID 
     */  
    public PK save(T entity);  
    
    /**
     * 查询列表
     * @param hql
     * @param callback
     * @return
     */
    public List<T> list(String hql, QuerySettable callback);
    
    public boolean update(T entity) ;
    
    public boolean delete(PK pk);
}
