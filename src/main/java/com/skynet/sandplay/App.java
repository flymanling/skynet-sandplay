package com.skynet.sandplay;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.skynet.sandplay.model.User;
import com.skynet.sandplay.service.IBaseService;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
//        System.out.println( "Hello World!" );
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring.xml");
        IBaseService service = (IBaseService) ac.getBean("baseService");
        User user = new User();
        user.setName("ethan ling");
        service.save(user);
    }

}
