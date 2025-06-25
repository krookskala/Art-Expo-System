
# Art Expo System - JavaFX GUI Client

This is the frontend desktop client for the **Art Expo System**. It is a modern, responsive application built with **JavaFX** and **FXML**, designed to interact with the backend **REST API**.

This client provides a rich, user-friendly interface for browsing the gallery's exhibitions and artworks, demonstrating best practices for building robust and asynchronous desktop applications in Java.

![Image](https://github.com/user-attachments/assets/e853b3f6-84e9-448c-a1ef-e1bcb8337563)

![Image](https://github.com/user-attachments/assets/47a0c847-7ad3-4ccb-aca4-36648beff0f0)

![Image](https://github.com/user-attachments/assets/3092452a-a14f-4691-a5b5-9df1fd458f3d)

![Image](https://github.com/user-attachments/assets/b83888c0-d625-40b6-9b7a-91b6274cde36)

## Table Of Contents

- [Project Overview](#project-overview)
- [Features](#features)
- [Getting Started](#getting-started)
- [Detailed File Descriptions](#usage)
- [Contributing](#contributing)
- [Links](#links)
- [License](#license)

## Project Overview

This JavaFX application serves as the user-facing component of the Art Expo System. It is a pure client, meaning it contains no business logic or direct database access. Instead, it communicates with the backend via HTTP requests to fetch and display data.

The application focuses on providing a clean, responsive, and intuitive user experience for browsing complex, related data in a classic Master-Detail format.

## Features

### 🎨 Core Domain Features

- **Master-Detail Interface:** Allows users to select an exhibition from a master list and view its specific details and associated artworks in a separate detail window.
- **Dynamic Data Loading:** All data is fetched live from the backend API upon application start and user interaction.
- **Custom List & Table Views:** Implements custom cell factories for `ListView` and `TableView` to display data in a rich, formatted way.
- **Error Handling:** Displays user-friendly error dialogs if the backend API cannot be reached or returns an error.

### 🚀 User Experience (UX)

- **Asynchronous Operations:** All network requests are performed on background threads, ensuring the UI remains completely responsive and never freezes.
- **Visual Feedback:** Loading indicators are displayed during data fetching operations, clearly communicating the application's status to the user.
- **Ergonomic Controls:** Buttons are intelligently disabled/enabled based on user selection to prevent errors and guide user interaction.
- **Modern Styling:** Utilizes the **AtlantaFX** `PrimerDark` theme for a professional and modern look and feel.

### 🏗️ Architectural Features

- **Separation of Concerns:** Strictly separates the view `(FXML)`, controller `(Java classes)`, and data access `(ApiService)` layers for maintainability.
- **Dedicated API Service:** A single `ApiService` class encapsulates all logic for making HTTP requests and parsing JSON responses, creating a clean and reusable data access layer.
- **Modular Design:** Uses the Java Platform Module System `(module-info.java)` to define clear dependencies and improve encapsulation.

### 🖼️ UI & Architectural Highlights

- **Asynchronous UI Updates:** The use of `new Thread(...)` for network calls and `Platform.runLater(...)` for UI updates is a core feature, demonstrating the correct way to build responsive JavaFX applications.
- **FXML-Driven Views:** All layouts are defined in FXML, allowing the visual design to be completely decoupled from the Java controller logic.
- **Clean Data Flow:** The application follows a clear data flow: `Controller -> ApiService -> Backend API -> ApiService -> Controller -> UI Controls.`

---

## Getting Started

### Prerequisites

Ensure the following tools are installed:

- Java 17+
- Maven 3.8+
- Git
- IDE (IntelliJ IDEA recommended)
- **The Art Expo System Backend must be running** on `localhost:8080`.

### Installation & Running

1. **Clone the repository**
   ```bash
   git clone https://github.com/krookskala/Art-Expo-System

   cd ArtGalleryGUI
   ```
2. **Ensure the Backend is Running**
   Follow the instructions in the backend's README to start the server first.

3. **Run The Application**
    ```bash
   mvn javafx:run
   ```
- The desktop application window will launch and connect to the running backend.

## 🧾 Project Structure

The project is organized following standard JavaFX best practices.

### `src/main/java/edu/pja/mas/s22899/ArtGalleryGUI/`

- **`MainApplication.java`**
  The main entry point of the JavaFX application, responsible for setting up the initial stage and scene.

---
### `dto/`
Contains client-side Data Transfer Objects that mirror the backend's DTOs for easy JSON parsing with Jackson.

- **`ArtworkSummaryDTO.java`** – DTO for representing a summary of an artwork in lists.
- **`ExhibitionDetailDTO.java`** – DTO for representing the full details of a single exhibition.
- **`ExhibitionSummaryDTO.java`** – DTO for representing a summary of an exhibition in lists.


---
### `controller/`
Contains the controller classes that define the logic and event handling for the FXML views.

- **`ExhibitionListController.java`** – Manages the main master view for listing exhibitions.
- **`ExhibitionDetailController.java`** – Manages the detail view for a single exhibition and its artworks.

---
### `service/`

- **`ApiService.java`** – A dedicated service class that handles all HTTP communication with the backend REST API using Java's `HttpClient`.

---

### `src/test/java/edu/pja/mas/s22899/ArtGalleryGUI/`

- **`exhibition-list-view.fxml`** – The FXML file defining the layout for the main exhibition list window.
- **`exhibition-detail-view.fxml`** – The FXML file defining the layout for the exhibition detail window.

---

## Technologies Used
- JavaFX 17
- FXML
- Java 17
- Apache Maven
- Jackson Databind (for JSON parsing)
- AtlantaFX (for UI theme)

## Contributing

Contributions are welcome!

If you find any issues or have ideas for improvements, feel free to open an issue or submit a pull request.

Please make sure to follow the project's code of conduct.

1. **Fork the repository**
2. **Create your feature branch (git checkout -b feature/YourFeature)**
3. **Commit your changes (git commit -am 'Add some feature')**
4. **Push to the branch (git push origin feature/YourFeature)**
5. **Open a pull request**



## Links

[![Gmail](https://img.shields.io/badge/ismailsariarslan7@gmail.com-D14836?style=for-the-badge&logo=gmail&logoColor=white)](ismailsariarslan7@gmail.com)

[![instagram](https://img.shields.io/badge/Instagram-E4405F?style=for-the-badge&logo=instagram&logoColor=white)](https://www.instagram.com/ismailsariarslan/)

[![linkedin](https://img.shields.io/badge/linkedin-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/ismailsariarslan/)
## License

The code in this repository is licensed under the [MIT License.](https://github.com/krookskala/Art-Expo-System/blob/main/LICENSE)
