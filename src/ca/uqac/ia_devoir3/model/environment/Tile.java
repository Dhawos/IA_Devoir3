package ca.uqac.ia_devoir3.model.environment;

import java.util.HashSet;
import java.util.Observable;

/**
 * Created by dhawo on 28/11/2016.
 */
public class Tile extends Observable{
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
        setChanged();
        notifyObservers();
    }

    public void insertMonster(){
        monster = true;
        for(Tile tile : neighbors){
            tile.insertSmell(true);
        }
        setChanged();
        notifyObservers();
    }

    public void removeMonster(){
        monster = false;
        for(Tile tile : neighbors){
            boolean foundOtherMonster = false;
            for (Tile otherTile : tile.neighbors){
                if(otherTile.isMonster() && otherTile.getPosition() != pos) {
                    foundOtherMonster = true;
                }
            }
            if (!foundOtherMonster){
                tile.insertSmell(false);
            }
        }
        setChanged();
        notifyObservers();
    }

    public void insertCliff(){
        cliff = true;
        for(Tile tile : neighbors){
            tile.insertWind(true);
        }
        setChanged();
        notifyObservers();
    }

    public void insertSmell(boolean smell) {
        this.smell = smell;
        setChanged();
        notifyObservers();
    }

    public void insertWind(boolean wind) {
        this.wind = wind;
        setChanged();
        notifyObservers();
    }

    public void setNeighbors(HashSet<Tile> neighbors){
        this.neighbors = neighbors;
    }

}
