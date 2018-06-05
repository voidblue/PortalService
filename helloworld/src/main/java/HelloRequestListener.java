import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

public class HelloRequestListener implements ServletRequestListener {
    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        System.out.println("******************* request Destroy *******************");
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        System.out.println("******************* request Init *******************");

    }
}
