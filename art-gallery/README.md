
# Art Expo System – Backend API

The **Art Expo System Backend** is a fully object-oriented, domain-driven application built using **Spring Boot**, **JPA (Hibernate)**, and **Jakarta Validation**. It provides robust functionality for managing artists, curators, artworks, and exhibitions, complete with complex relationships, business rule enforcement, and a clean **RESTful API**.
This system serves as the foundational data and logic layer for the **Art Expo** suite, offering a scalable and maintainable codebase designed to support client applications, exhibition planning, and artwork lifecycle tracking.



## Table Of Contents

- [Project Overview](#project-overview)
- [Features](#features)
- [Getting Started](#getting-started)
- [Detailed File Descriptions](#usage)
- [Contributing](#contributing)
- [Links](#links)
- [License](#license)

## Project Overview

This system models the core business operations of a modern art gallery, including:

- Management of Artists and Curators as specialized subclasses of GalleryMember.
- Scheduling and management of Exhibitions, with distinct logic for permanent and temporary types.
- Cataloging of Artworks, including their creators, genres, and display history.
- Compositional relationships between a Gallery and its physical Sections.
- State-based management of gallery sections (FREE, USED, IN_RENOVATION).
- Tracking artwork display periods using an Association Class (ArtworkInExhibition).

This backend focuses on the correct relational mapping of a complex object-oriented model to a database, implementing modern JPA strategies for inheritance, associations, and persistence.

## Features

### 🎨 Core Domain Features

- **Artists & Curators:** Represented as distinct subclasses inheriting from a common GalleryMember, each with unique attributes and relationships
- **Artworks:** Linked to multiple artists, categorized by a type-safe ArtGenre enum, and tracked via a unique inventory number.
- **Exhibitions:** Modeled with a deep inheritance hierarchy to handle multiple aspects (Permanent/Temporary, Themed/Individual) and polymorphic behavior.
- **Gallery & Sections:** Modeled using a strict composition relationship, where sections cannot exist without a parent gallery.
- **Association with Attributes:** The link between an Artwork and an Exhibition is managed by the ArtworkInExhibition class, which holds additional attributes like displayStartDate and displayEndDate.

### 🔐 Data Validation & Constraints

- **Bean Validation:** Uses **Jakarta Validation** annotations `(@NotBlank, @Size, @Min, @Max)` to enforce data integrity at the model level.
- **Logical Constraints:** Implements `@Transient` validation methods to enforce complex business rules, such as ensuring an exhibition's end date is after its start date.
- **Database Constraints:** Leverages `@Column(unique=true)` and `@Table(uniqueConstraints=...)` to enforce uniqueness at the database level.

### 🔍 Advanced Data Access

- **Spring Data JPA Repositories:** Provides a clean data access layer with no boilerplate code for standard **CRUD** operations.
- **Custom Queries:** Includes derived query methods `(e.g., findByStatus)` and custom `@Query` annotations for specialized lookups.
- **Optimized Fetching:** Defaults to `FetchType.LAZY` on collections to prevent the N+1 problem and ensure efficient data loading.

### 🧪 Automated Testability

- Includes a default **JUnit 5** test class `(ArtGalleryApplicationTests)` that verifies the Spring application context can load successfully, establishing a foundation for further integration testing.

---

## 🧱 Domain Model Highlights

- **Gallery** contains multiple Sections `(Composition)`.
- **GalleryMember** is an abstract superclass with disjoint inheritance for Artist and Curator (Inheritance).
- **Exhibition** is the base of a four-level hierarchy to manage multi-aspect inheritance `(Polymorphism)`.
- **ArtworkInExhibition** models a many-to-many relationship with attributes `(Association Class)`.
- Gallery's link to Artwork is managed by inventoryNumber `(Qualified Association)`.

---

## Getting Started

### Prerequisites

Ensure the following tools are installed:

- Java 17+
- Maven 3.8+
- Git
- IDE (IntelliJ IDEA recommended)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/krookskala/Art-Expo-System

   cd art-gallery
   ```
2. **Build The Project**
    ```bash
   mvn clean install
   ```
3. **Run The Application**
    ```bash
   mvn spring-boot:run
   ```
- The API will be available at: http://localhost:8080/
- The H2 console for direct database access is available at: http://localhost:8080/h2-console
- **JDBC URL:** jdbc:h2:file:./data/artGallery
- **Username:** sa
- **Password:** (leave blank)

## 🧾 Project Structure

The project is organized following standard Spring Boot architecture principles.

### `src/main/java/edu/pja/mas/s22899/artgallery/`

- **`ArtGalleryApplication.java`**
  Main entry point of the Spring Boot application.

- **`configuration/DataInitializer.java`**
  Initializes the database with realistic and interconnected sample data at startup.

---

### `model/`
Contains all domain entity classes `(Artwork, Exhibition, Artist, etc.)` representing the system's business logic and relationships.

- `Address.java` – Embeddable value object for storing address information.
- `ArtGenre.java` – Enum listing available artwork genres for classification.
- `Artist.java` – Subclass of `GalleryMember`, holds artist-specific data like art style.
- `Artwork.java` – Represents an individual artwork, its details, and its relation to creators.
- `ArtworkInExhibition.java` – Association class modeling the many-to-many relationship between `Artwork` and `Exhibition` with attributes.
- `Curator.java` – Subclass of `GalleryMember`, stores curator-specific fields and relations.
- `DailyVisitorLog.java` – Tracks daily visitor counts for a `Section`, used for derived attributes.
- `Exhibition.java` – Abstract base class for the exhibition hierarchy, defining common properties and behavior.
- `Gallery.java` – Represents an art gallery, including its sections, members, and artwork catalog.
- `GalleryMember.java` – Abstract base class for shared attributes of artists and curators.
- `IndividualPermanentExhibition.java` – Concrete class for an individual artist's permanent exhibition.
- `IndividualTemporaryExhibition.java` – Concrete class for an individual artist's temporary exhibition.
- `PermanentExhibition.java` – Abstract subclass for permanent exhibitions, adding specific attributes and polymorphic behavior.
- `Section.java` –  Defines a physical area within a gallery, with state management logic.
- `SectionStatus.java` – Enum listing the possible operational states of a `Section`.
- `TemporaryExhibition.java` – Abstract subclass for temporary exhibitions, adding specific attributes and polymorphic behavior.
- `ThemedPermanentExhibition.java` –  Concrete class for a theme-based permanent exhibition.
- `ThemedTemporaryExhibition.java` – Concrete class for a theme-based temporary exhibition.
- `Usage.java` – Association class modeling the usage of a `Section` by an `Exhibition`.

---

### `repository/`
Houses Spring Data JPA repository interfaces with query methods for data access.

- `ArtistRepository.java` – JPA repository for the `Artist` entity.
- `ArtworkInExhibitionRepository.java` – JPA repository for the `ArtworkInExhibition` association class.
- `ArtworkRepository.java` – JPA repository for the `Artwork` entity.
- `CuratorRepository.java` – JPA repository for the `Curator` entity.
- `DailyVisitorLogRepository.java` – JPA repository for the `DailyVisitorLog` entity.
- `ExhibitionRepository.java` – JPA repository for the `Exhibition` entity hierarchy.
- `GalleryRepository.java` – JPA repository for the `Gallery` entity.
- `SectionRepository.java` – JPA repository for the `Section` entity.
- `UsageRepository.java` – JPA repository for the `Usage` association class.

---
### `dto/`
Data Transfer Objects used to define the public API contract.

- **`ArtworkSummaryDTO.java`** – DTO for representing a summary of an artwork in lists.
- **`ExhibitionDetailDTO.java`** – DTO for representing the full details of a single exhibition.
- **`ExhibitionSummaryDTO.java`** – DTO for representing a summary of an exhibition in lists.

---

---
### `controller/`
The API layer that exposes endpoints to clients.

- **`ExhibitionController.java`** – REST controller that handles all HTTP requests related to exhibitions.

---

---
### `service/`
The business logic layer, containing classes that orchestrate operations and manage transactions.

- **`ArtworkService.java`** – Service layer for orchestrating artwork-related business logic.
- **`ExhibitionService.java`** – Service layer for managing the lifecycle and relationships of exhibitions.
- **`GalleryService.java`** – Service layer for managing galleries and their sections.
- **`MemberService.java`** – Service layer for handling the hiring and management of gallery members.

---

### `resources/`

- **`application.properties`**  
  Contains configuration for database (H2), Hibernate, logging, and other Spring Boot settings.

---

### `src/test/java/edu/pja/mas/s22899/artgallery/`

- **`ArtGalleryApplicationTests.java`** – Contains the initial integration test to verify the Spring application context loads correctly.




## Technologies Used
- Spring Boot 3
- Spring Data JPA (Hibernate)
- Jakarta Persistence API
- Jakarta Bean Validation
- Lombok
- H2 Database (File-based)
- JUnit 5

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
