package kr.jaen.android.thread;

public class Test {
    public static void main(String[] args) {
        System.out.println("Hello World");
//        MyThread t = new MyThread();
//        t.run();    // 호출하는거라서 멀티스레드 아님
//        t.start();
//        MyRunnable runnable = new MyRunnable();
//        Thread my = new Thread(runnable);   // new Thread(new MyRunnable()).start()
//        my.start();

        new Thread(
            () -> {
                for (int i = 0; i < 10; i++) {
                    System.out.println("MyRunnable"+i);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        ).start();

        for (int i = 0; i < 10; i++) {
            System.out.println("MainThread"+i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class MyThread extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("MyThread"+i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class MyRunnable implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("MyRunnable"+i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}