package com.mda.planit.view;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import com.mda.planit.MainApp;
import com.mda.planit.model.DeveloperWork;
import com.mda.planit.model.Sprint;
import com.mda.planit.model.Task;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.converter.LocalTimeStringConverter;

public class MyWorkController {
	private MainApp mainApp;
	private Sprint currentSprint;

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

	@FXML
	private TableView<DeveloperWork> tableGlobalDevTasks;
	@FXML
	private TableColumn<DeveloperWork, String> columnTaskName;
	@FXML
	private TableColumn<DeveloperWork, String> columnTaskBeginDate;
	@FXML
	private TableColumn<DeveloperWork, String> columnTaskEndDate;
	@FXML
	private TableColumn<DeveloperWork, String> columnTaskComment;

	public MyWorkController() {
	}

	@FXML
	private void initialize() {
		columnTaskName.setCellValueFactory(cd -> cd.getValue().getTask().nameProperty());
		columnTaskBeginDate.setCellValueFactory(cd -> cd.getValue().beginDateStringProperty());
		columnTaskEndDate.setCellValueFactory(cd -> cd.getValue().endDateStringProperty());
		columnTaskComment.setCellValueFactory(cd -> cd.getValue().commentProperty());

		/*
		 * UnaryOperator<TextFormatter.Change> filter = new
		 * UnaryOperator<TextFormatter.Change>() {
		 * 
		 * @Override public TextFormatter.Change apply(TextFormatter.Change t) {
		 * String newText = t.getControlNewText(); try {
		 * LocalTime.parse(newText); return t; } catch(DateTimeParseException
		 * ex) { return null; } } }; spinner.getEditor().setTextFormatter(new
		 * TextFormatter<>(filter));
		 */
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

	public void showGlobalDeveloperTask(Sprint sp) {
		if (sp == null) return;
		comboTasks.setItems(sp.taskProperty());
		if (comboTasks.getItems().size() > 0) comboTasks.getSelectionModel().select(0);

		tableGlobalDevTasks.setItems(sp.developerAllWorkProperty(mainApp.getConnectedDeveloper()));
		currentSprint = sp;
	}

	@FXML
	private void handleSave() {
		if (!isInputValid()) return;

		Task task = comboTasks.getValue();
		String comment = txtComment.getText();
		Date dateBegin = getDateBegin();
		Date dateEnd = getDateEnd();

		task.addDeveloperWork(mainApp.getConnectedDeveloper(), dateBegin, dateEnd, comment);
		tableGlobalDevTasks.setItems(currentSprint.developerAllWorkProperty(mainApp.getConnectedDeveloper()));
		
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
		if (txtComment.getText() == null || txtComment.getText().isEmpty()) {
			content += "Please enter a comment\n";
			error = true;
		}
		if (comboTasks.getValue() == null) {
			content += "Please select a task\n";
			error = true;
		}
		if (dateBegin.getValue() == null || spinnerBegin.getValue() == null) {
			content += "Please select a begin time \n";
			error = true;
		}
		if (dateEnd.getValue() == null || spinnerEnd.getValue() == null) {
			content += "Please select a end time \n";
			error = true;
		}

		if (error) {
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
