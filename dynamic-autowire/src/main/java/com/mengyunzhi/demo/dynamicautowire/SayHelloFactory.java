package com.mengyunzhi.demo.dynamicautowire;

/**
 * 说话工厂
 */
public interface SayHelloFactory {

    void setCountryCode(CountryCode countryCode);

    SayHelloService getSpeakService();
}
