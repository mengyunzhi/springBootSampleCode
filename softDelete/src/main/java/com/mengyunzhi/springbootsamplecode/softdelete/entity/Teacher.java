package com.mengyunzhi.springbootsamplecode.softdelete.entity;

import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

/**
 * 教师
 */
@Entity
@SQLDelete(sql = "update `Teacher` set deleted = 1 where id = ?")
public class Teacher extends BaseEntity {
    @Id @GeneratedValue
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Klass klass;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private KlassTest klassTest;

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

    public Klass getKlass() {
        return klass;
    }

    public void setKlass(Klass klass) {
        this.klass = klass;
    }

    public KlassTest getKlassTest() {
        return klassTest;
    }

    public void setKlassTest(KlassTest klassTest) {
        this.klassTest = klassTest;
    }
}
