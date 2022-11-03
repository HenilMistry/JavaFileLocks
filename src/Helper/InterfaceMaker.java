package Helper;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class InterfaceMaker {
    private Path pathHelper = Paths.get(".");
    private String inputDirectory;

    // constructors.....................................................................................................
    // empty constructor...
    public InterfaceMaker() {

    }

    // set the input directory initially....
    public InterfaceMaker(String id)  {
        this.inputDirectory = id;
    }

    // methods..........................................................................................................
    /*
     * This function will be responsible for setting new input directory...
     * */
    public void setInputDirectory(String inputDirectory) {
        this.inputDirectory = inputDirectory;
    }

    /*
     * This function will be responsible for getting the file...
     **/
    public FileReader getFile(String fileName) throws FileNotFoundException {
        return new FileReader(pathHelper.toAbsolutePath().toString()+"//src"+"//"+this.inputDirectory+"//"+fileName);
    }

    public File getFileObj(String name) {
        return new File(pathHelper.toAbsolutePath().toString()+"//src//"+this.inputDirectory+"//"+name);
    }

    public FileWriter getWritableFile(String name) throws IOException {
        return new FileWriter(pathHelper.toAbsolutePath().toString()+"//src//"+this.inputDirectory+"//"+name);
    }

}
