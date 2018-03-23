import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


//strategy패턴
public interface ConnectionMaker {
    Connection getConnection() throws ClassNotFoundException, SQLException;
//        Class.forName("com.mysql.jdbc.Driver");
//
//        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/jejuuniv?characterEncoding=utf-8",
//                "root", "456111");
//
//        return connection;

}