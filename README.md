# Android-test
This repository contains solutions for selected problems from the Android Assignment Set 2.  
The focus is on algorithmic problem solving and Android app development using kotlin and MVVM architecture.

## Problems Included

### Q1. N-Queens Puzzle
Solve the classic n-queens problem: place n queens on an n×n chessboard so no two queens attack each other.

- Returns all valid board configurations.
- Implemented with backtracking.

### Q2. Circular Dependency Detection
Detect cycles in a directed graph representing module dependencies.

- Returns true if any circular dependency exists, else false.
- Uses graph traversal, DFS and cycle detection algorithms.

### Q4. WeatherTrack Android App
A weather tracking app fetching data from a mock API.

- Saves data locally every 6 hours using Room database and WorkManager for background sync.
- Displays weekly temperature trends and detailed daily weather stats.
- Implements MVVM architecture with clean separation of concerns.
- Handles network and database errors gracefully.
