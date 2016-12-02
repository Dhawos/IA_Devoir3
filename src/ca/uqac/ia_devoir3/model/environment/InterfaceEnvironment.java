package ca.uqac.ia_devoir3.model.environment;

import java.awt.*;

/**
 * Created by dhawo on 29/11/2016.
 */
public class InterfaceEnvironment {
    private Environment env;
    private Robot robot;

    public InterfaceEnvironment(Environment env, Robot robot) {
        this.env = env;
        this.robot = robot;
    }


}
