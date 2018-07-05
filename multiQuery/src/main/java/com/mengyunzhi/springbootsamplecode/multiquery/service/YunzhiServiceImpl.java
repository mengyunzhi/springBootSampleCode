package com.mengyunzhi.springbootsamplecode.multiquery.service;

import com.mengyunzhi.springbootsamplecode.multiquery.annotation.BeginQueryParam;
import com.mengyunzhi.springbootsamplecode.multiquery.annotation.EndQueryParam;
import com.mengyunzhi.springbootsamplecode.multiquery.annotation.IgnoreQueryParam;
import com.mengyunzhi.springbootsamplecode.multiquery.entity.YunZhiAbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.criteria.*;
import java.lang.reflect.Field;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

/**
 * 多条件综合查询
 * @author panjie
 */
@Service
public class YunzhiServiceImpl implements YunzhiService {
    private static final Logger logger = LoggerFactory.getLogger(YunzhiServiceImpl.class);

    @Override
    public Page<Object> page(JpaSpecificationExecutor jpaSpecificationExecutor, Object entity, Pageable pageable) {
        Specification specification = this.getSpecificationByEntity(entity);
        Page<Object> objectPage = jpaSpecificationExecutor.findAll(specification, pageable);
        return objectPage;
    }

    @Override
    public List<Object> findAll(JpaSpecificationExecutor jpaSpecificationExecutor, Object entity) {
        Specification specification = this.getSpecificationByEntity(entity);
        List<Object> objectList = jpaSpecificationExecutor.findAll(specification);
        return objectList;
    }

