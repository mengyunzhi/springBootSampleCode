package com.mengyunzhi.demo.dynamicautowire;

public enum CountryCode {
    CHINA((byte) 0, "中国"),
    USA((byte) 1, "美国");
    private Byte code;
    private String name;

    CountryCode(Byte code, String name) {
        this.code = code;
        this.name = name;
    }

    public Byte getCode() {
        return code;
    }
    public String getName() {
        return name;
    }
}
