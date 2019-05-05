package com.mengyunzhi.springBootSampleCode.jsonview;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;

@Entity
public class Student {
    public Student() {
    }

    public Student(String name) {
        this.name = name;
    }

    /**
     * 定义JSON输出
     */
    static abstract class Json {
         interface base {}  // 基本字段
         interface klass extends Klass.Json.base {} // 对应klass字段
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Json.base.class)
    private Long id;

    @JsonView(Json.base.class)
    private String name;

    @JsonView(Json.klass.class)
    @ManyToOne
    private Klass klass;

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
}
