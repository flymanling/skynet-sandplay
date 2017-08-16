package com.skynet.sandplay.dispatcher;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.skynet.sandplay.form.BaseMsg;

@Component("springContext")
public class SpringContext implements ApplicationContextAware  {

	private static ApplicationContext applicationContext; 
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		SpringContext.applicationContext = applicationContext;
		
	}
	
	/**
	* 取得存储在静态变量中的ApplicationContext.
	*/ 
	public static ApplicationContext getApplicationContext() { 
		checkApplicationContext(); 
		return applicationContext; 
	} 
	   
	/**
	* 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
	*/ 
	@SuppressWarnings("unchecked") 
	public static <T> T getBean(String name) { 
		checkApplicationContext(); 
		return (T) applicationContext.getBean(name); 
	} 
	   
	/**
	* 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
	*/ 
	@SuppressWarnings("unchecked") 
	public static <T> T getBean(Class<T> clazz) { 
		checkApplicationContext(); 
		return (T) applicationContext.getBeansOfType(clazz); 
	} 
	   
	/**
	* 清除applicationContext静态变量.
	*/ 
	public static void cleanApplicationContext() { 
		applicationContext = null; 
	} 
	   
	private static void checkApplicationContext() { 
		if (applicationContext == null) { 
			throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义SpringContextHolder"); 
		} 
	} 

	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("spring.xml");
		SpringContext springContext = (SpringContext) ac.getBean("springContext");
		UserActionCtroller controller = springContext.getBean("userActionCtroller");
		BaseMsg msg = new BaseMsg();
		msg.actionId = 1;
		msg.serviceId = 1;
		String rs = controller.getUser(msg);
		System.out.println(rs);
	}
}
