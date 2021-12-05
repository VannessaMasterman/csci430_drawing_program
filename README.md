# CSCI 430 Drawing Program

The goal of this project is to take a base program code using the MVC architecture and enhance it to include several features to make it a more complete project


## **Design Documentation** *Due December 6th*
## **Project Code** *Due December 15th* no extensions

## Required Changes

### Improved, user-friendly line drawing

Shows line between first click and mouse position after one click; after 2 clicks, shows a line and cursor returns to default.

### Drawing a triangle

The triangle is specified by clicking on three points. The system responds as follows:

Shows line between first click and mouse position after one click; after 2 clicks, shows a triangle between the two clicked points and the mouse position; after 3 clicks the cursor returns to default

### Rotating an item (line or triangle) by 90 degrees
There are two expected cases
1. If the mouse click does not fall on an object --> the cursor returns to default
2. If the mouse click falls on an object --> the user is asked to choose if they want to rotate clockwise or counterclockwise. The entire object is rotated 90 degress in the chosen direction

# Help or ideas

## How to do rotation

Rotating an object around a point is a math problem. We need to rotate every vertex of the object around the point. For example rotating (x1, y1) about (x2, y2) by 90 degrees counterclockwise.



### Rotate 90 Degress Counter Clockwise
(x2, y2) is the centre of rotation

the rotated position of (x1, y1) is denoted by (xr, yr)

```C++
xr = x2 + (y2-y1);
yr = y2 + (x1-x2)
```

### Rotate 90 Degress Clockwise
(x2, y2) is the centre of rotation

the rotated position of (x1, y1) is denoted by (xr, yr)

```C++
xr = x2 + (y1-y2);
yr = y2 + (x2-x1)
```



# What to turn in

## Design documentation (Due Mon, Dec 6th)

1. Line drawing
    * detailed use case
    * construct a sequence diagram (only MVC)
    * create a word doc similar to example explaining how the line is drawn in a user friendly format
2. Drawing a triangle
    * detailed use case
    * construct a sequence diagram (only MVC)
    * What classes were modified and how
    * What classes were added and how
3. Rotating an Item
    * detailed use case
    * construct a sequence diagram (only MVC)
    * What classes were modified and how
    * What classes were added and how

## Code and Implementation Report (Due 5pm Wed, Dec 15th NO EXTENSIONS)
1. How to run features implemented in program. Explain clearly the steps to be followed by a user testing all the changes you have made to the program
2. Completed code. In a seperate `Project3` folder in your CourseFiles folder. All code should be compiled and tested


# Solutions Used

## Line Drawing Preview

1. Created a private class under `LineButton.MouseHandler` called `PreviewLineCommand` which is really an empty child class used for tagging.
2. Edited the `UndoManager` to include a method called `getLastCommand` which returns the most recent command processed and throws an `EmptyStackException`
3. In `LineButton.actionPerformed` added a line to call `drawingPanel.addMouseMotionListener(mouseHandler);` which allows the mouse handler to recieve mouse motion events (Without this the `MouseHandler.mouseMoved` method is never called)
4. Added `MouseHandler.mouseMoved(MouseEvent)` method to take in motion commands.
5. Added a member variable to `MouseHandler` called `preview` of type `PreviewLineCommand`, initialized to `null`
6. Initialized `preview` alongside `lineCommand` to use the same event position in `mouseClicked`
7. Set `preview` to be null when the `lineCommand` is finalized
8. In `mouseMoved` check if `preview` is non-null, then try to get the last command from the undoManager. If it is type `PreviewLineCommand` then call `undoManager.undo()`.
    * Set the `preview` line point to be the event point as well as the `lineCommand` point. Have the undoManager begin and end the preview command.
9. Edit the `LineCommand` class so that the `pointCount` variable isn't incremented in the `if` branch but rather is assigned constant values.
