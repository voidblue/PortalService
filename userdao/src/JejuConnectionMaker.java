import com.mysql.jdbc.ExceptionInterceptor;
import com.mysql.jdbc.Extension;
import com.mysql.jdbc.MySQLConnection;
import com.mysql.jdbc.log.Log;
import org.springframework.beans.factory.annotation.Value;

import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;
import java.util.concurrent.Executor;

public class JejuConnectionMaker implements ConnectionMaker {
    @Value("${db.classname}")
    private String className;
    @Value("${db.url}")
    private String url;
    @Value("${db.username}")
    private String username;
    @Value("${db.password}")
    private String password;


    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(className);

        Connection connection = DriverManager.getConnection(url,
                username, password);

        return connection;
    }
}
