package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TimetableController {
	
	@FXML private TableView<Timetable> courseTable;
	@FXML private TableColumn<Timetable, String> timeColumn;
	@FXML private TableColumn<Timetable, String> mondayColumn;
	@FXML private TableColumn<Timetable, String> tuesdayColumn;
	@FXML private TableColumn<Timetable, String> wednesdayColumn;
	@FXML private TableColumn<Timetable, String> thursdayColumn;
	@FXML private TableColumn<Timetable, String> fridayColumn;
	@FXML private TableColumn<Timetable, String> saturdayColumn;
	@FXML private TableColumn<Timetable, String> sundayColumn;
	
	@FXML
	protected void initialize()  {
		load();
	}

	@FXML
	public void load() {
		timeColumn.setCellValueFactory(new PropertyValueFactory<Timetable, String>("time"));
		mondayColumn.setCellValueFactory(new PropertyValueFactory<Timetable, String>("mondaySlot"));
		tuesdayColumn.setCellValueFactory(new PropertyValueFactory<Timetable, String>("tuesdaySlot"));
		wednesdayColumn.setCellValueFactory(new PropertyValueFactory<Timetable, String>("wednesdaySlot"));
		thursdayColumn.setCellValueFactory(new PropertyValueFactory<Timetable, String>("thursdaySlot"));
		fridayColumn.setCellValueFactory(new PropertyValueFactory<Timetable, String>("fridaySlot"));
		saturdayColumn.setCellValueFactory(new PropertyValueFactory<Timetable, String>("saturdaySlot"));
		sundayColumn.setCellValueFactory(new PropertyValueFactory<Timetable, String>("sundaySlot"));
		
		//2D array that should contain all of the data to display in the table
		String[][] dataArray = new String[][] {
			{"CSY2028", "", "CSY2003", "", "", "CSY2001", "CSY2016"}, 
			{"CSY2006", "", "", "", "", "CSY2001", "CSY1026"}, 
			{"", "CSY2003", "", "CSY2016", "", "", ""}, 
			{"", "", "", "", "", "CSY2001", "CSY2028"}, 
			{"CSY2001", "", "CSY2016", "", "", "", "CSY2028"}
		};
		
		courseTable.getItems().setAll(getData(dataArray));
	}
	
	//Takes the data provided and adds it to a List object of type specified within the angled brackets <>
	private List<Timetable> getData(String[][] dataArray) {
		List<Timetable> x = new ArrayList<Timetable>();
		int hour = 9;
		
		for (int j = 0; j < dataArray.length; j++) {
			String[] data = dataArray[j];
			Timetable y = new Timetable(hour + ":00", data);
			x.add(j, y);
			hour++;
		}

		return x;
	}
	
	
	public static class Timetable {
		private final String time;
		private final String mondaySlot;
		private final String tuesdaySlot;
		private final String wednesdaySlot;
		private final String thursdaySlot;
		private final String fridaySlot;
		private final String saturdaySlot;
		private final String sundaySlot;
		
		private Timetable(String time, String[] timetableSlots) {
			this.time = new String(time);
			this.mondaySlot = new String(timetableSlots[0]);
			this.tuesdaySlot = new String(timetableSlots[1]);
			this.wednesdaySlot = new String(timetableSlots[2]);
			this.thursdaySlot = new String(timetableSlots[3]);
			this.fridaySlot = new String(timetableSlots[4]);
			this.saturdaySlot = new String(timetableSlots[5]);
			this.sundaySlot = new String(timetableSlots[6]);
		}
		
		public String getTime() {
			return time;
		}
		
		public String getMondaySlot() {
			return mondaySlot;
		}
		
		public String getTuesdaySlot() {
			return tuesdaySlot;
		}
		
		public String getWednesdaySlot() {
			return wednesdaySlot;
		}
		
		public String getThursdaySlot() {
			return thursdaySlot;
		}
		
		public String getFridaySlot() {
			return fridaySlot;
		}
		
		public String getSaturdaySlot() {
			return saturdaySlot;
		}
		
		public String getSundaySlot() {
			return sundaySlot;
		}
	}
}
