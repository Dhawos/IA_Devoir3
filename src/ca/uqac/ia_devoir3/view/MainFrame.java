package ca.uqac.ia_devoir3.view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    static final String TITLE = "Magic Forest";
    private ForestBoard forestBoard;
    private ControlPanel controlPanel = new ControlPanel();

    public MainFrame() {
        super(TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 768);
        setLayout(new BorderLayout(0, 0));

        forestBoard = new ForestBoard(3);
        add(controlPanel, BorderLayout.EAST);
        add(forestBoard, BorderLayout.CENTER);
        //setResizable(false);
        setVisible(true);

    }

    public ForestBoard getForestBoard() {
        return forestBoard;
    }

    public ControlPanel getControlPanel() {
        return controlPanel;
    }
}
