package cn.wbw.study.thread;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.thread.ThreadUtil;

import java.util.concurrent.TimeUnit;

/**
 * 过期的 suspend()、resume()、stop()
 * Java并发编程 - 93
 *
 * @author wbw
 * @date 2019/12/6 16:46
 */
public class TheadDeprecatedDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread printThread = new Thread(new Runner(), "PrintThread");
        printThread.setDaemon(true);
        printThread.start();
        TimeUnit.SECONDS.sleep(3);
        // 将 PrintThread 进行暂停，输出内容工作停止
        printThread.suspend();
        System.out.println("main suspend PrintThread at " + DateUtil.date().toTimeStr());
        TimeUnit.SECONDS.sleep(3);
        // 将 PrintThread 进行恢复，输出内容继续
        printThread.resume();
        System.out.println("main resume PrintThread at " + DateUtil.date().toTimeStr());
        TimeUnit.SECONDS.sleep(3);
        // 将 PrintThread 进行种植，输出内容停止
        printThread.stop();
        System.out.println("main stop PrintThread at " + DateUtil.date().toTimeStr());
        TimeUnit.SECONDS.sleep(3);
    }

    static class Runner implements Runnable {

        @Override
        public void run() {
            while (true) {
                System.out.println(Thread.currentThread().getName() + " Run at " + DateUtil.date().toTimeStr());
                ThreadUtil.sleep(1);
            }
        }
    }
}
