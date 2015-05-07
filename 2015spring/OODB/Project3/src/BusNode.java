import java.util.HashSet;

/**
 * 
 * @author suhaijun
 *
 */
@javax.jdo.annotations.PersistenceCapable
public class BusNode extends Node implements Comparable<BusNode> {
	/**
	 * Implements the inverse of BusNode: (form) BusLink --> BusNode *:1
	 */
	HashSet<BusLink> fromBusLinks = new HashSet<BusLink>();

	/**
	 * Implements the inverse of BusNode: (to) BusLink --> BusNode *:1
	 */
	HashSet<BusLink> toBusLinks = new HashSet<BusLink>();

	/**
	 * Default constructor
	 */
	public BusNode() {
	}

	/**
	 * Constructor with name
	 * 
	 * @param name
	 */
	public BusNode(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(BusNode o) {
		if (o == null)
			return 1;
		return this.name.compareTo(o.name);
	}

	@Override
	public String toString() {
		return "BusNode [fromBusLinks=" + fromBusLinks + ", toBusLinks="
				+ toBusLinks + ", name=" + name + ", near=" + near
				+ ", adjacent=" + adjacent + ", transportationNetworks="
				+ transportationNetworks + ", schedules=" + schedules + "]";
	}

	/**
	 * @return the fromBusLinks
	 */
	public HashSet<BusLink> getFromBusLinks() {
		return fromBusLinks;
	}

	/**
	 * @param fromBusLinks
	 *            the fromBusLinks to set
	 */
	public void setFromBusLinks(HashSet<BusLink> fromBusLinks) {
		this.fromBusLinks = fromBusLinks;
	}

	/**
	 * @return the toBusLinks
	 */
	public HashSet<BusLink> getToBusLinks() {
		return toBusLinks;
	}

	/**
	 * @param toBusLinks
	 *            the toBusLinks to set
	 */
	public void setToBusLinks(HashSet<BusLink> toBusLinks) {
		this.toBusLinks = toBusLinks;
	}

}
