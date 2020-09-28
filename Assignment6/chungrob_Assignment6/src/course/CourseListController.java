/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package course;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.InputMismatchException;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Course;

/**
 * FXML Controller class
 *
 * @author karen
 */
public class CourseListController implements Initializable {

    @FXML
    private ComboBox<Course> ddlCourses;
    @FXML
    private TextField txtCode;
    @FXML
    private TextField txtGrade;
    @FXML
    private TextField txtMaxGrade;

    private ObservableList<Course> olCourses;

    @FXML
    private Label lblStatus;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        olCourses = FXCollections.observableArrayList();
        loadRecord();
        ddlCourses.setItems(olCourses);
        ddlCourses.getSelectionModel().selectedItemProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable o) {
                Course selection = ddlCourses.getSelectionModel().getSelectedItem();
                txtCode.setText(selection.getCode());
                String s = "";
                txtGrade.setText(s + selection.getGrade());
                txtMaxGrade.setText(s + selection.getMaxGrade());
                lblStatus.setText("");
            }
        });
    }

    /**
     * Method reads the records in the CourseList text file, then populates an
     * observable array list of the course objects
     */
    private void loadRecord() {
        try {
            File file = new File("src/res/CourseList.txt");
            Scanner fileIn = new Scanner(file);
            String courseCode;
            double grade, maxGrade;
            String line;
            while (fileIn.hasNext()) {
                line = fileIn.nextLine();
                String[] token = line.split(",");
                grade = Double.parseDouble(token[1]);
                maxGrade = Double.parseDouble(token[2]);
                olCourses.add(new Course(token[0], grade, maxGrade));
            }

            fileIn.close();
        } catch (IOException ioe) {
            System.out.println("Error: File not Found!");
        }
    }

    @FXML
    private void edit(ActionEvent event) {
        File f = new File("src/res/CourseList.txt");
        try {
            PrintWriter fileOut = new PrintWriter(new BufferedWriter(new FileWriter(f, true)));
            Course course = readTextFields();
            lblStatus.setText("Successfully edited");
            fileOut.println(course.toString() + "," + course.getGrade() + "," + course.getMaxGrade());
            olCourses.add(course);
            fileOut.close();
        } catch (FileNotFoundException fnf) {
            System.out.println("Error");
            lblStatus.setText("Error");
        } catch (IOException ioe) {
            System.out.println("Error");
            lblStatus.setText("Error");
        } catch (NumberFormatException nme) {
            System.out.println("Grade must be a valid number!");
            lblStatus.setText("Grade must be a valid number!");
        } catch (IllegalArgumentException iae) {
            System.out.println(iae.getMessage());
            lblStatus.setText(iae.getMessage());
        }
    }

    @FXML
    private void exit(ActionEvent event) {
//        fileOut.close();
        System.exit(0);
    }

    /**
     * Method for reading data in text fields then constructing a course object
     * from the data
     *
     * @return a course object containing the values from text fields as
     * properties
     */
    private Course readTextFields() {
        String code = txtCode.getText();
        double grade = Double.parseDouble(txtGrade.getText());
        double maxGrade = Double.parseDouble(txtMaxGrade.getText());
        Course course = new Course(code, grade, maxGrade);

        return course;
    }
}
//        String numRegex = "\\b\\d+.?\\d+\\b"; // must be any number that is positive
//        String code;
//        double grade = 0.0, maxGrade = 0.0;
//        boolean isValid = true;
//
//        try {
////            fWriter = new FileWriter(f, true);
////            bw = new BufferedWriter(fWriter);
////            fileOut = new PrintWriter(bw);
//            fileOut = new PrintWriter(new BufferedWriter(new FileWriter(f, true)));
//            
//            
//            code = txtCode.getText();
//
//            String strGrade = txtGrade.getText();
//            if (!strGrade.matches(numRegex)) {
//                lblStatus.setText("Invalid Data");
//                isValid = false;
//            } else {
//                grade = Double.parseDouble(txtGrade.getText());
//            }
//
//            String strMaxGrade = txtMaxGrade.getText();
//            if (!strMaxGrade.matches(numRegex)) {
//                lblStatus.setText("Invalid Data");
//                isValid = false;
//            } else {
//                maxGrade = Double.parseDouble(txtMaxGrade.getText());
//            }
//
//            if (isValid) {
//                lblStatus.setText("Successfully edited");
//                course = new Course(code, grade, maxGrade);
//                fileOut.println(course.toString() + "," + course.getGrade() + "," + course.getMaxGrade());
//                olCourses.add(course);
//                
//            }
//        } catch (IOException ioe) {
//            System.out.println("File not Found");
//        }
