import java.util.*;
import java.io.*;

class Driver {

	public static void main(String[] args) {

		int	   nEpochs	  = 0;
		int	   nParticles = 0;
		double inertia 	  = 0;
		double maxVel     = 0;
		double cognition  = 0;
		double social	  = 0;
		int	   function   = 0;

		if (args.length == 7) {
			
			nEpochs	   = Integer.parseInt(args[0]);
			nParticles = Integer.parseInt(args[1]);
			inertia    = Double.parseDouble(args[2]);
			maxVel     = Double.parseDouble(args[3]);
			cognition  = Double.parseDouble(args[4]);
			social	   = Double.parseDouble(args[5]);
			function   = Integer.parseInt(args[6]);

		} else {
			
			System.out.println("usage: java -jar Swarm.jar nEpochs(int) nParticles(int) inertia max_velocity cognition social function(1 or 2)");
			System.exit(-1);
		}

		for (String s : args) System.out.println(s);

		World world = new World(200, 200, nParticles, inertia, cognition, social, maxVel, function);
		PPM ppm = new PPM(200, 200);
		List<String> data = new ArrayList<String> ();
		data.add("epoch, avg_err, std_dev, n_within_0.0001, converged");
		
		for (int i = 0; i < nEpochs; i++) {
			world.update(ppm);
			ppm.write(i);
			if (!world.calcData(data, i)) break;
		}
	
		try (PrintWriter w = new PrintWriter(
								 String.format( "e%d_p%d_i%f_v%f_c%f_s%f_f%d.csv", 
								 nEpochs,									  
								 nParticles, 
								 inertia, 
								 maxVel,
								 cognition, 
								 social, 
								 function))) {
			
			for (String s : data) w.println(s);
		}

		catch (IOException e) {}
	}
}
