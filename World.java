import java.util.*;
import java.lang.Math.*;

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

	private List<Particle> particles;

	public World (int mX, 
				  int mY, 
				  int nP, 
				  double inrt, 
				  double cogn, 
				  double soc, 
				  double mv) {

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
		
		int i = 0;
		double[] init = new double[2];
		Random rand = new Random();
		for (i = 0; i < nParticles; i++) {
			
			init[0] = (rand.nextDouble() * max_x) - (max_x / 2);
			init[1] = (rand.nextDouble() * max_y) - (max_y / 2);
			particles.add(new Particle(this, init));
			
			if (particles.get(0).Q1(init) > particles.get(0).Q1(best)) best = init.clone();
		}
	}

	public void update() {
		for (Particle p : particles) p.update();
	}
}
