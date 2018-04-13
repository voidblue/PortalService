import javax.sql.DataSource;
import java.sql.*;

public class UserDao {
    //이 코드 떄문에 한라 코드를 어떻게 해야할지 ?? dependancy 생김
    //private final ConnectionMaker connectionMaer = new JejuConnectionMaker();

    //Dependancy Injection, connectionMaker으로 생기는 의존성을 클라이언트 한테 넘김(생성자를 통해서)
    //클라이언트(test에서) DaoFactory에게 다시 넘김
    //스프링은 이 DaoFactory와 같은 역할을 해줌

    private JdbcContext jdbcContext = null;

    public UserDao(JdbcContext jdbcContext) {
        this.jdbcContext = jdbcContext;
    }
    //변경이 되지 않은 부분을 Context <-- 변하지 않은 부분을 묶어놓은 부분, 및 그 환경

    //아래 get함수는 Context라고 할 수 있다.
    public User get(int id) throws SQLException {
        StatementStrategy statementStrategy = new GetUserStatementStrategy(id);
        return jdbcContext.jdbcContextForGet(statementStrategy);
    }
    public int insert(User user) throws SQLException {
        StatementStrategy statementStrategy = new InsertUserStatementStrategy(user);

        return jdbcContext.jdbcContextForInsert(statementStrategy);
    }
    public void update(User user) throws SQLException {
        StatementStrategy statementStrategy = new UpdateUserStatementStrategy(user);
        jdbcContext.jdbcContext(statementStrategy);
    }
    public void delete(int id) throws SQLException {
        StatementStrategy statementStrategy = new DeleteUserStatementStrategy(id);
        jdbcContext.jdbcContext(statementStrategy);
    }
}


