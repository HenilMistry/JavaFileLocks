package Locks;

import Helper.InterfaceMaker;
import Locks.Exceptions.NotLockedException;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.StandardCharsets;

public class ExclusiveLock extends Lock {

    protected FileOutputStream fileOut;
    protected FileChannel fileChannel;
    private FileLock fileLock;
    private boolean locked;
    public StringBuffer fileContent;

    public ExclusiveLock(String rootPath, String filePath) {
        helperInterface = new InterfaceMaker(rootPath);
        actualFile = helperInterface.getFileObj(filePath);
        this.fileContent = new StringBuffer();
        this.locked = false;
    }

    public boolean acquireLock(boolean append) {
        boolean locked = false;

        try {
            this.fileOut = new FileOutputStream(actualFile, append);
            this.fileChannel = this.fileOut.getChannel();
            this.fileLock = this.fileChannel.lock(0, Long.MAX_VALUE, false);
            locked = true;
            this.locked = true;
        } catch (FileNotFoundException e) {
            System.out.println("--E-- File that you try to get, not exist!");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("--E-- Some error occurred while acquiring X lock!");
            e.printStackTrace();
        }

        return locked;
    }

    public void acquireLock() {
        this.acquireLock(false);
    }

    private void write(String data) {
        try {
            this.fileChannel.write(ByteBuffer.wrap(data.getBytes(StandardCharsets.UTF_8)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

   private void append(String data) {
        try {
            this.fileChannel.write(ByteBuffer.wrap(data.getBytes(StandardCharsets.UTF_8)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void writeToFile(String data) {
        if (this.locked) {
            this.write(data);
        } else {
            if (this.acquireLock(false)) {
                this.write(data);
            } else {
                System.out.println("--L-- Cannot acquire X lock for writing to file!");
            }
        }
    }

    public void appendToFile(String data) {
        if (this.locked) {
            this.append(data);
        } else {
            if (this.acquireLock(false)) {
                this.append(data);
            } else {
                System.out.println("--L-- Cannot acquire X lock for writing to file!");
            }
        }
    }

    public void releaseLock() throws NotLockedException {
        if (this.locked) {
            this.locked = false;
            try {
                this.fileLock.release();
                this.fileChannel.close();
                this.fileOut.close();
            } catch (IOException e) {
                System.out.println("--E-- Some error occurred while releasing the S lock!");
                e.printStackTrace();
            }
        } else {
            throw new NotLockedException("You cannot call this method until you acquire lock.");
        }
    }
}
