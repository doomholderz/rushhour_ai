package rushhour;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import search.Action;
import search.State;
import java.util.Collections;

/**
 *
 * @author steven
 */
public class GameState implements search.State {

    boolean[][] occupiedPositions;
    List<Car> cars;  
    int nrRows;
    int nrCols;
    List<Position> occupiedPositionsList = new ArrayList<Position>();
    HashSet<Car> occupiedPositionsInit = new HashSet();

    public GameState(String fileName) throws Exception {
        // ADDS INFORMATION FROM TEXT FILE INTO THE CORRECT VARIABLES
        BufferedReader in = new BufferedReader(new FileReader(fileName));
        nrRows = Integer.parseInt(in.readLine().split("\\s")[0]);
        nrCols = Integer.parseInt(in.readLine().split("\\s")[0]);
        String s = in.readLine();
        cars = new ArrayList();
        while (s != null) {
            cars.add(new Car(s));
            s = in.readLine();
        }

        initOccupied();
    }

    public GameState(int nrRows, int nrCols, List<Car> cars) {
        this.nrRows = nrRows;
        this.nrCols = nrCols;
        this.cars = cars;

        initOccupied();
    }

    public GameState(GameState gs) {
        nrRows = gs.nrRows;
        nrCols = gs.nrCols;

        occupiedPositions = new boolean[nrRows][nrCols];

        for (int i = 0; i < nrRows; i++) {
            for (int j = 0; j < nrCols; j++) {
                occupiedPositions[i][j] = gs.occupiedPositions[i][j];
            }
        }

        cars = new ArrayList();
        for (Car c : gs.cars) {
            cars.add(new Car(c));
        }
    }

