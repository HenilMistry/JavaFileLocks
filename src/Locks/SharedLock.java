package Locks;

import Helper.InterfaceMaker;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class SharedLock {
    public static InterfaceMaker Interface;
    public static String fileString;
    public static File actualFile;
    protected FileChannel fileChannel;
    private FileLock fileLock;
    private boolean locked;
    private FileInputStream fileIn;
    public StringBuffer data = new StringBuffer();

    public SharedLock(String rootPath, String fileStrin) {
        Interface = new InterfaceMaker(rootPath);
        fileString = fileStrin;
        actualFile = Interface.getFileObj(fileString);
        locked = false;
    }

    public FileInputStream getInputStream() {
        if (this.locked) {
            return this.fileIn;
        } else {
            System.out.println("Please consider locking the file first!");
            throw new RuntimeException();
        }
    }

    public void acquireSharedLock() {
        if (actualFile.exists()) {
            try {
                this.fileIn = new FileInputStream(actualFile);
                this.fileChannel = fileIn.getChannel();
                this.fileLock = fileChannel.lock(0,Long.MAX_VALUE,true);
                System.out.println("The file has been locked with S mode.");
                locked = true;
            } catch (FileNotFoundException e) {
                System.out.println("The file not found!");
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("The file that you have provided, doesn't exist.");
        }
    }

    public void readWholeFile() {
        this.readWholeFile(true);
    }

    public void readWholeFile(boolean print) {
        if (this.locked) {
            BufferedReader scanner = new BufferedReader(new InputStreamReader(this.fileIn));
            try {
                String data = scanner.readLine();
                while (data!=null) {
                    this.data.append(data);
                    if (print) {
                        System.out.println(data);
                    }
                    data = scanner.readLine();
                }
                scanner.close();
            } catch (IOException e) {
                System.out.println("IO Exception while reading file after acquiring S lock.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Please consider acquiring S lock on file first!");
        }
    }

    public void releaseSharedLock() {
        try {
            this.fileLock.release();
            this.fileChannel.close();
            this.fileIn.close();
            this.locked = false;
        } catch (IOException e) {
            System.out.println("IOException has occurred while releasing the S lock.");
            e.printStackTrace();
        }
    }

}
