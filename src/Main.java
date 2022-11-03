import Helper.InterfaceMaker;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.StandardCharsets;

public class Main {

    public static InterfaceMaker ifm = new InterfaceMaker("Files\\");
    public static File file = ifm.getFileObj("MySelf.txt");

    public static void writeToFile() {
        // logic and steps are same as shared lock...
        // just it's write operation...
        try {
            FileOutputStream fileOut = new FileOutputStream(file);

            try {
                FileChannel inputChannel = fileOut.getChannel();
                FileLock exclusiveLock = inputChannel.lock(0,Long.MAX_VALUE,false);

                try {
                    inputChannel.write(ByteBuffer.wrap("This is happy family".getBytes(StandardCharsets.UTF_8)));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                exclusiveLock.release();
                fileOut.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        try {
            // get the file input stream...
            FileInputStream fileIn = new FileInputStream(file);

            // from stream, get the channel to lock and read...
            FileChannel channel = fileIn.getChannel();
            // make a shared lock...
            FileLock sharedLock = channel.lock(0,Long.MAX_VALUE, true);

            // scanner to read from inout stream...
            BufferedReader scan = new BufferedReader(new InputStreamReader(fileIn));
            // reading whole file...
            String data = scan.readLine();
            while (data!=null) {
                System.out.println(data);
                data = scan.readLine();
            }

            // try to write by making request for exclusive lock...
            // it will throw "OverlappingFileLockException"...
            writeToFile();

            // release shared lock, close all streams and chnnels...
            sharedLock.release();
            scan.close();
            channel.close();
            fileIn.close();

            // try to write again...
            writeToFile();

        } catch (FileNotFoundException e) {
            // for input stream...
            throw new RuntimeException(e);
        } catch (IOException e) {
            // for FileLock...
            throw new RuntimeException(e);
        }

    }
}