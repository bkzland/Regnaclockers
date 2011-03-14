package engine.map.event.fulfillable;

/**
 * Classes which can fulfill something.
 * 
 * @author regnaclockers
 */
public interface Fulfillable {

	/**
	 * should return true if it fulfilled a condition.
	 * 
	 * @return true, if fulfilled
	 */
	boolean isFulfilled();
}