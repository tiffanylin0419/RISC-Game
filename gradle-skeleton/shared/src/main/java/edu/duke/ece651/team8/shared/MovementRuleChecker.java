package edu.duke.ece651.team8.shared;

//use: new NoCollisionRuleChecker<T>(new InBoundsRuleChecker<T>(null))
public abstract class MovementRuleChecker {
    private final MovementRuleChecker next;

    //constructor
    public MovementRuleChecker(MovementRuleChecker next){
        this.next=next;
    }

    /**
     * check all rules that are connected after next
     * @param action
     * @return null if obey rule, return String as the error message if do not obey
     */
    protected abstract String checkMyRule(Action action);

    /**
     * check one rule
     * @return null if obey rule, return String as the error message if do not obey
     */
    public String checkAllRule( Action action){
        // if we fail our own rule: stop the placement is not legal
        String s = checkMyRule(action);
        if (s != null) {
            return s;
        }
        // other wise, ask the rest of the chain.
        if (next != null) {
            return next.checkAllRule(action);
        }
        // if there are no more rules, then the placement is legal
        return null;
    }
}
