package com.mengyunzhi.springbootsamplecode.softdelete.repository;

import com.mengyunzhi.springbootsamplecode.softdelete.entity.Klass;
import com.mengyunzhi.springbootsamplecode.softdelete.entity.KlassTest;
import com.mengyunzhi.springbootsamplecode.softdelete.entity.Teacher;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;


/**
 * @author panjie
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TeacherRepositoryTest {
    private final static Logger logger = LoggerFactory.getLogger(TeacherRepositoryTest.class);
    @Autowired KlassRepository klassRepository;
    @Autowired KlassTestRepository klassTestRepository;
    @Autowired TeacherRepository teacherRepository;

    @Test
    public void findById() {
        logger.info("新建一个有Klass和KlassTest的教师");
        Klass klass = new Klass();
        klassRepository.save(klass);
        KlassTest klassTest = new KlassTest();
        klassTestRepository.save(klassTest);
        Teacher teacher = new Teacher();
        teacher.setKlass(klass);
        teacher.setKlassTest(klassTest);
        teacherRepository.save(teacher);

        logger.info("查找教师，断言查找了实体，并且不发生异常");
        Optional<Teacher> teacherOptional = teacherRepository.findById(teacher.getId());
        Assertions.assertThat(teacherOptional.get()).isNotNull();


        logger.info("删除关联的Klass, 再查找教师实体，断言查找到了实体，不发生异常。断言教师实体中，仍然存在已经删除的Klass实体");
        klassRepository.deleteById(klass.getId());
        teacherOptional = teacherRepository.findById(teacher.getId());
        Assertions.assertThat(teacherOptional.get()).isNotNull();
        Assertions.assertThat(teacherOptional.get().getKlass().getId()).isEqualTo(klass.getId());

        logger.info("查找教师列表，不发生异常。断言教师实体中，存在已删除的Klass实体记录");
        List<Teacher> teacherList = (List<Teacher>) teacherRepository.findAll();
        for (Teacher teacher1 : teacherList) {
            Assertions.assertThat(teacher1.getKlass().getId()).isEqualTo(klass.getId());
        }

        logger.info("删除关联的KlassTest，再查找教师实体, 断言找到了删除的klassTest");
        klassTestRepository.deleteById(klassTest.getId());
        teacherOptional = teacherRepository.findById(teacher.getId());
        Assertions.assertThat(teacherOptional.get()).isNotNull();
        Assertions.assertThat(teacherOptional.get().getKlassTest().getId()).isEqualTo(klassTest.getId());

        logger.info("再查找教师列表，断言将发生JpaObjectRetrievalFailureException(EntityNotFound 异常被捕获后，封装抛出)异常");
        Boolean catchException = false;
        try {
            teacherRepository.findAll();
        } catch (JpaObjectRetrievalFailureException e) {
            catchException = true;
        }
        Assertions.assertThat(catchException).isTrue();
    }

}