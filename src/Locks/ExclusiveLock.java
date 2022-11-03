package Locks;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileLock;
import java.nio.charset.StandardCharsets;

public class ExclusiveLock extends SharedLock {
    private FileOutputStream fileOut;
    private FileLock fileLock;
    private boolean locked;

    public ExclusiveLock(String rootPath, String fileString) {
        super(rootPath, fileString);
    }


    public void acquireExclusiveLock() {
        if(actualFile.exists()) {
            try {
                this.fileOut = new FileOutputStream(actualFile);
                this.fileChannel = fileOut.getChannel();
                this.fileLock = this.fileChannel.lock(0, Long.MAX_VALUE, false);
                System.out.println("The file has been locked with X mode.");
                locked = true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("The file that you have provided, doesn't exist.");
        }
    }

    public void writeToFile(String data) {
        try {
            this.fileChannel.write(ByteBuffer.wrap(data.getBytes(StandardCharsets.UTF_8)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void releaseExclusiveLock() {
        try {
            this.fileLock.release();
            this.fileChannel.close();
            this.fileOut.close();
            this.locked = false;
        } catch (IOException e) {
            System.out.println("IOException has occurred while releasing the X lock.");
            e.printStackTrace();
        }
    }
}
