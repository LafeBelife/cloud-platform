package cn.wbw.study.thread;

/**
 * 缓存伪共享
 *
 * @author wbw
 * @date 2020-01-05 14:23
 */
public class CacheFalseSharing {
    public static class TestForContent {
        private static final int LINE_NUM = 1024;
        private static final int COLUM_NUM = 1024;

        private static void run() {
            long[][] array = new long[LINE_NUM][COLUM_NUM];
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < LINE_NUM; i++) {
                for (int j = 0; j < COLUM_NUM; j++) {
                    array[i][j] = i * 2 + j;
                }
            }
            long endTime = System.currentTimeMillis();
            System.out.println("cache time:\t" + (endTime - startTime));
        }
    }

    public static class TestForContent2 {
        private static final int LINE_NUM = 1024;
        private static final int COLUM_NUM = 1024;

        private static void run() {
            long[][] array = new long[LINE_NUM][COLUM_NUM];
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < COLUM_NUM; i++) {
                for (int j = 0; j < LINE_NUM; j++) {
                    array[j][i] = i * 2 + j;
                }
            }
            long endTime = System.currentTimeMillis();
            System.out.println("no cache time:\t" + (endTime - startTime));
        }
    }

    public static void main(String[] args) {
        TestForContent2.run();
        System.out.println();
        TestForContent.run();
    }
}
