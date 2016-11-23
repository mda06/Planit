package com.mda.planit.view;

import com.mda.planit.MainApp;
import com.mda.planit.model.Developer;
import com.mda.planit.model.DeveloperWork;
import com.mda.planit.model.Task;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class DevelopersWorkController {
	@SuppressWarnings("unused")
	private MainApp mainApp;
	private Task task;

	@FXML
	private TableView<Developer> tableDevs;
	@FXML
	private TableColumn<Developer, String> columnDevName;

	@FXML
	private TableView<DeveloperWork> tableWork;
	@FXML
	private TableColumn<DeveloperWork, String> columnBeginDate;
	@FXML
	private TableColumn<DeveloperWork, String> columnEndDate;
	@FXML
	private TableColumn<DeveloperWork, String> columnComment;
	@FXML
	private TableColumn<DeveloperWork, String> columnDuration;

	@FXML
	private void initialize() {
		columnBeginDate.setCellValueFactory((data) -> data.getValue().beginDateStringProperty());
		columnEndDate.setCellValueFactory((data) -> data.getValue().endDateStringProperty());
		columnComment.setCellValueFactory(cd -> cd.getValue().commentProperty());
		columnDuration.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DeveloperWork, String>, ObservableValue<String>>() {

		    @Override
		    public ObservableValue<String> call(TableColumn.CellDataFeatures<DeveloperWork, String> p) {
		        if (p.getValue() != null) {
		        	String h = String.valueOf((int)p.getValue().getDuration().toHours());
		        	String m = String.valueOf((int)p.getValue().getDuration().toMinutes() % 60);
		            return new SimpleStringProperty(h + ":" + m);
		        } else {
		            return new SimpleStringProperty("<no time>");
		        }
		    }
		});
		
		columnDevName.setCellValueFactory(cd -> cd.getValue().nameProperty());
		tableDevs.setRowFactory(tv -> new TableRow<Developer>() {
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
		
		tableDevs.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> {
					if(newValue != null && task != null && task.workOfProperty(newValue) != null) {
						tableWork.setItems(task.workOfProperty(newValue));
					}
				});
		
		showDeveloperTask(null);
	}

	public void showDeveloperTask(Task task) {
		this.task = task;
		if (task != null) {
			tableDevs.setItems(task.getAssignedDevsList());
		} else {
			tableDevs.setItems(null);
			tableWork.setItems(null);
		}
	}

	public void setMainApp(MainApp app) {
		mainApp = app;
	}
}
