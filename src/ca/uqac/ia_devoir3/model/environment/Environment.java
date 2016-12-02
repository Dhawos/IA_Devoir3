package ca.uqac.ia_devoir3.model.environment;

import ca.uqac.ia_devoir3.agent.ForestAgent;

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
    private final static int SCORE_MOVEMENT_MODIFIER = 1;
    private final static int SCORE_USING_ROCK_MODIFIER = -10;


    public Environment() {
        score = 0;
        currentMapSize = 3;

    }

    public void setMap(Map map) {
        this.map = map;
        setChanged();
        notifyObservers("Map");
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

    public void spawnNewAgent(){
        currentAgent = new ForestAgent(map.getSpawnPosition());
        setChanged();
        notifyObservers("Agent");
    }

}
