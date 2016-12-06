package ca.uqac.ia_devoir3.agent;

import ca.uqac.ia_devoir3.model.environment.Direction;
import ca.uqac.ia_devoir3.model.environment.Position;

/**
 * Created by dhawo on 29/11/2016.
 */
public class ForestAgent{
    private boolean alive;
    private Position pos;

    public ForestAgent(Position pos){
        this.pos = pos;
        this.alive = true;
    }
    /*private Action chooseAnAction(){
        return new Action();
    }*/

    public boolean isAlive(){
        return alive;
    }

    public Position getPos() {
        return pos;
    }

    public void move(Direction direction){
        if(direction == Direction.UP){
            this.pos.setX(this.pos.getX() - 1);
        }
        else if(direction == Direction.DOWN){
            this.pos.setX(this.pos.getX() + 1);
        }
        else if(direction == Direction.LEFT){
            this.pos.setX(this.pos.getY() - 1);
        }
        else if(direction == Direction.RIGHT){
            this.pos.setX(this.pos.getY() + 1);
        }
    }
}
