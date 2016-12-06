package ca.uqac.ia_devoir3.model.environment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Observable;
import java.util.Random;

/**
 * Created by dhawo on 28/11/2016.
 */
public class Map {
    private ArrayList<ArrayList<Tile>> grid;
    private Position spawnPosition;
    private int size;

    public Map(int n) { //Builds a nxn map
        size = n;
        grid = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            grid.add(new ArrayList<Tile>(n));
            for (int j = 0; j < n; j++) {
                grid.get(i).add(new Tile(new Position(i, j)));
            }
        }

        //Building neighbors
        for (ArrayList<Tile> row : grid) {
            for (Tile tile : row) {
                HashSet<Tile> set = new HashSet<>();
                int rowIndex = tile.getPosition().getX();
                int columnIndex = tile.getPosition().getY();
                if (rowIndex - 1 >= 0) {
                    set.add(grid.get(rowIndex - 1).get(columnIndex));
                }
                if (rowIndex + 1 < n) {
                    set.add(grid.get(rowIndex + 1).get(columnIndex));
                }
                if (columnIndex + 1 < n) {
                    set.add(grid.get(rowIndex).get(columnIndex + 1));
                }
                if (columnIndex - 1 >= 0) {
                    set.add(grid.get(rowIndex).get(columnIndex - 1));
                }
                tile.setNeighbors(set);
            }
        }

    }

    public void placeElements(){
        //Placing portal
        Random rn = new Random();
        int portalX = rn.nextInt(size);
        int portalY = rn.nextInt(size);
        grid.get(portalX).get(portalY).insertPortal();
        //Determining number of traps to place
        int nbTrapsToPlace = size - 2;
        int counterCliff = 0;
        //Placing cliffs
        while (counterCliff < nbTrapsToPlace) {
            int x = rn.nextInt(size);
            int y = rn.nextInt(size);
            Tile tile = grid.get(x).get(y);
            if (!tile.isPortal() && !tile.isCliff() && !tile.isMonster()) {
                counterCliff++;
                tile.insertCliff();
            }
        }
        //Placing monsters
        int counterMonsters = 0;
        while (counterMonsters < nbTrapsToPlace) {
            int x = rn.nextInt(size);
            int y = rn.nextInt(size);
            Tile tile = grid.get(x).get(y);
            if (!tile.isPortal() && !tile.isCliff() && !tile.isMonster()) {
                counterMonsters++;
                tile.insertMonster();
            }
        }
        //Determining spawnPosition
        boolean validPositionFound = false;
        while (!validPositionFound) {
            int x = rn.nextInt(size);
            int y = rn.nextInt(size);
            Tile tile = grid.get(x).get(y);
            if (!tile.isPortal() && !tile.isCliff() && !tile.isMonster()) {
                validPositionFound = true;
                spawnPosition = tile.getPosition();
            }
        }
    }

    public Tile getTile(int i, int j) {
        return grid.get(i).get(j);
    }

    public Position getSpawnPosition() {
        return spawnPosition;
    }
}
