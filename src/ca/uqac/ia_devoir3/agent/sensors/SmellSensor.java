package ca.uqac.ia_devoir3.agent.sensors;

import ca.uqac.ia_devoir3.agent.ForestAgent;
import ca.uqac.ia_devoir3.modele.environment.Environment;

/**
 * Created by dhawo on 29/11/2016.
 */
public class SmellSensor extends Sensor {
    public SmellSensor(Environment env, ForestAgent robot) {
        super(env, robot);
    }

    @Override
    public boolean useSensor() {
        return env.getTile(robot.getPos()).isSmell();
    }
}
