package cn.wbw.study.thread;

import org.junit.Test;

/**
 * interrupt() 与 isInterupted() 区别
 *
 * @author wbw
 * @date 2020/1/2 9:22
 */
public class ThreadInterrupt {

    public static void main(String[] args) throws InterruptedException {
        Thread threadOne = new Thread(() -> {
            for (; ; ) {
            }
        });
        // 启动线程
        threadOne.start();
        // 设置中断标志
        threadOne.interrupt();
        // 获取中断标志
        System.out.println("isInterrupted:\t" + threadOne.isInterrupted());
        //获取中断标志并重置
        System.out.println("isInterrupted:\t" + threadOne.interrupted());
        // 获取中断标志并重置
        System.out.println("isInterrupted:\t" + Thread.interrupted());
        // 获取中断标志
        System.out.println("isInteerupted:\t" + threadOne.isInterrupted());
        threadOne.join();
        System.out.println("main thread is over");
    }

    @Test
    public void interrupt() throws InterruptedException {
        Thread threadOne = new Thread(() -> {
            System.out.println("threadOne isInterrupted:\t" + Thread.currentThread().isInterrupted());
            System.out.println("threadOne isInterrupted:\t" + Thread.currentThread().isInterrupted());
            // 中断标志位true 时会退出循环，并且清除中断标志
            while (!Thread.interrupted()) {

            }
            System.out.println("threadOne isInterrupted:\t" + Thread.currentThread().isInterrupted());
        });
        // 启动线程
        threadOne.start();
        // 设置中断标识
        threadOne.interrupt();
        // 等待线程死亡
        threadOne.join();
        System.out.println("main thread is over");
    }
}
