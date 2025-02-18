// **********************************************************************************
// Title: TubeFlow
// Author: Ayush Regmi
// Course Section: CMIS201-ONL2 (Seidel) Spring 2024
// File: NewProjectController.java
// Description: This file contains the NewProjectController class, which manages the user interface for creating
//              new projects in TubeFlow. It provides methods to display a form where users can input information
//              about their new project. Once the user fills out the form, the controller handles sending this data
//              to the backend system and saves it in a JSON file for storage and retrieval purposes.
// **********************************************************************************

package com.ayushrg.tubeflowx.controller;

import com.ayushrg.tubeflowx.model.Project;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller class for the new project creation view in TubeFlow application.
 */
public class NewProjectController implements Initializable {
    @FXML
    private TextField title;
    @FXML
    private TextArea description;
    @FXML
    private ComboBox<Project.ProjectStatus> projectStatus;
    @FXML
    private TextField tags;
    @FXML
    private TextArea script;

    // Flag to track if a project is created successfully
    public static boolean projectCreated = false;


    /**
     * Initializes the NewProjectController and sets up the project status ComboBox.
     *
     * @param url            The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources for the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (projectStatus == null) {
            System.out.println("ProjectStatus ComboBox is null!");
        } else {
            projectStatus.getItems().setAll(Project.ProjectStatus.values());
        }
    }

    /**
     * Clears the form fields.
     */
    private void clearForm() {
        title.clear();
        description.clear();
        projectStatus.getSelectionModel().clearSelection();
        tags.clear();
        script.clear();
    }


    /**
     * Handles the action when the "Create New Project" button is clicked.
     */
    @FXML
    private void createNewProject() {
        try {
            String filename = System.getProperty("user.dir") + "/src/main/java/com/ayushrg/tubeflowx/data/Projects.json";
            String tagString = tags.getText();
            List<String> tagsList = new ArrayList<>();

            if (!tagString.isEmpty()) {
                tagsList = Arrays.asList(tagString.split(",\\s*"));  // Split tags by commas and optional whitespace
            }
            Project newProject = new Project(
                    title.getText(),
                    description.getText(),
                    projectStatus.getValue(),
                    tagsList,
                    script.getText()
            );

            Project.createProject(newProject, filename);

            JSONObject readProject = Project.readProject(newProject.getProjectId(), filename);
            if (readProject != null) {
                System.out.println("Project found: " + readProject.toString());
            }

            finishCreatingProject();

            // Show success alert
            showAlert("Success", "Project created successfully!", Alert.AlertType.INFORMATION);
            // Optionally, close the form here if desired
            closeForm();


            // Close dialog and refresh main UI...
        } catch (IOException e) {
            // Handle exceptions, could show an alert dialog here
            e.printStackTrace();
            System.out.println(e.getMessage());
            // Show error alert
            showAlert("Error", "Failed to create the project: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Sets the projectCreated flag to true.
     */
    public void finishCreatingProject() {
        projectCreated = true;
    }


    /**
     * Handles the action when the "Cancel" button is clicked.
     */
    @FXML
    private void cancelProject() {
        // Close the dialog without doing anything
        closeForm();
    }

    /**
     * Closes the form window.
     */
    private void closeForm() {
        Stage stage = (Stage) title.getScene().getWindow();
        stage.close();
    }

    /**
     * Displays an alert dialog with the specified title, content, and type.
     *
     * @param title   The title of the alert.
     * @param content The content of the alert.
     * @param type    The type of the alert (INFORMATION, ERROR, etc.).
     */
    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

