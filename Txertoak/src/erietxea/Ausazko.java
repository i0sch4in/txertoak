package erietxea;

import java.util.concurrent.ThreadLocalRandom;

public class Ausazko {
	public static int random(int min, int max) {
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}
}