package com.mda.planit.view;

import java.time.LocalDate;

import com.mda.planit.MainApp;
import com.mda.planit.model.Sprint;
import com.mda.planit.model.SprintGoal;
import com.mda.planit.model.Task;
import com.mda.planit.model.TaskLabel;
import com.mda.planit.model.TaskState;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

public class TaskDetailsController {
	private MainApp mainApp;
	private Sprint sprint;
	
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
	
	@FXML
	private void initialize() {
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
						mainApp.showDevelopersTasksDetails(newValue);
					} 
				});
		
		tableTasksLabel.setRowFactory(tv -> new TableRow<TaskLabel>() {
			@Override
		    public void updateItem(TaskLabel lbl, boolean empty) {
		        super.updateItem(lbl, empty) ;
		        if (lbl == null) {
		            setStyle("");
		        } else {
		            setStyle("-fx-background-color: '" + lbl.getColor() + "';");
		        }
		    }
		});
	}
	
	public void showTasks(Sprint sprint) {
		this.sprint = sprint;
		tableTasksGoals.getItems().clear();
		tableTasksLabel.getItems().clear();
		tableTasks.setItems(sprint.taskProperty());
	}
	
	@FXML
	private void handleNewTask() {
		if(sprint == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Sprint Selected");
			alert.setContentText("Please select a sprint before.");

			alert.showAndWait();
			return;
		}
		
		Task tmp = new Task();
		boolean ok = mainApp.showEditTaskDialog(tmp);
		if (ok) {
			sprint.addTask(tmp);
		}
	}

	@FXML
	private void handleEditTask() {
		Task selected = tableTasks.getSelectionModel().getSelectedItem();
		if (selected != null) {
			mainApp.showEditTaskDialog(selected);
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Task Selected");
			alert.setContentText("Please select a task in the table.");

			alert.showAndWait();
		}
	}

	@FXML
	private void handleDeleteTask() {
		int index = tableTasks.getSelectionModel().getSelectedIndex();
		if(index >= 0) {
			tableTasks.getItems().remove(index);
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getStage());
			alert.setTitle("No selection");
			alert.setHeaderText("No Tasks selected");
			alert.setContentText("Please select a task");
			alert.showAndWait();
		}
	}
	
	@FXML
	private void handleLinkSprintGoal() {
		Task selected = tableTasks.getSelectionModel().getSelectedItem();
		if (selected != null) {
			mainApp.showLinkTaskGoalDialog(selected);
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Task Selected");
			alert.setContentText("Please select a task in the table.");

			alert.showAndWait();
		}
	}
	
	@FXML
	private void handleRemoveSprintGoal() {
		int index = tableTasksGoals.getSelectionModel().getSelectedIndex();
		if(index >= 0) {
			tableTasksGoals.getItems().remove(index);
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getStage());
			alert.setTitle("No selection");
			alert.setHeaderText("No Tasks selected");
			alert.setContentText("Please select a task");
			alert.showAndWait();
		}
	}
	
	@FXML
	private void handleLinkLabel() {
		Task selected = tableTasks.getSelectionModel().getSelectedItem();
		if (selected != null) {
			mainApp.showLinkTaskLabelDialog(selected);
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Task Selected");
			alert.setContentText("Please select a task in the table.");

			alert.showAndWait();
		}
	}
	
	@FXML
	private void handleRemoveLabel() {
		int index = tableTasksLabel.getSelectionModel().getSelectedIndex();
		if(index >= 0) {
			tableTasksLabel.getItems().remove(index);
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getStage());
			alert.setTitle("No selection");
			alert.setHeaderText("No Tasks selected");
			alert.setContentText("Please select a task");
			alert.showAndWait();
		}
	}	
	
	public void setMainApp(MainApp app) {
		mainApp = app;
	}
}
