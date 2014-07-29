import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * CS700 project: 3-dimensional matching This solution can accept three sets
 * from an input file. One set is one line which is separated by a blank.
 * 
 * Output file stores the solution result.
 * 
 * @author Haijun Su 2014 summer
 *
 */
public class ThreeDimensionalMatching {

	public static void main(String[] args) {
		// Read data from file
		if (args.length != 2) {
			System.out
					.println("Usage: java -cp . ThreeDimensionalMatching <input file> <output file>");
			System.exit(1); // input error and exit
		}
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(args[0]));
			List<Triple> triples = new ArrayList<Triple>(); // subset T
			List<Triple> matchings = new ArrayList<Triple>(); // matching set M
			int lineNum = 0;
			String line = null;
			String[] setX = null;
			String[] setY = null;
			String[] setZ = null;
			// Read data from file 
			while ((line = br.readLine()) != null) {
				if (line.trim().startsWith("#")) {
					continue; // comment and ignore this line
				}
				System.out.println(line);
				switch (lineNum) {
				case 0: // set x value
					setX = line.split(" ");
					break;
				case 1: // set y value
					setY = line.split(" ");
					break;
				case 2: // set y value
					setZ = line.split(" ");
					break;
				default:
					break;
				}
				lineNum++;
			}
			//build subset T
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
			System.out.println(triples);
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
			System.out.println(matchings);

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
			} catch (IOException ex) {
				ex.printStackTrace();
			}
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
