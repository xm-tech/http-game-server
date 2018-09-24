package com.xxgames;

import java.util.HashSet;

public class Test {
    public static void main(String[] args) throws InterruptedException {
//        NumberUtils.toInt("9");

//
//        final CountDownLatch latch = new CountDownLatch(10);
//        final Runnable toRun = new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.currentThread().sleep(1000L);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                latch.countDown();
//                System.out.println(latch.getCount());
//            }
//        };
//
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        for (int i = 0; i < 10; i++) {
//            executorService.execute(toRun);
//        }
//        latch.await(20, TimeUnit.SECONDS);
//        System.out.println("stop");

//        long millis = TimeUnit.MINUTES.toMillis(5L);
//        System.out.println(millis);
//
//
//        int[] excludes = new int[]{};
//        excludes = ArrayUtils.add(excludes, 3);
//        excludes = ArrayUtils.add(excludes, 4);
//
//        for (int e : excludes) {
//            System.out.println(e);
//        }

        HashSet<String> set = new HashSet<>();
        set.add("1");
        boolean contains = set.contains("1");
        System.out.println(contains);
        System.out.println(set.contains("2"));
    }
}
