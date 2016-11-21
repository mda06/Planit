package com.mda.planit.view;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.util.converter.LocalTimeStringConverter;

public class MyWorkController {
	private MainApp mainApp;
	private HashMap<Integer, Task> tasks;

	@FXML
	private ComboBox<Task> comboTasks;
	@FXML
	private TextField txtComment;
	@FXML
	private DatePicker dateBegin;
	@FXML
	private Spinner<LocalTime> spinnerBegin;
	@FXML
	private DatePicker dateEnd;
	@FXML
	private Spinner<LocalTime> spinnerEnd;

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
			if (t != null) return new ReadOnlyStringWrapper(t.getName());
			else return new ReadOnlyStringWrapper();
		});
		columnTaskBeginDate.setCellValueFactory(cd -> cd.getValue().beginDateProperty());
		columnTaskEndDate.setCellValueFactory(cd -> cd.getValue().endDateProperty());
		columnTaskComment.setCellValueFactory(cd -> cd.getValue().commentProperty());

		/*UnaryOperator<TextFormatter.Change> filter = new UnaryOperator<TextFormatter.Change>() {
	        @Override
	        public TextFormatter.Change apply(TextFormatter.Change t) {
	            String newText = t.getControlNewText();
	            try {
	            	LocalTime.parse(newText);
	            	return t;
	            } catch(DateTimeParseException ex) {
	            	return null;
	            }
	        }
	    };
		spinner.getEditor().setTextFormatter(new TextFormatter<>(filter));*/
		dateBegin.setValue(LocalDate.now());
		spinnerBegin.setValueFactory(createLocalTimeSpinnerValue());
		spinnerBegin.increment();
		dateEnd.setValue(LocalDate.now());
		spinnerEnd.setValueFactory(createLocalTimeSpinnerValue());
		spinnerEnd.increment();
	}
	
	private SpinnerValueFactory<LocalTime> createLocalTimeSpinnerValue() {
		return new SpinnerValueFactory<LocalTime>() {
			{
				setConverter(new LocalTimeStringConverter(DateTimeFormatter.ofPattern("HH:mm"), DateTimeFormatter.ofPattern("HH:mm")));
			}

			@Override
			public void decrement(int steps) {
				if (getValue() == null) setValue(LocalTime.now());
				else {
					LocalTime time = (LocalTime) getValue();
					setValue(time.minusMinutes(steps));
				}
			}

			@Override
			public void increment(int steps) {
				if (this.getValue() == null) setValue(LocalTime.now());
				else {
					LocalTime time = (LocalTime) getValue();
					setValue(time.plusMinutes(steps));
				}
			}
		};
	}

	public void showGlobalDeveloperTask(Sprint sp, Developer d) {
		if (sp == null || d == null) return;
		
		comboTasks.setItems(sp.taskProperty());
		if(comboTasks.getItems().size() > 0)
			comboTasks.getSelectionModel().select(0);

		List<DeveloperTaskDetail> items = new ArrayList<>();
		for (int i = 0; i < sp.getTasks(d).size(); i++) {
			DeveloperTask dt = sp.getTasks(d).get(i);
			tasks.put(i, dt.getTask());
			for (DeveloperTaskDetail detail : dt.detailsProperty())
				items.add(detail);
		}
		tableGlobalDevTasks.setItems(FXCollections.observableArrayList(items));
	}
		
	@FXML
	private void handleSave() {
		if(!isInputValid()) return;
		
		Task task = comboTasks.getValue();
		String comment = txtComment.getText();
		Date dateBegin = getDateBegin();
		Date dateEnd = getDateEnd();
		
		DeveloperTaskDetail dtd = task.addDeveloperTask(mainApp.getConnectedDeveloper(), dateBegin, dateEnd, comment);
		tableGlobalDevTasks.getItems().add(dtd);
		txtComment.clear();
	}
	
	private Date getDateEnd() {
		LocalDate ld = dateEnd.getValue();
		LocalTime lt = spinnerEnd.getValue();
		Calendar cal = Calendar.getInstance();
		cal.set(ld.getYear(), ld.getMonthValue(), ld.getDayOfMonth(), lt.getHour(), lt.getMinute(), 0);
		return cal.getTime();
	}

	private Date getDateBegin() {
		LocalDate ldBegin = dateBegin.getValue();
		LocalTime ltBegin = spinnerBegin.getValue();
		Calendar cal = Calendar.getInstance();
		cal.set(ldBegin.getYear(), ldBegin.getMonthValue(), ldBegin.getDayOfMonth(), ltBegin.getHour(), ltBegin.getMinute(), 0);
		return cal.getTime();
	}
	
	private boolean isInputValid() {
		boolean error = false;
		String content = "";
		if(txtComment.getText() == null || txtComment.getText().isEmpty()) {
			content += "Please enter a comment\n";
			error = true;
		}
		if(comboTasks.getValue() == null) {
			content += "Please select a task\n";
			error = true;
		}
		if(dateBegin.getValue() == null || spinnerBegin.getValue() == null) {
			content += "Please select a begin time \n";
			error = true;
		}
		if(dateEnd.getValue() == null || spinnerEnd.getValue() == null) {
			content += "Please select a end time \n";
			error = true;
		}
		
		if(error) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(mainApp.getStage());
			alert.setTitle("Invalid fields");
			alert.setHeaderText("Please fill in the fields");
			alert.setContentText(content);
			
			alert.showAndWait();
			return false;
		}
		return true;
	}

	public void setMainApp(MainApp app) {
		mainApp = app;
	}

}
