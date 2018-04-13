import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


//strategy패턴
@Deprecated
public interface ConnectionMaker {
    Connection getConnection() throws ClassNotFoundException, SQLException;
}