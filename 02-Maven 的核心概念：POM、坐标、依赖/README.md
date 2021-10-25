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
<groupId>org.springframework</groupId>
<artifactId>spring-core</artifactId>
<version>4.0.0.RELEASE</version>
```

仓库中路径：

`org\springframework\spring-core\4.0.0.RELEASE\spring-core-4.0.0.RELEASE.jar`



## 3、仓库

### 3.1、仓库的分类

- **本地仓库**：本地部署的仓库目录，为当前电脑所有 Maven 工程服务

- **远程仓库**

  - **私服**：搭建在局域网中，为局域网范围内的所有 Maven 工程服务

    ![image-20211022210545309](https://i.loli.net/2021/10/22/bBhaNO7CSJXIzv1.png)

  - **中央仓库**：搭建在 Internet 上，为全世界所有 Maven 工程服务

  - **中央仓库镜像**：搭建在各大洲，为中央仓库分担流量。减轻中央仓库的压力，同时更快的响应用户请求

### 3.2、仓库的内容

- Maven 自身所需的插件
- 第三方框架或工具的 Jar 包
- 自己开发的 Maven 工程



## 4、第二个 Maven 工程

**目录结构**

![image-20211025210027979](https://i.loli.net/2021/10/25/uMTZ3FJY1RQCelN.png)

**pom.xml**

```xml
<?xml version="1.0"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.vectorx.maven</groupId>
    <artifactId>HelloFriend</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>HelloFriend</name>
    <dependencies>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.0</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>com.vectorx.maven</groupId>
            <artifactId>Hello</artifactId>
            <version>0.0.1-SNAPSHOT</version>
            <scope>compile</scope>
        </dependency>
    </dependencies>
</project>
```

**HelloFriend**

```java
public class HelloFriend {
  public String sayHello2Friend(String name) {
    Hello hello = new Hello();
    String str = hello.sayHello(name) + " I am " + this.getMyName();
    System.out.println(str);
    return str;
  }

  public String getMyName() {
    return "John";
  }
}
```

**HelloFriendTest**

```java
public class HelloFriendTest {
  @Test
  public void testHelloFriend() {
    HelloFriend helloFriend = new HelloFriend();
    String results = helloFriend.sayHello2Friend("world");
    assertEquals("Hello world! I'm John", results);
  }
}
```

此时，我们运行`mvn compile`命令，能否成功呢？

![image-20211025210311155](https://i.loli.net/2021/10/25/gskNtAaf1GeY8Pc.png)

我们发现，编译失败了：

```java
Failed to execute goal on project HelloFriend: Could not resolve dependencies for project com.vectorx.maven:HelloFriend:jar:0.0.1-SNAPSHOT: Could not find artifact com.vectorx.maven:Hello:jar:0.0.1-SNAPSHOT
```

即：无法解决 HelloFriend 包中的依赖，找不到 Hello 的 Jar 包

根据前面所学知识，我们知道：

- 执行的 Maven 命令需要用到某些插件时，Maven 核心程序会首先在本地仓库中查找
- 如果在本地仓库中找不到需要的插件，会自动到中央仓库下载。如果此时无法连接外网，则构建失败

而我们这里的 Hello 本身是自定义的 Maven 工程，其中的坐标也是自定义的，中央仓库中是几乎不可能有的

我们希望的是 Maven 核心程序在本地仓库中寻找，并且能够找到，这就要了解下 Maven 依赖的相关知识了



## 5、依赖（初步介绍）

### 5.1、依赖的基本操作

Maven 解析依赖信息时会到本地仓库中查找被依赖的 jar 包

对于我们自己开发的 Maven 工程，使用`mvn install`命令安装后就可以进入仓库

》》》我们实操一遍

![image-20211025211647843](https://i.loli.net/2021/10/25/oTAXVhaRFfESvDr.png)

![image-20211025211712214](https://i.loli.net/2021/10/25/8CfyEbp59oncBzW.png)

我们注意到，`install`命令，将工作空间中 Hello 的 Jar 包安装到了默认的用户家目录下

```java
[INFO] Installing D:\workspace\NOTE_Maven\Demo\Hello\target\Hello-0.0.1-SNAPSHOT.jar to C:\Users\Archimedes\.m2\repository\com\vectorx\maven\Hello\0.0.1-SNAPSHOT\Hello-0.0.1-SNAPSHOT.jar
[INFO] Installing D:\workspace\NOTE_Maven\Demo\Hello\pom.xml to C:\Users\Archimedes\.m2\repository\com\vectorx\maven\Hello\0.0.1-SNAPSHOT\Hello-0.0.1-SNAPSHOT.pom
```

我们打开自己的用户家目录，确实发现了 Hello 的 Jar 包已经安装到了我们的本地仓库中

![image-20211025211759937](https://i.loli.net/2021/10/25/FAzsyh7Xw5rB6KR.png)

好，现在我们再次对 HelloFriend 进行编译，看下是否会成功

![image-20211025212301294](https://i.loli.net/2021/10/25/pqAiTbJHSa8MDU4.png)

果然不负众望，编译成功了！

### 5.2、依赖的范围

大家注意到上面的依赖信息中除了目标 jar 包的坐标还有一个`scope`设置，这是依赖的范围

依赖的范围有几个可选值，我们用得到的是：`compile`、`test`、`provided`

#### compile 范围依赖

- 对主程序是否有效：有效
- 对测试程序是否有效：有效
- 是否参与打包部署：参与

#### test 范围依赖

- 对主程序是否有效：无效
- 对测试程序是否有效：有效
- 是否参与打包部署：不参与
- 典型例子：`junit`

#### provided 范围依赖

- 对主程序是否有效：有效
- 对测试程序是否有效：有效
- 是否参与打包部署：不参与
- 典型例子：`servlet-api`

**compile & test 对比**

![compile&test](https://i.loli.net/2021/10/25/sGHIcpPdFWKSQ7x.png)

结合具体例子：对于 HelloFriend 来说，Hello 就是服务于主程序的，junit 是服务于测试程序的

HelloFriend 主程序需要 Hello 是非常明显的，测试程序由于要调用主程序所以也需要 Hello，所以`compile`范围依赖对主程序和测试程序都应该有效

HelloFriend 的测试程序部分需要 junit 也是非常明显的，而主程序是不需要的，所以`test`范围依赖仅仅对于测试程序有效

**compile & provided 对比**

![compile&provided](C:/Users/Archimedes/Pictures/Test/2E9LJxuUqmdQKZl.png)

**小结**

| 依赖范围           |      compile       |        test        |      provided      |
| :----------------- | :----------------: | :----------------: | :----------------: |
| 对主程序是否有效   | :heavy_check_mark: |        :x:         | :heavy_check_mark: |
| 对测试程序是否有效 | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: |
| 是否参与打包部署   | :heavy_check_mark: |        :x:         |        :x:         |

