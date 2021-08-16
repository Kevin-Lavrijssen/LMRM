package environments.blockSorting;
import java.util.Random;

import environments.IWorld;

public class BlockSortingWorld implements IWorld{

	public BlockSortingWorld(int nTypes, int nBinsPerType, int nSourcesPerType, double nondeterminism, int xSize, int ySize, String[] actions) {
		
		this.maxX = xSize;
		this.maxY = ySize;
		this.nTypes = nTypes;
		this.nondeterminism=nondeterminism;
		this.actions=actions;
		this.r = new Random();
		
		this.startX = r.nextInt(xSize);
		this.startY = r.nextInt(ySize);
		
		this.currentX = startX;
		this.currentY = startY;
		
		this.tileTypes = new int[xSize][ySize];
		
		int setBins=0;
		while(setBins <= nBinsPerType) {
			for (int i=1; i<=nTypes; i++) {
				while(true) {
					int x = r.nextInt(xSize);
					int y = r.nextInt(ySize);
					if(tileTypes[x][y]==0) {tileTypes[x][y] = i; break;}
				}
			}
			setBins++;
		}
		
		int setSources=0;
		while(setSources <= nSourcesPerType) {
			for (int i=1; i<=nTypes; i++) {
				while(true) {
					int x = r.nextInt(xSize);
					int y = r.nextInt(ySize);
					if(tileTypes[x][y]==0) {tileTypes[x][y] = -i; break;}
				}
			}
			setSources++;
		}
		
	}

	private int maxX;
	private int maxY;
	private int nTypes;
	private double nondeterminism;
	private Random r;
	private String[] actions;
	
	private int startX;
	private int startY;
	
	private int currentX;
	private int currentY;
	
	/**
	 * Matrix representing tile types
	 * ...
	 *  1: Block of type 1
	 *  0: Empty tile
	 * -1: Bin for type one blocks
	 * ...
	 * 
	 */
	private int[][] tileTypes;
	
	@Override
	public int[] execute(String action) {
		double p = r.nextDouble();
		if(p<nondeterminism) {action=actions[r.nextInt(actions.length)];}
		
		if(action.equals("North") && isValid(currentX, currentY-1)) {
			currentY--;
			return new int[] {currentX, currentY, getTileType()};
		}
		
		if(action.equals("East") && isValid(currentX+1, currentY)) {
			currentX++;
			return new int[] {currentX, currentY, getTileType()};
		}
		
		if(action.equals("South") && isValid(currentX, currentY+1)) {
			currentY++;
			return new int[] {currentX, currentY, getTileType()};
		}
		
		if(action.equals("West") && isValid(currentX-1, currentY)) {
			currentX--;
			return new int[] {currentX, currentY, getTileType()};
		}
		
		
		// Only moving results in a different world state
		return new int[] {currentX, currentY, getTileType()};
		
	}

	private int getTileType() {
		return tileTypes[currentX][currentY];
	}

	@Override
	public void reset() {
		currentX = startX;
		currentY = startY;
	}
	
	private boolean isValid(int x, int y) {
		return x>=0 && y>= 0 && x< maxX && y < maxY;
	}

}
