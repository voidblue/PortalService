import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class HelloContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("******************* context Init *******************");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("******************* contest destroy *******************");

    }
}
