package com.mengyunzhi.springbootsamplecode.createview.view;

import com.mengyunzhi.springbootsamplecode.createview.annotation.ViewAnnotation;
import com.mengyunzhi.springbootsamplecode.createview.entity.Teacher;
import com.sun.javafx.beans.IDProperty;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * @author panjie
 */
@ViewAnnotation(name = "klass_view",
        sql = "select `klass`.`teacher_id` AS `teacher_id`,`klass`.`name` AS `name`,`klass`.`id` AS `id`,`teacher`.`name` AS `teacher_name` from (`klass` left join `teacher` on(`klass`.`teacher_id` = `teacher`.`id`))")
@Entity
public class KlassView {
    @Id
    private Long id;
    private String name;
    private String teacherName;
    @ManyToOne
    private Teacher teacher;

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

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
