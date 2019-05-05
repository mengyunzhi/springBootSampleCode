package com.mengyunzhi.springBootSampleCode.jsonview;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("klass")
public class KlassController {
    static abstract class Json {
        // 这是关键！继承了两个interface,即显示这两个interface对应的字段。
        interface getById extends Klass.Json.base, Klass.Json.students {}
    }

    @Autowired private KlassRepository klassRepository;

    @GetMapping("{id}")
    @JsonView(Json.getById.class)
    public Klass getById(@PathVariable  Long id) {
        return klassRepository.findById(id).get();
    }
}
