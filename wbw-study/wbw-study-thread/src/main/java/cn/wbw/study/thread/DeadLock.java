package cn.wbw.study.thread;

/**
 * 死锁
 *
 * @author wbw
 * @date 2019-12-22 19:15
 */
public class DeadLock {
    /**
     * 创建资源
     */
    private static final Object resourceA = new Object();
    private static final Object resourceB = new Object();

    public static void main(String[] args) throws InterruptedException {
        // 创建线程
        Thread threadA = new Thread(() -> {
            try {
                // 获取resourceA共享资源的监视器锁
                synchronized (resourceA) {
                    System.out.println("threadA get resourceA lock");
                    // 获取resourceB共享资源的监视器锁
                    synchronized (resourceB) {
                        System.out.println("threadA get resourceB lock");
                        // 线程a阻塞，并释放获取到resourceA的锁
                        System.out.println("threadA release resourceA lock");
                        resourceA.wait();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread threadB = new Thread(() -> {
            // 休眠一秒
            try {
                Thread.sleep(1000);
                // 获取resourceA共享监视器资源的锁
                synchronized (resourceA) {
                    System.out.println("threadB get resourceA lock");
                    System.out.println("threadB try get resourceB lock");
                    // 获取resourceB共享资源监视器的锁
                    synchronized (resourceB) {
                        System.out.println("threadB get resourceB lock");
                        // 线程b阻塞，并释放获取到resourceA的锁
                        System.out.println("threadB release resourceA lock");
                        resourceA.wait();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        threadA.start();
        threadB.start();
        // 等待线程结束
        threadA.join();
        threadB.join();
        System.out.println("main over");
    }
}
