package com.mda.planit;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;

import com.mda.planit.model.Developer;
import com.mda.planit.model.Project;
import com.mda.planit.model.Sprint;
import com.mda.planit.model.SprintGoal;
import com.mda.planit.model.Task;
import com.mda.planit.model.TaskLabel;
import com.mda.planit.model.TaskState;
import com.mda.planit.view.DevelopersDetailController;
import com.mda.planit.view.ProjectOverviewController;
import com.mda.planit.view.SprintDetailsController;
import com.mda.planit.view.TaskDetailsController;
import com.mda.planit.view.dialog.DialogFactory;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainApp extends Application {
	
	private ObservableList<TaskLabel> labels;
	private Developer connectedDev;

	private Stage stage;
	private Project project;
	private SplitPane rootLayout;
	private SprintDetailsController spDetails;
	private ProjectOverviewController pOverview;
	private TaskDetailsController tsDetails;
	private DevelopersDetailController devDetails;
	private DialogFactory dialogFactory;
	
	private VBox taskPane;
	private Tab tabTasks;
	private TabPane tabPane;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		stage.setTitle("Project");
		dialogFactory = new DialogFactory(stage);
		
		initData();
		initRootLayout();
		showProjectOverview();
		showSprintDetails();
		showTasksDetails();
		showDevelopersTasksDetail();
		showProjectOverview(project);
	}
	
	private void initData() {
		connectedDev = new Developer("Mike", Color.BURLYWOOD);
		
		labels = FXCollections.observableArrayList(
				new TaskLabel("Programming", Color.RED),
				new TaskLabel("DB", Color.LIME),
				new TaskLabel("Setup", Color.YELLOW));
		
		project = new Project("First Project on Planit", "Testing every feature", LocalDate.now(), LocalDate.of(2017, 2, 1));
		project.addDeveloper(connectedDev);
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
		sp1.taskProperty().get(0).addDev(project.devsProperty().get(1));
		sp1.taskProperty().get(0).addDev(project.devsProperty().get(2));
		sp1.taskProperty().get(0).setTaskState(TaskState.DONE);
		sp1.addTask(new Task("Set ups", "On each pc", LocalDate.now().plusDays(3), LocalDate.now().plusDays(6)));
		Task ts = new Task("Developing the base architecture", "UML in code", LocalDate.now().plusDays(3), LocalDate.of(2016, 11, 30));
		ts.addDev(connectedDev);
		ts.addDev(project.devsProperty().get(3));
		ts.addDev(project.devsProperty().get(4));
		ts.setTaskState(TaskState.RUNNING);
		ts.addTaskLabel(labels.get(0));
		ts.addTaskLabel(labels.get(2));
		ts.addSprintGoal(sp1.goalsProperty().get(0));
		ts.addSprintGoal(sp1.goalsProperty().get(1));
		ts.addSprintGoal(sp1.goalsProperty().get(2));
		sp1.addTask(ts);
		project.addSprint(sp1);
		
		Sprint sp2 = new Sprint("Sprint 2", LocalDate.of(2016, 11, 30), LocalDate.of(2016, 12, 10));
		sp2.addSprintGoal(new SprintGoal("User manupilating", "CRUD basics"));
		sp2.addSprintGoal(new SprintGoal("DB connecting", "Connect savetly to the DB"));		
		ts = new Task("Create the DB", "MySQL", LocalDate.of(2016, 11, 30), LocalDate.of(2016, 12, 1));
		ts.addTaskLabel(labels.get(1));
		ts.addDev(connectedDev);
		Calendar begin = Calendar.getInstance(); begin.set(2016, 11, 31, 16, 32);
		Calendar end = Calendar.getInstance(); end.set(2016, 11, 31, 18, 32);
		ts.addDeveloperTask(connectedDev, begin.getTime(), end.getTime(), "Create this feature");
		sp2.addTask(ts);
		sp2.addSprintGoal(new SprintGoal("GUI", "Nice gui"));
		sp2.goalsProperty().get(2).setAccomplish(true);
		sp2.addSprintGoal(new SprintGoal("Test", "No bugs in this progam"));		
		sp2.addSprintGoal(new SprintGoal("UML modeling", "We want a good example of this uml"));
		project.addSprint(sp2);
		
		Sprint sp3 = new Sprint("Sprint 3", LocalDate.of(2017, 1, 1), project.getEndDate());
		sp3.addSprintGoal(new SprintGoal("Clear code", "No bugs, beautifull code"));	
		ts = new Task("Review code of Mike", "Some white spaces to clean", LocalDate.of(2017, 1, 1), LocalDate.of(2017, 1, 1).plusDays(5));
		ts.addDev(project.devsProperty().get(1));
		ts.addDev(project.devsProperty().get(3));
		ts.addTaskLabel(labels.get(0));
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
			stage.setFullScreen(true);
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
			AnchorPane pane = loader.load();


			tabPane = new TabPane();
			rootLayout.getItems().add(tabPane);
			Tab tab = new Tab("Sprint Overview");
			tab.setContent(pane);
			tabPane.getTabs().add(tab);
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
			taskPane = loader.load();
			
			tabTasks = new Tab("Tasks Overview");
			tabTasks.setContent(taskPane);
			tabPane.getTabs().add(tabTasks);
			tsDetails = loader.getController();
			tsDetails.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void showDevelopersTasksDetail() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/DevelopersTaskDetails.fxml"));
			TitledPane pane = loader.load();
			
			taskPane.getChildren().add(pane);
			
			devDetails = loader.getController();
			devDetails.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean showEditDeveloperDialog(Developer d) {
		return dialogFactory.showEditDeveloperDialog(d);
	}
	
	public boolean showEditSprintDialog(Sprint sp) {
		return dialogFactory.showEditSprintDialog(sp, project);
	}
	
	public boolean showEditSprintGoalDialog(SprintGoal tmp) {
		return dialogFactory.showEditSprintGoalDialog(tmp);
	}
	
	public boolean showEditTaskDialog(Task selected) {
		return dialogFactory.showEditTaskDialog(selected, pOverview.getSelectedSprint());
	}
	
	public boolean showLinkTaskGoalDialog(Task selected) {
		return dialogFactory.showLinkTaskGoalDialog(selected, pOverview);
	}
	
	public boolean showLinkTaskLabelDialog(Task selected) {
		return dialogFactory.showLinkTaskLabelDialog(selected, labels);
	}
	
	public void showProjectOverview(Project p) {
		pOverview.showProject(p);
	}
	
	public void showSprintDetail(Sprint s) {
		spDetails.showSprint(s);
		tsDetails.showTasks(s);
		devDetails.showDeveloperTask(null);
	}
	
	public void showDevelopersTasksDetails(Task t) {
		devDetails.showDeveloperTask(t);
	}
	
	public Stage getStage() {
		return stage;
	}
	
	public Project getProject() {
		return project;
	}
	
	public Developer getConnectedDeveloper() {
		return connectedDev;
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
