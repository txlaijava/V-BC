package com.shopping.base.foundation.base.entity;


import com.shopping.base.annotation.Lock;
import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public class IdEntity implements java.io.Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 7060381435224185276L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    public Long id;

    public Date addTime = new Date();

    @Lock
    public boolean deleteStatus = Boolean.FALSE;


    public boolean isDeleteStatus() {
        return this.deleteStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getAddTime() {
        return addTime;
    }


    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }


    public void setDeleteStatus(boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }
}