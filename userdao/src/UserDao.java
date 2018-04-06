import javax.sql.DataSource;
import java.sql.*;

public class UserDao {
    //이 코드 떄문에 한라 코드를 어떻게 해야할지 ?? dependancy 생김
    //private final ConnectionMaker connectionMaer = new JejuConnectionMaker();

    //Dependancy Injection, connectionMaker으로 생기는 의존성을 클라이언트 한테 넘김(생성자를 통해서)
    //클라이언트(test에서) DaoFactory에게 다시 넘김
    //스프링은 이 DaoFactory와 같은 역할을 해줌

    //클래스를 추상화 할 필요가 있어서 인터페이스 사용
    //함수를 추상회 할 필요가 있는경우 상속기판 template method사용
    private final DataSource dataSource;

    public UserDao(DataSource dataSource) {
        this.dataSource= dataSource;
    }
    //변경이 되지 않은 부분을 Context <-- 변하지 않은 부분을 묶어놓은 부분, 및 그 환경

    //아래 get함수는 Context라고 할 수 있다.
    public User get(int id) throws ClassNotFoundException, SQLException {
        Connection connection = null;

        PreparedStatement preparedStatement= null;

        ResultSet resultSet = null;

        User user;

        try {

            connection = dataSource.getConnection();   //팩토리 메소드 패턴 ??
            preparedStatement = connection.prepareStatement("select * from user where id =?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            user = new User();
            resultSet.next();
            user.setId(resultSet.getInt("id"));
            user.setName(resultSet.getString("name"));
            user.setPassword(resultSet.getString("password"));

            resultSet.close();
            preparedStatement.close();
            connection.close();
        }finally {
            if (resultSet != null)
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if (preparedStatement != null)
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if (connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        return user;
    }

    public int insert(User user) throws ClassNotFoundException, SQLException {
        Connection connection = dataSource.getConnection();

        PreparedStatement preparedStatement=
                connection.prepareStatement("INSERT INTO user (name, password) VALUES (?, ?)");
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getPassword());

        preparedStatement.executeUpdate();

        preparedStatement = connection.prepareStatement("select last_insert_id()");
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        int id = resultSet.getInt(1);
        resultSet.close();
        preparedStatement.close();
        connection.close();

        return id;
    }


    public void update(User user) throws SQLException, ClassNotFoundException {
        Connection connection = dataSource.getConnection();

        PreparedStatement preparedStatement=
                connection.prepareStatement("UPDATE user SET name = ?, password = ?  WHERE id = ?");
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setInt(3, user.getId());
        preparedStatement.executeUpdate();
    }

    public void delete(User user) throws SQLException {
        Connection connection = dataSource.getConnection();

        PreparedStatement preparedStatement=
                connection.prepareStatement("DELETE FROM user WHERE id = ?");
        preparedStatement.setInt(1, user.getId());
        preparedStatement.executeUpdate();
    }
}


