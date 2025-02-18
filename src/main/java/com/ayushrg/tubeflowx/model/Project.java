// **********************************************************************************
// Title: TubeFlow
// Author: Ayush Regmi
// Course Section: CMIS201-ONL2 (Seidel) Spring 2024
// File: Project.java
// Description: This is the file where all the attributes and methods of a YouTube video project is defined
// **********************************************************************************

package com.ayushrg.tubeflowx.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;


// This class represents a project within the Tubeflow with functionality to manage project data.
public class Project {
    private String projectId; // Unique identifier for each video project
    private String title; // title of the video project.
    private String description; // description of the video project
    private ProjectStatus projectStatus; // current status of the project
    private List<String> tags; // tags associated with the video project
    private String script; // script of the video project

    /**
     * Constructs a new Project with specified details and initializes a unique identifier.
     *
     * @param title         The title of the project.
     * @param description   The description of the project.
     * @param projectStatus The current status of the project.
     * @param tags          Tags associated with the project.
     * @param script        The script associated with the project.
     */
    public Project(String title, String description, ProjectStatus projectStatus, List<String> tags, String script) {
        this.projectId = UUID.randomUUID().toString();  // Unique identifier for each project
        this.title = title;
        this.description = description;
        this.projectStatus = projectStatus;
        this.tags = new ArrayList<>(tags);
        this.script = script;
    }

    /**
     * Converts project details to a JSONObject.
     *
     * @return A JSONObject representing the project.
     */
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("projectId", this.projectId);
        json.put("title", this.title);
        json.put("description", this.description);
        json.put("projectStatus", this.projectStatus.getStatus());
        json.put("tags", new JSONArray(this.tags));
        json.put("script", this.script);
        return json;
    }

    /**
     * Creates a Project instance from a JSONObject.
     *
     * @param json JSONObject containing project data.
     * @return A Project instance.
     */
    public static Project fromJson(JSONObject json) {
        List<String> tags = new ArrayList<>();
        json.getJSONArray("tags").forEach(item -> tags.add((String) item));
        Project project = new Project(
                json.getString("title"),
                json.getString("description"),
                ProjectStatus.fromString(json.getString("projectStatus")),
                tags,
                json.getString("script")
        );
        project.setProjectId(json.getString("projectId"));
        return project;
    }

    /**
     * Saves a JSONArray of projects to a specified file.
     *
     * @param projects JSONArray of projects.
     * @param filename Path of the file to save to.
     * @throws IOException If an I/O error occurs.
     */
    public static void saveProjects(JSONArray projects, String filename) throws IOException {
        try (FileWriter file = new FileWriter(filename)) {
            file.write(projects.toString());
        }
    }

    /**
     * Loads projects from a specified file.
     * @param filename Path to the file to read from.
     * @return JSONArray of projects.
     * @throws IOException If the file cannot be read or does not exist.
     */
    public static JSONArray loadProjects(String filename) throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            return new JSONArray();
        }
        String content = new String(Files.readAllBytes(Paths.get(filename)));
        return new JSONArray(content);
    }

    /**
     * Adds a new project to the project list and saves it to the file.
     * @param project Project to be added.
     * @param filename Path to the file to save to.
     * @throws IOException If an I/O error occurs.
     */
    public static void createProject(Project project, String filename) throws IOException {
        JSONArray projects = loadProjects(filename);
        projects.put(project.toJson());
        saveProjects(projects, filename);
        System.out.println("Project is Created");
    }

    /**
     * Updates an existing project in the project list and saves the changes.
     * @param projectId Identifier of the project to update.
     * @param filename Path to the file to save to.
     * @param newData New data for the project in JSONObject format.
     * @throws IOException If an I/O error occurs or the project is not found.
     */
    public static void updateProject(String projectId, String filename, JSONObject newData) throws IOException {
        JSONArray projects = loadProjects(filename);
        boolean found = false;

        for (int i = 0; i < projects.length(); i++) {
            JSONObject json = projects.getJSONObject(i);
            System.out.println("Current projectId in JSON: " + json.getString("projectId")); // Print each projectId

            if (json.getString("projectId").equals(projectId)) {
                newData.put("projectId", projectId); // Ensure projectId remains the same
                projects.put(i, newData);
                found = true;
                break; // Exit loop once found and updated
            }
        }

        if (found) {
            saveProjects(projects, filename);
            System.out.println("Project is updated");
        } else {
            System.out.println("Project not found while updating");
        }
    }



    /**
     * Deletes a project from the project list based on the project ID and saves the updated list.
     * @param projectId Identifier of the project to delete.
     * @param filename Path to the file to save to.
     * @throws IOException If an I/O error occurs.
     */
    public static void deleteProject(String projectId, String filename) throws IOException {
        JSONArray projects = loadProjects(filename);
        JSONArray updatedProjects = new JSONArray();
        boolean found = false;
        for (int i = 0; i < projects.length(); i++) {
            JSONObject json = projects.getJSONObject(i);
            if (!json.getString("projectId").equals(projectId)) {
                updatedProjects.put(json);
            } else {
                found = true;
            }
        }
        if (found) {
            saveProjects(updatedProjects, filename);
            System.out.println("Project is Deleted");
        } else {
            System.out.println("Project not found");
        }
    }

    /**
     * Retrieves project details from a file based on the project ID.
     * @param projectId Identifier of the project to find.
     * @param filename Path to the file to read from.
     * @return JSONObject representing the project if found, otherwise null.
     * @throws IOException If the project is not found or an I/O error occurs.
     */
    public static JSONObject readProject(String projectId, String filename) throws IOException {
        JSONArray projects = loadProjects(filename);
        for (int i = 0; i < projects.length(); i++) {
            JSONObject json = projects.getJSONObject(i);
            if (json.getString("projectId").equals(projectId)) {
                return json;
            }
        }
        System.out.println("Project not found");
        return null;
    }

    public static void updateProjectStatus(String projectId, String filename, String newStatus) throws IOException {
        JSONArray projects = loadProjects(filename);
        boolean found = false;
        for (int i = 0; i < projects.length(); i++) {
            JSONObject project = projects.getJSONObject(i);
            if (project.getString("projectId").equals(projectId)) {
                project.put("projectStatus", newStatus);
                found = true;
            }
        }
        if (found) {
            saveProjects(projects, filename);
            System.out.println("Project status updated to: " + newStatus);
        } else {
            System.out.println("Project not found!!");
        }
    }



    /**
     * Enumeration for project status types with utility methods to manage status conversions.
     */
    public enum ProjectStatus {
        ToDo("ToDo"), InProgress("InProgress"), Done("Done");

        private final String status;

        ProjectStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }

        public static ProjectStatus fromString(String status) {
            for (ProjectStatus ps : ProjectStatus.values()) {
                if (ps.getStatus().equalsIgnoreCase(status)) {
                    return ps;
                }
            }
            throw new IllegalArgumentException("Invalid status: " + status);
        }
    }

    // Standard getters and setters for project properties
    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = ProjectStatus.valueOf(projectStatus);
    }

    public String getProjectStatus() {
        return projectStatus.getStatus();
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = new ArrayList<>(tags);
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }
}

