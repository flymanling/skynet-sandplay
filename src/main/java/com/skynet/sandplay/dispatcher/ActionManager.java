package com.skynet.sandplay.dispatcher;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.skynet.sandplay.annotation.ActionMark;
import com.skynet.sandplay.annotation.ServiceMark;
import com.skynet.sandplay.form.BaseMsg;

@Component("actionManager")
public class ActionManager {

	private static Action[][] actions = new Action[10][100];
	private static ActionCtroller[] services = new ActionCtroller[10];
	@Resource
	private ActionCtroller actionCtroller;
	
	static {
		List<Class<?>> cList = null;
		try {
			cList = getAllAssignedClass(ActionCtroller.class);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(cList != null) {
			for(Class<?> c : cList) {
				if(c.isAnnotationPresent(ServiceMark.class) && c.isAnnotationPresent(Component.class)) {
					ServiceMark serviceMark = (ServiceMark) c.getAnnotation(ServiceMark.class);
					Component conponent = c.getAnnotation(Component.class);
					int serviceId = serviceMark.value();
					final ActionCtroller actionCtroller = SpringContext.getBean(conponent.value());
					System.out.println("resource:" + conponent.value() + ", serviceId:" + serviceId + ", actionCtroller class" + actionCtroller);
					services[serviceId] = actionCtroller;
					Method[] methods = c.getMethods();
					for(final Method method : methods) {
						if(method.isAnnotationPresent(ActionMark.class)) {
							ActionMark annotation = method.getAnnotation(ActionMark.class);
							int actionId = annotation.value();
							System.out.println("serviceId:" + serviceId + ", actionId:" + actionId);
							Action action = new Action() {
								@Override
								public String execute(BaseMsg msg) throws Exception{
									return (String)method.invoke(actionCtroller, msg);
								}
								
							};
							actions[serviceId][actionId] = action;
						}
					}
				}
			}
		}
	}
	
	/**
	 * 获取同一路径下所有子类或接口实现类
	 * @param cls
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static List<Class<?>> getAllAssignedClass(Class<?> cls) throws IOException, ClassNotFoundException {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		for (Class<?> c : getClasses(cls)) {
			if (cls.isAssignableFrom(c) && !cls.equals(c)) {
				classes.add(c);
			}
		}
		return classes;
	}
	
	/**
	* 取得当前类路径下的所有类
	* 
	* @param cls
	* @return
	* @throws IOException
	* @throws ClassNotFoundException
	*/
	public static List<Class<?>> getClasses(Class<?> cls) throws IOException, ClassNotFoundException {
		String pk = cls.getPackage().getName();
		String path = pk.replace('.', '/');
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		URL url = classloader.getResource(path);
		return getClasses(new File(url.getFile()), pk);
	}
	
	/**
	* 迭代查找类
	* 
	* @param dir
	* @param pk
	* @return
	* @throws ClassNotFoundException
	*/
	private static List<Class<?>> getClasses(File dir, String pk) throws ClassNotFoundException {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		if (!dir.exists()) {
			return classes;
		}
		for (File f : dir.listFiles()) {
			if (f.isDirectory()) {
				classes.addAll(getClasses(f, pk + "." + f.getName()));
			}
			String name = f.getName();
			if (name.endsWith(".class")) {
				classes.add(Class.forName(pk + "." + name.substring(0, name.length() - 6)));
			}
		}
		return classes;
	}
	
	public Action getAction(int serviceId, int actionId) {
		return actions[serviceId][actionId];
	}
	
	public String onBaseMsg(BaseMsg msg) {
		Action action = getAction(msg.serviceId, msg.actionId);
		if(action == null) {
			return "action not found! serviceId:" + msg.serviceId + ", actionId:" + msg.actionId;
		}
		String result = null;
		try {
			 result = action.execute(msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("spring.xml");
		ActionManager actionManager = (ActionManager) ac.getBean("actionManager");
		BaseMsg msg = new BaseMsg();
		msg.actionId = 1;
		msg.serviceId = 1;
		String result = actionManager.onBaseMsg(msg);
		System.out.println(result);
	}
}
