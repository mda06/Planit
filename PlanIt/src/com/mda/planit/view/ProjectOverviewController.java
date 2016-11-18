package com.mda.planit.view;

import java.time.LocalDate;

import com.mda.planit.MainApp;
import com.mda.planit.model.Developer;
import com.mda.planit.model.Project;
import com.mda.planit.model.Sprint;
import com.mda.planit.util.DateUtil;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;

public class ProjectOverviewController {
	private MainApp mainApp;

	@FXML
	private Label lblName;
	@FXML
	private Label lblDesc;
	@FXML
	private Label lblStartDate;
	@FXML
	private Label lblEndDate;

	@FXML
	private TableView<Sprint> tableSprint;
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

	@FXML
	private TableView<Developer> tableDevelopers;
	@FXML
	private TableColumn<Developer, String> developerNameColumn;

	public ProjectOverviewController() {
	}

	@FXML
	private void initialize() {
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		startDateColumn.setCellValueFactory(cellData -> cellData.getValue().startDateProperty());
		endDateColumn.setCellValueFactory(cellData -> cellData.getValue().endDateProperty());
		goalsPercColumn.setCellValueFactory(cellData -> cellData.getValue().goalsPercProperty());
		tasksPercColumn.setCellValueFactory(cellData -> cellData.getValue().tasksPercProperty());
		developerNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		tableDevelopers.setRowFactory(tv -> new TableRow<Developer>() {
			@Override
			public void updateItem(Developer dev, boolean empty) {
				super.updateItem(dev, empty);
				if (dev == null) {
					setStyle("");
				} else {
					setStyle("-fx-background-color: '" + dev.getColor() + "';");
				}
			}
		});

		tableSprint.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> mainApp.showSprintDetail(newValue));

		showProject(null);
	}

	public void showProject(Project p) {
		if (p != null) {
			tableSprint.setItems(p.sprintsProperty());
			tableDevelopers.setItems(p.devsProperty());
			lblName.setText(p.getName());
			lblDesc.setText(p.getDesc());
			lblStartDate.setText(DateUtil.format(p.getStartDate()));
			lblEndDate.setText(DateUtil.format(p.getEndDate()));
		} else {
			tableSprint.setItems(null);
			tableDevelopers.setItems(null);
			lblName.setText("");
			lblDesc.setText("");
			lblStartDate.setText("");
			lblEndDate.setText("");
		}
	}

	@FXML
	private void handleNewDev() {
		Developer tmp = new Developer();
		boolean ok = mainApp.showEditDeveloperDialog(tmp);
		if (ok) {
			mainApp.getProject().addDeveloper(tmp);
		}
	}

	@FXML
	private void handleEditDev() {
		Developer selected = tableDevelopers.getSelectionModel().getSelectedItem();
		if (selected != null) {
			mainApp.showEditDeveloperDialog(selected);
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Developer Selected");
			alert.setContentText("Please select a developer in the table.");

			alert.showAndWait();
		}
	}

	@FXML
	private void handleDeleteDev() {
		int index = tableDevelopers.getSelectionModel().getSelectedIndex();
		if(index >= 0) {
			tableDevelopers.getItems().remove(index);
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getStage());
			alert.setTitle("No selection");
			alert.setHeaderText("No Developer selected");
			alert.setContentText("Please select a developer");
			alert.showAndWait();
		}
	}

	@FXML
	private void handleNewSprint() {
		Sprint tmp = new Sprint();
		boolean ok = mainApp.showEditSprintDialog(tmp);
		if (ok) {
			mainApp.getProject().addSprint(tmp);
		}
	}

	@FXML
	private void handleEditSprint() {
		Sprint selected = tableSprint.getSelectionModel().getSelectedItem();
		if (selected != null) {
			mainApp.showEditSprintDialog(selected);
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Sprint Selected");
			alert.setContentText("Please select a sprint in the table.");

			alert.showAndWait();
		}
	}

	@FXML
	private void handleDeleteSprint() {
		int index = tableSprint.getSelectionModel().getSelectedIndex();
		if(index >= 0) {
			tableSprint.getItems().remove(index);
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getStage());
			alert.setTitle("No selection");
			alert.setHeaderText("No Sprint selected");
			alert.setContentText("Please select a sprint");
			alert.showAndWait();
		}
	}
	
	public Sprint getSelectedSprint() {
		return tableSprint.getSelectionModel().getSelectedItem();
	}

	public void setMainApp(MainApp app) {
		mainApp = app;
	}
}
