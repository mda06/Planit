package com.mda.planit.view.dialog;

import com.mda.planit.model.Project;
import com.mda.planit.model.Sprint;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SprintEditDialogController {
	@FXML
	private TextField txtName;
	@FXML
	private DatePicker startDatePicker;
	@FXML
	private DatePicker endDatePicker;
	
	private Stage dialogStage;
	private Project project;
	private Sprint sprint;
	private boolean okClicked = false;
	
	@FXML
	private void initialize() {}
	
	public void setDialogStage(Stage stage) {
		dialogStage = stage;
	}
	
	public void setSprint(Sprint sp, Project p) {
		sprint = sp;
		project = p;
		
		txtName.setText(sprint.getName());
		startDatePicker.setValue(sprint.getStartDate());
		endDatePicker.setValue(sprint.getEndDate());
	}
	
	public boolean isOkClicked() {
		return okClicked;
	}
	
	@FXML
	private void handleOk() {
		if(isInputValid()) {
			sprint.setName(txtName.getText());
			sprint.setStartDate(startDatePicker.getValue());
			sprint.setEndDate(endDatePicker.getValue());
			
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
		boolean error = false;
		String content = "";
		if(txtName.getText() == null || txtName.getText().isEmpty()) {
			content += "Please enter a name\n";
			error = true;
		}
		if(startDatePicker.getValue() == null || endDatePicker.getValue() == null) {
			content += "Please enter the dates\n";
			error = true;
		}
		if(startDatePicker.getValue().isAfter(endDatePicker.getValue())) {
			content += "Start date must be before end date\n";
			error = true;
		}
		if(startDatePicker.getValue().isAfter(project.getStartDate())) {
			content += "Sprint start date must be after project start date\n";
			error = true;
		}
		if(endDatePicker.getValue().isAfter(project.getEndDate())) {
			content += "Sprint end date must be before project end date\n";
			error = true;
		}
		
		if(error) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("Invalid fields");
			alert.setHeaderText("Please fill in the fields");
			alert.setContentText(content);
			
			alert.showAndWait();
			return false;
		}
		return true;
	}
}
