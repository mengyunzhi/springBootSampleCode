package com.mengyunzhi.springbootsamplecode.softdelete.repository;

import com.mengyunzhi.springbootsamplecode.softdelete.entity.KlassTest;
import org.springframework.data.repository.CrudRepository;


/**
 * 班级测试
 * @author panjie
 */
public interface KlassTestRepository extends CrudRepository<KlassTest, Long> {
}
