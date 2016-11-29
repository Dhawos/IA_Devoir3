package ca.uqac.ia_devoir3.agent;

import ca.uqac.ia_devoir3.agent.actions.Action;
import ca.uqac.ia_devoir3.modele.environment.Position;

/**
 * Created by dhawo on 29/11/2016.
 */
public class ForestAgent{
    private boolean alive;
    private Position pos;

    /*private Action chooseAnAction(){
        return new Action();
    }*/

    public boolean isAlive(){
        return alive;
    }

    public Position getPos() {
        return pos;
    }
}
