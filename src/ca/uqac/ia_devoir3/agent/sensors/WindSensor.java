package ca.uqac.ia_devoir3.agent.sensors;

import ca.uqac.ia_devoir3.agent.ForestAgent;
import ca.uqac.ia_devoir3.model.environment.Environment;

/**
 * Created by dhawo on 29/11/2016.
 */
public class WindSensor extends Sensor {
    public WindSensor(Environment env, ForestAgent robot) {
        super(env, robot);
    }

    @Override
    public boolean useSensor() {
        return env.getTile(robot.getPos()).isWind();
    }
}
