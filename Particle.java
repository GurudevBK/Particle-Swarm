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

public class Particle {
	
	private World     world;
	private double[]  vel;
	private double[]  best;
	public  double[]  pos;

	// constructor
	public Particle(World wrld, double[] initpos) {
		
		world  = wrld;
		pos	   = initpos.clone();
		best   = initpos.clone();;
		vel    = new double[2];
		vel[0] = 0;
		vel[1] = 0;
	}

	public void update(int func) {
		
		Random rand = new Random();
		
		// update velocity
		vel[0] = (world.inertia * vel[0]) + 
				 (best[0] - pos[0]) * (world.cognition * rand.nextDouble()) +
				 (world.best[0] - pos[0]) * (world.social * rand.nextDouble());
		
		vel[1] = (world.inertia * vel[1]) + 
				 (best[1] - pos[1]) * (world.cognition * rand.nextDouble()) +
				 (world.best[1] - pos[1]) * (world.social * rand.nextDouble());
		
		if ((Math.pow(vel[0], 2) + Math.pow(vel[1], 2)) > Math.pow(world.maxVel, 2)) {
			vel[0] = (world.maxVel/Math.hypot(vel[0], vel[1])) * vel[0];
			vel[1] = (world.maxVel/Math.hypot(vel[0], vel[1])) * vel[1];
		}

		// update position
		pos[0] += vel[0];
		pos[1] += vel[1];
		
		if (func == 1) {
			
			// update personal best
			if (Q1(pos) > Q1(best)) {
				best = pos.clone();
			}

			// update world best
			if (Q1(pos) > Q1(world.best)) {
				world.best = pos.clone();
			}
		} 
		
		else if (func == 2) {

			// update personal best
				if (Q2(pos) > Q2(best)) {
				best = pos.clone();
			}

			// update world best
			if (Q2(pos) > Q2(world.best)) {
				world.best = pos.clone();
			} 
		}
		
		else {

			// update personal best
				if (Q3(pos) > Q3(best)) {
				best = pos.clone();
			}

			// update world best
			if (Q3(pos) > Q3(world.best)) {
				world.best = pos.clone();
			} 
		}
	}

	public double Q1(double[] pos) {
		return 100 * (1 - (pDist(pos) / world.maxDist));
	}

	public double Q2(double[] pos) {
		return 9 * Math.max(0, 10 - Math.pow(pDist(pos), 2)) + 
			   10 * (1 - (pDist(pos) / world.maxDist)) + 
			   70 * (1 - (nDist(pos) / world.maxDist));
	}

	public double Q3(double[] pos) {
		return Math.cos(Math.sqrt(Math.abs(Math.pow(pos[0], 2) + Math.pow(pos[1], 2) + Math.sin(10.0 - pos[0])))) * Math.sqrt(Math.abs(Math.pow(pos[0], 2) + Math.pow(pos[1], 2)));
	}

	public double pDist(double[] pos) {
		return Math.hypot(pos[0] - 20, pos[1] - 7);
	}

	public double nDist(double[] pos) {
		return Math.hypot(pos[0] + 20, pos[1] + 7);
	}
}
