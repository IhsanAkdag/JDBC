import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcUtils {
    private static Connection connection;
    private static Statement statement;

    //---------------------------Connection method----------------------------------(1. ve 2. adim)
    public static Connection connectToDataBase(String hostName, String dbName, String username, String password) {

        // 1.adim
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        //2.adim
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://" + hostName + ":5432/" + dbName, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (connection != null) {
            System.out.println("Connection Success");
        } else {
            System.out.println("Connection Fail");
        }
        return connection;
    }

    //---------------------------Statement method----------------------------------(3. adim)
    //3. Adım: Statement oluştur.
    public static Statement createStatement() {

        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return statement;
    }

    //---------------------------Execute method----------------------------------(4. adim)
    //4. adim: query i calistir
    public static boolean execute(String sql) {
        boolean isExecute;
        try {
            isExecute = statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return isExecute;
    }

    //---------------------------Close method----------------------------------(5. adim)
    public static void closeConnectionAndStatement() {
        try {
            connection.close();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            if (connection.isClosed() && statement.isClosed()) {
                System.out.println("connection and statement closed");
            } else System.out.println("connection and statement not closed");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    //---------------------------Create table method----------------------------------
    public static void createTable(String tableName, String... columnName_dataType) {
        StringBuilder columnName_dataValue = new StringBuilder("");

        for (String w : columnName_dataType) {
            columnName_dataValue.append(w).append(",");
        }

        columnName_dataValue.deleteCharAt(columnName_dataValue.length() - 1); //en sondaki virgulu sildik

        try {
            statement.execute("CREATE TABLE " + tableName + "(" + columnName_dataValue + ")");
            System.out.println("Table " + tableName + " successfully created!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
