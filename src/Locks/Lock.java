package Locks;

import Helper.InterfaceMaker;

import java.io.File;

/**
 * <p>
 *     {@code Lock} class will be able to serve the basic shared resources between any type of lock that you want to
 *     make on file.
 * </p>
 *
 * @author HENIL
 * @since 03-11-22
 * @version 1.0
 * */
public abstract class Lock {

    // for helping to get rid of path giving while requiring the file, fileStream, etc.
    public static InterfaceMaker helperInterface;
    // file that will hold lock...
    public static File actualFile;

}
