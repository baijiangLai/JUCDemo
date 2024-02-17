package org.example;

import java.util.concurrent.RecursiveTask;

public class SumForkJoin extends RecursiveTask<Long> {
    private Long start;//开始值
    private Long end;//结束值
    private Long criticalValue = 10000L;//临界值，超过这个值开始分任务执行

    public SumForkJoin(Long start, Long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        if (end - start <= criticalValue) {
            Long sum = 0L;
            for (Long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        } else {
            Long middle = (start + end) / 2;
            SumForkJoin task1 = new SumForkJoin(start, middle);
            SumForkJoin task2 = new SumForkJoin(middle + 1, end);

            invokeAll(task1, task2);

            return task1.join() + task2.join();
        }
    }
}
