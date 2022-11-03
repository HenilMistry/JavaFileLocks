# JavaFileLocks
This repository contains the demonstration of Java File Locks with explanation and useful Readme.

# Poster
I hope this poster will help you to understand the whole topic in one go.

![](C:\Users\HENIL\Downloads\Locks.jpg)

# Index
- [Theory](#Theory)
  - [Locks](#Locks)
    - [Types of locks](#Types of locks?)
      - [Shared](#Shared Lock)
      - [Exclusive](#Exclusive Lock)
    - [Purpose](#Purpose?)
- [How to achieve in Java?](#How to?)

Theory
======

## Locks
Basically, what do you mean by locks? The lock simply means restriction on some action. Here you can consider that the
  **"some action"** refers to File operations such as **"read"** or **"write"**.
  - ### Types of locks?
    There are mainly two types of lock, represented in **top left corner** of the poster. One is **shared lock** and other
    is **exclusive lock**. They are often referred as **"read lock"** and **"write lock"** respectively.
    - #### Shared Lock
        One cannot perform write operation if they have acquired this lock on some file.
    - #### Exclusive Lock
        This lock will allow you to perform write and read both operation on this file.
  
  - ### Purpose?
    Together they both helps to ensure data integrity. Suppose when some thread is reading the file, the time until it
    reads, any other thread should not read that file or write to that file. While we want to perform write operation then also
    it should be taken care so, we will take help of Exclusive Lock.

# How to?
 Let's have some coding in Java.

- ### Acquire Shared Lock

    
    // create an object of file.
    File file = new File(path);
    // File input stream
    FileInputStream fIn = new FileInputStream(file);
    // get channel...
    FileChannel fChannel = fIn.getChannel();
    // acquire lock...
    FileLock lock = fChannel.lock(0, Long.MAX_VALUE, true);

- ### Acquire Exclusive Lock

    
      // create an object of file.
      File file = new File(path);
      // File output stream
      FileOutputStream fOut = new FileOutputStream(file);
      // get channel...
      FileChannel fChannel = fOut.getChannel();
      // acquire lock...
      FileLock lock = fChannel.lock(0, Long.MAX_VALUE, false);
    
    
        