package com.mda.planit.view.dialog;

import com.mda.planit.model.Sprint;
import com.mda.planit.model.SprintGoal;
import com.mda.planit.model.Task;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class SelectSprintGoalDialogController {
	@FXML
	private ComboBox<SprintGoal> comboGoals;
	
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
	
	public void setSprint(Sprint sp) {
		comboGoals.setItems(sp.goalsProperty());
		comboGoals.getSelectionModel().select(0);
	}
	
	@FXML
	private void handleOk() {
		SprintGoal goal = comboGoals.getValue();
		if(goal != null && task != null) {
			task.addSprintGoal(goal);
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
