package com.mengyunzhi.demo.dynamicautowire;

public interface SpeakFactory {

    void setCountryCode(CountryCode countryCode);

    void register(Byte code, SpeakService speakService);

    SpeakService getDynamicService();
}
