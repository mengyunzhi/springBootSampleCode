package com.mengyunzhi.springbootsamplecode.multiquery.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author panjie
 */
@Service
public class YunzhiServiceImpl implements YunzhiService {
    @Override
    public Page<Object> pageByEntity(Object entity, Pageable pageable) {
        return null;
    }

    @Override
    public List<Object> findAllByEntity(Object entity) {
        return null;
    }
}
