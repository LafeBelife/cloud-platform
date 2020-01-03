package cn.wbw.study.thread;

/**
 * threadLocal inheritedThreadLocal
 *
 * @author wbw
 * @date 2020/1/2 14:27
 */
public class ThreadLocalAndInheritedTest {

    /**
     * 创建线程变量
     */
//    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();
    private static ThreadLocal<String> threadLocal = new InheritableThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        // 设置线程变量值
        threadLocal.set("hello world");
        // 启动子线程
        Thread thread = new Thread(() -> {
            System.out.println("thread:\t" + threadLocal.get());
            threadLocal.set("child hello");
            System.out.println("thred:\t" + threadLocal.get());
        });
        thread.start();
        thread.join();
        System.out.println("main:\t" + threadLocal.get());
    }
}
