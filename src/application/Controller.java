package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
	private TextField txt_categoryId;
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
	private TextArea txt_Area;

	//CREATE
	@FXML
	public void btnAddLibrary_Click(ActionEvent event) {
		String library_Id = txt_libraryId.getText();
		String cat_Id = txt_categoryId.getText();
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

	//UPDATE
	public void btnUpdateCategory_Click(ActionEvent event) {
		String category_Id = txt_CatId.getText();
		String categoryName = txt_CatName.getText();
		
		try {
			int categoryId = Integer.parseInt(category_Id);
			dal.updateCategory(categoryId, categoryName);
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
				
				if (isBorrowable == false) {
					dal.updateLibraryItem(libraryId, isBorrowable, borrower, borrowDate);

				}else {
					txt_Area.setText("The item is already borrwed by" + borrower + "From" + borrowDate);
				}
				
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
	
	@FXML
	public void btnShowList_Click(ActionEvent event) {
		String categoryId = txt_CatId.getText();
		if (categoryId.length() == 4) {
			
			try {
				int category_Id = Integer.parseInt(categoryId);
				dal.showLibraryList(category_Id);
				ResultSet result = dal.showLibraryList(category_Id);

				boolean anyResults = false; 
				txt_Area.setText("All Library Items which are ordered by category Name"); 
				while(result.next()) {
					anyResults= true;
					String studentID = result.getString("studentID");
					String grade = result.getString("courseGrade");
					txt_Area.appendText("\n"+studentID+": "+grade); 
				}
				if (!anyResults) {
					txt_Area.setText("No category found for this ID");
				}

			}
			catch (SQLException ex) {
				txt_Area.setText("We are very sorry to inform you that something went wrong, please call +46 98 018 37 34 and we are happy to solve the problem. \nError code: " + ex.getErrorCode()+"Message from SQL-server: " + ex.getMessage());
			}
		}
		if (categoryId.length() == 0) {
			txt_Area.setText("Category Id is empty! \nPlease enter a valid category Id");
			
		}
		else if(categoryId.length()!=4) {
			txt_Area.setText("Please enter a valid Categor Id  and make sure it's formated as: 'xxxx', example: 1234");
		}
	}
	

}
