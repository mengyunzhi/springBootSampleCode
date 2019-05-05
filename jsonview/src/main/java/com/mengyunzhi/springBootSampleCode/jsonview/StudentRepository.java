package com.mengyunzhi.springBootSampleCode.jsonview;

import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Long> {
}
