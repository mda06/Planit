package com.mda.planit.view.dialog;

import com.mda.planit.model.Sprint;
import com.mda.planit.model.Task;
import com.mda.planit.model.TaskState;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TaskEditDialogController {
	@FXML
	private TextField txtName;
	@FXML
	private TextField txtDesc;
	@FXML
	private DatePicker startDatePicker;
	@FXML
	private DatePicker endDatePicker;
	@FXML
	private ComboBox<TaskState> comboState;
	
	private Stage dialogStage;
	private Sprint sprint;
	private Task task;
	private boolean okClicked = false;
	
	@FXML
	private void initialize() {
		comboState.setItems(FXCollections.observableArrayList(TaskState.values()));
		comboState.getSelectionModel().select(TaskState.TODO);
	}
	
	public void setDialogStage(Stage stage) {
		dialogStage = stage;
	}
	
	public void setTask(Task t, Sprint s) {
		task = t;
		sprint = s;
		
		txtName.setText(t.getName());
		txtDesc.setText(t.getDesc());
		startDatePicker.setValue(t.getStartDate());
		endDatePicker.setValue(t.getEndDate());
		comboState.getSelectionModel().select(t.getState());
	}
	
	public boolean isOkClicked() {
		return okClicked;
	}
	
	@FXML
	private void handleOk() {
		if(isInputValid()) {
			task.setName(txtName.getText());
			task.setDesc(txtDesc.getText());
			task.setStartDate(startDatePicker.getValue());
			task.setEndDate(endDatePicker.getValue());
			task.setTaskState(comboState.getValue());
			
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
		if(txtDesc.getText() == null || txtDesc.getText().isEmpty()) {
			content += "Please enter a description\n";
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
		if(startDatePicker.getValue().isAfter(sprint.getStartDate())) {
			content += "Task start date must be after sprint start date\n";
			error = true;
		}
		if(endDatePicker.getValue().isAfter(sprint.getEndDate())) {
			content += "Task end date must be before sprint end date\n";
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
