package cn.wbw.study.thread;

/**
 * 线程死锁
 *
 * @author wbw
 * @date 2020/1/2 10:07
 */
public class DeadLockTest2 {
    /**
     * 创建资源
     */
    private static Object resourceA = new Object();
    private static Object resourceB = new Object();

    public static void main(String[] args) {
        // 创建线程A
        Thread threadA = new Thread(() -> {
            synchronized (resourceA) {
                System.out.println(Thread.currentThread() + "\tgetResourceA");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread() + "\twaiting getResourceB");
                synchronized (resourceB) {
                    System.out.println(Thread.currentThread() + "\tgetResourceB");
                }
            }
        });
        // 创建线程B
        Thread threadB = new Thread(() -> {
            synchronized (resourceB) {
                System.out.println(Thread.currentThread() + "\tgetResourceB");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread() + "\twaiting getResourceA");
                synchronized (resourceA) {
                    System.out.println(Thread.currentThread() + "\tgetResourceA");
                }
            }
        });
        // 启动线程
        threadA.start();
        threadB.start();
    }
}
