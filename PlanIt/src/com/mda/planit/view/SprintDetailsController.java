package com.mda.planit.view;

import com.mda.planit.MainApp;
import com.mda.planit.model.Sprint;
import com.mda.planit.model.SprintGoal;
import com.mda.planit.util.DateUtil;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class SprintDetailsController {
	private MainApp mainApp;
	private Sprint sprint;
	
	@FXML
	private Label lblName;
	@FXML
	private Label lblStartDate;
	@FXML
	private Label lblEndDate;
	
	@FXML
	private TableView<SprintGoal> tableGoals;
	@FXML
	private TableColumn<SprintGoal, String> columnGoalName;
	@FXML
	private TableColumn<SprintGoal, String> columnGoalDesc;
	@FXML
	private TableColumn<SprintGoal, Boolean> columnGoalAccomplish;
	
	public SprintDetailsController() {}
	
	@FXML
	private void initialize() {
		columnGoalName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		columnGoalDesc.setCellValueFactory(cellData -> cellData.getValue().descProperty());
		columnGoalAccomplish.setCellValueFactory(cellData -> cellData.getValue().accomplishProperty());
		
		showSprint(null);
	}
	
	@FXML
	private void handleNewGoal() {
		SprintGoal tmp = new SprintGoal();
		boolean ok = mainApp.showEditSprintGoalDialog(tmp);
		if (ok) {
			sprint.addSprintGoal(tmp);
		}
	}

	@FXML
	private void handleEditGoal() {
		SprintGoal selected = tableGoals.getSelectionModel().getSelectedItem();
		if (selected != null) {
			mainApp.showEditSprintGoalDialog(selected);
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Goal Selected");
			alert.setContentText("Please select a goal in the table.");

			alert.showAndWait();
		}
	}

	@FXML
	private void handleDeleteGoal() {
		int index = tableGoals.getSelectionModel().getSelectedIndex();
		if(index >= 0) {
			tableGoals.getItems().remove(index);
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getStage());
			alert.setTitle("No selection");
			alert.setHeaderText("No Goal selected");
			alert.setContentText("Please select a goal");
			alert.showAndWait();
		}
	}
	
	public void showSprint(Sprint s) {
		sprint = s;
		
		if(s != null) {
			tableGoals.setItems(s.goalsProperty());
			lblName.setText(s.getName());
			lblStartDate.setText(DateUtil.format(s.getStartDate()));
			lblEndDate.setText(DateUtil.format(s.getEndDate()));
		} else {
			lblName.setText("");
			lblStartDate.setText("");
			lblEndDate.setText("");
		}
	}
	
	public void setMainApp(MainApp app) {
		mainApp = app;
	}
}
