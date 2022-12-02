/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.femxa.app;

import com.femxa.concurrent.Lector;
import com.femxa.concurrent.TestExecutorService;
import com.femxa.concurrent.RunnableImplements;
import com.femxa.concurrent.SumArray;
import com.femxa.concurrent.ThreadExtends;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author richa
 */
public class ApMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //ThreadExtends.testThreads(); 

        //RunnableImplements.testThreads();
        //TestExecutorService.testExecutorServiceSingleRun();
        //TestExecutorService.testExecutorServiceSingleCall();
        //TestExecutorService.testExecutorFixedThreadPoolCall(3);
        //TestExecutorService.textExecutorScheduledThreadPoolRun();
//        
//        List<Integer> lista = new CopyOnWriteArrayList<>(); // Este tipo de lista es Thread Safe
//        CyclicBarrier barrier = new CyclicBarrier(3,() -> { // CyclicBarrier su primer argumento del constructor es el numero de hilos que tiene que esperar
//            System.out.println(lista.stream().mapToInt(n->n).sum());
//        });
//        for (int i = 1; i <=3; i++) {
//            new Thread(new Lector(barrier, lista, "C:\\Prueba\\numbers"+i+".txt")).start();
//        }
//        
//        ExecutorService ex = Executors.newCachedThreadPool();
//        ex.submit(()->System.out.println("Hello"));
//        ex.submit(()->System.out.println("World"));
//        ex.shutdown();
        int a[] = {1, 2, 3, 4, 5};
        MiHilo mh1 = MiHilo.creaEInicia("Hilo Nº1", a);
        MiHilo mh2 = MiHilo.creaEInicia("Hilo Nº2", a);
        try {
            mh1.hilo.join();
            mh2.hilo.join();
        } catch (InterruptedException exc) {
            System.out.println("Hilo principal interrumpido.");
        }
        
    }


}

class MiHilo implements Runnable {

    Thread hilo;
    static SumArray sumarray = new SumArray();
    int a[];
    int resp;

    //Construye un nuevo hilo.
    MiHilo(String nombre, int nums[]) {
        hilo = new Thread(this, nombre);
        a = nums;
    }

    //Un método que crea e inicia un hilo
    public static MiHilo creaEInicia(String nombre, int nums[]) {
        MiHilo miHilo = new MiHilo(nombre, nums);
        miHilo.hilo.start(); //Inicia el hilo
        return miHilo;
    }

    //Punto de entrada del hilo
    @Override
    public void run() {
        //int sum;
        System.out.println(hilo.getName() + " iniciando.");
//        synchronized (sumarray) {
            resp = sumarray.sumArray(a);
//        }
        System.out.println("Suma para " + hilo.getName() + " es " + resp);
        System.out.println(hilo.getName() + " terminado.");
    }
}
