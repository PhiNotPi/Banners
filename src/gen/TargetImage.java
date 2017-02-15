package gen;

import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.imageio.*;
import javax.swing.*;

public class TargetImage {

	public static void main(String[] args) {

		JFrame f = new JFrame("Target");

		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		TargetImage ti = new TargetImage("7nPIzZg.jpg");
		f.add(ti.flat);
		f.pack();
		f.setVisible(true);
	}

	public TargetImage(String filename) {
		try {
			origimg = ImageIO.read(new File(filename));
			calcImageData();
		} catch (IOException e) {
		}
	}

	public BufferedImage origimg;
	public Flattened flat = new Flattened();

	public static Random rand = new Random();

	public void calcImageData() {
		for (int x = 0; x < 20; x++) {
			for (int y = 0; y < 40; y++) {
				Set<Color> colors = new HashSet<Color>();
				Color found = null;
				int b = -1;
				int g = -1;
				int r = -1;
				while (found == null) {
					int minx = x * origimg.getWidth() / 20;
					int miny = y * origimg.getHeight() / 40;
					int maxx = (x + 1) * origimg.getWidth() / 20;
					int maxy = (y + 1) * origimg.getHeight() / 40;
					int randx = rand.nextInt(maxx - minx) + minx;
					int randy = rand.nextInt(maxy - miny) + miny;
					int dec = origimg.getRGB(randx, randy);
					// if (x == 0 && y == 0) {
					// System.out.println("dec" + dec);
					// }
					b = dec & 255;
					g = (dec & (256 * 255)) / 256;
					r = (dec & (256 * 256 * 255)) / (256 * 256);
					Color closest = Color.getClosest(r, g, b);
					if (colors.contains(closest)) {
						found = closest;
					} else {
						colors.add(closest);
					}
				}
				flat.data[y][x][0] = r;
				flat.data[y][x][1] = g;
				flat.data[y][x][2] = b;
			}
		}
	}
}
