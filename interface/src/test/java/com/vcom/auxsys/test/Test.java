package com.vcom.auxsys.test;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test {

    @org.junit.Test
    public void testDate(){
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        System.out.println(sf.format(date));

    }

}
