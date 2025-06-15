# Room Reservation System Frontend

A simple React frontend application for a room reservation system built with Material-UI.

## Features

-   **Rooms Management**: Create, edit, delete and view rooms with their details and tags
-   **Reservations Management**: Create new reservations, view existing ones, and cancel them
-   **Tags Management**: Create, edit, and delete tags for room features
-   **Real-time Operations**: Shows reservations for specific rooms and handles conflicts

## Technologies Used

-   React 18 with JavaScript
-   Material-UI for components and styling
-   Axios for API communication
-   Vite for build tooling

## Getting Started

1. Install dependencies:

```bash
npm install
```

2. Start the development server:

```bash
npm run dev
```

3. Open your browser and navigate to `http://localhost:5173`

## Backend Integration

The frontend is configured to connect to a Spring Boot backend API running on `http://localhost:8080/api` through a Vite proxy configuration.

Make sure your backend server is running on port 8080 before using the frontend.

The proxy automatically forwards all `/api` requests from the frontend to `http://localhost:8080/api`.

## Project Structure

-   `src/components/` - React components for each page
-   `src/services/` - API service layer for backend communication
-   `src/App.jsx` - Main application component with navigation
