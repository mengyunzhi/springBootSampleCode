在项目的开发中，我们很难做到开发一套标准的流程来解决所有客户的需求。比如，我们当前的计量项目，分别运行于赤峰市和河北省。虽然两个区域处理的业务相同，但是对细节的实现要求却不同。前面也学习过计量检定软件，其为了解决各个定制者使用的功能需求，最后采取的方案是：将基础项目复制多份，进而满足不同的客户需求。优点当然是有的，但比起缺点来，优点便不值一提。缺点很明显，总结为一句话就是：项目变得难以维护。所以，当前让我们看到的就是，几个开发人员，每天处于解决问题当中。本文将给出一种方案，来有效的规避上述问题。

## 资源与环境
segmentfault同步地址：[https://segmentfault.com/a/1190000018673552](https://segmentfault.com/a/1190000018673552)

> 开发环境：java1.8 + spring-boot:2.1.3.RELEASE

## 需求假设
* 假设使用本项目的人员为：中国人、美国人，分别能接受的语言为中文和英文。
* 项目运行后，可以根据当前的访问人员是国籍来动态显示：`你好`或`hello`
* 有新的需求后，比如：增加德国人并显示`Hallo`。增加功能时，不更改核心代码。
* 不使用if else

**注意：如果你看完需求假设后，毫无触动，请忽略本文以下内容**

## 解决方案
解决方案中，我们涉及了两种设计模块，分别为：`策略模式`及`工厂模式`。  
策略模式：一般用于将具体的`算法`进行抽象及剥离。此项目中，我们的具体算法是`说你好`。
工厂模式：一般用于根据环境来动态的创建BEAN的情况下。引项目中，我们将根据不同国家的人，来返回不同的`说你好`这个算法。

先给出UML图：
![clipboard.png](https://segmentfault.com/img/bVbqvPl?w=1671&h=594)

### SpeakService
`SpeakService`即为我们供其它模块调用的`说话服务`，调用其中的`SayHello()`来完成`说你好`功能。
```java
package com.mengyunzhi.demo.dynamicautowire;

/**
 * 你好
 */
public interface SpeakService {
    void sayHello();
}
```

在其实现类中，我们注入`SayHelloFactory`，让其来返回正确的`SayHelloService`，最终调用`sayHello()`来完成目标。
```java
package com.mengyunzhi.demo.dynamicautowire;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 你好
 */
@Service
public class SpeakServiceImpl implements SpeakService {
    private final
    SayHelloFactory sayHelloFactory;  // 说话工厂

    @Autowired
    public SpeakServiceImpl(SayHelloFactory sayHelloFactory) {
        this.sayHelloFactory = sayHelloFactory;
    }

    @Override
    public void sayHello() {
        this.sayHelloFactory.getSayHelloService().sayHello();
    }
}
```

### SayHelloFactory

```java
package com.mengyunzhi.demo.dynamicautowire;

/**
 * 说话工厂
 */
public interface SayHelloFactory {

    void setCountryCode(CountryCode countryCode);

    SayHelloService getSayHelloService();
}
```
在此，我们增加一个`CountryCode`表示当前访问者的国家。其实在获取访问者国家时，我们也可以调用其它Bean的其它来实现。

```java
package com.mengyunzhi.demo.dynamicautowire;

/**
 * 国家代码
 */
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
```
使用`enum`来控制范围，避免`Factory`在获取Bean时发生异常。

```java
package com.mengyunzhi.demo.dynamicautowire;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 说话工厂
 */
@Service
public class SayHelloFactoryImpl implements SayHelloFactory {
    /**
     * BEAN列表
     */
    private final Map<Byte, SayHelloService> servicesByCode = new HashMap<>();
    /**
     * 国家代码
     */
    private CountryCode countryCode = CountryCode.CHINA;

    @Override
    public void setCountryCode(CountryCode countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * 初始化
     *
     * @param sayHelloServices spring获取到的所以实现了SpeakService的BEAN
     */
    @Autowired
    public void init(List<SayHelloService> sayHelloServices) {
        for (SayHelloService sayHelloService : sayHelloServices) {
            this.register(sayHelloService.getCode(), sayHelloService);
        }
    }

    /**
     * 注册Bean
     *
     * @param code         代码
     * @param sayHelloService BEAN
     */
    private void register(Byte code, SayHelloService sayHelloService) {
        this.servicesByCode.put(code, sayHelloService);
    }

    /**
     * 获取BEAN
     *
     * @return 对应的SayHelloService BEAN
     */
    @Override
    public SayHelloService getSayHelloService() {
        return this.servicesByCode.get(this.countryCode.getCode());
    }
}
```
增加`Map<Byte, SayHelloService> servicesByCode`来存储对应国家的`SayHelloService`BEAN。增加`getSayHelloService()`来根据当前国家代码来返回相应的Bean。

### SayHelloService
```java
package com.mengyunzhi.demo.dynamicautowire;

/**
 * 说话
 */
public interface SayHelloService {
    void sayHello();

    Byte getCode();
}
```
将`sayHello()`方法抽离，`getCode()`以获取国家代码。

### 中国人你好
```java
package com.mengyunzhi.demo.dynamicautowire;

import org.springframework.stereotype.Component;

/**
 * 中国话
 */
@Component
public class SayHelloServiceChineseImpl implements SayHelloService {
    @Override
    public void sayHello() {
        System.out.println("您好");
    }

    @Override
    public Byte getCode() {
        return CountryCode.CHINA.getCode();
    }
}
```

### 美国人Hello
```java
package com.mengyunzhi.demo.dynamicautowire;

import org.springframework.stereotype.Component;

/**
 * 美国话
 */
@Component
public class SayHelloServiceEnglishImpl implements SayHelloService {
    @Override
    public void sayHello() {
        System.out.println("hello");
    }

    @Override
    public Byte getCode() {
        return CountryCode.USA.getCode();
    }
}
```

### 测试
```java
package com.mengyunzhi.demo.dynamicautowire;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SpeakServiceImplTest {
    @Autowired
    SpeakService speakService;
    @Autowired
    SayHelloFactory sayHelloFactory;

    @Test
    public void sayHello() {
        // 默认说你好
        speakService.sayHello();

        // 将国家设置为美国，再说你好
        sayHelloFactory.setCountryCode(CountryCode.USA);
        speakService.sayHello();

        // 将国家设置为中国，再说你好
        sayHelloFactory.setCountryCode(CountryCode.CHINA);
        speakService.sayHello();
    }
}
```

```
您好
hello
您好
```

### 时序图

![clipboard.png](https://segmentfault.com/img/bVbqvXD?w=2314&h=1108)

## 增加德国人
1. 增加德国人`SayHelloServiceGermanyImpl`.
2. 在`CountryCode`中，增加德国.

```java
package com.mengyunzhi.demo.dynamicautowire;

import org.springframework.stereotype.Component;

@Component
public class SayHelloServiceGermanyImpl implements SayHelloService {
    @Override
    public void sayHello() {
        System.out.println("Hallo");
    }

    @Override
    public Byte getCode() {
        return CountryCode.GERMANY.getCode();
    }
}
```

```java
package com.mengyunzhi.demo.dynamicautowire;

/**
 * 国家代码
 */
public enum CountryCode {
    CHINA((byte) 0, "中国"),
    USA((byte) 1, "美国"),
    GERMANY((byte) 2, "德国");
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
```

### 单元测试
```java
   @Test
    public void sayHello1() {
        // 默认说你好
        speakService.sayHello();

        // 将国家设置为美国，再说你好
        sayHelloFactory.setCountryCode(CountryCode.USA);
        speakService.sayHello();

        // 将国家设置为德国，再说你好
        sayHelloFactory.setCountryCode(CountryCode.GERMANY);
        speakService.sayHello();

        // 将国家设置为中国，再说你好
        sayHelloFactory.setCountryCode(CountryCode.CHINA);
        speakService.sayHello();
    }
```

测试结果如下：
```
您好
hello
Hallo
您好
```

## 总结
在解决问题时，只所有我们看的不够远，可能是由于自己站的不够高。同样的问题，困惑我了多日，直到近期系统的学习`设计模式` 、`angular官方教程`、`Spring 实战`后，结果项目变更的新需求，才在使用设计模式解决定制化需求上有所启发。

> 欲穷千里目，更上一层楼 唐·王之涣·《登鹳雀楼》




