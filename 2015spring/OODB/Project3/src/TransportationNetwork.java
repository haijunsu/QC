import java.util.HashSet;

/**
 * 
 * @author suhaijun
 *
 */
@javax.jdo.annotations.PersistenceCapable
public class TransportationNetwork implements Comparable<TransportationNetwork>{

	/**
	 * Transportation network code
	 */
	String code;
	
	/**
	 * Implements TransportationNetwork: TransportationNetwork --> Link *:1..*
	 */
	HashSet<Link> links = new HashSet<Link>();

	/**
	 * Implements TransportationNetwork: TransportationNetwork --> Node *:1..*
	 */
	HashSet<Node> nodes = new HashSet<Node>();

	/**
	 * Implements TransportationNetwork: TransportationNetwork --> TrainOrBus *:1..*
	 */
	HashSet<TrainOrBus> trainOrBuses = new HashSet<TrainOrBus>();

	/**
	 * Default constructor
	 */
	public TransportationNetwork() {
	}
	
	/**
	 * Constructor TransportationNetwork
	 * @param code
	 */
	public TransportationNetwork(String code) {
		this.code = code;
	}
	
	@Override
	public int compareTo(TransportationNetwork o) {
		if (o==null) return 1;
		return this.code.compareTo(o.code);
	}

	@Override
	public String toString() {
		return "TransportationNetwork [code=" + code + ", links=" + links
				+ ", nodes=" + nodes + ", trainOrBuses=" + trainOrBuses + "]";
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the links
	 */
	public HashSet<Link> getLinks() {
		return links;
	}

	/**
	 * @param links the links to set
	 */
	public void setLinks(HashSet<Link> links) {
		this.links = links;
	}

	/**
	 * @return the nodes
	 */
	public HashSet<Node> getNodes() {
		return nodes;
	}

	/**
	 * @param nodes the nodes to set
	 */
	public void setNodes(HashSet<Node> nodes) {
		this.nodes = nodes;
	}

	/**
	 * @return the trainOrBuses
	 */
	public HashSet<TrainOrBus> getTrainOrBuses() {
		return trainOrBuses;
	}

	/**
	 * @param trainOrBuses the trainOrBuses to set
	 */
	public void setTrainOrBuses(HashSet<TrainOrBus> trainOrBuses) {
		this.trainOrBuses = trainOrBuses;
	}


}
