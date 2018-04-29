import java.util.*;
import java.lang.Math.*;
import java.io.*;

public class World {
	
	public int	      max_x;
	public int	      max_y;
	public int		  nParticles;
	public double	  maxDist;
	public double	  inertia;
	public double	  cognition;
	public double     social;
	public double	  maxVel;
	public double[]	  best;
	public int		  function; // 1 for Q1, 2 for Q2

	private List<Particle> particles;

	public World (int mX, 
				  int mY, 
				  int nP, 
				  double inrt, 
				  double cogn, 
				  double soc, 
				  double mv,
				  int	 func) {

		max_x	    = mX;
		max_y	    = mY;
		maxDist	    = Math.hypot((double)max_x, (double)max_y) / 2;
		inertia     = inrt;
		cognition   = cogn;
		social		= soc;
		maxVel      = mv;
		nParticles  = nP;
		best		= new double[2];
		best[0]		= 0;
		best[1]		= 0;
		particles   = new ArrayList<Particle> ();
		function    = func;

		int i = 0;
		double[] init = new double[2];
		Random rand = new Random();
		for (i = 0; i < nParticles; i++) {
			
			init[0] = (rand.nextDouble() * max_x) - (max_x / 2);
			init[1] = (rand.nextDouble() * max_y) - (max_y / 2);
			particles.add(new Particle(this, init));
			
			if (function == 1) {
				if (particles.get(0).Q1(init) > particles.get(0).Q1(best)) best = init.clone();
			} else if (function == 2) {
				if (particles.get(0).Q2(init) > particles.get(0).Q2(best)) best = init.clone();
			} else {
				System.out.println("fatal error: function must be 1 or 2");
				System.exit(-1);
			}
		}
	}

	public void update(PPM ppm) {
		for (Particle p : particles) p.update(function);
		ppm.clear();

		for (Particle p : particles) {
			int row = (int) Math.floor(p.pos()[0] + (max_y / 2));
			int col = (int) Math.floor(p.pos()[1] + (max_x / 2));
			ppm.addDot(col, row);
		}
	}
}
