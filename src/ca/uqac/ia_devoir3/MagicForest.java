package ca.uqac.ia_devoir3;

import ca.uqac.ia_devoir3.controllers.MoveController;
import ca.uqac.ia_devoir3.model.environment.Environment;
import ca.uqac.ia_devoir3.model.environment.Map;
import ca.uqac.ia_devoir3.view.MainFrame;

/**
 * Created by dhawo on 28/11/2016.
 */
public class MagicForest {
    public static void main(String[] args){
        Environment environment = new Environment();
        MainFrame mf = new MainFrame();

        //init Controller
        MoveController moveController = new MoveController(environment);
        mf.getControlPanel().getMoveButton().addMouseListener(moveController);

        //init views
        environment.addObserver(mf.getForestBoard());

        environment.setMap(new Map(3));
        environment.spawnNewAgent();
    }

}
