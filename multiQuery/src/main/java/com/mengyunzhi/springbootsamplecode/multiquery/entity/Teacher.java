package com.mengyunzhi.springbootsamplecode.multiquery.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author panjie
 */
@Entity
public class Teacher extends YunZhiAbstractEntity {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
