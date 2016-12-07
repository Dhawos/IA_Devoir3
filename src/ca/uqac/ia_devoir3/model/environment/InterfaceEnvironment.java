package ca.uqac.ia_devoir3.model.environment;


import ca.uqac.ia_devoir3.agent.ForestAgent;

/**
 * Created by dhawo on 29/11/2016.
 */
public class InterfaceEnvironment {
    private Environment env;
    private ForestAgent robot;

    public InterfaceEnvironment(Environment env, ForestAgent robot) {
        this.env = env;
        this.robot = robot;
    }

    public void move(Direction dir){
        env.moveAgent(dir);
    }

    public void throwRock(Direction dir){
        env.throwRock(dir);
    }

    public void escape(){
        env.exitMap();
    }
}
