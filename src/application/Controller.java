package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Controller {
	
	DataAccessLayer dal = new DataAccessLayer();
	
	@FXML
	private TextField txt_CatId;
	@FXML
	private TextField txt_CatName;
	@FXML
	private TextField libraryId;
	@FXML
	private TextField categoryId;
	@FXML
	private TextField title;
	@FXML
	private TextField author;
	@FXML
	private TextField pages;
	@FXML
	private TextField runTime;
	@FXML
	private TextField borrowable;
	@FXML
	private TextField borrower;
	@FXML
	private TextField date;
	@FXML
	private TextField type;
	
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
	public void btnAddLibrary_Click(ActionEvent event) {
		String libraryId = libraryId.getText();
		Integer categoryId = categoryId.getValue();
		String tilte = title.getText();
		String author = author.getText();
	}
	
	public void btnAddCategory_Click(ActionEvent event) {
		String categoryId = txt_CatId.getText();
		String categoryName = txt_CatName.getText();
	}

}
