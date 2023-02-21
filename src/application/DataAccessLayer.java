package application;

	import java.sql.Connection;

	import java.sql.DriverManager;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;

	public class DataAccessLayer {
		
			String connectionURL = "jdbc:sqlserver://localhost:1433;database=Foretagdb;user=devretake;password=sara1234;encrypt=true;trustServerCertificate=true;";

		//Register

		public void registerEmployee(String employeeID, String employeeName, int employeeSalary, String departmentName) throws SQLException {

				String query = "INSERT INTO Employee VALUES(?, ?, ?, ?)";

				Connection connection = DriverManager.getConnection(connectionURL);

				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, employeeID);
				preparedStatement.setString(2, employeeName);
				preparedStatement.setInt(3, employeeSalary);
				preparedStatement.setString(4, departmentName);

				preparedStatement.executeUpdate();
				preparedStatement.close();
				connection.close();
				
			}
		
		public void registerDepartment(String departmentName, int departmentBudget) throws SQLException {

			Connection connection = DriverManager.getConnection(connectionURL);
			String query = "INSERT INTO Departments VALUES(?, ?)";

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, departmentName);
			preparedStatement.setInt(2, departmentBudget);

			preparedStatement.executeUpdate();
			preparedStatement.close();
			connection.close();
			
		}
		
		public void registerEmployeeOnDepartment(String departmentName, String employeeID) throws SQLException {

			Connection connection = DriverManager.getConnection(connectionURL);
			String query = "UPDATE Employee SET departmentName = ? WHERE employeeID = ? ";

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, departmentName);
			preparedStatement.setString(2, employeeID);

			preparedStatement.executeUpdate();
			preparedStatement.close();
			connection.close();
			
		}
		
		//Find
		
		public ResultSet findEmployeeByEmployeeId(String employeeID) throws SQLException {
			String query = "SELECT  employeeID, employeeName, employeeSalary, departmentName FROM Employee WHERE employeeID = ?";
			
			Connection connection = DriverManager.getConnection(connectionURL);
			
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, employeeID);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			return resultSet;
			
		}
		
		public ResultSet findEmployee(String employeeID) throws SQLException {
			String query = "SELECT  employeeID FROM Employee WHERE employeeID = ?";
			
			Connection connection = DriverManager.getConnection(connectionURL);
			
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, employeeID);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			return resultSet;
			
		}
		
		public ResultSet findDepartmentByDepartmentName(String departmentName) throws SQLException {
			String query = "SELECT departmentName, departmentBudget FROM Departments WHERE departmentName = ?";
			
			Connection connection = DriverManager.getConnection(connectionURL);
			
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, departmentName);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			return resultSet;
			
		}
		
		//Show All
		public ResultSet showAllEmployeeOnDepartment(String departmentName) throws SQLException {
			String query = "SELECT employeeID, employeeName FROM Employee WHERE departmentName = ?";
			
			Connection connection = DriverManager.getConnection(connectionURL);
			
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, departmentName);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			return resultSet;
			
		}

	}

