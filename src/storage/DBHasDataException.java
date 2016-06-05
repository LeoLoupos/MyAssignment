package storage;

public class DBHasDataException extends Exception {
    // Creation of our own exception class with four major constructors each applicable in every case.
    //In the specific use the second constructor is being in use.
    public DBHasDataException() {
    }

    public DBHasDataException(String message) {
        super(message);
    }

    public DBHasDataException(Throwable cause) {
        super(cause);
    }

    public DBHasDataException(String message, Throwable cause) {
        super(message, cause);
    }

}
