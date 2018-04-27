public class Driver {
	
	public static void main(String[] args) {

		if (args.length != 7) {
			System.out.println("usage: [prog] x-max y-max numParticles inertia cognition social maxVelocity");
			System.exit(-1);
		}

		for (String s : args) System.out.println(s);

		World world = new World(Integer.parseInt(args[0]),
								Integer.parseInt(args[1]),
								Integer.parseInt(args[2]),
								Double.parseDouble(args[3]),
								Double.parseDouble(args[4]),
								Double.parseDouble(args[5]),
								Double.parseDouble(args[6]));

		for (int i = 0; i < 100; i++) {
			world.update();
//			System.out.printf("best: [%f, %f]\n", world.best[0], world.best[1]);
		}
	}
}
