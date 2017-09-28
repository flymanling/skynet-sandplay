package com.skynet.sandplay.model;

import java.io.Serializable;  
import java.util.Date;  
  
import javax.persistence.Column;  
import javax.persistence.GeneratedValue;  
import javax.persistence.Id;  
import javax.persistence.MappedSuperclass;  
  
import org.hibernate.annotations.GenericGenerator;  
  
/** 
 * 实体类 - 基类 
 */  
@MappedSuperclass  
public class BaseEntity implements Serializable{  
  
    /** 
     * ID 
     */  
    private int id;  
    /** 
     * 创建日期 
     */  
    private Date createDate;  
    /** 
     * 修改日期 
     */  
    private Date modifyDate;  
      
    @Id  
//    @Column(length = 32, nullable = true)  
    @GeneratedValue(generator = "id")  
    @GenericGenerator(name = "id", strategy = "increment")  
    public int getId() {  
        return id;  
    }  
  
    public void setId(int id) {  
        this.id = id;  
    }  
  
    @Column(updatable = false)  
    public Date getCreateDate() {  
        return createDate;  
    }  
  
    public void setCreateDate(Date createDate) {  
        this.createDate = createDate;  
    }  
  
    public Date getModifyDate() {  
        return modifyDate;  
    }  
  
    public void setModifyDate(Date modifyDate) {  
        this.modifyDate = modifyDate;  
    }  
  
//    @Override  
//    public int hashCode() {  
//        return id == null ? System.identityHashCode(this) : id.hashCode();  
//    }  
//  
    @Override  
    public boolean equals(Object obj) {  
        if (this == obj) {  
            return true;  
        }  
        if (obj == null) {  
            return false;  
        }  
        if (getClass().getPackage() != obj.getClass().getPackage()) {  
            return false;  
        }  
        final BaseEntity other = (BaseEntity) obj;  
        if (id == 0) {  
            if (other.getId() != 0) {  
                return false;  
            }  
        } else if (id != other.getId()) {  
            return false;  
        }  
        return true;  
    }  
}  