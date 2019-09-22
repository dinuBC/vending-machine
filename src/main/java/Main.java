
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
	static Connection connection;

	public static void mainForJDBC(String[] args) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Congrats - Seems your MySQL JDBC Driver Registered!");
		} catch (ClassNotFoundException e) {
			System.out.println(
					"Sorry, couldn't found JDBC driver. Make sure you have added JDBC Maven Dependency Correctly");
			e.printStackTrace();
			return;
		}

		try {
			// DriverManager: The basic service for managing a set of JDBC drivers.
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sda_vending", "root", "1310");
			if (connection != null) {
				System.out.println("Connection Successful! Enjoy. Now it's time to push data");
				insertProduct("Joe", 2.4);
			} else {
				System.out.println("Failed to make connection!");
			}
		} catch (SQLException e) {
			System.out.println("MySQL Connection Failed!");
			e.printStackTrace();
			return;
		} finally {
			try {
				connection.close();
				System.out.println("Connection closed successfully");
			} catch (SQLException e) {
				System.out.println("Could not close the connection");
				e.printStackTrace();
			}
		}
	}

	public static void insertProduct(String name, double price) {

		try {
			String insertQueryStatement = "INSERT  INTO  Product  VALUES  (?,?,?)";
			PreparedStatement prepareStat;
			prepareStat = connection.prepareStatement(insertQueryStatement);
			prepareStat.setString(1, null);
			prepareStat.setString(2, name);
			prepareStat.setDouble(3, price);

			// execute insert SQL statement
			prepareStat.executeUpdate();
			System.out.println(name + " added successfully");
		} catch (

		SQLException e) {
			e.printStackTrace();
		}
	}
}
