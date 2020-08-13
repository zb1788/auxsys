/**
 * SpringContextUtil.java
 * @date   2012-2-7 下午05:03:42
 * @author 苌黄林
 * @version 
 */
package com.vcom.auxsys.util;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


/**
 * @todo 获取当前Spring上下文
 *
 */
public class SpringContextUtil implements ApplicationContextAware {
	private static transient Logger log = Logger.getLogger(SpringContextUtil.class);
	private static ApplicationContext applicationContext;     //Spring应用上下文环境
	 
	  /**
	  * 实现ApplicationContextAware接口的回调方法，设置上下文环境
	  * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)   
	  * @param applicationContext
	  * @throws BeansException
	  */
	  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		  log.info(" ***  *** [SpringContextUtil] *** set Spring ApplicationContext! *** [SpringContextUtil] *** *** ");
		  this.applicationContext = applicationContext;
	  }
	 
	  /**
	   * 安全起见，屏蔽直接获取Spring上下文方法。
	  * @return ApplicationContext
	  */
	  /*
	  public static ApplicationContext getApplicationContext() {
	    return applicationContext;
	  }
	 */
	  
	  /**
	  * 获取对象   
	  * @param name
	  * @return Object 一个以所给名字注册的bean的实例
	  * @throws BeansException
	  */
	  public static Object getBean(String name) throws BeansException {
		  if(applicationContext!=null){
			  return applicationContext.getBean(name);
		  }
		  log.error("NullException :: SpringContextUtil init ApplicationContext is null!!! CAN NOT GET BEAN!!!");
		  return null;
	  }
	 
	  /**
	  * 获取类型为requiredType的对象
	  * 如果bean不能被类型转换，相应的异常将会被抛出（BeanNotOfRequiredTypeException）
	  * @param name       bean注册名
	  * @param requiredType 返回对象类型
	  * @return Object 返回requiredType类型对象
	  * @throws BeansException
	  */
	  public static Object getBean(String name, Class requiredType) throws BeansException {
	    return applicationContext.getBean(name, requiredType);
	  }
	 
	  /**
	  * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true 
	  * @param name
	  * @return boolean
	  */
	  public static boolean containsBean(String name) {
	    return applicationContext.containsBean(name);
	  }
	 
	  /**
	  * 判断以给定名字注册的bean定义是一个singleton还是一个prototype。
	  * 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）   
	  * @param name
	  * @return boolean
	  * @throws NoSuchBeanDefinitionException
	  */
	  public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
	    return applicationContext.isSingleton(name);
	  }
	 
	  /**
	  * @param name
	  * @return Class 注册对象的类型
	  * @throws NoSuchBeanDefinitionException
	  */
	  public static Class getType(String name) throws NoSuchBeanDefinitionException {
	    return applicationContext.getType(name);
	  }
	 
	  /**
	  * 如果给定的bean名字在bean定义中有别名，则返回这些别名   
	  * @param name
	  * @return
	  * @throws NoSuchBeanDefinitionException
	  */
	  public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
	    return applicationContext.getAliases(name);
	  }
	  
	  /**
	   * 是否被初始化,applicationContext是否为空,如果为空，则此类中方法均不可用
	   * 
	   * @return
	   */
	  public static boolean inited(){
		  if(applicationContext==null){
			  return false;
		  }
		  return true;
	  }

}
