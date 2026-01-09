HQ Restaurant – Android Application (COMP2000)
Overview

HQ Restaurant is an Android application developed in Java using Android Studio as part of the COMP2000 coursework.
The application supports two user types: Guest/User and Staff, and allows users to browse menu items, make restaurant reservations, and manage bookings. Staff users are able to manage menu items and view or delete reservations.

The application uses a combination of a central API for user management and local SQLite databases for menu items and reservations.

Features
Guest/User Features

Register an account via the provided API

Log in as a guest user

View menu items loaded from a local SQLite database

Create new reservations (date, time, number of guests)

View and delete personal reservations

Receive confirmation feedback when a reservation is created

Staff Features

Log in as staff

Access a staff dashboard

View all reservations made by users

Delete reservations if required

View and manage menu items stored in SQLite

Receive notifications when a new reservation is booked (same device)

Technologies Used

Java

Android Studio

SQLite (local database for menu and reservations)

Volley (network requests to API)

RecyclerView (displaying menu and reservations)

Notifications API (Android notifications)

SharedPreferences (session management)

API Integration

The application integrates with a provided REST API to manage user accounts.

API Capabilities

Create student database

Register users

Read user details (login)

Update and delete users

API calls are handled in a dedicated Api.java class using the Volley library to ensure all network operations run asynchronously and do not block the UI thread.

Data Management
Central Database (API)

Used for user registration and authentication

Stores user credentials remotely

Accessed via HTTP requests using Volley

Local Database (SQLite)

Two SQLite databases are used:

Menu Database

Stores menu items (name, price, image reference)

Seeded with default items on first launch

Reservations Database

Stores reservations (username, date, time, guest count)

Used by both guest and staff users

Notifications

When a guest user books a reservation, the application triggers a local notification on the same device.
This notification informs staff users that a new reservation has been created and opens the staff reservations screen when tapped.

Android 13+ notification permissions (POST_NOTIFICATIONS) are handled at runtime.

Project Structure
app/
 ├── java/com.example.hqrestaurant/
 │   ├── Api.java
 │   ├── MenuDbHelper.java
 │   ├── ReservationsDbHelper.java
 │   ├── GuestReservationsActivity.java
 │   ├── StaffMainActivity.java
 │   ├── StaffReservationsActivity.java
 │   ├── Adapters (MenuAdapter, ReservationsAdapter, etc.)
 │   └── Models (User, MenuItem, ReservationsModel)
 ├── res/
 │   ├── layout/
 │   ├── drawable/
 │   └── values/

How to Run the Project

Clone the repository from GitHub

Open the project in Android Studio

Sync Gradle

Run the app on an emulator or physical Android device

Ensure the API base URL is accessible from your network

Demo & Evidence

A demonstration video is included as part of the coursework submission, showing:

User registration and login

Reservation booking

Staff reservation management

Notifications

Menu management

Screenshots of the application UI and API usage are included in the implementation report.

GitHub Repository

The full source code for this project is available on GitHub:

(https://github.com/Plymouth-COMP2000/design-exercises-isuned)

## Third-Party Libraries & Resources

The following third-party libraries and resources were used in this project:

- **Android SDK** – Core Android development framework
- **Volley** – Used for handling asynchronous API requests and JSON parsing
- **SQLite** – Local database used for menu items and reservations
- **Material Components for Android** – UI components such as buttons and navigation
- **AndroidX Libraries** – RecyclerView, CardView, ConstraintLayout
- **REST API (University-provided)** – Used for user registration and authentication

No additional external frameworks or paid services were used.


Author

Student Name: [Chukwunedum Isu]

Student ID: [10897390]

Module: COMP2000 



