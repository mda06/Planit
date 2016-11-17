package com.mda.planit;

import java.io.IOException;
import java.time.LocalDate;

import com.mda.planit.model.Project;
import com.mda.planit.model.Sprint;
import com.mda.planit.model.SprintGoal;
import com.mda.planit.model.Task;
import com.mda.planit.model.TaskLabel;
import com.mda.planit.model.TaskState;
import com.mda.planit.view.SprintDetailsController;
import com.mda.planit.view.SprintOverviewController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainApp extends Application {

	private Stage stage;
	private Project project;
	private SplitPane rootLayout;
	private SprintDetailsController spDetails;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		stage.setTitle("Project");
		
		initData();
		initRootLayout();
		showSprintOverview();
		showDetailsOverview();
	}
	
	private void initData() {
		final TaskLabel lblProg = new TaskLabel("Programming", Color.RED);
		
		project = new Project("First Project on Planit", "Testing every feature", LocalDate.now(), LocalDate.of(2017, 2, 1));
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
	
	private void showSprintOverview() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/SprintOverview.fxml"));
			AnchorPane pane = loader.load();

			rootLayout.getItems().add(pane);
			SprintOverviewController sc = loader.getController();
			sc.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void showDetailsOverview() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/SprintDetails.fxml"));
			AnchorPane pane = loader.load();

			rootLayout.getItems().add(pane);
			spDetails = loader.getController();
			spDetails.setMainApp(this);
			showSprintDetail(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showSprintDetail(Sprint s) {
		spDetails.showSprint(s);
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
