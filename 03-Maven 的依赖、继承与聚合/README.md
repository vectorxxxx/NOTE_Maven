> 笔记来源：[Maven零基础入门教程（一套轻松搞定maven工具）](https://www.bilibili.com/video/BV1TW411g7hP)

[TOC]

# Maven 的依赖、集成与聚合

## 1、依赖

### 1.1、依赖的传递性

![image-20211129232327794](https://i.loli.net/2021/11/29/UMaYK4OLePs5v2m.png)

- 【1】好处：可以传递的依赖不必在每个模块工程中都重复声明，在“最下面”的工程中依赖一次即可
- 【2】注意：非 compile 范围的依赖不能传递。所以在各个工程模块中，如果有需要就得重复声明依赖

### 1.2、依赖的排除

- 【1】需要设置依赖排除的场合

![image-20211129232635639](https://i.loli.net/2021/11/29/42mVph5SbNtDO9v.png)

- 【2】依赖排除的设置方式

  ```xml
  <exclusions>
      <exclusion>
          <artifactId>commons-logging</artifactId>
          <groupId>commons-logging</groupId>
      </exclusion>
  </exclusions>
  ```

### 1.3、依赖的原则说明

- 【1】作用：解决模块工程之间的 jar 包冲突问题

- 【2】情景设定1：验证路径最短者优先原则

  ![image-20211129233424068](https://i.loli.net/2021/11/29/T4FGhLrYiMHjuAf.png)

- 【3】情景设定2：验证路径相同时先声明者优先（先声明指的是 dependency 标签的声明顺序）

  ![image-20211129233559266](https://i.loli.net/2021/11/29/1LSjOAcoKMJFR4f.png)

### 1.4、统一管理依赖的版本号

![image-20211129235341043](https://i.loli.net/2021/11/29/mcCp4FnujbTVX2d.png)

这里对 Spring 各个jar包的依赖版本都是 4.0.0，如果需要统一升级为 4.1.1，手动修改不可靠，怎么办？

- 1）使用`properties`标签内使用自定义标签统一声明版本号

  ```xml
  <properties>
      <vectorx.spring.version>4.0.0.RELEASE</vectorx.spring.version>
  </properties>
  ```

- 2）在需要统一版本的位置，使用`${自定义标签名}`引用声明的版本号

  ```xml
  <version>${vectorx.spring.version}</version>
  ```

- 3）其实`properties`标签配合自定义标签声明数据的配置，并不是只能用于声明依赖的版本号。凡是需要统一声明后再引用的场合都可以使用

  ```xml
  <properties>
      <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
  </properties>
  ```

  

## 2、继承

## 3、聚合







