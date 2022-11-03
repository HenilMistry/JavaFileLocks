import Locks.Exceptions.NotLockedException;
import Locks.ExclusiveLock;
import Locks.SharedLock;

public class TestPackage {
    public static void main(String[] args) throws NotLockedException {
        SharedLock SLock = new SharedLock("Files\\","MySelf.txt");
        ExclusiveLock XLock = new ExclusiveLock("Files\\","MySelf.txt");

        SLock.acquireLock();
        SLock.readWholeFile(true);
        // this will throw OverlappingFileLockException...
        // XLock.writeToFile("This is amazing!");
        SLock.releaseLock();
        XLock.acquireLock(true);
        XLock.appendToFile("\nThis is amazing right...?");
        XLock.releaseLock();
    }
}
