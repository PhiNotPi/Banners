package gen;

import java.util.Random;

public enum Color {
	// RED(10040115), BLUE(3361970), GREEN(6717235), YELLOW(15066419),
	// WHITE(16777215), BLACK(1644825), LIGHTGRAY(
	// 10066329);
	RED(10040115), BLUE(3361970), WHITE(16777215), GREEN(6717235), YELLOW(15066419), BLACK(1644825), ORANGE(
			14188339), LIGHTGRAY(10066329), GRAY(5000268), BROWN(6704179);
	public int dec;
	public int r;
	public int g;
	public int b;

	private Color(int val) {
		dec = val;
		b = val % 256;
		val = (val - b) / 256;
		g = val % 256;
		val = (val - g) / 256;
		r = val % 256;
	}

	public static Random rand = new Random();

	public static Color getRand() {
		return Color.values()[rand.nextInt(Color.values().length)];
	}

	public static Color getClosest(int r, int g, int b) {
		double bestscore = Double.MAX_VALUE;
		Color bestcolor = null;
		for (Color c : Color.values()) {
			double score = Math.abs(c.r - r) + Math.abs(c.g - g) + Math.abs(c.b - b) + Math.abs(c.r - c.g - r + g)
					+ Math.abs(c.r + c.g - c.b - r - g + b) / 2 + Math.abs(c.r + c.g + c.b - r - g - b) / 3;
			if (score < bestscore) {
				bestcolor = c;
				bestscore = score;
			}
		}
		return bestcolor;
	}
}
