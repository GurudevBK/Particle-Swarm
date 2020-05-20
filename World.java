/*
MIT License

Copyright (c) 2020 Gurudev Ball-Khalsa

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

import java.util.*;
import java.lang.Math.*;
import java.io.*;

public class World {
	
	public int			max_x;
	public int			max_y;
	public int			nParticles;
	public double		maxDist;
	public double		inertia;
	public double		cognition;
	public double		social;
	public double		maxVel;
	public double[]		best;
	public int			function; // 1 for Q1, 2 for Q2
	
	public List<String>	  data;
	public List<Particle> particles;

	public World (int mX, 
				  int mY, 
				  int nP, 
				  double inrt, 
				  double mv,
				  double cogn, 
				  double soc, 
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
		function    = func;
		particles   = new ArrayList<Particle> ();

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
			} else if (function == 3) {
				if (particles.get(0).Q3(init) > particles.get(0).Q3(best)) best = init.clone();
			} else {
				System.out.println("fatal error: function must be 1, 2, or 3");
				System.exit(-1);
			}
		}
	}

	public void update(PPM ppm) {
		for (Particle p : particles) p.update(function);
		ppm.clear();

		for (Particle p : particles) {
			int row = (int) Math.floor(p.pos[0] + (max_y / 2));
			int col = (int) Math.floor(p.pos[1] + (max_x / 2));
			ppm.addDot(col, row);
		}

		
	}

	public boolean calcData(List<String> data, int epoch) {
		//epoch, avg_err_x, avg_err_y, avg_err_mag, abs_err, std_dev_avg, std_dev_best, %_within_0.001

		double epsilon = 0;
		double[] avg   = new double[2];
		double[] err   = new double[2];
		double abs_err = 0;
		double std_dev_avg  = 0;
		double std_dev_best = 0;

		avg[0] = 0;
		avg[1] = 0;
		err[0] = 0;
		err[1] = 0;

		// avg all vectors and errors relative to best particle
		for (Particle p : particles) {
			
			//avg
			avg[0] += p.pos[0] / nParticles;
			avg[1] += p.pos[1] / nParticles;
			
			//error with respect to best particle
			err[0] += Math.pow((p.pos[0] - best[0]), 2);
			err[1] += Math.pow((p.pos[1] - best[1]), 2);
			
			//number of particles within 0.1 of global maximum
			if (Math.hypot(20 - p.pos[0], 7 - p.pos[1]) < 0.1) epsilon++;
		}

		// "absolute error" calculated relative to known global maximum (irrelevant with Q3)
		abs_err = Math.hypot(20 - avg[0], 7 - avg[1]);
		
		//calculate error vector
		err[0] = Math.sqrt((1.0 / (2 * nParticles) * err[0]));
		err[1] = Math.sqrt((1.0 / (2 * nParticles) * err[1]));

		//calculate standard deviation from best particle
		for (Particle p : particles) {
			std_dev_avg  += Math.pow(Math.hypot(p.pos[0] - avg[0], p.pos[1] - avg[1]), 2) / nParticles; 
			std_dev_best += Math.pow(Math.hypot(p.pos[0] - best[0], p.pos[1] - best[1]), 2) / nParticles; 
		}
		
		std_dev_avg  = Math.sqrt(std_dev_avg);
		std_dev_best = Math.sqrt(std_dev_best);
		
		data.add(String.format("%d, %f, %f, %f, %f, %f, %f, %f", 
							   epoch,						
							   err[0],						
							   err[1],						
							   Math.hypot(err[0], err[1]),
							   abs_err,
							   std_dev_avg,
							   std_dev_best,
							   100.0 * (epsilon / nParticles)));

		//stopping condition
		if (std_dev_best < 0.02)
			return false;
		else 
			return true;
	}
}
