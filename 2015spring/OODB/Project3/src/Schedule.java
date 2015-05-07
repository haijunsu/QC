import java.util.HashSet;


/**
 * 
 * @author suhaijun
 *
 */
@javax.jdo.annotations.PersistenceCapable
public class Schedule {
	
	/**
	 * Implements Schedule: Schedule --> TrainOrBus 1..*:1
	 */
	TrainOrBus trainOrBus;

	/**
	 * Implements Schedule: Schedule --> Node 1..*:1
	 */
	Node node;
	
	
	/**
	 * Implements Schedule: (arrivalTime) Schedule --> Time 0..1:*
	 */
	HashSet<Time> arrivalTimes = new HashSet<Time>();

	/**
	 * Implements Schedule: (departureTime) Schedule --> Time 0..1:*
	 */
	HashSet<Time> departureTimes = new HashSet<Time>();

	/**
	 * Default constructor
	 */
	public Schedule() {
		
	}
	
	@Override
	public String toString() {
		return "Schedule [trainOrBus=" + trainOrBus + ", node=" + node
				+ ", arrivalTimes=" + arrivalTimes + ", departureTimes="
				+ departureTimes + "]";
	}

	/**
	 * @return the trainOrBus
	 */
	public TrainOrBus getTrainOrBus() {
		return trainOrBus;
	}

	/**
	 * @param trainOrBus the trainOrBus to set
	 */
	public void setTrainOrBus(TrainOrBus trainOrBus) {
		this.trainOrBus = trainOrBus;
	}

	/**
	 * @return the node
	 */
	public Node getNode() {
		return node;
	}

	/**
	 * @param node the node to set
	 */
	public void setNode(Node node) {
		this.node = node;
	}

	/**
	 * @return the arrivalTimes
	 */
	public HashSet<Time> getArrivalTimes() {
		return arrivalTimes;
	}

	/**
	 * @param arrivalTimes the arrivalTimes to set
	 */
	public void setArrivalTimes(HashSet<Time> arrivalTimes) {
		this.arrivalTimes = arrivalTimes;
	}

	/**
	 * @return the departureTimes
	 */
	public HashSet<Time> getDepartureTimes() {
		return departureTimes;
	}

	/**
	 * @param departureTimes the departureTimes to set
	 */
	public void setDepartureTimes(HashSet<Time> departureTimes) {
		this.departureTimes = departureTimes;
	}
	
}
