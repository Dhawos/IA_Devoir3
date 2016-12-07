package ca.uqac.ia_devoir3.agent.PrologEngine;

import org.jpl7.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by dhawo on 06/12/2016.
 */
public class JPLPrologInterface {
    private JPL jpl;

    public JPLPrologInterface() {
        jpl = new JPL();
        String t0 = "consult('src/ca/uqac/ia_devoir3/rules.pl')";
        if (!Query.hasSolution(t0)) {
            System.out.println(t0 + " failed");
            // System.exit(1);
        }else{
            System.out.println("Rules file loaded");
        }
    }

    public boolean requestNoVar(String query){
        Query jplQuery = new Query(query);
        if(jplQuery.hasSolution()) {
            return false;
        }else{
            return true;
        }
    }

    public boolean assertion(String query){
        Query jplQuery = new Query("assert("+query+").");
        if(jplQuery.hasSolution()) {
            return false;
        }else{
            return true;
        }
    }

    public Map request2Vars(String query){
        Query jplQuery = new Query(query);
        return jplQuery.oneSolution();
    }

    public static void main(String[] args){
        /*
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
        */
        JPLPrologInterface jplInterface = new JPLPrologInterface();
        jplInterface.requestNoVar("assert(visited(0,0))");
        jplInterface.requestNoVar("assert(safe(0,0))");
        jplInterface.requestNoVar("assert(safe(1,0))");
        Map map = jplInterface.request2Vars("tileRemaining(X,Y)");
        System.out.println(map.get("X"));
        System.out.println(map.get("Y"));
        map.isEmpty();
    }
}
