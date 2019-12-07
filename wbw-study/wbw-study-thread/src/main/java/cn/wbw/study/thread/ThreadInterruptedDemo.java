package cn.wbw.study.thread;

import cn.hutool.core.thread.ThreadUtil;

import java.util.concurrent.TimeUnit;

/**
 * 理解中断
 * Java 并发编程的艺术 - 92页
 *
 * @author wbw
 * @date 2019/12/6 15:40
 */
public class ThreadInterruptedDemo {
    public static void main(String[] args) throws InterruptedException {
        // sleepThread 不停地尝试睡眠
        Thread sleepThread = new Thread(new SleepRunner(), "sleepRhread");
        sleepThread.setDaemon(true);
        // busyThread 不停地运行
        Thread busyThread = new Thread(new BusyRunner(), "busyThread");
        busyThread.setDaemon(true);
        sleepThread.start();
        busyThread.start();
        // 休眠 5 秒，让 sleepThread 和 busyThread 充分运行
        TimeUnit.SECONDS.sleep(2);
        sleepThread.interrupt();
        busyThread.interrupt();
        System.out.println("SleepThread interrupted is " + sleepThread.isInterrupted());
        System.out.println("BusyThread interrupted is " + busyThread.isInterrupted());
        // 防止 sleepThread 与 busyThread 立即退出
        ThreadUtil.sleep(200);
    }

    static class SleepRunner implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class BusyRunner implements Runnable {

        @Override
        public void run() {
            while (true) {

            }
        }
    }
}