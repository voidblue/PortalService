import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteUserStatementStrategy implements StatementStrategy {
    private int id;
    public DeleteUserStatementStrategy(int id) {
        this.id = id;
    }


    @Override
    public PreparedStatement makeStatement(Connection connection) throws SQLException {
        PreparedStatement preparedStatement =
                connection.prepareStatement("DELETE FROM user WHERE id = ?");
        preparedStatement.setInt(1, id);
        return preparedStatement;
    }
}
