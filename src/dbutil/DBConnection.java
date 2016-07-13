package dbutil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

	private static final String DB_CONFIG_FILE ="dbconfig.properties";
	private static final String DB_CONFIG_FILE1 ="dbutil/DBConnection.java";
	private static final String DB_DRIVER = "driver";
	private static final String DB_URL = "url";
	private static final String DB_USERNAME = "username";
	private static final String DB_PASSWORD = "password";
	
	public DBConnection() {
    }

    public static Connection getDBConnection() throws IOException,
        SQLException, ClassNotFoundException {
        Connection dbConn = null;
        Properties props = new Properties();
        props.load(DBConnection.class.getClassLoader().getResourceAsStream(DB_CONFIG_FILE));
        String dbDriver = props.getProperty(DB_DRIVER);
        String dbURL = props.getProperty(DB_URL);
        String dbUserName = props.getProperty(DB_USERNAME);
        String dbPassword = props.getProperty(DB_PASSWORD);
        Class.forName(dbDriver);
        dbConn = DriverManager.getConnection(dbURL, dbUserName, dbPassword);
        return dbConn;
    }


}
