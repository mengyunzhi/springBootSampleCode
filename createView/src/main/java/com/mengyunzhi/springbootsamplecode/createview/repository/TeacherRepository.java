package com.mengyunzhi.springbootsamplecode.createview.repository;

import com.mengyunzhi.springbootsamplecode.createview.entity.Teacher;
import org.springframework.data.repository.CrudRepository;

/**
 * @author panjie
 */
public interface TeacherRepository extends CrudRepository<Teacher, Long> {
}
