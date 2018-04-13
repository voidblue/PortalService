import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertUserStatementStrategy implements StatementStrategy {
    private User user;
    public InsertUserStatementStrategy(User user) {
        this.user = user;
    }

    @Override
    public PreparedStatement makeStatement(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user (name, password) VALUES (?, ?)");
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getPassword());
        return preparedStatement;
    }
}
