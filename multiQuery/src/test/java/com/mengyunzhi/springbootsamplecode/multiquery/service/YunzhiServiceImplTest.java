package com.mengyunzhi.springbootsamplecode.multiquery.service;

import com.mengyunzhi.springbootsamplecode.multiquery.entity.Address;
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

import java.util.Calendar;
import java.util.List;

/**
 * @author panjie
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class YunzhiServiceImplTest {
    private final static Logger logger = LoggerFactory.getLogger(YunzhiServiceImplTest.class);
    @Autowired KlassService klassService;
    @Autowired YunzhiService yunzhiService;
    @Autowired TeacherService teacherService;
    @Autowired AddressService addressService;
    @Autowired
    KlassRepository klassRepository;

    @Test
    public void pageByEntity() {
        Klass originKlass = klassService.getOneSavedKlass();
        PageRequest pageRequest = PageRequest.of(0, 2);

        Klass klass = this.initQueryKlass();
        Page<Klass> klassPage = (Page<Klass>) yunzhiService.page(klassRepository, klass, pageRequest);
        Assertions.assertThat(klassPage.getTotalElements()).isEqualTo(1);

        logger.info("更改short值 ，断言返回为0");
        klass.setTotalStudentCount((short) 11);
        klassPage = (Page<Klass>) yunzhiService.page(klassRepository, klass, pageRequest);
        Assertions.assertThat(klassPage.getTotalElements()).isEqualTo(0);

        logger.info("设置short值为null ，断言返回为1");
        klass.setTotalStudentCount(null);
        klassPage = (Page<Klass>) yunzhiService.page(klassRepository, klass, pageRequest);
        Assertions.assertThat(klassPage.getTotalElements()).isEqualTo(1);

        logger.info("更改int值 ，断言返回为0");
        klass.setIntegerTest(101);
        klassPage = (Page<Klass>) yunzhiService.page(klassRepository, klass, pageRequest);
        Assertions.assertThat(klassPage.getTotalElements()).isEqualTo(0);

        logger.info("设置int值为null ，断言返回为1");
        klass.setIntegerTest(null);
        klassPage = (Page<Klass>) yunzhiService.page(klassRepository, klass, pageRequest);
        Assertions.assertThat(klassPage.getTotalElements()).isEqualTo(1);

        logger.info("更改long值 ，断言返回为0");
        klass.setLongTest((long) 1001);
        klassPage = (Page<Klass>) yunzhiService.page(klassRepository, klass, pageRequest);
        Assertions.assertThat(klassPage.getTotalElements()).isEqualTo(0);

        logger.info("设置long值为null ，断言返回为1");
        klass.setLongTest(null);
        klassPage = (Page<Klass>) yunzhiService.page(klassRepository, klass, pageRequest);
        Assertions.assertThat(klassPage.getTotalElements()).isEqualTo(1);

        logger.info("测试关联实体");
        klass = this.initQueryKlass();
        klass.setTeacher(originKlass.getTeacher());
        klassPage = (Page<Klass>) yunzhiService.page(klassRepository, klass, pageRequest);
        Assertions.assertThat(klassPage.getTotalElements()).isEqualTo(1);

        logger.info("更改关联实体");
        Teacher teacher = teacherService.getOneSavedTeacher();
        klass.setTeacher(teacher);
        klassPage = (Page<Klass>) yunzhiService.page(klassRepository, klass, pageRequest);
        Assertions.assertThat(klassPage.getTotalElements()).isEqualTo(0);

        logger.info("测试二级关联实体Address");
        teacher = new Teacher();
        teacher.setAddress(originKlass.getTeacher().getAddress());
        klass.setTeacher(teacher);
        klassPage = (Page<Klass>) yunzhiService.page(klassRepository, klass, pageRequest);
        Assertions.assertThat(klassPage.getTotalElements()).isEqualTo(1);

        logger.info("更改测试二级关联实体Address");
        teacher.setAddress(addressService.getOneSavedAddress());
        klassPage = (Page<Klass>) yunzhiService.page(klassRepository, klass, pageRequest);
        Assertions.assertThat(klassPage.getTotalElements()).isEqualTo(0);

        logger.info("测试二级关联实体 属性");
        Address address = new Address();
        address.setCity("测试城市");
        teacher.setAddress(address);
        klassPage = (Page<Klass>) yunzhiService.page(klassRepository, klass, pageRequest);
        Assertions.assertThat(klassPage.getTotalElements()).isEqualTo(1);

        logger.info("测试二级关联实体 属性");
        address.setCity("测试城市不存在");
        teacher.setAddress(address);
        klassPage = (Page<Klass>) yunzhiService.page(klassRepository, klass, pageRequest);
        Assertions.assertThat(klassPage.getTotalElements()).isEqualTo(0);

        logger.info("测试@IgnoreQueryParam注解");
        klass.setTeacher(null);
        klass.setIgnoreTeacher(teacher);
        klassPage = (Page<Klass>) yunzhiService.page(klassRepository, klass, pageRequest);
        Assertions.assertThat(klassPage.getTotalElements()).isEqualTo(1);

        logger.info("测试@BeginQueryParam, @EndQueryParam注解在Calendar上在作用");
        klass = this.initQueryKlass();
        teacher = new Teacher();
        klass.setTeacher(teacher);
        Calendar beginCalendar = Calendar.getInstance();
        beginCalendar.setTimeInMillis(originKlass.getTeacher().getCreateTime().getTimeInMillis());
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTimeInMillis(originKlass.getTeacher().getCreateTime().getTimeInMillis());
        teacher.setBeginCreateTime(beginCalendar);
        teacher.setEndCreateTime(endCalendar);
        klassPage = (Page<Klass>) yunzhiService.page(klassRepository, klass, pageRequest);
        Assertions.assertThat(klassPage.getTotalElements()).isEqualTo(1);

        logger.info("将范围扩大");
        beginCalendar.add(Calendar.MINUTE, -1);
        endCalendar.add(Calendar.MINUTE, 1);
        klassPage = (Page<Klass>) yunzhiService.page(klassRepository, klass, pageRequest);
        Assertions.assertThat(klassPage.getTotalElements()).isEqualTo(1);

        logger.info("区间后移");
        beginCalendar.add(Calendar.MINUTE, 2);
        endCalendar.add(Calendar.MINUTE, 2);
        klassPage = (Page<Klass>) yunzhiService.page(klassRepository, klass, pageRequest);
        Assertions.assertThat(klassPage.getTotalElements()).isEqualTo(0);

        logger.info("区间前移");
        beginCalendar.add(Calendar.MINUTE, -4);
        endCalendar.add(Calendar.MINUTE, -4);
        klassPage = (Page<Klass>) yunzhiService.page(klassRepository, klass, pageRequest);
        Assertions.assertThat(klassPage.getTotalElements()).isEqualTo(0);

        logger.info("区间调换");
        beginCalendar.add(Calendar.MINUTE, 4);
        klassPage = (Page<Klass>) yunzhiService.page(klassRepository, klass, pageRequest);
        Assertions.assertThat(klassPage.getTotalElements()).isEqualTo(0);

        logger.info("date的区间测试累了，不测了");
    }

    @Test
    public void findAllByEntity() {
        logger.info("上面的分页，已经将该测试的测试完了，这里只是做做样子，防止语法错语");
        Klass originKlass = klassService.getOneSavedKlass();

        Klass klass = this.initQueryKlass();
        List<Klass> klassPage = (List<Klass>) yunzhiService.findAll(klassRepository, klass);
        Assertions.assertThat(klassPage.size()).isEqualTo(1);

        logger.info("测试二级关联实体Address");
        Teacher teacher = new Teacher();
        teacher.setAddress(originKlass.getTeacher().getAddress());
        klass.setTeacher(teacher);
        klassPage = (List<Klass>) yunzhiService.findAll(klassRepository, klass);
        Assertions.assertThat(klassPage.size()).isEqualTo(1);
    }

    /**
     * 获取初始化用于查询的班级
     * @return
     * panjie
     */
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