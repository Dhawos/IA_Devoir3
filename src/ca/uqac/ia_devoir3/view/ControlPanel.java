package ca.uqac.ia_devoir3.view;

import ca.uqac.ia_devoir3.model.environment.Environment;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class ControlPanel extends JPanel implements Observer{
    private JButton moveButton = new JButton("Move");
    private JLabel scoreLabel = new JLabel();
    private int score = 0;
    public ControlPanel() {
        setLayout(new GridLayout(3,1,0,20));
        setBorder(new EmptyBorder(50,20,50,20));
        setPreferredSize(new Dimension(200,100));
        JPanel captionPanel = new JPanel(new GridLayout(6, 1, 5, 10));
        captionPanel.setBorder(new CompoundBorder(new EmptyBorder(0, 0, 0, 40), BorderFactory.createTitledBorder("Caption")));
        captionPanel.add(new JLabel("M is for Monster"));
        captionPanel.add(new JLabel("C is for Cliff"));
        captionPanel.add(new JLabel("P is for Portal"));
        captionPanel.add(new JLabel("S is for Smell"));
        captionPanel.add(new JLabel("W is for Wind"));
        captionPanel.add(new JLabel("A is for Agent"));

        scoreLabel.setText("Score: " + score);
        add(captionPanel);
        add(scoreLabel);
        add(moveButton);
    }

    public JButton getMoveButton() {
        return moveButton;
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof Environment){
            if(arg instanceof Integer){
                score = (Integer)arg;
                scoreLabel.setText("Score: " + score);
            }
        }
    }
}
