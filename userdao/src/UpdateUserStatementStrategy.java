import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateUserStatementStrategy implements StatementStrategy {
    private User user;
    public UpdateUserStatementStrategy(User user){
        this.user = user;
    }
    @Override
    public PreparedStatement makeStatement(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE user SET name = ?, password = ?  WHERE id = ?");
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setInt(3, user.getId());

        return preparedStatement;
    }
}
