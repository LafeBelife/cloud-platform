package cn.wbw.study.thread;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * 通过反射获取实例
 *
 * @author wbw
 * @date 2020-01-05 10:31
 */
public class UnSafeTest02 {
    private static final Unsafe unsafe;
    private static final long stateOffset;
    private volatile long state = 0;

    static {
        try {
            // 使用反射获取UnSafe的成员变量theUnsafe
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            // 设置为可存取
            field.setAccessible(true);
            // 获取改变量的值
            unsafe = (Unsafe) field.get(null);
            // 获取state咋填 UnSafeTest02中的偏移量
            stateOffset = unsafe.objectFieldOffset(UnSafeTest02.class.getDeclaredField("state"));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.out.println(e);
            throw new Error(e);
        }
    }

    public static void main(String[] args) {
        UnSafeTest02 test02 = new UnSafeTest02();
        Boolean success = unsafe.compareAndSwapInt(test02, stateOffset, 1, 1);
        System.out.println(stateOffset);
        System.out.println(test02.state);
        System.out.println(success);
    }
}
