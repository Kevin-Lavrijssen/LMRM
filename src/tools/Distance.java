package tools;

public final class Distance {

	public static double minkowski(int[] x, int[] y, int r) {
		if(x.length!=y.length) {throw new IllegalArgumentException("Traces must be of the same length for Minkowski");}
		double distance = 0;
		
		for(int i=0; i<x.length;i++) {
			int abs = Math.abs(x[i]-y[i]);
			double power = Math.pow(abs, r);
			distance+=power;
		}
		
		return Math.pow(distance, 1.0/r);
	}
	
	public static int mispredictions(int[] x, int[] y) {
		if(x.length!=y.length) {throw new IllegalArgumentException("Traces must be of the same length for mispredictions");}
		int mispredictions=0;
		
		for(int i=0; i<x.length;i++) {
			if(x[i]!=y[i]) {mispredictions+=1;}
		}
		
		return mispredictions;
	}
	
}
