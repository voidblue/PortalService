import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class GetUserStatementStrategy implements StatementStrategy {
    private int id;
    public GetUserStatementStrategy(int id) {
        this.id = id;
    }

    @Override
    public PreparedStatement makeStatement(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from user where id =?");
        preparedStatement.setInt(1, id);
        return preparedStatement;
    }
}
