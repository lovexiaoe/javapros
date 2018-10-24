package com.zhaoyu.threads.forkJoin.CreatePool;

import java.util.List;
import java.util.concurrent.RecursiveAction;

public class Task extends RecursiveAction {

    private static final long serialVersionUID = 1L;

    /**
     * List of products
     */
    private List<Product> products;

    /**
     * Fist and Last position of the interval assigned to the task
     */
    private int first;
    private int last;


    private double increment;


    public Task(List<Product> products, int first, int last, double increment) {
        this.products = products;
        this.first = first;
        this.last = last;
        this.increment = increment;
    }

    /**
     * 子任务中任务数小于10个则更新价格，否则划分为更小的任务
     */
    @Override
    protected void compute() {
        if (last - first < 10) {
            updatePrices();
        } else {
            int middle = (last + first) / 2;
            System.out.printf("Task: 排队任务数: %s\n", getQueuedTaskCount());
            Task t1 = new Task(products, first, middle + 1, increment);
            Task t2 = new Task(products, middle + 1, last, increment);
            invokeAll(t1, t2);
        }
    }

    /**
     * Method that updates the prices of the assigned products to the task
     */
    private void updatePrices() {
        for (int i = first; i < last; i++) {
            Product product = products.get(i);
            product.setPrice(product.getPrice() * (1 + increment));
        }
    }
}
