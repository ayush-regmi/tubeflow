// **********************************************************************************
// Title: TubeFlow
// Author: Ayush Regmi
// Course Section: CMIS201-ONL2 (Seidel) Spring 2024
// File: ProjectListController.java
// Description: This file contains the ProjectListController class, which manages the user interface for displaying
//              a list of projects in TubeFlow. When the user navigates to the project section, this controller
//              retrieves project data from the backend and presents it in a structured table view format. Users
//              can view project details, statuses, and titles conveniently through this interface, enhancing
//              project management within TubeFlow.
// **********************************************************************************

package com.ayushrg.tubeflowx.controller;

import com.ayushrg.tubeflowx.model.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Controller class for the project list view in TubeFlow application.
 */
public class ProjectListController {
    @FXML
    private TableView<Project> projectTable;
    @FXML
    private TableColumn<Project, String> statusColumn;
    @FXML
    private TableColumn<Project, String> titleColumn;
    // ObservableList to hold the projects
    private final ObservableList<Project> projectData = FXCollections.observableArrayList();

    /**
     * Initializes the ProjectListController and loads project data into the TableView.
     */
    @FXML
    public void initialize() {
        try {
            loadProjectData();
        } catch (IOException e) {
            System.err.println("Error loading projects: " + e.getMessage());
        }

        setupTableColumns();
    }

    /**
     * Loads project data from a JSON file into the TableView.
     *
     * @throws IOException If an I/O error occurs.
     */
    private void loadProjectData() throws IOException {
        // Get the path to the projects data file
        String fileName = System.getProperty("user.dir") + "/src/main/java/com/ayushrg/tubeflowx/data/Projects.json";
        JSONArray projects = Project.loadProjects(fileName);     // Ensure this static method exists and returns JSONArray
        // Iterate through the JSON array and add projects to projectData ObservableList
        for (int i = 0; i < projects.length(); i++) {
            JSONObject projectJson = projects.getJSONObject(i);
            Project project = Project.fromJson(projectJson);
            projectData.add(project);
        }
        // Set the projectData ObservableList as the items for the TableView
        projectTable.setItems(projectData);
        System.out.println("data loaded successfully");
    }

    /**
     * Sets up the columns in the TableView to link with Project class properties.
     */
    private void setupTableColumns() {
        // Set up the columns to link with the Project class properties
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("projectStatus"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
    }
}
