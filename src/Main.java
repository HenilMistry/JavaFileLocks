import Helper.InterfaceMaker;
import Locks.ExclusiveLock;
import Locks.SharedLock;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {

    public static InterfaceMaker ifm = new InterfaceMaker("Files\\");
    public static File file = ifm.getFileObj("MySelf.txt");

    public static void writeToFile() {
        try {
            FileOutputStream fileOut = new FileOutputStream(file);

            try {
                FileChannel inputChannel = fileOut.getChannel();
                FileLock exclusiveLock = inputChannel.lock(0,Long.MAX_VALUE,false);

                try {
//                    BufferedWriter writer = new BufferedWriter(ifm.getWritableFile("MySelf.txt"));
//                    writer.append("This is happy family!");
//                    writer.close();
                    inputChannel.write(ByteBuffer.wrap("This is happy family again".getBytes(StandardCharsets.UTF_8)));
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

        ExclusiveLock SXLock = new ExclusiveLock("Files\\","MySelf.txt");
        SXLock.acquireSharedLock();

        SXLock.readWholeFile();
        SXLock.acquireExclusiveLock();
        SXLock.writeToFile("This is me henil!");

        SXLock.releaseSharedLock();

        SXLock.acquireExclusiveLock();
        SXLock.writeToFile("This is me henil!");
        SXLock.releaseExclusiveLock();
    }
}