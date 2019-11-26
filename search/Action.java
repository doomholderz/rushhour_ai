package search;
import rushhour.Car;

public interface Action {
    
    public int getCost();
    public String toString();
    public String getDirection();
    public Car getCar();
    public int getDistance();
    
}
