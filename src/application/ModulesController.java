package application;

import java.util.ArrayList;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Pair;

public class ModulesController {
	
	@FXML Label departmentName;
	
	@FXML Accordion coursePanel;
	
	private String[] addCourseDialog() {
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("Add Course");
		dialog.setHeaderText("Add a course");
		
		ButtonType addButtonType = new ButtonType("Add", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));
		
		TextField courseName = new TextField();
		courseName.setPromptText("Course Name");
		TextField courseID = new TextField();
		courseID.setPromptText("Course ID");

		grid.add(new Label("Course Name:"), 0, 0);
		grid.add(courseName, 1, 0);
		grid.add(new Label("Course ID:"), 0, 1);
		grid.add(courseID, 1, 1);

		dialog.getDialogPane().setContent(grid);
		
		dialog.setResultConverter(dialogButton -> {
		    if (dialogButton == addButtonType) {
		        return new Pair<>(courseName.getText(), courseID.getText());
		    }
		    return null;
		});

		Optional<Pair<String, String>> result = dialog.showAndWait();
		
		String[] courseDetailArray = new String[2];

		result.ifPresent(courseDetails -> {
			
			courseDetailArray[0] = courseDetails.getKey();
			courseDetailArray[1] =  courseDetails.getValue();
		});
		return courseDetailArray;
	}
	
	private String[] addModuleDialog() {

		Dialog<ArrayList<String>> dialog = new Dialog<>();
		
		dialog.setTitle("Add Module");
		dialog.setHeaderText("Add a module");
		
		ButtonType addButtonType = new ButtonType("Add", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));
		
		TextField moduleName = new TextField();
		moduleName.setPromptText("Module Name");
		TextField moduleID = new TextField();
		moduleID.setPromptText("Module ID");
		
		//Should generate courses from database readout
		ChoiceBox<String> moduleCourse = new ChoiceBox<String>(
				FXCollections.observableArrayList("First", "Second", "Third")
				);

		grid.add(new Label("Module Name:"), 0, 0);
		grid.add(moduleName, 1, 0);
		grid.add(new Label("Module ID:"), 0, 1);
		grid.add(moduleID, 1, 1);
		grid.add(new Label("Course:"), 0, 2);
		grid.add(moduleCourse, 1, 2);

		dialog.getDialogPane().setContent(grid);
		
		dialog.setResultConverter(dialogButton -> {
		    if (dialogButton == addButtonType) {
		    	ArrayList<String> moduleDataExtract = new ArrayList<String>();
		    	moduleDataExtract.add(0, moduleName.getText());
		    	moduleDataExtract.add(1, moduleID.getText());
		    	moduleDataExtract.add(2, moduleCourse.getValue());
		    	
		        return moduleDataExtract;
		    }
		    return null;
		});

		String[] moduleDetailArray = new String[3];
		
		Optional<ArrayList<String>> result = dialog.showAndWait();
		
		result.ifPresent(courseDetails -> {
			
			moduleDetailArray[0] = courseDetails.get(0);
			moduleDetailArray[1] =  courseDetails.get(1);
			moduleDetailArray[2] =  courseDetails.get(2);
		});
		
		return moduleDetailArray;
		
	}
	
	//Temporarily passing in course details but should get all from database
	public void drawCourses(String[] courseDetails) {
		coursePanel.getPanes().clear();
		//Will read from database and extract information on courses and modules
		TitledPane course;
		AnchorPane modulesDropDownContainer = new AnchorPane();
		Label module = new Label("Module Name");
		Button delete = new Button("Delete");
		Button amend = new Button("Amend");
		HBox moduleContainer = new HBox();
		moduleContainer.setSpacing(10);
		moduleContainer.getChildren().addAll(module, delete, amend);
		modulesDropDownContainer.getChildren().add(0, moduleContainer);
		course = new TitledPane(courseDetails[0], modulesDropDownContainer);
		course.setExpanded(false);
		coursePanel.getPanes().add(course);
	}
	
	@FXML
	public void addCourse() {
		
		String[] courseDetails = addCourseDialog(); 
		
		System.out.println(courseDetails[0] + " " + courseDetails[1]);
		
		//Add Course to database
		
		drawCourses(courseDetails);
		
	}
	
	@FXML
	public void addModule() {
		String[] moduleDetails = addModuleDialog();
		System.out.println(moduleDetails[0] + " " + moduleDetails[1] + " " + moduleDetails[2]);
		//Add to database
		
		//Call drawCourses()
	}
	
	@FXML
	public void searchDepartment() {
		
	}

}