    private Specification getSpecificationByEntity(Object entity) {
        Specification<Object> specification = new Specification<Object>() {
            private Predicate predicate = null;
            private CriteriaBuilder criteriaBuilder;

            // 设置and谓语.注意，这里只能设置and关系的谓语，如果谓语为OR，则需要手动设置
            private void andPredicate(Predicate predicate) {
                if (null != predicate) {
                    if (null == this.predicate) {
                        this.predicate = predicate;
                    } else {
                        this.predicate = this.criteriaBuilder.and(this.predicate, predicate);
                    }
                }
            }

            private void generatePredicate(Object entity, From<Object, ?> root) {
                logger.debug("反射字段，按字段类型，进行综合查询");
                Field[] fields = entity.getClass().getDeclaredFields();
                try {
                    for (Field field : fields) {
                        logger.info("反射字段名，及字段值。并设置为字段可见");
                        String name = field.getName();
                        field.setAccessible(true);
                        Object value = field.get(entity);

                        if (value != null) {
                            if (field.getAnnotation(IgnoreQueryParam.class) != null) {
                                logger.debug("存在@IgnoreQueryParam注解, 跳出");
                                continue;
                            }

                            if (field.getAnnotation(ManyToOne.class) != null) {
                                logger.debug("有manyToOne的注解");
                                YunZhiAbstractEntity yunZhiAbstractEntity = (YunZhiAbstractEntity) value;
                                if (yunZhiAbstractEntity.getId() != null) {
                                    logger.debug("对应的ManyToOne，加入了id, 则按ID查询");
                                    this.andPredicate(criteriaBuilder.equal(root.join(name).get("id").as(Long.class), yunZhiAbstractEntity.getId()));
                                } else {
                                    logger.debug("未加入id, 则进行Join查询");
                                    this.generatePredicate(value, root.join(name));
                                }

                            } else if (field.getAnnotation(OneToMany.class) != null) {
                                logger.error("暂不支持OneToMany注解");

                            } else if (field.getAnnotation(ManyToMany.class) != null) {
                                logger.error("暂不支持ManyToMany注解");

                            } else {
                                logger.debug("没有@ManyToOne, OneToMany, ManyToMany注解，说明为普通字段, 按value类型，进行综合查询");
                                // 初始化两个界限的变量
                                Boolean isBegin = false;
                                Boolean isEnd = false;

                                // 查找开始与结束的注解
                                BeginQueryParam beginQueryParam = field.getAnnotation(BeginQueryParam.class);
                                if (beginQueryParam != null) {
                                    logger.debug("存在@BeginQueryParam注解");
                                    isBegin = true;
                                    name = beginQueryParam.name();
                                } else if (field.getAnnotation(EndQueryParam.class) != null) {
                                    logger.debug("存在@EndQueryParam注解");
                                    isEnd = true;
                                    name = field.getAnnotation(EndQueryParam.class).name();
                                }

                                // 按字段类型进行查询
                                if (value instanceof String) {
                                    logger.debug("字符串则进行模糊查询");
                                    String stringValue = ((String) value);
                                    if (!stringValue.isEmpty()) {
                                        this.andPredicate(criteriaBuilder.like(root.get(name).as(String.class), "%" + stringValue + "%"));
                                    }
                                } else if (value instanceof Number) {
                                    logger.debug("如果为number，则进行精确或范围查询");
                                    if (value instanceof Short) {
                                        Short shortValue = (Short) value;
                                        if (isBegin) {
                                            this.andPredicate(criteriaBuilder.greaterThanOrEqualTo(root.get(name).as(Short.class), shortValue));
                                        } else if (isEnd) {
                                            this.andPredicate(criteriaBuilder.lessThanOrEqualTo(root.get(name).as(Short.class), shortValue));
                                        } else {
                                            this.andPredicate(criteriaBuilder.equal(root.get(name).as(Short.class), shortValue));
                                        }
                                    } else if (value instanceof Integer) {
                                        Integer integerValue = (Integer) value;
                                        if (isBegin) {
                                            this.andPredicate(criteriaBuilder.greaterThanOrEqualTo(root.get(name).as(Integer.class), integerValue));
                                        } else if (isEnd) {
                                            this.andPredicate(criteriaBuilder.lessThanOrEqualTo(root.get(name).as(Integer.class), integerValue));
                                        } else {
                                            this.andPredicate(criteriaBuilder.equal(root.get(name).as(Integer.class), integerValue));
                                        }
                                    } else if (value instanceof Long) {
                                        Long longValue = (Long) value;
                                        if (isBegin) {
                                            this.andPredicate(criteriaBuilder.greaterThanOrEqualTo(root.get(name).as(Long.class), longValue));
                                        } else if (isEnd) {
                                            this.andPredicate(criteriaBuilder.lessThanOrEqualTo(root.get(name).as(Long.class), longValue));
                                        } else {
                                            this.andPredicate(criteriaBuilder.equal(root.get(name).as(Long.class), longValue));
                                        }
                                    } else {
                                        logger.error("综合查询Number类型，暂时只支持到Short,Integer,Long");
                                    }
                                } else if (value instanceof Calendar) {
                                    logger.debug("Calendar类型");
                                    Calendar calendarValue = (Calendar) value;
                                    if (isBegin) {
                                        this.andPredicate(criteriaBuilder.greaterThanOrEqualTo(root.get(name).as(Calendar.class), calendarValue));
                                    } else if (isEnd) {
                                        this.andPredicate(criteriaBuilder.lessThanOrEqualTo(root.get(name).as(Calendar.class), calendarValue));
                                    } else {
                                        this.andPredicate(criteriaBuilder.equal(root.get(name).as(Calendar.class), calendarValue));
                                    }
                                } else if (value instanceof Date) {
                                    logger.debug("Sql.Date类型");
                                    Date dateValue = (Date) value;
                                    if (isBegin) {
                                        this.andPredicate(criteriaBuilder.greaterThanOrEqualTo(root.get(name).as(Date.class), dateValue));
                                    } else if (isEnd) {
                                        this.andPredicate(criteriaBuilder.lessThanOrEqualTo(root.get(name).as(Date.class), dateValue));
                                    } else {
                                        this.andPredicate(criteriaBuilder.equal(root.get(name).as(Date.class), dateValue));
                                    }
                                } else {
                                    logger.error("暂不支持传入的数据类型");
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public Predicate toPredicate(Root<Object> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                // 设置CriteriaBuilder，用于合并谓语
                this.criteriaBuilder = criteriaBuilder;
                this.generatePredicate(entity, root);
                return this.predicate;
            }
        };

        return specification;
    }
}