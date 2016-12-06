package ca.uqac.ia_devoir3.agent;

import ca.uqac.ia_devoir3.agent.actions.Action;
import ca.uqac.ia_devoir3.agent.actions.Escape;
import ca.uqac.ia_devoir3.agent.sensors.LightSensor;
import ca.uqac.ia_devoir3.agent.sensors.SmellSensor;
import ca.uqac.ia_devoir3.agent.sensors.WindSensor;
import ca.uqac.ia_devoir3.model.environment.*;

/**
 * Created by dhawo on 29/11/2016.
 */
public class ForestAgent{
    private boolean alive;
    private Position pos;
    private Map map;
    private SmellSensor smellSensor;
    private WindSensor windSensor;
    private LightSensor lightSensor;



    public ForestAgent(Position pos, int currentMapSize, Environment env){
        this.pos = pos;
        this.alive = true;
        this.smellSensor = new SmellSensor(env,this);
        this.windSensor = new WindSensor(env,this);
        this.lightSensor = new LightSensor(env,this);
    }

    private void updateState(){
        Tile tile = map.getTile(pos.getX(),pos.getY());
        if(smellSensor.useSensor()){
            tile.insertSmell(true);
        }
        if(lightSensor.useSensor()){
            tile.insertPortal();
        }
        if(windSensor.useSensor()){
            tile.insertWind(true);
        }
    }

    private Action chooseAnAction(){
        updateState();
        //Write all knowledge to a file
        //Load Rules and Knowledge base
        //Get action and do it

        //Placeholder
        return new Escape();
    }

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
