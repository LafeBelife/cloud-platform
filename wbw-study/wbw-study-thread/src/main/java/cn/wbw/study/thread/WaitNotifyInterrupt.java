package cn.wbw.study.thread;

/**
 * 当线程调用了wait(),其他线程阻断该线程，则抛出InterruptedException
 *
 * @author wbw
 * @date 2019-12-23 22:18
 */
public class WaitNotifyInterrupt {
    static Object object = new Object();

    public static void main(String[] args) {
        Thread threadA = new Thread(() -> {
            try {
                System.out.println("---begin---");
                // 阻塞当前线程
                synchronized (object) {
                    object.wait();
                }
                System.out.println("---end---");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        threadA.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("---begin interrupted threadA---");
        threadA.interrupt();
        System.out.println("---end interrupted threadA---");
    }
}
