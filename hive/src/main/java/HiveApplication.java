import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;

public class HiveApplication {

  private static String driverName = "org.apache.hive.jdbc.HiveDriver";
  private static final String HIVE_URL = "jdbc:hive2://localhost:10000/default";
  private static final String HIVE_USER = "hive";
  private static final String HIVE_PWD = "hive";

  public static void main(String[] args)
          throws SQLException, ClassNotFoundException {

    Class.forName(driverName);
    Connection con = DriverManager.getConnection(HIVE_URL, HIVE_USER, HIVE_PWD);
    Statement stmt = con.createStatement();

    stmt.execute("CREATE DATABASE IF NOT EXISTS testdb");
    System.out.println("Database testdb created successfully.");

    stmt.execute("CREATE TABLE IF NOT EXISTS " +
            "testTable (YEAR int, CATEGORY String, ATTENDANCE int) " +
            "COMMENT 'hospital attendance'" +
            "ROW FORMAT DELIMITED " +
            "FIELDS TERMINATED BY ',' " +
            "LINES TERMINATED BY '\n' " +
            "STORED AS TEXTFILE");
    System.out.println("Table testTable created successfully.");

    stmt.execute("LOAD DATA LOCAL INPATH '" + args[0] + "' " +
            "OVERWRITE INTO TABLE testTable");
    System.out.println("Load data from " + args[0] + " into testTable successfully.");

    ResultSet resultSet = stmt.executeQuery("SELECT CATEGORY, SUM(ATTENDANCE) FROM testTable GROUP BY CATEGORY");

    while(resultSet.next()) {
      System.out.println(resultSet.getString(1) + ", " + resultSet.getInt(2));
    }

    con.close();
  }
}