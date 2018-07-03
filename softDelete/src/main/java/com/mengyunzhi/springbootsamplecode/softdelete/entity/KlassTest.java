package com.mengyunzhi.springbootsamplecode.softdelete.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 测试班级
 */
@Entity
@SQLDelete(sql = "update `klass_test` set deleted = 1 where id = ?")
@Where(clause = "deleted = false")
public class KlassTest extends BaseEntity {
    @Id @GeneratedValue
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