    public void printState() {
        int[][] state = new int[nrRows][nrCols];

        for (int i = 0; i < cars.size(); i++) {
            List<Position> l = cars.get(i).getOccupyingPositions();
            for (Position pos : l) {
                state[pos.getRow()][pos.getCol()] = i + 1;
            }
        }

        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                if (state[i][j] == 0) {
                    System.out.print(".");
                } else {
                    System.out.print(state[i][j] - 1);
                }
            }
            System.out.println();
        }
    }

    private void initOccupied() {

        occupiedPositions = new boolean[nrRows][nrCols];
        

        for (Car c : cars) {

            List<Position> l = c.getOccupyingPositions();

            for (Position pos : l) {
                occupiedPositions[pos.getRow()][pos.getCol()] = true;

            }
        }
    }

    public boolean isGoal() {
        Car goalCar = cars.get(0);
        return goalCar.getCol() + goalCar.getLength() == nrCols;
    }

    public boolean equals(Object o) {
        if (!(o instanceof GameState)) {
            return false;
        } else {
            GameState gs = (GameState) o;
            return nrRows == gs.nrRows && nrCols == gs.nrCols && cars.equals(gs.cars); // note that we don't need to check equality of occupiedPositions since that follows from the equality of cars
        }
    }

    public int hashCode() {
        return cars.hashCode();
    }

    public void printToFile(String fn) {
        try {
            PrintWriter out = new PrintWriter(new FileWriter(fn));
            out.println(nrRows);
            out.println(nrCols);
            for (Car c : cars) {
                out.println(c.getRow() + " " + c.getCol() + " " + c.getLength() + " " + (c.isVertical() ? "V" : "H"));
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<Action> getLegalActions() {
        ArrayList<Action> res = new ArrayList();
        for (Car c: this.cars) {
     
            int i = 1;
            // canMove will be true if the car can move a direction a certain distance
            boolean canMove = true;

            // while canMove is true, distance will increase until it is blocked by wall or other car
            while (canMove == true) {
                if (isLegal(new Movement("left", c, i))) {
                    res.add(new Movement("left", c, i));
                    i++;
                }
                else {
                    canMove = false;
                    i = 1;
                }
            }

            canMove = true;
            while (canMove == true) {
                if (isLegal(new Movement("right", c, i))) {
                    //System.out.println("Car: " + c.getOccupyingPositions() + " can move " + i + " steps right");
                    res.add(new Movement("right", c, i));
                    i++;
                } else {
                    //System.out.println("Blocked right");
                    canMove = false;
                    i = 1;
                }
            }
            canMove = true;
            while (canMove == true) {
                if (isLegal(new Movement("up", c, i))) {
                    //System.out.println("Car: " + c.getOccupyingPositions() + " can move " + i + " steps up");
                    res.add(new Movement("up", c, i));
                    i++;
                } else {
                    //System.out.println("Blocked up");
                    canMove = false;
                    i = 1;
                }
            }
            canMove = true;
            while (canMove == true) {
                if (isLegal(new Movement("down", c, i))) {
                    //System.out.println("Car: " + c.getOccupyingPositions() + " can move " + i + " steps down");
                    res.add(new Movement("down", c, i));
                    i++;
                } else {
                    //System.out.println("Blocked down");
                    canMove = false;
                    i = 1;
                }
            }
        }
        
        initOccupied();
        return res;
    }


    // CHECKS IF ACTION IS LEGAL 
    public boolean isLegal(Action action){

        // Get informarion pertaining to the car passed in the action argument
        int length = action.getCar().getLength();
        int dist = action.getDistance();
        int currentRow = action.getCar().getRow();
        int currentCol = action.getCar().getCol();

        // if the action is 'left'
        if (action.getDirection() == "left") {

            // Checks to make sure the car is horizontal
            if (!action.getCar().isVertical()) {

                // Checks if the car will pass through the wall during the movement
                if (currentCol == 0 || currentCol - dist < 0 || occupiedPositions[currentRow][currentCol - dist] == true) {
                    return false;
                } else {
                    return true;
                }
            }
        }

        // if the action is 'right'
        else if (action.getDirection() == "right") {

            // Checks to make sure the car is horizontal
            if (!action.getCar().isVertical()) {

                // Checks if the car will pass through the wall during the movement
                if (currentCol == nrCols - length || currentCol + dist + length > nrRows || occupiedPositions[currentRow][currentCol + length - 1 + dist] == true) {
                    return false;
                } else {
                    return true;
                }
            }
        }

        // if the action is 'up'
        else if (action.getDirection() == "up") {

            // Checks to make sure the car is vertical
            if (action.getCar().isVertical()) {

                   // Checks if the car will pass through the wall during the movement
                if (currentRow == nrRows - length || currentRow + dist + length > nrRows || occupiedPositions[currentRow + length - 1 + dist][currentCol] == true) {
                    return false;
                } else {
                    return true;
                }
            }
        }

        // if the action is 'down'
        else if (action.getDirection() == "down") {

            // Checks to make sure the car is vertical
            if (action.getCar().isVertical()) {

                   // Checks if the car will pass through the wall during the movement
                if (currentRow == 0 || currentRow - dist < 0 || occupiedPositions[currentRow - dist][currentCol] == true) {
                    return false;
                } else {
                    return true;
                }
            }
        }        
        return false;
    }


    // Copies list so list contains car entires, not references (deep copy) 
    public void copyList(List<Car> dest, List<Car> source){
        dest.clear();
        for (int i = 0; i < source.size(); i++) {
            dest.add(new Car(source.get(i)));
        }
    }


    public State doAction(Action action){

        // create deep copy of the cars list
        List<Car> tmpCars = new ArrayList<Car>();
        copyList(tmpCars, cars);

        // Creates game state with the deep copy of the cars (so we don't change the actual car positions permanently)
        GameState res = new GameState(nrRows, nrCols, tmpCars);

        // get information about the car passed in the action argument
        int distance = action.getDistance();
        int currentRow = action.getCar().getRow();
        int currentCol = action.getCar().getCol();
        int length = action.getCar().getLength();
        int position = tmpCars.indexOf(action.getCar());
        String direction = action.getDirection();
   
        // if the direction of travel is left
        if (direction == "left") {
          
            // move the car left the number of positions passed in the 'distance' part of the action argument
            tmpCars.get(position).setCol(currentCol - distance);
            
            // update occupiedPositions for the gamestate so the old position of car is now empty
            for (int i = (currentCol - distance + length); i < (currentCol + length); i++) {
                res.occupiedPositions[currentRow][i] = false;
                res.initOccupied();
            }

        // if the direction of travel is right
        } else if (direction == "right") {

            // move the car right the number of positions passed in the 'distance' part of the action argument
            tmpCars.get(position).setCol(currentCol + distance);
            
            // update occupiedPositions for the gamestate so the old position of car is now empty
            for (int i = currentCol; i < currentCol + distance; i++) {
                res.occupiedPositions[currentRow][i] = false;
                res.initOccupied();
            }

        // if the direction of travel is up
        } else if (direction == "up") {

            // move the car up the number of positions passed in the 'distance' part of the action argument
            tmpCars.get(position).setRow(currentRow + distance);
            
            // update occupiedPositions for the gamestate so the old position of car is now empty
            for (int i = currentRow; i < currentRow + distance; i++) {
                res.occupiedPositions[i][currentCol] = false;
                res.initOccupied();
            }

        // if the direction of travel is down
        } else if (direction == "down") {

            // move the car down the number of positions passed in the 'distance' part of the action argument
            tmpCars.get(position).setRow(currentRow - distance);

            // update occupiedPositions for the gamestate so the old position of car is now empty
            for (int i = (currentRow - distance + length); i < (currentRow + length); i++) {
                res.occupiedPositions[i][currentCol] = false;
                res.initOccupied();
            }

        } else {

            return null;
        }
      
        // update the occupied positions list
        initOccupied();
        return res;

    }




  
    public int getEstimatedDistanceToGoal() {
        int startRow = cars.get(0).getRow();
        int dist = 1;
        int count = 0;
        
        // create hashset that will store unique car values 
        HashSet noDupSet = new HashSet();
        
        for (Car c: cars) {
            
            // if the car c is in front of the goal car
            if (c.getCol() >= cars.get(0).getCol() + cars.get(0).getLength()) {
                
                // if part of the car c is blocking the goal car from the goal
                if (c.getRow() <= startRow && c.getRow() + c.getLength() > startRow) {
                    
                    // car is a blocking car, will need to be moved so increase count by 1
                    count++;
                    
                    

                    // create lists that will store the number of cars above goal-blocking
                    // car, and the number of cars below goal-blocking car
                    List<Car> upTmp = new ArrayList<Car>();
                    List<Car> downTmp = new ArrayList<Car>();
                    
                    for (Car c2: cars) {
                        // if the car is blocking the goal-blocking car from moving either up or down
                        if (c2.getCol() <= c.getCol() && c2.getCol() + c2.getLength() >= c.getCol()) {
                            
                            // if the car is above the goal-blocking car
                            if (c2.getRow() <= startRow + c.getLength() && c2.getRow() >= c.getRow() + c.getLength() ) {
                                
                                // add this car to the upTmp list 
                                upTmp.add(c2);
                            
                            // if the car is below the goal-blocking car, and is vertical    
                            } else if (c2.isVertical() == true && c2.getRow() + c2.getLength() - 1 >= startRow - c.getLength() && c2.getRow() + c2.getLength() - 1 < c.getRow()) {
                                
                                // add this car to the downTmp list
                                downTmp.add(c2);

                            // if the car is below the goal-blocking car, and is horizontal  
                            } else if (c2.isVertical() == false && c2.getRow() >= startRow - c.getLength() && c2.getRow() < c.getRow()) {
                                    
                                // add this car to the downTmp list
                                downTmp.add(c2);
                            } 
                        }
                        
                    }

                    // if there are cars both above the goal-blocking car, and below
                    if (downTmp.size() > 0 && upTmp.size() > 0) {

                        // if there are less cars blocking the car from moving up than moving down
                        if (downTmp.size() > upTmp.size()) {

                            // add every car from the upTmp list to the hashset
                            for (Car c3: upTmp) {
                                noDupSet.add(c3);
                            }

                        // else add every car from the downTmp list to the hashset
                        } else {
                            for (Car c4: downTmp) {
                                noDupSet.add(c4);
                            }
                        }
                    }

                    }
                    

                }
            }

            // return dist (1) + number of goal-blocking cars + number of unique cars blocking these goal-blocking cars
            return dist + count + noDupSet.size();
        }




      

    }
    
    