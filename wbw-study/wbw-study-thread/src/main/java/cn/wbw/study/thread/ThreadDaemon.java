package cn.wbw.study.thread;

/**
 * 守护线程
 *
 * @author wbw
 * @date 2020/1/2 10:22
 */
public class ThreadDaemon {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            while (true) {
            }
        });
        // 设为守护线程
        thread.setDaemon(true);
        thread.start();
        System.out.println("main thread is over");
    }
}
