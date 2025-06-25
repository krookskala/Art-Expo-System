
# Art Expo System - A Full-Stack Gallery Management Application

Welcome to the **Art Expo System**, a complete, full-stack application designed to manage and browse a sophisticated art gallery. This repository contains a multi-module project that demonstrates a modern approach to Java development, combining a powerful backend API with a responsive desktop GUI client.

This client provides a rich, user-friendly interface for browsing the gallery's exhibitions and artworks, demonstrating best practices for building robust and asynchronous desktop applications in Java.

![Image](https://github.com/user-attachments/assets/e853b3f6-84e9-448c-a1ef-e1bcb8337563)

![Image](https://github.com/user-attachments/assets/47a0c847-7ad3-4ccb-aca4-36648beff0f0)

![Image](https://github.com/user-attachments/assets/3092452a-a14f-4691-a5b5-9df1fd458f3d)

![Image](https://github.com/user-attachments/assets/b83888c0-d625-40b6-9b7a-91b6274cde36)

## Table Of Contents

- [Project Overview](#project-overview)
- [Getting Started](#getting-started)
- [Detailed File Descriptions](#usage)
- [Contributing](#contributing)
- [Links](#links)
- [License](#license)

## Project Overview

This system is architected as two separate but connected applications:

- **Backend API `(/artgallery)`:** A robust RESTful service built with **Spring Boot** and **JPA/Hibernate**. It handles all business logic, data persistence, and complex domain modeling. It acts as the single source of truth for the entire system.

- **Frontend GUI `(/artgallerygui)`:** A modern desktop client built with **JavaFX**. It provides a user-friendly interface for interacting with the backend, focusing on a responsive and intuitive user experience.#


This decoupled architecture allows for independent development, testing, and deployment of the server and client components.

## Key Technologies

| Area      | Technology                                    |
| :-------- | :-------------------------------------------- |
| **Backend**   | Java 17, Spring Boot 3, Spring Data JPA, Hibernate, H2 Database |
| **Frontend**  | Java 17, JavaFX 17, FXML, AtlantaFX Theme       |
| **Build**     | Apache Maven                                  |
| **API**       | REST, JSON (with Jackson)                     |

---

## Getting Started

To run the entire system, you must start the backend server first, followed by the frontend GUI client.

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

## 🧾 Project Structure & Documentation

This repository contains two main projects, each with its own detailed README.md file. For more in-depth information about the architecture, features, and file structure of each component, please refer to their respective documentation.

### 📂 /artgallery - Backend API
The backend server project. It contains all the domain models, business logic, and API endpoints.

[➡️ Click here to view the detailed Backend](https://github.com/krookskala/Art-Expo-System/tree/main/art-gallery)

---

### 📂 /artgallerygui - Frontend GUI
The JavaFX desktop client project. It contains all the FXML views, controllers, and logic for communicating with the backend.

[➡️ Click here to view the detailed Frontend](https://github.com/krookskala/Art-Expo-System/tree/main/ArtGalleryGUI)

---

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
