package cn.wbw.study.source.code;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机数生成
 *
 * @author wbw
 * @date 2020/1/6 10:11
 */
public class RandomTest {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        // 1 创建一个默认种子的随机数生成器
        Random random = new Random();
        // 2 输出10个在0~5（包含0，不包含5）之间的随机数
        for (int i = 0; i < 10; i++) {
            System.out.println(random.nextInt(5));
        }
        System.out.println("elapsed time(ms):\t" + (System.currentTimeMillis() - startTime));
    }

    @Test
    public void test() {
        long startTime = System.currentTimeMillis();
        // 获取一个随机数生成器
        ThreadLocalRandom random = ThreadLocalRandom.current();
        // 输出10个在0~5（包含0，不包含5）之间的随机数
        for (int i = 0; i < 10; i++) {
            System.out.println(random.nextInt(5));
        }
        System.out.println("elapsed time(ms):\t" + (System.currentTimeMillis() - startTime));
    }
}
