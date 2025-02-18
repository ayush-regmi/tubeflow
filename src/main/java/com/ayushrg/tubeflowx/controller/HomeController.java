// **********************************************************************************
// Title: TubeFlow
// Author: Ayush Regmi
// Course Section: CMIS201-ONL2 (Seidel) Spring 2024
// File: HomeController.java
// Description: This file has a class called HomeController which contains all the methods
//              to connect our UI and backend logic for our home page
// **********************************************************************************

package com.ayushrg.tubeflowx.controller;

import com.ayushrg.tubeflowx.MainApp;
import com.ayushrg.tubeflowx.model.KanbanBoard;
import com.ayushrg.tubeflowx.model.Project;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;


/**
 * Controller class for the home view in TubeFlow application.
 */
public class HomeController {
    @FXML
    private VBox ToDoVBox;
    @FXML
    private VBox InProgressVBox;
    @FXML
    private VBox DoneVBox;
    @FXML
    private Button newProjectBtn, homeBtn, projectBtn, helpBtn;

    private KanbanBoard kanbanBoard;

    /**
     * Initializes the HomeController by loading projects and displaying them on the Kanban board.
     */
    @FXML
    public void initialize() {

        kanbanBoard = new KanbanBoard();
        displayProjects();
    }

    /**
     * Displays projects on the Kanban board.
     */
    private void displayProjects() {
        clearKanbanBoard();
        displayProjectList(kanbanBoard.getToDoProjects(), ToDoVBox);
        displayProjectList(kanbanBoard.getInProgressProjects(), InProgressVBox);
        displayProjectList(kanbanBoard.getDoneProjects(), DoneVBox);
        System.out.println("display projects is on");
    }


    /**
     * Displays a list of projects in a specified VBox container on the Kanban board.
     *
     * @param projects The list of projects to display.
     * @param vBox     The VBox container where projects will be displayed.
     */
    private void displayProjectList(java.util.List<Project> projects, VBox vBox) {
        vBox.getChildren().clear();
        for (Project project : projects) {

            HBox projectItem = new HBox(10);
            projectItem.getStyleClass().add("project-item");

            CheckBox checkBox = new CheckBox();
            Label titleLabel = new Label(project.getTitle());
            titleLabel.getStyleClass().add("project-title");

            checkBox.setOnAction(event -> {
                if (checkBox.isSelected()) {
                    try {
                        moveProjectToNextColumn(project);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            // Set the action when the project title is clicked
            titleLabel.setOnMouseClicked(event -> openProjectDetails(project));

            projectItem.getChildren().addAll(checkBox, titleLabel);
            vBox.getChildren().add(projectItem);
        }
    }


    /**
     * Moves a project to the next column on the Kanban board.
     *
     * @param project The project to move.
     * @throws IOException If an I/O error occurs.
     */
    private void moveProjectToNextColumn(Project project) throws IOException {
        String fileName = System.getProperty("user.dir") + "/src/main/java/com/ayushrg/tubeflowx/data/Projects.json";;
        String newStatus = null;
        switch (project.getProjectStatus()) {
            case "ToDo":
                kanbanBoard.moveProject(project, "InProgress");
                newStatus = "InProgress";
                break;
            case "InProgress":
                kanbanBoard.moveProject(project, "Done");
                newStatus = "Done";
                break;
            case "Done":
                System.out.println("Project is already in 'Done' status.");
                return;
        }
        if(newStatus != null) {
            kanbanBoard.moveProject(project, newStatus);
            Project.updateProjectStatus(project.getProjectId(), fileName, newStatus);
            displayProjects();
        }
    }

    /**
     * Refreshes the Kanban board by reloading projects from the data source.
     *
     * @throws IOException If an I/O error occurs.
     */
    public void refreshKanbanBoard() throws IOException {
        // Re-load the projects from the data source (e.g., JSON file)
        kanbanBoard.loadProjects();
        // Re-display the projects
        displayProjects();
    }

    /**
     * Clears the Kanban board.
     */
    private void clearKanbanBoard() {
        ToDoVBox.getChildren().clear();
        InProgressVBox.getChildren().clear();
        DoneVBox.getChildren().clear();
    }

    /**
     * Opens the create new project window.
     */
    @FXML
    private void createNewProject() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("view/new-project-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            URL cssResource = getClass().getResource("/com/ayushrg/tubeflowx/newProject.css");
            if (cssResource != null) {
                scene.getStylesheets().add(cssResource.toExternalForm());
            } else {
                System.out.println("CSS file not found, please check the path");
                return; // or handle differently
            }

            Stage createProjectStage = new Stage();
            createProjectStage.initModality(Modality.APPLICATION_MODAL); // Blocks interaction with other windows
            createProjectStage.setTitle("Create New Project");
            createProjectStage.setScene(scene);
            createProjectStage.showAndWait(); // Use showAndWait to block interaction with other windows until this one is closed

            // Check the flag after the window has been closed
            if (NewProjectController.projectCreated) {
                refreshKanbanBoard();
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            //showAlert("Error", "Failed to open the new project window: " + e.getMessage(), Alert.AlertType.ERROR);
            // Handle the error appropriately
        }
    }

    /**
     * Opens the project list window.
     */
    @FXML
    private void openProjectList() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("view/project-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            URL cssResource = getClass().getResource("/com/ayushrg/tubeflowx/projectList.css");
            if (cssResource != null) {
                scene.getStylesheets().add(cssResource.toExternalForm());
            } else {
                System.out.println("CSS file not found, please check the path");
                return; // or handle differently
            }
            Stage projectStage = new Stage();
            projectStage.setTitle("Projects");
            projectStage.setScene(scene);
            projectStage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void home() throws IOException {
        // Re-load the projects from the data source (e.g., JSON file)
        kanbanBoard.loadProjects();
        // Re-display the projects
        displayProjects();
    }

    @FXML
    private void help() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("view/help-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            URL cssResource = getClass().getResource("/com/ayushrg/tubeflowx/help.css");
            if (cssResource != null) {
                scene.getStylesheets().add(cssResource.toExternalForm());
            } else {
                System.out.println("CSS file not found, please check the path");
                return; // or handle differently
            }
            Stage projectStage = new Stage();
            projectStage.setTitle("Help");
            projectStage.setScene(scene);
            projectStage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void openProjectDetails(Project project) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("view/project-detail-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            URL cssResource = getClass().getResource("/com/ayushrg/tubeflowx/newProject.css");
            if (cssResource != null) {
                scene.getStylesheets().add(cssResource.toExternalForm());
            } else {
                System.out.println("CSS file not found, please check the path");
                return; // or handle differently
            }

            ProjectDetailController controller = fxmlLoader.getController();
            controller.setCurrentProject(project);  // Set the project on the controller

            Stage createProjectStage = new Stage();
            createProjectStage.initModality(Modality.APPLICATION_MODAL); // Blocks interaction with other windows
            createProjectStage.setTitle("Project Details");
            createProjectStage.setScene(scene);
            createProjectStage.showAndWait(); // Use showAndWait to block interaction with other windows until this one is closed

            // Refresh Kanban board if needed after the dialog is closed
            if (controller.projectUpdated) {
                refreshKanbanBoard();
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            //showAlert("Error", "Failed to open the new project window: " + e.getMessage(), Alert.AlertType.ERROR);
            // Handle the error appropriately
        }
    }


}

