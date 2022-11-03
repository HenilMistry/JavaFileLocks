package SharedLockDemo;

import Locks.Exceptions.NotLockedException;
import Locks.SharedLock;

import java.io.BufferedReader;
import java.io.IOException;

public class ThreadDemo extends Thread {

    public ThreadDemo(String name) {
        super(name);
    }

    @Override
    public void run() {
        super.run();
        // created object of shared lock...
        SharedLock SLock = new SharedLock("Files\\","MySelf.txt");
        // acquired lock...
        SLock.acquireLock();

        // reading from file...
        BufferedReader scanner = SLock.getReader();
        try {
            String data = scanner.readLine();
            while (data!=null) {
                System.out.println("From thread : "+this.getName()+" >> "+data);
                Thread.sleep(1000);
                data = scanner.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // releasing the lock...
        try {
            SLock.releaseLock();
        } catch (NotLockedException e) {
            throw new RuntimeException(e);
        }
    }
}
