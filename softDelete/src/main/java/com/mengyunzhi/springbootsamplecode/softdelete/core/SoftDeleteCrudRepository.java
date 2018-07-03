package com.mengyunzhi.springbootsamplecode.softdelete.core;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * 应用软删除
 * 默认的@Where(clause = "deleted = 0")会导致hibernate内部进行关联查询时，发生ObjectNotFound的异常
 * 在此重新定义接口
 * 参考：https://stackoverflow.com/questions/19323557/handling-soft-deletes-with-spring-jpa/22202469
 * @author
 */
@NoRepositoryBean
public interface SoftDeleteCrudRepository<T, ID> extends CrudRepository<T, ID> {
    @Override
    @Transactional
    @Query("select e from #{#entityName} e where e.id = ?1 and e.deleted = false")
    Optional<T> findById(ID id);

    @Override
    @Transactional
    default boolean existsById(ID id) {
        return findById(id).isPresent();
    }

    @Override
    @Transactional
    @Query("select e from #{#entityName} e where e.deleted = false")
    Iterable<T> findAll();

    @Override
    @Transactional
    @Query("select e from #{#entityName} e where e.id in ?1 and e.deleted = false")
    Iterable<T> findAllById(Iterable<ID> ids);

    @Override
    @Transactional
    @Query("select count(e) from #{#entityName} e where e.deleted = false")
    long count();
}

