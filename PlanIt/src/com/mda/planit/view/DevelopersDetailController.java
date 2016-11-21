package com.mda.planit.view;

import java.util.Date;

import com.mda.planit.MainApp;
import com.mda.planit.model.Developer;
import com.mda.planit.model.DeveloperTaskDetail;
import com.mda.planit.model.Task;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

public class DevelopersDetailController {
	@SuppressWarnings("unused")
	private MainApp mainApp;
	private Task task;
	
	@FXML
	private TableView<Developer> tableDevs;
	@FXML
	private TableColumn<Developer, String> columnDevName;
	
	@FXML
	private TableView<DeveloperTaskDetail> tableWork;
	@FXML
	private TableColumn<DeveloperTaskDetail, Date> columnBeginDate;
	@FXML
	private TableColumn<DeveloperTaskDetail, Date> columnEndDate;
	@FXML
	private TableColumn<DeveloperTaskDetail, String> columnComment;
	
	@FXML
	private void initialize() {
		columnBeginDate.setCellValueFactory(cd -> cd.getValue().beginDateProperty());
		columnEndDate.setCellValueFactory(cd -> cd.getValue().endDateProperty());
		columnComment.setCellValueFactory(cd -> cd.getValue().commentProperty());
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
					if(newValue != null && task != null) {
						tableWork.setItems(task.getTasks(newValue).detailsProperty());
					}
				});
		
		showDeveloperTask(null);
	}
	
	public void showDeveloperTask(Task task) {
		this.task = task;
		if(task != null) {
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
