package ca.uqac.ia_devoir3.model.environment;

import ca.uqac.ia_devoir3.agent.ForestAgent;
import ca.uqac.ia_devoir3.agent.actions.Action;
import ca.uqac.ia_devoir3.exceptions.IllegalMoveException;

import java.util.Observable;

/**
 * Created by dhawo on 28/11/2016.
 */
public class Environment extends Observable {
    private Map map;
    private int score;
    private int currentMapSize;
    private ForestAgent currentAgent;
    private final static int SCORE_FIND_PORTAL_MULTIPLICATOR = 10;
    private final static int SCORE_DEATH_MULTIPLICATOR = -10;
    private final static int SCORE_MOVEMENT_MODIFIER = -1;
    private final static int SCORE_USING_ROCK_MODIFIER = -10;

    public Environment() {
        score = 0;
        currentMapSize = 3;

    }

    public void setMap(Map map) {
        this.map = map;
        setChanged();
        notifyObservers("Map");
        this.map.placeElements();
    }

    public void moveAgent(Direction direction){
        if(direction == Direction.UP){
            if(currentAgent.getPos().getX() > 0){
                Tile nextTile = getTile(currentAgent.getPos().getX()-1, currentAgent.getPos().getY());
                if(nextTile.isMonster() || nextTile.isCliff()){
                    resetMap();
                }else{
                    updateScore(SCORE_MOVEMENT_MODIFIER);
                    currentAgent.move(direction);
                }
            }else{
                throw new IllegalMoveException(direction);
            }
        }
        else if(direction == Direction.DOWN){
            if(currentAgent.getPos().getX() < currentMapSize - 1){
                Tile nextTile = getTile(currentAgent.getPos().getX()+1, currentAgent.getPos().getY());
                if(nextTile.isMonster() || nextTile.isCliff()){
                    resetMap();
                }else{
                    updateScore(SCORE_MOVEMENT_MODIFIER);
                    currentAgent.move(direction);
                }
            }else{
                throw new IllegalMoveException(direction);
            }
        }
        else if(direction == Direction.LEFT){
            if(currentAgent.getPos().getY() > 0){
                Tile nextTile = getTile(currentAgent.getPos().getX(), currentAgent.getPos().getY()-1);
                if(nextTile.isMonster() || nextTile.isCliff()){
                    resetMap();
                }else{
                    updateScore(SCORE_MOVEMENT_MODIFIER);
                    currentAgent.move(direction);
                }
            }else{
                throw new IllegalMoveException(direction);
            }
        }
        else if(direction == Direction.RIGHT){
            if(currentAgent.getPos().getY() < currentMapSize - 1){
                Tile nextTile = getTile(currentAgent.getPos().getX(), currentAgent.getPos().getY()+1);
                if(nextTile.isMonster() || nextTile.isCliff()){
                    resetMap();
                }else{
                    updateScore(SCORE_MOVEMENT_MODIFIER);
                    currentAgent.move(direction);
                }
            }else{
                throw new IllegalMoveException(direction);
            }
        }

        setChanged();
        notifyObservers("MoveAgent");
    }

    public void throwRock(Direction direction){
        if(direction == Direction.UP){
            if(currentAgent.getPos().getX() > 0){
                Tile nextTile = getTile(currentAgent.getPos().getX()-1, currentAgent.getPos().getY());
                updateScore(SCORE_USING_ROCK_MODIFIER);
                nextTile.removeMonster();
            }else{
                throw new IllegalMoveException(direction);
            }
        }
        else if(direction == Direction.DOWN){
            if(currentAgent.getPos().getX() < currentMapSize - 1){
                Tile nextTile = getTile(currentAgent.getPos().getX()+1, currentAgent.getPos().getY());
                updateScore(SCORE_USING_ROCK_MODIFIER);
                nextTile.removeMonster();
            }else{
                throw new IllegalMoveException(direction);
            }
        }
        else if(direction == Direction.LEFT){
            if(currentAgent.getPos().getY() > 0){
                Tile nextTile = getTile(currentAgent.getPos().getX(), currentAgent.getPos().getY()-1);
                updateScore(SCORE_USING_ROCK_MODIFIER);
                nextTile.removeMonster();
            }else{
                throw new IllegalMoveException(direction);
            }
        }
        else if(direction == Direction.RIGHT){
            if(currentAgent.getPos().getY() < currentMapSize - 1){
                Tile nextTile = getTile(currentAgent.getPos().getX(), currentAgent.getPos().getY()+1);
                updateScore(SCORE_USING_ROCK_MODIFIER);
                nextTile.removeMonster();
            }else{
                throw new IllegalMoveException(direction);
            }
        }
    }

    public void resetMap(){
        updateScore( SCORE_DEATH_MULTIPLICATOR * getCurrentMapSize());
        spawnNewAgent();
    }

    public void exitMap(){
        if(getTile(currentAgent.getPos()).isPortal()){
            //Maybe send a notification
            updateScore(SCORE_FIND_PORTAL_MULTIPLICATOR * currentMapSize);
            this.currentMapSize++;
            setMap(new Map(currentMapSize));
            spawnNewAgent();
        }
    }
    public Map getMap() {
        return map;
    }

    public int getCurrentMapSize() {
        return currentMapSize;
    }

    public Tile getTile(Position pos){
        return map.getTile(pos.getX(),pos.getY());
    }

    public Tile getTile(int x , int y){
        return map.getTile(x,y);
    }

    public void updateScore(int diff){
        score += diff;
        setChanged();
        notifyObservers(new Integer(score));
    }

    public void spawnNewAgent(){
        currentAgent = new ForestAgent(map.getSpawnPosition(),currentMapSize,this);
        setChanged();
        notifyObservers("Spawn");
    }

    public void requestAgentAction(){
        currentAgent.chooseAndDoAction();
    }

    public ForestAgent getCurrentAgent() {
        return currentAgent;
    }
}
