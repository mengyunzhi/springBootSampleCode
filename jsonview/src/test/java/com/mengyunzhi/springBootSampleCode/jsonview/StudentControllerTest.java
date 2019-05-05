package com.mengyunzhi.springBootSampleCode.jsonview;

import com.alibaba.fastjson.JSON;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@AutoConfigureMockMvc
public class StudentControllerTest extends JsonviewApplicationTests {
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


        String result = this.mockMvc.perform(
                MockMvcRequestBuilders.get("/student/" + student.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn().getResponse().getContentAsString();

        Student resultStudent = JSON.parseObject(result, Student.class);
        Assertions.assertThat(resultStudent.getName()).isEqualTo("测试学生");
        Assertions.assertThat(resultStudent.getKlass().getName()).isEqualTo("测试班级");
    }
}