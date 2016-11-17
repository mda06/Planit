package com.mda.planit.view;

import java.time.LocalDate;

import com.mda.planit.MainApp;
import com.mda.planit.model.Sprint;
import com.mda.planit.model.SprintGoal;
import com.mda.planit.model.Task;
import com.mda.planit.model.TaskLabel;
import com.mda.planit.model.TaskState;
import com.mda.planit.util.DateUtil;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class SprintDetailsController {
	@SuppressWarnings("unused")
	private MainApp mainApp;
	
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
	
	@FXML
	private TableView<Task> tableTasks;
	@FXML
	private TableColumn<Task, String> columnTaskName;
	@FXML
	private TableColumn<Task, String> columnTaskDesc;
	@FXML
	private TableColumn<Task, LocalDate> columnTaskStartDate;
	@FXML
	private TableColumn<Task, LocalDate> columnTaskEndDate;
	@FXML
	private TableColumn<Task, TaskState> columnTaskState;

	@FXML
	private TableView<SprintGoal> tableTasksGoals;
	@FXML
	private TableColumn<SprintGoal, String> tableTaskGoalName;

	@FXML
	private TableView<TaskLabel> tableTasksLabel;
	@FXML
	private TableColumn<TaskLabel, String> tableTaskLabelName;
	
	public SprintDetailsController() {}
	
	@FXML
	private void initialize() {
		columnGoalName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		columnGoalDesc.setCellValueFactory(cellData -> cellData.getValue().descProperty());
		columnGoalAccomplish.setCellValueFactory(cellData -> cellData.getValue().accomplishProperty());
		
		columnTaskName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		columnTaskDesc.setCellValueFactory(cellData -> cellData.getValue().descProperty());
		columnTaskStartDate.setCellValueFactory(cellData -> cellData.getValue().startDateProperty());
		columnTaskEndDate.setCellValueFactory(cellData -> cellData.getValue().endDateProperty());
		columnTaskState.setCellValueFactory(cellData -> cellData.getValue().taskStateProperty());
		
		tableTaskGoalName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		tableTaskLabelName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		
		tableTasks.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> {
					if(newValue != null) {
						tableTasksGoals.setItems(newValue.getGoalsList());
						tableTasksLabel.setItems(newValue.getLabelList());
					} 
				});
		
		showSprint(null);
	}
	
	public void showSprint(Sprint s) {
		tableTasksGoals.setItems(null);
		tableTasksLabel.setItems(null);
		
		if(s != null) {
			tableGoals.setItems(s.goalsProperty());
			tableTasks.setItems(s.taskProperty());
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
