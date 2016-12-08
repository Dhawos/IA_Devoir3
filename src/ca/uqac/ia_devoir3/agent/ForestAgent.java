package ca.uqac.ia_devoir3.agent;

import ca.uqac.ia_devoir3.agent.PrologEngine.JPLPrologInterface;
import ca.uqac.ia_devoir3.agent.actions.*;
import ca.uqac.ia_devoir3.agent.sensors.LightSensor;
import ca.uqac.ia_devoir3.agent.sensors.SmellSensor;
import ca.uqac.ia_devoir3.agent.sensors.WindSensor;
import ca.uqac.ia_devoir3.model.environment.*;
import org.jpl7.Integer;

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
    private JPLPrologInterface prologInterface;
    private InterfaceEnvironment envInterface;



    public ForestAgent(Position pos, int currentMapSize, Environment env){
        this.pos = pos;
        this.alive = true;
        this.smellSensor = new SmellSensor(env,this);
        this.windSensor = new WindSensor(env,this);
        this.lightSensor = new LightSensor(env,this);
        this.prologInterface = new JPLPrologInterface();
        this.map = new Map(currentMapSize);
        this.envInterface = new InterfaceEnvironment(env,this);
    }

    public void resetKnowledgeBase(){
        this.prologInterface = new JPLPrologInterface();
    }

    private void updateState(){
        Tile tile = map.getTile(pos.getX(),pos.getY());
        //Updating state and Sending info to knowledge database
        prologInterface.assertion("safe("+pos.getX()+","+pos.getY()+")");
        prologInterface.assertion("visited("+pos.getX()+","+pos.getY()+")");
        if(smellSensor.useSensor()){
            tile.insertSmell(true);
            for(Tile neighborTile : tile.getNeighbors()){
                prologInterface.assertion("neighborSmell("+neighborTile.getPosition().getX()+","+neighborTile.getPosition().getY()+")");
            }
        }
        if(lightSensor.useSensor()){
            prologInterface.assertion("safe("+pos.getX()+","+pos.getY()+")");
            prologInterface.assertion("portal("+pos.getX()+","+pos.getY()+")");
            tile.insertPortal();
        }
        if(windSensor.useSensor()){
            tile.insertWind(true);
            for(Tile neighborTile : tile.getNeighbors()){
                prologInterface.assertion("neighborWind("+neighborTile.getPosition().getX()+","+neighborTile.getPosition().getY()+")");
            }
        }
        if(!tile.isSmell() && !tile.isWind()){
            for(Tile neighborTile : tile.getNeighbors()){
                prologInterface.assertion("safe("+neighborTile.getPosition().getX()+","+neighborTile.getPosition().getY()+")");
            }
        }
    }

    public void chooseAndDoAction(){
        updateState();
        //Get action and do it
        Tile currentTile = map.getTile(pos.getX(),pos.getY());
        if(currentTile.isPortal()){
            Action chosenAction = new Escape();
            chosenAction.doAction(envInterface);
        }else{
            java.util.Map[] safeTiles = prologInterface.request2Vars("tileRemaining(X,Y)");
            Tile objTile = null;
            for(java.util.Map solution : safeTiles){
                Integer jplIntX = (Integer)solution.get("X");
                int X = jplIntX.intValue();
                Integer jplIntY = (Integer)solution.get("Y");
                int Y = jplIntY.intValue();
                Tile testedTile = map.getTile(X,Y);
                if(currentTile.getNeighbors().contains(testedTile)){
                    objTile = testedTile;
                    break;
                }
            }
            if(objTile == null){
                java.util.Map[] safeButVisitedSolutions = prologInterface.request2Vars("safe(X,Y)");
                for(java.util.Map solution : safeButVisitedSolutions){
                    Integer jplIntX = (Integer)solution.get("X");
                    int X = jplIntX.intValue();
                    Integer jplIntY = (Integer)solution.get("Y");
                    int Y = jplIntY.intValue();
                    Tile testedTile = map.getTile(X,Y);
                    if(currentTile.getNeighbors().contains(testedTile) && currentTile != testedTile){
                        objTile = testedTile;
                        break;
                    }
                }
            }
            if(objTile != null){
                Direction direction = currentTile.getDirection(objTile);
                Action chosenAction;
                if(direction == Direction.UP){
                    chosenAction = new MoveUp();
                    chosenAction.doAction(envInterface);
                }
                if(direction == Direction.DOWN){
                    chosenAction = new MoveDown();
                    chosenAction.doAction(envInterface);
                }
                if(direction == Direction.LEFT){
                    chosenAction = new MoveLeft();
                    chosenAction.doAction(envInterface);
                }
                if(direction == Direction.RIGHT){
                    chosenAction = new MoveRight();
                    chosenAction.doAction(envInterface);
                }
            }else{
                System.out.println("No suitable action was found");
            }

        }
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
            this.pos.setY(this.pos.getY() - 1);
        }
        else if(direction == Direction.RIGHT){
            this.pos.setY(this.pos.getY() + 1);
        }
    }
}
