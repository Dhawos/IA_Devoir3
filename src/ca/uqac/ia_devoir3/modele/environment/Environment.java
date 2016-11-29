package ca.uqac.ia_devoir3.modele.environment;

import ca.uqac.ia_devoir3.agent.ForestAgent;

/**
 * Created by dhawo on 28/11/2016.
 */
public class Environment {
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
        map = new Map(currentMapSize);
    }

    public Tile getTile(Position pos){
        return map.getTile(pos.getX(),pos.getY());
    }

    public void spawnNewAgent(){
        currentAgent = new ForestAgent(map.getSpawnPosition());
    }

}
