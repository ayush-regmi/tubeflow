# TubeFlow

TubeFlow is a Kanban-based project management tool designed to organize and track tasks efficiently. It features an intuitive drag-and-drop task management system, robust JSON-based data storage, and a modern UI built with JavaFX. This application aims to streamline your workflow and help you manage projects with ease.

---

## Table of Contents

- [Features](#features)
- [Technologies](#technologies)
- [Installation](#installation)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [Configuration](#configuration)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

---

## Features

- **Kanban Board Interface:**  
  Visualize your tasks with distinct columns (e.g., To-Do, In Progress, Done) for clear status tracking.

- **Drag-and-Drop Task Management:**  
  Easily move tasks between columns to update their progress and streamline your workflow.

- **JSON-Based Data Storage:**  
  Persist project and task data in a JSON file, ensuring that your work is saved and portable.

- **Intuitive UI with JavaFX:**  
  Enjoy a modern, responsive, and user-friendly interface designed with JavaFX, making project management visually appealing and efficient.

- **Task Details and Management:**  
  Create, edit, and delete tasks with detailed descriptions, due dates, and additional metadata to keep everything organized.

- **Real-Time Updates:**  
  Changes to tasks are immediately reflected on the board for seamless project tracking.

---

## Technologies

- **Java:**  
  The core programming language used for developing the application's logic and functionality.

- **JSON:**  
  Employed for data storage, enabling lightweight and human-readable project data management.

- **JavaFX:**  
  Used to design and build the modern graphical user interface, ensuring a smooth and engaging user experience.

---

## Installation

### Prerequisites

- **Java Development Kit (JDK):**  
  Ensure that JDK 8 or later is installed on your system.

- **IDE:**  
  Use an IDE such as IntelliJ IDEA, Eclipse, or NetBeans for development.

- **Build Tool:**  
  Maven or Gradle (depending on your project configuration) for dependency management and building the project.

### Steps

1. **Clone the Repository:**

   ```bash
   git clone https://github.com/yourusername/tubeflow.git
   cd tubeflow
   ```

2. **Import the Project:**
   - Open your preferred IDE.
   - Import the project as a Maven or Gradle project.

3. **Build the Project:**
   - **For Maven:**
     ```bash
     mvn clean install
     ```
   - **For Gradle:**
     ```bash
     gradle build
     ```

4. **Run the Application:**
   - Execute the main class to start the application:
     ```bash
     java -jar target/tubeflow.jar
     ```

---

## Usage

- **Creating a New Task:**
  - Click the "Add Task" button within the desired column.
  - Fill in task details in the pop-up form and click "Save" to add the task.

- **Editing a Task:**
  - Double-click on an existing task to open the edit window.
  - Modify task details and save the changes.

- **Drag-and-Drop Functionality:**
  - Click and hold a task to drag it between columns, updating its status in real time.

- **Saving and Loading Projects:**
  - The application automatically saves project data in a JSON file.
  - To load an existing project, use the "Open" option available in the File menu.

---

## Project Structure

```
TubeFlow/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/tubeflow/
│   │   │       ├── Main.java         // Entry point of the application
│   │   │       ├── controllers/      // JavaFX controllers for UI components
│   │   │       ├── models/           // Data models (e.g., Task, Board)
│   │   │       └── utils/            // Utility classes (e.g., JSON handling)
│   │   └── resources/
│   │       └── fxml/               // FXML files for JavaFX UI layout
│   └── test/
│       └── java/                  // Unit tests
├── data/
│   └── projects.json              // JSON storage for project data
├── README.md                      // This file
├── pom.xml / build.gradle         // Build configuration files
└── LICENSE                        // License file
```

---

## Configuration

- **JSON Data Storage:**  
  All project data is saved in the `data/projects.json` file. Ensure your application has proper read/write permissions for this file.

- **JavaFX UI Customization:**  
  The UI can be modified by editing the FXML files in the `src/main/resources/fxml/` directory.

- **Application Settings:**  
  Application-specific settings can be adjusted through configuration files (if provided) within the project.

---

## Contributing

Contributions to TubeFlow are welcome! To contribute:

1. **Fork the Repository:**
   - Click the "Fork" button on the repository page.

2. **Create a Feature Branch:**
   - Run `git checkout -b feature/your-feature` to create a new branch.

3. **Commit Your Changes:**
   - Ensure your commits are clear and descriptive.

4. **Push to Your Branch:**
   - Run `git push origin feature/your-feature`.

5. **Submit a Pull Request:**
   - Open a pull request with a detailed description of your changes.

---

## License

This project is licensed under the [MIT License](LICENSE). Feel free to use, modify, and distribute the software as per the terms outlined in the license.

---

## Contact

For any questions, feedback, or support, please contact:

- **Email:** support@tubeflow.com
- **GitHub Issues:** [TubeFlow Issues](https://github.com/yourusername/tubeflow/issues)

---

TubeFlow is committed to enhancing project management with a user-friendly, efficient, and flexible approach. Happy managing!
