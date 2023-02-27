package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {
	
	DataAccessLayer dal = new DataAccessLayer();
	
	@FXML
	private TextField txt_CatId;
	@FXML
	private TextField txt_CatName;
	@FXML
	private TextField txt_libraryId;
	@FXML
	private TextField txt_CategoryId;
	@FXML
	private TextField txt_title;
	@FXML
	private TextField txt_author;
	@FXML
	private TextField txt_pages;
	@FXML
	private TextField txt_runTime;
	@FXML
	private TextField txt_borrowable;
	@FXML
	private TextField txt_borrower;
	@FXML
	private TextField txt_date;
	@FXML
	private TextField txt_type;
	@FXML
	private TextField employeeId;
	@FXML
	private TextField firstName;
	@FXML
	private TextField lastName;
	@FXML
	private TextField salary;
	@FXML
	private TextField isCEO;
	@FXML
	private TextField isManager;
	@FXML
	private TextField managerId;
	
	@FXML
	private Button createCat;
	@FXML
	private Button createLib;
	@FXML
	private Button deleteCat;
	@FXML
	private Button deleteLib;
	@FXML
	private Button updateLib;
	@FXML
	private Button updateCat;
	@FXML
	private Button showList;
	@FXML
	private Button sortType;
	@FXML
	private Button btn_create;
	@FXML
	private Button btn_Update;
	@FXML
	private Button btn_Delete;
	@FXML
	private Button btn_salary;
	
	@FXML
	private TextArea txt_Area;

	//CREATE
	@FXML
	public void btnAddLibrary_Click(ActionEvent event) {
		String library_Id = txt_libraryId.getText();
		String cat_Id = txt_CategoryId.getText();
		String title = txt_title.getText();
		String author = txt_author.getText();
		String page = txt_pages.getText();
		String runTime = txt_runTime.getText();
		String borrowable = txt_borrowable.getText();
		String borrower = txt_borrower.getText();
		String borrowDate = txt_date.getText();
		String type = txt_type.getText();
		
		if (library_Id.length() == 4) {
			try {
				int libraryId = Integer.parseInt(library_Id);
				int categoryId = Integer.parseInt(cat_Id);
				int pages = Integer.parseInt(page);
				int runTimeMinutes = Integer.parseInt(runTime);
				boolean isBorrowable = Boolean.valueOf(borrowable);

				dal.createLibrary(libraryId, categoryId, title, author, pages, runTimeMinutes, isBorrowable, borrower, borrowDate, type);
				txt_Area.setText("You have successfully created the library with the Id " + libraryId);
			} catch (SQLException ex) {
				if (ex.getErrorCode() == 2627) {
					txt_Area.setText("This ID already exists, please enter another one");
				} else if (ex.getErrorCode() == 0) {
					txt_Area.setText("There is no connection to the server.");
				} else {
					txt_Area.setText("Something went wrong.");
				}
			}

		} else if (library_Id.length() != 4) {
			txt_Area.setText("Library Id should have four numbers");
		}
	}
	
	@FXML
	public void btnAddCategory_Click(ActionEvent event) {
		String category_Id = txt_CatId.getText();
		String categoryName = txt_CatName.getText();
		
		if (category_Id.length() == 4) {
			try {
				int categoryId = Integer.parseInt(category_Id);
				
				dal.createCategory(categoryId, categoryName);
				txt_Area.setText("You have successfully created the Category with the Id " + categoryId);
			} catch (SQLException ex) {
				if (ex.getErrorCode() == 2627) {
					txt_Area.setText("This ID already exists, please enter another one");
				} else if (ex.getErrorCode() == 0) {
					txt_Area.setText("There is no connection to the server.");
				} else {
					txt_Area.setText("Something went wrong.");
				}
			}

		} else if (category_Id.length() != 4) {
			txt_Area.setText("Library Id should have four numbers");
		}
	}
	
	@FXML
	public void btnAddEmployee_Click(ActionEvent event) {
		String employee_Id = employeeId.getText();
		String emp_firstName = firstName.getText();
		String emp_lastName = lastName.getText();
		String empSalary = salary.getText();
		String isCeo = isCEO.getText();
		String isManag = isManager.getText();
		String manager_Id = managerId.getText();
		if (employee_Id.length() == 4) {
			try {
				int employeeId = Integer.parseInt(employee_Id);
				double salary = Double.valueOf(empSalary);
				boolean isCEO = Boolean.valueOf(isCeo);
				boolean isManager = Boolean.valueOf(isManag);
				int managerId = Integer.parseInt(manager_Id);
				
				dal.createEmployee(employeeId, emp_firstName, emp_lastName, salary, isCEO, isManager, managerId);
				if(isCEO == true) {
					txt_Area.setText("You can not add this employee as a CEO");
				}else {
					txt_Area.setText("Employee with id " + employee_Id +" has been created");
				}
			} catch (SQLException ex) {
				if (ex.getErrorCode() == 2627) {
					txt_Area.setText("This ID already exists, please enter another one");
				} else if (ex.getErrorCode() == 0) {
					txt_Area.setText("There is no connection to the server.");
				} else {
					txt_Area.setText("Something went wrong.");
				}
			}

		} else if (employee_Id.length() != 4) {
			txt_Area.setText("Library Id should have four numbers");
		}
		
	}
	//UPDATE
	public void btnUpdateCategory_Click(ActionEvent event) {
		String category_Id = txt_CatId.getText();
		String categoryName = txt_CatName.getText();
		
		try {
			int categoryId = Integer.parseInt(category_Id);
			ResultSet result = dal.findCategory(categoryId);

			boolean anyResults = false;
			while (result.next()) {
				anyResults = true;
				dal.updateCategory(categoryId, categoryName);
				txt_Area.setText("Category Updated");
			}
			if (!anyResults) {
				txt_Area.setText("No category found with this Id");
			}
			PreparedStatement preparedStatement = (PreparedStatement) result.getStatement();

			Connection connection = preparedStatement.getConnection();
			connection.close();

			preparedStatement.close();
			result.close();
		} catch (SQLException ex) {
			if (ex.getErrorCode() == 2627) {
				txt_Area.setText("This ID already exists, please enter another one");
			} else if (ex.getErrorCode() == 0) {
				txt_Area.setText("There is no connection to the server.");
			} else {
				txt_Area.setText("Something went wrong.");
			}
		}
	}
	
	public void btnUpdateLibrary_Click(ActionEvent event) {
		String library = txt_libraryId.getText();
		String borrowable = txt_borrowable.getText();
		String borrower = txt_borrower.getText();
		String borrowDate = txt_date.getText();
		
		if (library.length() == 4) {
			try {				
				int libraryId = Integer.parseInt(library);
				boolean isBorrowable = Boolean.valueOf(borrowable);
				ResultSet result = dal.findLibrary(libraryId);

				boolean anyResults = false;
				while (result.next()) {
					anyResults = true;
					dal.updateLibraryItem(libraryId, isBorrowable, borrower, borrowDate);
					txt_Area.setText("Library Item Updated");
				
				if (!anyResults) {
					txt_Area.setText("No library found with this Id");
				}
				else if (isBorrowable == false) {
					dal.updateLibraryItem(libraryId, isBorrowable, borrower, borrowDate);

				}else {
					txt_Area.setText("The item is already borrwed by" + borrower + "From" + borrowDate);
				}
				}
				PreparedStatement preparedStatement = (PreparedStatement) result.getStatement();

				Connection connection = preparedStatement.getConnection();
				connection.close();

				preparedStatement.close();
				result.close();
				
		} catch (SQLException ex) {
			if (ex.getErrorCode() == 2627) {
				txt_Area.setText("This ID already exists, please enter another one");
			} else if (ex.getErrorCode() == 0) {
				txt_Area.setText("There is no connection to the server.");
			} else {
				txt_Area.setText("Something went wrong.");
			}}
			}
	}
	
	public void btnUpdateEmployee_Click(ActionEvent event) {
		String employeeID = employeeId.getText();
		String isCeo = isCEO.getText();
		String isManag = isManager.getText();
		String manag_Id = managerId.getText();
		
		if (employeeID.length() == 4) {
			try {				
				int employeeId = Integer.parseInt(employeeID);
				boolean isCEO = Boolean.valueOf(isCeo);
				boolean isManager = Boolean.valueOf(isManag);
				int managerId = Integer.parseInt(manag_Id);

				ResultSet result = dal.findEmployee(employeeId);

				boolean anyResults = false;
				while (result.next()) {
					anyResults = true;
					dal.updateEmployee(employeeId, isCEO, isManager, managerId);
					txt_Area.setText("Employee Updated");
				}
				if (!anyResults) {
					txt_Area.setText("No employee found with this Id");
				}

				if(isCEO == true) {
					txt_Area.setText("You can not manage a CEO employee");
				}else if (isManager == true) {
					txt_Area.setText("add a manager to this employee" + employeeID);
				}else {
					txt_Area.setText("employee has been updated");
				}
				PreparedStatement preparedStatement = (PreparedStatement) result.getStatement();

				Connection connection = preparedStatement.getConnection();
				connection.close();

				preparedStatement.close();
				result.close();
				
		} catch (SQLException ex) {
			if (ex.getErrorCode() == 2627) {
				txt_Area.setText("This ID already exists, please enter another one");
			} else if (ex.getErrorCode() == 0) {
				txt_Area.setText("There is no connection to the server.");
			} else {
				txt_Area.setText("Something went wrong.");
			}}
			}
	}
	
	//DELETE
	public void btnDeleteCategory_Click(ActionEvent event) {
		String cat = txt_CatId.getText();
		try {
			int categoryId = Integer.parseInt(cat);
			ResultSet result = dal.findCategoryInLibrary(categoryId); 
			boolean anyResult = false;

			while (result.next() ) {
				anyResult = true;
				txt_Area.setText("You can not delete this category because it is referenced to a library Item");
				
			}if (!anyResult) {
				dal.deleteCategory(categoryId);
				txt_Area.setText("This category Id has been removed");
			}

			PreparedStatement preparedStatement = (PreparedStatement) result.getStatement();

			Connection connection = preparedStatement.getConnection();
			connection.close();

			preparedStatement.close();
			result.close();
					
	} catch (SQLException ex) {
			if (ex.getErrorCode() == 0) {
				txt_Area.setText("No connection with server!");

			} else {
				txt_Area.setText("Something went wrong \nError code: " + ex.getErrorCode()
						+ "Message from SQL-server: " + ex.getMessage());
			}		
	}
}	

	public void btnDeleteLibrary_Click(ActionEvent event) {
		String library_ID = txt_libraryId.getText();
		
		try {
			int libraryId = Integer.parseInt(library_ID);
			ResultSet result = dal.findLibrary(libraryId); 
			boolean anyResult = false;

			while (result.next() ) {
				anyResult = true;
				dal.deleteCategory(libraryId);
				txt_Area.setText("This library Id has been removed");
				
			}if (!anyResult) {
				txt_Area.setText("Library not found");
			}

			PreparedStatement preparedStatement = (PreparedStatement) result.getStatement();

			Connection connection = preparedStatement.getConnection();
			connection.close();

			preparedStatement.close();
			result.close();
					
	} catch (SQLException ex) {
			if (ex.getErrorCode() == 0) {
				txt_Area.setText("No connection with server!");

			} else {
				txt_Area.setText("Something went wrong \nError code: " + ex.getErrorCode()
						+ "Message from SQL-server: " + ex.getMessage());
			}		
	}	
	}
	
	public void btnDeleteEmployee_Click(ActionEvent event) {
		String employeeID = employeeId.getText();
		try {
			int employeeId = Integer.parseInt(employeeID);
			ResultSet result = dal.findEmployee(employeeId); 
			boolean anyResult = false;

			while (result.next() ) {
				anyResult = true;
				dal.deleteEmployee(employeeId);
				txt_Area.setText("This employee Id has been removed");
				
			}if (!anyResult) {
				txt_Area.setText("employee Id not found");
			}

			PreparedStatement preparedStatement = (PreparedStatement) result.getStatement();

			Connection connection = preparedStatement.getConnection();
			connection.close();

			preparedStatement.close();
			result.close();
			
		} catch (SQLException ex) {
			if (ex.getErrorCode() == 0) {
				txt_Area.setText("No connection with server!");

			} else {
				txt_Area.setText("Something went wrong \nError code: " + ex.getErrorCode()
						+ "Message from SQL-server: " + ex.getMessage());
			}		
		}
		}	
	
	//Show List
	
	@FXML
	public void btnShowList_Click(ActionEvent event) {		
			try {
				ResultSet result = dal.showLibraryList();
				ObservableList<String> items =FXCollections.observableArrayList ();
				boolean anyResults = false;
				while (result.next()) {
					anyResults= true;
					String categoryId = result.getString("categoryId");
					items.add(categoryId);
				}

				if (!anyResults) {
					txt_Area.setText("No category found for this ID");
				} else {
					ListView<String> lists = new ListView<String>(items);

					lists.setItems(items);
				}
				PreparedStatement preparedStatement = (PreparedStatement) result.getStatement();

				Connection connection = preparedStatement.getConnection();
				connection.close();

				preparedStatement.close();
				result.close();
			}
			
			catch (SQLException ex) {
				txt_Area.setText("Something went wrong");
			}
	}

	public void btnShowListByType_Click(ActionEvent event) {		
		try {
			ResultSet result = dal.sortByType();
			ObservableList<String> item =FXCollections.observableArrayList ();
			boolean anyResults = false;
			while (result.next()) {
				anyResults= true;
				String libraryId = result.getString("libraryId");
				item.add(libraryId);
			}

			if (!anyResults) {
				txt_Area.setText("No library found for this ID");
			} else {
				ListView<String> lists = new ListView<String>(item);

				lists.setItems(item);
			}
			PreparedStatement preparedStatement = (PreparedStatement) result.getStatement();

			Connection connection = preparedStatement.getConnection();
			connection.close();

			preparedStatement.close();
			result.close();
		}
		
		catch (SQLException ex) {
			txt_Area.setText("Something went wrong");
		}
}
	@FXML
	public void btnCalculate_Click(ActionEvent event) {
		String emp_Id = employeeId.getText();
		try {
			int employeeId = Integer.parseInt(emp_Id);

			ResultSet result = dal.findEmployee(employeeId);
			boolean anyResults = false; 
			double cEOSalary = 2.125;
			double managerSalary = 1.125;
			double employee = 0.125;
			boolean manager = result.getBoolean("isManager");
			boolean ceo = result.getBoolean("isCEO");

			while(result.next()) {
				anyResults= true;
				if (ceo == true) {
					double totalSalary = result.getDouble("salary") * cEOSalary;
					txt_Area.setText("total salary for employee with id" + emp_Id + " :" + totalSalary);
				}else if (manager == true) {
					double totalSalary = result.getDouble("salary") * managerSalary;
					txt_Area.setText("total salary for manager with id" + emp_Id + " :" + totalSalary);
				} else  {
					double totalSalary = result.getDouble("salary") * employee;
					txt_Area.setText("total salary for manager with id" + emp_Id + " :" + totalSalary);
				}
				if  (!anyResults){
					txt_Area.setText("No salary found for this Id");
				}}
			PreparedStatement preparedStatement = (PreparedStatement) result.getStatement();

			Connection connection = preparedStatement.getConnection();
			connection.close();

			preparedStatement.close();
			result.close();
			
			} catch (SQLException ex) {
			txt_Area.setText("Something went wrong");
		}
	}
		
}
