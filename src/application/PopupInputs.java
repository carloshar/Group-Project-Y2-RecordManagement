package application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

public class PopupInputs {
	
	public GridPane grid = new GridPane();
	public ChoiceBox<String> choice1 = new ChoiceBox<String>();
	public ChoiceBox<String> choice2 = new ChoiceBox<String>();
	String viewID = "";
	
	private ChoiceBox<String> populateChoiceBox(ArrayList<String> data) {
		ChoiceBox<String> choiceBox = new ChoiceBox<String>(
				FXCollections.observableArrayList(data)
			);
		
		return choiceBox;
	}
	
	private ArrayList<String> setGeneration (Pair<String, SQLTable> table) {
		ResultSet resultSet = table.getValue().findAll();
		
		ArrayList<String> set = new ArrayList<String>();
		
		try {
			while (resultSet.next()) {
				String label = resultSet.getString(1) + "-" + resultSet.getString(2);
				set.add(label);
			}
		} catch (SQLException e) {
			System.out.println("Error: " + e);
		}
		
		return set;
	}
	
	public void placeChoiceBoxes(ArrayList<String> set1, ArrayList<String> set2, ChoiceBox<String> choice1, ChoiceBox<String> choice2) {
		choice1 = populateChoiceBox(set1); 
		choice2 = populateChoiceBox(set2); 
		grid.add(choice1, 1, 1);
		grid.add(choice2, 4, 1);
	}
	
	private void setId(String id) {
		viewID = id;
	}
	
	public String displayStudents(String studentName, SQLTable connection) {
		try {
			ResultSet studentSet = connection.findAllWhere("firstname", studentName);
			Dialog<ArrayList<String>> dialog = new Dialog<ArrayList<String>>();
			dialog.setTitle("Search Results");
			dialog.setHeaderText("Results for students called " + studentName);
			
			ButtonType selectButtonType = new ButtonType("View Selected");
			
			dialog.getDialogPane().getButtonTypes().addAll(selectButtonType, ButtonType.CANCEL);
			
			ArrayList<Label> studentIDList = new ArrayList<Label>();
			ArrayList<Label> studentNameList = new ArrayList<Label>();
			ArrayList<Button> studentButtonList = new ArrayList<Button>();
			ArrayList<RadioButton> studentRadioButtonList = new ArrayList<RadioButton>();
			
			GridPane layout = new GridPane();
			layout.setHgap(10);
			layout.setVgap(10);
			layout.setPadding(new Insets(20, 150, 10, 10));
			
			final ToggleGroup selectedStudent = new ToggleGroup();
			
			int index = 0;
			while (studentSet.next()) {
				Label id = new Label(studentSet.getString(1));
				studentIDList.add(id);
				Label name = new Label(studentSet.getString(4));
				studentNameList.add(name);
				Button search = new Button("Select");
				
				search.setOnAction(new EventHandler<ActionEvent>() {
				    @Override public void handle(ActionEvent e) {
				        Button source = (Button) e.getSource();
				    	for (int x = 0; x < studentRadioButtonList.size(); x++) {
				    		if (studentRadioButtonList.get(x).getId().equals(source.getId())) {
				    			studentRadioButtonList.get(x).setSelected(true);
				    		}
				    	}
				        setId(source.getId());
				    }
				});
				
				search.setId(id.getText());
				studentButtonList.add(search);
				RadioButton selectedRB = new RadioButton();
				selectedRB.setToggleGroup(selectedStudent);
				selectedRB.setId(id.getText());
				selectedRB.setMouseTransparent(true);
				studentRadioButtonList.add(selectedRB);
				layout.add(id, 0, index);
				layout.add(name, 1, index);
				layout.add(search, 2, index);
				layout.add(selectedRB, 3, index);
				index++;
			}
			
			dialog.getDialogPane().setContent(layout);
			
			dialog.setResultConverter(dialogButton -> {
			    if (dialogButton == selectButtonType) {
					if (!viewID.equals("")) {
				    	ArrayList<String> dataExtract = new ArrayList<String>();
				    	dataExtract.add(0, viewID);
				    	
				        return dataExtract;
					} else {
						return null;
					}
			    } else {
			    	return null;
			    }
			});

			
			Optional<ArrayList<String>> result = dialog.showAndWait();
			ArrayList<String> idList = new ArrayList<String>();
			
			result.ifPresent(details -> {
				idList.add(details.get(0));
			});

			return idList.get(0);
			
			
		} catch (Exception e) {
		}
		return null;
		
	}
	
	public Pair<String, String> twinPairDropBox(String title, String promptText, String[] labelLabels, Pair<String, SQLTable> table1, Pair<String, SQLTable> table2) {
		
		Dialog<ArrayList<String>> dialog = new Dialog<>();
		
		dialog.setTitle(title);
		dialog.setHeaderText(promptText);
		
		ButtonType functionButtonType = new ButtonType("Pair", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(functionButtonType, ButtonType.CANCEL);
		
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));
		
