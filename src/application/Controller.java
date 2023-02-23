package application;

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
				if (cat_Id.length() == 0) {
					cat_Id = null;
				}
				int libraryId = Integer.parseInt(library_Id);
				int categoryId = Integer.parseInt(cat_Id);
				int pages = Integer.parseInt(page);
				int runTimeMinutes = Integer.parseInt(runTime);
				boolean isBorrowable = Boolean.valueOf(borrowable);

				dal.createLibrary(libraryId, categoryId, title, author, pages, runTimeMinutes, isBorrowable, borrower, borrowDate, type);
				txt_Area.setText("You have successfully created the library with the Id " + libraryId);
			} catch (NumberFormatException ex) {
				txt_Area.setText("Salary must be a number");
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
		String cat = txt_categoryId.getText();
		String title = txt_title.getText();
		String author = txt_author.getText();
		String page = txt_pages.getText();
		String runTime = txt_runTime.getText();
		String borrowable = txt_borrowable.getText();
		String borrower = txt_borrower.getText();
		String borrowDate = txt_date.getText();
		String type = txt_type.getText();
		
		
		if (library.length() == 4) {
			try {
				if (cat.length() == 0) {
					cat = null;
				}
				int libraryId = Integer.parseInt(library);
				int categoryId = Integer.parseInt(cat);
				int pages = Integer.parseInt(page);
				int runTimeMinutes = Integer.parseInt(runTime);
				boolean isBorrowable = Boolean.valueOf(borrowable);
				
				dal.updateLibraryItem(libraryId, categoryId, title, author, pages, runTimeMinutes, isBorrowable, borrower, borrowDate, type);
		
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
			dal.findCategory(categoryId);
			ResultSet result = dal.findCategoryInLibrary(categoryId); 

			if (result.next() == true) { 
				dal.deleteCategory(categoryId);
				txt_Area.setText("Category with Id: '"+result.getString("cat")+"'has been removed");

			} else 
				dal.deleteCategory(categoryId);
			txt_Area.setText("Category cant be removed");
				txt_Area.setText("Category with Id: '"+result.getString("cat")+"'has been removed");
		
		} catch (SQLException ex) {
			txt_Area.setText(("Error code: " + ex.getErrorCode() + " Message from SQL-server: " + ex.getMessage()));
		}		
	}

	public void btnDeleteLibrary_Click(ActionEvent event) {
		String library_ID = txt_libraryId.getText();
		
		try {
			int libraryId = Integer.parseInt(library_ID);
			dal.findLibrary(libraryId);
			ResultSet result = dal.findLibrary(libraryId); 

			if (result.next() == false) { 
				txt_Area.setText("Library not found");
			} else 
				dal.deleteLibrary(libraryId);
				txt_Area.setText("Library with Id: '"+result.getString("library_ID")+"'has been removed");
		
		} catch (SQLException ex) {
			txt_Area.setText(("Error code: " + ex.getErrorCode() + " Message from SQL-server: " + ex.getMessage()));
		}	
	}
	

}
