package gen;

public class Layer {

	public Color color;
	public Shape shape;

	public Layer(Color color, Shape shape) {
		this.color = color;
		this.shape = shape;
	}

	public static Layer getRand() {
		return new Layer(Color.getRand(), Shape.getRand());
	}

	public String toString() {
		return color.toString() + "-" + shape.toString();
	}

	public Layer clone() {
		return new Layer(color, shape);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Layer other = (Layer) obj;
		if (color != other.color)
			return false;
		if (shape != other.shape)
			return false;
		return true;
	}

}
