## RUSHHOUR AI
An AI that will complete a [Rush Hour](https://en.wikipedia.org/wiki/Rush_Hour_(puzzle)) board in as few a moves as is possible.
Given a .txt file with information about the Rush Hour gameboard, this Java script will find the most efficient number of steps to complete the board.
### RUNNING THE PROGRAM

To run the program on random1.txt<br/>
`java RushHour random1.txt`<br/>

The program takes the information inside the random text file as followed:
* 1st Line: Number of columns
* 2nd Line: Number of rows<br/>
* 3rd Line: Position of the goal car (we want to get this car to the final column)<br/>
  * Car position is shown as Column, Row, Length, Orientiation, where H = horizontal, and V = vertical
* 4th Line Onwards: Positions of the blocking cars


### DETAILS ABOUT PROGRAM
The program uses the A* algorithm to quickly identify the best route for the car.<br/>
The heuristic chosen for the A* algorithm takes into account:
* The number of cars directly in front of the goal car
* The number of cars either above or below these blocking cars
* The smallest number of cars that need to be moved to allow these blocking cars to move

### FUTHER IMPROVEMENTS
The next improvement to be made is directly relating to the heuristic, as at present it is not admissible.<br/>
I will be implementing a recursive algorithm that will check each blocking car, which cars are blocking it, and how many moves are required to move these cars.
