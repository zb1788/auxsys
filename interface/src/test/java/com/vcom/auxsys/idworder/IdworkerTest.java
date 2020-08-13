package com.vcom.auxsys.idworder;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IdworkerTest {

    @Autowired
    Sid sid;

    @Test
    public void testId(){
        String userId = sid.nextShort();
        System.out.println(userId);
        String userId2 = sid.nextShort();
        System.out.println(userId2);
    }

}
