package com.mengyunzhi.springbootsamplecode.multiquery.service;

import com.mengyunzhi.springbootsamplecode.multiquery.entity.Teacher;
import com.mengyunzhi.springbootsamplecode.multiquery.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author panjie
 */
@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    TeacherRepository teacherRepository;
    @Override
    public Teacher getOneSavedTeacher() {
        Teacher teacher = new Teacher();
        teacher.setName(CommonService.getRandomStringByLength(4) + "测试班级名称" + CommonService.getRandomStringByLength(4));
        teacherRepository.save(teacher);
        return teacher;
    }
}
