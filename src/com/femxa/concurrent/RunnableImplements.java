/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.femxa.concurrent;

import java.time.Duration;
import java.time.Instant;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author richa
 */
public class RunnableImplements implements Runnable {

    private static final Instant start = Instant.now();

    @Override
    public void run() {
        try {
            print("Comienza el hilo");
            Thread.sleep(5000);
            print("Finaliza el hilo");
        } catch (InterruptedException ex) {
            Logger.getLogger(ThreadExtends.class.getName()).log(Level.SEVERE, "Proceso interrumpido", ex);
        }
    }

    public static void testThreads() {
        var obj = new RunnableImplements();
        Thread t1 = new Thread(obj);
        Thread t2 = new Thread(obj);

        t1.start();
        t2.start();

        try {
            print("Se espera al hilo principal");
            t1.join(3000);
            print("Se termina de esperar al hilo");

            print("Se espera al hilo principal");
            t2.join(2000);
            print("Se termina de esperar al hilo");
        } catch (InterruptedException ex) {
            Logger.getLogger(ThreadExtends.class.getName()).log(Level.SEVERE, "Se interrumpe el proceso", ex);
        }
    }

    private static void print(Object message) {
        System.out.printf("Duración: %s Proceso: [%s] Mensaje: %s \n",
                Duration.between(start, Instant.now()).toMillis(),
                Thread.currentThread().getName(),
                message.toString());
    }
}
