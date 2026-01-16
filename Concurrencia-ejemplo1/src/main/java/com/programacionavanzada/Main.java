package com.programacionavanzada;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void sleep(int t)
    {
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    static Future<Void> print(String xd)
    {
       return CompletableFuture.runAsync(() -> {
            sleep(2*1000);
            System.out.println(xd);
        });
    }
    static Future<Integer> h(int x)
    {
      //  CompletableFuture.runAsync(() -> {}); no retorna nada

        return CompletableFuture.supplyAsync(()->{
            System.out.println(Thread.currentThread().getName());
            sleep(1000*10);
            return x;
        });
    }
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        var res1 = h(5);
        var res2 = h(10);
        List<Future<Integer>> futures = new ArrayList<>();
        for(int i = 0; i < 24; i++)
        {
            futures.add(h(i));
        }
        futures.stream().forEach(f -> {
            try {
                System.out.println(f.get().toString());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        });
        var xd = print("Hola Mundo");
        System.out.println(res1.get() + res2.get());
        xd.get();
    }
    public static void mainFuture3(String[] args) throws InterruptedException, ExecutionException {
        var cores = Runtime.getRuntime().availableProcessors();
        Runnable task = () -> {
            System.out.println("Iniciando thread");
            sleep(5000);
        };
        var f1 = CompletableFuture.runAsync(task);
        var f2 = CompletableFuture.runAsync(task);
        var f3 = CompletableFuture.runAsync(task);
        var f4 = CompletableFuture.runAsync(task);
        var f5 = CompletableFuture.runAsync(task);
        var f6 = CompletableFuture.runAsync(task);
        var f7 = CompletableFuture.runAsync(task);
        var f8 = CompletableFuture.runAsync(task);
        var f9 = CompletableFuture.runAsync(task);
        var f10 = CompletableFuture.runAsync(task);
        CompletableFuture.allOf(f1,f2,f3,f4,f5,f6,f7,f8,f9,f10).join();
    }

    public static void mainFutures(String[] args) throws ExecutionException, InterruptedException {
        var cores = Runtime.getRuntime().availableProcessors();
       // ExecutorService executor = Executors.newFixedThreadPool(cores);
        ExecutorService executor = Executors.newFixedThreadPool(4);
      /*  System.out.println("cores: " + cores);

        var res1 = executor.submit(()->f(5));
        var res2 = executor.submit(()->g(6));
        var res = res1.get() + res2.get();
        System.out.println(res);
        executor.shutdown(); /// para no hacer esto usar un try catch

        System.out.println(end - start);*/
        var start = System.currentTimeMillis();
        List<Future<Integer>> futures = new ArrayList<>();
        for (int i = 1; i <= cores; i++) {

            futures.add(executor.submit(()-> g(5)));
        }
        var res = futures.stream().map(r-> {
            try {
                return r.get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }).reduce(Integer::sum);
        System.out.println(res.get());
        var end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    // Assuming these methods exist
    private static int f(int x) {
        System.out.println(Thread.currentThread().getName() + " iniciando");
        sleep(1000);
        System.out.print("Uwuwuwuwu");

        return x; // example implementation
    }
    public static class Result {
        public int left;
        public int right;
    }

    private static int g(int x) {
        System.out.println(Thread.currentThread().getName() + " iniciando");
        sleep(1000* 5);
        return x; // example implementation
    }






    public static void main1(String[] args) throws InterruptedException {
        int x = 1337;
        var start = System.currentTimeMillis();
        Result result = new Result();

        Thread t1 = new Thread(() -> {
            result.left = f(x);
        });

        Thread t2 = new Thread(() -> {
            result.right = g(x);
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println(result.left + result.right);
        var end = System.currentTimeMillis();
        System.out.println((end - start)/1000);


    }

}
