# 📦 Mobile Package Tracking Application

### Android Application for Postal Services in Bosnia and Herzegovina

**Undergraduate Final Project | International Burch University | 2026**

An Android mobile application designed to simplify shipment management by bringing package tracking information from multiple postal service providers into one organized interface.

Built with **Kotlin, Jetpack Compose, Room Database, and MVVM architecture**, the application focuses on usability, local data persistence, modular design, and automated background processing.

---

## Project Overview

Tracking shipments in Bosnia and Herzegovina often requires users to visit different postal service websites individually.

This project addresses that inconvenience by providing a centralized Android application where users can organize their shipments, monitor simulated delivery progress, and manage packages from a single interface.

The application supports three postal service providers:

- BH Pošta
- Hrvatska pošta Mostar (HP Mostar)
- Pošte Srpske

**Important:** The current version uses simulated tracking information. Its modular architecture is designed to support future integration with real postal tracking services.

## Key Features

- **Package Management:** Add, edit, delete, and archive shipments.
- **Multiple Postal Providers:** Select a supported postal service when adding a package.
- **Tracking Timeline:** View shipment statuses and delivery progress.
- **Search & Filtering:** Find packages and organize them by status.
- **Local Data Storage:** Persist shipment information using Room Database.
- **Notifications:** Configure notifications for shipment status updates.
- **Background Refresh:** Schedule tracking updates using WorkManager.
- **Statistics Dashboard:** View package counts and delivery progress.
- **Application Settings:** Manage notification and tracking preferences.

## Technology Stack

| Technology | Purpose |
|---|---|
| Kotlin | Android application development |
| Jetpack Compose | Declarative user interface |
| Material Design 3 | Consistent UI design |
| Room Database | Local data persistence |
| MVVM Architecture | Separation of UI and application logic |
| WorkManager | Background refresh operations |
| SharedPreferences | Application preferences |
| Android Studio | Development environment |
| Git & GitHub | Version control |

## Software Architecture

The application follows the **Model–View–ViewModel (MVVM)** architectural pattern.

- **UI Layer:** Jetpack Compose screens and reusable components.
- **ViewModel Layer:** Manages application state and coordinates operations.
- **Repository Layer:** Separates business logic from data access.
- **Data Layer:** Room entities, DAO interfaces, and local database.
- **Tracking Layer:** Modular provider implementations for different postal services.
- **Background Processing:** WorkManager workers for scheduled refresh operations.
- **Notification Layer:** Android notification services.

The tracking subsystem applies **Factory and Strategy design patterns**, allowing provider-specific tracking logic to remain separate and extensible.

## Application Screens

The application includes:

1. Home Screen
2. Add Package Screen
3. Package Details Screen
4. Edit Package Screen
5. Archive Screen
6. Statistics Screen
7. Settings Screen

The interface was designed with a focus on clear navigation, readability, and a consistent user experience.

## Testing & Quality Assurance

Functional and usability testing were performed to validate the application's core features.

**Documented result: 10/10 test cases passed.**

| Test Case | Scenario | Result |
|---|---|---|
| TC-01 | Add Package | PASS |
| TC-02 | Edit Package | PASS |
| TC-03 | Delete Package | PASS |
| TC-04 | Archive Package | PASS |
| TC-05 | Refresh Tracking Status | PASS |
| TC-06 | Search Packages | PASS |
| TC-07 | Filter Packages | PASS |
| TC-08 | Enable Notifications | PASS |
| TC-09 | Disable Notifications | PASS |
| TC-10 | View Statistics | PASS |

Testing also covered application navigation, local data persistence, and core user interactions.

## Future Improvements

Potential future enhancements include:

- Integration with real postal service tracking APIs
- Additional postal service providers
- Cloud synchronization and backup
- Multilingual support
- Expanded package analytics
- Additional notification customization

## Academic Context

This application was developed as my undergraduate final project at **International Burch University**, Department of Information Technologies, Sarajevo.

The project involved requirements analysis, UML modeling, software architecture, implementation, functional testing, and maintenance planning.

**Project Title:** Mobile Package Tracking Application for Postal Services in Bosnia and Herzegovina

**Author:** Ada Mahmutović

**Academic Mentor:** Adnan Miljković, MSc

**Year:** 2026

---


