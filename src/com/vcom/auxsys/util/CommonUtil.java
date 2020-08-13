package com.vcom.auxsys.util;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import zzvcom.sys.util.ReadProperties;

import net.sf.json.JSONObject;


/**
 * @description： 公共方法类
 * @time： 2015-11-23
 * @author： donghaoyu
 */
public class CommonUtil {
    
    /**
     * @description：解码
     * @time： 2015-11-23
     * @author：donghaoyu
     * @param text
     * @return 
     */
    public static String decode4utf8(String text){
        if(text==null || text.trim().length()<=0)
            return text;
        String s = "";
            try {
                s = java.net.URLDecoder.decode(text, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        //过滤特殊字符
        //s = StringFilter(s);
        return s;
    }
    
    /**
     * @description：json串中存在集合的可以用一下方法转换对象
     * @time： 2015-11-27
     * @author：donghaoyu
     * @param jsonString
     * @param pojoCalss
     * @param classMap
     * @return 
     */
    public static Object getObject3JsonString(String jsonString, Class pojoCalss ,Map<String, Class> classMap) {
        Object pojo;
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        pojo = JSONObject.toBean(jsonObject, pojoCalss,classMap);
        return pojo;
    }
    
    /**
     * <p> 标题:json转换对象 
     * @param jsonString  字符串
     * @param pojoCalss   类名称
     * @return
     */
    public static Object getObject4JsonString(String jsonString, Class pojoCalss) {
        Object pojo;
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        pojo = JSONObject.toBean(jsonObject, pojoCalss);
        return pojo;
    }
    
    
    public static String getJsonHtml(String str) {
    	if(str!=null) {
    		//replaceAll 参数为正则，需要双斜线表示一个斜线，同时字符串双斜线表示一个斜线，所以4个斜线表示一个斜线
    		str=str.replaceAll(";","&#59;").replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("&", "&amp;").replaceAll("\"", "&quot;").replaceAll("\\\\","\\\\\\\\");
    	}
    	return str;
    }
    
    /**
     * <p> 标题:字符串转换对象
     * </p>
     * @param jsonString 字符串
     * @return     返回Map对象
     */
    public static LinkedHashMap getMap4Json(String jsonString) {
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        Iterator keyIter = jsonObject.keys();
        String key;
        Object value;
        LinkedHashMap valueMap = new LinkedHashMap();
        while (keyIter.hasNext()) {
            key = (String) keyIter.next();
            value = jsonObject.get(key);
            valueMap.put(key, value);
        }
        return valueMap;
    }
    
    // 过滤特殊字符  ,过滤英文符号
    public static String StringFilter(String str) throws PatternSyntaxException   {     
      ReadProperties readProperties = new ReadProperties();
      String regEx = readProperties.readPropertiesInfo("regEx");
      Pattern p = Pattern.compile(regEx);     
      Matcher m = p.matcher(str);     
      return m.replaceAll("").trim();     
    }
    
    public static boolean sfcztszf(String str) throws PatternSyntaxException   {     
        ReadProperties readProperties = new ReadProperties();
        String regEx = readProperties.readPropertiesInfo("regEx");
        //Pattern p = Pattern.compile(regEx);     
        //Matcher m = p.matcher(str); 
        boolean srcz = false;
        for(int i=0;i<regEx.length();i++){
            if(str.indexOf(regEx.charAt(i))>-1){
                srcz = true;
                break;
            }
        }
        return srcz;
      }
    
    /**
     * @description：判断是否为数字
     * @time： 2015-12-14
     * @author：donghaoyu
     * @param str
     * @return 
     */
    public static boolean fnumber(String str){
        String regEx="[0-9]*";   
        Pattern p = Pattern.compile(regEx);     
        Matcher m = p.matcher(str);
        return m.matches();
    }
    
    public static void main(String args[]) {
    	System.out.println(getJsonHtml("a;lsdjf\\"));
    }
    
}
