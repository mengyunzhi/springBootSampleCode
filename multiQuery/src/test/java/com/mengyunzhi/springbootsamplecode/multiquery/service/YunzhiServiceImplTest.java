package com.mengyunzhi.springbootsamplecode.multiquery.service;

import com.mengyunzhi.springbootsamplecode.multiquery.entity.Klass;
import com.mengyunzhi.springbootsamplecode.multiquery.entity.Teacher;
import com.mengyunzhi.springbootsamplecode.multiquery.repository.KlassRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author panjie
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class YunzhiServiceImplTest {
    private final static Logger logger = LoggerFactory.getLogger(YunzhiServiceImplTest.class);
    @Autowired KlassService klassService;
    @Autowired YunzhiService yunzhiService;
    @Autowired
    KlassRepository klassRepository;

    @Test
    public void pageByEntity() {
        klassService.getOneSavedKlass();
        PageRequest pageRequest = PageRequest.of(0, 2);

        Klass klass = this.initQueryKlass();
        Page<Klass> klassPage = (Page<Klass>) yunzhiService.pageByEntity(klassRepository, klass, pageRequest);
        Assertions.assertThat(klassPage.getTotalElements()).isEqualTo(1);

        logger.info("更改short值 ，断言返回为0");
        klass.setTotalStudentCount((short) 11);
        klassPage = (Page<Klass>) yunzhiService.pageByEntity(klassRepository, klass, pageRequest);
        Assertions.assertThat(klassPage.getTotalElements()).isEqualTo(0);

        logger.info("设置short值为null ，断言返回为1");
        klass.setTotalStudentCount(null);
        klassPage = (Page<Klass>) yunzhiService.pageByEntity(klassRepository, klass, pageRequest);
        Assertions.assertThat(klassPage.getTotalElements()).isEqualTo(1);

        logger.info("更改int值 ，断言返回为0");
        klass.setIntegerTest(101);
        klassPage = (Page<Klass>) yunzhiService.pageByEntity(klassRepository, klass, pageRequest);
        Assertions.assertThat(klassPage.getTotalElements()).isEqualTo(0);

        logger.info("设置int值为null ，断言返回为1");
        klass.setIntegerTest(null);
        klassPage = (Page<Klass>) yunzhiService.pageByEntity(klassRepository, klass, pageRequest);
        Assertions.assertThat(klassPage.getTotalElements()).isEqualTo(1);

        logger.info("更改long值 ，断言返回为0");
        klass.setLongTest((long) 1001);
        klassPage = (Page<Klass>) yunzhiService.pageByEntity(klassRepository, klass, pageRequest);
        Assertions.assertThat(klassPage.getTotalElements()).isEqualTo(0);

        logger.info("设置long值为null ，断言返回为1");
        klass.setLongTest(null);
        klassPage = (Page<Klass>) yunzhiService.pageByEntity(klassRepository, klass, pageRequest);
        Assertions.assertThat(klassPage.getTotalElements()).isEqualTo(1);

        logger.info("设置起始日期");
        logger.info("设置终止日期");
        logger.info("设置起始Clander");
        logger.info("设置终止clander");
        logger.info("测试Transient注解");
        logger.info("测试@QueryParam注解");
    }

    @Test
    public void findAllByEntity() {
    }

    private Klass initQueryKlass() {
        logger.info("加入所有的测试信息，断言返回1条记录");
        Klass klass = new Klass();
        klass.setName("测试班级名称");
        klass.setTotalStudentCount((short) 10);
        klass.setIntegerTest(100);
        klass.setLongTest(1000L);
        return klass;
    }
}