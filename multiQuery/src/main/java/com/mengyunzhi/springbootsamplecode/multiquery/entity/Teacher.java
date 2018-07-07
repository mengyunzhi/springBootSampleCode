package com.mengyunzhi.springbootsamplecode.multiquery.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mengyunzhi.springbootsamplecode.multiquery.annotation.BeginQueryParam;
import com.mengyunzhi.springbootsamplecode.multiquery.annotation.EndQueryParam;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Calendar;

/**
 * @author panjie
 */
@Entity
public class Teacher extends YunZhiAbstractEntity {
    private String name;
    @ManyToOne
    private Address address;    // 住址

    @CreationTimestamp
    private Calendar createTime;    // 创建时间

    @Transient
    @JsonIgnore
    @BeginQueryParam(name = "createTime")
    private Calendar beginCreateTime;   // 用于查询的开始创建时间

    @Transient
    @JsonIgnore
    @EndQueryParam(name = "createTime")
    private Calendar endCreateTime;     // 用于查询的结束创建时间

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Calendar getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Calendar createTime) {
        this.createTime = createTime;
    }

    public Calendar getBeginCreateTime() {
        return beginCreateTime;
    }

    public void setBeginCreateTime(Calendar beginCreateTime) {
        this.beginCreateTime = beginCreateTime;
    }

    public Calendar getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(Calendar endCreateTime) {
        this.endCreateTime = endCreateTime;
    }
}
