package com.mda.planit;

import java.io.IOException;
import java.time.LocalDate;

import com.mda.planit.model.Developer;
import com.mda.planit.model.Project;
import com.mda.planit.model.Sprint;
import com.mda.planit.model.SprintGoal;
import com.mda.planit.model.Task;
import com.mda.planit.model.TaskLabel;
import com.mda.planit.model.TaskState;
import com.mda.planit.view.DeveloperEditDialogController;
import com.mda.planit.view.ProjectOverviewController;
import com.mda.planit.view.SprintDetailsController;
import com.mda.planit.view.SprintEditDialogController;
import com.mda.planit.view.TaskDetailsController;
import com.mda.planit.view.dialog.SprintGoalEditDialogController;
import com.mda.planit.view.dialog.TaskEditDialogController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {

	private Stage stage;
	private Project project;
	private SplitPane rootLayout;
	private SprintDetailsController spDetails;
	private ProjectOverviewController pOverview;
	private TaskDetailsController tsDetails;
	
	private AnchorPane paneSprintDetails;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		stage.setTitle("Project");
		
		initData();
		initRootLayout();
		showProjectOverview();
		showSprintDetails();
		showTasksDetails();
		showProjectOverview(project);
	}
	
	private void initData() {
		final TaskLabel lblProg = new TaskLabel("Programming", Color.RED);
		
		project = new Project("First Project on Planit", "Testing every feature", LocalDate.now(), LocalDate.of(2017, 2, 1));
		project.addDeveloper(new Developer("Mike", Color.BURLYWOOD));
		project.addDeveloper(new Developer("Ruben", Color.BLUEVIOLET));
		project.addDeveloper(new Developer("Illya", Color.TOMATO));
		project.addDeveloper(new Developer("Willem-jan", Color.DARKSALMON));
		project.addDeveloper(new Developer("Christian", Color.WHITESMOKE));
		project.addDeveloper(new Developer("Pieter", Color.AQUA));
		
		Sprint sp1 = new Sprint("Sprint 1", LocalDate.now(), LocalDate.of(2016, 11, 30));
		sp1.addSprintGoal(new SprintGoal("Set up the Windows", "Also mac"));
		sp1.addSprintGoal(new SprintGoal("Set up the IDE", "Eclipse and so for"));		
		sp1.addSprintGoal(new SprintGoal("Set up the Java", "Test this !"));
		sp1.goalsProperty().get(1).setAccomplish(true);
		sp1.addTask(new Task("Meeting the group", "Greet each others", LocalDate.now(), LocalDate.now().plusDays(3)));
		sp1.taskProperty().get(0).setTaskState(TaskState.DONE);
		sp1.addTask(new Task("Set ups", "On each pc", LocalDate.now().plusDays(3), LocalDate.now().plusDays(6)));
		Task ts = new Task("Developing the base architecture", "UML in code", LocalDate.now().plusDays(3), LocalDate.of(2016, 11, 30));
		ts.setTaskState(TaskState.RUNNING);
		ts.addTaskLabel(lblProg);
		ts.addTaskLabel(new TaskLabel("Setup", Color.YELLOW));
		ts.addSprintGoal(sp1.goalsProperty().get(0));
		ts.addSprintGoal(sp1.goalsProperty().get(1));
		ts.addSprintGoal(sp1.goalsProperty().get(2));
		sp1.addTask(ts);
		project.addSprint(sp1);
		
		Sprint sp2 = new Sprint("Sprint 2", LocalDate.of(2016, 11, 30), LocalDate.of(2016, 12, 10));
		sp2.addSprintGoal(new SprintGoal("User manupilating", "CRUD basics"));
		sp2.addSprintGoal(new SprintGoal("DB connecting", "Connect savetly to the DB"));		
		sp2.addSprintGoal(new SprintGoal("GUI", "Nice gui"));
		sp2.goalsProperty().get(2).setAccomplish(true);
		sp2.addSprintGoal(new SprintGoal("Test", "No bugs in this progam"));		
		sp2.addSprintGoal(new SprintGoal("UML modeling", "We want a good example of this uml"));
		project.addSprint(sp2);
		
		Sprint sp3 = new Sprint("Sprint 3", LocalDate.of(2017, 1, 1), project.getEndDate());
		sp3.addSprintGoal(new SprintGoal("Clear code", "No bugs, beautifull code"));	
		ts = new Task("Review code of Mike", "Some white spaces to clean", LocalDate.of(2017, 1, 1), LocalDate.of(2017, 1, 1).plusDays(5));
		ts.addTaskLabel(lblProg);
		ts.addSprintGoal(sp3.goalsProperty().get(0));
		sp3.addTask(ts);
		sp3.addSprintGoal(new SprintGoal("Setup release", "Everything right"));
		project.addSprint(sp3);
	}
	
	private void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = loader.load();
			
			Scene scene = new Scene(new BorderPane(rootLayout), 1300, 750);
			stage.setScene(scene);
			stage.show();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private void showProjectOverview() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/ProjectOverview.fxml"));
			AnchorPane pane = loader.load();

			rootLayout.getItems().add(pane);
			pOverview = loader.getController();
			pOverview.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void showSprintDetails() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/SprintDetails.fxml"));
			paneSprintDetails = loader.load();

			rootLayout.getItems().add(paneSprintDetails);
			spDetails = loader.getController();
			spDetails.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void showTasksDetails() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/TaskDetails.fxml"));
			AnchorPane pane = loader.load();

			((VBox)(paneSprintDetails.getChildren().get(0))).getChildren().add(pane);
			tsDetails = loader.getController();
			tsDetails.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean showEditDeveloperDialog(Developer d) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/DeveloperEditDialog.fxml"));
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
			loader.setLocation(MainApp.class.getResource("view/SprintEditDialog.fxml"));
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
	
	public void showProjectOverview(Project p) {
		pOverview.showProject(p);
	}
	
	public void showSprintDetail(Sprint s) {
		spDetails.showSprint(s);
		tsDetails.showTasks(s);
	}
	
	public Stage getStage() {
		return stage;
	}
	
	public Project getProject() {
		return project;
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
