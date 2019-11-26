## RUSHHOUR AI
***
An AI that will complete a [Rush Hour](https://en.wikipedia.org/wiki/Rush_Hour_(puzzle)) board in as few a moves as is possible.<br/><br/>
Given a .txt file with information about the Rush Hour gameboard, this Java script will find the most efficient number of steps to complete the board.<br/><br/>

### RUNNING THE PROGRAM
***

To run the program on random1.txt<br/>
`java RushHour random1.txt`<br/><br/>

The program takes the information inside the random text file as followed:<br/>
1st Line: Number of columns<br/>
2nd Line: Number of rows<br/>
3rd Line: Position of the goal car (we want to get this car to the final column)<br/>
Car position is shown as Column, Row, Length, Orientiation, where H = horizontal, and V = vertical
  <br/>
4th Line +: Positions of the blocking cars
