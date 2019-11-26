/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rushhour;

/**
 *
 * @author steven
 */
public class Position {
    
    private int row;
    private int col;

    /* NO LONGER USED
    private Car car;
    */
    
    public Position(int row, int col){
         this.row=row;
         this.col=col;
    }
    
    public int getRow(){
         return row;
    }
    
    public int getCol(){
         return col;
    }
    
    /* NO LONGER USED
    public Car getOccupyingCar(){
        return car;
    }
    */

    public String toString(){
        return "(" +row+","+col+")";
    }

    /* NO LONGER USED
    public void setOccupyingCar(Car car) {
        this.car = car;
    }*/
    


}
