package ca.uqac.ia_devoir3.exceptions;

import ca.uqac.ia_devoir3.model.environment.Direction;

/**
 * Created by dhawo on 05/12/2016.
 */
public class IllegalMoveException extends RuntimeException {
    private Direction dir;

    public IllegalMoveException(Direction dir) {
        this.dir = dir;
    }

    public IllegalMoveException(String message, Direction dir) {
        super(message);
        this.dir = dir;
    }
}
