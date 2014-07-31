import java.util.ArrayList;
import java.util.List;

/**
 * CS700 project: Knapsack: Given a set of items, each with a weight and a
 * value, determine the number of each item to include in a collection so that
 * the total weight is less than or equal to a given limit and the total value
 * is as large as possible.
 * 
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
	 * @param name
	 * @param weight
	 * @param value
	 */
	public void add(String name, int weight, int value) {
		itemList.add(new Item(name, weight, value));
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
						currIter.add((weight > j) ? prevIter.get(j) : Math.max(
								prevIter.get(j), itemList.get(i - 1).getValue()
										+ prevIter.get(j - weight)));
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
					j -= weight;
					totalWeight += weight;
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

		Knapsack pack = new Knapsack(400); // 400
		// making the list of items that you want to bring
		pack.add("map", 9, 150);
		pack.add("compass", 13, 35);
		pack.add("water", 153, 200);
		pack.add("sandwich", 50, 160);
		pack.add("glucose", 15, 60);
		pack.add("tin", 68, 45);
		pack.add("banana", 27, 60);
		pack.add("apple", 39, 40);
		pack.add("cheese", 23, 30);
		pack.add("beer", 52, 10);
		pack.add("suntan cream", 11, 70);
		pack.add("camera", 32, 30);
		pack.add("t-shirt", 24, 15);
		pack.add("trousers", 48, 10);
		pack.add("umbrella", 73, 40);
		pack.add("waterproof trousers", 42, 70);
		pack.add("waterproof overclothes", 43, 75);
		pack.add("note-case", 22, 80);
		pack.add("sunglasses", 7, 20);
		pack.add("towel", 18, 12);
		pack.add("socks", 4, 50);
		pack.add("book", 30, 10);

		// choose items:
		List<Item> itemList = pack.chooseItems();

		// write out the result in the standard output

		System.out.println("Maximal weight = " + pack.getMaxWeight());
		System.out.println("Total weight of solution = "
				+ pack.getTotalWeight());
		System.out.println("Total value = " + pack.getBenefit());
		System.out.println();
		System.out.println("The following items has been choosed");
		for (Item item : itemList) {
			System.out.println(item.getName() + "(weight = " + item.getWeight()
					+ ", value = " + item.getValue() + ")");
		}
		System.out.println("The following items has been choosed");
		
		System.out.println();
		System.out.println("The following items are the total items which can be choosed for packing");
		itemList = pack.getItemList();
		for (Item item : itemList) {
			System.out.println(item.getName() + "(weight = " + item.getWeight()
					+ ", value = " + item.getValue() + ")");
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