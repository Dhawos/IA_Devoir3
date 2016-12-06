package ca.uqac.ia_devoir3.agent.PrologEngine;

import org.jpl7.*;

import java.io.File;

/**
 * Created by dhawo on 06/12/2016.
 */
public class JPLPrologInterface {

    public static void main(String[] args){
        JPL jpl = new JPL();
        Variable X = new Variable();
        String t0 = "consult('src/ca/uqac/ia_devoir3/example.pl')";
            if (!Query.hasSolution(t0)) {
                System.out.println(t0 + " failed");
                // System.exit(1);
            }else{
                System.out.println("passed");
            }
        String t1 = "jealous(vincent,mia).";
        if (!Query.hasSolution(t1)) {
            System.out.println(t1 + " failed");
            // System.exit(1);
        }else{
            System.out.println("passed");
        }
    }
}
