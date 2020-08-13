package zzvcom.sys.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ReadProperties {
    
    public String readPropertiesInfo(String parameter){
        //读取sysconfig.properties配置中关于上传类型的信息
        Properties prop = new Properties();
        InputStream in = ReadProperties.class.getResourceAsStream("/sysconfig.properties");
        String arg = null;
        try {
            prop.load(in);
            arg = prop.getProperty(parameter).trim();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return arg;
    }

}
