package cs313.ch3;

/**
 * Find middle element in a SingleLinkedList
 * 
 * @author Haijun Su
 * Created date: 2013-9-26
 */
public class Sep26 {
	
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	

}

/**
 * Node in SingleLinkedList
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
	 * @param name
	 */
	public SingleLinkedNode(String name) {
		this.name = name;
	}
	
	/**
	 * Construct node with name and next node.
	 * @param name
	 * @param next
	 */
	public SingleLinkedNode(String name, SingleLinkedNode next) {
		this.name = name;
		this.next = next;
	}
	
	/**
	 * Get node name
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set or update node name with new value
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get next SingleLinkedNode
	 * @return
	 */
	public SingleLinkedNode getNext() {
		return next;
	}

	/**
	 * Set or update next SingleLinkedNode
	 * @param next
	 */
	public void setNext(SingleLinkedNode next) {
		this.next = next;
	}

	@Override
	public String toString() {
		return "SingleLinkedNode [name=" + name + ", next=" + next + "]";
	}
	
}

/**
 * SingleLinkedList
 * @author Haijun Su
 * Created date: 2013-9-26
 */
class SingleLinkedList {
	
}