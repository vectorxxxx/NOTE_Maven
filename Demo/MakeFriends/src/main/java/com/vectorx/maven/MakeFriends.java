package com.vectorx.maven;

/**
 * @author Archimedes
 */
public class MakeFriends {
    public String makeFriends(String name) {
        HelloFriend friend = new HelloFriend();
        friend.sayHello2Friend("litingwei");
        String str = "Hey," + friend.getMyName() + "make a friend please.";
        System.out.println(str);
        return str;
    }
}
