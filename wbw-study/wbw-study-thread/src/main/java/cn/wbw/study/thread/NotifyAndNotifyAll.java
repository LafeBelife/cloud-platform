package cn.wbw.study.thread;

/**
 * 唤醒当前和唤醒全部
 *
 * @author wbw
 * @date 2019-12-23 22:27
 */
public class NotifyAndNotifyAll {
    private static volatile Object obj = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread threadA = new Thread(() -> {
            synchronized (obj) {
                System.out.println("threadA begin wait");
                try {
                    obj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("threadA end wait");
            }
        });
        Thread threadB = new Thread(() -> {
            synchronized (obj) {
                System.out.println("threadB get obj lock");
                System.out.println("threadB begin wait");
                try {
                    obj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("threadB end wait");
            }
        });
        Thread threadC = new Thread(() -> {
            synchronized (obj) {
                System.out.println("threadC begin notify");
                obj.notifyAll();
            }
        });
        threadA.start();
        threadB.start();
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadC.start();
        // 等待线程结束
        threadA.join();
        threadB.join();
        threadC.join();
        System.out.println("main over");
    }
}
