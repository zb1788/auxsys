package com.vcom.auxsys.tasks;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class TestTask {
    private static final SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    //每隔3秒执行一次
//    @Scheduled(fixedRate = 3000)
    //通过crond设置
    @Scheduled(cron = "30-40 * * * * ?")
    public void reportCurrentTime(){
        System.out.println("现在时间："+dataFormat.format(new Date()));
    }
}
