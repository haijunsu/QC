import java.util.HashSet;

/**
 * 
 * @author suhaijun
 *
 */
@javax.jdo.annotations.PersistenceCapable
public class TrainNode extends Node implements Comparable<TrainNode> {

	/**
	 * Implements the inverse of TrainNode: (form) TrainLink --> TrainNode *:1
	 */
	HashSet<TrainLink> fromTrainLinks = new HashSet<TrainLink>();

	/**
	 * Implements the inverse of TrainNode: (to) TrainLink --> TrainNode *:1
	 */
	HashSet<TrainLink> toTrainLinks = new HashSet<TrainLink>();

	/**
	 * default constructor
	 */
	public TrainNode() {
	}

	/**
	 * Constructor with name
	 * @param name
	 */
	public TrainNode(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(TrainNode o) {
		if (o == null)
			return 1;
		return this.name.compareTo(o.name);
	}

	@Override
	public String toString() {
		return "TrainNode [fromTrainLinks=" + fromTrainLinks
				+ ", toTrainLinks=" + toTrainLinks + ", name=" + name
				+ ", near=" + near + ", adjacent=" + adjacent
				+ ", transportationNetworks=" + transportationNetworks
				+ ", schedules=" + schedules + "]";
	}

	/**
	 * @return the fromTrainLinks
	 */
	public HashSet<TrainLink> getFromTrainLinks() {
		return fromTrainLinks;
	}

	/**
	 * @param fromTrainLinks
	 *            the fromTrainLinks to set
	 */
	public void setFromTrainLinks(HashSet<TrainLink> fromTrainLinks) {
		this.fromTrainLinks = fromTrainLinks;
	}

	/**
	 * @return the toTrainLinks
	 */
	public HashSet<TrainLink> getToTrainLinks() {
		return toTrainLinks;
	}

	/**
	 * @param toTrainLinks
	 *            the toTrainLinks to set
	 */
	public void setToTrainLinks(HashSet<TrainLink> toTrainLinks) {
		this.toTrainLinks = toTrainLinks;
	}

}
