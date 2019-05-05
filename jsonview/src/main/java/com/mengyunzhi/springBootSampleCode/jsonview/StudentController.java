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
    static abstract class Json {
        interface getById extends Student.Json.base, Student.Json.klass {}
    }

    @Autowired private StudentRepository studentRepository;

    @GetMapping("{id}")
    @JsonView(Json.getById.class)
    public Student getById(@PathVariable  Long id) {
        return studentRepository.findById(id).get();
    }
}
