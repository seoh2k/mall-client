package mall.client.listener;

import java.sql.DriverManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class DBListener implements ServletContextListener {

    public void contextDestroyed(ServletContextEvent sce)  { // 톰캣이 꺼질 때 해야하는 행동
    	System.out.println("contextDestroyed!");
    }
    public void contextInitialized(ServletContextEvent sce)  { // 톰캣이 켜질 때 해야하는 행동
    	try{
    		Class.forName("org.mariadb.jdbc.Driver"); // 톰캣이 켜질 때 이미 로딩되어 있으니 dbUtil에 없어도 된다
    		System.out.println("mariadb 로딩 성공");
		} catch(ClassNotFoundException e) {
			System.out.println("mariadb 로딩 실패");
			e.printStackTrace();
		}
    }
	
}
