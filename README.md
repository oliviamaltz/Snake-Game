# Snake Game in Java

This is a classic Snake game implemented in Java as an exercise for a CIS1200 class project at the University of Pennsylvania.

## Core Concepts Implemented
- **File I/O:** Used `BufferedWriter` and `BufferedReader` to save and load game states.
- **JUnit Testing:** Tested key functions like snake movement, direction changes, growth, and food consumption.

## Project Structure
- **Main.java:** Main entry point.
- **RunSnake.java:** Sets up the GUI frame, controls, and instructions.
- **GameCourt.java:** Game logic, input handling, rendering, saving/loading, and timer updates.
- **Snake.java:** Controls snake movement, direction, and growth.
- **Tile.java / TileGrid.java:** Handles individual tiles and the game grid.
- **GrowFood.java / SpeedFood.java:** Define food items affecting snake growth and speed.
- **GameObj.java:** Base class for all game objects.
- **Direction.java:** Enum for directions.
- **Consumable.java:** Interface for consumable items.

## Features
- Snake movement with arrow keys.
- Power-ups for speed and length.
- Save/load functionality.
- GUI with status, scores, and instructions.
