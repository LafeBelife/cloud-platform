package cn.wbw.study.thread;

import java.security.AccessControlContext;

import static java.lang.Thread.currentThread;

/**
 * 构造线程
 *
 * @author wbw
 * @date 2019/12/4 14:47
 */
public class ThreadConstructionDemo {

    private ThreadGroup group;
    private boolean daemon;
    private int priority;
    private char[] name;
    private Runnable target;

    /**
     * java并发线程
     * 91页
     *
     * @param g         线程组
     * @param target    编译运行器
     * @param name      名称
     * @param stackSize 堆栈打下
     * @param acc       上下文访问控制
     */
    public void init(ThreadGroup g, Runnable target, String name, long stackSize, AccessControlContext acc) {
        if (name == null) {
            throw new NullPointerException("name cannot be null");
        }
        // 当前线程就是该线程的父线程
        Thread parent = currentThread();
        this.group = g;
        // 将daemon、priority属性设置为父线程的对应属性
        this.daemon = parent.isDaemon();
        this.priority = parent.getPriority();
        this.name = name.toCharArray();
        this.target = target;
//        setPriority(priority);
//        // 将父线程的 InheritableThreadLocal 复制过来
//        if (parent.)
    }
}
