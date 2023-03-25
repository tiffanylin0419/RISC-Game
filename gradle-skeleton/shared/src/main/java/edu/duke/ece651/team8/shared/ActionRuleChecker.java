package edu.duke.ece651.team8.shared;

//use: new NoCollisionRuleChecker<T>(new InBoundsRuleChecker<T>(null))
public abstract class ActionRuleChecker {
    private final ActionRuleChecker next;

    //constructor
    public ActionRuleChecker(ActionRuleChecker next){
        this.next=next;
    }

    /**
     * check all rules that are connected after next
     * @param action the action to be checked
     * @return null if obey rule, return String as the error message if it does not obey
     */
    protected abstract String checkMyRule(Action action);

    /**
     * check one rule
     * @return null if obey rule, return String as the error message if it does not obey
     */
    public String checkAllRule( Action action){
        // if we fail our own rule: stop the placement is not legal
        String s = checkMyRule(action);
        if (s != null) {
            return s;
        }
        //Otherwise, ask the rest of the chain.
        if (next != null) {
            return next.checkAllRule(action);
        }
        // if there are no more rules, then the placement is legal
        return null;
    }
}
