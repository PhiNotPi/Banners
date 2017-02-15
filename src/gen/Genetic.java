package gen;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.swing.*;

import gen.Color;
import gen.Shape;

public class Genetic {

	static double fitness(Flattened a, Flattened b) {
		double totaldif = 0;
		for (int row = 0; row < 40; row++) {
			double rowdif = 0;
			for (int col = 0; col < 20; col++) {
				// double dif = Math.pow(a.data[row][col][0] -
				// b.data[row][col][0], 2)
				// + Math.pow(a.data[row][col][1] - b.data[row][col][1], 2)
				// + Math.pow(a.data[row][col][2] - b.data[row][col][2], 2);
				double dif = Math.abs(a.data[row][col][0] - b.data[row][col][0])
						+ Math.abs(a.data[row][col][1] - b.data[row][col][1])
						+ Math.abs(a.data[row][col][2] - b.data[row][col][2]);
				rowdif += dif;
			}
			totaldif += rowdif;
		}
		return totaldif;
	}

	static double fitnessMultiRes(Flattened a, Flattened b) {
		return fitness5x5(a, b, 1) + fitness5x5(a, b, 2) / 1 + fitness5x5(a, b, 4) / 2 + fitness5x5(a, b, 5) / 2
				+ fitness5x5(a, b, 10) / 3 + fitness5x5(a, b, 20) / 4;
	}

	static double fitness5x5(Flattened a, Flattened b, int res) {
		int blocksize = res;
		double totaldif = 0;
		for (int row = 0; row < 40; row += blocksize) {
			double rowdif = 0;
			for (int col = 0; col < 20; col += blocksize) {
				int[] red = new int[256];
				int[] green = new int[256];
				int[] blue = new int[256];
				int[] redgreen = new int[512];
				int[] yellowblue = new int[768];
				int[] bright = new int[768];
				for (int x = col; x < col + blocksize; x++) {
					for (int y = row; y < row + blocksize; y++) {
						red[a.data[y][x][0]]++;
						green[a.data[y][x][1]]++;
						blue[a.data[y][x][2]]++;
						redgreen[a.data[y][x][0] + 256 - a.data[y][x][1]]++;
						yellowblue[a.data[y][x][0] + a.data[y][x][1] + 256 - a.data[y][x][2]]++;
						bright[a.data[y][x][0] + a.data[y][x][1] + a.data[y][x][2]]++;
						red[b.data[y][x][0]]--;
						green[b.data[y][x][1]]--;
						blue[b.data[y][x][2]]--;
						redgreen[b.data[y][x][0] + 256 - b.data[y][x][1]]--;
						yellowblue[b.data[y][x][0] + b.data[y][x][1] + 256 - b.data[y][x][2]]--;
						bright[b.data[y][x][0] + b.data[y][x][1] + b.data[y][x][2]]--;
					}
				}
				double dif = earthdistance(red) + earthdistance(green) + earthdistance(blue)
						+ earthdistance(redgreen) / 2 + earthdistance(yellowblue) / 3 + earthdistance(bright) / 3;
				rowdif += dif;
			}
			totaldif += rowdif;
		}
		return totaldif;
	}

	static double[][] fitnessBlocks(Flattened a, Flattened b, int res) {
		int blocksize = res;
		double[][] err = new double[40][20];
		for (int row = 0; row < 40; row += blocksize) {
			for (int col = 0; col < 20; col += blocksize) {
				int[] red = new int[256];
				int[] green = new int[256];
				int[] blue = new int[256];
				int[] redgreen = new int[512];
				int[] yellowblue = new int[768];
				int[] bright = new int[768];
				for (int x = col; x < col + blocksize; x++) {
					for (int y = row; y < row + blocksize; y++) {
						red[a.data[y][x][0]]++;
						green[a.data[y][x][1]]++;
						blue[a.data[y][x][2]]++;
						redgreen[a.data[y][x][0] + 256 - a.data[y][x][1]]++;
						yellowblue[a.data[y][x][0] + a.data[y][x][1] + 256 - a.data[y][x][2]]++;
						bright[a.data[y][x][0] + a.data[y][x][1] + a.data[y][x][2]]++;
						red[b.data[y][x][0]]--;
						green[b.data[y][x][1]]--;
						blue[b.data[y][x][2]]--;
						redgreen[b.data[y][x][0] + 256 - b.data[y][x][1]]--;
						yellowblue[b.data[y][x][0] + b.data[y][x][1] + 256 - b.data[y][x][2]]--;
						bright[b.data[y][x][0] + b.data[y][x][1] + b.data[y][x][2]]--;
					}
				}
				double dif = earthdistance(red) + earthdistance(green) + earthdistance(blue)
						+ earthdistance(redgreen) / 2 + earthdistance(yellowblue) / 3 + earthdistance(bright) / 3;
				for (int x = col; x < col + blocksize; x++) {
					for (int y = row; y < row + blocksize; y++) {
						err[y][x] = dif / (blocksize * blocksize);
					}
				}
			}
		}
		return err;
	}

