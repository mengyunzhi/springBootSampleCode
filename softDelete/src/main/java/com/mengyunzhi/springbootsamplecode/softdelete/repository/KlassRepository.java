package com.mengyunzhi.springbootsamplecode.softdelete.repository;

import com.mengyunzhi.springbootsamplecode.softdelete.core.SoftDeleteCrudRepository;
import com.mengyunzhi.springbootsamplecode.softdelete.entity.Klass;

/**
 * 班级
 * @author panjie
 */
public interface KlassRepository extends SoftDeleteCrudRepository<Klass, Long>{
}
