package ca.uqac.ia_devoir3.agent.actions;

import ca.uqac.ia_devoir3.model.environment.InterfaceEnvironment;

/**
 * Created by dhawo on 05/12/2016.
 */
public class Escape extends Action {
    @Override
    public void doAction(InterfaceEnvironment env) {
        env.escape();
    }
}
