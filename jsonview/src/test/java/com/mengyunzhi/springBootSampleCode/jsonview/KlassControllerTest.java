package com.mengyunzhi.springBootSampleCode.jsonview;

import com.alibaba.fastjson.JSON;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@AutoConfigureMockMvc
public class KlassControllerTest extends  JsonviewApplicationTests {
    @Autowired
    private KlassRepository klassRepository;
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getById() throws Exception {
        Klass klass = new Klass("测试班级");
        klassRepository.save(klass);

        Student student = new Student("测试学生");
        student.setKlass(klass);
        studentRepository.save(student);

        klass.getStudents().add(student);
        klassRepository.save(klass);

        String result = this.mockMvc.perform(
                MockMvcRequestBuilders.get("/klass/" + klass.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn().getResponse().getContentAsString();

        Klass resultKlass = JSON.parseObject(result, Klass.class);
        Assertions.assertThat(resultKlass.getName()).isEqualTo("测试班级");
        Assertions.assertThat(resultKlass.getStudents().size()).isEqualTo(1);
        Assertions.assertThat(resultKlass.getStudents().get(0).getName()).isEqualTo("测试学生");
    }
}