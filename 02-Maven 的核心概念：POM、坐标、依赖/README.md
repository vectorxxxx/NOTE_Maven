> 笔记来源：[Maven零基础入门教程（一套轻松搞定maven工具）](https://www.bilibili.com/video/BV1TW411g7hP)

[TOC]

# Maven 的核心概念：POM、坐标、依赖

## 1、POM

- ① `POM`：`Project Object Model`，项目对象模型
  - 类似`DOM`：`Document Object Model`，文档对象模型
- ② pom.xml 对于 Maven 工程是核心配置文件，与构建过程相关的一切设置都在这个文件中进行配置
  - 重要程度相当于 web.xml 对于动态 Web 工程



## 2、坐标

**数学中的坐标**

1. 在平面上，使用X、Y两个向量可以唯一的定位平面中的任何一个点
2. 在空间中，使用X、Y、Z三个向量可以唯一的定位空间中的任何一个点

**Maven 的坐标**

使用下面三个参数`GAV`在仓库中唯一定位一个 Maven 工程

- `G`——`groupId`：公司或组织域名倒序 + 项目名

  ```xml
  <groupId>com.vectorx.maven</groupId>
  ```

- `A`——`artifactId`：模块名

  ```xml
  <artifactId>Hello</artifactId>
  ```

- `V`——`version`：版本号

  ```xml
  <version>1.0.0-SNAPSHOT</version>
  ```

**Maven 工程的坐标与仓库中路径的对应关系**

Maven 工程的坐标：

```xml
<groupId>org.springframework</groupld>
<artifactid>spring-core</artifactld>
<version>4.0.0.RELEASE</version>
```

仓库中路径：

`org\springframework\spring-core\4.0.0.RELEASE\spring-core-4.0.0.RELEASE.jar`



## 3、仓库

### 仓库的分类

- **本地仓库**：本地部署的仓库目录，为当前电脑所有 Maven 工程服务

- **远程仓库**

  - **私服**：搭建在局域网中，为局域网范围内的所有 Maven 工程服务

    ![image-20211022210545309](https://i.loli.net/2021/10/22/bBhaNO7CSJXIzv1.png)

  - **中央仓库**：搭建在 Internet 上，为全世界所有 Maven 工程服务

  - **中央仓库镜像**：搭建在各大洲，为中央仓库分担流量。减轻中央仓库的压力，同时更快的响应用户请求

### 仓库的内容

- Maven 自身所需的插件
- 第三方框架或工具的 Jar 包
- 自己开发的 Maven 工程