		ArrayList<String> set1 = setGeneration(table1);
		ArrayList<String> set2 = setGeneration(table2);
		
		choice1 = populateChoiceBox(set1); 
		choice2 = populateChoiceBox(set2); 
		
		grid.add(new Label(labelLabels[0] + ":"), 0, 0);
		grid.add(choice1, 1, 0);
		grid.add(new Label("<->"), 2, 0);
		grid.add(new Label(labelLabels[1] + ":"), 3, 0);
		grid.add(choice2, 4, 0);
		
		dialog.getDialogPane().setContent(grid);
		
		dialog.setResultConverter(dialogButton -> {
		    if (dialogButton == functionButtonType) {
		    	ArrayList<String> dataExtract = new ArrayList<String>();
		    	dataExtract.add(0, choice1.getValue().toString().split("-")[0]);
		    	dataExtract.add(1, choice2.getValue().toString().split("-")[0]);
		    	
		        return dataExtract;
		    }
		    return null;
		});

		Pair<String, String> submittedResults;
		String[] tempList = new String[2];
		
		Optional<ArrayList<String>> result = dialog.showAndWait();
		
		result.ifPresent(details -> {
			tempList[0] = details.get(0);
			tempList[1] = details.get(1);
		});

		submittedResults = new Pair<String, String>(tempList[0], tempList[1]);
		
		return submittedResults;
	}
	
	public ArrayList<String> inputDialog(String title, String promptText, String buttonPromptText, String[] textFieldKeys, ArrayList<Pair<String, SQLTable>> sqlChoiceBox, Pair<String,String[]> cbOptions) {
		
		Dialog<ArrayList<String>> dialog = new Dialog<>();
		
		dialog.setTitle(title);
		dialog.setHeaderText(promptText);
		
		ButtonType functionButtonType = new ButtonType(buttonPromptText, ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(functionButtonType, ButtonType.CANCEL);
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));
		
		ArrayList<TextField> textFields = new ArrayList<TextField>();
		
		ArrayList<String> allCourses = new ArrayList<String>();
		
		if (sqlChoiceBox != null) {
			ResultSet allCoursesQueryResult = sqlChoiceBox.get(0).getValue().findAll();
			
			try {
				while (allCoursesQueryResult.next()) {
					String choiceCourseText = allCoursesQueryResult.getString(1) + " - " + allCoursesQueryResult.getString(2);
					allCourses.add(choiceCourseText);
				}
			} catch (SQLException e) {
				System.out.println("Error: " + e);
			}
		}
		
		ChoiceBox<String> tableChoiceBox = new ChoiceBox<String>(
			FXCollections.observableArrayList(allCourses)
		);
		
		ArrayList<String> predefinedChoiceStrings = new ArrayList<String>();
		
		if (cbOptions != null) {
			for (int a=0; a < cbOptions.getValue().length; a++) {
				predefinedChoiceStrings.add(a, cbOptions.getValue()[a]);
			}
		}
		
		ChoiceBox<String> predefinedChoiceBox = new ChoiceBox<String>(
				FXCollections.observableArrayList(predefinedChoiceStrings)
			);
		
		for (int i=0; i<textFieldKeys.length; i++) {
			TextField textField = new TextField();
			textField.setPromptText(textFieldKeys[i]);
			textFields.add(i, textField);
			grid.add(new Label(textField.getPromptText() + ":"), 0, i);
			grid.add(textField, 1, i);
			
			if (i+1 == textFieldKeys.length) {
				i++;
				if (sqlChoiceBox != null) {
					grid.add(new Label(sqlChoiceBox.get(0).getKey() + ":"), 0, i);
					grid.add(tableChoiceBox, 1, i);
					i++;
				} else if (cbOptions != null) {
					grid.add(new Label(cbOptions.getKey() + ":"), 0, i);
					grid.add(predefinedChoiceBox, 1, i);
				}
			}
		}

		dialog.getDialogPane().setContent(grid);
		
		dialog.setResultConverter(dialogButton -> {
		    if (dialogButton == functionButtonType) {
		    	ArrayList<String> moduleDataExtract = new ArrayList<String>();
		    	int index = 0;
		    	for (int i = 0; i<textFields.size(); i++) {
			    	moduleDataExtract.add(index, textFields.get(i).getText());
			    	index++;
		    	}
		    	if (sqlChoiceBox != null) {
			    	moduleDataExtract.add(index, tableChoiceBox.getValue());
			    	index++;
		    	}
		    	if (cbOptions != null) {
			    	moduleDataExtract.add(index, predefinedChoiceBox.getValue());
			    	index++;
		    	}
		    	
		        return moduleDataExtract;
		    }
		    return null;
		});
		
		ArrayList<String> moduleDetailArray = new ArrayList<String>();
		
		Optional<ArrayList<String>> result = dialog.showAndWait();
		
		result.ifPresent(courseDetails -> {
			for (int i = 0; i<courseDetails.size(); i++) {
				moduleDetailArray.add(i, courseDetails.get(i));
			}
		});
		
		return moduleDetailArray;
	}

}
