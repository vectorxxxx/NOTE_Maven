package com.vectorx.maven;

import com.vectorx.maven.Hello;

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
