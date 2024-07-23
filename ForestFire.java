package soc;

import java.util.Random;   
/** 
 * An implementation of the Forest-fire model described by Drossel and Schwabl
 * (1992). This implementation uses a 2-dimensional regular grid of cells where
 * each cell may be in one of three states: (1) empty, (2) occupied by a tree,
 * (3) burning. The state of each cell of the grid evolves over discrete time
 * steps according to four rules:
 * 
 * <ol>
 * <li>a burning cell becomes an empty cell
 * <li>a cell occupied by a tree becomes burning if at least one cell adjacent
 * to the tree is burning
 * <li>a cell occupied by a tree becomes burning with some probability even if
 * it has no burning cells adjacent to it
 * <li>an empty cell becomes occupied by a tree with some probability
 * </ol>
 * 
 * All cells are updated simultaneously at each time step.
 * 
 * <p>
 * Drossel B. and Schwabl F. (1992). Self-organized critical forest-fire model.
 * Physical Review Letters. Americal Physical Society. 69(11), 1629-1632.
 * 
 */
public class ForestFire {

	/**
	 * The probability that a tree ignites (becomes burning) during one time step.
	 */
	public static final double PROB_IGNITION = 0.0001;

	/**
	 * The probability that a tree grows in an empty cell during one time step.
	 */
	public static final double PROB_GROWTH =  0.001;
	
	 private static Random rng = new Random(1);


	
	
	/**
	 * Returns the number of rows in the two-dimensional array of cells.
	 * 
	 * @param cells a two-dimensional array
	 * @return the number of rows in the two-dimensional array {@code cells}
	 */
	public static int rowsIn(State[][] cells) {
		int numberOfRows = cells.length;
		return numberOfRows;
	}
	

	/**
	 * Returns the number of columns in the two-dimensional array of cells.
	 * 
	 * @param cells a two-dimensional array
	 * @return the number of columns in the two-dimensional array {@code cells}
	 */
	public static int colsIn(State[][] cells) {
		 if (rowsIn(cells) > 0){
			int numberOfCols = cells[0].length;
		 	return numberOfCols;
		 
		 }
		 else {
			 return 0;
		 }
	}
	


	/**
	 * Returns true if {@code row} is a valid row index and {@code col} is a valid
	 * column index for the two-dimensional array of cells, and false otherwise.
	 * 
	 * @param cells a two-dimensional array
	 * @param row   a row index
	 * @param col   a column index
	 * @return true if row and col are valid indexes for {@code cells}, false
	 *         otherwise
	 */
	public static boolean indexOk(State[][] cells, int row, int col) {
	    if (row < 0 || row >= rowsIn(cells)){
	        return false;
	    }
	    if (col < 0 || col >= colsIn(cells)) {
	        return false;
	    }
	    else {
	    	return true;
	    }
		
	}

	/**
	 * Prints the cells of a Forest-fire model followed by a new line.
	 * 
	 * <p>
	 * Empty cells are represented with a hyphen '-', trees are represented with the
	 * character 'T', and burning trees are represented with the character '#'.
	 * There are no separator characters between cells. Each row of {@code cells} is
	 * printed on a separate line.
	 * 
	 * @param cells a two-dimensional array
	 */
	public static void printForest(State[][] cells) {
		int rows = rowsIn(cells);
		int cols = colsIn(cells);
		for (int r = 0; r < rows; r++) {
			for(int c = 0; c < cols; c++) {
				State s = cells[r][c];
				if (s == State.EMPTY) {
					System.out.print('-');
				}
				else if (s == State.OCCUPIED){
					System.out.print('T');}
				else {
					System.out.print('#');}
			}
			System.out.println();
		}
		
		
	}
	
	

	public static State[][] copy(State[][] cells) {
		State[][] copy = new State[cells.length][cells[0].length];
		for (int r = 0; r < cells.length; r++) {
	        for (int c =0; c < cells[0].length; c++) {
	            copy[r][c] = cells[r][c];
	        	}
	        }
	    return copy;
		
	}
	
	
	public static State[][] nMoore(State[][] cells, int row, int col)
	{
		State[][] nhood = new State[3][3];
		
		int left = col - 1;
		int top = row - 1;
		
		for(int r = 0; r < 3; r++) {
	        int cellsRow = top + r;
	        for(int c = 0; c < 3; c++) {
	            int cellsCol = left + c;
	            if(indexOk(cells, cellsRow, cellsCol)) {
	                nhood[r][c] = cells[cellsRow][cellsCol]; }
	            else {
	                nhood[r][c] = State.EMPTY;}
	            
	        }
		}
	    return nhood;
	}
	

	public static boolean isBurning(State[][] cells, int row, int col) {
		if(!indexOk(cells, row, col)) {
			throw new IllegalArgumentException();
		}
		return cells[row][col] == State.BURNING;
	}
	
	
	public static int numBurning(State[][] cells) {
		int rows = rowsIn(cells);
		int cols = colsIn(cells);
		int n = 0;
		for(int r = 0; r < rows; r++) {
			for(int c = 0; c < cols; c++) {
				if(isBurning(cells, r, c)) n++;
			}
		}
		return n;
	}
	
	
    /**
     * Tests if a tree spontaneously grows during one time step in a Forest-fire
     * simulation.
     * 
     * @return true if a tree spontaneously grows, false otherwise
     */
    public static boolean randomlyGrows() {
        double valueForGrowth = ForestFire.rng.nextDouble();
        if (valueForGrowth < PROB_GROWTH) {
        	return true;}
        else {
        	return false;}

    }
    
    
    /**
     * Tests if a tree burns after being struck by lightning during one time step in
     * a Forest-fire simulation.
     * 
     * @return true if a tree burns after being struck by lightning, false otherwise
     */
    public static boolean randomlyIgnites() {
        double valueForIgnites = ForestFire.rng.nextDouble();
        if (valueForIgnites < PROB_IGNITION) {
        	return true;}
        else {
        	return false;}

    }
    
