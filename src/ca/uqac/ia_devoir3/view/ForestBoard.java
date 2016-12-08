package ca.uqac.ia_devoir3.view;

import ca.uqac.ia_devoir3.model.environment.Environment;
import ca.uqac.ia_devoir3.model.environment.Map;
import ca.uqac.ia_devoir3.model.environment.Position;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class ForestBoard extends JPanel implements Observer{

    private TilePanel[][] tiles;
    public ForestBoard(int boardSize) {
        super(new BorderLayout());
    }

    protected JPanel createBoard(int boardSize){
        tiles = new TilePanel[boardSize][boardSize];
        JPanel board = new JPanel(new GridLayout(boardSize, boardSize));
        board.setBorder(new LineBorder(Color.DARK_GRAY, 2));
        for (int row = 0; row < boardSize; row++)
            for (int col = 0; col < boardSize; col++) {
                TilePanel tile = new TilePanel(new Position(row,col));
                tile.setBorder(new LineBorder(Color.DARK_GRAY, 2));
                tiles[row][col] = tile;
                board.add(tiles[row][col]);
            }
        return board;
    }

    public TilePanel[][] getTiles() {
        return tiles;
    }

    @Override
    public void update(Observable o, Object arg) {
            if(o instanceof Environment){
                Environment env = (Environment) o;
                if(arg == "Map"){
                    removeAll();
                    add(createBoard(env.getCurrentMapSize()), BorderLayout.CENTER);
                    for(int row = 0; row < env.getCurrentMapSize(); row++){
                        for(int col = 0; col < env.getCurrentMapSize(); col++){
                            env.getTile(new Position(row,col)).addObserver(tiles[row][col]);
                        }
                    }

                } else if(arg == "Spawn"){
                    Position spawnPosition = env.getMap().getSpawnPosition();
                    tiles[spawnPosition.getX()][spawnPosition.getY()].insertAgent();
                } else if(arg == "MoveAgent"){
                    for(TilePanel[] rowTile : tiles){
                        for(TilePanel tile : rowTile){
                            if(tile.hasAgent()){
                                tile.removeAgent();
                            }
                        }
                    }
                    Position agentPos = env.getCurrentAgent().getPos();
                    tiles[agentPos.getX()][agentPos.getY()].insertAgent();

                }


            }
    }
}
