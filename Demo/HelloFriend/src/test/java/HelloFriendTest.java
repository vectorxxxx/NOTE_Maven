package com.vectorx.maven;

import static junit.framework.Assert.*;

import com.vectorx.maven.HelloFriend;
import org.junit.Test;

public class HelloFriendTest {

  @Test
  public void testHelloFriend() {
    HelloFriend helloFriend = new HelloFriend();
    String results = helloFriend.sayHello2Friend("world");
    assertEquals("Hello world! I'm John", results);
  }
}
