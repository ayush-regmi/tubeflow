// **********************************************************************************
// Title: TubeFlow
// Author: Ayush Regmi
// Course Section: CMIS201-ONL2 (Seidel) Spring 2024
// File: ProjectDetailController.java
// Description: This file contains the ProjectDetailController class, which displays all the details of specific selected project
//                from Kanban board, and it also allows the user to update the data
// **********************************************************************************

package com.ayushrg.tubeflowx.controller;

import com.ayushrg.tubeflowx.model.Project;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Arrays;

/**
 * Controller class to display the project details and also update the data TubeFlow application.
 */
public class ProjectDetailController {
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

    private Project currentProject;

    // Flag to track if a project is created successfully
    public static boolean projectUpdated = false;


    public void setCurrentProject(Project project) {
        this.currentProject = project;
        loadProjectDetails();
    }

    /**
     * Initializes the NewProjectController and sets up the project status ComboBox.
     */
    public void initialize() {
        projectStatus.getItems().setAll(Project.ProjectStatus.values()); // Assuming you have a ProjectStatus enum in Project
    }


    private void loadProjectDetails() {
        title.setText(currentProject.getTitle());
        description.setText(currentProject.getDescription());
        projectStatus.setValue(Project.ProjectStatus.fromString(currentProject.getProjectStatus()));  // Use fromString to correctly convert
        tags.setText(String.join(", ", currentProject.getTags()));
        script.setText(currentProject.getScript());
    }


    /**
     * Updates the current project's details based on user input in the form and saves
     * these changes to the `Projects.json` file.
     *
     * The method retrieves the project's title, description, status, tags, and script
     * from the form fields, updates the current project, and calls `Project.updateProject`
     * to write these changes to the JSON file.
     *
     * If the update succeeds, it sets `projectUpdated` to `true` and closes the form.
     * If an error occurs, it displays an alert with the failure message.
     *
     **/
    @FXML
    private void updateProject() {
        String fileName = System.getProperty("user.dir") + "/src/main/java/com/ayushrg/tubeflowx/data/Projects.json";
        try {
            currentProject.setTitle(title.getText());
            currentProject.setDescription(description.getText());
            currentProject.setProjectStatus(String.valueOf(projectStatus.getValue()));
            currentProject.setTags(Arrays.asList(tags.getText().split(",\\s*")));
            currentProject.setScript(script.getText());

            Project.updateProject(currentProject.getProjectId(), fileName, currentProject.toJson());
            projectUpdated = true;
            closeForm();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to update the project: " + e.getMessage());
        }
    }

    @FXML
    private void cancelProject() {
        closeForm();
    }

    /**
     * Closes the page.
     */
    private void closeForm() {
        Stage stage = (Stage) title.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String content) {
        // Implementation of alert dialogs in JavaFX
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // Change to AlertType.ERROR or AlertType.WARNING if needed
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait(); // This will block until the user closes the alert
    }
}
