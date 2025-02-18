// **********************************************************************************
// Title: TubeFlow
// Author: Ayush Regmi
// Course Section: CMIS201-ONL2 (Seidel) Spring 2024
// File: KanbanBoard.java
// Description: This file has a class called KanbanBoard which is responsible to represent all the video projects in a Kanban Board
// **********************************************************************************

package com.ayushrg.tubeflowx.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Kanban board in TubeFlow, visually organizing video projects into categories (ToDo, InProgress, Done).
 */
public class KanbanBoard {
    private List<Project> toDoProjects = new ArrayList<>();
    private List<Project> inProgressProjects = new ArrayList<>();
    private List<Project> doneProjects = new ArrayList<>();

    /**
     * Constructs a KanbanBoard and loads projects from a JSON file.
     */
    public KanbanBoard() {
        try {
            loadProjects();
        } catch (IOException e) {
            System.err.println("Error loading projects: " + e.getMessage());
        }
    }

    /**
     * Loads projects from a JSON file and categorizes them into ToDo, InProgress, or Done lists.
     *
     * @throws IOException If an I/O error occurs.
     */
    public void loadProjects() throws IOException {
        toDoProjects.clear();
        inProgressProjects.clear();
        doneProjects.clear();
        try {
            String fileName = System.getProperty("user.dir") + "/src/main/java/com/ayushrg/tubeflowx/data/Projects.json";
            JSONArray projectsJson = Project.loadProjects(fileName);
            for (int i = 0; i < projectsJson.length(); i++) {
                JSONObject projectJson = projectsJson.getJSONObject(i);
                Project project = Project.fromJson(projectJson);
                switch (project.getProjectStatus()) {
                    case "ToDo":
                        toDoProjects.add(project);
                        break;
                    case "InProgress":
                        inProgressProjects.add(project);
                        break;
                    case "Done":
                        doneProjects.add(project);
                        break;
                    default:
                        System.out.println("Unknown status: " + project.getProjectStatus());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    /**
     * Retrieves the list of projects in the ToDo category.
     *
     * @return List of ToDo projects.
     */
    public List<Project> getToDoProjects() {
        return toDoProjects;
    }

    /**
     * Retrieves the list of projects in the InProgress category.
     *
     * @return List of InProgress projects.
     */
    public List<Project> getInProgressProjects() {
        return inProgressProjects;
    }

    /**
     * Retrieves the list of projects in the Done category.
     *
     * @return List of Done projects.
     */
    public List<Project> getDoneProjects() {
        return doneProjects;
    }


    /**
     * Moves a project between categories and updates its status.
     *
     * @param project   The project to move.
     * @param newStatus The new status for the project (ToDo, InProgress, Done).
     * @throws IOException If an I/O error occurs.
     */
    public void moveProject(Project project, String newStatus) throws IOException {
        String fileName = System.getProperty("user.dir") + "/src/main/java/com/ayushrg/tubeflowx/data/Projects.json";
        if (newStatus.equals(project.getProjectStatus())) {
            System.out.println("Project is already in the requested status.");
            return;
        }

        removeFromCurrentStatus(project);
        project.setProjectStatus(newStatus);
        addToNewStatus(project);
        Project.updateProject(project.getProjectId(), fileName, project.toJson());
    }

    /**
     * Removes a project from its current status list.
     *
     * @param project The project to remove.
     */
    private void removeFromCurrentStatus(Project project) {
        switch (project.getProjectStatus()) {
            case "ToDo":
                toDoProjects.remove(project);
                break;
            case "InProgress":
                inProgressProjects.remove(project);
                break;
            case "Done":
                doneProjects.remove(project);
                break;
        }
    }

    /**
     * Adds a project to a new status list.
     *
     * @param project The project to add.
     */
    private void addToNewStatus(Project project) {
        switch (project.getProjectStatus()) {
            case "ToDo":
                toDoProjects.add(project);
                break;
            case "InProgress":
                inProgressProjects.add(project);
                break;
            case "Done":
                doneProjects.add(project);
                break;
        }
    }
}
