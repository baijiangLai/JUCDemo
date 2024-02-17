package org.example;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Main main = new Main();
        main.test1();
        main.test2();
        main.test3();
    }

    public void test1() {
        long start = System.currentTimeMillis();
        Long sum = 0L;
        for (Long i = 0L; i <= 10_0000_0000L; i++) {
            sum += i;
        }
        long end = System.currentTimeMillis();
        System.out.println("test1===>和为" + sum + ",耗时" + (end - start));
    }

    public void test2() {
        try {
            long start = System.currentTimeMillis();
            // 也可以使用公用的线程池 ForkJoinPool.commonPool()：
            // pool = ForkJoinPool.commonPool()
            ForkJoinPool pool = new ForkJoinPool();
            ForkJoinTask<Long> task = pool.submit(new SumForkJoin(0L, 10_0000_0000L));//提交任务

            Long sum = task.get();

            long end = System.currentTimeMillis();
            System.out.println("test2===>和为" + sum + ",耗时" + (end - start));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void test3() {//没有Long的拆箱装箱操作
        long start = System.currentTimeMillis();
        long sum = LongStream.rangeClosed(0L, 10_0000_0000L).parallel().reduce(0, Long::sum);//reduce第一个参数为0，为了保证，在进行流条件计算下，保证每次累计的值不受多线程影响
        long end = System.currentTimeMillis();
        System.out.println("test3===>和为" + sum + ",耗时" + (end - start));
    }

}