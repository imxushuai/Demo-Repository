# JSR-303

[JSR-303](https://link.jianshu.com/?t=https://jcp.org/en/jsr/detail?id=303) 是 Java EE 6 中的一项子规范，叫做 Bean Validation，官方参考实现是hibernate Validator。

Bean Validation 为 JavaBean 验证定义了相应的元数据模型和 API。缺省的元数据是 Java Annotations，通过使用 XML 可以对原有的元数据信息进行覆盖和扩展。在应用程序中，通过使用 Bean Validation 或是你自己定义的 constraint，例如 `@NotNull`, `@Max`, `@ZipCode` ， 就可以确保数据模型（JavaBean）的正确性。constraint 可以附加到字段，getter 方法，类或者接口上面。对于一些特定的需求，用户可以很容易的开发定制化的 constraint。Bean Validation 是一个运行时的数据验证框架，在验证之后验证的错误信息会被马上返回。

# 最佳实践

JSR 303校验从入门到进阶

1. 基本使用

2. 统一异常处理

3. 分组校验

4. 自定义校验