    /**
     * Sets all of the cells of a Forest-fire model to a specified state.
     * 
     * <p>
     * 
     * @param cells the cells of a Forest-fire model
     */
    public static void fill(State[][] cells, State s) {
        int rows = ForestFire.rowsIn(cells);
        int cols = ForestFire.colsIn(cells);
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                cells[r][c] = s;
            }
        }
    }
    
    /**
     * Sets the cells of a Forest-fire model to a checkerboard pattern of
     * {@code State.EMPTY} and {@code State.OCCUPIED} cells. The state of the
     * upperleft-most cell {@code State.EMPTY}.
     * 
     * @param cells the cells of a Forest-fire model
     */
    public static void checkerBoard(State[][] cells) {
    	int rows = ForestFire.rowsIn(cells);
    	int cols = ForestFire.colsIn(cells);
        for (int i = 0; i < rows; i++) {
        	for (int j = 0; j < cols; j++) {
        		if ((i + j) % 2 == 0) {
        			cells[i][j] = State.EMPTY;}
        		else {
        			cells[i][j] = State.OCCUPIED; 
                }
            }
        }
  
    }
    /**
     * Updates {@code cells} so that it is equal to the next generation of cells.
     * 
     * <p>
     * 
     * @param cells the cells of a Forest-fire model
     */
    public static void update(State[][] cells) { 
    	int rows = rowsIn(cells);
    	int cols = colsIn(cells);
        State[][] copy = ForestFire.copy(cells);
        for (int i = 0; i < rows; i++) {            
        	for (int j = 0; j < cols; j++) {
        		State currentState = cells[i][j];
        		if (currentState == State.BURNING) {
        			currentState = State.EMPTY;
        			cells[i][j] = currentState;
        		}
        		else if (currentState == State.OCCUPIED) {
        			if (randomlyIgnites()) {
        				currentState = State.BURNING;
            			cells[i][j] = currentState;
        			} else {
        				State[][] closeCells = ForestFire.nMoore(copy,i,j);
            			int burning = numBurning(closeCells);
            			if (burning > 0) {
            				currentState = State.BURNING;
            				cells[i][j] = currentState;
            				}
        			}
        		}
        		else if (currentState == State.EMPTY && randomlyGrows()) {
        		 	currentState = State.OCCUPIED;
        			cells[i][j] = currentState;}	
            }
        }
    }
    /**
     * Returns a two-dimensional array of the row and column indexes corresponding
     * to the cells occupied by a tree in a Forest-fire model.
     * 
     * <p>
     * The returned array has two rows and n columns, where n is the number of cells
     * occupied by a tree in the Forest-fire model. The first row of the array
     * contains the row indexes of the occupied cells and the second row of the
     * array contains the column indexes of the occupied cells.
     * 
     * @param cells the cells of a Forest-fire model
     * @return a 2 x n array of row-column indexes of the cells occupied by a tree
     */
    public static int[][] occupiedIndexes(State[][] cells) {
        int numRows = rowsIn(cells);
        int numCols = colsIn(cells);
        
        
        int occupiedCount = 0;
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                if (cells[row][col] == State.OCCUPIED) {
                    occupiedCount++;
                }
            }
        }
        

        int[][] occupiedIndexes = new int[2][occupiedCount];
        
      
        int index = 0;
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                if (cells[row][col] == State.OCCUPIED) {
                    occupiedIndexes[0][index] = row;
                    occupiedIndexes[1][index] = col; 
                    index++;
                }
            }
        }
        
        return occupiedIndexes;
    }
    /**
     * Sets the cells of a Forest-fire model to a random pattern of
     * {@code State.EMPTY}, {@code State.OCCUPIED}, and {@code State.BURNING} cells.
     * 
     * <p>
     * 
     * @param percentNotEmpty the percentage of the total number of cells in the
     *                        model to set to State.OCCUPIED or State.BURNING
     * @param percentBurning  the percentage of the number of non-empty cells in the
     *                        model to set to State.BURNING
     * @param cells           the cells of a Forest-fire model
     * @throws IllegalArgumentException if percentNotEmpty or percentBurning is less
     *                                  than zero or greater than one
     */
    public static void randomize(double percentNotEmpty, double percentBurning, State[][] cells) {
    	ForestFire.fill(cells, State.EMPTY);
    	int numRows = rowsIn(cells);
    	int numCols = colsIn(cells);
    	int totalCells = numRows * numCols;
    	int numOccupiedCells = (int) (percentNotEmpty * totalCells);

    	Random random = new Random();
    	while (numOccupiedCells > 0) {
    		int row = random.nextInt(numRows);
    	    int col = random.nextInt(numCols);
    	    if (cells[row][col] == State.EMPTY) {
    	    cells[row][col] = State.OCCUPIED;
    	    numOccupiedCells--;
    	    }
    	}

    
    	int[][] occupiedIndexes = occupiedIndexes(cells);
    	int numBurningCells = (int) (percentBurning * occupiedIndexes[0].length);

    	 
    	while (numBurningCells > 0) {
    	    int randomIndex = random.nextInt(occupiedIndexes[0].length);
    	    int row = occupiedIndexes[0][randomIndex];
    	    int col = occupiedIndexes[1][randomIndex];
    	    if (cells[row][col] == State.OCCUPIED) {
    	    	cells[row][col] = State.BURNING;
    	        numBurningCells--;
    	        }
    	    }
    	}
}
        
        
    
