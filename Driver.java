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
			
			System.out.println("usage: java -jar Swarm.jar nEpochs(int) nParticles(int) inertia max_velocity cognition social function(1|2|3)");
			System.exit(-1);
		}

		World world = new World(100, 100, nParticles, inertia, maxVel, cognition, social, function);
		PPM ppm = new PPM(100, 100);
		List<String> data = new ArrayList<String> ();
		data.add("epoch, avg_err_x, avg_err_y, avg_err_mag, abs_err, std_dev_avg, std_dev_best, %_within_0.1");
		
		int count;
		for (count = 0; count < nEpochs; count++) {
			world.update(ppm);
			ppm.write(count);
			if (!world.calcData(data, count)) break;
		}
	
		try (PrintWriter w = new PrintWriter(
								 String.format("raw/e%d_p%d_i%.2f_v%.2f_c%.2f_s%.2f_f%d.csv", 
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

		try (PrintWriter w = new PrintWriter(
								 String.format("raw/e%d_p%d_i%.2f_v%.2f_c%.2f_s%.2f_f%d_FINALPOS.csv", 
								 nEpochs,									  
								 nParticles, 
								 inertia, 
								 maxVel,
								 cognition, 
								 social, 
								 function))) {
			
			w.println("FINALPOS_X, FINALPOS_Y\n20, 7\n-20, -7");
			for (Particle p : world.particles) w.printf("%f, %f\n", p.pos[0], p.pos[1]);
		}

		catch (IOException e) {}
	}
}
