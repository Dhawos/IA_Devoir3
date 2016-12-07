package ca.uqac.ia_devoir3.controllers;

import ca.uqac.ia_devoir3.model.environment.Environment;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MoveController extends MouseAdapter {
    //model
    private Environment env;


    public MoveController(Environment env) {
        this.env = env;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //model.doAction();
        this.env.requestAgentAction();
    }
}
