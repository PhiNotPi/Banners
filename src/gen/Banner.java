package gen;

import java.awt.event.*;
import java.util.Random;

import javax.swing.*;

public class Banner {

	public static void main(String[] args) {

		JFrame f = new JFrame("Banner");

		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		Banner b = new Banner();
		// b.base = Color.BLUE;
		b.layers[0] = new Layer(Color.WHITE, Shape.drs);
		b.layers[1] = new Layer(Color.WHITE, Shape.dls);
		b.layers[2] = new Layer(Color.RED, Shape.cr);
		b.layers[3] = new Layer(Color.WHITE, Shape.ms);
		b.layers[4] = new Layer(Color.WHITE, Shape.cs);
		b.layers[5] = new Layer(Color.RED, Shape.sc);
		b.calcImageData();
		f.add(b.flat);
		f.pack();
		f.setVisible(true);
	}

	public Color base;
	public Layer[] layers = new Layer[6];
	public int layercount = 0;

	public Flattened flat = new Flattened();

	public Double fitness = null;
	public static boolean transparency = true;

	public void calcImageData() {
		for (int x = 0; x < 20; x++) {
			for (int y = 0; y < 40; y++) {
				int r = base.r;
				int g = base.g;
				int b = base.b;
				for (int l = 0; l < 6; l++)
					if (layers[l] != null) {
						int maxo = 4;
						int opacity = layers[l].shape.arr[y][x];
						if (transparency) {
							r = (r * (maxo - opacity) + layers[l].color.r * opacity) / maxo;
							g = (g * (maxo - opacity) + layers[l].color.g * opacity) / maxo;
							b = (b * (maxo - opacity) + layers[l].color.b * opacity) / maxo;
						} else {
							if (opacity >= maxo / 2) {
								r = layers[l].color.r;
								g = layers[l].color.g;
								b = layers[l].color.b;
							}
						}
					}
				flat.data[y][x][0] = r;
				flat.data[y][x][1] = g;
				flat.data[y][x][2] = b;
			}
		}
	}

	public void addLayer(Layer l) {
		if (layers[5] == null) {
			layercount++;
		}
		for (int i = 0; i < 6; i++) {
			if (layers[i] == null) {
				layers[i] = l;
				break;
			}
		}
	}

	public void insertLayer(int index, Layer l) {
		if (layers[index] == null) {
			addLayer(l);
		} else {
			if (layers[5] == null) {
				layercount++;
			}
			for (int i = index; i < 6; i++) {
				Layer swap = layers[i];
				layers[i] = l;
				l = swap;
			}
		}
	}

	public void deleteLayer(int index) {
		if (layers[index] != null) {
			layercount--;
		}
		for (int i = index; i < 5; i++) {
			layers[i] = layers[i + 1];
		}
		layers[5] = null;
	}

	public void removeLayer() {
		if (layers[0] != null) {
			layercount--;
		}
		for (int i = 5; i >= 0; i--) {
			if (layers[i] != null) {
				layers[i] = null;
				break;
			}
		}
	}

	public static Random rand = new Random();

	public static Banner getRand() {
		Banner res = new Banner();
		res.base = Color.getRand();
		int numlayers = rand.nextInt(7);
		for (int i = 0; i < numlayers; i++) {
			res.addLayer(Layer.getRand());
		}
		return res;
	}

	public String toString() {
		String res = layercount + " " + base.toString();
		for (int i = 0; i < 6 && layers[i] != null; i++) {
			res += " " + layers[i].toString();
		}
		return res;
	}

	public Banner clone() {
		Banner res = new Banner();
		res.base = base;
		for (int i = 0; i < 6; i++) {
			if (layers[i] != null) {
				res.layers[i] = layers[i].clone();
			}
		}
		res.layercount = layercount;
		return res;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Banner other = (Banner) obj;
		if (base != other.base)
			return false;
		for (int i = 0; i < 6; i++) {
			if (layers[i] != other.layers[i])
				return false;
		}
		return true;
	}
}
