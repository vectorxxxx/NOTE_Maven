package com.vectorx.maven;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MakeFriendsTest {
    @Test
    public void testMakeFriends() {
        MakeFriends makeFriend = new MakeFriends();
        String str = makeFriend.makeFriends("litingwei");
        assertEquals("Hey, John make a friend please.", str);
    }
}
