import java.sql.*;

public abstract class UserDao {


    public User get(int id) throws ClassNotFoundException, SQLException {
        Connection connection = getConnection();

        PreparedStatement preparedStatement=
                connection.prepareStatement("select * from user where id =?");
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setName(resultSet.getString("name"));
        user.setPassword(resultSet.getString("password"));

        resultSet.close();
        preparedStatement.close();
        connection.close();


        return user;
    }


    public int insert(User user) throws ClassNotFoundException, SQLException {
        Connection connection = getConnection();

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

    public abstract Connection getConnection() throws ClassNotFoundException, SQLException ;
//    {
//        Class.forName("com.mys1ql.jdbc.111Driver");
//
//        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/jejuuniv?characterEncoding=utf-8" ,
//                "root", "456111");
//
//        return connection;
//    }

}


