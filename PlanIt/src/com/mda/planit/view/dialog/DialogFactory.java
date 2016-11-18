package com.mda.planit.view.dialog;

import java.io.IOException;

import com.mda.planit.MainApp;
import com.mda.planit.model.Developer;
import com.mda.planit.model.Sprint;
import com.mda.planit.model.SprintGoal;
import com.mda.planit.model.Task;
import com.mda.planit.model.TaskLabel;
import com.mda.planit.view.ProjectOverviewController;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DialogFactory {
	private Stage stage;
	
	public DialogFactory(Stage st) {
		stage = st;
	}
	
	public boolean showEditDeveloperDialog(Developer d) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/dialog/DeveloperEditDialog.fxml"));
			AnchorPane pane = loader.load();
			
			Stage dia = new Stage();
			dia.setTitle("Edit Developer");
			dia.initModality(Modality.WINDOW_MODAL);
			dia.initOwner(stage);
			Scene scene = new Scene(pane);
			dia.setScene(scene);
			
			DeveloperEditDialogController dc = loader.getController();
			dc.setDialogStage(dia);
			dc.setDeveloper(d);
			
			dia.showAndWait();
			return dc.isOkClicked();
		} catch(IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean showEditSprintDialog(Sprint sp) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/dialog/SprintEditDialog.fxml"));
			AnchorPane pane = loader.load();
			
			Stage dia = new Stage();
			dia.setTitle("Edit Sprint");
			dia.initModality(Modality.WINDOW_MODAL);
			dia.initOwner(stage);
			Scene scene = new Scene(pane);
			dia.setScene(scene);
			
			SprintEditDialogController dc = loader.getController();
			dc.setDialogStage(dia);
			dc.setSprint(sp);
			
			dia.showAndWait();
			return dc.isOkClicked();
		} catch(IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	

	public boolean showEditSprintGoalDialog(SprintGoal tmp) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/dialog/SprintGoalEditDialog.fxml"));
			AnchorPane pane = loader.load();
			
			Stage dia = new Stage();
			dia.setTitle("Edit Goal");
			dia.initModality(Modality.WINDOW_MODAL);
			dia.initOwner(stage);
			Scene scene = new Scene(pane);
			dia.setScene(scene);
			
			SprintGoalEditDialogController dc = loader.getController();
			dc.setDialogStage(dia);
			dc.setGoal(tmp);
			
			dia.showAndWait();
			return dc.isOkClicked();
		} catch(IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean showEditTaskDialog(Task selected) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/dialog/TaskEditDialog.fxml"));
			AnchorPane pane = loader.load();
			
			Stage dia = new Stage();
			dia.setTitle("Edit Task");
			dia.initModality(Modality.WINDOW_MODAL);
			dia.initOwner(stage);
			Scene scene = new Scene(pane);
			dia.setScene(scene);
			
			TaskEditDialogController dc = loader.getController();
			dc.setDialogStage(dia);
			dc.setTask(selected);
			
			dia.showAndWait();
			return dc.isOkClicked();
		} catch(IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean showLinkTaskGoalDialog(Task selected, ProjectOverviewController pOverview) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/dialog/SelectSprintGoalDialog.fxml"));
			AnchorPane pane = loader.load();
			
			Stage dia = new Stage();
			dia.setTitle("Select a sprint goal");
			dia.initModality(Modality.WINDOW_MODAL);
			dia.initOwner(stage);
			Scene scene = new Scene(pane);
			dia.setScene(scene);
			
			SelectSprintGoalDialogController dc = loader.getController();
			dc.setDialogStage(dia);
			dc.setSprint(pOverview.getSelectedSprint());
			dc.show(selected);
			
			dia.showAndWait();
			return dc.isOkClicked();
		} catch(IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean showLinkTaskLabelDialog(Task selected,  ObservableList<TaskLabel> labels) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/dialog/SelectTaskLabelDialog.fxml"));
			AnchorPane pane = loader.load();
			
			Stage dia = new Stage();
			dia.setTitle("Select a task label");
			dia.initModality(Modality.WINDOW_MODAL);
			dia.initOwner(stage);
			Scene scene = new Scene(pane);
			dia.setScene(scene);
			
			SelectLabelDialogController dc = loader.getController();
			dc.setDialogStage(dia);
			dc.setLabels(labels);
			dc.show(selected);
			
			dia.showAndWait();
			return dc.isOkClicked();
		} catch(IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
