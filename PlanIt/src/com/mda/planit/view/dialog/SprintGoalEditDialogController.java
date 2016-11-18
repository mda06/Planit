package com.mda.planit.view.dialog;

import com.mda.planit.model.SprintGoal;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SprintGoalEditDialogController {

	@FXML
	private TextField txtName;
	@FXML
	private TextField txtDesc;
	
	private Stage dialogStage;
	private SprintGoal goal;
	private boolean okClicked = false;

	@FXML
	private void initialize() {
	}

	public void setDialogStage(Stage stage) {
		dialogStage = stage;
	}

	public void setGoal(SprintGoal g) {
		goal = g;

		txtName.setText(g.getName());
		txtDesc.setText(g.getDesc());
	}

	public boolean isOkClicked() {
		return okClicked;
	}

	@FXML
	private void handleOk() {
		if (isInputValid()) {
			goal.setName(txtName.getText());
			goal.setDesc(txtDesc.getText());

			okClicked = true;
			dialogStage.close();
		}
	}

	@FXML
	private void handleCancel() {
		okClicked = false;
		dialogStage.close();
	}

	private boolean isInputValid() {
		if (txtName.getText() == null || txtName.getText().isEmpty()
				|| txtDesc.getText() == null || txtDesc.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("Invalid fields");
			alert.setHeaderText("Please enter a name");
			alert.setContentText("Name and description are required");

			alert.showAndWait();
			return false;
		}
		return true;
	}
}
