import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class HallaUserDao extends UserDao {
    @Override
    //connetion의 결정을 이 클래스의 객체가 결정---->팩토리 메소드 패턴
    //getConnection을 UserDao가 추상함수로 만들어서 상속을 기반으로 여기서 구현해야함 ----->템플릿 패턴
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/jejuuniv?characterEncoding=utf-8" ,
                "root", "456111");

        return connection;
    }
}
