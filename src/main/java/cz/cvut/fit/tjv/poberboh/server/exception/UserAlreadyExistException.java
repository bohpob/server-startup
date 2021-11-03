package cz.cvut.fit.tjv.poberboh.server.exception;

public class UserAlreadyExistException extends Exception{
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
