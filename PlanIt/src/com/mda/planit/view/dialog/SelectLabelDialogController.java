package com.mda.planit.view.dialog;

import com.mda.planit.model.Task;
import com.mda.planit.model.TaskLabel;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class SelectLabelDialogController {
	@FXML
	private ComboBox<TaskLabel> comboLabels;
	
	private Stage dialogStage;
	private Task task;
	private boolean okClicked = false;
	
	@FXML
	private void initialize() {}
	
	public void setDialogStage(Stage stage) {
		dialogStage = stage;
	}
	
	public void show(Task t) {
		task = t;
	}
	
	public boolean isOkClicked() {
		return okClicked;
	}
	
	public void setLabels(ObservableList<TaskLabel> lst) {
		comboLabels.setItems(lst);
		comboLabels.getSelectionModel().select(0);
	}
	
	@FXML
	private void handleOk() {
		TaskLabel lbl = comboLabels.getValue();
		if(lbl != null && task != null) {
			task.addTaskLabel(lbl);
			okClicked = true;
			dialogStage.close();
		}
	}
	
	@FXML
	private void handleCancel() {
		okClicked = false;
		dialogStage.close();
	}
}
