package com.mengyunzhi.springbootsamplecode.multiquery.repository;

import com.mengyunzhi.springbootsamplecode.multiquery.entity.Address;
import org.springframework.data.repository.CrudRepository;

/**
 * 地址
 * @author panjie
 */
public interface AddressRepository extends CrudRepository<Address, Long> {
}
