package Locks;

import Helper.InterfaceMaker;
import Locks.Exceptions.NotLockedException;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * <p>
 *     {@code SharedLock} class will help you to acquire shared lock on file. Also, it will help you to manage reading
 *     from file and managing the paths.
 * </p>
 *
 * @author HENIL
 * @since 03-11-22
 * @version 1.0
 * */
public class SharedLock extends Lock {

    protected FileInputStream fileIn;
    protected FileChannel fileChannel;
    private FileLock fileLock;
    private boolean locked;
    public StringBuffer fileContent;
    protected BufferedReader scanner;

    public SharedLock(String rootPath, String filePath) {
        helperInterface = new InterfaceMaker(rootPath);
        actualFile = helperInterface.getFileObj(filePath);
        this.locked = false;
        this.fileContent = new StringBuffer();
    }

    public boolean acquireLock() {
        // to return the result of acquiring S lock...
        boolean locked = false;

        try {
            this.fileIn = new FileInputStream(actualFile);
            this.fileChannel = this.fileIn.getChannel();
            this.fileLock = this.fileChannel.lock(0, Long.MAX_VALUE, true);
            locked = true;
            this.locked = true;
        } catch (FileNotFoundException e) {
            // for file input stream...
            System.out.println("--E-- The file that you want to get, not exist!");
            e.printStackTrace();
        } catch (IOException e) {
            // for file lock...
            System.out.println("--E-- Some error occurred while acquiring S lock!");
            e.printStackTrace();
        }

        // return the result...
        return locked;
    }

    /**
     * <p>
     *      This method will read whole file content and it will check for the lock before reading from file.
     * </p>
     *
     * @param print Whether you want to print file data immediately when you perform read.
     *
     * @throws NotLockedException Custom exception. That is indicating you to call {@link #acquireLock()} before calling
     *                              to this method.
     * */
    public void readWholeFile(boolean print) throws NotLockedException {
        if (this.locked) {
            // if lock has been acquired...
            scanner = new BufferedReader(new InputStreamReader(this.fileIn));
            try {
                String data = scanner.readLine();
                while (data!=null) {
                    if (print) {
                        System.out.println(data);
                    }
                    this.fileContent.append(data);
                    data = scanner.readLine();
                }
            } catch (IOException e) {
                System.out.println("--E-- Some error occurred while reading from file.");
                e.printStackTrace();
            }
        } else {
            throw new NotLockedException("Consider Locking The File Before Reading It.");
        }
    }

    /**
     * <p>
     *     It will release the shared lock and close the file, file stream, file channel, etc.
     * </p>
     *
     * @throws NotLockedException thrown when you try to release the lock before actually acquiring it.
     * */
    public void releaseLock() throws NotLockedException {
        if (this.locked) {
            this.locked = false;
            try {
                this.fileLock.release();
                this.fileChannel.close();
                this.fileIn.close();
                this.scanner.close();
            } catch (IOException e) {
                System.out.println("--E-- Some error occurred while releasing the S lock!");
                e.printStackTrace();
            }
        } else {
            throw new NotLockedException("You cannot call this method until you acquire lock.");
        }
    }

    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(this.fileIn));
    }

}
