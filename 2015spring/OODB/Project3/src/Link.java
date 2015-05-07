import java.util.HashSet;

/**
 * @author suhaijun
 *
 */
@javax.jdo.annotations.PersistenceCapable
public abstract class Link {
	
	/**
	 * distance between two nodes
	 */
	double distance;

	/**
	 * Implements the inverse of Link: TransportationNetwork --> Link *:1..*
	 */
	HashSet<TransportationNetwork> transportationNetworks = new HashSet<TransportationNetwork>();

	/**
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * @param distance the distance to set
	 */
	public void setDistance(double distance) {
		this.distance = distance;
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
	
	

}
