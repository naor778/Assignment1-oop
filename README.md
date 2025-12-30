# OOP Assignment 4 — Java 2D Arcade Game (BiuOOP)

A portfolio-ready Java project built as **Assignment 4** in an Object-Oriented Programming course. The project implements a small **2D arcade game framework** (animation loop, sprites, collisions, and level flow) using the **BiuOOP** library for rendering and basic GUI.

Tech: Java (recommended 17+), OOP, BiuOOP  
Focus: Clean architecture, separation of concerns, reusable components

---

## Why this project matters (for recruiters)

This repository demonstrates:
- Solid object-oriented design (encapsulation, abstractions, and clear responsibilities)
- A stable animation/game loop with predictable frame updates
- Modular collision handling and movement logic
- A scalable structure that supports adding new sprites/levels with minimal changes

---

## Main Features

- Animation runner (game loop)
- Sprites rendering & updates
- Collision detection & response (walls / blocks / paddle / etc., depending on implementation)
- Level management / game flow
- Win/Lose end states (if implemented in the assignment)

---

## Project Structure

Typical layout (may vary slightly depending on the repo):

.
├─ src/
│  ├─ geometry/        # Point/Line/Rectangle and geometry helpers
│  ├─ sprites/         # Ball, Block, Paddle, Sprite interface
│  ├─ collision/       # Collidable, HitListener, HitNotifier, etc.
│  ├─ animations/      # Animation, AnimationRunner, screens (pause/end)
│  └─ game/            # GameLevel, GameFlow, main entry point
├─ lib/                # external jars (biuoop-1.4.jar)  (recommended)
├─ README.md
└─ .gitignore

---

## Requirements

- Java 17+ (or the Java version required by your course)
- BiuOOP library: biuoop-1.4.jar

Dependency setup:
- Recommended: place the jar at ./lib/biuoop-1.4.jar
- Alternative: keep the jar in the project root (less clean)

---

## Build & Run

### Linux / macOS (Terminal)
Use ":" as the classpath separator.

mkdir -p bin
find src -name "*.java" > sources.txt
javac -d bin -cp "lib/biuoop-1.4.jar" @sources.txt
java -cp "bin:lib/biuoop-1.4.jar" <MAIN_CLASS>

### Windows (PowerShell)
Use ";" as the classpath separator.

New-Item -ItemType Directory -Force bin | Out-Null
$srcs = Get-ChildItem -Recurse -Path src -Filter *.java | ForEach-Object { $_.FullName }
javac -d bin -cp "lib\biuoop-1.4.jar" $srcs
java -cp "bin;lib\biuoop-1.4.jar" <MAIN_CLASS>

How to find <MAIN_CLASS>:
Search in src/ for: public static void main(String[] args)
Then use the fully-qualified name (package.ClassName), for example: game.Main

---

## Controls (edit to match your implementation)

- Left / Right: move paddle
- Space: start / continue
- P: pause (if implemented)
- Esc: exit (if implemented)

---

## Design Notes

This project is organized so that:
- Rendering and the animation loop are separated from game logic
- Sprites (drawable/updatable objects) are independent and reusable
- Collision handling is modular (collidable objects define their own behavior)
- Level/game flow is extendable (new levels and objects can be added without rewriting the core engine)

---

## Suggested Portfolio Upgrades

If you want to push this further as a CV project:
- Add unit tests for geometry and collision logic
- Make levels configuration-driven (e.g., JSON/text files)
- Add GitHub Actions CI to compile on every push
- Convert the project to Maven/Gradle for cleaner dependency management

---

## Repository Hygiene (Professional GitHub)

Recommended .gitignore entries:

.idea/
*.iml
bin/
out/
target/
.DS_Store
Thumbs.db

---

## Academic Note

This repository is based on a university assignment (Assignment 4). The code was written by the author for learning purposes and refined to be presentable as a portfolio project.

---

## Author

<YOUR_NAME>  
GitHub: https://github.com/<YOUR_GITHUB_USERNAME>  
LinkedIn: <YOUR_LINKEDIN_URL>
