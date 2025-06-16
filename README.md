# Room Reservation System Frontend

## Introduction

A simple backend system with implemented frontend. 
It is a universal room booking system that supports the possibility of reserving specific rooms and assigning individual tags to them.

## Authors

[Stanisław Strojniak](https://github.com/stawkey)

[Wiktor Kostka](https://github.com/Mekost)

## Features

-   **Rooms Management**: Create, edit, delete and view rooms with their details and tags
-   **Reservations Management**: Create new reservations, view existing ones, and cancel them
-   **Tags Management**: Create, edit, and delete tags for room features
-   **Real-time Operations**: Shows reservations for specific rooms and handles conflicts

## Technologies Used

### Backend

- Database: PostgreSQL
- ORM: Hibernate + Java for mapping Java classes to database tables without writing SQL queries
- Spring Boot for configurating HTTP server, creating REST API and integrating with database
- Swagger for testing and documenting API
- OpenAPI Codegen - for generating backend basics basing on .yaml configuration

### Frontend

-   React 18 with JavaScript for creating UI and interactions with backend
-   Material-UI for components and styling
-   Axios for API communication
-   Vite for build tooling

## Getting Started

Clone the repository

```bash
git clone https://github.com/stawkey/room-reservation
```

# Backend

1. Go to the backend folder:

```bash
cd room-reservation/backend
```

2. Make sure Docker is running. Run the database with:

```
docker-compose up
```

3. Run OpenApiGeneratorApplication.java

4. You can navigate to `http://localhost:8080` in your browser to access OpenAPI documentation.

#Frontend

1. Go to the frontend folder and install dependencies:

```bash
cd room-reservation/frontend
npm install
```

2. Start frontend development server:

```bash
npm run dev
```

3. Open your browser and navigate to `http://localhost:5173`

## Backend Integration

The frontend is configured to connect to a Spring Boot backend API running on `http://localhost:8080/api` through a Vite proxy configuration.

Make sure your backend server is running on port 8080 before using the frontend.

The proxy automatically forwards all `/api` requests from the frontend to `http://localhost:8080/api`.

## Project Structure

- `backend` - contains all backend logic
- `frontend` - contains implementation of UI for project
