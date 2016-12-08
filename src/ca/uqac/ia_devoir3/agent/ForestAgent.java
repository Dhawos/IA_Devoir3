package ca.uqac.ia_devoir3.agent;

import ca.uqac.ia_devoir3.agent.PrologEngine.JPLPrologInterface;
import ca.uqac.ia_devoir3.agent.actions.*;
import ca.uqac.ia_devoir3.agent.sensors.LightSensor;
import ca.uqac.ia_devoir3.agent.sensors.SmellSensor;
import ca.uqac.ia_devoir3.agent.sensors.WindSensor;
import ca.uqac.ia_devoir3.model.environment.*;
import org.jpl7.Integer;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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
    private static JPLPrologInterface prologInterface = new JPLPrologInterface();;
    private InterfaceEnvironment envInterface;



    public ForestAgent(Position pos, int currentMapSize, Environment env){
        this.pos = pos;
        this.alive = true;
        this.smellSensor = new SmellSensor(env,this);
        this.windSensor = new WindSensor(env,this);
        this.lightSensor = new LightSensor(env,this);
        this.map = new Map(currentMapSize);
        this.envInterface = new InterfaceEnvironment(env,this);
        resetKnowledgeBase();
    }

    public void resetKnowledgeBase(){
        prologInterface.requestNoVar("retractall(safe(X,Y))");
        prologInterface.requestNoVar("retractall(visited(X,Y))");
        prologInterface.requestNoVar("retractall(neighborSmell(X,Y))");
        prologInterface.requestNoVar("retractall(neighborWind(X,Y))");
    }

    private void updateState(){
        Tile tile = map.getTile(pos.getX(),pos.getY());
        //Updating state and Sending info to knowledge database
        if(!prologInterface.requestNoVar("safe("+tile.getPosition().getX()+","+tile.getPosition().getY()+").")){
            prologInterface.assertion("safe("+pos.getX()+","+pos.getY()+")");
        }
        if(!prologInterface.requestNoVar("visited("+tile.getPosition().getX()+","+tile.getPosition().getY()+").")){
            prologInterface.assertion("visited("+pos.getX()+","+pos.getY()+")");
        }
        if(smellSensor.useSensor()){
            tile.insertSmell(true);
            for(Tile neighborTile : tile.getNeighbors()){
                if(!prologInterface.requestNoVar("neighborSmell("+neighborTile.getPosition().getX()+","+neighborTile.getPosition().getY()+").")){
                    prologInterface.assertion("neighborSmell("+neighborTile.getPosition().getX()+","+neighborTile.getPosition().getY()+")");
                }
            }
        }else{
            for(Tile neighbor : tile.getNeighbors()){
                prologInterface.requestNoVar("retract(neighborSmell("+neighbor.getPosition().getX()+","+neighbor.getPosition().getY()+")).");
            }
        }
        if(lightSensor.useSensor()){
            tile.insertPortal();
        }
        if(windSensor.useSensor()){
            tile.insertWind(true);
            for(Tile neighborTile : tile.getNeighbors()){
                if(!prologInterface.requestNoVar("neighborWind("+neighborTile.getPosition().getX()+","+neighborTile.getPosition().getY()+").")){
                    prologInterface.assertion("neighborWind("+neighborTile.getPosition().getX()+","+neighborTile.getPosition().getY()+")");
                }
            }
        }else{
            for(Tile neighbor : tile.getNeighbors()){
                prologInterface.requestNoVar("retract(neighborWind("+neighbor.getPosition().getX()+","+neighbor.getPosition().getY()+")).");
            }
        }
        if(!smellSensor.useSensor() && !windSensor.useSensor()){
            for(Tile neighborTile : tile.getNeighbors()){
                if(!prologInterface.requestNoVar("safe("+neighborTile.getPosition().getX()+","+neighborTile.getPosition().getY()+").")){
                    prologInterface.assertion("safe("+neighborTile.getPosition().getX()+","+neighborTile.getPosition().getY()+")");
                }
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
            LinkedList<Tile> solutions = new LinkedList<>();
            Tile objTile = null;
            for(java.util.Map solution : safeTiles){
                Integer jplIntX = (Integer)solution.get("X");
                int X = jplIntX.intValue();
                Integer jplIntY = (Integer)solution.get("Y");
                int Y = jplIntY.intValue();
                Tile testedTile = map.getTile(X,Y);
                if(currentTile.getNeighbors().contains(testedTile) && currentTile != testedTile){
                    solutions.add(testedTile);
                }
            }
            if(!solutions.isEmpty()){
                Random rn = new Random();
                int selectedSolution = rn.nextInt(solutions.size());
                objTile = solutions.get(selectedSolution);
            }
            solutions = new LinkedList<>();
            if(objTile == null){
                java.util.Map[] visited = prologInterface.request2Vars("visited(X,Y)");
                java.util.Map[] safeButVisitedSolutions = prologInterface.request2Vars("safe(X,Y)");
                if(visited.length != safeButVisitedSolutions.length){
                    for(java.util.Map solution : safeButVisitedSolutions){
                        Integer jplIntX = (Integer)solution.get("X");
                        int X = jplIntX.intValue();
                        Integer jplIntY = (Integer)solution.get("Y");
                        int Y = jplIntY.intValue();
                        Tile testedTile = map.getTile(X,Y);
                        if(currentTile.getNeighbors().contains(testedTile) && currentTile != testedTile){
                            solutions.add(testedTile);
                        }
                    }
                    if(!solutions.isEmpty()){
                        Random rn = new Random();
                        int selectedSolution = rn.nextInt(solutions.size());
                        objTile = solutions.get(selectedSolution);
                    }
                }

            }
            boolean shouldThrowRock = false;
            solutions = new LinkedList<>();
            if(objTile == null){
                //No more safe tiles
                java.util.Map[] riskySolutions = prologInterface.request2Vars("riskyTile(X,Y)");
                for(java.util.Map solution : riskySolutions){
                    Integer jplIntX = (Integer)solution.get("X");
                    int X = jplIntX.intValue();
                    Integer jplIntY = (Integer)solution.get("Y");
                    int Y = jplIntY.intValue();
                    Tile testedTile = map.getTile(X,Y);
                    if(currentTile.getNeighbors().contains(testedTile) && currentTile != testedTile){
                        shouldThrowRock = prologInterface.requestNoVar("shouldThrowRock("+X+","+Y+")");
                        solutions.add(testedTile);
                    }
                }
                if(!solutions.isEmpty()){
                    Random rn = new Random();
                    int selectedSolution = rn.nextInt(solutions.size());
                    objTile = solutions.get(selectedSolution);
                }
            }
            if(objTile != null){
                Direction direction = currentTile.getDirection(objTile);
                Action chosenAction;
                if(direction == Direction.UP){
                    if(shouldThrowRock){
                        chosenAction = new ThrowRockUp();
                        chosenAction.doAction(envInterface);
                    }
                    chosenAction = new MoveUp();
                    chosenAction.doAction(envInterface);
                }
                if(direction == Direction.DOWN){
                    if(shouldThrowRock){
                        chosenAction = new ThrowRockDown();
                        chosenAction.doAction(envInterface);
                    }
                    chosenAction = new MoveDown();
                    chosenAction.doAction(envInterface);
                }
                if(direction == Direction.LEFT){
                    if(shouldThrowRock){
                        chosenAction = new ThrowRockLeft();
                        chosenAction.doAction(envInterface);
                    }
                    chosenAction = new MoveLeft();
                    chosenAction.doAction(envInterface);
                }
                if(direction == Direction.RIGHT){
                    if(shouldThrowRock){
                        chosenAction = new ThrowRockRight();
                        chosenAction.doAction(envInterface);
                    }
                    chosenAction = new MoveRight();
                    chosenAction.doAction(envInterface);
                }
            }else{
                System.out.println("No suitable action found");
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
