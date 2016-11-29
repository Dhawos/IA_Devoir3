package ca.uqac.ia_devoir3.modele.environment;

/**
 * Created by dhawo on 28/11/2016.
 */
public class Environment {
    private Map map;
    private int score;
    private int currentMapSize;
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

}
