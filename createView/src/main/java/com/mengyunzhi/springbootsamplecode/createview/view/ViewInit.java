package com.mengyunzhi.springbootsamplecode.createview.view;

import com.mengyunzhi.springbootsamplecode.createview.annotation.ViewAnnotation;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 视图层初始化
 * @author panjie
 */
@Component
public class ViewInit implements ApplicationListener<ContextRefreshedEvent> {
    private final static Logger logger = LoggerFactory.getLogger(ViewInit.class);
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        logger.debug("扫描当前包下所有的使用了ViewAnnotation注解的类");
        Reflections reflections = new Reflections("com.mengyunzhi.springbootsamplecode.createview.view");
        Set<Class<?>> annotatedClassSet = reflections.getTypesAnnotatedWith(ViewAnnotation.class);

        for (Class<?> klass : annotatedClassSet) {
            ViewAnnotation viewAnnotation = klass.getAnnotation(ViewAnnotation.class);

            try {
                logger.debug("如果存在历史视图，先删除");
                jdbcTemplate.execute("DROP TABLE IF EXISTS `" + viewAnnotation.name() + "`");
                jdbcTemplate.execute("DROP VIEW IF EXISTS `" + viewAnnotation.name() + "`");
                String sql = "CREATE VIEW `" + viewAnnotation.name() + "` AS " + viewAnnotation.sql();
                jdbcTemplate.execute(sql);
            } catch (BadSqlGrammarException e) {
                logger.error("执行" + klass.getName() + "的ViewAnnotation注解语句时发生错误");
                throw e;
            }
        }
        logger.info("视图创建完毕");
    }
}
