import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import javax.jdo.*;

import com.objectdb.Utilities;

/**
 * 
 * @author suhaijun
 *
 */
public class CreateData {

	/**
	 * PersistenceManager
	 */
	private PersistenceManager pm = null;

	public static void main(String[] args) {
		try {
			System.out.println("CS780 OODB Project 3, Spring 2015");
			System.out.println("By Haijun Su");
			CreateData cdata = new CreateData();
			// initial pm
			cdata.pm = Utilities.getPersistenceManager("transportation.odb");

			// define Transportation Network
			List<TransportationNetwork> transNetworks = new ArrayList<TransportationNetwork>();

			transNetworks.add(new TransportationNetwork("Queens"));
			transNetworks.add(new TransportationNetwork("Manhattan"));
			transNetworks.add(new TransportationNetwork("Staten Island"));
			transNetworks.add(new TransportationNetwork("Bronx"));
			transNetworks.add(new TransportationNetwork("Brooklyn"));
			TransportationNetwork transNWQueens = cdata
					.findTransNWOrTrainBusInList(transNetworks, "Queens");
			TransportationNetwork transNWManhattan = cdata
					.findTransNWOrTrainBusInList(transNetworks, "Manhattan");
			// define nodes
			String[] names = new String[] { "College Point", "Flushing",
					"Forest Hill", "Fresh Meadows", "Jamaica", "Bayside",
					"CityHall", "Wall Street", "Time Square" };
			List<Node> busNodes = new ArrayList<Node>();
			List<Node> trainNodes = new ArrayList<Node>();
			for (int i = 0; i < names.length; i++) {
				BusNode busNode = new BusNode(names[i]);
				busNodes.add(busNode);
				TrainNode trainNode = new TrainNode(names[i]);
				trainNodes.add(trainNode);
				if (i < 6) {
					transNWQueens.getNodes().add(busNode);
					transNWQueens.getNodes().add(trainNode);
					busNode.getTransportationNetworks().add(transNWQueens);
					trainNode.getTransportationNetworks().add(transNWQueens);
				} else {
					transNWManhattan.getNodes().add(busNode);
					transNWManhattan.getNodes().add(trainNode);
					busNode.getTransportationNetworks().add(transNWManhattan);
					trainNode.getTransportationNetworks().add(transNWManhattan);
				}
			}

			// add adjacent node
			cdata.addAdjacentNode(busNodes, "College Point", "Flushing");
			cdata.addAdjacentNode(busNodes, "Forest Hill", "Flushing");
			cdata.addAdjacentNode(busNodes, "Forest Hill", "Fresh Meadows");
			cdata.addAdjacentNode(busNodes, "Forest Hill", "Jamaica");
			cdata.addAdjacentNode(busNodes, "Fresh Meadows", "Jamaica");
			cdata.addAdjacentNode(busNodes, "Fresh Meadows", "Flushing");
			cdata.addAdjacentNode(busNodes, "Fresh Meadows", "Bayside");

			cdata.addAdjacentNode(busNodes, "Wall Street", "Time Square");
			cdata.addAdjacentNode(busNodes, "CityHall", "Wall Street");

			cdata.addAdjacentNode(trainNodes, "College Point", "Flushing");
			cdata.addAdjacentNode(trainNodes, "Forest Hill", "Flushing");
			cdata.addAdjacentNode(trainNodes, "Forest Hill", "Fresh Meadows");
			cdata.addAdjacentNode(trainNodes, "Forest Hill", "Jamaica");
			cdata.addAdjacentNode(trainNodes, "Fresh Meadows", "Jamaica");
			cdata.addAdjacentNode(trainNodes, "Fresh Meadows", "Flushing");
			cdata.addAdjacentNode(trainNodes, "Fresh Meadows", "Bayside");

			cdata.addAdjacentNode(trainNodes, "Wall Street", "Time Square");
			cdata.addAdjacentNode(trainNodes, "CityHall", "Wall Street");

			// add near node
			cdata.addNearNode(busNodes, "College Point", "Forest Hill");
			cdata.addNearNode(busNodes, "College Point", "Bayside");
			cdata.addNearNode(busNodes, "College Point", "Fresh Meadows");
			cdata.addNearNode(busNodes, "Forest Hill", "Bayside");
			cdata.addNearNode(busNodes, "Jamaica", "Flushing");
			cdata.addNearNode(busNodes, "CityHall", "Time Square");

			cdata.addNearNode(trainNodes, "College Point", "Forest Hill");
			cdata.addNearNode(trainNodes, "College Point", "Bayside");
			cdata.addNearNode(trainNodes, "College Point", "Fresh Meadows");
			cdata.addNearNode(trainNodes, "Forest Hill", "Bayside");
			cdata.addNearNode(trainNodes, "Jamaica", "Flushing");
			cdata.addNearNode(trainNodes, "CityHall", "Time Square");

			// define links
			List<Link> busLinks = new ArrayList<Link>();
			List<Link> trainLinks = new ArrayList<Link>();

			busLinks.add(cdata.createBusLink(busNodes, "College Point",
					"Forest Hill", 10.0, transNWQueens));
			busLinks.add(cdata.createBusLink(busNodes, "College Point",
					"Bayside", 10.0, transNWQueens));
			busLinks.add(cdata.createBusLink(busNodes, "College Point",
					"Fresh Meadows", 10.0, transNWQueens));
			busLinks.add(cdata.createBusLink(busNodes, "College Point",
					"Jamaica", 10.0, transNWQueens));
			busLinks.add(cdata.createBusLink(busNodes, "Jamaica", "Flushing",
					10.0, transNWQueens));
			busLinks.add(cdata.createBusLink(busNodes, "Jamaica", "Bayside",
					10.0, transNWQueens));
			busLinks.add(cdata.createBusLink(busNodes, "Jamaica",
					"Fresh Meadows", 10.0, transNWQueens));
			busLinks.add(cdata.createBusLink(busNodes, "CityHall",
					"Time Square", 10.0, transNWManhattan));
			busLinks.add(cdata.createBusLink(busNodes, "CityHall",
					"Wall Street", 10.0, transNWManhattan));
			busLinks.add(cdata.createBusLink(busNodes, "Flushing",
					"Wall Street", 10.0, new TransportationNetwork[] {
							transNWManhattan, transNWQueens }));
			busLinks.add(cdata.createBusLink(busNodes, "Jamaica",
					"Wall Street", 10.0, new TransportationNetwork[] {
							transNWManhattan, transNWQueens }));

			trainLinks.add(cdata.createTrainLink(trainNodes, "College Point",
					"Forest Hill", 10.0, transNWQueens));
			trainLinks.add(cdata.createTrainLink(trainNodes, "College Point",
					"Bayside", 10.0, transNWQueens));
			trainLinks.add(cdata.createTrainLink(trainNodes, "College Point",
					"Fresh Meadows", 10.0, transNWQueens));
			trainLinks.add(cdata.createTrainLink(trainNodes, "College Point",
					"Jamaica", 10.0, transNWQueens));
			trainLinks.add(cdata.createTrainLink(trainNodes, "Jamaica",
					"Flushing", 10.0, transNWQueens));
			trainLinks.add(cdata.createTrainLink(trainNodes, "Jamaica",
					"Bayside", 10.0, transNWQueens));
			trainLinks.add(cdata.createTrainLink(trainNodes, "Jamaica",
					"Fresh Meadows", 10.0, transNWQueens));
			trainLinks.add(cdata.createTrainLink(trainNodes, "CityHall",
					"Time Square", 10.0, transNWManhattan));
			trainLinks.add(cdata.createTrainLink(trainNodes, "CityHall",
					"Wall Street", 10.0, transNWManhattan));
			trainLinks.add(cdata.createTrainLink(trainNodes, "Flushing",
					"Wall Street", 10.0, new TransportationNetwork[] {
							transNWManhattan, transNWQueens }));
			trainLinks.add(cdata.createTrainLink(trainNodes, "Jamaica",
					"Wall Street", 10.0, new TransportationNetwork[] {
							transNWManhattan, transNWQueens }));

			// define trainOrBus
			String[] trainBusNums = new String[] { "Q1", "Q2", "Q3", "M1",
					"M2", "M3", "QM1", "QM2" };
			List<TrainOrBus> trainBuses = new ArrayList<TrainOrBus>();
			for (int i = 0; i < trainBusNums.length; i++) {
				TrainOrBus tbs = new TrainOrBus(trainBusNums[i]);
				trainBuses.add(tbs);
				if (tbs.getCode().startsWith("QM")) {
					transNWManhattan.getTrainOrBuses().add(tbs);
					tbs.getTransNetwork().add(transNWManhattan);
					transNWQueens.getTrainOrBuses().add(tbs);
					tbs.getTransNetwork().add(transNWQueens);
				} else if (tbs.getCode().startsWith("Q")) {
					transNWQueens.getTrainOrBuses().add(tbs);
					tbs.getTransNetwork().add(transNWQueens);
				} else if (tbs.getCode().startsWith("M")) {
					transNWManhattan.getTrainOrBuses().add(tbs);
					tbs.getTransNetwork().add(transNWManhattan);
				}
			}

			// define schedule and time
			TrainOrBus q1 = cdata.findTransNWOrTrainBusInList(trainBuses, "Q1");
			// Q1 schedule
			for (int i = 0; i < 6; i++) {
				Node bsn = cdata.findNodeInList(busNodes, names[i]);
				Schedule schedule = new Schedule();
				schedule.setTrainOrBus(q1);
				schedule.setNode(bsn);
				for (int j = 0; j < 24;) {
					int min = i * 10;
					j = j + 3;
					Time arriveTime = new Time(j, min);
					Time departureTime = new Time(j, min + 2);
					schedule.getArrivalTimes().add(arriveTime);
					schedule.getDepartureTimes().add(departureTime);
				}
				bsn.getSchedules().add(schedule);
				q1.getSchedules().add(schedule);
			}
			TrainOrBus qm1 = cdata.findTransNWOrTrainBusInList(trainBuses,
					"QM1");
			String[] qm1stops = new String[] { "College Point", "Flushing",
					"Forest Hill", "Wall Street", "Time Square" };
			// QM1 schedule
			for (int i = 0; i < qm1stops.length; i++) {
				Node bsn = cdata.findNodeInList(busNodes, qm1stops[i]);
				Schedule schedule = new Schedule();
				schedule.setTrainOrBus(qm1);
				schedule.setNode(bsn);
				for (int j = 0; j < 24;) {
					int min = i * 10;
					j = j + 3;
					Time arriveTime = new Time(j, min);
					Time departureTime = new Time(j, min + 2);
					schedule.getArrivalTimes().add(arriveTime);
					schedule.getDepartureTimes().add(departureTime);
					arriveTime.setArrivalTimeSchedule(schedule);
					departureTime.setDepartureTimeSchedule(schedule);
				}
				bsn.getSchedules().add(schedule);
				qm1.getSchedules().add(schedule);
			}

			// make persistent data
			cdata.makePersistent(transNetworks);
			cdata.makePersistent(trainNodes);
			cdata.makePersistent(trainLinks);
			cdata.makePersistent(busNodes);
			cdata.makePersistent(busLinks);
			cdata.makePersistent(trainBuses);

			// close pm
			if (!cdata.pm.isClosed()) {
				cdata.pm.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Done!");
	}

	/**
	 * Store object in database
	 * 
	 * @param objs
	 */
	public <T> void makePersistent(List<T> objs) {
//		System.out.println("makePersistent....");
		pm.currentTransaction().begin();
		for (T t : objs) {
			pm.makePersistent(t);
		}
		pm.currentTransaction().commit();
	}

	/**
	 * Find object in a list base on it's method and value
	 * 
	 * @param objs
	 * @param methodName
	 * @param name
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public <T> T findObjInList(List<T> objs, String methodName, String name)
			throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
//		System.out.println("findObjInList ...");
		T rtn = null;
		for (T t : objs) {
			Class<? extends Object> clazz = t.getClass();
			Method method = clazz.getMethod(methodName, new Class<?>[] {});
			String value = (String) method.invoke(t, new Object[] {});
			if (name.equals(value)) {
				rtn = t;
				break;
			}
		}
		return rtn;
	}

	/**
	 * Find Transportation network
	 * 
	 * @param objs
	 * @param name
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public <T> T findTransNWOrTrainBusInList(List<T> objs, String name)
			throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		return findObjInList(objs, "getCode", name);
	}

	/**
	 * Find node
	 * 
	 * @param objs
	 * @param name
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public <T> T findNodeInList(List<T> objs, String name)
			throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		return findObjInList(objs, "getName", name);
	}

	/**
	 * Add near node to each other
	 * 
	 * @param nodes
	 * @param firstNode
	 * @param secondNode
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public void addNearNode(List<Node> nodes, String firstNode,
			String secondNode) throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Node first = findNodeInList(nodes, firstNode);
		Node second = findNodeInList(nodes, secondNode);
		first.getNear().add(second);
		second.getNear().add(first);
	}

	/**
	 * Add adjacent node to each other
	 * 
	 * @param nodes
	 * @param firstNode
	 * @param secondNode
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public void addAdjacentNode(List<Node> nodes, String firstNode,
			String secondNode) throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Node first = findNodeInList(nodes, firstNode);
		Node second = findNodeInList(nodes, secondNode);
		first.getAdjacent().add(second);
		second.getAdjacent().add(first);
	}

	/**
	 * Create a bus link
	 * 
	 * @param busNodes
	 * @param from
	 * @param to
	 * @param distance
	 * @param nws
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public Link createBusLink(List<Node> busNodes, String from, String to,
			double distance, TransportationNetwork[] nws)
			throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		BusLink link = new BusLink(distance);
		for (int i = 0; i < nws.length; i++) {
			nws[i].getLinks().add(link);
			link.getTransportationNetworks().add(nws[i]);
		}
		BusNode fromNode = (BusNode) findNodeInList(busNodes, from);
		BusNode toNode = (BusNode) findNodeInList(busNodes, to);

		link.setFrom(fromNode);
		link.setTo(toNode);

		fromNode.getFromBusLinks().add(link);
		toNode.getToBusLinks().add(link);

		return link;
	}

	/**
	 * create a bus link
	 * 
	 * @param busNodes
	 * @param from
	 * @param to
	 * @param distance
	 * @param nws
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public Link createBusLink(List<Node> busNodes, String from, String to,
			double distance, TransportationNetwork nw)
			throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		return createBusLink(busNodes, from, to, distance,
				new TransportationNetwork[] { nw });
	}

	/**
	 * Create a train link
	 * 
	 * @param trainNodes
	 * @param from
	 * @param to
	 * @param distance
	 * @param nws
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public Link createTrainLink(List<Node> trainNodes, String from, String to,
			double distance, TransportationNetwork[] nws)
			throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		TrainLink link = new TrainLink(distance);
		for (int i = 0; i < nws.length; i++) {
			nws[i].getLinks().add(link);
			link.getTransportationNetworks().add(nws[i]);
		}
		TrainNode fromNode = (TrainNode) findNodeInList(trainNodes, from);
		TrainNode toNode = (TrainNode) findNodeInList(trainNodes, to);

		link.setFrom(fromNode);
		link.setTo(toNode);

		fromNode.getFromTrainLinks().add(link);
		toNode.getToTrainLinks().add(link);

		return link;
	}

	/**
	 * create a train link
	 * 
	 * @param trainNodes
	 * @param from
	 * @param to
	 * @param distance
	 * @param nw
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public Link createTrainLink(List<Node> trainNodes, String from, String to,
			double distance, TransportationNetwork nw)
			throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		return createTrainLink(trainNodes, from, to, distance,
				new TransportationNetwork[] { nw });
	}
}
