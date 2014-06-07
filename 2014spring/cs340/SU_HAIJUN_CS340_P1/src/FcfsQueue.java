import java.util.ArrayList;
import java.util.List;

/**
 *
 */

/**
 * @author Haijun Su
 *
 */
public class FcfsQueue<T> {
	List<T> items = new ArrayList<T>();

	public void add(T item) {
		items.add(item);
	}

	public T pop() {
		if(items.isEmpty()) {
			return null;
		}
		return items.remove(0);
	}
}
