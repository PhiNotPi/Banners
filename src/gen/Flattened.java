package gen;

import java.awt.*;
import java.awt.image.*;

public class Flattened extends Component {

	private static final long serialVersionUID = 2043599135918678273L;

	public int[][][] data = new int[40][20][3];

	BufferedImage img;

	public void paint(Graphics g) {
		calcImage();
		g.drawImage(img, 0, 0, null);
	}

	public Dimension getPreferredSize() {
		if (img == null) {
			return new Dimension(240, 480);
		} else {
			return new Dimension(img.getWidth(null), img.getHeight(null));
		}
	}

	public void calcImage() {

		img = new BufferedImage(240, 480, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < 240; x++) {
			for (int y = 0; y < 480; y++) {
				int r = data[y / 12][x / 12][0];
				int g = data[y / 12][x / 12][1];
				int b = data[y / 12][x / 12][2];
				img.setRGB(x, y, (r * 256 + g) * 256 + b);
			}
		}

	}
}
