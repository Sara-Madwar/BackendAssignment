package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javafx.scene.control.ListView;

public class DataAccessLayer {
	
	String connectionURL = "jdbc:sqlserver://localhost:1433;database=Backenddb;user=considUppgift;password=consid2023;encrypt=true;trustServerCertificate=true;";

	//Create
		public void createLibrary(int libraryId, int categoryId, String title, String author, int pages, int runTimeMinutes, boolean isBorrowable, String borrower, String borrowDate, String type) throws SQLException {

				String query = "INSERT INTO LibraryItem VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

				Connection connection = DriverManager.getConnection(connectionURL);

				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, libraryId);
				preparedStatement.setInt(2, categoryId);
				preparedStatement.setString(3, title);
				preparedStatement.setString(4, author);
				preparedStatement.setInt(5, pages);
				preparedStatement.setInt(6, runTimeMinutes);
				preparedStatement.setBoolean(7, isBorrowable);
				preparedStatement.setString(8, borrower);
				preparedStatement.setString(9, borrowDate);
				preparedStatement.setString(10, type);

				preparedStatement.executeUpdate();
				preparedStatement.close();
				connection.close();
				
			}
		
		public void createCategory(int categoryId, String categoryName) throws SQLException {
			Connection connection = DriverManager.getConnection(connectionURL);
			String query = "INSERT INTO Category VALUES (?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, categoryId);
			preparedStatement.setString(2, categoryName);

			preparedStatement.executeUpdate();
			preparedStatement.close();
			connection.close();

		}
		
		public void createEmployee(int employeeId, String firstName, String lastName, double salary, boolean isCEO, boolean isManager, int managerId) throws SQLException {
			Connection connection = DriverManager.getConnection(connectionURL);
			String query = "INSERT INTO Employee VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, employeeId);
			preparedStatement.setString(2, firstName);
			preparedStatement.setString(3, lastName);
			preparedStatement.setDouble(4, salary);
			preparedStatement.setBoolean(5, isCEO);
			preparedStatement.setBoolean(6, isManager);
			preparedStatement.setInt(7, managerId);

			preparedStatement.executeUpdate();
			preparedStatement.close();
			connection.close();
		}
		
		//Update
		public void updateCategory(int categoryId, String categoryName) throws SQLException {

			Connection connection = DriverManager.getConnection(connectionURL);
			String query = "UPDATE Category SET categoryName = ? WHERE categoryID = ?";

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, categoryId);
			preparedStatement.setString(2, categoryName);

			preparedStatement.executeUpdate();
			preparedStatement.close();
			connection.close();
			
		}
		public void updateLibraryItem(int libraryId, boolean isBorrowable, String borrower, String borrowDate) throws SQLException {

			Connection connection = DriverManager.getConnection(connectionURL);
			String query = "UPDATE LibraryItem SET isBorrowable=?, borrower=?, borrowDate=? WHERE libraryId = ?";

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, libraryId);
			preparedStatement.setBoolean(2, isBorrowable);
			preparedStatement.setString(3, borrower);
			preparedStatement.setString(4, borrowDate);

			preparedStatement.executeUpdate();
			preparedStatement.close();
			connection.close();
		}
		
		public void updateEmployee(int employeeId, boolean isCEO, boolean isManager, int managerId) throws SQLException {

			Connection connection = DriverManager.getConnection(connectionURL);
			String query = "UPDATE Employee SET isCEO=?, isManager=?, managerId=? WHERE employeeId = ?";

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, employeeId);
			preparedStatement.setBoolean(2, isCEO);
			preparedStatement.setBoolean(3, isManager);
			preparedStatement.setInt(4, managerId);

			preparedStatement.executeUpdate();
			preparedStatement.close();
			connection.close();
		}
			//Delete
			
			public void deleteCategory(int categoryId) throws SQLException {

			Connection connection = DriverManager.getConnection(connectionURL);
			String query = "DELETE FROM Category WHERE categoryId = ?";

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, categoryId);

			preparedStatement.executeUpdate();
			preparedStatement.close();
			connection.close();
		}
			
			public void deleteLibrary(int libraryId) throws SQLException {

				Connection connection = DriverManager.getConnection(connectionURL);
				String query = "DELETE FROM LibraryItem WHERE libraryId = ?";

				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, libraryId);

				preparedStatement.executeUpdate();
				preparedStatement.close();
				connection.close();
			}
			
			public void deleteEmployee(int employeeId) throws SQLException {

				Connection connection = DriverManager.getConnection(connectionURL);
				String query = "DELETE FROM Employee WHERE employeeId = ?";

				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, employeeId);

				preparedStatement.executeUpdate();
				preparedStatement.close();
				connection.close();
			}
			
			//find
			public ResultSet findLibrary(int libraryId) throws SQLException {

				Connection connection = DriverManager.getConnection(connectionURL);
				String query = "SELECT categoryId, title, author, pages, runTimeMinutes, isBorrowable, borrower, borrowDate, type FROM LibraryItem WHERE libraryId = ?";
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, libraryId);

				preparedStatement.executeUpdate();
				ResultSet resultSet = preparedStatement.executeQuery();
				return resultSet;
			}
		
			public ResultSet findCategory(int categoryId) throws SQLException {

				Connection connection = DriverManager.getConnection(connectionURL);
				String query = "SELECT categoryName FROM Category WHERE categoryId = ?";
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, categoryId);
				
				ResultSet resultSet = preparedStatement.executeQuery();
				return resultSet;
			}
			
			public ResultSet findCategoryInLibrary(int categoryId) throws SQLException {

				Connection connection = DriverManager.getConnection(connectionURL);
				String query = "SELECT categoryId FROM LibraryItem WHERE categoryId = ?";
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, categoryId);
				
				ResultSet resultSet = preparedStatement.executeQuery();
				return resultSet;
			}
			
			public ResultSet findEmployee(int employeeId) throws SQLException {

				Connection connection = DriverManager.getConnection(connectionURL);
				String query = "SELECT salary, isCEO, isManager FROM Employee WHERE employeeId = ?";
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, employeeId);
				
				ResultSet resultSet = preparedStatement.executeQuery();
				return resultSet;
			}
			
			public ResultSet findSalary(int employeeId) throws SQLException {

				Connection connection = DriverManager.getConnection(connectionURL);
				String query = "SELECT salary, CEO FROM Employee WHERE employeeId = ?";
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, employeeId);
				
				ResultSet resultSet = preparedStatement.executeQuery();
				return resultSet;
			}
			
			//Show List
			
			public ResultSet showLibraryList() throws SQLException {
				Connection connection = DriverManager.getConnection(connectionURL);
			String query = "SELECT *\r\n"
					+ "FROM LibraryItem\r\n"
					+ "LEFT OUTER JOIN Category \r\n"
					+ "ON LibraryItem.categoryId = Category.categoryId\r\n"
					+ "ORDER BY CategoryName";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			return resultSet;
			}
			
			public ResultSet sortByType() throws SQLException {
			Connection connection = DriverManager.getConnection(connectionURL);
			String query = "SELECT * FROM LibraryItem ORDER BY type";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			return resultSet;
			}
			
			/*public ListView<String> showList(){
				ListView<String> list = new ListView<String>();

				Connection connection = DriverManager.getConnection(connectionURL);
				String query = "SELECT * FROM LibraryItem ORDER BY type";
				PreparedStatement preparedStatement = connection.prepareStatement(query);

				List<String> resultSet = (List<String>) preparedStatement.executeQuery();
				return list.resultSet;

			}*/
}
