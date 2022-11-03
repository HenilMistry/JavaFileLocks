import Helper.InterfaceMaker;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class AnotherMain {

    public static InterfaceMaker ifm = new InterfaceMaker("Files\\");

    public static void main(String[] args) {
        try {
            FileOutputStream fileOut = new FileOutputStream(ifm.getFileObj("MySelf.txt"));

            try {
                FileChannel inputChannel = fileOut.getChannel();
                FileLock exclusiveLock = inputChannel.lock(0,Long.MAX_VALUE,false);

                BufferedWriter writer = new BufferedWriter(ifm.getWritableFile("MySelf.txt"));
                writer.write("This is happy family!");
                writer.close();

                exclusiveLock.release();

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