	static double earthdistance(int[] a) {
		// MUST have total of 0
		int bal = 0;
		double total = 0;
		for (int i = 0; i < a.length; i++) {
			total += Math.abs(bal);
			bal += a[i];
		}
		return total;
	}

	static double earthdistance(int[] a, int[] b) {
		int atot = 0;
		for (int i = 0; i < a.length; i++) {
			atot += a[i];
		}
		double[] x = new double[a.length];
		for (int i = 0; i < a.length; i++) {
			x[i] += a[i] / atot;
		}
		// MUST have total of 0
		int bal = 0;
		double total = 0;
		for (int i = 0; i < a.length; i++) {
			total += Math.abs(bal);
			bal += a[i];
		}
		return total;
	}

	// static TargetImage ti = new TargetImage("spain.jpg");
	// static TargetImage ti = new TargetImage("apple.jpg");
	// static TargetImage ti = new TargetImage("unionjack.jpg");
	// static TargetImage ti = new TargetImage("7nPIzZg.jpg");
	static TargetImage ti = new TargetImage("roman.jpg");
	// static TargetImage ti = new TargetImage("gourds.jpg");
	// static TargetImage ti = new TargetImage("tree0.jpg");
	static JFrame ft = new JFrame("Target");

	static void paintTarget() {
		ft.add(ti.flat);
		ft.pack();
		ft.setVisible(true);
	}

	static JFrame fb = new JFrame("Banner");

	static JFrame fe = new JFrame("Error");

	static {
		ft.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		fb.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		fe.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	static Banner curRendered = null;

	public static void paintBanner(Banner b) {
		if (curRendered != null) {
			fb.remove(curRendered.flat);
		}
		curRendered = b;
		fb.add(b.flat);
		fb.pack();
		fb.setVisible(true);
	}

	static Flattened curRenderedErr = null;

	public static void paintBannerErr(Flattened b) {
		if (curRenderedErr != null) {
			fb.remove(curRenderedErr);
		}
		curRenderedErr = b;
		fb.add(b);
		fb.pack();
		fb.setVisible(true);
	}

	public static void main(String[] args) {
		paintTarget();
		Banner c = new Banner();
		// c.base = Color.BLUE;
		// c.layers[0] = new Layer(Color.WHITE, Shape.drs);
		// c.layers[1] = new Layer(Color.WHITE, Shape.dls);
		// c.layers[2] = new Layer(Color.RED, Shape.cr);
		// c.layers[3] = new Layer(Color.WHITE, Shape.ms);
		// c.layers[4] = new Layer(Color.WHITE, Shape.cs);
		// c.layers[5] = new Layer(Color.RED, Shape.sc);
		c.base = Color.RED;
		c.addLayer(new Layer(Color.WHITE, Shape.ss));
		// c.addLayer(new Layer(Color.BLUE, Shape.tl));
		c.calcImageData();
		// paintBanner(c);
		System.out.println(fitness(c.flat, ti.flat));
		System.out.println(fitnessMultiRes(c.flat, ti.flat));
		// System.out.println(c.flat.data[0][0][0]);
		// System.out.println(ti.flat.data[0][0][0]);

		// Banner seed1 = new Banner();
		// seed1.base = Color.RED;
		// seed1.addLayer(new Layer(Color.YELLOW, Shape.mr));
		// seed1.addLayer(new Layer(Color.YELLOW, Shape.ss));
		// seed1.addLayer(new Layer(Color.BLACK, Shape.cs));
		// seed1.addLayer(new Layer(Color.RED, Shape.sc));
		// seed1.addLayer(new Layer(Color.RED, Shape.bo));
		// seed1.addLayer(new Layer(Color.LIGHTGRAY, Shape.mc));
		// seed1.calcImageData();
		// pool.add(seed1);
		//
		// Banner seed2 = new Banner();
		// seed2.base = Color.RED;
		// seed2.addLayer(new Layer(Color.YELLOW, Shape.mr));
		// seed2.addLayer(new Layer(Color.YELLOW, Shape.ss));
		// seed2.addLayer(new Layer(Color.RED, Shape.cs));
		// seed2.addLayer(new Layer(Color.BLACK, Shape.bo));
		// seed2.addLayer(new Layer(Color.RED, Shape.bo));
		// seed2.addLayer(new Layer(Color.LIGHTGRAY, Shape.mc));
		// seed2.calcImageData();
		// pool.add(seed2);
		//
		// Banner seed3 = new Banner();
		// seed3.base = Color.RED;
		// seed3.addLayer(new Layer(Color.YELLOW, Shape.bo));
		// seed3.addLayer(new Layer(Color.YELLOW, Shape.cr));
		// seed3.addLayer(new Layer(Color.RED, Shape.cbo));
		// seed3.addLayer(new Layer(Color.RED, Shape.cbo));
		// seed3.addLayer(new Layer(Color.RED, Shape.cs));
		// seed3.addLayer(new Layer(Color.LIGHTGRAY, Shape.mc));
		// seed3.calcImageData();
		// pool.add(seed3);

		for (int i = pool.size(); i < poolsize; i++) {
			Banner x = Banner.getRand();
			x.calcImageData();
			// paintBanner(x);
			// System.out.println(x);
			pool.add(x);
		}

		double bestfitness = Double.MAX_VALUE;
		Banner bestbanner = null;
		for (int i = 0; i < 500000; i++) {
			// System.out.println(i);
			double totalfitness = 0;
			double worstcur = 0;
			for (Banner b : pool) {
				if (b.fitness == null) {
					b.fitness = fitnessMultiRes(b.flat, ti.flat);
					if (b.fitness < bestfitness) {
						bestfitness = b.fitness;
						bestbanner = b.clone();
						bestbanner.calcImageData();
						// try {
						// Thread.sleep(100);
						// } catch (InterruptedException e) {
						// // TODO Auto-generated catch block
						// e.printStackTrace();
						// }
						paintBanner(b);
						System.out.println(b.fitness + " " + b + " " + i);
					}
				}
				if (b.fitness > worstcur) {
					worstcur = b.fitness;
				}
				totalfitness += b.fitness;
			}

			try {
				Collections.sort(pool, (a, b) -> a.fitness < b.fitness ? -1 : a.fitness == b.fitness ? 0 : 1);
			} catch (Throwable e) {
			}

			// double averagefitness = totalfitness / pool.size();
			int keepsize = 20;
			for (int b = keepsize; b < pool.size(); b++) {
				if (rand.nextBoolean() || pool.get(b).fitness >= worstcur) {
					pool.remove(b);
					b--;
				}
			}
			// Banner seed = bestbanner.clone();
			// seed.calcImageData();
			// pool.add(seed);
			for (int j = 0; j < 1; j++) {
				Banner x = Banner.getRand();
				x.calcImageData();
				pool.add(x);
			}
			while (pool.size() < poolsize) {
				Banner child = merge(pool.get(rand.nextInt(pool.size())), pool.get(rand.nextInt(pool.size())));
				if (rand.nextBoolean()) {
					child = getMutation(child);
				}
				child.calcImageData();
				pool.add(child);
			}
		}
		System.out.println("DONE");
		for (Banner b : pool) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(b);
			paintBanner(b);
		}
	}

