package cn.wbw.study.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 异步线程
 *
 * @author wbw
 * @date 2019-12-22 15:39
 */
public class CallerTask {
    public static class Task implements Callable<String> {

        @Override
        public String call() throws Exception {
            return "hello";
        }
    }

    public static void main(String[] args) {
        // 创建异步线程
        FutureTask<String> futureTask = new FutureTask<>(new Task());
        // 启动线程
        new Thread(futureTask).start();
        try {
            String result  = futureTask.get();
            System.out.println(result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
