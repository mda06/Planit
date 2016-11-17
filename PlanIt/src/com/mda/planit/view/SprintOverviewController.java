package com.mda.planit.view;

import java.time.LocalDate;

import com.mda.planit.MainApp;
import com.mda.planit.model.Project;
import com.mda.planit.model.Sprint;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class SprintOverviewController {
	private MainApp mainApp;
	private Project project;
	
	@FXML
	private TableView<Sprint> sprintTable;

	@FXML
	private TableColumn<Sprint, String> nameColumn;
	@FXML
	private TableColumn<Sprint, LocalDate> startDateColumn;
	@FXML
	private TableColumn<Sprint, LocalDate> endDateColumn;
	@FXML
	private TableColumn<Sprint, Number> goalsPercColumn;
	@FXML
	private TableColumn<Sprint, Number> tasksPercColumn;
	
	public SprintOverviewController() {}
	
	@FXML
	private void initialize() {
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		startDateColumn.setCellValueFactory(cellData -> cellData.getValue().startDateProperty());
		endDateColumn.setCellValueFactory(cellData -> cellData.getValue().endDateProperty());
		goalsPercColumn.setCellValueFactory(cellData -> cellData.getValue().goalsPercProperty());
		tasksPercColumn.setCellValueFactory(cellData -> cellData.getValue().tasksPercProperty());
		
		sprintTable.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> mainApp.showSprintDetail(newValue));
	}

	public void setMainApp(MainApp app) {
		mainApp = app;
		project = mainApp.getProject();
		sprintTable.setItems(project.sprintsProperty());
	}
}
