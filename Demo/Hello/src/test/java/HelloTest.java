package com.vectorx.maven;

import static junit.framework.Assert.*;

import com.vectorx.maven.Hello;
import org.junit.Test;

public class HelloTest {

  @Test
  public void testHello() {
    Hello hello = new Hello();
    String results = hello.sayHello("world");
    assertEquals("Hello world!", results);
  }
}
