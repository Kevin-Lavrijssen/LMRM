package experiments;

import agents.Log;
import environments.packageDelivery.NoiseWrapperLabelingFunction;
import environments.packageDelivery.PackageDeliveryEnvironment;
import environments.packageDelivery.PackageDeliveryLabeling;
import environments.packageDelivery.PackageDeliveryRewardFunction_BC;
import environments.packageDelivery.PackageDeliveryRewardFunction_IO;
import environments.packageDelivery.PackageDeliveryRewardFunction_MPD;
import environments.packageDelivery.PackageDeliveryRewardFunction_SPD;

public class PackageDeliveryTests {

	public void run() {
	
		example1(); // Succesful delivery in all tasks
		example2(); // Delivers non matched product
		example3(); // Violates the constraint of bridge 1
		example4(); // Violqtes the constraint of bridge 2
		example5(); // Falls in the water for all tasks
		example6(); // Succesful delivery in all tasks
		example7(); // Delivers non matched product 
	}
	
	public void example1() {
		
		PackageDeliveryEnvironment spd = new PackageDeliveryEnvironment(0, new PackageDeliveryRewardFunction_SPD(), new PackageDeliveryLabeling());
		PackageDeliveryEnvironment bc = new PackageDeliveryEnvironment(0, new PackageDeliveryRewardFunction_BC(), new PackageDeliveryLabeling());
		PackageDeliveryEnvironment mpd = new PackageDeliveryEnvironment(0, new PackageDeliveryRewardFunction_MPD(), new PackageDeliveryLabeling());
		PackageDeliveryEnvironment io = new PackageDeliveryEnvironment(0, new PackageDeliveryRewardFunction_IO(2), new NoiseWrapperLabelingFunction(new PackageDeliveryLabeling(), 2));

		// Quick check to see whether final result is ok
		String[] example = new String[] {"West", "North", "PickUp", "East", "East", "East", "East", "Drop"};
		
		spd.reset(); int spdFinalReward = 0;
		bc.reset(); int bcFinalReward = 0;
		mpd.reset(); int mpdFinalReward = 0;
		io.reset(); int ioFinalReward = 0;
		
		for (int i=0; i<example.length; i++) {
			// ok!
			spdFinalReward = spd.execute(example[i]).getReward();
			
			// ok !
			bcFinalReward = bc.execute(example[i]).getReward();
			
			// ok!
			mpdFinalReward = mpd.execute(example[i]).getReward();
			
			// ok!
			ioFinalReward = io.execute(example[i]).getReward();
		}

		if(spdFinalReward != 1) {System.out.println("Example 1: SPD Failed");}
		if(bcFinalReward != 1) {System.out.println("Example 1: BC Failed");}
		if(mpdFinalReward != 1) {System.out.println("Example 1: MPD Failed");}
		if(ioFinalReward != 1) {System.out.println("Example 1: IO Failed");}
		
	}
	
	public void example2() {
		
		PackageDeliveryEnvironment spd = new PackageDeliveryEnvironment(0, new PackageDeliveryRewardFunction_SPD(), new PackageDeliveryLabeling());
		PackageDeliveryEnvironment bc = new PackageDeliveryEnvironment(0, new PackageDeliveryRewardFunction_BC(), new PackageDeliveryLabeling());
		PackageDeliveryEnvironment mpd = new PackageDeliveryEnvironment(0, new PackageDeliveryRewardFunction_MPD(), new PackageDeliveryLabeling());
		PackageDeliveryEnvironment io = new PackageDeliveryEnvironment(0, new PackageDeliveryRewardFunction_IO(2), new NoiseWrapperLabelingFunction(new PackageDeliveryLabeling(), 2));

		// Quick check to see whether final result is ok
		String[] example = new String[] {"West", "PickUp", "North", "East", "East", "East", "East", "Drop"};
		
		spd.reset(); int spdFinalReward = 0;
		bc.reset(); int bcFinalReward = 0;
		mpd.reset(); int mpdFinalReward = 0;
		io.reset(); int ioFinalReward = 0;
		
		for (int i=0; i<example.length; i++) {
			// ok!
			spdFinalReward = spd.execute(example[i]).getReward();
			
			// ok !
			bcFinalReward = bc.execute(example[i]).getReward();
			
			// ok!
			mpdFinalReward = mpd.execute(example[i]).getReward();
			
			// ok!
			ioFinalReward = io.execute(example[i]).getReward();
		}

		if(spdFinalReward != 1) {System.out.println("Example 2: SPD Failed");}
		if(bcFinalReward != 1) {System.out.println("Example 2: BC Failed");}
		if(mpdFinalReward != 0) {System.out.println("Example 2: MPD Failed");}
		if(ioFinalReward != 0) {System.out.println("Example 2: IO Failed");}
	}
	
