package cn.wbw.study.thread;

/**
 * 同步方法
 * Java 并发编程的艺术 - 97
 *
 * @author wbw
 * @date 2019/12/6 17:12
 */
public class ThreadSyncDemo {
    public static void main(String[] args) {
        // 对 synchronized Class 对象加锁
        synchronized (ThreadSyncDemo.class) {

        }
        // 静态方法同步。对synchronized class 对象加锁
        m();
    }

    public static synchronized void m() {
    }
}
