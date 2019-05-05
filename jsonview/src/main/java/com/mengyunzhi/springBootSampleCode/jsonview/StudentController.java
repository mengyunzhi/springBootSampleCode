package com.mengyunzhi.springBootSampleCode.jsonview;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("student")
public class StudentController {

    // 这是关键！继承了两个interface,即显示这两个interface对应的字段。
    interface getById extends Student.base, Student.klass {
    }

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("{id}")
    @JsonView(getById.class)
    public Student getById(@PathVariable Long id) {
        return studentRepository.findById(id).get();
    }
}
