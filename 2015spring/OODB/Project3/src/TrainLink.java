/**
 * 
 * @author suhaijun
 *
 */
@javax.jdo.annotations.PersistenceCapable
public class TrainLink extends Link implements Comparable<TrainLink> {

	/**
	 * Implements TrainLink: (from) TrainLink --> TrainNode *:1
	 */
	TrainNode from;

	/**
	 * Implements TrainLink: (to) TrainLink --> TrainNode *:1
	 */
	TrainNode to;

	@Override
	public int compareTo(TrainLink o) {
		if (o == null)
			return 1;
		if (this.distance > o.distance)
			return 1;
		else if (this.distance > o.distance)
			return -1;
		// this.distance == o.distance
		return 0;
	}

	/**
	 * Default constructor
	 */
	public TrainLink() {

	}
	
	/**
	 * Constructor with distance
	 * @param distance
	 */
	public TrainLink(double distance) {
		this.distance = distance;
	}

	@Override
	public String toString() {
		return "TrainLink [from=" + from + ", to=" + to + ", distance="
				+ distance + ", transportationNetworks="
				+ transportationNetworks + "]";
	}

	/**
	 * @return the from
	 */
	public TrainNode getFrom() {
		return from;
	}

	/**
	 * @param from
	 *            the from to set
	 */
	public void setFrom(TrainNode from) {
		this.from = from;
	}

	/**
	 * @return the to
	 */
	public TrainNode getTo() {
		return to;
	}

	/**
	 * @param to
	 *            the to to set
	 */
	public void setTo(TrainNode to) {
		this.to = to;
	}

}
