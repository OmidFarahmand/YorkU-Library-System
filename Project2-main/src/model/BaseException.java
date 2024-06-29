package model;

public abstract class BaseException extends Exception {

    public String message;
    public BaseException(String ms){
        super(ms);
        this.message = ms;
    }
}
