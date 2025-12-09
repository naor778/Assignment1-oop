Assignment 1 – OOP (Ariel University)

This project is an implementation of Object-Oriented Programming fundamentals as part of Assignment 1 in the OOP course at Ariel University.
The assignment includes creating basic geometric classes and implementing several graphical animations using the biuoop library.
📂 Project Structure:
src/
 ├── geometry.Point.java
 ├── geometry.Line.java
 ├── sprites.Velocity.java
 ├── sprites.Ball.java
 ├── animations.HelloWorld.java
 ├── animations.BouncingBallAnimation.java
 ├── animations.MultipleBouncingBallsAnimation.java
 ├── animations.MultipleFramesBouncingBallsAnimation.java
 ├── game.AbstractArtDrawing.java
 ├── Tests (optional)


Implementing classes, fields, constructors, and methods

Practicing encapsulation & information hiding

Working with objects and composition

Understanding vectors, geometry, and intersections

Building animations with the biuoop library

Handling randomization and velocities

Working with command-line arguments

geometry.Point

Represents a 2D point.

animations.Main methods:

double distance(geometry.Point other)

boolean equals(geometry.Point other)

double getX()

double getY()

geometry.Line

Represents a line segment between two points.

animations.Main methods:

double length()

geometry.Point middle()

Double slope() (returns null for vertical lines)

boolean isIntersecting(geometry.Line other)

geometry.Point intersectionWith(geometry.Line other)

Helper: inRange(a, b, value)

sprites.Velocity

Represents movement in dx/dy.

animations.Main methods:

applyToPoint(geometry.Point p)

getDx()

getDy()

static fromAngleAndSpeed(angle, speed)

sprites.Ball

Represents a moving ball in 2D.

Fields:

geometry.Point center

int radius

Color color

sprites.Velocity velocity

animations.Main methods:

drawOn(DrawSurface d)

moveOneStep()

setVelocity(dx, dy)

setVelocity(sprites.Velocity v)

getVelocity()

🎬 Animations
animations.BouncingBallAnimation

Displays a single bouncing ball inside a 200×200 window.

Usage:
java animations.BouncingBallAnimation x y dx dy

animations.MultipleBouncingBallsAnimation

Creates several balls with random starting positions.
sprites.Ball speed is inversely proportional to size (smaller → faster).

Usage:
java animations.MultipleBouncingBallsAnimation 10 5 20 30 50

animations.MultipleFramesBouncingBallsAnimation

Displays two separate animation frames on the same screen, each containing balls with different boundaries.

game.AbstractArtDrawing

Draws random lines and highlights their intersection points.

📦 External Library

Project uses:

biuoop-1.4.jar


Library provides:

GUI management

DrawSurface graphics

Sleeper for timing

▶ How to Compile & Run
Compile:
javac -cp biuoop-1.4.jar -d bin src/*.java

Run:
java -cp biuoop-1.4.jar;bin animations.MultipleBouncingBallsAnimation 10 20 5 15

✔ Notes

All fields are private to enforce encapsulation.

sprites.Velocity objects are immutable (no setters).

sprites.Ball movement includes full collision detection with boundaries.

Code follows OOP guidelines required in the assignment.

✏ Author

Naor Eliyahu
Ariel University – Computer Science
