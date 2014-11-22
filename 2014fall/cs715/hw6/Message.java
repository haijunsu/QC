
import java.io.Serializable;

/**
 * 
 * @author Haijun Su Date Nov 18, 2014
 *
 */
public class Message implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3107526342754745403L;
	public int number, id;

	public Message(int number, int id) {
		this.number = number;
		this.id = id;
	}

	@Override
	public String toString() {
		return "Message [number=" + number + ", id=" + id + "]";
	}

}