	public void example3() {
		
		PackageDeliveryEnvironment spd = new PackageDeliveryEnvironment(0, new PackageDeliveryRewardFunction_SPD(), new PackageDeliveryLabeling());
		PackageDeliveryEnvironment bc = new PackageDeliveryEnvironment(0, new PackageDeliveryRewardFunction_BC(), new PackageDeliveryLabeling());
		PackageDeliveryEnvironment mpd = new PackageDeliveryEnvironment(0, new PackageDeliveryRewardFunction_MPD(), new PackageDeliveryLabeling());
		PackageDeliveryEnvironment io = new PackageDeliveryEnvironment(0, new PackageDeliveryRewardFunction_IO(2), new NoiseWrapperLabelingFunction(new PackageDeliveryLabeling(), 2));

		// Quick check to see whether final result is ok
		String[] example = new String[] {"West", "PickUp", "North", "North", "North", "North",  "East", "East", "East", "East", "Drop"};
		
		spd.reset(); int spdFinalReward = 0;
		bc.reset(); int bcFinalReward = 0;
		mpd.reset(); int mpdFinalReward = 0;
		io.reset(); int ioFinalReward = 0;
		
		for (int i=0; i<example.length; i++) {
			// ok!
			spdFinalReward = spd.execute(example[i]).getReward();
			
			// ok !
			bcFinalReward = bc.execute(example[i]).getReward();
			
			// ok!
			mpdFinalReward = mpd.execute(example[i]).getReward();
			
			// ok!
			ioFinalReward = io.execute(example[i]).getReward();
		}

		if(spdFinalReward != 1) {System.out.println("Example 3: SPD Failed");}
		if(bcFinalReward != -1) {System.out.println("Example 3: BC Failed");}
		if(mpdFinalReward != -1) {System.out.println("Example 3: MPD Failed");}
		if(ioFinalReward != -1) {System.out.println("Example 3: IO Failed");}
	}
	
	public void example4() {
		
		PackageDeliveryEnvironment spd = new PackageDeliveryEnvironment(0, new PackageDeliveryRewardFunction_SPD(), new PackageDeliveryLabeling());
		PackageDeliveryEnvironment bc = new PackageDeliveryEnvironment(0, new PackageDeliveryRewardFunction_BC(), new PackageDeliveryLabeling());
		PackageDeliveryEnvironment mpd = new PackageDeliveryEnvironment(0, new PackageDeliveryRewardFunction_MPD(), new PackageDeliveryLabeling());
		PackageDeliveryEnvironment io = new PackageDeliveryEnvironment(0, new PackageDeliveryRewardFunction_IO(2), new NoiseWrapperLabelingFunction(new PackageDeliveryLabeling(), 2));

		// Quick check to see whether final result is ok
		String[] example = new String[] {"West", "North", "North", "PickUp", "South",  "East", "East", "East", "East", "Drop"};
		
		spd.reset(); int spdFinalReward = 0;
		bc.reset(); int bcFinalReward = 0;
		mpd.reset(); int mpdFinalReward = 0;
		io.reset(); int ioFinalReward = 0;
		
		for (int i=0; i<example.length; i++) {
			// ok!
			spdFinalReward = spd.execute(example[i]).getReward();
			
			// ok !
			bcFinalReward = bc.execute(example[i]).getReward();
			
			// ok!
			mpdFinalReward = mpd.execute(example[i]).getReward();
			
			// ok!
			ioFinalReward = io.execute(example[i]).getReward();
		}

		if(spdFinalReward != 1) {System.out.println("Example 4: SPD Failed");}
		if(bcFinalReward != -1) {System.out.println("Example 4: BC Failed");}
		if(mpdFinalReward != -1) {System.out.println("Example 4: MPD Failed");}
		if(ioFinalReward != -1) {System.out.println("Example 4: IO Failed");}
	}
	
public void example5() {
		
		PackageDeliveryEnvironment spd = new PackageDeliveryEnvironment(0, new PackageDeliveryRewardFunction_SPD(), new PackageDeliveryLabeling());
		PackageDeliveryEnvironment bc = new PackageDeliveryEnvironment(0, new PackageDeliveryRewardFunction_BC(), new PackageDeliveryLabeling());
		PackageDeliveryEnvironment mpd = new PackageDeliveryEnvironment(0, new PackageDeliveryRewardFunction_MPD(), new PackageDeliveryLabeling());
		PackageDeliveryEnvironment io = new PackageDeliveryEnvironment(0, new PackageDeliveryRewardFunction_IO(2), new NoiseWrapperLabelingFunction(new PackageDeliveryLabeling(), 2));

		// Quick check to see whether final result is ok
		String[] example = new String[] {"East"};
		
		spd.reset(); int spdFinalReward = 0;
		bc.reset(); int bcFinalReward = 0;
		mpd.reset(); int mpdFinalReward = 0;
		io.reset(); int ioFinalReward = 0;
		
		for (int i=0; i<example.length; i++) {
			// ok!
			spdFinalReward = spd.execute(example[i]).getReward();
			
			// ok !
			bcFinalReward = bc.execute(example[i]).getReward();
			
			// ok!
			mpdFinalReward = mpd.execute(example[i]).getReward();
			
			// ok!
			ioFinalReward = io.execute(example[i]).getReward();
		}

		if(spdFinalReward != -1) {System.out.println("Example 5: SPD Failed");}
		if(bcFinalReward != -1) {System.out.println("Example 5: BC Failed");}
		if(mpdFinalReward != -1) {System.out.println("Example 5: MPD Failed");}
		if(ioFinalReward != -1) {System.out.println("Example 5: IO Failed");}
	}

