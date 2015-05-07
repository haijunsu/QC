import java.util.HashSet;

/**
 * 
 * @author suhaijun
 *
 */
@javax.jdo.annotations.PersistenceCapable
public abstract class Node {
	/**
	 * Node's name
	 */
	String name;
	
	/**
	 * Implements Node: (near) Node --> Node *:*
	 */
	HashSet<Node> near = new HashSet<Node>();
	
	/**
	 * Implements Node: (adjacent) Node --> Node *:*
	 */
	HashSet<Node> adjacent = new HashSet<Node>();
	
	/**
	 * Implements the inverse of Node: TransportationNetwork --> Node *:1..*
	 */
	HashSet<TransportationNetwork> transportationNetworks = new HashSet<TransportationNetwork>();
	
	/**
	 * Implements the inverse of Node: Schedule --> Node 1..*:1
	 */
	HashSet<Schedule> schedules = new HashSet<Schedule>();

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the near
	 */
	public HashSet<Node> getNear() {
		return near;
	}

	/**
	 * @param near the near to set
	 */
	public void setNear(HashSet<Node> near) {
		this.near = near;
	}

	/**
	 * @return the adjacent
	 */
	public HashSet<Node> getAdjacent() {
		return adjacent;
	}

	/**
	 * @param adjacent the adjacent to set
	 */
	public void setAdjacent(HashSet<Node> adjacent) {
		this.adjacent = adjacent;
	}

	/**
	 * @return the transportationNetworks
	 */
	public HashSet<TransportationNetwork> getTransportationNetworks() {
		return transportationNetworks;
	}

	/**
	 * @param transportationNetworks the transportationNetworks to set
	 */
	public void setTransportationNetworks(
			HashSet<TransportationNetwork> transportationNetworks) {
		this.transportationNetworks = transportationNetworks;
	}

	/**
	 * @return the schedules
	 */
	public HashSet<Schedule> getSchedules() {
		return schedules;
	}

	/**
	 * @param schedules the schedules to set
	 */
	public void setSchedules(HashSet<Schedule> schedules) {
		this.schedules = schedules;
	}
	
	

}
