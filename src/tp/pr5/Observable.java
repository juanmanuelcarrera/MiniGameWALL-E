package tp.pr5;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * This class contains a collection of generic observers to the RobotEngine, NavigationModule and ItemContainer.
 * @author Juan Manuel Carrera García 
 *
 * @param <T> Type of the observable collection
 */
public class Observable<T> implements Iterable<T> {
	private Collection<T> observerCollection;
	
	/**
	 * Construct a Collection with a ArrayList
	 */
	protected Observable() {
		observerCollection = new ArrayList<T>();
	}

	/**
	 * Add a observer to the collection
	 * @param obser
	 */
	protected void addObserver(T obser) {
		if (!observerCollection.contains(obser))
			observerCollection.add(obser);
	}
	
	/**
	 * Get an iterator of the collection
	 * @return the iterator of the collection
	 */
	@Override
	public Iterator<T> iterator() {
		return this.observerCollection.iterator();
	}
}
