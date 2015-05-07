import java.util.HashSet;

/**
 * 
 * @author suhaijun
 *
 */
@javax.jdo.annotations.PersistenceCapable
public class TrainOrBus implements Comparable<TrainOrBus> {
	/**
	 * Train or bus's code
	 */
	String code;
	
	/**
	 * Implements the inverse of TrainOrBus: TransportationNetwork --> TrainOrBus *:1..*
	 */
	HashSet<TransportationNetwork> transNetwork = new HashSet<TransportationNetwork>();

	/**
	 * Implements the inverse of TrainOrBus: Schedule --> TrainOrBus 1..*:1
	 */
	HashSet<Schedule> schedules = new HashSet<Schedule>();
	
	/**
	 * default constructor 
	 */
	public TrainOrBus() {
	}
	
	/**
	 * Constructor with code
	 * @param code
	 */
	public TrainOrBus(String code) {
		this.code = code;
	}
	@Override
	public int compareTo(TrainOrBus o) {
		if (o == null) return 1;
		return this.code.compareTo(o.code);
	}

	@Override
	public String toString() {
		return "TrainOrBus [code=" + code + ", transNetwork=" + transNetwork
				+ ", schedules=" + schedules + "]";
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
	 * @return the transNetwork
	 */
	public HashSet<TransportationNetwork> getTransNetwork() {
		return transNetwork;
	}

	/**
	 * @param transNetwork the transNetwork to set
	 */
	public void setTransNetwork(HashSet<TransportationNetwork> transNetwork) {
		this.transNetwork = transNetwork;
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
