/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.femxa.concurrent;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestExecutorService {

    static ExecutorService executorSingle = Executors.newSingleThreadExecutor();
    static final Instant start = Instant.now();
    static int threadCount = 1;

    //Métodos de Executors
    public static void testExecutorServiceSingleRun() {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                print("Inicio del hilo");
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException ex) {
                    Logger.getLogger(TestExecutorService.class.getName()).log(Level.SEVERE, null, ex);
                }
                print("Finaliza el hilo");
            }
        };
        executorSingle.submit(run);
        executorSingle.shutdown();
    }

    public static void testExecutorServiceSingleCall() {
        try {
            Callable<String> call = () -> {
                print("Inicio del hilo");
                TimeUnit.SECONDS.sleep(2);
                print("Finaliza el hilo");
                return "Fin de la tarea";
            };

            Future<String> future = executorSingle.submit(call);
            print("¿Proceso finalizado? " + future.isDone());
            String message = future.get();
            print("¿Proceso finalizado? " + future.isDone());

            print(message);
            executorSingle.shutdown();

        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(TestExecutorService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void testExecutorFixedThreadPoolCall(int num) {
        try {
            List<Callable<String>> subprocessList = Stream
                    .generate(TestExecutorService::getThreadSleep)
                    .limit(4)
                    .collect(Collectors.toList());

            ExecutorService executorPool = Executors.newFixedThreadPool(num);

            List<Future<String>> results = executorPool.invokeAll(subprocessList);
            results.forEach(fut -> {
                try {
                    print(fut.get());
                } catch (InterruptedException | ExecutionException ex) {
                    Logger.getLogger(TestExecutorService.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            print("Proceso principal continua");

            print(executorPool.invokeAny(subprocessList));

            print("Proceso principal continua");
            executorPool.shutdown();

        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(TestExecutorService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void textExecutorScheduledThreadPoolRun() {

        ScheduledExecutorService executorsScheduled = Executors.newScheduledThreadPool(1);

        Runnable run = () -> {
            try {
                print("Se ejecuta la tarea");
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException ex) {
                Logger.getLogger(TestExecutorService.class.getName()).log(Level.SEVERE, null, ex);
            }
            print("Finaliza la tarea");
        };

        executorsScheduled.schedule(run, 3, TimeUnit.SECONDS);
        executorsScheduled.scheduleAtFixedRate(run, 2, 2, TimeUnit.SECONDS);
        executorsScheduled.scheduleWithFixedDelay(run, 2, 2, TimeUnit.SECONDS);

        try {
            print("Proceso Principal comienza a esperar");
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException ex) {
            Logger.getLogger(TestExecutorService.class.getName()).log(Level.SEVERE, null, ex);
        }

        executorsScheduled.shutdown();
    }

    // Métodos utilizados por los Threads
    private static Callable<String> getThreadSleep() {
        int threadNumber = threadCount++;

        return () -> {
            print("Thread Nº= " + threadNumber + " Start");
            try {
                TimeUnit.SECONDS.sleep(4);
                print("Thread Nº=" + threadNumber + " End");
                return "Thread " + threadNumber + " return";
            } catch (InterruptedException e) {
                return "Thread " + threadNumber + " interrupted";
            }
        };
    }

    private static void print(Object message) {
        System.out.printf("Duración: %s Proceso: [%s] Mensaje: %s \n",
                Duration.between(start, Instant.now()).toMillis(),
                Thread.currentThread().getName(),
                message.toString());
    }
}
