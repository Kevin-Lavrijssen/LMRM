package environments.packageDelivery;

import java.util.Random;

import environments.IWorld;

public class PackageDeliveryWorld implements IWorld{
	
	public PackageDeliveryWorld(String[] actions){
		this.actions = actions;
		currentX = 2;
		currentY = 1;
		maxX = 5;
		maxY = 6;
	}
	
	int currentX;
	int currentY;
	
	int maxX;
	int maxY;
	
	String[] actions;
	
	Random r;
	
	@Override
	public int[] execute(String action) {
		
		if (action.equals("North") && currentY < maxY) {currentY++; return new int[] {currentX, currentY};}
		else if (action.equals("East") && currentX < maxX) {currentX++; return new int[] {currentX, currentY};}
		else if (action.equals("South") && currentY > 1) {currentY--; return new int[] {currentX, currentY};}
		else if (action.equals("West") && currentX > 1) {currentX--; return new int[] {currentX, currentY};}
	
		// Only moving results in a different world state
		return new int[] {currentX, currentY};
		
	}

	@Override
	public void reset() {
		currentX = 2;
		currentY = 1;
	}
	
}
