package com.mengyunzhi.springbootsamplecode.multiquery.entity;

import javax.persistence.*;

/**
 * @author panjie
 */
@Entity
public class Klass extends YunZhiAbstractEntity{
    private String name;        // 班级名称
    private Short totalStudentCount;    // 学生总数
    private Integer integerTest;        // 整形测试
    private Long longTest;              // 长整形测试

    @ManyToOne
    private Teacher teacher;

    @Transient
    private Teacher transientTeacher;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Short getTotalStudentCount() {
        return totalStudentCount;
    }

    public void setTotalStudentCount(Short totalStudentCount) {
        this.totalStudentCount = totalStudentCount;
    }

    public Integer getIntegerTest() {
        return integerTest;
    }

    public void setIntegerTest(Integer integerTest) {
        this.integerTest = integerTest;
    }

    public Long getLongTest() {
        return longTest;
    }

    public void setLongTest(Long longTest) {
        this.longTest = longTest;
    }

    public Teacher getTransientTeacher() {
        return transientTeacher;
    }

    public void setTransientTeacher(Teacher transientTeacher) {
        this.transientTeacher = transientTeacher;
    }
}
