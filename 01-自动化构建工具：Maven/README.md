> 笔记来源：[Maven零基础入门教程（一套轻松搞定maven工具）](https://www.bilibili.com/video/BV1TW411g7hP)

[TOC]

# 自动化构建工具：Maven

**目前掌握的技术**

![软件分层](https://i.loli.net/2021/10/19/sDB8iIomZd3LEaM.png)

**目前技术在开发中存在的问题**

1. <mark>一个项目就是一个工程</mark>

   - **产生的问题**：项目非常庞大时，就不适合使用 package 划分模块。最好是<mark>一个模块对应一个工程</mark>，这样利于分工协作
   - **借助 Maven**：可以将<mark>一个项目拆分成多个工程</mark>

2. 项目所需 Jar 包需要<mark>手动复制粘贴</mark>至`WEB-INF/lib`下

   - **产生的问题**：同样的 Jar 包重复出现在不同的项目工程中，一来占用存储空间，二来显得臃肿
   - **借助 Maven**：可以将 Jar 存储在 *仓库* 中，需使用的项目工程中只需 *引用* 即可，无需真正地复制 Jar 包

3. Jar 包需要别人替我们准备好，或是官网下载

   - **产生的问题**：不同技术的官网提供 Jar 包下载的形式是五花八门的，有些官网就是通过 Maven 或 SVN 等专门的工具提供下载的。如果下载的 Jar 包 *来路不正* ，那么很可能其中的内容也是不正规的
   - **借助 Maven**：可以以规范的方式下载 Jar 包。因为所有知名框架或是第三方工具的 Jar 包已经按照统一规范存放在了 Maven 的中央仓库中，其内容也是可靠的
   - **Tips**：<mark>统一规范</mark>不仅对于 IT 开发领域非常之重要，对于整个人类社会也是非常之重要

4. 一个 Jar 包所依赖的其他 Jar 包需要<mark>手动加入项目</mark>中

   - **产生的问题**：如果所有的 Jar 包依赖关系都需要程序员自己了解的特别清楚，就会极大地<mark>增加学习和开发成本</mark>

   - **借助 Maven**：自动将 Jar 包所需的依赖包导入进来

     FileUpload 组件 → IO 组件；commons-fileupload-1.3.jar 依赖于 commons-io-2.0.1.jar

     ![image-20211019214158362](https://i.loli.net/2021/10/19/PO4pomzXyMAVvQx.png)



## 1、Maven 到底是啥？

- **概念**：Maven 是 Java 平台上的<mark>自动化构建工具</mark>（Maven 本身也是使用 Java 编写的）

- **构建工具发展历程**：`Make` :arrow_right: `Ant` :arrow_right: `Maven` :arrow_right: `Gradle`



## 2、什么是构建？

以 Java 源文件、框架配置文件、HTML/CSS/JS/JSP、图片等资源为 *原材料*，去 *生产* 一个可以运行的工程项目的过程

- 编译：Java 源文件 :arrow_right: ^编译^ :arrow_right: Class 字节码文件
- 部署：一个 BS 项目最终运行的并不是动态 Web 工程本身，而是动态 Web 工程*编译* 后的结果

要深入地理解构建的含义，可以从以下三个层面来看：

1. **纯 Java 代码**

   大家都知道，我们Java是一门编译型语言，`.java`扩展名的源文件需要编译成`.class`扩展名的字节码文件才能够执行。所以编写任何 Java 代码想要执行的话就必须经过编译得到对应的`.class`文件

2. **Web 工程**

   当我们需要通过浏览器访问 Java 程序时就必须将包含 Java 程序的 Web 工程编译的结果 *拿* 到服务器上的指定目录，并启动服务器才行。这个 *拿* 的过程我们叫部署。我们可以将未编译的 Web 工程比喻为一只生的鸡，编译好的 Web 工程是一只煮熟的鸡，编译部署的过程就是将鸡炖熟。即：动态 Web 工程 :arrow_right: ^编译、部署^ :arrow_right: 编译结果  <=>  生鸡 :arrow_right: ^煮熟^ :arrow_right: 熟鸡

   Web 工程和其编译结果的目录结构对比见下图

   ![image-20211020211940092](https://i.loli.net/2021/10/20/d6zkyF8li7cKWHo.png)

   开发过程中，所有的路径或配置文件中的类路径都是以编译结果的目录结构为标准的

   Tips：运行时环境

   ![image-20211019224029879](https://i.loli.net/2021/10/19/Y2VwfnyHXkTLJzD.png)

   其实是一组 jar 包的引用，并没有把 jar 包本身复制到工程中，所以并不是目录

3. **实际项目**

   在实际项目中整合第三方框架， Web 工程中除了 Java 程序和 JSP 页面、图片等静态资源之外，还包括第三方框架的 jar 包以及各种各样的配置文件。所有这些资源都必须按照正确的目录结构部署到服务器上，项目才可以运行

所以综上所述：构建就是以我们编写的 Java 代码、框架配置文件、国际化等其他资源文件、JSP 页面和图片等静态资源作为 *原材料*，去 *生产* 出一个可以运行的项目的过程



## 3、构建过程中的各个环节

- :one: **清理**：讲之前编译得到的旧的`.class`字节码文件删除，为下一次编译做准备
- :two: **编译**：将 Java 源程序编译成 Class 字节码文件
- :three: **测试**：自动测试，调用 Junit 程序
- :four: **报告**：测试程序执行的结果
- :five: **打包**：动态 Web 工程打成 War 包，Java 工程打成 Jar 包
- :six: **安装**：讲打包得到的文件 *复制* 到 *仓库* 中的特定位置
- :seven: **部署**：将动态 Web 工程生成的 War 包 *复制* 到 Servlet 容器中的指定目录下，使其可以运行



## 4、自动化构建

其实上述环节我们在 Eclipse 中都可以找到对应的操作，只是不太标准。那么既然 IDE 已经可以进行构建了我们为什么还要使用 Maven 这样的构建工具呢？我们来看一个小故事：

> 这是阳光明媚的一天。托马斯向往常一样早早的来到了公司，冲好一杯咖啡，进入了自己的邮箱——很不幸，QA 小组发来了一封邮件，报告了他昨天提交的模块的测试结果——有BUG。
>
> “好吧，反正也不是第一次”，托马斯摇摇头，进入 IDE，运行自己的程序，编译、打包、部署到服务器上，然后按照邮件中的操作路径进行测试。
>
> “嗯，没错，这个地方确实有问题”，托马斯说道。于是托马斯开始尝试修复这个BUG，当他差不多有眉目的时候已经到了午饭时间。
>
> 下午继续工作。BUG很快被修正了，接着托马斯对模块重新进行了编译、打包、部署，测试之后确认没有问题了，回复了QA小组的邮件。
>
> 一天就这样过去了，明媚的阳光化作了美丽的晚霞，托马斯却觉得生活并不像晚霞那样美好啊。

让我们来梳理一下托马斯这一天中的工作内容

![image-20211020220633809](https://i.loli.net/2021/10/20/Qk1g9OypNmDYCnU.png)

从中我们发现，托马斯的很大一部分时间花在了“**编译、打包、部署、测试**”这些程式化的工作上面，而真正需要由“**人**”的智慧实现的分析问题和编码却只占了很少一部分

能否将这些程式化的工作交给机器自动完成呢？——当然可以！这就是自动化构建

![image-20211020220810813](https://i.loli.net/2021/10/20/NidHTa1qheJtOk6.png)

此时 Maven 的意义就体现出来了，它可以自动地从构建过程的起点一直执行到终点



## 5、安装 Maven 核心程序

1. 检查 JAVA_HOME 环境变量

   ![image-20211020221238991](https://i.loli.net/2021/10/20/yQdmFgXJCNzRSwZ.png)

2. 解压 Maven 核心程序的压缩包，放在一个非中文无空格路径下

3. 配置 Maven 相关的环境变量

   - `MAVEN_HOME`或`M2_HOME`

   ![image-20211020222833121](https://i.loli.net/2021/10/20/csHEu5aO3YAWoiQ.png)

   - `PATH`

   ![image-20211020222907269](https://i.loli.net/2021/10/20/QnrDbClYOgUPo49.png)

4. 验证：运行`mvn -v`查看 Maven 版本

   ![image-20211020223150834](https://i.loli.net/2021/10/20/5fYZMqQVnDFoeJx.png)



## 6、Maven 的核心概念

- :one: **约定的目录结构**
- :two: **POM**
- :three: **坐标**
- :four: **依赖**
- :five: **仓库**
- :six: **生命周期 / 插件 / 目标**
- :seven: **继承**
- :eight: **聚合**



## 7、约定的目录结构

- **根目录**：工程名
- |—— **src 目录**：源码
- |—— **pom.xml文件**：Maven 工程的核心配置文件
- |———— **main 目录**：存放主程序
- |———————— **java 目录**：存放 Java 源文件
- |———————— **resource 目录**：存放框架或其他工具的配置文件
- |———— **test 目录**：存放测试程序
- |———————— **java 目录**：存放 Java 测试源文件
- |———————— **resource 目录**：存放框架或其他工具的测试配置文件

**为什么要遵守约定的目录结构呢？**

   - *巧妇难为无米之炊*：Maven 要负责我们项目的自动化构建，以编译为例，Maven 要想自动进行编译，就必须要知道 Java 源文件的位置
   - 如果想让框架或工具知道我们自定义的东西，方法有二：
        1. **以配置的方式指定**：如`<param-value>classpath:spring-context.xml</param-value>`
        2. **遵守框架内部约定**：如`log4j.properties`/`log4j.xml`
- JavaEE 的开发共识：**约定 > 配置 > 编码**



## 8、常见的 Maven 命令

:heavy_exclamation_mark: :heavy_exclamation_mark: :heavy_exclamation_mark: 注意：执行与构建过程相关的 Maven 命令，必须进入 pom.xml 所在的目录

与构建过程相关：编译、测试、打包、.……

- `mvn clean`：清理
- `mvn compile `：编译主程序
- `mvn test-compile`：编译测试程序
- `mvn test`：执行测试
- `mvn package`：打包
- `mvn install`：安装
- `mvn site`：生成站点



## 9、关于联网问题

Maven 核心程序仅仅是定义了抽象的生命周期，但是具体的工作必须要由插件来完成。而插件本身并不包含在 Maven 核心程序中

1. 执行的 Maven 命令需要用到某些插件时，Maven 核心程序会首先在本地仓库中查找
   - 本地仓库的默认位置：`【系统当前用户的家目录】\.m2\repository`（windows：`C:\Users\[用户名]\.m2\repository`）
2. 修改本地仓库的默认位置，Maven 核心程序会到我们事先准备好的目录下查找插件
   - 打开`[Maven解压目录]\conf\settings.xml`，找到`localRepository`标签，修改目录
3. Maven 核心程序如果在本地仓库中找不到需要的插件，会自动到中央仓库下载。如果此时无法连接外网，则构建失败



## 10、第一个 Maven 程序

### 目录结构

![image-20211022200617690](https://i.loli.net/2021/10/22/c3eW2LOaKNmpjbu.png)

### 编写代码

```java
public class Hello {
  public String sayHello(String name) {
    return "Hello " + name + "!";
  }
}

public class HelloTest {
  @Test
  public void testHello() {
    Hello hello = new Hello();
    String results = hello.sayHello("world");
    assertEquals("Hello world!", results);
  }
}
```

### 编译

```shell
mvn compile
```

执行过程

![image-20211022200853734](https://i.loli.net/2021/10/22/w4TdnC5hPq3ik6J.png)

目录内容：

- `classes`目录

![image-20211022202257765](https://i.loli.net/2021/10/22/hBEOXNUjovPIm45.png)

### 测试编译

```shell
mvn test-compile
```

执行过程

![image-20211022202142335](https://i.loli.net/2021/10/22/tkNahpPO2BQ5sXW.png)

目录内容：

- `classes`目录：主程序编译后的字节码文件
- `test-classes`目录：测试程序编译后的字节码文件

![image-20211022202245195](https://i.loli.net/2021/10/22/CnBqgLNMFJSGoQi.png)

### 测试

```shell
mvn test
```

执行过程

![image-20211022202514540](https://i.loli.net/2021/10/22/nAZ9KG1VYDrqHXp.png)

目录结构

- `surefire-reports`：测试报告，本例为`com.vectorx.maven.HelloTest.txt`
- 测试报告会统计所有测试方法执行的最终结果

![image-20211022202427603](https://i.loli.net/2021/10/22/WE6Lu9txV3kUlBo.png)

![image-20211022202839723](https://i.loli.net/2021/10/22/wYmtBLgaRNvEfCx.png)

本例只有一个测试方法，并且最终运行结果为：`运行成功数：1，运行失败数：0，运行错误数：0，跳过：0，总耗时：0.103 秒`，说明本例中`testHello`测试方法断言正确，验证通过

### 打包

```
mvn package
```

执行过程

![image-20211022200943539](https://i.loli.net/2021/10/22/k9zrRNnsLomS1yi.png)

目录内容：

- `classes`/`test-classes`：主程序 / 测试程序编译的字节码文件
- `surefire-reports`：测试报告
- `Hello-0.0.1-SNAPSHOT.jar`：jar 包文件
- `maven-archiver`/`maven-status`等其他额外文件

![image-20211022203445618](https://i.loli.net/2021/10/22/Vd36r4yiSFA5GL1.png)

### 清理

```shell
mvn clean
```

执行过程

![image-20211022200830744](https://i.loli.net/2021/10/22/erXvUTE8YnGyKdZ.png)

目录内容：

- 清理会清空整个`target`目录及其内容

![image-20211022202049176](https://i.loli.net/2021/10/22/4cRTxhKYfwJsLki.png)
