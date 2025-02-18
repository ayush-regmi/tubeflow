// **********************************************************************************
// Title: TubeFlow
// Author: Ayush Regmi
// Course Section: CMIS201-ONL2 (Seidel) Spring 2024
// File: Help.java
// Description: This file has a class called Help which is responsible to help users navigate through the app
// **********************************************************************************

package com.ayushrg.tubeflowx.model;
public class Help {
    public Help() {
    }

    public static void about() {
        System.out.println("\nDeclaimer: This is only the backend part, Frontend feature is still to be developed\n");
        System.out.println("\nYouTube Content Management System (YT-CMS)");
        System.out.println("This application helps content creators manage their YouTube video projects.");
        System.out.println("Features include project management, Kanban board for tracking progress,");
        System.out.println("and handling of content metadata.\n");
    }

    public static void usage() {
        System.out.println("\nHow to Use YT-CMS");
        System.out.println("-----------------");
        System.out.println("1. Create a Project: Manage your YouTube video by adding it to the system.");
        System.out.println("2. Update a Project: Modify details of your existing projects.");
        System.out.println("3. Move Projects on Kanban: Shift projects between To Do, In Progress, and Done statuses.");
        System.out.println("4. Display Kanban Board: Visualize the progress of all projects on the Kanban board.");
        System.out.println("5. Save Projects: Ensure all your project changes are saved to disk.\n");
    }

    public static void troubleshooting() {
        System.out.println("\nTroubleshooting Common Issues");
        System.out.println("----------------------------");
        System.out.println("Ensure that all project fields are correctly filled out before saving.");
        System.out.println("Check the console output for error messages if operations fail.");
        System.out.println("Make sure the 'Projects.json' file is accessible and not corrupt.");
        System.out.println("Contact support if you encounter persistent issues.\n");
    }
}
