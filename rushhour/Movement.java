package rushhour;

import search.Action;
import search.State;


public class Movement implements Action{

	String direction;
	Car car;
    int distance;

	public Movement(String direction, Car car, int distance) {

		this.direction = direction;
		this.car = car;
        this.distance = distance;

		//System.out.println(direction + " " + car);
	}

    public int getCost() {
        return 1;
    }

    public String getDirection(){
    	return direction;
    }

    public int getDistance() {
        return distance;
    }

    public Car getCar(){
    	return car;
    }

    public String toString(){
    	String rt = getDirection();
    	Car cr = getCar();
        int distance = getDistance();
    
        return cr + " - " + rt + " - " + distance;
    }
    
}
