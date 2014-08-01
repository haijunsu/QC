import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * CS700 project: 3-dimensional matching: Let X, Y, and Z be finite, disjoint
 * sets, and let T be a subset of X ¡Á Y ¡Á Z. That is, T consists of triples (x,
 * y, z) such that x in X,y in Y, and z in Z. Now M is subset of T is a
 * 3-dimensional matching if the following holds: for any two distinct triples
 * (x1, y1, z1) in M and (x2, y2, z2) in M, we have x1 <> x2, y1 <> y2, and z1
 * <> z2.
 * 
 * This solution can accept three sets from an input file. One set is one line
 * and members are separated by a blank.
 * 
 * Output file stores the solution result.
 * 
 * Compile: javac -cp . ThreeDimensionalMatching.java
 * 
 * Usage: java -cp . ThreeDimensionalMatching <input file> <output file>
 * 
 * java -cp . -DisDebug=true ThreeDimensionalMatching <input file> <output file>
 * 
 * @author Haijun Su 2014 summer
 *
 */
public class ThreeDimensionalMatching {

	private static boolean isDebug = false;

	public static void main(String[] args) {
		isDebug = "true".equalsIgnoreCase(System.getProperty("isDebug"));
		// Read data from file
		if (args.length != 2) {
			System.out
					.println("Usage: java -cp . ThreeDimensionalMatching <input file> <output file>");
			System.exit(1); // input error and exit
		}
		BufferedReader br = null;
		PrintWriter writer = null;
		try {
			br = new BufferedReader(new FileReader(args[0]));
			writer = new PrintWriter(args[1], "utf-8");
			List<Triple> triples = new ArrayList<Triple>(); // subset T
			List<Triple> matchings = new ArrayList<Triple>(); // matching set M
			List<String> inputLines = new ArrayList<String>();
			int lineNum = 0;
			String line = null;
			String[] setX = null;
			String[] setY = null;
			String[] setZ = null;
			boolean isReadSetZ = false;
			// Read data from file
			while ((line = br.readLine()) != null) {
				if (line.trim().startsWith("#") || line.trim().equals("")) {
					continue; // ignore comment and blank line
				} else {
					switch (lineNum) {
					case 0: // set x value
						setX = line.split(" ");
						line = "Set X: {" + line + "}";
						break;
					case 1: // set y value
						setY = line.split(" ");
						line = "Set Y: {" + line + "}";
						break;
					case 2: // set y value
						setZ = line.split(" ");
						line = "Set Z: {" + line + "}";
						isReadSetZ = true;
						break;
					default:
						break;
					}
					inputLines.add(line);
					if (isReadSetZ) // get all 3 sets and stop reading remain
									// lines
						break;
					lineNum++;
				}
			}
			// build subset T
			for (int i = 0; i < setX.length; i++) {
				for (int j = 0; j < setY.length; j++) {
					for (int k = 0; k < setZ.length; k++) {
						Triple tri = new Triple(Integer.parseInt(setX[i]),
								Integer.parseInt(setY[j]),
								Integer.parseInt(setZ[k]));
						triples.add(tri);
					}

				}

			}
			// iterator T to fill M
			for (Triple triple : triples) {
				boolean isMatched = false;
				for (Triple triple2 : matchings) {
					if (triple2.isMatch(triple))
						isMatched = true;
				}
				if (!isMatched) {
					matchings.add(new Triple(triple.getX(), triple.getY(),
							triple.getZ()));
				}
			}
			// write matchings to output file
			writer.println("============================");
			writer.println("3-DIMENTIONAL MATCHING SET: ");
			writer.println("{ ");
			log("============================");
			log("3-DIMENTIONAL MATCHING SET: ");
			log("{ ");
			for (Triple tri : matchings) {
				String strTri = "(" + tri.getX() + ", " + tri.getY() + ", "
						+ tri.getZ() + ") ";
				writer.println(strTri);
				log(strTri);

			}
			writer.println("}");
			log("}");

			writer.println("============================");
			log("============================");
			writer.println("INPUT:");
			log("INPUT:");
			for (String inputline : inputLines) {
				writer.println(inputline);
				log(inputline);
			}
			// write matchings to output file
			writer.println("============================");
			writer.println("SUBSET T: ");
			writer.println("{ ");
			log("============================");
			log("Subset T: ");
			log("{ ");
			for (Triple tri : triples) {
				String strTri = "(" + tri.getX() + ", " + tri.getY() + ", "
						+ tri.getZ() + ")";
				writer.println(strTri);
				log(strTri);

			}
			writer.println("}");
			log("}");
			System.out.println("Done!");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (writer != null)
					writer.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}

	// print message to console
	private static void log(String message) {
		if (isDebug) {
			System.out.println(message);
		}
	}
}

/**
 * Triple object in set
 * 
 * @author Haijun Su
 *
 */
class Triple {
	private int x;
	private int y;
	private int z;

	/**
	 * default constructor.
	 */
	public Triple() {
		x = 0;
		y = 0;
		z = 0;
	}

	/**
	 * construct triple with parameters
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public Triple(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * If x1=x2 or y1=y2 or z1=z2, they match each other
	 * 
	 * @param t
	 * @return
	 */
	public boolean isMatch(Triple t) {
		if (t == null)
			return false;
		if (this == t)
			return true;
		return x == t.x || y == t.y || z == t.z;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		result = prime * result + z;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Triple other = (Triple) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		if (z != other.z)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Triple [x=" + x + ", y=" + y + ", z=" + z + "]";
	}

}
