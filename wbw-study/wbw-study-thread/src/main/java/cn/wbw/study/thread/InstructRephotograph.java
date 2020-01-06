package cn.wbw.study.thread;

/**
 * 指令重拍
 *
 * @author wbw
 * @date 2020-01-05 14:06
 */
public class InstructRephotograph {
    public static class ReadThread extends Thread {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                if (ready) {
                    System.out.println(num + num);
                }
                System.out.println("read thread...");
            }
        }
    }

    public static class WriteThread extends Thread {
        @Override
        public void run() {
            num = 2;
            ready = true;
            System.out.println("writeThread set over");
        }
    }

    private static int num = 0;
    private static boolean ready = false;

    public static void main(String[] args) {
        ReadThread readThread = new ReadThread();
        readThread.start();

        WriteThread writeThread = new WriteThread();
        writeThread.start();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        readThread.interrupt();
        System.out.println("main exit");
    }


}
