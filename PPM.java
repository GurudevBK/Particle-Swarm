import java.io.*;
import java.util.*;

public class PPM {

	private String	  header;
	private int		  height;
	private int		  width;
	private int		  max;
	private int[][]   pic;

	public PPM(int width, int height) {
		pic = new int[width][height];
		this.width = width;
		this.height = height;
		max = 1;

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				pic[i][j] = 0;
			}
		}
	}

	public void addDot(int row, int col) {
		pic[row][col] = 1;
	}

	public void clear() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				pic[i][j] = 0;
			}
		}
	}

	public void print() {
		
		PrintStream o = System.out;
	
		o.print("P1\n");
		o.printf("%d %d", height * 5, width * 5);
		o.printf("%d", max);
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < 5; j++) {
				for (int k = 0; k < width; k++) {
					for (int l = 0; l < 5; l++) {
						o.printf("%d\n", pic[i][k]);
					}
				}
			}
		}
	}
	
	public void write(int count) {
		
		try (PrintWriter w = new PrintWriter(String.format("frame%05d.pgm", count))) {
			
			w.println("P1");
			w.println(String.format("%d %d", height * 5, width * 5));
			w.println(Integer.toString(max));
			
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < 5; j++) {
					for (int k = 0; k < width; k++) {
						for (int l = 0; l < 5; l++) {
							w.print(Integer.toString(pic[i][k]) + " ");
						}
						w.println();
					}
				}
			}
		}

		catch (IOException e) {}
	}
}
