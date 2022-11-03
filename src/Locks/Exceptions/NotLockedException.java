package Locks.Exceptions;

/**
 * <p>
 *     {@code NotLockedException} will be thrown when you'll try to either {@code Read} from or {@code Write} to file
 *     before acquiring proper lock.
 * </p>
 *
 * */
public class NotLockedException extends Exception {


    public NotLockedException() {
        super("Please consider locking first before this action!");
    }


    public NotLockedException(String message) {
        super(message);
    }
}
