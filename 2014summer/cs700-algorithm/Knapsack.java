import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * CS700 project: Knapsack: Given a set of items, each with a weight and a
 * value, determine the number of each item to include in a collection so that
 * the total weight is less than or equal to a given limit and the total value
 * is as large as possible.
 * 
 * This solution can read the maximum weight of a container and items from an
 * input file. The first valid line is the maximum weight value and each other
 * valid line is one item.
 * 
 * Output file stores the solution result.
 * 
 * Compile: javac -cp . Knapsack.java
 * 
 * Usage: java -cp . Knapsack <input file> <output file>
 * 
 * 	      java -cp . -DisDebug=true Knapsack <input file> <output file>
 * 
 * 
 * @author Haijun Su 2014 summer
 *
 */
public class Knapsack {

	private static boolean isDebug = false;

	/**
	 * An item container which provides item to pack
	 */
	private List<Item> itemList;

	/**
	 * maximum capacity of pack
	 */
	private int maxWeight;

	/**
	 * total weight after packing items
	 */
	private int totalWeight;

	/**
	 * total values of items which have be packed
	 */
	private int benefit = 0;

	/**
	 * Init a pack with maximum capacity
	 * 
	 * @param weight
	 */
	public Knapsack(int weight) {
		this.maxWeight = weight;
		itemList = new ArrayList<Item>();
	}

	/**
	 * add an item into item container
	 * 
	 * @param item
	 */
	public void add(Item item) {
		itemList.add(item);
	}

	/**
	 * Choose items and pack them
	 * 
	 * @return
	 */
	public List<Item> chooseItems() {
		List<Item> packedList = new ArrayList<Item>();
		int total = itemList.size();

		if (total > 0 && maxWeight > 0) {
			List<List<Integer>> iterates = new ArrayList<List<Integer>>();
			List<Integer> currIter = new ArrayList<Integer>();

			iterates.add(currIter);
			for (int j = 0; j <= maxWeight; j++)
				currIter.add(0); // first iterate
			for (int i = 1; i <= total; i++) {
				List<Integer> prevIter = currIter;
				iterates.add(currIter = new ArrayList<Integer>());
				for (int j = 0; j <= maxWeight; j++) {
					if (j > 0) {
						int weight = itemList.get(i - 1).getWeight();
						// if weight is greater than j, value is previous
						// iterator's value. else get the maximum value between
						// previous iterator's value and current item value plus
						// previous (j - weigh)'s value
						if (weight > j) {
							currIter.add(prevIter.get(j));
						} else {
							int value = itemList.get(i - 1).getValue()
									+ prevIter.get(j - weight);
							if (value > prevIter.get(j)) {
								currIter.add(value);
							} else {
								currIter.add(prevIter.get(j));
							}
						}
					} else {
						// 0 items
						currIter.add(0);
					}
				}
			}
			// the last one is the benefit
			benefit = currIter.get(maxWeight);

			// find which items have been packed in pack
			for (int i = total, j = maxWeight; i > 0 && j >= 0; i--) {
				int value1 = iterates.get(i).get(j);
				int value2 = iterates.get(i - 1).get(j);
				// compare with previous iterate's value. If it different, the
				// item is packed
				if ((i == 0 && value1 > 0) || (i > 0 && value1 != value2)) {
					Item item = itemList.get(i - 1);
					int weight = item.getWeight();
					packedList.add(item);
					totalWeight += weight;
					j = j - weight;
				}
			}
		}
		return packedList;
	}

	public int getBenefit() {
		return benefit;
	}

	public int getTotalWeight() {
		return totalWeight;
	}

	public int getMaxWeight() {
		return maxWeight;
	}

	public List<Item> getItemList() {
		return itemList;
	}

	// print message to console
	private static void log(String message) {
		if (isDebug) {
			System.out.println(message);
		}
	}

	public static void main(String[] args) {
		isDebug = "true".equalsIgnoreCase(System.getProperty("isDebug"));
		// Read data from file
		if (args.length != 2) {
			System.out
					.println("Usage: java -cp . Knapsack <input file> <output file>");
			System.exit(1); // input error and exit
		}
		BufferedReader br = null;
		PrintWriter writer = null;
		try {
			br = new BufferedReader(new FileReader(args[0]));
			writer = new PrintWriter(args[1], "utf-8");
			List<String> inputLines = new ArrayList<String>();
			Knapsack pack = null;
			boolean isInitPack = false;
			String line = null;
			// Read data from file
			while ((line = br.readLine()) != null) {
				if (line.trim().startsWith("#") || line.trim().equals("")) {
					continue; // ignore comment and blank line
				}
				inputLines.add(line);
				if (!isInitPack) {
					// Init Knapsack
					pack = new Knapsack(Integer.valueOf(line.trim()));
					isInitPack = true;
				} else {
					// construct item and add it to item list
					String[] values = line.trim().split(",");
					Item item = new Item(values[0].trim(),
							Integer.valueOf(values[1].trim()),
							Integer.valueOf(values[2].trim()));
					pack.add(item);
				}
			}

			// choose items:
			List<Item> itemList = pack.chooseItems();
			writer.println("============================");
			writer.println("Knapsack result: ");
			log("============================");
			log("Knapsack result: ");

			writer.println("Maximal weight of the pack = " + pack.getMaxWeight());
			log("Maximal weight of the pack = " + pack.getMaxWeight());
			writer.println("Total weight = "
					+ pack.getTotalWeight());
			log("Total weight = "
					+ pack.getTotalWeight());
			writer.println("Total value = " + pack.getBenefit());
			log("Total value = " + pack.getBenefit());
			writer.println();
			log("");
			writer.println("The following items has been choosed");
			log("The following items has been choosed");
			for (Item item : itemList) {
				writer.println(item.getName() + "(weight = "
						+ item.getWeight() + ", value = " + item.getValue()
						+ ")");
				log(item.getName() + "(weight = "
						+ item.getWeight() + ", value = " + item.getValue()
						+ ")");
			}
			writer.println();
			log("");
			
			writer.println("============================");
			log("============================");
			writer.println("INPUT:");
			log("INPUT:");
			for (String inputline : inputLines) {
				writer.println(inputline);
				log(inputline);
			}
			writer.println();
			log("");
			
			writer.println("============================");
			log("============================");

			writer.println("The following items are the total items which can be choosed for packing");
			log("The following items are the total items which can be choosed for packing");
			itemList = pack.getItemList();
			for (Item item : itemList) {
				writer.println(item.getName() + "(weight = "
						+ item.getWeight() + ", value = " + item.getValue()
						+ ")");
				log(item.getName() + "(weight = "
						+ item.getWeight() + ", value = " + item.getValue()
						+ ")");
			}
			System.out.println("Done!");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
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

}

/**
 * Item which can be packed
 * 
 * @author Haijun Su
 *
 */
class Item {

	private String name;
	private int weight;
	private int value;

	/**
	 * Construct item all properties
	 * 
	 * @param name
	 * @param weight
	 * @param value
	 */
	public Item(String name, int weight, int value) {
		this.name = name;
		this.weight = weight;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Item other = (Item) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Item [name=" + name + ", weight=" + weight + ", value=" + value
				+ "]";
	}

}