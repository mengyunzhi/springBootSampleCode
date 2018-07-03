package com.mengyunzhi.springbootsamplecode.softdelete.repository;

import com.mengyunzhi.springbootsamplecode.softdelete.entity.Teacher;
import org.springframework.data.repository.CrudRepository;

/**
 * 教师仓库
 * @author panjie
 */
public interface TeacherRepository extends CrudRepository<Teacher, Long> {
}
