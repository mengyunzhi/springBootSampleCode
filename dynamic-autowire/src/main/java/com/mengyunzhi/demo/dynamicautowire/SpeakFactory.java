package com.mengyunzhi.demo.dynamicautowire;

/**
 * 说话工厂
 */
public interface SpeakFactory {

    void setCountryCode(CountryCode countryCode);

    SpeakService getSpeakService();
}
