# OOP Assignment 4 — Arkanoid / Brick Breaker (Java + BiuOOP)

A portfolio-ready Java project built as **Assignment 4** in an Object-Oriented Programming course.  
This project implements an **Arkanoid / Brick Breaker** style 2D game with a clean OOP architecture: animation loop, sprites, collision handling, score tracking, and multiple levels — using **BiuOOP** for rendering and keyboard input.

**Tech:** Java (recommended 17+), OOP, BiuOOP  
**Window:** 800×600 (title: *Arkanoid*)  
**Main entry point:** `game.Ass4Game`

---

## What this project demonstrates

This repository demonstrates:
- Strong object-oriented design (clear responsibilities, modular components)
- A stable game loop (draw → input → update)
- Modular collision handling (ball vs blocks/walls/paddle)
- Event-driven architecture using listeners (e.g., score & removal logic)
- A scalable structure that supports adding new levels/sprites with minimal changes

---

## Gameplay

Break all blocks to clear a level.  
If you lose all balls, the game ends.

---

## Controls

- **Left Arrow / Right Arrow:** move the paddle  
- **p / P / פ:** pause  
- **Space:** resume (pause screen) / continue (end screens)

---

## Levels & CLI Arguments

The launcher (`game.Ass4Game`) supports selecting levels by command-line arguments:

- **No arguments** → runs all levels in order: `1 2 3 4`
- **With arguments** → runs only valid level numbers (invalid values are ignored)
- **If none of the arguments are valid** → runs all levels

**Level mapping:**
- `1` → DirectHit  
- `2` → WideEasy  
- `3` → Green3  
- `4` → FinalFour  

**Examples:**
```bash
# Run all levels
java -cp "bin:lib/biuoop-1.4.jar" game.Ass4Game

# Run levels 2 and 4 only
java -cp "bin:lib/biuoop-1.4.jar" game.Ass4Game 2 4

# Invalid args are ignored; if nothing valid remains -> runs all levels
java -cp "bin:lib/biuoop-1.4.jar" game.Ass4Game abc 9
```

---

## Project Structure

Source files are organized by packages:

```text
.
├─ animations/         # Animation framework + screens (pause / win / game over)
├─ collidables/        # Collidable objects & collision environment
├─ game/               # Game orchestration (GameLevel, GameFlow) + main (Ass4Game)
├─ geometry/           # Geometry primitives (Point/Line/Rectangle)
├─ levels/             # Level definitions (DirectHit, WideEasy, Green3, FinalFour)
├─ listeners/          # Hit listeners (score tracking, block/ball removers)
├─ sprites/            # Sprites (Ball, Paddle, indicators, etc.)
└─ lib/                # Put biuoop-1.4.jar here (recommended)
```

---

## Requirements

- Java 17+ (or the Java version required by your course)
- BiuOOP jar: `biuoop-1.4.jar`

### Dependency setup (recommended)

Create a folder named `lib/` and place the jar here:
- `./lib/biuoop-1.4.jar`

---

## Extra Runnable Demos (optional)

This repo also contains extra runnable classes useful for testing/learning (not the main submission), e.g.:
- `animations.BouncingBallAnimation`
- `animations.MultipleBouncingBallsAnimation`
- `animations.MultipleFramesBouncingBallsAnimation`
- `animations.SimpleGuiExample`
- `animations.HelloWorld`
- `game.BallsTest1`

The main project entry point remains: `game.Ass4Game`.

---

## Academic Note

This repository is based on a university assignment (Assignment 4).  
The code was written for learning purposes and refined to be presentable as a portfolio project.

---

## Author

**Naor Eliyahu**  
GitHub: https://github.com/naor778
