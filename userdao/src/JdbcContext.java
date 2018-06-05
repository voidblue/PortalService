import lombok.AllArgsConstructor;
import lombok.Cleanup;

import javax.sql.DataSource;
import java.sql.*;


@Deprecated
@AllArgsConstructor
public class JdbcContext {//클래스를 추상화 할 필요가 있어서 인터페이스 사용
    //함수를 추상회 할 필요가 있는경우 상속기판 template method사용
    final DataSource dataSource;

    void jdbcContext(StatementStrategy statementStrategy) throws SQLException {
        @Cleanup
        Connection connection = null;
        @Cleanup
        PreparedStatement preparedStatement = null;

        connection = dataSource.getConnection();
        preparedStatement = statementStrategy.makeStatement(connection);
        preparedStatement.executeUpdate();

    }

    int jdbcContextForInsert(StatementStrategy statementStrategy) throws SQLException {
        Integer id = null;
        @Cleanup
        Connection connection = dataSource.getConnection();
        @Cleanup
        PreparedStatement preparedStatement = statementStrategy.makeStatement(connection);
        @Cleanup
        ResultSet resultSet = preparedStatement.getGeneratedKeys();

        preparedStatement.executeUpdate();
        resultSet.next();
        id = resultSet.getInt(1);

        return id;
    }

    public User jdbcContextForGet(StatementStrategy statementStrategy) throws SQLException {
        @Cleanup
        Connection connection = null;
        @Cleanup
        PreparedStatement preparedStatement = null;
        @Cleanup
        ResultSet resultSet = null;

        User user = null;

        connection = dataSource.getConnection();   //팩토리 메소드 패턴 ??
        preparedStatement = statementStrategy.makeStatement(connection);
        resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            user = new User();
            user.setId(resultSet.getInt("id"));
            user.setName(resultSet.getString("name"));
            user.setPassword(resultSet.getString("password"));
        }

        return user;
    }

    public User queryForObject(String sql, Object[] params) throws SQLException {
        StatementStrategy statementStrategy = (connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for(int i = 0 ; i < params.length ; i++) {
                preparedStatement.setObject(i+1, params[i]);
            }
            return preparedStatement;
        });
        return jdbcContextForGet(statementStrategy);
    }

    public int insert(String sql, Object[] params) throws SQLException {
        StatementStrategy statementStrategy =  (connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            for(int i = 0 ; i < params.length ; i++) {
                preparedStatement.setObject(i+1, params[i]);
            }
            return preparedStatement;
        });
        return jdbcContextForInsert(statementStrategy);
    }

    public void update(String sql, Object[] params) throws SQLException {
        StatementStrategy statementStrategy = (connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for(int i = 0 ; i < params.length ; i++) {
                preparedStatement.setObject(i+1, params[i]);
            }
            return preparedStatement;
        });
        jdbcContext(statementStrategy);

    }
}