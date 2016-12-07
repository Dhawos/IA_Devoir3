package ca.uqac.ia_devoir3.view;

import ca.uqac.ia_devoir3.model.environment.Position;
import ca.uqac.ia_devoir3.model.environment.Tile;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class TilePanel extends JPanel implements Observer {

    private Position position;
    private JLabel label = new JLabel("");

    public TilePanel(Position position) {
        setBackground(Color.WHITE);
        add(label);
    }

    public Position getPosition() {
        return position;
    }
    public void insertAgent(){
        label.setText(label.getText() + "A");
    }

    public void removeAgent(){
        label.setText(label.getText().replace("A", ""));
    }

    public boolean hasAgent(){
        return label.getText().contains("A");
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof Tile){
            Tile tile = (Tile)o;
            label.setText("");
            if(((Tile) o).isCliff()){
                label.setText(label.getText() + "C");
            }
            if(((Tile) o).isMonster()){
                label.setText(label.getText() + "M");
            }
            if(((Tile) o).isSmell()){
                label.setText(label.getText() + "S");
            }
            if(((Tile) o).isPortal()){
                label.setText(label.getText() + "P");
            }
            if(((Tile) o).isWind()){
                label.setText(label.getText() + "W");
            }
        }
    }
}
