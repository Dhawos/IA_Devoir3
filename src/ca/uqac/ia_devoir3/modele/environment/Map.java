package ca.uqac.ia_devoir3.modele.environment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

/**
 * Created by dhawo on 28/11/2016.
 */
public class Map {
    private ArrayList<ArrayList<Tile>> grid;

    public Map(int n) { //Builds a nxn map
        grid = new ArrayList<>(n);
        for(int i = 0; i < n; i++){
            grid.add(new ArrayList<Tile>(n));
            for(int j = 0; j < n; j++){
                grid.get(i).add(new Tile(new Position(i,j)));
            }
        }
        //Building neighbors
        HashSet<Tile> set = new HashSet<>();
        for(ArrayList<Tile> row : grid){
            for(Tile tile : row){
                int rowIndex = tile.getPosition().getX();
                int columnIndex = tile.getPosition().getY();
                if(rowIndex - 1 >= 0){
                    set.add(grid.get(rowIndex-1).get(columnIndex));
                }
                if(rowIndex + 1 < n){
                    set.add(grid.get(rowIndex+1).get(columnIndex));
                }
                if(columnIndex + 1 < n){
                    set.add(grid.get(rowIndex).get(columnIndex + 1));
                }
                if(columnIndex - 1 >= 0){
                    set.add(grid.get(rowIndex).get(columnIndex - 1));
                }
                tile.setNeighbors(set);
            }
        }
        //Placing portal
        Random rn = new Random();
        int portalX = rn.nextInt(n);
        int portalY = rn.nextInt(n);
        grid.get(portalX).get(portalY).insertPortal();
        //Determining number of traps to place
        int nbTrapsToPlace = n - 2;
        int counterCliff = 0;
        //Placing cliffs
        while(counterCliff < nbTrapsToPlace){
            int x = rn.nextInt(n);
            int y = rn.nextInt(n);
            Tile tile = grid.get(x).get(y);
            if(!tile.isPortal() && !tile.isCliff() && !tile.isMonster()){
                counterCliff++;
                tile.insertCliff();
            }
        }
        //Placing monsters
        int counterMonsters = 0;
        while(counterMonsters < nbTrapsToPlace){
            int x = rn.nextInt(n);
            int y = rn.nextInt(n);
            Tile tile = grid.get(x).get(y);
            if(!tile.isPortal() && !tile.isCliff() && !tile.isMonster()){
                counterMonsters++;
                tile.insertMonster();
            }
        }


    }

    public Tile getTile(int i,int j){
        return grid.get(i).get(j);
    }


}
