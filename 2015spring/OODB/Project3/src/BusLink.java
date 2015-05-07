import java.util.HashSet;

/**
 * 
 * @author suhaijun
 *
 */
@javax.jdo.annotations.PersistenceCapable
public class BusLink extends Link implements Comparable<BusLink> {

	/**
	 * Implements BusLink: (from) BusLink --> BusNode *:1
	 */
	BusNode from;
	
	/**
	 * Implements BusLink: (to) BusLink --> BusNode *:1
	 */
	BusNode to;

	/**
	 * Default constructor
	 */
	public BusLink() {

	}
	
	/**
	 * Constructor with distance
	 * @param distance
	 */
	public BusLink(double distance) {
		this.distance = distance;
	}
	
	@Override
	public int compareTo(BusLink o) {
		if (o == null)
			return 1;
		if (this.distance > o.distance)
			return 1;
		else if (this.distance > o.distance)
			return -1;
		// this.distance == o.distance
		return 0;
	}


	@Override
	public String toString() {
		return "BusLink [from=" + from + ", to=" + to + ", distance="
				+ distance + ", transportationNetworks="
				+ transportationNetworks + "]";
	}


	/**
	 * @return the from
	 */
	public BusNode getFrom() {
		return from;
	}


	/**
	 * @param from the from to set
	 */
	public void setFrom(BusNode from) {
		this.from = from;
	}


	/**
	 * @return the to
	 */
	public BusNode getTo() {
		return to;
	}


	/**
	 * @param to the to to set
	 */
	public void setTo(BusNode to) {
		this.to = to;
	}
	
	
	
}
