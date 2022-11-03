package SharedLockDemo;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        ThreadDemo reader1 = new ThreadDemo("Reader1");
        ThreadDemo reader2 = new ThreadDemo("Reader2");
        ThreadDemo reader3 = new ThreadDemo("Reader3");

        reader1.start();
        reader1.join();
        reader2.start();
        reader2.join();
        reader3.start();

    }
}
