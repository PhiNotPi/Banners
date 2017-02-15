package gen;

import java.io.File;
import java.util.Random;
import java.util.Scanner;

public enum Shape {
	// blank("blank.txt"), solid("solid.txt"),
	ls("ls.txt"), rs("rs.txt"), cs("cs.txt"), bs("bs.txt"), ts("ts.txt"), ms("ms.txt"), sc("sc.txt"), ss("ss.txt"), vh(
			"vh.txt"), drs("drs.txt"), dls("dls.txt"), cr("cr.txt"), vhr("vhr.txt"), hh("hh.txt"), hhb("hhb.txt"), tl(
					"tl.txt"), bl("bl.txt"), mc("mc.txt"), bo("bo.txt"), mr("mr.txt"), cbo(
							"cbo.txt"), flo("flo.txt"), tt("tt.txt"), bt("bt.txt"), tts("tts.txt"), bts("bts.txt");

	private Shape(String filename) {
		arr = readFile(filename);
	}

	public static void main(String[] args) {
		System.out.println(Shape.ss);
	}

	int[][] arr = new int[40][20];

	public static int[][] readFile(String filename) {
		try {
			Scanner in = new Scanner(new File(filename));
			int[][] arr = new int[40][20];

			for (int row = 0; row < 40 && in.hasNextLine(); row++) {
				char[] line = in.nextLine().toCharArray();
				for (int col = 0; col < 20 && col < line.length; col++) {
					if (line[col] == '4') {
						arr[row][col] = 4;
					}
					if (line[col] == '3') {
						arr[row][col] = 3;
					}
					if (line[col] == '2') {
						arr[row][col] = 2;
					}
					if (line[col] == '1') {
						arr[row][col] = 1;
					}
				}

			}

			in.close();
			return arr;
		} catch (Throwable e) {
			return null;
		}
	}

	public String toFile() {
		String res = "";
		for (int row = 0; row < 40; row++) {
			for (int col = 0; col < 20; col++) {
				res += arr[row][col];
			}
			res += "\n";
		}
		return res;
	}

	public static Random rand = new Random();

	public static Shape getRand() {
		return Shape.values()[rand.nextInt(Shape.values().length)];
	}
}
