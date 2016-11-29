package ca.uqac.ia_devoir3.modele.environment;

import java.util.HashSet;

/**
 * Created by dhawo on 28/11/2016.
 */
public class Tile {
    private Position pos;
    private boolean smell;
    private boolean wind;
    private boolean cliff;
    private boolean monster;
    private boolean portal;
    private HashSet<Tile> neighbors;

    public Tile(Position pos) {
        this.pos = pos;
        smell = false;
        wind = false;
        cliff = false;
        monster = false;
        portal = false;
    }

    public Position getPosition() {
        return pos;
    }

    public boolean isSmell() {
        return smell;
    }

    public boolean isWind() {
        return wind;
    }

    public boolean isCliff() {
        return cliff;
    }

    public boolean isMonster() {
        return monster;
    }

    public boolean isPortal() {
        return portal;
    }

    public void insertPortal(){
        portal = true;
    }

    public void insertMonster(){
        monster = true;
        for(Tile tile : neighbors){
            tile.smell = true;
        }
    }

    public void insertCliff(){
        cliff = true;
        for(Tile tile : neighbors){
            tile.wind = true;
        }
    }

    public void setNeighbors(HashSet<Tile> neighbors){
        this.neighbors = neighbors;
    }

}
