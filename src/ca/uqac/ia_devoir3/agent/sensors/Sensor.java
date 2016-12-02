package ca.uqac.ia_devoir3.agent.sensors;

import ca.uqac.ia_devoir3.agent.ForestAgent;
import ca.uqac.ia_devoir3.model.environment.Environment;

/**
 * Created by dhawo on 29/11/2016.
 */
public abstract class Sensor {
    protected Environment env;
    protected ForestAgent robot;

    public Sensor(Environment env, ForestAgent robot) {
        this.env = env;
        this.robot = robot;
    }

    abstract public boolean useSensor();
}