	public void example6() {
		
		PackageDeliveryEnvironment spd = new PackageDeliveryEnvironment(0, new PackageDeliveryRewardFunction_SPD(), new PackageDeliveryLabeling());
		PackageDeliveryEnvironment bc = new PackageDeliveryEnvironment(0, new PackageDeliveryRewardFunction_BC(), new PackageDeliveryLabeling());
		PackageDeliveryEnvironment mpd = new PackageDeliveryEnvironment(0, new PackageDeliveryRewardFunction_MPD(), new PackageDeliveryLabeling());
		PackageDeliveryEnvironment io = new PackageDeliveryEnvironment(0, new PackageDeliveryRewardFunction_IO(2), new NoiseWrapperLabelingFunction(new PackageDeliveryLabeling(), 2));
	
		// Quick check to see whether final result is ok
		String[] example = new String[] {"West", "North","North","North","North", "PickUp", "East", "East", "East", "East", "North", "Drop"};
		
		spd.reset(); int spdFinalReward = 0;
		bc.reset(); int bcFinalReward = 0;
		mpd.reset(); int mpdFinalReward = 0;
		io.reset(); int ioFinalReward = 0;
		
		for (int i=0; i<example.length; i++) {
			// ok!
			spdFinalReward = spd.execute(example[i]).getReward();
			
			// ok !
			bcFinalReward = bc.execute(example[i]).getReward();
			
			// ok!
			mpdFinalReward = mpd.execute(example[i]).getReward();
			
			// ok!
			ioFinalReward = io.execute(example[i]).getReward();
		}
	
		if(spdFinalReward != 1) {System.out.println("Example 6: SPD Failed");}
		if(bcFinalReward != 1) {System.out.println("Example 6: BC Failed");}
		if(mpdFinalReward != 1) {System.out.println("Example 6: MPD Failed");}
		if(ioFinalReward != 1) {System.out.println("Example 6: IO Failed");}
		
	}

public void example7() {
	
	PackageDeliveryEnvironment spd = new PackageDeliveryEnvironment(0, new PackageDeliveryRewardFunction_SPD(), new PackageDeliveryLabeling());
	PackageDeliveryEnvironment bc = new PackageDeliveryEnvironment(0, new PackageDeliveryRewardFunction_BC(), new PackageDeliveryLabeling());
	PackageDeliveryEnvironment mpd = new PackageDeliveryEnvironment(0, new PackageDeliveryRewardFunction_MPD(), new PackageDeliveryLabeling());
	PackageDeliveryEnvironment io = new PackageDeliveryEnvironment(0, new PackageDeliveryRewardFunction_IO(2), new NoiseWrapperLabelingFunction(new PackageDeliveryLabeling(), 2));

	// Quick check to see whether final result is ok
	String[] example = new String[] {"West", "North","North","North","North", "PickUp", "East", "East", "East", "East", "Drop"};
	
	spd.reset(); int spdFinalReward = 0;
	bc.reset(); int bcFinalReward = 0;
	mpd.reset(); int mpdFinalReward = 0;
	io.reset(); int ioFinalReward = 0;
	
	for (int i=0; i<example.length; i++) {
		// ok!
		spdFinalReward = spd.execute(example[i]).getReward();
		
		// ok !
		bcFinalReward = bc.execute(example[i]).getReward();
		
		// ok!
		mpdFinalReward = mpd.execute(example[i]).getReward();
		
		// ok!
		ioFinalReward = io.execute(example[i]).getReward();
	}

	if(spdFinalReward != 1) {System.out.println("Example 7: SPD Failed");}
	if(bcFinalReward != 1) {System.out.println("Example 7: BC Failed");}
	if(mpdFinalReward != 0) {System.out.println("Example 7: MPD Failed");}
	if(ioFinalReward != 0) {System.out.println("Example 7: IO Failed");}
}

}
