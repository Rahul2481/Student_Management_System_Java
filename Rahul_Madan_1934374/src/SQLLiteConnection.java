import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class SQLLiteConnection {
	
	public static Connection dbConnector() {

		try {

			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:Studentdb.db");
			JOptionPane.showMessageDialog(null, "Connection Successfully Established ...");
			return conn;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
			return null;
		}

 }
}
