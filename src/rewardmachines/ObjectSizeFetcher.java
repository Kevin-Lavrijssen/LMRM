package rewardmachines;
import java.lang.instrument.Instrumentation;

public final class ObjectSizeFetcher {
	
    private static Instrumentation instrumentation;

    public static void premain(String args, Instrumentation inst) {
        instrumentation = inst;
    }

    public static long getObjectSize(Object o) {
        return instrumentation.getObjectSize(o);
    }
}
