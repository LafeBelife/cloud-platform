package cn.wbw.study.thread;

/**
 * threadLocal 数据安全
 *
 * @author wbw
 * @date 2020/1/2 10:59
 */
public class ThreadLocalTest {
    /**
     * 1、print 函数
     */
    private static void print(String str) {
        // 1.1、打印当前线程在本地内存中localVariable变量的值
        System.out.println(str + ":\t" + localVariable.get());
        // 1.2、清除当前线程本地内存中的localVariable变量
        localVariable.remove();
    }

    /**
     * 2、创建threadLocal 变量
     */
    private static ThreadLocal<String> localVariable = new ThreadLocal<>();

    public static void main(String[] args) {
        // 3、创建线程一
        Thread threadOne = new Thread(() -> {
            // 3.1、设置线程one中本地变量localVariable的值
            localVariable.set("threadOne local variable");
            // 3.2、调用打印函数
            print("threadOne");
            // 3.3、打印本地变量的值
            System.out.println("threadOne remove after:\t" + localVariable.get());
        });
        // 4、创建线程two
        Thread threadTwo = new Thread(() -> {
            //4.1、设置线程two本地变量localVariable
            localVariable.set("threadTwo local variable");
            // 4.2、调用打印函数
            print("threadTwo");
            //4.3、打印本地变量的值
            System.out.println("threadTwo remove after:\t" + localVariable.get());
        });
        // 5、启动线程
        threadOne.start();
        threadTwo.start();
    }
}
