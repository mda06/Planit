package com.mda.planit.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.mda.planit.MainApp;
import com.mda.planit.model.Developer;
import com.mda.planit.model.DeveloperTask;
import com.mda.planit.model.DeveloperTaskDetail;
import com.mda.planit.model.Sprint;
import com.mda.planit.model.Task;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class MyWorkController {

	@SuppressWarnings("unused")
	private MainApp mainApp;
	private HashMap<Integer, Task> tasks;
	
	@FXML
	private ComboBox<Task> comboTasks;
	@FXML
	private TextField txtComment;

	// Start - End Time

	@FXML
	private TableView<DeveloperTaskDetail> tableGlobalDevTasks;
	@FXML
	private TableColumn<DeveloperTaskDetail, String> columnTaskName;
	@FXML
	private TableColumn<DeveloperTaskDetail, Date> columnTaskBeginDate;
	@FXML
	private TableColumn<DeveloperTaskDetail, Date> columnTaskEndDate;
	@FXML
	private TableColumn<DeveloperTaskDetail, String> columnTaskComment;

	public MyWorkController() {
		tasks = new HashMap<Integer, Task>(); 
	}

	@FXML
	private void initialize() {
		columnTaskName.setCellValueFactory(cd -> {
			int id = cd.getTableView().getItems().indexOf(cd.getValue());
			Task t = tasks.get(id);
			if(t != null)
				return new ReadOnlyStringWrapper(t.getName());
			else
				return new ReadOnlyStringWrapper();
		});
		columnTaskBeginDate.setCellValueFactory(cd -> cd.getValue().beginDateProperty());
		columnTaskEndDate.setCellValueFactory(cd -> cd.getValue().endDateProperty());
		columnTaskComment.setCellValueFactory(cd -> cd.getValue().commentProperty());
	}

	public void showGlobalDeveloperTask(Sprint sp, Developer d) {
		if(sp == null || d == null) return;
		comboTasks.setItems(sp.taskProperty());
		
		List<DeveloperTaskDetail> items = new ArrayList<>();
		for(int i = 0; i < sp.getTasks(d).size(); i++) {
			DeveloperTask dt = sp.getTasks(d).get(i);
			tasks.put(i, dt.getTask());
			for(DeveloperTaskDetail detail : dt.detailsProperty())
				items.add(detail);
		}
		tableGlobalDevTasks.setItems(FXCollections.observableArrayList(items));
	}

	public void setMainApp(MainApp app) {
		mainApp = app;
	}

}
