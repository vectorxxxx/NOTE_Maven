[TOC]

## 1、形式引用属性（值的占位符）

- `env.propertyName`：系统的环境变量
- `java.propertyName`：`Java`的系统属性。`java.lang.System.getProperties()`能获取到的属性，`pom`中同样可以引用
- `settings.propertyName`：`Maven`本地配置文件`settings.xml`中`settings`根元素下的属性
- `project.propertyName`：当前`pom`文件中`project`根元素下的属性
- `自定义<properties>`：自定义`<properties>`属性中的属性

例如

```java
// 系统环境变量PATH
${env.PATH}
// Java系统属性home
${java.home}
// settings文件的localRepository
${settings.localRepository}
// project中version
${project.version}
// 自定义<properties>
${maven.compiler.source}
```



## 2、执行 main 方法

```bash
# 执行指定全限定名下的java类
mvn exec:java -Dexec.mainClass="xxx.xxx.xxx.XXX"
```



## 3、Settings 文件

```xml
<settings>
    <!-- 本地仓库的地址，存放jar包-->
    <localRepository>${user.home}/.m2</localRepository>
    <mirrors>
    	<mirror>
        	<id>nexus-aliyun</id>
            <mirrorOf>central</mirrorOf>
            <name>Nexus aliyun</name>
            <url>http://maven.aliyun.com/nexus/content/groups/public</url>
        </mirror>
    </mirrors>
</settings>
```



## 4、命令参数

### 4.1、-D 传入参数

```bash
# 执行打包操作时，跳过单元测试
mvn package -Dmaven.test.skip=true
```

### 4.2、-P 使用指定的 Profile 配置

首先，假设`pom.xml`文件配置如下

```xml
<!--打包环境配置：开发环境 测试环境 正式环境-->
<profiles>
    <profile>
        <id>dev</id>
        <properties>
            <env>dev</env>
        </properties>
        <!--未指定环境时，默认打包dev环境-->
        <activation>
            <activeByDefault>true</activeByDefault>
        </activation>
    </profile>
    <profile>
        <id>test</id>
        <properties>
            <env>test</env>
        </properties>
    </profile>
    <profile>
        <id>prod</id>
        <properties>
            <env>prod</env>
        </properties>
    </profile>
</profiles>
<build>
    <!--对于项目资源文件的配置放在build中-->
    <resources>
        <resource>
            <directory>src/main/resources/${env}</directory>
        </resource>
        <resource>
            <directory>src/main/java</directory>
            <includes>
                <include>**/*.xml</include>
                <include>**/*.properties</include>
                <include>**/*.tld</include>
            </includes>
            <filtering>false</filtering>
        </resource>
    </resources>
</build>
```

此时，使用命令，标识打包本地环境，并跳过单元测试

```bash
mvn package -Pdev -Dmaven.test.skip=true
```



## 5、Web 插件

### 5.1、Jetty 插件

```xml
<build>
    <finalName>Demo</finalName>
    <plugins>
        <plugin>
            <groupId>org.mortbay.jetty</groupId>
            <artifactId>maven-jetty-plugin</artifactId>
            <version>6.1.25</version>
            <configuration>
                <!--热部署，每10秒扫描一次-->
                <scanIntervalseconds>10</scanIntervalseconds>
                <!--可指定当前项目的站点名-->
                <contextPath>/test</contextPath>
                <connectors>
                    <connector implementation="org.mortbay.jetty.nio.selectchannelconnector">
                        <!--设置启动的端口号-->
                        <port>9090</port>
                    </connector>
                </connectors>
            </configuration>
        </plugin>
    </plugins>
</build>
```

### 5.2、Tomcat 插件

```xml
<build>
    <finalName>Demo</finalName>
    <plugins>
        <plugin>
            <groupId>org.apache.tomcat.maven</groupId>
            <artifactId>tomcat7-maven-plugin</artifactId>
            <version>2.1</version>
            <configuration>
                <!--启动端口默认：8080-->
                <port>8081</port>
                <!--项目的站点名，即对外访问路径-->
                <path>/test</path>
                <!--字符集编码默认：ISO-8859-1-->
                <uriEncoding>UTF-8</uriEncoding>
                <!--服务器名称-->
                <server>tomcat7</server>
            </configuration>
        </plugin>
    </plugins>
</build>
```



## 6、Maven 仓库

### 6.1、私服

私服的在`maven`中的配置

```xml
<repositories>
    <repository>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
        <id>public</id>
        <name>Public Repositories</name>
        <url>http://192.168.0.96:8081/content/groups/public/</url>
    </repository>
    <repository>
        <id>getui-nexus</id>
        <url>http://mvn.gt.igexin.com/nexus/content/repositories/releases/</ur1>
    </repository>
</repositories>
```

私服的好处

- 降低中央仓库的负荷
- 节省自己的外网带宽
- 加速maven构建
- 提高稳定性
- <mark>部署第三方控件</mark>

### 6.2、其他公共库

```xml
<mirror>
    <id>nexus-aliyun</id>
    <mirrorOf>central</mirrorOf>
    <name>Nexus aliyun</name>
    <url>http://maven.aliyun.com/nexus/content/groups/public</url>
</mirror>
```

