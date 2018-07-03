package com.mengyunzhi.springbootsamplecode.createview.repository;

import com.mengyunzhi.springbootsamplecode.createview.entity.Klass;
import com.mengyunzhi.springbootsamplecode.createview.entity.Teacher;
import com.mengyunzhi.springbootsamplecode.createview.view.KlassView;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

/**
 * @author panjie
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class KlassViewRepositoryTest {
    private final static Logger logger = LoggerFactory.getLogger(KlassViewRepositoryTest.class);
    @Autowired TeacherRepository teacherRepository;
    @Autowired KlassRepository klassRepository;
    @Autowired KlassViewRepository klassViewRepository;

    @Test
    public void find() {
        logger.info("持久化班级以及教师");
        Teacher teacher = new Teacher();
        teacher.setName("测试教师");
        teacherRepository.save(teacher);

        Klass klass = new Klass();
        klass.setTeacher(teacher);
        klass.setName("测试班级");
        klassRepository.save(klass);

        Optional<KlassView> klassViewOptional = klassViewRepository.findById(klass.getId());
        KlassView klassView = klassViewOptional.get();

        Assertions.assertThat(klassView.getTeacher().getId()).isEqualTo(teacher.getId());
        Assertions.assertThat(klassView.getTeacher().getName()).isEqualTo(teacher.getName());
        Assertions.assertThat(klassView.getName()).isEqualTo(klass.getName());
    }
}