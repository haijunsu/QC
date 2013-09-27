package ch3;

/**
 * Find middle element's position in a SingleLinkedList. If the list size is
 * even, the middle element's position is size divided by 2.
 *
 * Output:
 * List size is 10
 * Middle element index (by list size): 5. SingleLinkedNode [name=node 4]
 * List size is 10
 * Middle element index (by iterator): 5. SingleLinkedNode [name=node 4]
 * List size is 10
 * Middle element (with two pointers): SingleLinkedNode [name=node 4]
 * List size is 15
 * Middle element index (by list size): 8. SingleLinkedNode [name=node 7]
 * List size is 15
 * Middle element index (by iterator): 8. SingleLinkedNode [name=node 7]
 * List size is 15
 * Middle element (with two pointers): SingleLinkedNode [name=node 7]* 
 * 
 * @author Haijun Su 
 * Created date: 2013-9-26
 */
public class Sep26 {

	/**
	 * Assume the list has implement the size function
	 * 
	 * @param list
	 * @return
	 */
	public int findMiddleElementPositionWithLinkSize(SingleLinkedList list) {
		if (list == null) {
			System.out.println("The list is null and return zero");
			return 0;
		}
		int listSize = list.getSize();
		System.out.println("List size is " + listSize);
		if (listSize % 2 > 0) {
			// size is odd number.
			listSize++;
		}
		return listSize / 2;
	}

	/**
	 * Count all elements to find the list size and return the middle element's
	 * position.
	 * 
	 * @param list
	 * @return
	 */
	public int findMiddleElementPosition(SingleLinkedList list) {
		if (list == null) {
			System.out.println("The list is null and return zero");
			return 0;
		}
		int listSize = 0;
		SingleLinkedNode node = list.getHead();
		while (node != null) {
			listSize++;
			node = node.getNext();
		}
		System.out.println("List size is " + listSize);
		if (listSize % 2 > 0) {
			// size is odd number.
			listSize++;
		}
		return listSize / 2;
	}

	/**
	 * Using two pointers to find middle element in list.
	 * 
	 * @param list
	 * @return
	 */
	public SingleLinkedNode findMiddleElement(SingleLinkedList list) {
		if (list == null) {
			System.out.println("The list is null and return null");
			return null;
		}
		int listSize = 0;
		SingleLinkedNode middleNode = null;
		SingleLinkedNode iteratorNode = list.getHead();
		while (iteratorNode != null) {
			if (middleNode == null) {
				middleNode = list.getHead();
			} else {
				middleNode = middleNode.getNext();
			}
			iteratorNode = iteratorNode.getNext();
			listSize++;
			if (iteratorNode != null) {
				iteratorNode = iteratorNode.getNext();
				listSize++;
			}

		}
		System.out.println("List size is " + listSize);
		return middleNode;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SingleLinkedList list = new SingleLinkedList();
		// size is even number
		for (int i = 0; i < 10; i++) {
			SingleLinkedNode node = new SingleLinkedNode("node " + i);
			list.add(node);
		}
		Sep26 hw = new Sep26();
		int middleIndex = hw.findMiddleElementPositionWithLinkSize(list);
		System.out.println("Middle element position (by list size): "
				+ middleIndex + ". " + list.getNode(middleIndex - 1));
		middleIndex = hw.findMiddleElementPosition(list);
		System.out.println("Middle element position (by iterator): " + middleIndex
				+ ". " + list.getNode(middleIndex - 1));
		System.out.println("Middle element (with two pointers): "
				+ hw.findMiddleElement(list));
		// size is odd number
		for (int i = 10; i < 15; i++) {
			SingleLinkedNode node = new SingleLinkedNode("node " + i);
			list.add(node);
		}
		middleIndex = hw.findMiddleElementPositionWithLinkSize(list);
		System.out.println("Middle element position (by list size): "
				+ middleIndex + ". " + list.getNode(middleIndex - 1));
		middleIndex = hw.findMiddleElementPosition(list);
		System.out.println("Middle element position (by iterator): " + middleIndex
				+ ". " + list.getNode(middleIndex - 1));
		System.out.println("Middle element (with two pointers): "
				+ hw.findMiddleElement(list));

	}

}

/**
 * Node in SingleLinkedList
 * 
 * @author suhaijun
 * 
 */
class SingleLinkedNode {
	/**
	 * Node name
	 */
	private String name = null;

	/**
	 * Next node
	 */
	private SingleLinkedNode next = null;

	/**
	 * Construct node with name
	 * 
	 * @param name
	 */
	public SingleLinkedNode(String name) {
		this.name = name;
	}

	/**
	 * Construct node with name and next node.
	 * 
	 * @param name
	 * @param next
	 */
	public SingleLinkedNode(String name, SingleLinkedNode next) {
		this.name = name;
		this.next = next;
	}

	/**
	 * Get node name
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set or update node name with new value
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get next SingleLinkedNode
	 * 
	 * @return
	 */
	public SingleLinkedNode getNext() {
		return next;
	}

	/**
	 * Set or update next SingleLinkedNode
	 * 
	 * @param next
	 */
	public void setNext(SingleLinkedNode next) {
		this.next = next;
	}

	@Override
	public String toString() {
		return "SingleLinkedNode [name=" + name + "]";
	}

}

/**
 * SingleLinkedList
 * 
 * @author Haijun Su Created date: 2013-9-26
 */
class SingleLinkedList {

	/**
	 * The header of SingleLinkedList
	 */
	private SingleLinkedNode head = null;

	/**
	 * The tail of the the SingleLinkedList
	 */
	private SingleLinkedNode tail = null;

	/**
	 * The size of the SingleLinkedList
	 */
	private int size = 0;

	/**
	 * Default contrutuctor
	 */
	public SingleLinkedList() {

	}

	/**
	 * add node to the first position
	 * 
	 * @param node
	 * @return
	 */
	public SingleLinkedNode addFirst(SingleLinkedNode node) {
		if (node == null)
			return null;
		size++;
		if (head == null) {
			head = node;
			tail = node;
			return head;
		}
		node.setNext(head);
		head = node;
		return head;
	}

	/**
	 * add node to last position
	 * 
	 * @param node
	 * @return
	 */
	public SingleLinkedNode addLast(SingleLinkedNode node) {
		if (node == null)
			return null;
		size++;
		if (tail == null) {
			head = node;
			tail = node;
			return tail;
		}
		tail.setNext(node);
		tail = node;
		return tail;
	}

	/**
	 * add node
	 * 
	 * @param node
	 * @return
	 */
	public SingleLinkedNode add(SingleLinkedNode node) {
		return addLast(node);
	}

	/**
	 * Get the head of list
	 * 
	 * @return
	 */
	public SingleLinkedNode getHead() {
		return head;
	}

	/**
	 * The list size
	 * 
	 * @return
	 */
	public int getSize() {
		return size;
	}

	/**
	 * get node with index
	 * 
	 * @param i
	 * @return
	 */
	public SingleLinkedNode getNode(int i) {
		if (head == null)
			return null;
		if (i >= size) {
			System.out.println("Outofbound error!");
			return null;
		}
		SingleLinkedNode node = head;
		int counter = 0;
		while (counter < i) {
			counter++;
			node = node.getNext();
		}
		return node;
	}
}