	static int poolsize = 50;
	static ArrayList<Banner> pool = new ArrayList<Banner>();

	static Random rand = new Random();

	static Banner merge(Banner x, Banner y) {
		if (x == y) {
			return x.clone();
		}
		Banner child = new Banner();
		if (rand.nextBoolean()) {
			child.base = x.base;
		} else {
			child.base = y.base;
		}
		int xtop = x.layercount;
		int ytop = y.layercount;
		while ((xtop > 0 || ytop > 0) && child.layercount < 6) {
			Banner curpar = x;
			if (ytop > 0) {
				curpar = y;
				if (xtop > 0) {
					if (rand.nextBoolean()) {
						curpar = x;
					}
				}
			}
			Layer select = null;
			if (curpar == x) {
				xtop--;
				select = x.layers[xtop];
			} else {
				ytop--;
				select = y.layers[ytop];
			}
			if (rand.nextBoolean()) {
				child.insertLayer(0, select.clone());
			}
		}
		return child;
	}

	static Banner getMutation(Banner orig) {
		Banner child = orig.clone();
		switch (rand.nextInt(4)) {
		case 0: {
			int layer = rand.nextInt(orig.layercount + 1) - 1;
			if (layer == -1) {
				child.base = Color.getRand();
			} else {
				boolean feature = rand.nextBoolean();
				if (feature) {
					child.layers[layer].color = Color.getRand();
				} else {
					child.layers[layer].shape = Shape.getRand();
				}
			}
			break;
		}
		case 1: {
			int layer = rand.nextInt(6);
			child.insertLayer(layer, Layer.getRand());
			break;
		}
		case 2: {
			int layer = rand.nextInt(6);
			child.deleteLayer(layer);
			break;
		}
		case 3: {
			int layer = rand.nextInt(5);
			if (child.layers[layer + 1] != null) {
				Layer temp = child.layers[layer];
				child.layers[layer] = child.layers[layer + 1];
				child.layers[layer + 1] = temp;
			}
			break;
		}
		}
		return child;
	}

}
