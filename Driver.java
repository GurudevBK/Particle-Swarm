public class Driver {
	
	public static void main(String[] args) {

		int	   nEpochs	  = 0;
		int	   nParticles = 0;
		double inertia 	  = 0;
		double cognition  = 0;
		double social	  = 0;
		int	   function   = 1;

		if (args.length == 6) {
			
			nEpochs	   = Integer.parseInt(args[0]);
			nParticles = Integer.parseInt(args[1]);
			inertia    = Double.parseDouble(args[2]);
			cognition  = Double.parseDouble(args[3]);
			social	   = Double.parseDouble(args[4]);
			function   = Integer.parseInt(args[5]);

		} else {
			
			System.out.println("usage: [prog] nEpochs nParticles inertia cognition social function");
			System.exit(-1);
		}

		for (String s : args) System.out.println(s);

		World world = new World(100, 
								100, 
								nParticles, 
								inertia, 
								cognition, 
								social, 
								4, 
								function);

		PPM ppm = new PPM(100, 100);
		
		for (int i = 0; i < nEpochs; i++) {
			world.update(ppm, i);
//			ppm.write(i);
		}
	}
}
