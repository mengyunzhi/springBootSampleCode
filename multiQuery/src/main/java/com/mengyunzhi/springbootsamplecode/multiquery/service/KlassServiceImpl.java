package com.mengyunzhi.springbootsamplecode.multiquery.service;

import com.mengyunzhi.springbootsamplecode.multiquery.entity.Klass;
import com.mengyunzhi.springbootsamplecode.multiquery.entity.Teacher;
import com.mengyunzhi.springbootsamplecode.multiquery.repository.KlassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author panjie
 * 班级
 */
@Service
public class KlassServiceImpl implements KlassService {
    @Autowired KlassRepository klassRepository;
    @Autowired TeacherService teacherService;

    @Override
    public Klass getOneSavedKlass() {
        Teacher teacher = teacherService.getOneSavedTeacher();
        Klass klass = new Klass();
        klass.setName(CommonService.getRandomStringByLength(4) + "测试班级名称" + CommonService.getRandomStringByLength(4));
        klass.setLongTest(1000L);
        klass.setTotalStudentCount((short) 10);
        klass.setIntegerTest(100);
        klass.setTeacher(teacher);
        klass.setIgnoreTeacher(teacher);
        klassRepository.save(klass);
        return klass;
    }
}
