import Helper.InterfaceMaker;

import java.io.BufferedWriter;
import java.io.IOException;

public class Write {
    public static void main(String[] args) throws IOException {
        InterfaceMaker ifm = new InterfaceMaker("Files\\");
        BufferedWriter writer = new BufferedWriter(ifm.getWritableFile("MySelf.txt"));
        writer.append("Anything!!");
        writer.close();
    }
}
