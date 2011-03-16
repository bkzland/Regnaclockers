package engine.map.event.conditions;

/**
 * Classes which can fulfill something.
 * 
 * @author regnaclockers
 */
public interface Condition {

	/**
	 * should return true if it fulfilled a condition.
	 * 
	 * @return true, if fulfilled
	 */
	boolean isFulfilled();
}