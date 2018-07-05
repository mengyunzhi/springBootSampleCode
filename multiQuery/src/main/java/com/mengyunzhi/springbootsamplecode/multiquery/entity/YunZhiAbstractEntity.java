package com.mengyunzhi.springbootsamplecode.multiquery.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author panjie
 */
@MappedSuperclass
public abstract class YunZhiAbstractEntity {
    @Id @GeneratedValue
    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
