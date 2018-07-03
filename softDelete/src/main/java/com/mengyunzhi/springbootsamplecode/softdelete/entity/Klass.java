package com.mengyunzhi.springbootsamplecode.softdelete.entity;

import org.hibernate.annotations.SQLDelete;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 班级
 */
@Entity
@SQLDelete(sql = "update `klass` set deleted = 1 where id = ?")
public class Klass extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
