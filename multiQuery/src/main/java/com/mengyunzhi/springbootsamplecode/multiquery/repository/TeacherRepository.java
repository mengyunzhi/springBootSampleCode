package com.mengyunzhi.springbootsamplecode.multiquery.repository;

import com.mengyunzhi.springbootsamplecode.multiquery.entity.Teacher;
import org.springframework.data.repository.CrudRepository;

/**
 * @author panjie
 */
public interface TeacherRepository extends CrudRepository<Teacher, Long> {
}