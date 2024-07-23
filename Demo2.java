package soc;
import java.util.Arrays;
public class Demo2 {

    public static void main(String[] args) {
    	State[][] cells = {
    		    { State.EMPTY,    State.EMPTY,    State.OCCUPIED},
    		    { State.OCCUPIED, State.EMPTY,    State.EMPTY},
    		    { State.BURNING,  State.OCCUPIED, State.OCCUPIED},
    		};
    int[][] indexes = ForestFire.occupiedIndexes(cells);
     
     System.out.println(Arrays.deepToString(indexes));
    }
}
