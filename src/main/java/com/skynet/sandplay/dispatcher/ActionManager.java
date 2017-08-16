package com.skynet.sandplay.dispatcher;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.skynet.sandplay.annotation.ActionMark;
import com.skynet.sandplay.form.BaseMsg;

@Component("actionManager")
public class ActionManager {

	private static Action[][] actions = new Action[10][100];
	@Resource
	private ActionCtroller actionCtroller;
	
	static {
		Class c = ActionCtroller.class;
		Method[] methods = c.getMethods();
		for(final Method method : methods) {
			if(method.isAnnotationPresent(ActionMark.class)) {
				ActionMark annotation = method.getAnnotation(ActionMark.class);
				int serviceId = annotation.serviceId();
				int actionId = annotation.actionId();
				System.out.println("serviceId:" + serviceId + ", actionId:" + actionId);
				Action action = new Action() {
					@Override
					public String execute(ActionCtroller actionCtroller, BaseMsg msg) throws Exception{
						return (String)method.invoke(actionCtroller, msg);
					}
					
				};
				actions[serviceId][actionId] = action;
			}
		}
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
			 result = action.execute(actionCtroller, msg);
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
