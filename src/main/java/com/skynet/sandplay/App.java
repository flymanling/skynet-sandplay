package com.skynet.sandplay;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.skynet.sandplay.dao.IBaseDao;
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
        @SuppressWarnings("unchecked")
		IBaseService<User, String> service = (IBaseService<User, String>) ac.getBean("userService");
        IBaseDao dao = (IBaseDao) ac.getBean("userDao");
//        User user = new User();
//        user.setName("ethan ling");
//        service.save(user);
        User user = (User) service.get("402881e65de12882015de1288d690000");
        System.out.println(user.getName());
    }

}
