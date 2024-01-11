import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class ConfigDataBase {
    private final Logger logger = Logger.getLogger(ConfigDataBase.class.getName());

    final String dataBaseAddress = "jdbc:mysql://localhost:3306/karino";
    final String dataBaseUsername = "root";
    final String dataBasePassword = "Java1234";
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }
    public ConfigDataBase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            logger.info("error database");
            throw new RuntimeException(e);
        }
    }
    public void connect() {
        try {
            this.connection = DriverManager.getConnection(dataBaseAddress, dataBaseUsername, dataBasePassword);
        } catch (SQLException e) {
            logger.info("error connect");
            throw new RuntimeException(e);
        }
    }
    public void closeConnection() {
        try {
            this.connection.close();
            logger.info("DB closed");
        } catch (SQLException e) {
            logger.info("Fail closed");
            throw new RuntimeException(e);
        }
    }
}
