import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;

@AllArgsConstructor
public class UserDao {
    //이 코드 떄문에 한라 코드를 어떻게 해야할지 ?? dependancy 생김
    //private final ConnectionMaker connectionMaer = new JejuConnectionMaker();

    private JdbcTemplate jdbcTemplate = null;
//    private JdbcContext jdbcContext = null;

    //변경이 되지 않은 부분을 Context <-- 변하지 않은 부분을 묶어놓은 부분, 및 그 환경

    //아래 get함수는 Context라고 할 수 있다.
    public User get(int id) throws SQLException {
//        StatementStrategy statementStrategy = new GetUserStatementStrategy(id);
        String sql = "SELECT * FROM user WHERE id = ?";
        Object[] params = {id};
        try {
            return jdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                return user;
            });
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public int insert(User user) throws SQLException {
        String sql = "INSERT INTO user (name, password) VALUES (?, ?)";
        Object[] params = {user.getName(), user.getPassword()};
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            for (int i = 0 ; i < params.length; i++){
                preparedStatement.setObject(i+1, params[i]);
            }
            return preparedStatement;

        }, keyHolder);
        return keyHolder.getKey().intValue();
    }
    public void update(User user) throws SQLException {
        String sql = "UPDATE user SET name = ?, password = ?  WHERE id = ?";
        Object[] params = {user.getName(), user.getPassword(), user.getId()};
        jdbcTemplate.update(sql, params);
    }
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM user WHERE id = ?";
        Object[] params = {id};
        jdbcTemplate.update(sql, params);
    }
}


