package cn.wbw.study.thread;

import sun.misc.Unsafe;

/**
 * unSafe test
 *
 * @author wbw
 * @date 2020-01-03 22:51
 */
public class UnSafeTest {
    /**
     * 获取Unsafe的实例(2.2.1)
     */
    private static final Unsafe unsafe = Unsafe.getUnsafe();
    /**
     * 记录变量state在类UnSafeTest 中的偏移值(2.2.2)
     */
    private static final long stateOffset;
    /**
     * 变量
     */
    private volatile long state = 0;

    static {
        try {
            // 获取state变量在类 UnSafeTest 中的偏移值(2.2.4)
            stateOffset = unsafe.objectFieldOffset(UnSafeTest.class.getDeclaredField("state"));
        } catch (NoSuchFieldException e) {
            System.out.println(e.getLocalizedMessage());
            throw new Error(e);
        }
    }

    public static void main(String[] args) {
        // 创建实例，设置state值为1（2.2.5）
        UnSafeTest test = new UnSafeTest();
        // (2.2.6)
        Boolean success = unsafe.compareAndSwapInt(test, stateOffset, 0, 1);
        System.out.println(success);
    }
}
