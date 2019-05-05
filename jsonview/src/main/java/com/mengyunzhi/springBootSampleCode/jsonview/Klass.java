package com.mengyunzhi.springBootSampleCode.jsonview;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Klass {
    public Klass() {
    }

    public Klass(String name) {
        this.name = name;
    }

    /**
     * 定义JSON输出
     */
    static abstract class Json {
        interface base {}  // 基本字段
        interface students extends Student.Json.base {}// 对应students字段
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonView(Json.base.class)
    private String name;

    @JsonView(Json.students.class)
    @OneToMany(mappedBy = "klass")
    private List<Student> students = new ArrayList<>();

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

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
