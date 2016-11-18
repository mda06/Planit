package com.mda.planit.view.dialog;

import com.mda.planit.model.Developer;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DeveloperEditDialogController {
	@FXML
	private TextField txtName;
	@FXML
	private ColorPicker colorPicker;
	
	private Stage dialogStage;
	private Developer dev;
	private boolean okClicked = false;
	
	@FXML
	private void initialize() {}
	
	public void setDialogStage(Stage stage) {
		dialogStage = stage;
	}
	
	public void setDeveloper(Developer d) {
		dev = d;
		
		txtName.setText(d.getName());
		colorPicker.setValue(d.getColor());
	}
	
	public boolean isOkClicked() {
		return okClicked;
	}
	
	@FXML
	private void handleOk() {
		if(isInputValid()) {
			dev.setName(txtName.getText());
			dev.setColor(colorPicker.getValue());
			
			okClicked = true;
			dialogStage.close();
		}
	}
	
	@FXML
	private void handleCancel() {
		okClicked = false;
		dialogStage.close();
	}
	
	private boolean isInputValid() {
		if(txtName.getText() == null || txtName.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("Invalid fields");
			alert.setHeaderText("Please enter a name");
			alert.setContentText("Name is empty");
			
			alert.showAndWait();
			return false;
		}
		return true;
	}
}
